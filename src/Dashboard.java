import model.Transaction;
import model.User;
import service.BankService;

import javax.swing.*;
import java.util.List;

public class Dashboard extends JFrame {
    private JPanel Dashboard;
    private JLabel welcomeText;
    private JLabel balanceText;
    private JButton cashInMoneyButton;
    private JButton transferMoneyButton;
    private JButton checkTransactionsButton;
    private JButton logoutButton;

    Dashboard(User loggedInUser, BankService bankService){
        setTitle("Bank Dashboard");
        setContentPane(Dashboard);
        setVisible(true);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        try {
            if(bankService.isLoggedIn()){
                welcomeText.setText("Welcome " + loggedInUser.getFullName());
                refreshDashboardData(loggedInUser.getBalance());
            }
        }catch(IllegalStateException e){
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Unauthorized Access",
                    JOptionPane.WARNING_MESSAGE);
        }


        cashInMoneyButton.addActionListener(_ -> {
            CashIn cashInDialog = new CashIn(Dashboard.this, bankService);
            cashInDialog.setVisible(true);
        });

        transferMoneyButton.addActionListener(_ -> {
            Transfer transferDialog = new Transfer(Dashboard.this, bankService);
            transferDialog.setVisible(true);
        });

        checkTransactionsButton.addActionListener(_ -> {
            try {
                List<Transaction> transactions = bankService.showTransaction();
                TransactionHistory transactionDialog = new TransactionHistory(Dashboard.this, transactions);
                transactionDialog.setVisible(true);
            }catch(IllegalArgumentException ex1){
                JOptionPane.showMessageDialog(
                        Dashboard.this,
                        ex1.getMessage(),
                        "No Data",
                        JOptionPane.INFORMATION_MESSAGE);
            }catch(IllegalStateException ex2){
                JOptionPane.showMessageDialog(
                        Dashboard.this,
                        ex2.getMessage(),
                        "Session Expired. Please relogin",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        logoutButton.addActionListener(_ -> {
            int choice = JOptionPane.showConfirmDialog(Dashboard.this,
                    "Are you sure you want to logout?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION);
            if(choice == JOptionPane.YES_OPTION){
                bankService.logout();
                dispose();
                new Login(bankService).setVisible(true);
            }
        });
    }

    public void refreshDashboardData(double newBalance){
        balanceText.setText("Current balance: " + "₱" + String.format("%.2f", newBalance));
    }

}
