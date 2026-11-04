import service.BankService;

import javax.swing.*;
import java.awt.event.*;
import java.util.InputMismatchException;

public class Transfer extends JDialog {
    private JPanel Transfer;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField mobileField;
    private JTextField amountField;

    public Transfer(Dashboard parent, BankService bankService) {
        super(parent, "Transfer Money", true);
        setContentPane(Transfer);
        this.pack();
        setLocationRelativeTo(parent);
        getRootPane().setDefaultButton(buttonOK);


        buttonOK.addActionListener(_ -> onOK(parent, bankService));

        buttonCancel.addActionListener(_ -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        Transfer.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


    }

    private void onOK(Dashboard parent, BankService bankService) {
        // add your code here
        try{
            String receiverMobile = mobileField.getText().trim();
            String rawAmount = amountField.getText();
            if(receiverMobile.isEmpty() || rawAmount.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please enter both the receiver's mobile number and the amount.",
                        "Empty fields",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            double amount = Double.parseDouble(rawAmount);
            if(amount <= 0){
                JOptionPane.showMessageDialog(this,
                        "Please enter amount greater than 0",
                        "Invalid amount",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            double updatedBalance = bankService.transfer(receiverMobile, amount);
            parent.refreshDashboardData(updatedBalance);
            JOptionPane.showMessageDialog(this,
                    "₱" + String.format("%.2f ", amount) +
                            "Successfully transferred to " + receiverMobile);
            dispose();
        } catch(InputMismatchException | NumberFormatException e){
            JOptionPane.showMessageDialog(this,
                    "Please enter valid mobile number/amount.",
                    "Invalid input",
                    JOptionPane.ERROR_MESSAGE);
        }catch(IllegalArgumentException | IllegalStateException e){
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Transaction Denied",
                    JOptionPane.WARNING_MESSAGE);
        }

    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
