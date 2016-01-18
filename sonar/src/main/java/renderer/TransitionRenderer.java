package renderer;

import dataAccessLayer.tasks.OperationType;
import dataAccessLayer.tasks.treeReconstruction.TFVPlace;
import dataAccessLayer.tasks.treeReconstruction.TFVTransition;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

//TODO document
public class TransitionRenderer extends NodeRenderer<TFVTransition> {
    private int horizontalPadding = 36;
    private int verticalAssignmentMarginTop = 70;
    private int verticalAssignmentMarginBottom = 20;
    private int boxWidth = 318;
    private int boxHeight = 114;
    private Color borderColor = Color.black;
    private Stroke borderStroke = new BasicStroke(6f);
    private Stroke borderStrokeHidden = new BasicStroke(6f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] {6,9}, 0);
    private Color labelColor = Color.black;
    private Color labelColorHidden = Color.lightGray;
    private Font labelFont = new Font("Arial", Font.BOLD | Font.ITALIC, 60);
    private int childVerticalDistance = 160;
    private Color arrowColor = Color.black;
    private Stroke arrowStroke = new BasicStroke(6f);
    private Stroke arrowStrokeHidden = new BasicStroke(6f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] {6,9}, 0);
    private int arrowHeadWidth = 39;
    private int arrowHeadHeight = 33;
    private Map<OperationType, Color> typeColors = new HashMap<>();
    private Color defaultColor = Color.white;
    private Color selectedColor = Color.orange;

    /**
     * Default constructor
     */
    public TransitionRenderer() {
        typeColors.put(OperationType.EXEC, new Color(255, 255, 0));
        typeColors.put(OperationType.DELEG, new Color(35, 142, 104));
        typeColors.put(OperationType.REFINE, new Color(255, 0, 0));
        typeColors.put(OperationType.SPLIT, new Color(255, 175, 175));
    }

    //TODO document
    public void renderSubtreeTrackAssignments(TFVTransition transition, Graphics2D ctx, int x, int y, int options, boolean selected,
                                              Map<String, List<Rectangle>> assignments, PlaceRenderer childRenderer) {
        int width = getChildrenWidth(transition, childRenderer);
        int childY = y + boxHeight + childVerticalDistance;
        int childX = x - width / 2;
        for (TFVPlace child : transition.getChildren()) {
            int childWidth = childRenderer.getSubtreeWidth(child, this);
            ctx.setColor(arrowColor);
            if (((options & SONARRenderer.HIDE_NON_WORKFLOW) != 0) && !(transition.isExecutableLeaf() && selected)) {
                ctx.setStroke(arrowStrokeHidden);
            } else {
                ctx.setStroke(arrowStroke);
            }
            //Render a line to the child
            int targetX = childX + childWidth / 2;
            int targetY = childY;
            ctx.drawLine(x, y + getHeight(transition) / 2, targetX, targetY);
            //Render the child
            childRenderer.renderSubtreeTrackAssignments(child, ctx, childX + childWidth / 2, childY, options, selected,
                                                        assignments, this);
            childX += childWidth;
            //Render an arrowhead
            double angle = Math.atan(((double) targetX - x) / ((double) targetY - (y + getHeight(transition) / 2)));
            ctx.translate(targetX, targetY);
            ctx.rotate(-angle);
            ctx.fillPolygon(new int[] {0, -arrowHeadWidth / 2, arrowHeadWidth / 2},
                    new int[] {0, -arrowHeadHeight, -arrowHeadHeight},
                    3);
            ctx.rotate(angle);
            ctx.translate(-targetX, -targetY);
        }
        //Render box
        renderBox(transition, ctx, x, y, options, selected, transition.isExecutableLeaf() && selected);
        //Add to assignments
        String operator = transition.getOperation().getInput().getOperator();
        Rectangle area = new Rectangle(x - getWidth(transition) / 2 + horizontalPadding / 2, y - verticalAssignmentMarginTop,
                                       getWidth(transition) - horizontalPadding, getHeight(transition) + verticalAssignmentMarginTop + verticalAssignmentMarginBottom);
        if (assignments.containsKey(operator)) {
            assignments.get(operator).add(area);
        } else {
            List<Rectangle> areas = new ArrayList<>();
            areas.add(area);
            assignments.put(operator, areas);
        }
        //Render the label
        renderLabel(transition, ctx, x, y, options, selected, transition.isExecutableLeaf() && selected);
    }

    /**
     * Calculates the width of the rectangle necessary for rendering a transition and its subtree (bounding box).
     * @param transition The transition
     * @param childRenderer The renderer used for places in the subtree
     * @return The height of the bounding box
     */
    public int getSubtreeWidth(TFVTransition transition, PlaceRenderer childRenderer) {
        return Math.max(getWidth(transition), getChildrenWidth(transition, childRenderer));
    }

    /**
     * Calculates the width of the rectangle necessary for rendering a transition's children and their subtrees (bounding box).
     * @param transition The transition
     * @param childRenderer The renderer used for the transition's children
     * @return The height of the bounding box
     */
    public int getChildrenWidth(TFVTransition transition, PlaceRenderer childRenderer) {
        int childrenWidth = 0;
        for (TFVPlace child : transition.getChildren()) {
            childrenWidth += childRenderer.getSubtreeWidth(child, this);
        }
        return childrenWidth;
    }

    /**
     * Calculates the height of the rectangle necessary for rendering a transition and its subtree (bounding box).
     * @param transition The transition
     * @param childRenderer The renderer used for places in the subtree
     * @return The height of the bounding box
     */
    public int getSubtreeHeight(TFVTransition transition, PlaceRenderer childRenderer) {
        int height = getHeight(transition);
        int maxHeight = height;
        for (TFVPlace child : transition.getChildren()) {
            int newHeight = childRenderer.getSubtreeHeight(child, this) + childVerticalDistance + height;
            if (newHeight > maxHeight) {
                maxHeight = newHeight;
            }
        }
        return maxHeight;
    }

    @Override
    public int getWidth(TFVTransition transition) {
        return boxWidth + 2 * horizontalPadding;
    }

    @Override
    public int getHeight(TFVTransition sonarObject) {
        return boxHeight;
    }

    @Override
    public void renderBox(TFVTransition transition, Graphics2D ctx, int x, int y, int options, boolean selected, boolean workflow) {
        if ((options & SONARRenderer.COLOR_TRANSITIONS) != 0) {
            ctx.setColor(typeColors.get(transition.getOperation().getType()));
        } else if (((options & SONARRenderer.COLOR_SELECTED) != 0) && selected) {
            ctx.setColor(selectedColor);
        } else if (((options & SONARRenderer.COLOR_WORKFLOW) != 0) && workflow) {
            ctx.setColor(selectedColor);
        } else {
            ctx.setColor(defaultColor);
        }
        ctx.fillRect(x - boxWidth / 2, y, boxWidth, boxHeight);
        if (((options & SONARRenderer.HIDE_NON_WORKFLOW) != 0) && !workflow) {
            ctx.setStroke(borderStrokeHidden);
        } else {
            ctx.setStroke(borderStroke);
        }
        ctx.setColor(borderColor);
        ctx.drawRect(x - boxWidth / 2, y, boxWidth, boxHeight);
    }

    @Override
    public void renderLabel(TFVTransition transition, Graphics2D ctx, int x, int y, int options, boolean selected, boolean workflow) {
        String text = transition.getOperation().getType().toString();
        if (((options & SONARRenderer.HIDE_NON_WORKFLOW) != 0) && !workflow) {
            ctx.setColor(labelColorHidden);
        } else {
            ctx.setColor(labelColor);
        }
        ctx.setFont(labelFont);
        FontMetrics fm = ctx.getFontMetrics();
        Rectangle2D bounds = fm.getStringBounds(text, ctx);
        x -= bounds.getWidth() / 2;
        y += getHeight(transition) / 2 - bounds.getHeight() / 2 + fm.getAscent();
        ctx.drawString(text, x, y);
    }
}
