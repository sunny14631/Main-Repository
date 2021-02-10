package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText editIndEarn, editIndPoss, editProjEarn, editProjPoss, editMidEarn,
            editMidPoss, editFinEarn, editFinPoss;
    TextView finalGrade, letterGrade;
    Button calculateButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editIndEarn = (EditText) findViewById(R.id.individualEditEarned);
        editIndPoss = (EditText) findViewById(R.id.individualEditPossible);
        editProjEarn = (EditText) findViewById(R.id.projectEditEarned);
        editProjPoss = (EditText) findViewById(R.id.projectEditPossible);
        editMidEarn = (EditText) findViewById(R.id.midtermEditEarned);
        editMidPoss = (EditText) findViewById(R.id.midtermEditPossible);
        editFinEarn = (EditText) findViewById(R.id.finalEditEarned);
        editFinPoss = (EditText) findViewById(R.id.finalEditPossible);
        finalGrade = (TextView) findViewById(R.id.finalGrade);
        letterGrade = (TextView) findViewById(R.id.letterGrade);
        calculateButton = (Button) findViewById(R.id.calculateButton);

        calculateButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                double totalPossGrade = 100;
                DecimalFormat df = new DecimalFormat("#.#");

                double indPercentGrade = 0;
                String indEarnString = editIndEarn.getText().toString();
                String indPossString = editIndPoss.getText().toString();

                if(indEarnString.matches("") || indPossString.matches("") || Double.parseDouble(indPossString) == 0) {
                    totalPossGrade -= 20;
                }
                else {
                    double indEarn = Double.parseDouble(indEarnString);
                    double indPoss = Double.parseDouble(indPossString);
                    indPercentGrade = indEarn/indPoss * 20;
                }

                double projPercentGrade = 0;
                String projEarnString = editProjEarn.getText().toString();
                String projPossString = editProjPoss.getText().toString();
                if(projEarnString.matches("") || projPossString.matches("") || Double.parseDouble(projPossString) == 0) {
                    totalPossGrade -= 30;
                }
                else {
                    double projEarn = Double.parseDouble(projEarnString);
                    double projPoss = Double.parseDouble(projPossString);
                    projPercentGrade = projEarn/projPoss * 30;
                }

                double midPercentGrade = 0;
                String midEarnString = editMidEarn.getText().toString();
                String midPossString = editMidPoss.getText().toString();
                if(midEarnString.matches("") || midPossString.matches("") || Double.parseDouble(midPossString) == 0) {
                    totalPossGrade -= 30;
                }
                else {
                    double midEarn = Double.parseDouble(midEarnString);
                    double midPoss = Double.parseDouble(midPossString);
                    midPercentGrade = midEarn/midPoss * 30;
                }

                double finPercentGrade = 0;
                String finEarnString = editFinEarn.getText().toString();
                String finPossString = editFinPoss.getText().toString();
                if(finEarnString.matches("") || finPossString.matches("") || Double.parseDouble(finPossString) == 0) {
                    totalPossGrade -= 20;
                }
                else {
                    double finEarn = Double.parseDouble(finEarnString);
                    double finPoss = Double.parseDouble(finPossString);
                    finPercentGrade = finEarn/finPoss * 20;
                }

                double totalEarnGrade = finPercentGrade + indPercentGrade + midPercentGrade + projPercentGrade;
                double finalGradeResult = 0;
                if(totalPossGrade == 0) {
                    finalGrade.setText("0.0");
                    letterGrade.setText("F");
                }
                else {
                    finalGradeResult = totalEarnGrade/totalPossGrade * 100;
                    if (finalGradeResult >= 90) {
                        finalGrade.setText(df.format(finalGradeResult));
                        letterGrade.setText("A");
                    }
                    else if(finalGradeResult >= 80) {
                        finalGrade.setText(df.format(finalGradeResult));
                        letterGrade.setText("B");
                    }
                    else if(finalGradeResult >= 70) {
                        finalGrade.setText(df.format(finalGradeResult));
                        letterGrade.setText("C");
                    }
                    else if(finalGradeResult >= 60) {
                        finalGrade.setText(df.format(finalGradeResult));
                        letterGrade.setText("D");
                    }
                    else {
                        finalGrade.setText(df.format(finalGradeResult));
                        letterGrade.setText("F");
                    }
                }
            }
        });
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
}
