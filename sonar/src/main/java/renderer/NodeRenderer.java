package renderer;

import dataAccessLayer.tasks.treeReconstruction.TFVNode;

import java.awt.*;
import java.util.List;

//TODO document
//Anchor is the center of the top edge
public abstract class NodeRenderer<N extends TFVNode<?>> implements SONARRenderer<N> {

    public abstract void renderBox(N node, Graphics2D ctx, int x, int y, int options, boolean selected, boolean workflow);
    public abstract void renderLabel(N node, Graphics2D ctx, int x, int y, int options, boolean selected, boolean workflow);

    @Override
    public void render(N node, Graphics2D topCtx, Graphics2D bottomCtx, int x, int y, int options, List<String> filters) {
        renderBox(node, topCtx, x, y, options, false, false);
        renderLabel(node, topCtx, x, y, options, false, false);
    }
}
