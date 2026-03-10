package com.example.cryptodemo;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView priceText;
    private double demoBalance = 1000.0; // Starting demo balance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        priceText = findViewById(R.id.price_text);

        // Simulate crypto price
        simulatePrice();
    }

    private void simulatePrice() {
        // Ye temporary simulation hai, real-time API ke liye baad mein code daalenge
        double price = 30000 + Math.random() * 1000; 
        priceText.setText("BTC Price: $" + String.format("%.2f", price));
    }
}
