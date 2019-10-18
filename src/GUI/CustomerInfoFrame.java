package GUI;

import Model.*;
import Service.ATMCustomerService;
import Service.ATMManagerService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.awt.event.*;
import java.util.List;

public class CustomerInfoFrame extends JFrame{

    private ManagerFrame parentFrame;
    private String ID;

    private JLabel nameAndNumberLabel = new JLabel();

    private DefaultTableModel loanTableModel = new DefaultTableModel();
    private JTable loanTable = new JTable(loanTableModel);

    private DefaultTableModel customerTableModule = new DefaultTableModel();
    private JTable customerTable = new JTable(customerTableModule);

    private JButton addInterestBtn = new JButton("Add Interest");
    private JButton adjustRateBtn = new JButton("Adjust Rate");
    private JButton cancelBtn = new JButton("Cancel");

    private JScrollPane loanTableScrollPane = new JScrollPane(loanTable);
    private JScrollPane infoTableScrollPane = new JScrollPane(customerTable);

    public CustomerInfoFrame(ManagerFrame parentFrame, String ID) {
        this.ID = ID;
        this.parentFrame = parentFrame;
        this.setVisible(true);
        this.setSize(new Dimension(732, 546));
        this.setMinimumSize(new Dimension(732, 546));
        this.setMaximumSize(new Dimension(732, 546));
        init();
        addInterestBtn.setEnabled(false);
        adjustRateBtn.setEnabled(false);
    }

