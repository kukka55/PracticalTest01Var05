package ro.pub.cs.systems.eim.practicaltest01var01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01Var05MainActivity extends AppCompatActivity {
    private EditText NextTerm = null;
    private TextView AllTerms = null;
    private Button Add = null;
    private Button Compute = null;

    int LastSumComputed = 0;
    boolean ChangeMade = false;

    private IntentFilter intentFilter = new IntentFilter();

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.Add:
                    if(NextTerm.getText().length() > 0) {
                        if(AllTerms.getText().length() > 0)
                            AllTerms.setText(AllTerms.getText().toString() + " + " + NextTerm.getText().toString());
                        else
                            AllTerms.setText(NextTerm.getText().toString());
                        NextTerm.setText("");
                        ChangeMade = true;
                    }
                    break;
                case R.id.Compute:
                    if(ChangeMade) {
                        Intent intent = new Intent(getApplicationContext(), PracticalTest01Var05SecondaryActivity.class);
                        intent.putExtra("AllTerms", AllTerms.getText().toString());
                        startActivityForResult(intent, 11);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Last computed sum is :  " + String.valueOf(LastSumComputed), Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 11) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
            ChangeMade = false;
            LastSumComputed = resultCode;

            if(resultCode > 10) {
                Intent intentService = new Intent(getApplicationContext(), PracticalTest01Var05Service.class);
                intentService.putExtra("Sum", resultCode);
                getApplicationContext().startService(intentService);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("LastSumComputed", LastSumComputed);
        savedInstanceState.putBoolean("ChangeMade", ChangeMade);
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[Message]", intent.getStringExtra("message"));
            Toast.makeText(getApplicationContext(), intent.getStringExtra("message"), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey("LastSumComputed")) {
            LastSumComputed = savedInstanceState.getInt("LastSumComputed");
        } else {
            LastSumComputed = 0;
        }
        if (savedInstanceState.containsKey("ChangeMade")) {
            ChangeMade = savedInstanceState.getBoolean("ChangeMade");
        } else {
            ChangeMade = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var05_main);

        NextTerm = (EditText)findViewById(R.id.NextTerm);
        AllTerms = (TextView)findViewById(R.id.AllTerms);

        Add = (Button)findViewById(R.id.Add);
        Add.setOnClickListener(buttonClickListener);
        Compute = (Button)findViewById(R.id.Compute);
        Compute.setOnClickListener(buttonClickListener);


        intentFilter.addAction("Sum10");


        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("LastSumComputed")) {
                LastSumComputed = savedInstanceState.getInt("LastSumComputed");
            } else {
                LastSumComputed = 0;
            }
            if (savedInstanceState.containsKey("ChangeMade")) {
                ChangeMade = savedInstanceState.getBoolean("ChangeMade");
            } else {
                ChangeMade = false;
            }
        }

    }
}
