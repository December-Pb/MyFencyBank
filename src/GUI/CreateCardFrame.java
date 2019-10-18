package GUI;

import Controller.CustomerController;
import Model.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CreateCardFrame extends JFrame {

    private CustomerFrame parentFrame;

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
        this.setVisible(true);
        this.setMaximumSize(new Dimension(350, 270));
        this.setMinimumSize(new Dimension(350, 270));
        this.setSize(new Dimension(350, 270));
    }

    private void init() {
        Container container = this.getContentPane();
        container.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.insets = new Insets(10,0,10,0);
        add(firstNameLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(10,0,10,0);
        add(firstNameField, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.insets = new Insets(10,0,10,0);
        add(lastNameLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(10,0,10,0);
        add(lastNameField, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.insets = new Insets(10,0,10,0);
        add(passwordLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(10,0,10,0);
        add(passwordField, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.insets = new Insets(10,0,10,0);
        add(registerBtn, gridBagConstraints);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.insets = new Insets(10,0,10,0);
        add(clearBtn, gridBagConstraints);

        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.insets = new Insets(10,0,10,0);
        add(cancleBtn, gridBagConstraints);

        initTextField();

        firstNameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(firstNameFieldClickTime <= 1) {
                    firstNameField.setText("");
                    firstNameField.setForeground(Color.BLACK);
                    firstNameFieldClickTime++;
                }
            }
        });
        lastNameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(lastNameFieldClickTime <= 1) {
                    lastNameField.setText("");
                    lastNameField.setForeground(Color.BLACK);
                    lastNameFieldClickTime++;
                }
            }
        });
        passwordField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(passwordFieldClickTime <= 1) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                    passwordFieldClickTime++;
                }
            }
        });

        registerBtn.addActionListener(e -> {
            CustomerController customerController = CustomerController.getInstance();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String password = new String(passwordField.getPassword());
            Customer customer = customerController.createCustomer(firstName, lastName, password);
            JOptionPane.showConfirmDialog(null, "Your Card Number is: " + customer.getID(), "Card Information", JOptionPane.OK_OPTION);
            this.dispose();
            parentFrame.getCustomerFrame().setVisible(true);
        });
        clearBtn.addActionListener(e -> {
            initTextField();
        });
        cancleBtn.addActionListener(e -> {
            parentFrame.getCustomerFrame().setVisible(true);
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
