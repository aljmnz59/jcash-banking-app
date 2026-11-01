import model.User;
import service.BankService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends JFrame {
    private JPanel Dashboard;
    private JLabel welcomeText;
    private JLabel balanceText;
    private JButton cashInMoneyButton;
    private JButton transferMoneyButton;
    private JButton checkTransactionsButton;
    private JButton logoutButton;
    private User loggedInUser;
    private BankService bankService;

    Dashboard(User loggedInUser, BankService bankService){
        this.loggedInUser = loggedInUser;
        this.bankService = bankService;

        setTitle("Bank Dashboard");
        setContentPane(Dashboard);
        setVisible(true);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        welcomeText.setText(welcomeText.getText() + " " + loggedInUser.getFullName());
        balanceText.setText(loggedInUser.currentBalance());

        cashInMoneyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CashIn cashinDialog = new CashIn(Dashboard.this, loggedInUser, bankService);
                cashinDialog.setVisible(true);
            }
        });


        transferMoneyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void refreshDashboardData(double newBalance){
        loggedInUser.setBalance(newBalance);
        balanceText.setText(loggedInUser.currentBalance());
    }

}
