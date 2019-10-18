package GUI;

import Controller.CustomerController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CreateAccountFrame extends JFrame {

    JComboBox accountCategory = new JComboBox();
    JComboBox currencyCategory = new JComboBox();

    JLabel moneyLable = new JLabel("Money");
    JLabel currencyLable = new JLabel("Currency");
    JLabel accountLable = new JLabel("Account");

    NumberTextField moneyTextField = new NumberTextField();

    JButton okBtn = new JButton("OK");
    JButton cancelBtn = new JButton("Cancel");

    CustomerFrame parentFrame;

    public CreateAccountFrame(CustomerFrame parentFrame) {
        init();
        this.parentFrame = parentFrame;
        this.setVisible(true);
        this.setSize(new Dimension(350, 200));
        this.setMinimumSize(new Dimension(350, 200));
        this.setMaximumSize(new Dimension(350, 200));
    }

    private void init() {

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        Container container = this.getContentPane();

        container.setLayout(new GridBagLayout());

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(10, 0, 10, 0);
        container.add(moneyLable, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(10, 0, 10, 0);
        container.add(moneyTextField, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.insets = new Insets(10, 0, 10, 0);
        container.add(currencyLable, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(10, 0, 10, 0);
        container.add(currencyCategory, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.insets = new Insets(10, 0, 10, 0);
        container.add(accountLable, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(10, 0, 10, 0);
        container.add(accountCategory, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.insets = new Insets(10, 0, 10, 0);
        container.add(currencyLable, gridBagConstraints);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.insets = new Insets(10, 0, 10, 0);
        container.add(okBtn, gridBagConstraints);

        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.insets = new Insets(10, 0, 10, 0);
        container.add(cancelBtn, gridBagConstraints);

        accountCategory.addItem("Saving Account");
        accountCategory.addItem("Checking Account");

        currencyCategory.addItem("RMB");
        currencyCategory.addItem("Dollar");
        currencyCategory.addItem("GBP");

        cancelBtn.addActionListener(e -> {
            this.dispose();
            parentFrame.setVisible(true);
        });

        okBtn.addActionListener(e -> {
            String ID = parentFrame.getNameLabel().getText();
            String category = accountCategory.getSelectedItem().toString();
            double money = Double.valueOf(moneyTextField.getText());
            String currency = currencyCategory.getSelectedItem().toString();
            CustomerController.getInstance().createAccout(ID, category, money, currency);
            parentFrame.returnCustomerFrame(ID);
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
