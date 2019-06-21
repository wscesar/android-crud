package com.wscesar.crud;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bawp.crud.R;
import com.wscesar.crud.data.DatabaseHandler;
import com.wscesar.crud.model.Item;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseHandler databaseHandler;

    public Button saveButton;
    public EditText itemName;
    public EditText itemPrice;

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

        itemName = findViewById(R.id.productName);
        itemPrice = findViewById(R.id.productPrice);

        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( !itemName.getText().toString().isEmpty() && !itemPrice.getText().toString().isEmpty())
                    saveItem(v);
                else
                    Toast.makeText(MainActivity.this, "Preencha todos o campos", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void saveItem(View v) {
        Item item = new Item();

        String newItem = itemName.getText().toString().trim();
        Float newPrice = Float.parseFloat(itemPrice.getText().toString().trim());

        item.setItemName(newItem);
        item.setPrice(newPrice);

        databaseHandler.addItem(item);

        Toast.makeText(MainActivity.this, "Salvo", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MainActivity.this, MainActivity.class));

    }
}
