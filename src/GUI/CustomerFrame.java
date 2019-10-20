package GUI;

import Controller.CustomerController;
import Model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class CustomerFrame extends JFrame {

    private ChooseOptionFrame parentFrame;

    private JLabel nameLabel = new JLabel();

    private JButton createCardBtn = new JButton("Create Card");
    private JButton insertCardBtn = new JButton("Insert Card");
    private JButton withdrawAndSaveMoneyBtn = new JButton("Withdraw/Save Money");
    private JButton createAccountBtn = new JButton("Create Account");
    private JButton getLoanBtn = new JButton("Get Loan");
    private JButton printStatementBtn = new JButton("Print Statement");
    private JButton transferMoneyBtn = new JButton("Transfer Money");
    private JButton deleteAccountBtn = new JButton("Delete Account");
    private JButton payLoanBtn = new JButton("Pay The Loan");
    private JButton getCustomerInfo = new JButton("Get My Information");
    private JButton returnLastFrameBtn = new JButton("Return Last Interface");
    private JButton transactionBtn = new JButton("Transfer Money to Others");

    private DefaultTableModel userInfo = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private JTable table = new JTable(userInfo);

    private JScrollPane tablePanel = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    public CustomerFrame(ChooseOptionFrame parentFrame) {
        init();
        this.parentFrame = parentFrame;
        this.setVisible(true);
        this.setSize(new Dimension(732, 546));
        this.setMinimumSize(new Dimension(732, 546));
        this.setMaximumSize(new Dimension(732, 546));
    }

    private void init(){
        Container container = this.getContentPane();
        container.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(5,0,5,0);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        add(nameLabel, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        add(createCardBtn, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        add(insertCardBtn, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        add(withdrawAndSaveMoneyBtn, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        add(createAccountBtn, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        add(getLoanBtn, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        add(transferMoneyBtn, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        add(printStatementBtn, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        add(deleteAccountBtn, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        add(payLoanBtn, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        add(getCustomerInfo, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        add(transactionBtn, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        add(returnLastFrameBtn, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(10,0,10,0);
        add(tablePanel, gridBagConstraints);

        withdrawAndSaveMoneyBtn.setEnabled(false);
        printStatementBtn.setEnabled(false);
        createAccountBtn.setEnabled(false);
        deleteAccountBtn.setEnabled(false);
        getLoanBtn.setEnabled(false);
        transferMoneyBtn.setEnabled(false);
        payLoanBtn.setEnabled(false);
        getCustomerInfo.setEnabled(false);

        createCardBtn.addActionListener(e->{
            this.setVisible(false);
            CreateCardFrame createCardFrame = new CreateCardFrame(this);
        });

        insertCardBtn.addActionListener(e -> {
            CustomerLonginFrame customerLonginFrame = new CustomerLonginFrame(this);
            this.setVisible(false);
        });

        transferMoneyBtn.addActionListener(e -> {
            TransferMoneyFrame transferMoneyFrame = new TransferMoneyFrame(this);
            this.setVisible(false);
        });

        returnLastFrameBtn.addActionListener(e -> {
            parentFrame.setVisible(true);
            this.dispose();
        });

        withdrawAndSaveMoneyBtn.addActionListener(e -> {
            SaveWithdrawMoneyFrame saveWithdrawMoneyFrame = new SaveWithdrawMoneyFrame(this);
            this.setVisible(false);
        });

        createAccountBtn.addActionListener(e -> {
            CreateAccountFrame createAccountFrame = new CreateAccountFrame(this);
            this.setVisible(false);
        });

        getLoanBtn.addActionListener(e -> {
            GetLoanFrame getLoanFrame = new GetLoanFrame(this);
            this.setVisible(false);
        });

        payLoanBtn.addActionListener(e -> {
            PayLoanFrame payLoanFrame = new PayLoanFrame(this);
            this.setVisible(false);
        });

        getCustomerInfo.addActionListener(e -> {
            updateUserInfoTable(nameLabel.getText());
        });

        deleteAccountBtn.addActionListener(e -> {
            if(userInfo.getRowCount() <= 1)
                JOptionPane.showMessageDialog(null, "Only one accounts left. Cannot delete account.");
            else {
                int option = JOptionPane.showConfirmDialog(null, "Do you want to delete the account?", "Delete The Account", JOptionPane.OK_CANCEL_OPTION);
                if(option == JOptionPane.OK_OPTION) {
                    String ID = getNameLabel().getText();
                    int selectRow = table.getSelectedRow();
                    String accountNumber = userInfo.getValueAt(selectRow, 1).toString();
                    CustomerController.getInstance().deleteAccount(ID, accountNumber);
                    returnCustomerFrame(ID);
                }
            }
        });

        printStatementBtn.addActionListener(e -> {
            String ID = nameLabel.getText();
            updateStatementTable(ID);

        });

        transactionBtn.addActionListener(e -> {
            TransferMoneyToOtherFrame transferMoneyToOtherFrame = new TransferMoneyToOtherFrame(this);
            this.setVisible(false);
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                withdrawAndSaveMoneyBtn.setEnabled(true);
                createAccountBtn.setEnabled(false);
                deleteAccountBtn.setEnabled(true);
            }
        });

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                table.clearSelection();
                withdrawAndSaveMoneyBtn.setEnabled(false);
                createAccountBtn.setEnabled(true);
                deleteAccountBtn.setEnabled(false);
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
     * Update the customer information table
     * @param cardNumber The card number of a customer
     */
    private void updateUserInfoTable(String cardNumber) {
        Customer customer = CustomerController.getInstance().getCustomer(cardNumber);
        String[] columnNames = {"Account Type", "Account Number", "RMB", "Dollar", "GBP"};
        userInfo.addColumn("Account Type");
        userInfo.addColumn("Account Number");
        userInfo.addColumn("RMB");
        userInfo.addColumn("Dollar");
        userInfo.addColumn("GBP");
        userInfo.setColumnCount(columnNames.length);
        userInfo.setRowCount(customer.getAccountList().size());
        if(customer != null) {
            List<Account> accountList = customer.getAccountList();
            for(int i=0;i<accountList.size();i++) {
                if(accountList.get(i) instanceof CheckingAccount)
                    userInfo.setValueAt("Checking Account", i, 0);
                else
                    userInfo.setValueAt("Saving Account", i, 0);
                userInfo.setValueAt(accountList.get(i).getAccountNumber(), i, 1);
                List<Currency> currencyList = accountList.get(i).getCurrencies();
                for(int j=0;j<currencyList.size();j++) {
                    if(currencyList.get(j) instanceof RMBCurrency)
                        userInfo.setValueAt(currencyList.get(j).getDeposit(), i, 2);
                    if(currencyList.get(j) instanceof  DollarCurrency)
                        userInfo.setValueAt(currencyList.get(j).getDeposit(), i, 3);
                    else
                        userInfo.setValueAt(currencyList.get(j).getDeposit(), i, 4);
                }
            }
        }
    }

    /**
     * Update all the transaction of a customer
     * @param cardNumber the card number of customer
     */
    private void updateStatementTable(String cardNumber) {
        List<Statement> statementList = CustomerController.getInstance().getAllTransaction(cardNumber);
        String[] columnNames = {"Account Number", "Statement"};
        userInfo.setColumnCount(0);
        userInfo.setRowCount(0);
        userInfo.addColumn("Account Number");
        userInfo.addColumn("Statement");
        userInfo.setColumnCount(columnNames.length);
        userInfo.setRowCount(statementList.size());
        for(int i=0;i<statementList.size();i++) {
            userInfo.setValueAt(cardNumber, i, 0);
            userInfo.setValueAt(statementList.get(i).getTransaction(), i, 1);
        }
    }

    public void returnCustomerFrame(String ID) {
        nameLabel.setText(ID);

        userInfo.setColumnCount(0);

        createCardBtn.setEnabled(false);
        insertCardBtn.setEnabled(false);
        withdrawAndSaveMoneyBtn.setEnabled(false);
        printStatementBtn.setEnabled(true);
        createAccountBtn.setEnabled(true);
        deleteAccountBtn.setEnabled(false);
        getLoanBtn.setEnabled(true);
        transferMoneyBtn.setEnabled(true);
        payLoanBtn.setEnabled(true);
        getCustomerInfo.setEnabled(true);

        updateUserInfoTable(ID);
        this.setVisible(true);
    }

    public JFrame getCustomerFrame() {
        return this;
    }

    public JLabel getNameLabel() {
        return nameLabel;
    }

    public void setNameLabel(JLabel nameLabel) {
        this.nameLabel = nameLabel;
    }

    public JButton getCreateCardBtn() {
        return createCardBtn;
    }

    public JButton getInsertCardBtn() {
        return insertCardBtn;
    }

    public JButton getWithdrawAndSaveMoneyBtn() {
        return withdrawAndSaveMoneyBtn;
    }

    public JButton getCreateAccountBtn() {
        return createAccountBtn;
    }

    public JButton getGetLoanBtn() {
        return getLoanBtn;
    }

    public JButton getPrintStatementBtn() {
        return printStatementBtn;
    }

    public JButton getReturnLastFrameBtn() {
        return returnLastFrameBtn;
    }

    public DefaultTableModel getUserInfo() {
        return userInfo;
    }

    public JTable getTable() {
        return table;
    }
}
