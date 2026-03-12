package com.example.cryptodemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CryptoAdapter extends RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder> {

    private List<Crypto> cryptoList;

    public CryptoAdapter(List<Crypto> cryptoList) {
        this.cryptoList = cryptoList;
    }

    @Override
    public CryptoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.crypto_item, parent, false);
        return new CryptoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CryptoViewHolder holder, int position) {
        Crypto crypto = cryptoList.get(position);
        holder.name.setText(crypto.name);
        holder.price.setText(String.format("$%.2f", crypto.price));
        holder.change.setText(String.format("%.2f%%", crypto.change24h));
        holder.change.setTextColor(crypto.change24h >= 0 ? 0xFF4CAF50 : 0xFFF44336); // green/red
    }

    @Override
    public int getItemCount() {
        return cryptoList.size();
    }

    public static class CryptoViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, change;

        public CryptoViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.coinName);
            price = itemView.findViewById(R.id.coinPrice);
            change = itemView.findViewById(R.id.coinChange);
        }
    }
}