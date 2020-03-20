package com.example.gpacalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.SQLOutput;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    EditText editTextCurrGPA;
    EditText editTextCurrCredits;
    EditText editTextLG1;
    EditText editTextLG2;
    EditText editTextLG3;
    EditText editTextLG4;
    EditText editTextLG5;
    EditText editTextLG6;
    EditText editTextCC1;
    EditText editTextCC2;
    EditText editTextCC3;
    EditText editTextCC4;
    EditText editTextCC5;
    EditText editTextCC6;

    TextView textViewNewGPA;
    TextView textViewNewCredits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextCurrGPA = (EditText) findViewById(R.id.editTextCurrGPA);
        editTextCurrCredits = (EditText) findViewById(R.id.editTextCurrCredits);

        editTextLG1 = (EditText) findViewById(R.id.editTextLG1);
        editTextLG2 = (EditText) findViewById(R.id.editTextLG2);
        editTextLG3 = (EditText) findViewById(R.id.editTextLG3);
        editTextLG4 = (EditText) findViewById(R.id.editTextLG4);
        editTextLG5 = (EditText) findViewById(R.id.editTextLG5);
        editTextLG6 = (EditText) findViewById(R.id.editTextLG6);
        editTextCC1 = (EditText) findViewById(R.id.editTextCC1);
        editTextCC2 = (EditText) findViewById(R.id.editTextCC2);
        editTextCC3 = (EditText) findViewById(R.id.editTextCC3);
        editTextCC4 = (EditText) findViewById(R.id.editTextCC4);
        editTextCC5 = (EditText) findViewById(R.id.editTextCC5);
        editTextCC6 = (EditText) findViewById(R.id.editTextCC6);

        Button buttonCalculate = (Button) findViewById(R.id.buttonCalculate);
        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double currGPA;
                try {
                    currGPA = Double.parseDouble(editTextCurrGPA.getText().toString());
                } catch (Exception e) {
                    String title = String.format("%s", "Invalid Input: Current GPA");
                    String message = String.format("%s", "Current GPA must be a number");
                    invalidInputAlertMesasge(title, message);
                    return;
                }

                int currCredit;
                try {
                    currCredit = Integer.parseInt(editTextCurrCredits.getText().toString());
                } catch (Exception e) {
                    String title = String.format("%s", "Invalid Input: Credits Earned");
                    String message = String.format("%s", "Credits Earned must be a non-negative integer and can not be too large");
                    invalidInputAlertMesasge(title, message);
                    return;
                }

                textViewNewGPA = (TextView) findViewById(R.id.textViewNewGPA);
                textViewNewCredits = (TextView) findViewById(R.id.textViewNewCredits);
                editTextCurrGPA = (EditText) findViewById(R.id.editTextCurrGPA);
                editTextCurrCredits = (EditText) findViewById(R.id.editTextCurrCredits);

                Vector<Double> letterGrades = new Vector<Double>();
                Vector<Integer> courseCredits = new Vector<Integer>();

                String LG1;
                String LG2;
                String LG3;
                String LG4;
                String LG5;
                String LG6;
                String CC1;
                String CC2;
                String CC3;
                String CC4;
                String CC5;
                String CC6;

                try {
                    LG1 = editTextLG1.getText().toString();
                    LG2 = editTextLG2.getText().toString();
                    LG3 = editTextLG3.getText().toString();
                    LG4 = editTextLG4.getText().toString();
                    LG5 = editTextLG5.getText().toString();
                    LG6 = editTextLG6.getText().toString();
                } catch (Exception e) {
                    invalidInputAlertMesasge("Invalid Input: Letter Grades", "One or more Letters Grades not valid");
                    return;
                }
                try {
                    CC1 = editTextCC1.getText().toString();
                    CC2 = editTextCC2.getText().toString();
                    CC3 = editTextCC3.getText().toString();
                    CC4 = editTextCC4.getText().toString();
                    CC5 = editTextCC5.getText().toString();
                    CC6 = editTextCC6.getText().toString();

                } catch (Exception e) {
                    invalidInputAlertMesasge("Invalid Input: Course Credits", "One or more Course Credits input not valid");
                    return;
                }

                checkAndAddOrAlert(LG1, CC1, letterGrades, courseCredits);
                checkAndAddOrAlert(LG2, CC2, letterGrades, courseCredits);
                checkAndAddOrAlert(LG3, CC3, letterGrades, courseCredits);
                checkAndAddOrAlert(LG4, CC4, letterGrades, courseCredits);
                checkAndAddOrAlert(LG5, CC5, letterGrades, courseCredits);
                checkAndAddOrAlert(LG6, CC6, letterGrades, courseCredits);


                DecimalFormat df = new DecimalFormat("####.###");

                double sumNumber = 0;
                int sumCredits = 0;

                for (Double thisNumberGrade : letterGrades) {
                    sumNumber += thisNumberGrade;
                }
                for (Integer thisCreditAmount : courseCredits) {
                    sumCredits += thisCreditAmount;
                }


                if (letterGrades.size() == 0) {
                    textViewNewGPA.setText(Double.toString(currGPA));
                    textViewNewCredits.setText(Integer.toString(currCredit));
                    return;
                }
                int updatedCredits = currCredit + sumCredits;
                double newGPA = (((double) currCredit / (double) updatedCredits) * currGPA) + ((double) sumCredits / (double) updatedCredits) * (sumNumber / (double) courseCredits.size());
                String strNewGPA = df.format(newGPA);


                textViewNewCredits.setText(Integer.toString(updatedCredits));
                textViewNewGPA.setText(strNewGPA);

            }
        });
    }

    public void invalidInputAlertMesasge(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void checkAndAddOrAlert(String LG, String CC, Vector letterGrades, Vector courseCredits) {
        if (LG.trim().length() == 0) {
            if (CC.trim().length() != 0) {
                invalidInputAlertMesasge("Error: Both Inputs Required",
                        "If one of Letter Grades or Course Credits is filled, then both must be filled for that row. Incorrect GPA and/or credits might be displayed until next recalculation");
            }
            return;
        }
        if (CC.trim().length() == 0) {
            invalidInputAlertMesasge("Error: Both Inputs Required",
                    "If one of Letter Grades or Course Credits is filled, then both must be filled for that row. Incorrect GPA and/or credits might be displayed until next recalculation");
            return;
        }
        double numberGrade = convertToNumberGrade(LG.trim());
        if (numberGrade < 0) {
            String message = String.format("%s%n%s%n", "Letter Grades must be A+, A, A-, B+, B, B-, C+, C, C-, D, or F",
                    "Incorrect GPA and/or credits might be displayed until next recalculation");
            invalidInputAlertMesasge("Invalid Input: Letter Grade", message);
            return;
        }
        int intCC = Integer.parseInt(CC);
        letterGrades.add(numberGrade);
        courseCredits.add(intCC);
        System.out.println("P1");
        return;
    }

    public double convertToNumberGrade(String LG) {
        switch (LG) {
            case "A+":
                return 4;
            case "A":
                return 4;
            case "A-":
                return 3.7;
            case "B+":
                return 3.3;
            case "B":
                return 3;
            case "B-":
                return 2.7;
            case "C+":
                return 2.3;
            case "C":
                return 2;
            case "C-":
                return 1.7;
            case "D":
                return 1;
            case "F":
                return 0;
            default:
                return -1;
        }
    }
}