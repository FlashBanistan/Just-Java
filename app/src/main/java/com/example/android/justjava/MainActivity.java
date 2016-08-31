package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method creates an order summary
     */
    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String name){

        String summary =    "Name: " + name + "\n" +
                            "Add whipped cream? " + hasWhippedCream + "\n" +
                            "Add chocolate? " + hasChocolate + "\n" +
                            "Quantity: " + quantity + "\n" +
                            "Total $: " + price + "\n" +
                            "Thank you!";
        Intent sendOrder = new Intent(Intent.ACTION_SENDTO);
        sendOrder.setData(Uri.parse("mailto:")); //only email apps will handle this
        sendOrder.putExtra(Intent.EXTRA_EMAIL, "FlashBanistan66@gmail.com");
        sendOrder.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        sendOrder.putExtra(Intent.EXTRA_TEXT, summary);
        if(sendOrder.resolveActivity(getPackageManager()) != null) {
            startActivity(sendOrder);
        }

        return summary;
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText findName = (EditText) findViewById(R.id.name_field);
        String name = findName.getText().toString();
        CheckBox hasWhippedCream = (CheckBox) findViewById(R.id.whipped_cream);
        CheckBox hasChocolate = (CheckBox) findViewById(R.id.chocolate);
        boolean whippedCream = hasWhippedCream.isChecked();
        boolean chocolate = hasChocolate.isChecked();
        int price = calculatePrice(whippedCream, chocolate);
        String orderSummary = createOrderSummary(price, whippedCream, chocolate, name);
    }

    /**
     * Increment method
     */
    public void increment(View view){
        if(quantity == 100) {
            Toast.makeText(MainActivity.this, "Can't go above 100 cups of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * Decrement Method
     */
    public void decrement(View view){
        if(quantity == 1) {
            Toast.makeText(MainActivity.this, "Can't go below 1 cup of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * Calculates the price of the order.
     *
     * @param hasWhippedCream is whether or not user wants to add whipped cream
     * @param hasChocolate is whether or not user wants to add chocolate
     * @return price
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int basePrice = 5;
        if(hasWhippedCream) basePrice+=1;
        if(hasChocolate) basePrice+=2;

        return quantity * basePrice;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }





}
