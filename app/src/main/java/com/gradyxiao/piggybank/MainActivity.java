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

public class MainActivity extends AppCompatActivity {

    private SharedPreferences myPrefs;
    private SharedPreferences.Editor peditor;
    private Bank bank;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = getApplicationContext();  // app level storage
        myPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        peditor = myPrefs.edit();
        bank= new Bank();

        peditor.putString("currentBalance", bank.getCurrentBalanceAsString());
        peditor.commit();

        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDepositWithdrawActivity(v);
            }
        });
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
