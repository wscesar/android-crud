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

import com.wscesar.crud.data.DatabaseHandler;
import com.wscesar.crud.model.Product;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseHandler databaseHandler;

    public Button saveButton;
    public EditText itemName;
    public EditText itemPrice;

    private ProductList recyclerViewAdapter;
    private List<Product> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHandler = new DatabaseHandler(this);

        RecyclerView productList = findViewById(R.id.productList);
        productList.setHasFixedSize(true);
        productList.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();
        itemList = databaseHandler.getAllItems();

        recyclerViewAdapter = new ProductList(this, itemList);

        productList.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

        itemName = findViewById(R.id.mainProduct);
        itemPrice = findViewById(R.id.mainPrice);

        saveButton = findViewById(R.id.mainButton);
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
        Product item = new Product();

        String newItem = itemName.getText().toString().trim();
        Float newPrice = Float.parseFloat(itemPrice.getText().toString().trim());

        item.setItemName(newItem);
        item.setPrice(newPrice);

        databaseHandler.addItem(item);

        Toast.makeText(MainActivity.this, "Salvo", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MainActivity.this, MainActivity.class));

    }
}