    private void init() {
        Container container = this.getContentPane();
        container.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.insets = new Insets(5,5,5,5);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(nameAndNumberLabel, gridBagConstraints);

        gridBagConstraints.fill = GridBagConstraints.NONE;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridx = 0;
        add(addInterestBtn, gridBagConstraints);

        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridx = 1;
        add(adjustRateBtn, gridBagConstraints);

        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridx = 2;
        add(cancelBtn, gridBagConstraints);

        gridBagConstraints.fill = GridBagConstraints.BOTH;

        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.weightx = 0.5;
        add(loanTableScrollPane, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = GridBagConstraints.RELATIVE;
        add(infoTableScrollPane, gridBagConstraints);

        updateAccountTable();
        updateLoanTable();

        addInterestBtn.addActionListener(e -> {
            Customer customer = ATMCustomerService.getInstance().getCustomer(ID);
            int loanTableSelectedRow = loanTable.getSelectedRow();
            int customerTableSelectedRow = customerTable.getSelectedRow();
            if(loanTableSelectedRow != -1) {
                double loanMoney = Double.valueOf(loanTableModel.getValueAt(loanTableSelectedRow, 2).toString());
                double loanRate = Double.valueOf(loanTableModel.getValueAt(loanTableSelectedRow, 3).toString());
                customer.getLoanItems().get(loanTableSelectedRow).getCurrency().setDeposit(loanMoney + loanMoney * loanRate);
                updateLoanTable();
            }
            else {
                String accountCategory = customerTableModule.getValueAt(customerTableSelectedRow, 1).toString();
                if("Checking Account".equals(accountCategory))
                    JOptionPane.showMessageDialog(null, "Checking Account Does Not Have Interest");
                else {
                    String accountNumber = customerTableModule.getValueAt(customerTableSelectedRow, 0).toString();
                    List<Account> accountList = customer.getAccountList();
                    for(Account account : accountList) {
                        if(account.getAccountNumber().equals(accountNumber)) {
                            List<Currency> currencyList = account.getCurrencies();
                            for(Currency currency : currencyList) {
                                if (currency.getDeposit() >= ATMManagerService.LEASTMONEYWITHINTEREST)
                                    currency.setDeposit(currency.getDeposit() + currency.getDeposit() * currency.getDepositInterest());
                                else
                                    JOptionPane.showMessageDialog(null, currency.getCurrencyName() + " has too less money, no interest will be added");
                            }
                        }
                    }
                }
                updateAccountTable();
            }

        });

        adjustRateBtn.addActionListener(e -> {
            Customer customer = ATMCustomerService.getInstance().getCustomer(ID);
            int loanTableSelectedRow = loanTable.getSelectedRow();
            String value = JOptionPane.showInputDialog(null, "Please Enter the Value");
            double rate = 1;
            try {
                rate = Double.valueOf(value);
            } catch(Exception exception) {
                JOptionPane.showMessageDialog(null, value + " is not a number");
            }
            if(loanTableSelectedRow != -1) {
                customer.getLoanItems().get(loanTableSelectedRow).setLoanInterest(rate);
                updateLoanTable();
            }
        });

        cancelBtn.addActionListener(e -> {
            this.dispose();
            parentFrame.setVisible(true);
        });

        loanTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addInterestBtn.setEnabled(true);
                adjustRateBtn.setEnabled(true);
            }
        });

        customerTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addInterestBtn.setEnabled(true);
            }
        });

        loanTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        customerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addInterestBtn.setEnabled(false);
                adjustRateBtn.setEnabled(false);
                customerTable.clearSelection();
                loanTable.clearSelection();
            }
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

    /**
     * Get all the data of a customer to generate customerTable
     */
    private void updateAccountTable() {
        Customer customer = ATMCustomerService.getInstance().getCustomer(ID);
        nameAndNumberLabel.setText(customer.getFirstName() + " " + customer.getLastName() + ": " + customer.getID());
        List<Account> accountList = customer.getAccountList();
        customerTableModule.setRowCount(0);
        customerTableModule.setColumnCount(0);

        String[] columns = {"Account Number", "Account Category", "RMB", "Dollar", "GBP"};

        customerTableModule.addColumn("Account Number");
        customerTableModule.addColumn("Account Category");
        customerTableModule.addColumn("RMB");
        customerTableModule.addColumn("Dollar");
        customerTableModule.addColumn("GBP");

        customerTableModule.setRowCount(accountList.size());

        for(int i=0;i<accountList.size();i++) {
            customerTableModule.setValueAt(accountList.get(i).getAccountNumber(), i, 0);
            if(accountList.get(i) instanceof SavingAccount)
                customerTableModule.setValueAt("Saving Account", i, 1);
            else
                customerTableModule.setValueAt("Checking Account", i, 1);
            List<Currency> currencyList = accountList.get(i).getCurrencies();
            for(Currency currency : currencyList) {
                if(currency instanceof RMBCurrency)
                    customerTableModule.setValueAt(currency.getDeposit(), i, 2);
                else if(currency instanceof  DollarCurrency)
                    customerTableModule.setValueAt(currency.getDeposit(), i, 3);
                else
                    customerTableModule.setValueAt(currency.getDeposit(), i, 4);
            }
        }
    }

    /**
     * Get the loan information for customer to generate loanTable
     */
    private void updateLoanTable() {
        Customer customer = ATMCustomerService.getInstance().getCustomer(ID);
        List<Loan> loanList = customer.getLoanItems();
        loanTableModel.setRowCount(0);
        loanTableModel.setColumnCount(0);
        loanTableModel.setRowCount(loanList.size());
        String[] columnNames = {"Loan Number", "Currency", "Loan Amount", "Loan Rate", "Pawn Item"};
        loanTableModel.addColumn("Loan Number");
        loanTableModel.addColumn("Currency");
        loanTableModel.addColumn("Loan Amount");
        loanTableModel.addColumn("Loan Rate");
        loanTableModel.addColumn("Pawn Item");

        for(int i=0;i<loanList.size();i++) {
            loanTableModel.setValueAt(loanList.get(i).getLoanNumber(), i, 0);
            loanTableModel.setValueAt(loanList.get(i).getCurrency().getCurrencyName(), i, 1);
            loanTableModel.setValueAt(loanList.get(i).getCurrency().getDeposit(), i, 2);
            loanTableModel.setValueAt(loanList.get(i).getLoanInterest(), i, 3);
            loanTableModel.setValueAt(loanList.get(i).getPawnName().toString(), i, 4);
        }
    }
}
