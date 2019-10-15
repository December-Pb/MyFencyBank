package ATMGui;

import javax.swing.*;
import java.awt.*;

public class CreateCardFrame extends JFrame {

    private JFrame createCardFrame = new JFrame();

    private JPanel fieldPanel = new JPanel();
    private JPanel btnPanel = new JPanel();

    private JLabel firstNameLabel = new JLabel("First Name");
    private JLabel lastNameLabel = new JLabel("Last Name");
    private JLabel passwordLabel = new JLabel("Password");

    private JTextField firstNameField = new JTextField("First Name");
    private JTextField lastNameField = new JTextField("Last Name");
    private JPasswordField passwordField = new JPasswordField("Password");

    private JButton registerBtn = new JButton("Register");
    private JButton clearBtn = new JButton("Clear");
    private JButton cancleBtn = new JButton("Cancel");

    public CreateCardFrame() {
        init();
        createCardFrame.setVisible(true);
        createCardFrame.setMaximumSize(new Dimension(300, 700));
        createCardFrame.setMinimumSize(new Dimension(300, 700));
        createCardFrame.setSize(new Dimension(300, 700));
    }

    private void init() {
        createCardFrame.setLayout(new BorderLayout());
        createCardFrame.add("Center", fieldPanel);
        createCardFrame.add("South", btnPanel);

        firstNameField.setMaximumSize(new Dimension(200, 30));
        lastNameField.setMaximumSize(new Dimension(200, 30));
        passwordField.setMaximumSize(new Dimension(200, 30));

        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));

        fieldPanel.add(firstNameLabel);
        fieldPanel.add(Box.createVerticalStrut(5));
        fieldPanel.add(firstNameField);
        fieldPanel.add(Box.createVerticalStrut(5));
        fieldPanel.add(lastNameLabel);
        fieldPanel.add(Box.createVerticalStrut(5));
        fieldPanel.add(lastNameField);
        fieldPanel.add(Box.createVerticalStrut(5));
        fieldPanel.add(passwordLabel);
        fieldPanel.add(Box.createVerticalStrut(5));
        fieldPanel.add(passwordField);
        fieldPanel.add(Box.createVerticalStrut(5));

        btnPanel.add(registerBtn);
        btnPanel.add(Box.createHorizontalStrut(10));
        btnPanel.add(clearBtn);
        btnPanel.add(Box.createHorizontalStrut(10));
        btnPanel.add(cancleBtn);
    }
}
