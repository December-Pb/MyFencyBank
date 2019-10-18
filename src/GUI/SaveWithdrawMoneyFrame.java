package GUI;

import Controller.CustomerController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SaveWithdrawMoneyFrame extends JFrame{

    private CustomerFrame parentFrame;

    private JComboBox optionComboBox = new JComboBox();
    private JComboBox currencyComboBox = new JComboBox();

    private JLabel moneyLabel = new JLabel("Money");
    private JLabel currencyLable = new JLabel("Currency");
    private JLabel optionLabel = new JLabel("Option");

    private NumberTextField moneyTextField = new NumberTextField();

    private JButton okBtn = new JButton("OK");
    private JButton cancelBtn = new JButton("Cancle");

    public SaveWithdrawMoneyFrame(CustomerFrame parentFrame) {
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
        container.add(moneyLabel, gridBagConstraints);

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
        container.add(currencyComboBox, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.insets = new Insets(10, 0, 10, 0);
        container.add(optionLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(10, 0, 10, 0);
        container.add(optionComboBox, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.insets = new Insets(10, 0, 10, 0);
        container.add(okBtn, gridBagConstraints);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.insets = new Insets(10, 0, 10, 0);
        container.add(cancelBtn, gridBagConstraints);

        optionComboBox.addItem("Save Money");
        optionComboBox.addItem("Withdraw Money");

        currencyComboBox.addItem("RMB");
        currencyComboBox.addItem("Dollar");
        currencyComboBox.addItem("GBP");

        cancelBtn.addActionListener(e -> {
            parentFrame.setVisible(true);
            this.dispose();
        });

        okBtn.addActionListener(e -> {
            String option = optionComboBox.getSelectedItem().toString();
            String currency = currencyComboBox.getSelectedItem().toString();
            double money = Double.valueOf(moneyTextField.getText());
            String ID = parentFrame.getNameLabel().getText();
            int selectRow = parentFrame.getTable().getSelectedRow();
            String accountNumber = parentFrame.getTable().getValueAt(selectRow, 1).toString();
            boolean success;
            if(option.equals("Withdraw Money")) {
                success = CustomerController.getInstance().withdrawMoney(ID, accountNumber, currency, money);
                if(success) {
                    parentFrame.returnCustomerFrame(ID);
                    parentFrame.setVisible(true);
                    this.dispose();
                }
                else
                    JOptionPane.showMessageDialog(null, "Withdraw Money Failed");
            }
            else {
                success = CustomerController.getInstance().saveMoney(ID, accountNumber, currency, money);
                if(success) {
                    parentFrame.returnCustomerFrame(ID);
                    this.dispose();
                }
                else
                    JOptionPane.showMessageDialog(null, "Save Money Failed");
            }

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
