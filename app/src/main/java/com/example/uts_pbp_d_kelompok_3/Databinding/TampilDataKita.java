//package com.example.uts_pbp_d_kelompok_3.Databinding;
//import android.annotation.SuppressLint;
//import android.os.Bundle;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.databinding.DataBindingUtil;
//import androidx.recyclerview.widget.DefaultItemAnimator;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//
//import com.example.uts_pbp_d_kelompok_3.R;
//import com.example.uts_pbp_d_kelompok_3.databinding.ActivityRecyclerViewAdapterBinding;
//
//import java.util.ArrayList;
//
//public class TampilDataKita extends AppCompatActivity {
//    private ArrayList<DataKita> ListDataPeserta;
//    private RecyclerView recyclerView;
//    private RecyclerViewAdapter adapter;
//    private RecyclerView.LayoutManager mLayoutManager;
//    private ActivityRecyclerViewAdapterBinding binding;
//    private RecyclerView recyclerViewPeserta;
//    @SuppressLint("WrongViewCast")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_tampil_data);
//        ListDataPeserta = new DaftarKita().DataKita;
//        //recycler view
////        recyclerView = binding.;
//        adapter = new RecyclerViewAdapter(TampilDataKita.this, ListDataPeserta);
//        mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(adapter);
//    }
//}