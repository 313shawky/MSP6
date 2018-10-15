package com.example.ahmed.msp6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText edName;
    EditText edAge;
    DBConnection db;
    ListView lv;
    //TextView txt;

    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBConnection(this);
        edName = findViewById(R.id.et1);
        edAge = findViewById(R.id.et2);
        lv = findViewById(R.id.lv1);
        //txt = findViewById(R.id.txt);
    }

    public void show(ArrayList<String> arrayList)
    {
        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList));
    }

    public void onAdd(View view) {
        db.insertData(edName.getText().toString(), Integer.parseInt(edAge.getText().toString()));
    }

    public void onGet(View view) {
        //txt.setText((db.getData()));
        show(db.getData());
    }

    public void onSearch(View view) {
        ArrayList<String> result = db.getSearch(edName.getText().toString());
        //txt.setText(result);
        show(result);
    }

    public void onEdit(View view) {
        String newName = edName.getText().toString();
        int id = Integer.parseInt(edAge.getText().toString());
        db.update(id, newName);
    }

    public void onDelete(View view) {
        db.delete(Integer.parseInt(edAge.getText().toString()));
    }

    public void onStudent(View view) {
        EditText student = findViewById(R.id.student);
        db.insertStudent(student.getText().toString(), Integer.parseInt(edAge.getText().toString()));
    }

    public void onGetStudent(View view) {
        show(db.getStudents());
    }

    public void onGetAll(View view) {
        show(db.getALL());
    }
}
