package com.example.android.coffeeorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Global Variables
    static int quantity = 1; // default quantity
    int price = 300;
    int whippedCreamPrice = 150;
    int chocolatePrice = 100;
    static int total;
    CheckBox whippedCreamCheckBox;
    CheckBox chocolateCheckBox;
    static boolean whippedCheck;
    static boolean chocolateCheck;
    static String name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // making the whipped cream and chocolate checbox a global variable
        whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        chocolateCheckBox = findViewById(R.id.chocolate_checkbox);


        // starts the order summary activity
        Button viewOrder = findViewById(R.id.view_order);
        viewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent orderActivity = new Intent(MainActivity.this, OrderSummary.class);
                // getting the customer's name
                EditText nameView = findViewById(R.id.name);
                name = nameView.getText().toString();
                calculatePrice();
                if(name.matches("")){
                    nameView.setError(getString(R.string.name_enquiry));
                } else {
                    startActivity(orderActivity);
                }
            }
        });
    }


    /*
        the display quantity method changes the quantity textview to the value of the quantity variable
     */
    public void displayQuantity(){
        TextView quantityTextView = findViewById(R.id.quantity_text);
        quantityTextView.setText(String.valueOf(quantity));
    }

    /*
        increases or decreases the quantity based on the button tapped
     */
    public void setQuantity(View view){
        switch(view.getId()){
            case R.id.minus:
                if(quantity - 1 < 1){
                    Toast minimumWarning = Toast.makeText(getApplicationContext(), getString(R.string.minimum_coffees), Toast.LENGTH_SHORT);
                    minimumWarning.show();
                } else{
                    quantity--;
                    displayQuantity();
                }
                break;

            case R.id.plus:
                if(quantity + 1 > 100){
                    Toast maximumWarning = Toast.makeText(getApplicationContext(), getString(R.string.maximum_coffees), Toast.LENGTH_SHORT);
                    maximumWarning.show();
                } else {
                    quantity++;
                    displayQuantity();
                }
        }
    }

    /*
        this method changes the coffee image to an image of a coffee cups with whipped cream
     */
//    public void whippedCreamCheck(View view){
//
//        ImageView i = findViewById(R.id.coffeeImage);
//        if(whippedCreamCheckBox.isChecked()){
//            i.setImageResource(R.drawable.coffeewhipped);
//        } else{
//            i.setImageResource(R.drawable.coffeetransp);
//        }
//    }

    // calculates and displays the total price of the coffee ordered
    public void calculatePrice(){
        whippedCheck = false;
        chocolateCheck = false;
        if (!whippedCreamCheckBox.isChecked() && !chocolateCheckBox.isChecked()){
            total = quantity * price;
        } else if(whippedCreamCheckBox.isChecked() && !chocolateCheckBox.isChecked()){
            total = quantity * (price + whippedCreamPrice);
            whippedCheck = whippedCreamCheckBox.isChecked();
        } else if (chocolateCheckBox.isChecked() && !whippedCreamCheckBox.isChecked()){
            total = quantity * (price + chocolatePrice);
            chocolateCheck = chocolateCheckBox.isChecked();
        } else if(whippedCreamCheckBox.isChecked() && chocolateCheckBox.isChecked()){
            total = quantity * (price + whippedCreamPrice + chocolatePrice);
            whippedCheck = whippedCreamCheckBox.isChecked();
            chocolateCheck = chocolateCheckBox.isChecked();
        }
    }
}
