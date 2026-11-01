import model.User;
import service.BankService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JTextField mobileNumber;
    private JTextField pin;
    private JButton loginButton;
    private JPanel Login;
    private CardLayout cardLayout;

    Login(){
        BankService bankService = new BankService();
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
                if(mobileNumber.getText().isBlank() || pin.getText().isBlank()) {
                    JOptionPane.showMessageDialog(Login.this, "Please enter your Mobile number or PIN", "Empty", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                if(bankService.isLockedOut()){
                    JOptionPane.showMessageDialog(Login.this, "You run out of login attempts. Please contact support.", "Account Locked", JOptionPane.ERROR_MESSAGE);
                    mobileNumber.setText("");
                    pin.setText("");
                    return;
                }
                String inputMobile = mobileNumber.getText();
                int inputPin = Integer.parseInt(pin.getText());
                User user = bankService.userLogin(inputMobile, inputPin);
                if(bankService.isLoggedIn()){
                    new Dashboard(user, bankService);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Invalid Mobile Number or PIN");
                    mobileNumber.setText("");
                    pin.setText("");
                }
            }
        });
    }

    public static void main(String[] args) {
        new Login();
    }
}
