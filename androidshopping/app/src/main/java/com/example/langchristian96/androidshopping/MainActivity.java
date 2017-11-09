package com.example.langchristian96.androidshopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.langchristian96.androidshopping.model.ShoppingList;
import com.example.langchristian96.androidshopping.utils.Globals;

public class MainActivity extends AppCompatActivity {
    private Button addButton;
    private Button allListsButton;
    private EditText nameEditText;
    private EditText descriptionEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.addButton = (Button)findViewById(R.id.button);
        this.allListsButton = (Button) findViewById(R.id.button2);
        this.nameEditText = (EditText) findViewById(R.id.editText);
        this.descriptionEditText = (EditText) findViewById(R.id.editText2);
        addButton.setOnClickListener(new EmailSenderListener());
        allListsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ShoppingListsActivity.class);
                startActivity(intent);
            }
        });
    }

    private class EmailSenderListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String[] emails = {"langchristian96@gmail.com"};
            String subject = "New List Added";
            String listName = nameEditText.getText().toString();
            String listDescription = descriptionEditText.getText().toString();
            String message = "List with name: " + listName + " and description: "+listDescription+" was added";

            ShoppingList shoppingList = new ShoppingList(listName, listDescription);
            Globals.shoppingListRepository.add(shoppingList);

            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, emails);
            email.putExtra(Intent.EXTRA_SUBJECT, subject);
            email.putExtra(Intent.EXTRA_TEXT, message);
            email.setType("message/rfc822");

            startActivity(Intent.createChooser(email, "Choose an Email client :"));
        }
    }


}
