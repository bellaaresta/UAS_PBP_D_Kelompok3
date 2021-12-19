//package com.example.uts_pbp_d_kelompok_3.Databinding;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//
//import com.example.uts_pbp_d_kelompok_3.R;
//import com.example.uts_pbp_d_kelompok_3.databinding.ActivityRecyclerViewAdapterBinding;
//
//import java.util.List;
//
//public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
//    private Context context;
//    private List<DataKita> result;
//
//
//    public RecyclerViewAdapter(Context context, List<DataKita> result){
//        this.context = context;
//        this.result = result;
//
//    }
//
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        ActivityRecyclerViewAdapterBinding binding = ActivityRecyclerViewAdapterBinding.inflate(layoutInflater, parent, false);
//        return new MyViewHolder(binding);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
//        final DataKita pst = result.get(position);
//        holder.bind(pst);
//    }
//
//    @Override
//    public int getItemCount() {
//        return result.size();
//    }
//
//    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//        public ActivityRecyclerViewAdapterBinding binding;
//
//        public MyViewHolder(@NonNull ActivityRecyclerViewAdapterBinding binding){
//            super(binding.getRoot());
//            this.binding = binding;
//        }
//
//        public void bind(DataKita Peserta) {
//            binding.setAbout(Peserta);
//            binding.setImgUrl(Peserta.pic);
//            binding.executePendingBindings();
//        }
//        public void onClick(View view) {
//            Toast.makeText(context, "You touch me?", Toast.LENGTH_SHORT).show();
//        }
//    }
//}
