package businessLogicLayer.sonar;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dataAccessLayer.agents.IOpa;
import dataAccessLayer.IOrganisation;
import dataAccessLayer.tasks.Task;
import dataAccessLayer.tasks.treeReconstruction.TFVForest;
import dataAccessLayer.tasks.treeReconstruction.TFVPlace;
import renderer.PlaceRenderer;
import renderer.SONARRenderer;
import renderer.TFVRenderer;
import renderer.TransitionRenderer;

import javax.imageio.ImageIO;

public class SonarExample implements ISonar {
    private PlaceRenderer placeRenderer = new PlaceRenderer();
    private TransitionRenderer transitionRenderer = new TransitionRenderer();
    private TFVRenderer formationViewRenderer = new TFVRenderer(placeRenderer, transitionRenderer);
    private String runName;

    public SonarExample(String runName) {
        this.runName = runName;
    }

    @Override
	public void startSonar(IOrganisation organisation) {
        System.out.println("SONAR start.");

		//Get all OPAs of the organisation
		List<IOpa> opas = organisation.getOpas();

        //Render the initial formation views of the OPAs
        for (IOpa opa : opas) {
            renderToFile("PNG", new File(runName + "/opaViews/" + opa.getName() + "_initialFormationView.png"),
                         opa.getTeamFormationView(), SONARRenderer.COLOR_TRANSITIONS | SONARRenderer.HIGHLIGHT_AREAS, null);
        }

        //Provide OPA proxies to all OPAs
		for (IOpa opa1 : opas) {
			List<IOpa> opaReferences = new ArrayList<>();
			for (IOpa opa2 : opas) {
				if (!opa2.getName().equals(opa1.getName())) {
					opaReferences.add(opa2);
				}
			}
			opa1.setOpaProxies(opaReferences);
		}

        //Compute the initial nondeterministic global team formation view and acquire a root
        TFVForest initialGlobalView = new TFVForest();
        for (IOpa opa : opas) {
            try {
                initialGlobalView.mergeIn(opa.getTeamFormationView());
            } catch (TFVForest.TFVForestMergeException e) {
                //NOTE this can not occur in the current version
                e.printStackTrace();
            }
        }
        TFVPlace root = initialGlobalView.getRoots().get(0);


        //Start the agent that is responsible for the root
		for (IOpa opa : opas) {
			if (opa.getName().equals(root.getTask().getOperator())) {
                opa.enqueue(root.getTask());
				opa.start();
				break;
			}
		}

        //Construct the induced workflow string
        StringBuilder workflow = new StringBuilder("\tD = ");
        for (IOpa opa : opas) {
            workflow.append(opa.getInducedTeamWorkflow());
        }
        System.out.println("The induced team workflow:");
        System.out.println(workflow);

        //Compute the nondeterministic global team formation view
        TFVForest globalFormationView = new TFVForest();
        for (IOpa opa : opas) {
            try {
                globalFormationView.mergeIn(opa.getTeamFormationView());
            } catch (TFVForest.TFVForestMergeException e) {
                //NOTE this can not occur in the current version
                e.printStackTrace();
            }
        }
        //Render the global team formation view on slide 1
        renderToFile("PNG", new File(runName + "/slides/slide1.png"),
                globalFormationView, 0, null);
        //Slide 2 includes agent areas and transition colors
        renderToFile("PNG", new File(runName + "/slides/slide2.png"),
                     globalFormationView, SONARRenderer.HIGHLIGHT_AREAS | SONARRenderer.COLOR_TRANSITIONS, null);
        //Slide 3 displays the highlighted chosen deterministic team formation
        renderToFile("PNG", new File(runName + "/slides/slide3.png"),
                globalFormationView, SONARRenderer.HIGHLIGHT_AREAS | SONARRenderer.COLOR_SELECTED, null);
        //Slide 4 displays the induced team workflow
        List<String> workingAgents = new ArrayList<>();
        for (IOpa opa : opas) {
            if (opa.hasExecutableTasks()) {
                workingAgents.add(opa.getName());
            }
        }
        renderToFile("PNG", new File(runName + "/slides/slide4.png"),
                globalFormationView, SONARRenderer.HIGHLIGHT_AREAS | SONARRenderer.COLOR_WORKFLOW
                                   | SONARRenderer.HIDE_NON_WORKFLOW | SONARRenderer.EXECUTION_ICONS, workingAgents);

        //Compute the deterministic global team formation view
        TFVForest deterministicGlobalView = globalFormationView.copyDeterministic();

        //Render the deterministic global team formation view on slide 5
        renderToFile("PNG", new File(runName + "/slides/slide5.png"),
                deterministicGlobalView, SONARRenderer.HIGHLIGHT_AREAS | SONARRenderer.COLOR_TRANSITIONS
                                       | SONARRenderer.EXECUTION_ICONS, null);

	}

    //TODO document
    private void renderToFile(String type, File file, TFVForest formationView, int options, List<String> filters) {
        BufferedImage imgTop = new BufferedImage(formationViewRenderer.getWidth(formationView),
                                                 formationViewRenderer.getHeight(formationView),
                                                 BufferedImage.TYPE_INT_ARGB);
        BufferedImage imgBottom = new BufferedImage(formationViewRenderer.getWidth(formationView),
                                                    formationViewRenderer.getHeight(formationView),
                                                    BufferedImage.TYPE_INT_ARGB);
        Graphics2D topCtx = imgTop.createGraphics();
        Graphics2D bottomCtx = imgBottom.createGraphics();
        formationViewRenderer.render(formationView, topCtx, bottomCtx, 0, 0, options, filters);
        topCtx.dispose();
        bottomCtx.drawImage(imgTop, 0, 0, null);
        bottomCtx.dispose();
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            ImageIO.write(imgBottom, type, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
