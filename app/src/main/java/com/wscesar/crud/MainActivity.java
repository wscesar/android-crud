package com.wscesar.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bawp.babyneeds.R;
import com.wscesar.crud.data.DatabaseHandler;
import com.wscesar.crud.model.Item;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseHandler databaseHandler;

    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    private Button saveButton;
    private EditText itemName;
    private EditText itemQuantity;
    private FloatingActionButton fab;

    // private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHandler = new DatabaseHandler(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();
        itemList = databaseHandler.getAllItems();

        recyclerViewAdapter = new RecyclerViewAdapter(this, itemList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopDialog();
            }
        });
    }

    private void createPopDialog() {
        builder = new AlertDialog.Builder(this);
        View v = getLayoutInflater().inflate(R.layout.popup, null);

        itemName = v.findViewById(R.id.itemName);
        itemQuantity = v.findViewById(R.id.itemQuantity);
        saveButton = v.findViewById(R.id.saveButton);

        builder.setView(v);
        dialog = builder.create();
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!itemName.getText().toString().isEmpty() && !itemQuantity.getText().toString().isEmpty()) {
                    saveItem(v);
                } else {
                    Snackbar.make(v, "Preencha todos os campos", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void saveItem(View v) {
        Item item = new Item();

        String newItem = itemName.getText().toString().trim();
        int quantity = Integer.parseInt(itemQuantity.getText().toString().trim());

        item.setItemName(newItem);
        item.setItemQuantity(quantity);

        databaseHandler.addItem(item);

        Snackbar.make(v, "Salvo!!!", Snackbar.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                finish();
            }
        }, 500);
    }
}
