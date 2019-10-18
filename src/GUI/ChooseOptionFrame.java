package GUI;

import javax.swing.*;
import java.awt.*;


public class ChooseOptionFrame extends JFrame{

    JLabel label = new JLabel("Please Choose Your Option", SwingConstants.CENTER);
    JButton managerBtn = new JButton("Manager");
    JButton customerBtn = new JButton("Customer");
    Panel btnPanel = new Panel();

    public ChooseOptionFrame() {
        init();
        this.setVisible(true);
        this.setMaximumSize(new Dimension(300, 100));
        this.setMinimumSize(new Dimension(300, 100));
        this.setSize(new Dimension(300, 100));
    }

    private void init() {
        this.setLayout(new BorderLayout());
        this.add("Center", label);
        this.add("South", btnPanel);
        btnPanel.setLayout(new FlowLayout());
        btnPanel.add(managerBtn);
        btnPanel.add(Box.createHorizontalStrut(5));
        btnPanel.add(new JSeparator());
        btnPanel.add(Box.createHorizontalStrut(5));
        btnPanel.add(customerBtn);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        managerBtn.addActionListener(e -> {
            BankManagerLogInFrame bankManagerLogInFrame = new BankManagerLogInFrame(this);
            this.setVisible(false);
        });

        customerBtn.addActionListener(e -> {
            CustomerFrame customerFrame = new CustomerFrame(this);
            this.setVisible(false);
        });
    }
}
