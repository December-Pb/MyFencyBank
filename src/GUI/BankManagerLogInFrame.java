package GUI;

import Controller.BankManagerController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class BankManagerLogInFrame extends JFrame{

    private ChooseOptionFrame parentFrame;

    private JTextField managerID = new JTextField();
    private JPasswordField passwordField = new JPasswordField();

    private JLabel managerIDLabel = new JLabel("Manager ID");
    private JLabel passwordLabel = new JLabel("Password");

    private JButton okBtn = new JButton("OK");
    private JButton cancelBtn = new JButton("Cancel");

    public BankManagerLogInFrame(ChooseOptionFrame parentFrame) {
        this.parentFrame = parentFrame;
        init();
        this.setVisible(true);
        this.setSize(new Dimension(350, 200));
        this.setMinimumSize(new Dimension(350, 200));
        this.setMaximumSize(new Dimension(350, 200));
    }

    private void init() {
        Container container = this.getContentPane();
        container.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.insets = new Insets(10, 0, 10, 0);
        add(managerIDLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(10, 0, 10, 0);
        add(managerID, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.insets = new Insets(10, 0, 10, 0);
        add(passwordLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(10, 0, 10, 0);
        add(passwordField, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.insets = new Insets(10, 0, 10, 0);
        add(okBtn, gridBagConstraints);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new Insets(10, 0, 10, 0);
        add(cancelBtn, gridBagConstraints);

        okBtn.addActionListener(e -> {
            String ID = managerID.getText();
            String password = new String(passwordField.getPassword());
            if(BankManagerController.getInstance().loginIn(ID, password)) {
                ManagerFrame managerFrame = new ManagerFrame(this.parentFrame);
                this.setVisible(false);
            }
            else {
                JOptionPane.showMessageDialog(null, "Error Password or ID");
            }
        });

        cancelBtn.addActionListener(e -> {
            parentFrame.setVisible(true);
            this.dispose();
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                int input = JOptionPane.showConfirmDialog(null, "Are you sure to exit? Press OK to exit, Press cancel to cancel", "Select an option...", JOptionPane.OK_CANCEL_OPTION);
                if(input == 0)
                    System.exit(0);
                else{

                }
            }
        });
    }

}
