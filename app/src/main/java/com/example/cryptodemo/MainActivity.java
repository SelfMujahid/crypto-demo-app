package com.example.cryptodemo;

import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CryptoAdapter adapter;
    private List<Crypto> cryptoList = new ArrayList<>();
    private final Handler handler = new Handler();
    private final int REFRESH_INTERVAL = 5000; // 5 sec

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.cryptoRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CryptoAdapter(cryptoList);
        recyclerView.setAdapter(adapter);

        fetchCryptoData();
    }

    private void fetchCryptoData() {
        new Thread(() -> {
            try {
                URL url = new URL(
                    "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=10&page=1&sparkline=false"
                );
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(3000);
                conn.setReadTimeout(3000);

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) response.append(line);
                reader.close();

                JSONArray arr = new JSONArray(response.toString());

                cryptoList.clear();
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    String name = obj.getString("name");
                    double price = obj.getDouble("current_price");
                    double change = obj.getDouble("price_change_percentage_24h");
                    cryptoList.add(new Crypto(name, price, change));
                }

                runOnUiThread(() -> adapter.notifyDataSetChanged());

            } catch (Exception e) {
                e.printStackTrace();
            }

            handler.postDelayed(this::fetchCryptoData, REFRESH_INTERVAL);
        }).start();
    }
}