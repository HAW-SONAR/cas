package renderer;

import java.awt.*;
import java.util.List;

// TODO document
public interface SONARRenderer<O> {
    int HIGHLIGHT_AREAS =   0b00000001;
    int COLOR_TRANSITIONS = 0b00000010;
    int COLOR_SELECTED =    0b00000100;
    int COLOR_WORKFLOW =    0b00001000;
    int HIDE_NON_WORKFLOW = 0b00010000;
    int EXECUTION_ICONS =   0b00100000;

    public void render(O sonarObject, Graphics2D topCtx, Graphics2D bottomCtx, int x, int y, int options, List<String> filters);
    public int getWidth(O sonarObject);
    public int getHeight(O sonarObject);
}
