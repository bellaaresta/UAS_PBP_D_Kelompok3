package com.example.uts_pbp_d_kelompok_3;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.uts_pbp_d_kelompok_3.databinding.TrackingLayoutBinding;
import com.example.uts_pbp_d_kelompok_3.model.Delivery;
import com.example.uts_pbp_d_kelompok_3.model.History;

import java.util.ArrayList;
import java.util.Objects;

public class TampilTracking extends AppCompatActivity {
    public static final String DELIVERY = "delivery";
    public static final String HISTORIES = "histories";
    private TrackingLayoutBinding binding;
    private TrackingPreferences trackingPreferences;
    private Delivery delivery;
    private ArrayList<History> histories = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.tracking_layout);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (!getIntent().hasExtra(DELIVERY)) {
            finish();
        }
        delivery = getIntent().getParcelableExtra(DELIVERY);
        histories = getIntent().getParcelableArrayListExtra(HISTORIES);

        binding.textResi.setText("Nomor Resi #" + delivery.getResi());
        binding.tanggal.setText(delivery.getDate());
        if (histories != null) {
            binding.status.setText(histories.get(histories.size() - 1).getStatus());
            for (History history : histories) {
                TextView textView = new TextView(this);
                textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView.setText(history.getDate() + " \n" + history.getStatus());
                binding.layoutHistory.addView(textView);
            }
        }

        binding.btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TampilTracking.this, MainActivity.class));
                finish();
            }
        });

        binding.btnGeolocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TampilTracking.this, MapsActivity.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}