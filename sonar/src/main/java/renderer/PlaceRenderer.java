package renderer;

import dataAccessLayer.tasks.treeReconstruction.TFVPlace;
import dataAccessLayer.tasks.treeReconstruction.TFVTransition;

import java.awt.*;
import java.util.List;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Map;

//TODO document
public class PlaceRenderer extends NodeRenderer<TFVPlace> {
    private int horizontalPadding = 90;
    private int verticalAssignmentMarginTop = 70;
    private int verticalAssignmentMarginBottom = 20;
    private int boxWidth = 585;
    private int boxHeight = 120;
    private Color borderColor = Color.black;
    private Stroke borderStroke = new BasicStroke(6f);
    private Stroke borderStrokeHidden = new BasicStroke(6f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] {6,9}, 0);
    private Color boxColor = Color.white;
    private Color selectedColor = Color.orange;
    private Color labelColor = Color.black;
    private Color labelColorHidden = Color.lightGray;
    private Font labelFont = new Font("Arial", Font.BOLD | Font.ITALIC, 40);
    private int childVerticalDistance = 200;
    private Color arrowColor = Color.black;
    private Stroke arrowStroke = new BasicStroke(6f);
    private Stroke arrowStrokeHidden = new BasicStroke(6f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] {6,9}, 0);
    private int arrowHeadWidth = 39;
    private int arrowHeadHeight = 33;

    public void renderSubtreeTrackAssignments(TFVPlace place, Graphics2D ctx, int x, int y, int options, boolean selected,
                                              Map<String, List<Rectangle>> assignments, TransitionRenderer childRenderer) {
        int width = getChildrenWidth(place, childRenderer);
        int childY = y + boxHeight + childVerticalDistance;
        int childX = x - width / 2;
        for (TFVTransition child : place.getChildren()) {
            int childWidth = childRenderer.getSubtreeWidth(child, this);
            ctx.setColor(arrowColor);
            if (((options & SONARRenderer.HIDE_NON_WORKFLOW) != 0) && !(child.isExecutableLeaf() && selected)) {
                ctx.setStroke(arrowStrokeHidden);
            } else {
                ctx.setStroke(arrowStroke);
            }
            //Render a line to the child
            int targetX = childX + childWidth / 2;
            int targetY = childY;
            ctx.drawLine(x, y + getHeight(place) / 2, targetX, targetY);
            //Render the child
            boolean childSelected = (child == place.getSelectedTransition()) && selected;
            childRenderer.renderSubtreeTrackAssignments(child, ctx, childX + childWidth / 2, childY, options, childSelected,
                    assignments, this);
            childX += childWidth;
            //Render an arrowhead
            double angle = Math.atan(((double) targetX - x) / ((double) targetY - (y + getHeight(place) / 2)));
            ctx.translate(targetX, targetY);
            ctx.rotate(-angle);
            ctx.fillPolygon(new int[] {0, -arrowHeadWidth / 2, arrowHeadWidth / 2},
                    new int[] {0, -arrowHeadHeight, -arrowHeadHeight},
                    3);
            ctx.rotate(angle);
            ctx.translate(-targetX, -targetY);
        }
        //Render box
        renderBox(place, ctx, x, y, options, selected, place.hasExecutableLeafSelected() && selected);
        //Add to assignments
        String operator = place.getTask().getOperator();
        Rectangle area = new Rectangle(x - getWidth(place) / 2 + horizontalPadding / 2, y - verticalAssignmentMarginTop,
                                       getWidth(place) - horizontalPadding, getHeight(place) + verticalAssignmentMarginTop + verticalAssignmentMarginBottom);
        if (assignments.containsKey(operator)) {
            assignments.get(operator).add(area);
        } else {
            List<Rectangle> areas = new ArrayList<>();
            areas.add(area);
            assignments.put(operator, areas);
        }
        //Render the label
        renderLabel(place, ctx, x, y, options, selected, place.hasExecutableLeafSelected() && selected);
    }

    //TODO document
    public int getSubtreeWidth(TFVPlace place, TransitionRenderer childRenderer) {
        return Math.max(getWidth(place), getChildrenWidth(place, childRenderer));
    }

    //TODO document
    public int getChildrenWidth(TFVPlace place, TransitionRenderer childRenderer) {
        int childrenWidth = 0;
        for (TFVTransition child : place.getChildren()) {
            childrenWidth += childRenderer.getSubtreeWidth(child, this);
        }
        return childrenWidth;
    }

    //TODO document
    public int getSubtreeHeight(TFVPlace place, TransitionRenderer childRenderer) {
        int height = getHeight(place);
        int maxHeight = height;
        for (TFVTransition child : place.getChildren()) {
            int newHeight = childRenderer.getSubtreeHeight(child, this) + childVerticalDistance + height;
            if (newHeight > maxHeight) {
                maxHeight = newHeight;
            }
        }
        return maxHeight;
    }

    @Override
    public void renderBox(TFVPlace place, Graphics2D ctx, int x, int y, int options, boolean selected, boolean workflow) {
        if (((options & SONARRenderer.COLOR_SELECTED) != 0) && selected) {
            ctx.setColor(selectedColor);
        } else if (((options & SONARRenderer.COLOR_WORKFLOW) != 0) && workflow) {
            ctx.setColor(selectedColor);
        } else {
            ctx.setColor(boxColor);
        }
        ctx.fillOval(x - boxWidth / 2, y, boxWidth, boxHeight);
        if (((options & SONARRenderer.HIDE_NON_WORKFLOW) != 0) && !workflow) {
            ctx.setStroke(borderStrokeHidden);
        } else {
            ctx.setStroke(borderStroke);
        }
        ctx.setColor(borderColor);
        ctx.drawOval(x - boxWidth / 2, y, boxWidth, boxHeight);
    }

    @Override
    public void renderLabel(TFVPlace place, Graphics2D ctx, int x, int y, int options, boolean selected, boolean workflow) {
        String text = place.getTask().getTask().toString();
        if (((options & SONARRenderer.HIDE_NON_WORKFLOW) != 0) && !workflow) {
            ctx.setColor(labelColorHidden);
        } else {
            ctx.setColor(labelColor);
        }
        ctx.setFont(labelFont);
        FontMetrics fm = ctx.getFontMetrics();
        Rectangle2D bounds = fm.getStringBounds(text, ctx);
        x -= bounds.getWidth() / 2;
        y += getHeight(place) / 2 - bounds.getHeight() / 2 + fm.getAscent();
        ctx.drawString(text, x, y);
    }

    @Override
    public int getWidth(TFVPlace place) {
        return boxWidth + 2 * horizontalPadding;
    }

    @Override
    public int getHeight(TFVPlace sonarObject) {
        return boxHeight;
    }
}
