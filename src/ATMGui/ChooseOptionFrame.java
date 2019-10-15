package ATMGui;

import javax.swing.*;
import java.awt.*;


public class ChooseOptionFrame extends JFrame{

    JLabel label = new JLabel("Please Choose Your Option", SwingConstants.CENTER);
    JButton managerBtn = new JButton("Manager");
    JButton customerBtn = new JButton("Customer");
    JFrame chooseOptionFrame = new JFrame();
    Panel btnPanel = new Panel();

    public ChooseOptionFrame() {
        init();
        chooseOptionFrame.setVisible(true);
        chooseOptionFrame.setMaximumSize(new Dimension(300, 100));
        chooseOptionFrame.setMinimumSize(new Dimension(300, 100));
        chooseOptionFrame.setSize(new Dimension(300, 100));
    }

    private void init() {
        chooseOptionFrame.setLayout(new BorderLayout());
        chooseOptionFrame.add("Center", label);
        chooseOptionFrame.add("South", btnPanel);
        btnPanel.setLayout(new FlowLayout());
        btnPanel.add(managerBtn);
        btnPanel.add(Box.createHorizontalStrut(5));
        btnPanel.add(new JSeparator());
        btnPanel.add(Box.createHorizontalStrut(5));
        btnPanel.add(customerBtn);

        managerBtn.addActionListener(e -> {

        });

        customerBtn.addActionListener(e -> {
            CustomerFrame customerFrame = new CustomerFrame(chooseOptionFrame);
            chooseOptionFrame.setVisible(false);
        });
    }
}
