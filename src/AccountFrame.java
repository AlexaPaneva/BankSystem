import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class AccountFrame extends JFrame {
    JLabel accountNumberLBL, ownerLBL, balanceLBL, cityLBL, genderLBL, amountLBL;
    JTextField accountNumberTXT, ownerTXT, balanceTXT, amountTXT;
    JComboBox<City> citiesCMB;
    JButton newBTN, saveBTN, showBTN, quitBTN, depositBTN, withdrawBTN;
    JRadioButton maleRDB, femaleRDB;
    ButtonGroup genderBTNGRP;
    JList<Account> accountsLST;
    JPanel p1, p2, p3, p4, p5;

    Set<Account> accountSet = new TreeSet<>();
    Account account, x;
    boolean newRecord = true;

    DefaultComboBoxModel<City> citiesCMBMDL;
    DefaultListModel<Account> accountsLSTMDL;

    JTable table;
    DefaultTableModel tableModel;
    ArrayList<Transaction> transactionsList = new ArrayList<>();

    public AccountFrame() {
        super("Account Operations");
        setLayout(null);
        setSize(600, 400);

        accountNumberLBL = new JLabel("Account Number");
        ownerLBL = new JLabel("Owner");
        balanceLBL = new JLabel("Balance");
        cityLBL = new JLabel("City");
        genderLBL = new JLabel("Gender");
        amountLBL = new JLabel("Amount");

        accountNumberTXT = new JTextField();
        accountNumberTXT.setEnabled(false);
        ownerTXT = new JTextField();
        balanceTXT = new JTextField();
        balanceTXT.setEnabled(false);
        amountTXT = new JTextField();
        amountTXT.setPreferredSize(new Dimension(150, 25));

        citiesCMBMDL = new DefaultComboBoxModel<>();
        citiesCMBMDL.addElement(null);
        citiesCMBMDL.addElement(new City("Brooklyn", "New York"));
        citiesCMBMDL.addElement(new City("New Orleans", "Louisiana"));
        citiesCMBMDL.addElement(new City("Dothan", "Alabama"));
        citiesCMBMDL.addElement(new City("Miami", "Florida"));

        citiesCMB = new JComboBox<City>(citiesCMBMDL);

        maleRDB = new JRadioButton("Male", true);
        femaleRDB = new JRadioButton("Female");
        genderBTNGRP = new ButtonGroup();
        genderBTNGRP.add(maleRDB);
        genderBTNGRP.add(femaleRDB);

        newBTN = new JButton("New");
        saveBTN = new JButton("Save");
        showBTN = new JButton("Show");
        quitBTN = new JButton("Quit");
        depositBTN = new JButton("Deposit");
        withdrawBTN = new JButton("Withdraw");

        accountsLSTMDL = new DefaultListModel<>();
        accountsLST = new JList<>(accountsLSTMDL);

        p1 = new JPanel();
        p1.setBounds(5, 5, 300, 150);
        p1.setLayout(new GridLayout(5, 2));

        p2 = new JPanel();
        p2.setBounds(5, 155, 300, 40);
        p2.setLayout(new FlowLayout());

        p3 = new JPanel();
        p3.setBounds(5, 195, 600, 40);
        p3.setLayout(new FlowLayout());

        p4 = new JPanel();
        p4.setBounds(305, 5, 300, 190);
        p4.setLayout(new BorderLayout());

        p5 = new JPanel();
        p5.setBounds(5, 240, 580, 120);
        p5.setLayout(new BorderLayout());

        p1.add(accountNumberLBL);
        p1.add(accountNumberTXT);
        p1.add(ownerLBL);
        p1.add(ownerTXT);
        p1.add(balanceLBL);
        p1.add(balanceTXT);
        p1.add(cityLBL);
        p1.add(citiesCMB);
        p1.add(maleRDB);
        p1.add(femaleRDB);

        p2.add(newBTN);
        p2.add(saveBTN);
        p2.add(showBTN);
        p2.add(quitBTN);


        p3.add(amountLBL);
        p3.add(amountTXT);
        p3.add(depositBTN);
        p3.add(withdrawBTN);

        p4.add(accountsLST);


        add(p1);
        add(p2);
        add(p3);
        add(p4);
        add(p5);


        tableModel = new DefaultTableModel();

        table = new JTable(tableModel);
        tableModel.addColumn("Transaction Number");
        tableModel.addColumn("Transaction Date");
        tableModel.addColumn("Transaction Type");
        tableModel.addColumn("Transaction Amount");

        JScrollPane scrollPane = new JScrollPane(table);
        p5.add(scrollPane);

        newBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accountNumberTXT.setText("");
                ownerTXT.setText("");
                citiesCMB.setSelectedIndex(0);
                maleRDB.setSelected(true);
                balanceTXT.setText("");
                amountTXT.setText("");
                newRecord = true;
            }
        });

        saveBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (newRecord) {
                    if (ownerTXT.getText().length() != 0) {
                        account = new Account(
                                ownerTXT.getText(),
                                (City) citiesCMB.getSelectedItem(),
                                maleRDB.isSelected() ? 'M' : 'F');

                        accountNumberTXT.setText(String.valueOf(account.accountNumber));
                        accountSet.add(account);
                        accountsLSTMDL.addElement(account);
                        newRecord = false;
                    } else {
                        JOptionPane.showMessageDialog(null, "Please fill all fields");
                    }
                } else {
                    accountSet.remove(account);

                    int acc = Integer.parseInt(accountNumberTXT.getText());
                    String owner = ownerTXT.getText();
                    City city = (City) citiesCMB.getSelectedItem();

                    char gender = maleRDB.isSelected() ? 'M' : 'F';
                    double balance = Double.parseDouble(balanceTXT.getText());

                    account = new Account(acc, owner, city, gender, balance);
                    accountSet.add(account);
                    accountsLSTMDL.setElementAt(account, accountsLST.getSelectedIndex());
                    newRecord = false;
                }
            }
        });
        showBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = " ";
                Iterator<Account> accountIterator = accountSet.iterator();
                while (accountIterator.hasNext()) {
                    s += accountIterator.next().toString() + "\n";
                }
                JOptionPane.showMessageDialog(null, s);
            }
        });


        depositBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (newRecord && amountTXT.getText().length() != 0) {
                    Transaction transaction = new Transaction(account, LocalDate.now(),
                            'D', Double.parseDouble(amountTXT.getText()));
                    DisplayTransactionsInTable(transaction);

                    account.deposit(Double.parseDouble(amountTXT.getText()));
                    balanceTXT.setText(String.valueOf(account.balance));
                }
            }
        });

        withdrawBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!newRecord && amountTXT.getText().length() != 0) {
                    Transaction transaction = new Transaction(
                            account, LocalDate.now(),
                            'W',
                            Double.parseDouble(amountTXT.getText()));
                    DisplayTransactionsInTable(transaction);

                    account.withdraw(Double.parseDouble(amountTXT.getText()));
                    balanceTXT.setText(String.valueOf(account.balance));
                }
            }
        });

        accountsLST.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                account = x = accountsLST.getSelectedValue();

                accountNumberTXT.setText(String.valueOf(account.accountNumber));
                ownerTXT.setText(account.owner);
                citiesCMB.setSelectedItem(account.city);

                if (account.gender == 'M') maleRDB.setSelected(true);
                else femaleRDB.setSelected(true);

                balanceTXT.setText(String.valueOf(account.balance));
                amountTXT.setEnabled(true);
                depositBTN.setEnabled(true);
                newRecord = false;
            }
        });
    }

    private void SearchTransactionList(int accountNumber) {
        List<Transaction> filteredList = new ArrayList<>();
        for (Transaction transaction : transactionsList) {
            if (transaction.getAccount().accountNumber == accountNumber) {
                filteredList.add(transaction);
            }
        }
        for (int i = 0; i < filteredList.size(); i++) {
            tableModel.addRow(new Object[]{
                    filteredList.get(i).getTransactionNumber(),
                    filteredList.get(i).getDate(),
                    filteredList.get(i).getOperation(),
                    filteredList.get(i).getAmount()
            });
        }
    }

    private void DisplayTransactionsInTable(Transaction transaction) {
        tableModel.addRow(new Object[]{
                transaction.getTransactionNumber(),
                transaction.getDate(),
                transaction.getOperation(),
                transaction.getAmount()
        });
        transactionsList.add(transaction);
    }

    public static void main(String[] args) {
        AccountFrame accountFrame = new AccountFrame();
        accountFrame.setVisible(true);
        accountFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
