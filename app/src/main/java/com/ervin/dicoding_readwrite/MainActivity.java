package com.ervin.dicoding_readwrite;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    Button btnNew;
    Button btnOpen;
    Button btnSave;
    EditText editText;
    EditText editTitle;
    File path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNew = (Button) findViewById(R.id.button_new);
        btnOpen = (Button) findViewById(R.id.button_open);
        btnSave = (Button) findViewById(R.id.button_save);
        editText = (EditText) findViewById(R.id.editFile);
        editTitle = (EditText) findViewById(R.id.editTitle);
        btnNew.setOnClickListener(this);
        btnOpen.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        path = getFilesDir();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.button_new:
                newFile();
                break;
            case R.id.button_open:
                openFile();
                break;
            case R.id.button_save:
                saveFile();
                break;
        }
    }
    public void newFile() {

        editTitle.setText("");
        editText.setText("");

        Toast.makeText(this,"Clearing file", Toast.LENGTH_SHORT).show();
    }

    public void openFile() {
        showList();
    }

    public void saveFile() {
        if (editTitle.getText().toString().isEmpty()) {

            Toast.makeText(this,"Title harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
        }
        else {
            String title = editTitle.getText().toString();
            String text = editText.getText().toString();
            FileHelper.writeToFile(title, text, this);
            Toast.makeText(this,"Saving "+editTitle.getText().toString()+" file", Toast.LENGTH_SHORT).show();
        }
    }
    private void showList() {
        final ArrayList<String> arrayList = new ArrayList<String>();
        for (String file : path.list()) {
            Log.d("file", "showList: "+file);
            arrayList.add(file);
        }
        final CharSequence[] items = arrayList.toArray(new CharSequence[arrayList.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih file yang diinginkan");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                loadData(items[item].toString());
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void loadData(String title){
        String text = FileHelper.readFromFile(this,title);
        editTitle.setText(title);
        editText.setText(text);
        Toast.makeText(this,"Loading "+title+" data", Toast.LENGTH_SHORT).show();
    }
}
