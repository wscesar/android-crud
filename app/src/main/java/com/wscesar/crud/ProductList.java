package com.wscesar.crud;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wscesar.crud.data.DatabaseHandler;
import com.wscesar.crud.model.Product;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.List;

public class ProductList extends Adapter<ProductList.ViewHolder> {

    private Context context;
    private List<Product> itemList;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

    public ProductList(Context context, List<Product> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ProductList.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_list_item, viewGroup, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductList.ViewHolder viewHolder, int position) {

        Product item = itemList.get(position);

        viewHolder.labelName.setText(
                MessageFormat.format("{0}", item.getItemName())
        );

        viewHolder.labelPrice.setText(
                MessageFormat.format("Preço: {0}", item.getCurrency())
        );

        viewHolder.labelDate.setText(
                MessageFormat.format("Adicionado em {0}", item.getDateItemAdded())
        );
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView labelName;
        public TextView labelPrice;
        public TextView labelDate;
        public Button editButton;
        public Button deleteButton;

        //public int id;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;

            labelName = itemView.findViewById(R.id.listItemName);
            labelPrice = itemView.findViewById(R.id.listItemPrice);
            labelDate = itemView.findViewById(R.id.listItemDate);

            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            int position;
            position = getAdapterPosition();
            Product item = itemList.get(position);

            switch (v.getId()) {
                case R.id.editButton:
                    editItem(item);
                break;

                case R.id.deleteButton:
                    deleteItem(item.getId());
                break;
            }

        }

        private void deleteItem(final int id) {

            builder = new AlertDialog.Builder(context);

            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.delete_modal, null);

            Button noButton = view.findViewById(R.id.conf_no_button);
            Button yesButton = view.findViewById(R.id.conf_yes_button);

            builder.setView(view);
            dialog = builder.create();
            dialog.show();

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler db = new DatabaseHandler(context);
                    db.deleteItem(id);
                    itemList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());

                    dialog.dismiss();
                }
            });

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

        }

        private void editItem(final Product newItem) {

            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            final View v = inflater.inflate(R.layout.update_modal, null);

            Button saveButton;
            final EditText inputName;
            final EditText inputPrice;

            inputName = v.findViewById(R.id.popProduct);
            inputName.setText(newItem.getItemName());

            inputPrice = v.findViewById(R.id.popPrice);
            inputPrice.setText(String.valueOf(newItem.getPrice()));

            builder.setView(v);
            dialog = builder.create();
            dialog.show();

            saveButton = v.findViewById(R.id.popButton);
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler databaseHandler = new DatabaseHandler(context);

                    // update items
                    newItem.setItemName(inputName.getText().toString());
                    newItem.setPrice(Float.parseFloat(inputPrice.getText().toString()));

                    NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
                    String currencyPrice =
                            numberFormat.format(Float.parseFloat(inputPrice.getText().toString()));
                    newItem.setCurrency(currencyPrice);

                    if (!inputName.getText().toString().isEmpty() && !inputPrice.getText().toString().isEmpty()) {

                        databaseHandler.updateItem(newItem);
                        notifyItemChanged(getAdapterPosition(), newItem); // update item on the view

                    } else {

                        Snackbar.make(v, "Preencha todos os campos", Snackbar.LENGTH_SHORT).show();

                    }

                    dialog.dismiss();

                }

            });

        }

    }
}
