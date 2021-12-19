package com.example.uts_pbp_d_kelompok_3.ui.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.uts_pbp_d_kelompok_3.Preferences.UserPreferences;
import com.example.uts_pbp_d_kelompok_3.R;
import com.example.uts_pbp_d_kelompok_3.databinding.FragmentHomeBinding;
import com.example.uts_pbp_d_kelompok_3.model.Delivery;
import com.example.uts_pbp_d_kelompok_3.network.ApiClient;
import com.example.uts_pbp_d_kelompok_3.network.ApiInterface;
import com.example.uts_pbp_d_kelompok_3.network.Response;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private ArrayList<Delivery> deliveries = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            fetchDeliveries(getActivity());
        }
    }

    private void fetchDeliveries(Activity activity) {
        UserPreferences preferences = new UserPreferences(activity);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Response<ArrayList<Delivery>>> call = apiInterface.getDeliveries(preferences.getUserLogin().getId());
        call.enqueue(new Callback<Response<ArrayList<Delivery>>>() {
            @Override
            public void onResponse(Call<Response<ArrayList<Delivery>>> call, retrofit2.Response<Response<ArrayList<Delivery>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getStatus().equalsIgnoreCase("success")) {
                    deliveries = response.body().getData();
                    HomeAdapter adapter = new HomeAdapter(activity, deliveries);
                    binding.recyclerHistory.setLayoutManager(new LinearLayoutManager(activity));
                    binding.recyclerHistory.setAdapter(adapter);
                } else {
                    Toast.makeText(activity, "Gagal memuat data!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response<ArrayList<Delivery>>> call, Throwable t) {
                Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
