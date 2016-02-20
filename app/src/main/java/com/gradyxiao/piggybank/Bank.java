package com.gradyxiao.piggybank;

/**
 * Created by GradyXiao on 2/19/16.
 */
public class Bank {
    private double currentBalance;

    public Bank(){
        this.currentBalance=0;
    }

    public void deposit(double amount) {
        this.currentBalance=currentBalance+amount;
    }

    public void withdraw(double amount) throws BankError{
        if(currentBalance-amount>=0) {
            this.currentBalance = currentBalance - amount;
        } else{
            throw new BankError("Does not have enough money in balance");
        }
    }

    public void deposit(String amount){
        //Note: amount is "$xx.xx"
        //Offset the "$"
        amount = amount.substring(1);
        deposit(Double.parseDouble(amount));
    }

    public void withdraw(String amount) throws BankError{
        //Note: amount is "$xx.xx"
        //Offset the "$"
        amount = amount.substring(1);
        withdraw(Double.parseDouble(amount));
    }

    public void setCurrentBalance(double amount){
        this.currentBalance=amount;
    }

    public void setCurrentBalance(String amount){
        //Offset the "$"
        amount = amount.substring(1);
        this.currentBalance = Double.parseDouble(amount);
    }

    public Double getCurrentBalanceAsDouble(){
        return this.currentBalance;
    }

    public String getCurrentBalanceAsString(){
        return "$"+String.format("%.2f",this.currentBalance);
    }
}
