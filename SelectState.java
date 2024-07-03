import java.awt.Color;

public class SelectState extends State {
    StateManager stateManager;
    int Chosen_point_x;
    int Chosen_point_y;
    MyRectangle selectionRect;

    //constructor 
    public SelectState(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    @Override
    //Set what will happen after using mouse in each state
    public void mouseDown(int x, int y) {

        boolean objectClicked = false;
        for (MyDrawing d : stateManager.getMediator().getDrawings()) {
            if (d.contains(x, y)) {
                objectClicked = true;
                break;
            }
        }

        if (objectClicked == false) { //clicked on an empty position 
            //(because if we clicked on a shape, the shape will immediately be added
            //to the selectedDrawing vector, which means getSelectedDrawings != empty)
            selectionRect = new MyRectangle(x, y, 0, 0,  Color.BLACK, new Color(0, 0, 0, 0));
            selectionRect.setDashed(true);
            stateManager.addDrawing(selectionRect);
        } else {
            stateManager.setSelected(x, y); 
            stateManager.updateDrawing();
            Chosen_point_x = x;
            Chosen_point_y = y;
        }
        //clicked on a shape
        //that shape will immediately be added to the selectedDrawing vector,
        //which means getSelectedDrawings == empty
        
    }
    
    public void mouseDrag(int x, int y) {
        //create the selection rectangle
        if (selectionRect != null) {
            int width = x - Chosen_point_x;
            int height = y - Chosen_point_y;
            selectionRect.setSize(width, height);
            stateManager.updateDrawing();

            // Select all drawings within the selection rectangle
            for (MyDrawing d : stateManager.getMediator().getDrawings()) {
                if (selectionRect.contains(d.getX(), d.getY(), d.getW(), d.getH())) {
                    stateManager.getMediator().addSelectedDrawing(d);
                } else {
                    stateManager.getMediator().removeSelectedDrawing(d);
                }
            }

            // Move all the selected drawings
            
        } else { //click on a single shape
            //counting the gap between the point we first clicked and the point after dragging
            int dx = x - Chosen_point_x;
            int dy = y - Chosen_point_y;

            //Move the shape
            if (stateManager.getSelectedDrawings() != null) { //if there is a shape that is selected
                stateManager.move(dx, dy);
                //The point after dragging now became the starting point (treated as "the point we just clicked")
                Chosen_point_x = x;
                Chosen_point_y = y;
                stateManager.updateDrawing();
            }
        }
    }



    public void mouseUp(int x, int y) {
        if (selectionRect != null) {
            stateManager.getMediator().removeDrawing(selectionRect);
            selectionRect = null;
            stateManager.updateDrawing();
        }
    }
}
