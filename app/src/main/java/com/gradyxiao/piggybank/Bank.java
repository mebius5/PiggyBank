package com.gradyxiao.piggybank;

/**
 * Created by GradyXiao on 2/19/16.
 */
public class Bank {
    private double currentBalance;

    public Bank(){
        this.currentBalance=0;
    }

    public void deposit(double amount) throws BankError{
        this.currentBalance=currentBalance+amount;
    }

    public void withdraw(double amount) throws BankError{
        if(currentBalance-amount>=0) {
            this.currentBalance = currentBalance - amount;
        } else{
            throw new BankError("asdf");
        }
    }

    public void setCurrentBalance(int amount){
        this.currentBalance=amount;
    }

    public double getCurrentBanlance(int amount){
        return this.currentBalance;
    }

}
