package GUI;

import Controller.CustomerController;
import Model.Customer;
import Model.Loan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class PayLoanFrame extends JFrame{

    private JPanel btnPanel = new JPanel();

    private JButton okBtn = new JButton("OK");
    private JButton cancelBtn = new JButton("Cancel");

    private DefaultTableModel loanTableModel = new DefaultTableModel();
    private JTable loanTable = new JTable(loanTableModel);

    private JScrollPane tablePanel = new JScrollPane(loanTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


    private CustomerFrame parentFrame;

    public PayLoanFrame(CustomerFrame parentFrame) {
        this.parentFrame = parentFrame;
        init();
        setVisible(true);
        this.setSize(new Dimension(400, 400));
        this.setMinimumSize(new Dimension(400, 400));
        this.setMaximumSize(new Dimension(400, 400));
    }

    private void init() {
        Container container = this.getContentPane();

        container.setLayout(new BorderLayout());

        add(tablePanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.PAGE_END);

        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.LINE_AXIS));

        btnPanel.add(okBtn);
        btnPanel.add(cancelBtn);

        okBtn.setEnabled(false);

        cancelBtn.addActionListener(e -> {
            parentFrame.returnCustomerFrame(parentFrame.getNameLabel().getText());
            this.dispose();;
        });

        okBtn.addActionListener(e -> {
            List<String> accountNumberList = CustomerController.getInstance().getAccountNumber(parentFrame.getNameLabel().getText());
            String[] accountNumberStringList = accountNumberList.toArray(new String[accountNumberList.size()]);
            String[] currencyNameList = {"RMB", "Dollar", "GBP"};

            String accountNumber = (String) JOptionPane.showInputDialog(null, "Choose One Account Number to Pay The Loan", "Choose Account Number", JOptionPane.PLAIN_MESSAGE, null, accountNumberStringList, "");
            String currencyName = (String) JOptionPane.showInputDialog(null, "Choose One Currency Name to Pay The Loan", "Choose Currency Name", JOptionPane.PLAIN_MESSAGE, null, currencyNameList, "");
            int selectRow = loanTable.getSelectedRow();
            String loanNumber = loanTable.getValueAt(selectRow, 0).toString();
            double money = Double.valueOf(loanTable.getValueAt(selectRow, 2).toString());
            String loanCurrency = loanTable.getValueAt(selectRow, 1).toString();
            boolean success = CustomerController.getInstance().payLoan(parentFrame.getNameLabel().getText(), loanNumber, accountNumber, currencyName, loanCurrency, money);
            if(success) {
                this.dispose();
                parentFrame.returnCustomerFrame(parentFrame.getNameLabel().getText());
            }
            else {
                JOptionPane.showMessageDialog(null, "Fail to Pay The Loan");
                okBtn.setEnabled(false);
            }
        });

        loanTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                okBtn.setEnabled(true);
            }
        });

        updateTable();

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                parentFrame.setVisible(true);
                dispose();
            }
        });
    }

    /**
     * Get all loan information to update the table
     */
    private void updateTable() {
        Customer customer = CustomerController.getInstance().getCustomer(parentFrame.getNameLabel().getText());
        List<Loan> loanItems = customer.getLoanItems();

        loanTableModel.setRowCount(0);
        loanTableModel.setColumnCount(0);
        loanTableModel.setRowCount(loanItems.size());
        loanTableModel.addColumn("Loan Number");
        loanTableModel.addColumn("Currency Name");
        loanTableModel.addColumn("Loan Money");
        loanTableModel.addColumn("Loan Rate");
        loanTableModel.addColumn("Pawn Item");
        loanTableModel.setRowCount(loanItems.size());

        for(int i=0;i<loanItems.size();i++) {
            loanTableModel.setValueAt(loanItems.get(i).getLoanNumber(), i, 0);
            loanTableModel.setValueAt(loanItems.get(i).getCurrency().getCurrencyName(), i, 1);
            loanTableModel.setValueAt(loanItems.get(i).getCurrency().getDeposit(), i, 2);
            loanTableModel.setValueAt(loanItems.get(i).getLoanInterest(), i, 3);
            loanTableModel.setValueAt(loanItems.get(i).getPawnName(), i, 4);
        }
    }

}
