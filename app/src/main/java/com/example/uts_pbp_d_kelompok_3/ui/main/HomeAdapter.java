package com.example.uts_pbp_d_kelompok_3.ui.main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uts_pbp_d_kelompok_3.TampilTracking;
import com.example.uts_pbp_d_kelompok_3.databinding.ItemDeliveryBinding;
import com.example.uts_pbp_d_kelompok_3.model.Delivery;
import com.example.uts_pbp_d_kelompok_3.network.ApiClient;
import com.example.uts_pbp_d_kelompok_3.network.ApiInterface;
import com.example.uts_pbp_d_kelompok_3.network.Response;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Delivery> deliveries = new ArrayList<>();

    public HomeAdapter(Context context, ArrayList<Delivery> deliveries) {
        this.context = context;
        this.deliveries = deliveries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemDeliveryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Delivery delivery = deliveries.get(position);
        holder.binding.setDelivery(delivery);
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<Response<Delivery>> call = apiInterface.detailDelivery(delivery.getResi());
                call.enqueue(new Callback<Response<Delivery>>() {
                    @Override
                    public void onResponse(Call<Response<Delivery>> call, retrofit2.Response<Response<Delivery>> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().getStatus().equalsIgnoreCase("success")) {
                            Intent intent = new Intent(context, TampilTracking.class);
                            intent.putExtra(TampilTracking.DELIVERY, response.body().getData());
                            intent.putExtra(TampilTracking.HISTORIES, response.body().getData().getHistories());
                            context.startActivity(intent);
                        } else {
                            Toast.makeText(context, "Tidak ditemukan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Response<Delivery>> call, Throwable t) {
                        Toast.makeText(context, "Tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return deliveries.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemDeliveryBinding binding;

        public ViewHolder(@NonNull ItemDeliveryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
