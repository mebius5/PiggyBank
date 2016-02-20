package com.gradyxiao.piggybank;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences myPrefs;
    private SharedPreferences.Editor peditor;
    private Bank bank;
    private Button button;
    private TextView currentBalanceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = getApplicationContext();  // app level storage
        myPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        peditor = myPrefs.edit();
        bank= new Bank();

        if(myPrefs.getString("currentBalance","0").equals("0")){
            peditor.putString("currentBalance", bank.getCurrentBalanceAsString());
            peditor.commit();
        } else{
            bank.setCurrentBalance(myPrefs.getString("currentBalance","0"));
        }

        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDepositWithdrawActivity(v);
            }
        });

        currentBalanceTextView=(TextView)findViewById(R.id.textView);
        currentBalanceTextView.setText(bank.getCurrentBalanceAsString());
    }

    @Override
    protected void onStart(){
        System.out.println("onStart");

        super.onStart();
        bank.setCurrentBalance(myPrefs.getString("currentBalance", "0"));
        currentBalanceTextView.setText(bank.getCurrentBalanceAsString());
    }

    @Override
    protected void onResume(){
        System.out.println("onResume");

        super.onResume();
        bank.setCurrentBalance(myPrefs.getString("currentBalance", "0"));
        currentBalanceTextView.setText(bank.getCurrentBalanceAsString());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        System.out.println("onSaveInstanceState");

        outState.putString("currentBalance", bank.getCurrentBalanceAsString());

        peditor.putString("currentBalance", bank.getCurrentBalanceAsString());
        peditor.commit();

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        System.out.println("onRestoreInstanceState");

        super.onRestoreInstanceState(savedInstanceState);
        bank.setCurrentBalance(savedInstanceState.getString("currentBalance", "0"));

        peditor.putString("currentBalance", bank.getCurrentBalanceAsString());
        peditor.commit();

        currentBalanceTextView.setText(bank.getCurrentBalanceAsString());
    }

    @Override
    protected void onPause(){
        System.out.println("onPause");

        super.onPause();
        peditor.putString("currentBalance", bank.getCurrentBalanceAsString());
        peditor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startDepositWithdrawActivity(View view) {
        peditor.putString("currentBalance", bank.getCurrentBalanceAsString());
        peditor.commit();   // TO SAVE CHANGES

        Intent intent = new Intent(MainActivity.this, DepositWithdrawActivity.class);
        startActivity(intent);
    }

}
