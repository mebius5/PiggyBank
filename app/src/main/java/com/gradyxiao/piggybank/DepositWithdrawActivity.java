package com.gradyxiao.piggybank;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class DepositWithdrawActivity extends AppCompatActivity {

    private SharedPreferences myPrefs;
    private SharedPreferences.Editor peditor;
    private Bank bank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_withdraw);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Context context = getApplicationContext();  // app level storage
        myPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        peditor = myPrefs.edit();
        bank= new Bank();

        String balance = myPrefs.getString("currentBalance","0");
        bank.setCurrentBalance(balance);
    }

    @Override
    protected void onStart(){
        super.onStart();
        bank.setCurrentBalance(myPrefs.getString("currentBalance", "0"));
    }

    @Override
    protected void onResume(){
        super.onResume();
        bank.setCurrentBalance(myPrefs.getString("currentBalance","0"));
    }

    @Override
    protected void onPause(){
        super.onPause();
        peditor.putString("currentBalance", bank.getCurrentBalanceAsString());
        peditor.commit();
    }

}
