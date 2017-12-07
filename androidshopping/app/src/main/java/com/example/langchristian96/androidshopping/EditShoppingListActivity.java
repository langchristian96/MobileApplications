package com.example.langchristian96.androidshopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.langchristian96.androidshopping.model.Product;
import com.example.langchristian96.androidshopping.model.ShoppingList;
import com.example.langchristian96.androidshopping.model.ShoppingListProduct;
import com.example.langchristian96.androidshopping.repository.ShoppingListRepository;
import com.example.langchristian96.androidshopping.utils.Globals;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

public class EditShoppingListActivity extends AppCompatActivity {
    private int position;
    private ShoppingList crtShoppingList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shopping_list);
        Bundle b = getIntent().getExtras();
        position = b.getInt("position")-1;
        final EditText nameEdit = (EditText) findViewById(R.id.nameEdit);
        final EditText descriptionEdit = (EditText) findViewById(R.id.descriptionEdit);
        crtShoppingList = Globals.shoppingListRepository.getAll().get(position);
        final ShoppingList sl = new ShoppingList(crtShoppingList.getSlName(), crtShoppingList.getSlDescription());
        sl.setId(crtShoppingList.getId());
        sl.setBuyHour(crtShoppingList.getBuyHour());
        sl.setBuyMinute(crtShoppingList.getBuyMinute());
        nameEdit.setText(crtShoppingList.getSlName());
        descriptionEdit.setText(crtShoppingList.getSlDescription());
        TextView buyTime = (TextView) findViewById(R.id.buyTime);
        buyTime.setText(""+crtShoppingList.getBuyHour()+":"+crtShoppingList.getBuyMinute());
        Button updateButton = (Button) findViewById(R.id.updateButton);
        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        final ShoppingListRepository repo = Globals.shoppingListRepository;
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sl.setSlName(nameEdit.getText().toString());
                sl.setSlDescription(descriptionEdit.getText().toString());
                repo.update(sl);
                Intent intent = new Intent(EditShoppingListActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repo.delete(sl);
                Intent intent = new Intent(EditShoppingListActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ListView availableProductsView = (ListView) findViewById(R.id.productsAvailable);
        final List<Product> availableProducts = Globals.shoppingListRepository.getProducts(crtShoppingList.getId());
        final ArrayAdapter<Product> adapter = new ArrayAdapter<Product>(this,
                android.R.layout.simple_list_item_1, availableProducts);
        availableProductsView.setAdapter(adapter);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final List<Product> products = Globals.productRepository.getAll();
        final ArrayAdapter<Product> dataAdapter = new ArrayAdapter<Product>(this,
                android.R.layout.simple_spinner_item, products);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        Button addproduct = (Button) findViewById(R.id.addProduct);


        addproduct.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Product p = (Product) spinner.getSelectedItem();
                boolean added = Globals.shoppingListRepository.addProductToList(sl.getId(),p.getProductId());
                if(added) {
                    availableProducts.add(p);
                    adapter.notifyDataSetChanged();
                }
                System.out.println(p);
            }
        });
    }
}
