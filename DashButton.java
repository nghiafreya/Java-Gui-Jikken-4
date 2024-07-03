import javax.swing.*;
import java.awt.event.*;

public class DashButton extends JButton {
    StateManager stateManager;

    // Constructor
    public DashButton(StateManager stateManager) {
        super("Toggle Dash"); 
        this.stateManager = stateManager;
        addActionListener(new DashListener()); //add an action listener for the button
    }

    class DashListener implements ActionListener {
        public void actionPerformed(ActionEvent e) { //if anything happensto the button
            // execute the code below
            stateManager.setDashed(!stateManager.getDashed());
        }
    }
}
