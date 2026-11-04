import model.Transaction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TransactionHistory extends JDialog {
    private JPanel Transaction;
    private JButton buttonOK;
    private JTable transactionTable;

    public TransactionHistory(Dashboard parent, List<Transaction> transactions) {
        super(parent, "Transaction History", true);
        setContentPane(Transaction);
        setSize(700, 200);
        setLocationRelativeTo(parent);
        getRootPane().setDefaultButton(buttonOK);

        String[] headers = {"Date", "Type", "Amount", "Details"};

        DefaultTableModel model = new DefaultTableModel(null, headers){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        transactionTable.setModel(model);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a");

        for(Transaction tx : transactions){
            LocalDateTime rawDateTime = tx.getDateTime();
            String readableDate = (rawDateTime != null) ? rawDateTime.format(formatter) : "N/A";
            model.addRow(new Object[]{
                    readableDate,
                    tx.getType(),
                    tx.getAmount(),
                    tx.getDetails()
            });
        }
        buttonOK.addActionListener(e -> dispose());

    }
}
