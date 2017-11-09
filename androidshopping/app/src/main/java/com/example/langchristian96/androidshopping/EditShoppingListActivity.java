package com.example.langchristian96.androidshopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.langchristian96.androidshopping.model.ShoppingList;
import com.example.langchristian96.androidshopping.utils.Globals;

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
        nameEdit.setText(crtShoppingList.getName());
        descriptionEdit.setText(crtShoppingList.getDescription());
        Button updateButton = (Button) findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crtShoppingList.setName(nameEdit.getText().toString());
                crtShoppingList.setDescription(descriptionEdit.getText().toString());
                Intent intent = new Intent(EditShoppingListActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
