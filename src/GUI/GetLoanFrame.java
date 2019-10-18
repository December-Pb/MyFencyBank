package GUI;

import Controller.CustomerController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GetLoanFrame extends JFrame{

    private JComboBox pawnCategory = new JComboBox();
    private JComboBox currencyCategory = new JComboBox();

    private JLabel moneyLabel = new JLabel("Money");
    private JLabel currencyLable = new JLabel("Currency");
    private JLabel pawnLabel = new JLabel("Pawn");

    private NumberTextField moneyTextField = new NumberTextField();

    private JButton okBtn = new JButton("OK");
    private JButton cancelBtn = new JButton("Cancel");

    private CustomerFrame parentFrame;

    public GetLoanFrame(CustomerFrame parentFrame) {
        this.parentFrame = parentFrame;
        init();
        this.setVisible(true);
        this.setSize(new Dimension(350, 200));
        this.setMinimumSize(new Dimension(350, 200));
        this.setMaximumSize(new Dimension(350, 200));
    }

    private void init() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        Container container = getContentPane();

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
        container.add(currencyCategory, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.insets = new Insets(10, 0, 10, 0);
        container.add(pawnLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(10, 0, 10, 0);
        container.add(pawnCategory, gridBagConstraints);

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

        currencyCategory.addItem("RMB");
        currencyCategory.addItem("Dollar");
        currencyCategory.addItem("GBP");

        pawnCategory.addItem("Pen");
        pawnCategory.addItem("Sedan Car");
        pawnCategory.addItem("Sport Car");
        pawnCategory.addItem("House");

        cancelBtn.addActionListener(e -> {
            parentFrame.setVisible(true);
            this.dispose();
        });

        okBtn.addActionListener(e -> {
            String currency = currencyCategory.getSelectedItem().toString();
            String pawn = pawnCategory.getSelectedItem().toString();
            double money = Double.valueOf(moneyTextField.getText());
            String ID = parentFrame.getNameLabel().getText();
            boolean success = CustomerController.getInstance().getLoan(ID, pawn, currency, money);
            if(success) {
                parentFrame.returnCustomerFrame(ID);
                this.dispose();
            }
            else {
                JOptionPane.showMessageDialog(null, "Loan Failed");
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
