package org.bankTransaction.utils.pojo;


    /**
     * @author Zeus Vargas
     * A POJO based class that has base attributes and methods to handle its bank transaction information.
     * It also works as a base format to create json objects.
     */
    public class BankTransactionData {
        private int id;
        private String name;
        private String lastName;
        private int accountNumber;
        private double amount;
        private String transactionType;
        private String email;
        private boolean active;
        private String country;
        private String telephone;

    /**
     * Base BankTransactionData constructor method.
     */
    public BankTransactionData() {
    }
    /**
     * BankTransactionData constructor method called with the class attributes as needed to initiate a new bank transaction object.
     */
    public BankTransactionData(String name, String lastName, int accountNumber, double amount, String transactionType, String email, boolean active, String country, String telephone) {
        this.name = name;
        this.lastName = lastName;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.transactionType = transactionType;
        this.email = email;
        this.active = active;
        this.country = country;
        this.telephone = telephone;
    }

    /**
     * The mock api creates the id automatically and serialized. This method
     * @return the id.
     */
    public int getId() {
        return this.id;
    }
    /**
     * This method
     * @return the name of the bank transaction.
     */
    public String getName() {
        return this.name;
    }
    /**
     * This method
     * @return the lastName of the bank transaction.
     */
    public String getLastName() {
        return this.lastName;
    }
    /**
     * This method
     * @return the accountNumber of the bank transaction.
     */
    public int getAccountNumber() {
        return this.accountNumber;
    }
    /**
     * This method
     * @return the amount of the bank transaction.
     */
    public double getAmount() {
        return this.amount;
    }
    /**
     * This method
     * @return the transactionType of the bank transaction.
     */
    public String getTransactionType() {
        return this.transactionType;
    }
    /**
     * This method
     * @return the email of the bank transaction.
     */
    public String getEmail() {return this.email;
    }
    /**
     * This method
     * @return the active of the bank transaction.
     */
    public boolean getActive() {
        return this.active;
    }
    /**
     * This method
     * @return the country of the bank transaction.
     */
    public String getCountry() {
        return this.country;
    }
    /**
     * This method
     * @return the telephone of the bank transaction.
     */
    public String getTelephone() {
        return this.telephone;
    }

    /**
     * This method sets the name of the bank transaction.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * This method sets the lastName of the bank transaction.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * This method sets the accountNumber of the bank transaction.
     */
    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }
    /**
     * This method sets the amount of the bank transaction.
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }
    /**
     * This method sets the transactionType of the bank transaction.
     */
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
    /**
     * This method sets the email of the bank transaction.
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * This method sets the active property of the bank transaction.
     */
    public void setActive(boolean active) {
        this.active = active;
    }
    /**
     * This method sets the country of the bank transaction.
     */
    public void setCountry(String country) {
        this.country = country;
    }
    /**
     * This method sets the telephone of the bank transaction.
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
