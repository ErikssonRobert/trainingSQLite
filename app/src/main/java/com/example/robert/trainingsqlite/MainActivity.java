package com.example.robert.trainingsqlite;

import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDB; //skapar instans av databasehelper klassen
    EditText editName, editLastName, editGrade;
    Button btnAddData;
    Button btnViewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DatabaseHelper(this); //kommer att anropa konstruktorn

        editName = (EditText) findViewById(R.id.nameField);
        editLastName = (EditText) findViewById(R.id.lastnameField);
        editGrade = (EditText) findViewById(R.id.gradeField);
        btnAddData = (Button) findViewById(R.id.addButton);
        btnViewAll = (Button) findViewById(R.id.viewButton);
        addData();
        ViewAll();
    }

    public void addData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDB.insertData(editName.getText().toString(),
                                editLastName.getText().toString(),
                                editGrade.getText().toString());
                        if (isInserted = true)
                            Toast.makeText(MainActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data NOT inserted", Toast.LENGTH_LONG).show();

                        editName.setText("");
                        editLastName.setText("");
                        editGrade.setText("");
                    }
                }
        );
    }

    public void ViewAll() {
        btnViewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDB.getAllData();
                        if (res.getColumnCount() == 0) {
                            showMessage("ERROR", "No Data Found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Id : " + res.getString(0) + "\n");
                            buffer.append("Name : " + res.getString(1) + "\n");
                            buffer.append("Lastname : " + res.getString(2) + "\n");
                            buffer.append("Grade : " + res.getString(3) + "\n");
                        }
                        showMessage("Data", buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


}
