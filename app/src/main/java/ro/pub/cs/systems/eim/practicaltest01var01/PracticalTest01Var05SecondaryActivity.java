package ro.pub.cs.systems.eim.practicaltest01var01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PracticalTest01Var05SecondaryActivity extends AppCompatActivity {

    private TextView Sum = null;
    private Button Return = null;
    int FinalSum = 0;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.Return:
                    setResult(FinalSum, null);
                    break;
            }

            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var05_secondary);

        FinalSum = 0;

        Sum = (TextView)findViewById(R.id.Sum);

        Return = (Button)findViewById(R.id.Return);
        Return.setOnClickListener(buttonClickListener);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey("AllTerms")) {
            String AllTerms = intent.getStringExtra("AllTerms");
            Sum.setText(String.valueOf(AllTerms));
        }

        String[] parts = Sum.getText().toString(). split(" \\+ ");



        for(int i  = 0; i< parts.length; i++)
        {
            FinalSum += Integer.parseInt(parts[i]);
        }
        Sum.setText(Sum.getText().toString() + " = " + String.valueOf(FinalSum));

    }
}
