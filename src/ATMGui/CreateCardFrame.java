package ATMGui;

import ATM.CustomerATM;
import User.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.UUID;

public class CreateCardFrame extends JFrame {

    private JFrame createCardFrame = new JFrame();
    private CustomerFrame parentFrame;

    private JPanel fieldPanel = new JPanel();
    private JPanel btnPanel = new JPanel();

    private JLabel firstNameLabel = new JLabel("First Name");
    private JLabel lastNameLabel = new JLabel("Last Name");
    private JLabel passwordLabel = new JLabel("Password");

    private JTextField firstNameField = new JTextField();
    private JTextField lastNameField = new JTextField();
    private JPasswordField passwordField = new JPasswordField();

    private JButton registerBtn = new JButton("Register");
    private JButton clearBtn = new JButton("Clear");
    private JButton cancleBtn = new JButton("Cancel");

    private int firstNameFieldClickTime;
    private int lastNameFieldClickTime;
    private int passwordFieldClickTime;

    public CreateCardFrame(CustomerFrame parentFrame) {
        init();
        this.parentFrame = parentFrame;
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

        initTextField();

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

        firstNameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(firstNameFieldClickTime <= 1) {
                    firstNameField.setText("");
                    firstNameFieldClickTime++;
                }
            }
        });
        lastNameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(lastNameFieldClickTime <= 1) {
                    lastNameField.setText("");
                    lastNameFieldClickTime++;
                }
            }
        });
        passwordField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(passwordFieldClickTime <= 1) {
                    passwordField.setText("");
                    passwordFieldClickTime++;
                }
            }
        });

        registerBtn.addActionListener(e -> {
            CustomerATM customerATM = parentFrame.getCustomerController().getAtm();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String password = new String(passwordField.getPassword());
            String userName = firstName + lastName;
            String ID = UUID.randomUUID().toString().substring(0,8);
            Customer tempCustomer = new Customer(ID, userName, password, firstName, lastName, ID);
            customerATM.getBankSystemUsers().put(ID, tempCustomer);
            createCardFrame.dispose();
            parentFrame.getCustomerFrame().setVisible(true);
        });
        clearBtn.addActionListener(e -> {
            initTextField();
        });
        cancleBtn.addActionListener(e -> {
            createCardFrame.dispose();
            parentFrame.getCustomerFrame().setVisible(true);
        });
    }

    private void initTextField() {
        firstNameField.setText("First Name");
        firstNameField.setForeground(Color.GRAY);
        firstNameFieldClickTime = 0;
        lastNameField.setText("Last Name");
        lastNameField.setForeground(Color.GRAY);
        lastNameFieldClickTime = 0;
        passwordField.setText("Password");
        passwordFieldClickTime = 0;
    }
}
