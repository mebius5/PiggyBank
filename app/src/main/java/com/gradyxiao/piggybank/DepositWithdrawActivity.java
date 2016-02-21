package com.gradyxiao.piggybank;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Stack;
import java.util.Random;

public class DepositWithdrawActivity extends AppCompatActivity {

    private SharedPreferences myPrefs;
    private SharedPreferences.Editor peditor;
    private Bank bank;
    private Stack<Double> stack;
    private TextView amountTextView;
    private ImageButton cent1,cent5,cent10,cent25;
    private ImageButton dollar1,dollar5,dollar10,dollar20;
    private Button undo, withdraw, deposit;
    private String[] depositMessages = {"Your parents will be proud!", "A penny saved is a penny earned!", "Watch all that money piling up!"};
    private String[] withdrawlMessages = {"Don't spend it all on candy!", "Be careful with all that cash!", "Wow, the PiggyBank is lighter!"};
    int messagePicker;
    Random rand;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_withdraw);

        Context context = getApplicationContext();  // app level storage
        myPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        peditor = myPrefs.edit();
        bank= new Bank();
        stack = new Stack<Double>();

        String balance = myPrefs.getString("currentBalance","0");
        bank.setCurrentBalance(balance);

        amountTextView = (TextView)findViewById(R.id.textView3);
        amountTextView.setText(R.string.balance_text);

        cent1=(ImageButton)findViewById(R.id.imageButton);
        cent5=(ImageButton)findViewById(R.id.imageButton2);
        cent10=(ImageButton)findViewById(R.id.imageButton3);
        cent25=(ImageButton)findViewById(R.id.imageButton4);
        dollar1=(ImageButton)findViewById(R.id.imageButton5);
        dollar5=(ImageButton)findViewById(R.id.imageButton6);
        dollar10=(ImageButton)findViewById(R.id.imageButton7);
        dollar20=(ImageButton)findViewById(R.id.imageButton8);

        cent1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increment(v, 0.01);
            }
        });
        cent5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increment(v, 0.05);
            }
        });
        cent10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increment(v, 0.10);
            }
        });
        cent25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increment(v, 0.25);
            }
        });
        dollar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increment(v, 1);
            }
        });
        dollar5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increment(v, 5);
            }
        });
        dollar10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increment(v, 10);
            }
        });
        dollar20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increment(v, 20);
            }
        });

        deposit = (Button)findViewById(R.id.button2);
        undo = (Button)findViewById(R.id.button3);
        withdraw = (Button)findViewById(R.id.button4);

        deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                depositClick(v);
            }
        });

        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                undoClick(v);
            }
        });

        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withdrawClick(v);
            }
        });

        rand = new Random();

    }

    @Override
    protected void onStart(){
        super.onStart();
        bank.setCurrentBalance(myPrefs.getString("currentBalance", "0"));

        Double total=0.00;
        for(Double a:stack){
            total=total+a;
        }
        amountTextView.setText("$"+String.format("%.2f",total));
    }

    @Override
    protected void onResume(){
        super.onResume();
        bank.setCurrentBalance(myPrefs.getString("currentBalance", "0"));

        Double total=0.00;
        for(Double a:stack){
            total=total+a;
        }
        amountTextView.setText("$" + String.format("%.2f", total));
    }

    @Override
    protected void onPause(){
        super.onPause();
        peditor.putString("currentBalance", bank.getCurrentBalanceAsString());
        peditor.commit();
    }

    public void increment(View view, double amount){
        stack.push(amount);

        Double total=0.00;
        for(Double inStack:stack){
            total=total+inStack;
        }
        amountTextView.setText("$" + String.format("%.2f",total));
    }

    public void undoClick(View view) {
        if(stack.size()>0) {
            stack.pop();
            Double total=0.00;
            for(Double inStack:stack){
                total=total+inStack;
            }
            amountTextView.setText("$" + String.format("%.2f", total));
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), R.string.withdrawLessThan0, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void depositClick (View view){
        final String amountText = amountTextView.getText().toString();
        new AlertDialog.Builder(this)
                .setTitle(R.string.deposit_btn)
                .setMessage("Do you really want to deposit "+amountText)
                .setIcon(R.mipmap.piggy_icon)
                .setPositiveButton(R.string.confirm_btn, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        bank.deposit(amountText);
                        peditor.putString("currentBalance", bank.getCurrentBalanceAsString());
                        peditor.commit();

                        onBackPressed();
                        messagePicker = rand.nextInt(3);
                        Toast toast = Toast.makeText(getApplicationContext(), depositMessages[messagePicker], Toast.LENGTH_LONG);
                        toast.show();
                    }})
                .setNegativeButton(R.string.cancel_btn, null).show();
    }

    public void withdrawClick(View view){
        final String amountText = amountTextView.getText().toString();

        new AlertDialog.Builder(this)
                .setTitle(R.string.withdraw_btn)
                .setMessage("Do you really want to withdraw "+amountText)
                .setIcon(R.mipmap.piggy_icon)
                .setPositiveButton(R.string.confirm_btn, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        try {
                            bank.withdraw(amountText);
                            peditor.putString("currentBalance", bank.getCurrentBalanceAsString());
                            peditor.commit();

                            onBackPressed();
                            messagePicker = rand.nextInt(3);
                            Toast toast = Toast.makeText(getApplicationContext(), withdrawlMessages[messagePicker], Toast.LENGTH_LONG);
                            toast.show();
                        } catch (BankError e) {
                            Toast toast = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }})
                .setNegativeButton(R.string.cancel_btn, null).show();
    }
}
