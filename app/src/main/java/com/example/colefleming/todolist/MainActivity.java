package com.example.colefleming.todolist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.example.colefleming.todolist.R.id.editNewItem;

public class MainActivity extends Activity {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listItems = (ListView) findViewById(R.id.listItems);
        items = new ArrayList<String>();
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, items);
        listItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    public void addTodoItems(View v) {
        EditText editNewItems = (EditText)findViewById(editNewItem);
        itemsAdapter.add(editNewItems.getText().toString());
        editNewItems.setText("");
        saveItems(); //write to file
    }

    private void setupListViewListener(){
        listItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long rowId) {
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                saveItems();
                return true;
            }
        });
    }

    private void readItems() {
        File filesDir = getExternalFilesDir(null);
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile, "UTF-8"));
        } catch (IOException e) {
            items = new ArrayList<String>();
            e.printStackTrace();
        }
    }

    private void saveItems() {
        File filesDir = getExternalFilesDir(null);
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}