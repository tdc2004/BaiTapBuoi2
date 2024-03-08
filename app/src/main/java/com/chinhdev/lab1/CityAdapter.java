package com.chinhdev.lab1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {
    private List<Map<String, Object>> list;

    public CityAdapter(List<Map<String, Object>> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Map<String, Object> city = list.get(position);
        String cityName = city.get("name").toString();
        String country = city.get("country").toString();
        String population = city.get("population").toString();

        holder.textView.setText("City: "+cityName);
        holder.tv2.setText("Country: " + country);
        holder.tv3.setText("Population: " + population);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView,tv2,tv3;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.cityNameTextView);
            tv2=itemView.findViewById(R.id.countryTextView);
            tv3 =itemView.findViewById(R.id.populationTextView);
        }
    }
}
