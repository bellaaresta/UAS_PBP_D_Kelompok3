package com.example.uts_pbp_d_kelompok_3.ui.main;

import static android.app.Activity.RESULT_OK;
import static com.example.uts_pbp_d_kelompok_3.ui.ScanActivity.REQUEST_SCAN;
import static com.example.uts_pbp_d_kelompok_3.ui.ScanActivity.RESI;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.uts_pbp_d_kelompok_3.R;
import com.example.uts_pbp_d_kelompok_3.TampilTracking;
import com.example.uts_pbp_d_kelompok_3.databinding.FragmentTrackingBinding;
import com.example.uts_pbp_d_kelompok_3.model.Delivery;
import com.example.uts_pbp_d_kelompok_3.network.ApiClient;
import com.example.uts_pbp_d_kelompok_3.network.ApiInterface;
import com.example.uts_pbp_d_kelompok_3.network.Response;
import com.example.uts_pbp_d_kelompok_3.ui.ScanActivity;

import retrofit2.Call;
import retrofit2.Callback;

public class TrackingFragment extends Fragment {
    private FragmentTrackingBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tracking, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            binding.btnCek.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (validateForm()) {
                        String resi = binding.NoResi.getText().toString().trim();
                        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                        Call<Response<Delivery>> call = apiInterface.detailDelivery(resi);
                        call.enqueue(new Callback<Response<Delivery>>() {
                            @Override
                            public void onResponse(Call<Response<Delivery>> call, retrofit2.Response<Response<Delivery>> response) {
                                if (response.isSuccessful() && response.body() != null && response.body().getStatus().equalsIgnoreCase("success")) {
                                    Intent intent = new Intent(requireContext(), TampilTracking.class);
                                    intent.putExtra(TampilTracking.DELIVERY, response.body().getData());
                                    intent.putExtra(TampilTracking.HISTORIES, response.body().getData().getHistories());
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(requireContext(), "Tidak ditemukan", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Response<Delivery>> call, Throwable t) {
                                Toast.makeText(requireContext(), "Tidak ditemukan", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });

            binding.btnScan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(new Intent(requireContext(), ScanActivity.class), REQUEST_SCAN);
                }
            });
        }
    }

    private boolean validateForm() {
        if (binding.NoResi.getText().toString().trim().isEmpty()) {
            Toast.makeText(requireContext(), "Resi Kosong", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SCAN && resultCode == RESULT_OK && data != null) {
            String resi = data.getStringExtra(RESI);
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<Response<Delivery>> call = apiInterface.detailDelivery(resi);
            call.enqueue(new Callback<Response<Delivery>>() {
                @Override
                public void onResponse(Call<Response<Delivery>> call, retrofit2.Response<Response<Delivery>> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().getStatus().equalsIgnoreCase("success")) {
                        Intent intent = new Intent(requireContext(), TampilTracking.class);
                        intent.putExtra(TampilTracking.DELIVERY, response.body().getData());
                        intent.putExtra(TampilTracking.HISTORIES, response.body().getData().getHistories());
                        startActivity(intent);
                    } else {
                        Toast.makeText(requireContext(), "Tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Response<Delivery>> call, Throwable t) {
                    Toast.makeText(requireContext(), "Tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
