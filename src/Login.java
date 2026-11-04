import model.User;
import service.BankService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;

public class Login extends JFrame {
    private JTextField mobileNumber;
    private JTextField pin;
    private JButton loginButton;
    private JPanel Login;
    private BankService bankService;

    public Login(){
        this(new BankService());
    }
    public Login(BankService existingBankServices){
        this.bankService = existingBankServices;
        setTitle("JCash Banking App");
        setContentPane(Login);
        setVisible(true);
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getRootPane().setDefaultButton(loginButton);

            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    login(bankService);
                }
            });

    }

    private void login(BankService bankService){
        try {
            String inputMobile = mobileNumber.getText().trim();
            int inputPin = Integer.parseInt(pin.getText().trim());
            if(inputMobile.isBlank()) {
                JOptionPane.showMessageDialog(Login.this,
                        "Please enter your mobile number",
                        "Empty",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if(bankService.isLockedOut()) {
                JOptionPane.showMessageDialog(Login.this,
                        "You run out of login attempts. Please contact support.",
                        "Account Locked", JOptionPane.ERROR_MESSAGE);
                return;
            }
            User user = bankService.userLogin(inputMobile, inputPin);
            if(bankService.isLoggedIn()) {
                dispose();
                new Dashboard(user, bankService);

            }
        }catch(InputMismatchException | NumberFormatException e){
            JOptionPane.showMessageDialog(this,
                    "Please enter valid mobile number or PIN.",
                    "Invalid input",
                    JOptionPane.ERROR_MESSAGE);
        }catch(IllegalStateException | IllegalArgumentException loginE){
            JOptionPane.showMessageDialog(this,
                    loginE.getMessage(),
                    "Security Alert",
                    JOptionPane.WARNING_MESSAGE);
        }

    }

    static void main(String[] args) {
        new Login();
    }
}
