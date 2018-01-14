package com.example.langchristian96.androidshopping;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import com.example.langchristian96.androidshopping.model.Product;
import com.example.langchristian96.androidshopping.model.ShoppingList;
import com.example.langchristian96.androidshopping.model.ShoppingListProduct;
import com.example.langchristian96.androidshopping.repository.AppDatabase;
import com.example.langchristian96.androidshopping.repository.ProductRepository;
import com.example.langchristian96.androidshopping.repository.ShoppingListRepository;
import com.example.langchristian96.androidshopping.utils.Globals;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button addButton;
    private Button allListsButton;
    private Button logoutButton;
    private EditText nameEditText;
    private EditText descriptionEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Globals.currentActivity = this;
        setContentView(R.layout.activity_main);
        this.logoutButton = (Button) findViewById(R.id.logoutButton);
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
        if(Globals.shoppingListRepository == null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
                    Globals.shoppingListRepository = new ShoppingListRepository(appDatabase);
                    Globals.productRepository = new ProductRepository(appDatabase);
                }
            }).start();
        }
        Button dateButton = (Button) findViewById(R.id.dateButton);
        dateButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });
        this.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globals.logout();
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button statisticsButton = (Button) findViewById(R.id.statisticsButton);
        statisticsButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // programmatically create a LineChart
                BarChart chart = new BarChart(getApplicationContext());
                chart.setDrawBarShadow(true);

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                chart.setLayoutParams(layoutParams);
                // get a layout defined in xml
                RelativeLayout rl = (RelativeLayout) findViewById(R.id.statistics_layout);
                rl.addView(chart);
                List<ShoppingListProduct> shoppingListProducts =  Globals.shoppingListRepository.getShoppingListProducts();
                Map<Long, Integer> pidToX = new HashMap<>();
                Map<Integer, String> xToProductName = new HashMap<Integer, String>();
                int crtx = 1;
                for(ShoppingListProduct shoppingListProduct:shoppingListProducts) {
                    if(!pidToX.containsKey(shoppingListProduct.getProductId())) {
                        Product p = Globals.productRepository.getProductById(shoppingListProduct.getProductId());
                        xToProductName.put(crtx, p.toString());
                        pidToX.put(shoppingListProduct.getProductId(),crtx++);
                    }
                }
                Map<Integer, Integer> xToCounter = new HashMap<Integer, Integer>();
                for(ShoppingListProduct slp: shoppingListProducts) {
                    int x= pidToX.get(slp.getProductId());
                    if(!xToCounter.containsKey(x)) {
                        xToCounter.put(x, 1);
                    }
                    else {
                        xToCounter.put(x,xToCounter.get(x)+1);
                    }
                }
                List<BarEntry> entries = new ArrayList<BarEntry>();
                for(Map.Entry<Integer, Integer> entry: xToCounter.entrySet()) {
                    BarEntry barEntry = new BarEntry(entry.getKey(), entry.getValue(), xToProductName.get(entry.getKey()));
                    entries.add(barEntry);
                }
                BarDataSet dataSet = new BarDataSet(entries, "Product");
                dataSet.setValueFormatter(new IValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                        return entry.getData().toString();
                    }
                });
                BarData lineData = new BarData(dataSet);
                chart.setData(lineData);
                chart.invalidate(); // refresh

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
            shoppingList.setBuyHour(Globals.buyHour);
            shoppingList.setBuyMinute(Globals.buyMinute);
            Globals.shoppingListRepository.add(shoppingList);

            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, emails);
            email.putExtra(Intent.EXTRA_SUBJECT, subject);
            email.putExtra(Intent.EXTRA_TEXT, message);
            email.setType("message/rfc822");

            startActivity(Intent.createChooser(email, "Choose an Email client :"));
        }
    }
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Globals.buyHour = hourOfDay;
            Globals.buyMinute = minute;
        }
    }


}
