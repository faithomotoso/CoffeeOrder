package com.example.android.coffeeorder;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class OrderSummary extends MainActivity {

    NumberFormat naira;
    DecimalFormatSymbols nairaSymbol;
    static String topping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        ImageView coffee = findViewById(R.id.coffeeImage);
//        if(whippedCreamCheckBox.isChecked()){
//            coffee.setImageResource(R.drawable.coffeewhipped);
//        } else {
//            coffee.setImageResource(R.drawable.coffeetransp);
//        }

        // naira symbol
        naira = NumberFormat.getCurrencyInstance();
        nairaSymbol = new DecimalFormatSymbols();
        nairaSymbol.setCurrencySymbol("₦");
        nairaSymbol.setGroupingSeparator(',');
        ((DecimalFormat) naira).setDecimalFormatSymbols(nairaSymbol);

        // run the showOrderSummary method when the activity is launched
        showOrderSummary();
    }

    /*
        the showOrderSummary method displays the order summary
        self explanatory
     */
    public void showOrderSummary(){
        // name
        TextView nameView = findViewById(R.id.name);
        nameView.setText(name);

        // price
        TextView priceView = findViewById(R.id.price);
        priceView.setText(naira.format(total));

        // description
        TextView descriptionView = findViewById(R.id.description);
        String coffee = getString(R.string.coffee);
        if (quantity > 1){
            coffee = getString(R.string.coffees);
        }

        topping = getString(R.string.no_toppings);
        if(whippedCheck && !chocolateCheck){
            topping = getString(R.string.whipped_cream).toLowerCase();
        } else if (chocolateCheck && !whippedCheck){
            topping = getString(R.string.chocolate_topping);
        } else if (whippedCheck && chocolateCheck){
            topping = getString(R.string.whipped_cream_chocolate);
        }

        String description = quantity + coffee + getString(R.string.with) + topping + getString(R.string.for_name, name);
        descriptionView.setText(description);
    }

    public void placeOrder(View view){
        Toast sendingEmail = Toast.makeText(getApplicationContext(), getString(R.string.sending_order), Toast.LENGTH_SHORT);

        String subject = getString(R.string.subject_email, name);
        String emailText = getString(R.string.name_email, name) + "\n\n" + getString(R.string.toppings_email, topping.replace(getString(R.string.toppings), "").replace(getString(R.string.topping), "").replaceAll("\\s+", " "))
                + "\n\n" + getString(R.string.quantity_email, quantity)
                + "\n\n" + getString(R.string.total_email, naira.format(total))
                + "\n\n" + getString(R.string.thank_you);
        String [] addresses = new String[1];
        addresses[0] = "order@coffee.xyz";

        Intent sendEmail = new Intent(Intent.ACTION_SENDTO);
        sendEmail.putExtra(Intent.EXTRA_EMAIL, addresses)
                .putExtra(Intent.EXTRA_SUBJECT, subject)
                .putExtra(Intent.EXTRA_TEXT, emailText)
                .setType("message/rfc822")
                .setData(Uri.parse("mailto:"));
        if(sendEmail.resolveActivity(getPackageManager()) != null){
            sendingEmail.show();
            startActivity(sendEmail);
        }
    }
}
