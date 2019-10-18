package GUI;

import Controller.CustomerController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class CustomerLonginFrame extends JFrame {

    private CustomerFrame parentFrame;

    private JTextField cardNumberTextField = new JTextField();
    private JPasswordField passwordField = new JPasswordField();

    private JLabel cardNumberLabel = new JLabel("Card Number");
    private JLabel passwordLabel = new JLabel("Password");

    private JButton okBtn = new JButton("OK");
    private JButton cancelBtn = new JButton("Cancel");

    public CustomerLonginFrame(CustomerFrame parentFrame) {
        this.parentFrame = parentFrame;
        init();
        this.setVisible(true);
        this.setSize(new Dimension(300, 200));
        this.setMinimumSize(new Dimension(300, 200));
        this.setMaximumSize(new Dimension(300, 200));
    }

    private void init() {
        Container container = this.getContentPane();
        container.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(10,0,10,0);
        add(cardNumberLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        add(cardNumberTextField, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1;
        add(passwordLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        add(passwordField, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        add(okBtn, gridBagConstraints);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        add(cancelBtn, gridBagConstraints);

        okBtn.addActionListener(e -> {
            String ID = cardNumberTextField.getText();
            String password = new String(passwordField.getPassword());
            if(CustomerController.getInstance().loginIn(ID, password)) {
                parentFrame.returnCustomerFrame(ID);
                this.dispose();
            }
            else {
                JOptionPane.showMessageDialog(null, "Error Password or Card Number");
            }
        });

        cancelBtn.addActionListener(e -> {
            parentFrame.setVisible(true);
            this.dispose();
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                parentFrame.setVisible(true);
                dispose();
            }
        });
    }
}
