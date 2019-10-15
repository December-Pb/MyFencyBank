package ATMGui;

import ATMController.CustomerController;
import User.BankSystemUser;
import User.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.TableView;
import java.awt.*;
import java.lang.reflect.Field;

import java.util.List;
import java.util.Map;

public class CustomerFrame extends JFrame {

    private JFrame customerFrame = new JFrame();

    private JFrame parentFrame;

    private JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    private JPanel btnPanel = new JPanel();
    private JPanel tablePanel = new JPanel();

    private JLabel nameLabel = new JLabel("Show Your Name Here");

    private JButton createCardBtn = new JButton("Create Card");
    private JButton insertCardBtn = new JButton("Insert Card");
    private JButton withdrawMoneyBtn = new JButton("Withdraw Money");
    private JButton saveMoneyBtn = new JButton("Save Money");
    private JButton createAccountBtn = new JButton("Create Account");
    private JButton getLoanBtn = new JButton("Get Loan");
    private JButton printStatementBtn = new JButton("Print Statement");
    private JButton returnLastFrame = new JButton("Return Last Interface");

    private DefaultTableModel userInfo = new DefaultTableModel();

    private CustomerController customerController;

    private JTable table = new JTable(userInfo);

    public CustomerFrame(JFrame parentFrame) {
        init();
        this.parentFrame = parentFrame;
        customerFrame.setVisible(true);
        customerFrame.setSize(new Dimension(732, 546));
        customerFrame.setMinimumSize(new Dimension(732, 546));
        customerFrame.setMaximumSize(new Dimension(732, 546));
        customerController = new CustomerController();
    }

    private void init(){
        customerFrame.add(splitPane);
        splitPane.setLeftComponent(btnPanel);
        splitPane.setRightComponent(tablePanel);

        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.Y_AXIS));
        btnPanel.add(nameLabel);
        btnPanel.add(Box.createVerticalStrut(3));
        btnPanel.add(createCardBtn);
        btnPanel.add(Box.createVerticalStrut(3));
        btnPanel.add(insertCardBtn);
        btnPanel.add(Box.createVerticalStrut(3));
        btnPanel.add(withdrawMoneyBtn);
        btnPanel.add(Box.createVerticalStrut(3));
        btnPanel.add(saveMoneyBtn);
        btnPanel.add(Box.createVerticalStrut(3));
        btnPanel.add(createAccountBtn);
        btnPanel.add(Box.createVerticalStrut(3));
        btnPanel.add(getLoanBtn);
        btnPanel.add(Box.createVerticalStrut(3));
        btnPanel.add(printStatementBtn);
        btnPanel.add(Box.createVerticalStrut(3));
        btnPanel.add(returnLastFrame);
        btnPanel.add(Box.createVerticalStrut(3));

        tablePanel.add(table);

        createCardBtn.addActionListener(e->{

        });

        insertCardBtn.addActionListener(e -> {
            List<Customer> resultList = customerController.getUser();
            String[] names = {"First Name", "Last Name", "Card Number", "Password"};
            userInfo.setColumnCount(names.length);
            userInfo.setRowCount(resultList.size());
            for(int i=0;i<resultList.size();i++) {
                userInfo.setValueAt(resultList.get(i).getFirstName(), i, 0);
                userInfo.setValueAt(resultList.get(i).getLastName(), i, 1);
                userInfo.setValueAt(resultList.get(i).getCardNumber(), i, 2);
                userInfo.setValueAt(resultList.get(i).getPassword(), i, 3);
            }
        });

        returnLastFrame.addActionListener(e -> {
            customerFrame.dispose();
            parentFrame.setVisible(true);
        });
    }
}
