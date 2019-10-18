package GUI;

import Controller.CustomerController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class TransferMoneyFrame extends JFrame {

    private JLabel transferInLabel = new JLabel("Transfer In");
    private JLabel transferOutLabel = new JLabel("Transfer Out");
    private JLabel transferInMoneyLabel = new JLabel("Money:");
    private JLabel transferInAccountLabel = new JLabel("To Account Number");
    private JLabel transferInCurrency = new JLabel("To Currency");
    private JLabel transferOutMoneyLabel = new JLabel();
    private JLabel transferOutAccountLabel = new JLabel("From Account Number");
    private JLabel transferOutCurrency = new JLabel("From Currency");

    private NumberTextField inputMoneyTextField = new NumberTextField();
    private JLabel outputMoneyTextField = new JLabel();

    private JComboBox inputAccountNumberComboBox = new JComboBox();
    private JComboBox outputAccountNumberComboBox = new JComboBox();
    private JComboBox inputCurrencyComboBox = new JComboBox();
    private JComboBox outputCurrencyComboBox = new JComboBox();

    private JButton okBtn = new JButton("OK");
    private JButton cancelBtn = new JButton("Cancel");

    private CustomerFrame parentFrame;

    public TransferMoneyFrame(CustomerFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.setVisible(true);
        this.setMaximumSize(new Dimension(800, 250));
        this.setMinimumSize(new Dimension(800, 250));
        this.setSize(new Dimension(800, 250));
        init();
    }

    private void init() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        Container container = this.getContentPane();

        container.setLayout(new GridBagLayout());
        gridBagConstraints.insets = new Insets(5,5,5,5);

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(transferInLabel, gridBagConstraints);

        gridBagConstraints.gridy = 1;
        add(transferInMoneyLabel, gridBagConstraints);

        gridBagConstraints.gridy = 2;
        add(transferInAccountLabel, gridBagConstraints);

        gridBagConstraints.gridy = 3;
        add(transferInCurrency, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        add(inputMoneyTextField, gridBagConstraints);

        gridBagConstraints.gridy = 2;
        add(inputAccountNumberComboBox, gridBagConstraints);

        gridBagConstraints.gridy = 3;
        add(inputCurrencyComboBox, gridBagConstraints);

        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        add(transferOutLabel, gridBagConstraints);

        gridBagConstraints.gridy = 1;
        add(transferOutMoneyLabel, gridBagConstraints);

        gridBagConstraints.gridy = 2;
        add(transferOutAccountLabel, gridBagConstraints);

        gridBagConstraints.gridy = 3;
        add(transferOutCurrency, gridBagConstraints);

        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        add(outputMoneyTextField, gridBagConstraints);

        gridBagConstraints.gridy = 2;
        add(outputAccountNumberComboBox, gridBagConstraints);

        gridBagConstraints.gridy = 3;
        add(outputCurrencyComboBox, gridBagConstraints);

        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridx = 2;
        add(okBtn, gridBagConstraints);

        gridBagConstraints.gridx = 3;
        add(cancelBtn, gridBagConstraints);

        inputCurrencyComboBox.addItem("RMB");
        inputCurrencyComboBox.addItem("Dollar");
        inputCurrencyComboBox.addItem("GBP");
        outputCurrencyComboBox.addItem("RMB");
        outputCurrencyComboBox.addItem("Dollar");
        outputCurrencyComboBox.addItem("GBP");

        List<String> accountNumberList =  CustomerController.getInstance().getAccountNumber(parentFrame.getNameLabel().getText());
        for(String number : accountNumberList) {
            inputAccountNumberComboBox.addItem(number);
            outputAccountNumberComboBox.addItem(number);
        }

        inputAccountNumberComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String ID = parentFrame.getNameLabel().getText();
                String outAccount = outputAccountNumberComboBox.getSelectedItem().toString();
                String outCurrency = outputCurrencyComboBox.getSelectedItem().toString();
                double money = CustomerController.getInstance().getMoney(ID, outCurrency, outAccount);
                inputMoneyTextField.setText(String.valueOf(money));
            }
        });

        inputCurrencyComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String ID = parentFrame.getNameLabel().getText();
                String outAccount = outputAccountNumberComboBox.getSelectedItem().toString();
                String outCurrency = outputCurrencyComboBox.getSelectedItem().toString();
                double money = CustomerController.getInstance().getMoney(ID, outCurrency, outAccount);
                inputMoneyTextField.setText(String.valueOf(money));
            }
        });

        okBtn.addActionListener(e -> {
            String ID = parentFrame.getNameLabel().getText();
            String outputAccount = outputAccountNumberComboBox.getSelectedItem().toString();
            String outputCurrency = outputCurrencyComboBox.getSelectedItem().toString();
            String inputAccount = inputAccountNumberComboBox.getSelectedItem().toString();
            String inputCurrency = inputCurrencyComboBox.getSelectedItem().toString();
            double inputMoney = Double.valueOf(inputMoneyTextField.getText());
            CustomerController.getInstance().transferMoney(ID, inputAccount, inputCurrency, outputAccount, outputCurrency, inputMoney);
            parentFrame.returnCustomerFrame(ID);
            this.dispose();
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
