import service.BankService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.InputMismatchException;

public class CashIn extends JDialog {
    private JPanel CashIn;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField cashInAmount;

    public CashIn(Dashboard parent, BankService bankService) {
        super(parent, "Cash-In", true);
        setContentPane(CashIn);
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
        CashIn.registerKeyboardAction(_ -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK(Dashboard parent, BankService bankService) {
        // add your code here
        try{
            double amount = Double.parseDouble(cashInAmount.getText());
            if(amount <= 0){
                JOptionPane.showMessageDialog(this,
                        "Please input amount greater than 0",
                        "Invalid amount",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            double updatedBalance = bankService.cashIn(amount);
            parent.refreshDashboardData(updatedBalance);
            JOptionPane.showMessageDialog(this,
                    "₱" + String.format("%.2f ", amount) +
                            "Successfully added to your account. \n New Balance: ₱" +
                            String.format("%.2f", updatedBalance),
                    "Successful Cash-In",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }catch(InputMismatchException | NumberFormatException e){
            JOptionPane.showMessageDialog(this,
                    "Please enter valid amount.",
                    "Invalid input",
                    JOptionPane.ERROR_MESSAGE);
        } catch(IllegalArgumentException | IllegalStateException e){
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
