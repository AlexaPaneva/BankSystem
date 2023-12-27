import java.util.Date;

public class Account implements Comparable {

    static int nextAccountNumber = 10;
    int accountNumber;
    String owner;
    City city;
    char gender;
    double balance;
    private Date openDate;


    public Account() {
    }

    public Account(String owner, City city, char gender) {
        accountNumber = nextAccountNumber;
        nextAccountNumber += 10;

        this.owner = owner;
        this.city = city;
        this.gender = gender;

        balance = 0.0;
        openDate = null;
    }

    public Account(int accountNumber, String owner, City city, char gender, double balance) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.city = city;
        this.gender = gender;
        setBalance(balance);
    }

    public void setBalance(double balance) {
        if (balance > 0.0) {
            this.balance = balance;
        } else {
            this.balance = 0.0;
        }
    }

    @Override
    public String toString() {
        return accountNumber + " "
                + owner + " "
                + city + " "
                + gender + " "
                + balance;
    }

    @Override
    public int compareTo(Object o) {
        return this.owner.compareTo(((Account) o).owner);
    }

    public void deposit(double amount) {
        if (amount > 0) {
            setBalance(balance + amount);
        }
    }

    public double withdraw(double amount) {
        if (amount > 0) {
            if (amount < balance) {
                setBalance(balance - amount);
            } else {
                amount = balance;
                setBalance(0.0);
            }
            return amount;
        }
        return 0.0;
    }

}
