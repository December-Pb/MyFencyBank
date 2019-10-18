package GUI;

import Controller.BankManagerController;
import Model.Customer;
import Model.CustomerDAOImpl;
import Model.Statement;
import Service.ATMManagerService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class ManagerFrame extends JFrame{

    private JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

    private JButton viewWalletBtn = new JButton("View My Wallet");
    private JButton getAllCustomerBtn = new JButton("Get All Customer");
    private JButton getCustomerBtn = new JButton("Get Current Customer");
    private JButton getFullyTransactionBtn = new JButton("Get Fully Transaction");
    private JButton getCurrentTransactionBtn = new JButton("Get Current Transaction");
    private JButton returnBtn = new JButton("Return to Choose");

    private String bankManagerID = "1";

    private DefaultTableModel userInfo = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private JTable table = new JTable(userInfo);
    private JScrollPane tablePanel = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    ChooseOptionFrame parentFrame;

    public ManagerFrame(ChooseOptionFrame parentFrame) {
        init();
        this.parentFrame = parentFrame;
        this.setVisible(true);
        this.setSize(new Dimension(732, 546));
        this.setMinimumSize(new Dimension(732, 546));
        this.setMaximumSize(new Dimension(732, 546));
    }

    private void init() {
        Container container = this.getContentPane();
        container.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(10,0,10,0);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        add(viewWalletBtn, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new Insets(10,0,10,0);
        add(getAllCustomerBtn, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new Insets(10,0,10,0);
        add(getCustomerBtn, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new Insets(10,0,10,0);
        add(getFullyTransactionBtn, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new Insets(10,0,10,0);
        add(getCurrentTransactionBtn, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new Insets(10,0,10,0);
        add(returnBtn, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(10,0,10,0);
        add(tablePanel, gridBagConstraints);


        getCustomerBtn.setEnabled(false);

        viewWalletBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Balance of Your Wallet: " + ATMManagerService.getWallet());
        });

        getAllCustomerBtn.addActionListener(e -> {
            List<Customer> customerList = new ArrayList<>(CustomerDAOImpl.getInstance().getAllCustomer().values());
            updateCustomerTable(customerList);
        });

        returnBtn.addActionListener(e -> {
            parentFrame.setVisible(true);
            BankManagerController.getInstance().getCurrentStatement(bankManagerID).clear();
            this.dispose();
        });

        getCustomerBtn.addActionListener(e -> {
            int rowIndex = table.getSelectedRow();
            String cardNumber = userInfo.getValueAt(rowIndex, 2).toString();
            CustomerInfoFrame customerInfoFrame = new CustomerInfoFrame(this, cardNumber);
            this.setVisible(false);
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                getCustomerBtn.setEnabled(true);
            }
        });

        getCurrentTransactionBtn.addActionListener(e -> {
            updateTransactionTable(BankManagerController.getInstance().getCurrentStatement(bankManagerID));
        });

        getFullyTransactionBtn.addActionListener(e -> {
            updateTransactionTable(BankManagerController.getInstance().getFullyStatement(bankManagerID));
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                table.clearSelection();
                getCustomerBtn.setEnabled(false);
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

        BankManagerController.getInstance().initStatementList(bankManagerID);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    public String getBankManagerID() {
        return bankManagerID;
    }

    public void setBankManagerID(String bankManagerID) {
        this.bankManagerID = bankManagerID;
    }

    public void returnManagerFrame(String ID) {

    }

    /**
     * Get all customer of the bank and update the customer table
     * @param customerList the list of all customer
     */
    private void updateCustomerTable(List<Customer> customerList) {
        String[] columnNames = {"First Name", "Last Name", "Card Number"};
        userInfo.addColumn("First Name");
        userInfo.addColumn("Last Name");
        userInfo.addColumn("Card Number");
        userInfo.setRowCount(0);
        userInfo.setColumnCount(0);
        userInfo.setColumnCount(columnNames.length);
        userInfo.setRowCount(customerList.size());
        for(int i=0;i<customerList.size();i++) {
            userInfo.setValueAt(customerList.get(i).getFirstName(), i, 0);
            userInfo.setValueAt(customerList.get(i).getLastName(), i, 1);
            userInfo.setValueAt(customerList.get(i).getCardNumber(), i, 2);
        }
    }

    /**
     * Get all statement of the bank
     * @param statemList The list of statement
     */
    private void updateTransactionTable(List<Statement> statemList) {
        userInfo.setColumnCount(0);
        userInfo.setRowCount(0);
        userInfo.addColumn("Statement");
        userInfo.setRowCount(statemList.size());
        for(int i=0;i<statemList.size();i++) {
            userInfo.setValueAt(statemList.get(i).getTransaction(), i, 0);
        }
    }
}
