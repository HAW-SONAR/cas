package renderer;

import dataAccessLayer.tasks.treeReconstruction.TFVForest;
import dataAccessLayer.tasks.treeReconstruction.TFVPlace;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

//TODO document
//Anchor is the upper left corner
public class TFVRenderer implements SONARRenderer<TFVForest> {
    private PlaceRenderer placeRenderer;
    private TransitionRenderer transitionRenderer;
    private int verticalPaddingTop = 140;
    private int verticalPaddingBottom = 60;
    private int horizontalPadding = 80;
    private Color backgroundColor = Color.white;
    private Color backgroundColorWithAreas = Color.gray;
    private Color agentAreaColor = Color.white;
    private Image opaIcon;
    private int opaIconWidth = 112;
    private int opaIconHeight = 102;
    private Color opaLabelColor = Color.black;
    private Font opaLabelFont = new Font("Arial", Font.BOLD | Font.ITALIC, 40);
    private int opaLabelHorizontalPadding = 10;
    private int opaLabelVerticalPadding = 10;

    public TFVRenderer(PlaceRenderer placeRenderer, TransitionRenderer transitionRenderer) {
        this.placeRenderer = placeRenderer;
        this.transitionRenderer = transitionRenderer;
        try {
            opaIcon = ImageIO.read(new File("res/opa.png"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Resource for opa icon is missing.");
        }
    }

    public PlaceRenderer getPlaceRenderer() {
        return placeRenderer;
    }

    public void setPlaceRenderer(PlaceRenderer placeRenderer) {
        this.placeRenderer = placeRenderer;
    }

    @Override
    public void render(TFVForest forest, Graphics2D topCtx, Graphics2D bottomCtx, int x, int y, int options, List<String> filters) {
        int childX = x;
        int childY = y;
        topCtx.setComposite(AlphaComposite.Clear);
        topCtx.fillRect(x, y, getWidth(forest), getHeight(forest));
        topCtx.setComposite(AlphaComposite.SrcOver);
        HashMap<String, List<Rectangle>> assignments = new HashMap<>();
        childX += horizontalPadding;
        childY += verticalPaddingTop;
        for (TFVPlace root : forest.getRoots()) {
            int rootWidth = placeRenderer.getSubtreeWidth(root, transitionRenderer);
            //TODO assuming all roots are selected (?)
            placeRenderer.renderSubtreeTrackAssignments(root, topCtx, childX + rootWidth / 2, childY, options, true, assignments, transitionRenderer);
            childX += rootWidth;
        }
        if ((options & SONARRenderer.HIGHLIGHT_AREAS) != 0) {
            //Draw boxes for assigned agent areas
            bottomCtx.setColor(backgroundColorWithAreas);
            bottomCtx.fillRect(x, y, getWidth(forest), getHeight(forest));
            bottomCtx.setColor(agentAreaColor);
            Map<String, List<Rectangle>> agentAreas = computeAgentAreas(assignments);
            for (String agent : agentAreas.keySet()) {
                for (Rectangle area : agentAreas.get(agent)) {
                    bottomCtx.fillRect(area.x, area.y, area.width, area.height);
                }
            }
            for (String agent : agentAreas.keySet()) {
                boolean first = true;
                for (Rectangle area : agentAreas.get(agent)) {
                    topCtx.setColor(opaLabelColor);
                    topCtx.setFont(opaLabelFont);
                    FontMetrics fm = topCtx.getFontMetrics();
                    Rectangle2D bounds = fm.getStringBounds(agent, topCtx);
                    int opaLabelX = area.x + area.width - (int) Math.round(bounds.getWidth()) - opaLabelHorizontalPadding;
                    int opaLabelY = area.y + fm.getAscent() + opaLabelVerticalPadding;
                    topCtx.drawString(agent, opaLabelX, opaLabelY);
                    if (first) {
                        first = false;
                        if (filters != null) {
                            if (((options & SONARRenderer.HIDE_NON_WORKFLOW) != 0) && !filters.contains(agent)) {
                                continue;
                            }
                        }
                        topCtx.drawImage(opaIcon, area.x, area.y, opaIconWidth, opaIconHeight, null);
                    }
                }
            }
        } else {
            //Just provide a clean background
            bottomCtx.setColor(backgroundColor);
            bottomCtx.fillRect(x, y, getWidth(forest), getHeight(forest));
        }
    }

    private Map<String, List<Rectangle>> computeAgentAreas(Map<String, List<Rectangle>> assignments) {
        Map<String, List<Rectangle>> areas = new HashMap<>();
        for (String agent : assignments.keySet()) {
            areas.put(agent, new LinkedList<>());
        }
        for (String agent : assignments.keySet()) {
            LinkedList<Rectangle> newRects = new LinkedList<>(assignments.get(agent));
            for (Rectangle newRect : newRects) {
                //Try to unify with an existing area
                Rectangle unifiedRect = null;
                Rectangle replacedRect = null;
                for (Rectangle oldRect : areas.get(agent)) {
                    Rectangle unification = unification(oldRect, newRect);
                    boolean unifiable = true;
                    //For all other agents:
                    for (String otherAgent : assignments.keySet()) {
                        if (otherAgent.equals(agent)) {
                            continue;
                        }
                        for (Rectangle otherRect : assignments.get(otherAgent)) {
                            if (otherRect.intersects(unification)) {
                                unifiable = false;
                                break;
                            }
                        }
                        if (!unifiable) {
                            break;
                        }
                    }
                    if (unifiable) {
                        unifiedRect = unification;
                        replacedRect = oldRect;
                        break;
                    }
                }
                if (unifiedRect != null) {
                    //Unify the rects by replacing the old one
                    areas.get(agent).remove(replacedRect);
                    areas.get(agent).add(unifiedRect);
                } else {
                    areas.get(agent).add(newRect);
                }
            }
        }
        return areas;
    }

    private static Rectangle unification(Rectangle rect1, Rectangle rect2) {
        int minX = rect1.x;
        if (rect2.x < minX) minX = rect2.x;
        int minY = rect1.y;
        if (rect2.y < minY) minY = rect2.y;
        int maxX = rect1.x + rect1.width;
        if (rect2.x + rect2.width > maxX) maxX = rect2.x + rect2.width;
        int maxY = rect1.y + rect1.height;
        if (rect2.y + rect2.height > maxY) maxY = rect2.y + rect2.height;
        return new Rectangle(minX, minY, maxX - minX, maxY - minY);
    }

    @Override
    public int getWidth(TFVForest forest) {
        int width = 0;
        for (TFVPlace root : forest.getRoots()) {
            width += placeRenderer.getSubtreeWidth(root, transitionRenderer);
        }
        return width + 2 * horizontalPadding;
    }

    @Override
    public int getHeight(TFVForest forest) {
        int height = 0;
        for (TFVPlace root : forest.getRoots()) {
            int newHeight = placeRenderer.getSubtreeHeight(root, transitionRenderer);
            if (newHeight > height) {
                height = newHeight;
            }
        }
        return height + verticalPaddingTop + verticalPaddingBottom;
    }
}
