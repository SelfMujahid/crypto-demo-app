package com.example.cryptodemo;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView priceText, balanceText;
    private Button buyButton, sellButton;

    private double demoBalance = 1000.0; // USD
    private double cryptoAmount = 0.0;
    private final String cryptoSymbol = "BTC"; // Change to USDT if you want

    private final Handler handler = new Handler();
    private final int REFRESH_INTERVAL = 5000; // 5 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.os.StrictMode.setThreadPolicy(
        new android.os.StrictMode.ThreadPolicy.Builder().permitAll().build()
);
        setContentView(R.layout.activity_main);

        priceText = findViewById(R.id.priceText);
        balanceText = findViewById(R.id.balanceText);
        buyButton = findViewById(R.id.buyButton);
        sellButton = findViewById(R.id.sellButton);

        updateBalanceText();

        buyButton.setOnClickListener(v -> {
            double price = getCurrentPriceSync();
            if (demoBalance >= price) {
                cryptoAmount += 1.0;
                demoBalance -= price;
                updateBalanceText();
            }
        });

        sellButton.setOnClickListener(v -> {
            double price = getCurrentPriceSync();
            if (cryptoAmount >= 1.0) {
                cryptoAmount -= 1.0;
                demoBalance += price;
                updateBalanceText();
            }
        });

        startPriceUpdates();
    }

    private void updateBalanceText() {
        balanceText.setText(String.format("Balance: $%.2f | %s: %.4f", demoBalance, cryptoSymbol, cryptoAmount));
    }

    private void startPriceUpdates() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                double price = getCurrentPriceSync();
                priceText.setText(String.format("%s Price: $%.2f", cryptoSymbol, price));
                handler.postDelayed(this, REFRESH_INTERVAL);
            }
        }, 0);
    }

    private double getCurrentPriceSync() {
        try {
            URL url = new URL("https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=usd");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) response.append(line);
            reader.close();

            JSONObject obj = new JSONObject(response.toString());
            return obj.getJSONObject("bitcoin").getDouble("usd");

        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }
}
