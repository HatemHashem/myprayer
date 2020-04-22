package com.example.myprayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PrayerAdapter extends RecyclerView.Adapter<PrayerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Prayer> prayers;

    public PrayerAdapter(Context context) {
        this.context = context;
       prayers=new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.prayer_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Prayer currentPrayer=prayers.get(position);
        holder.bindTo(currentPrayer);

    }
    public void setPrayer(Prayer prayer){
        prayers.add(prayer);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return prayers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView prayerName;
        private TextView prayerTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            prayerName=itemView.findViewById(R.id.prayer_name);
            prayerTime=itemView.findViewById(R.id.prayer_time);
        }
        public void bindTo(Prayer currentPrayer){
            prayerName.setText(currentPrayer.getPrayer());
            prayerTime.setText(currentPrayer.getPrayerTime());
        }
    }
}
