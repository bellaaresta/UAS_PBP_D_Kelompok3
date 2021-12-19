package com.example.uts_pbp_d_kelompok_3.ui.main;

import static android.content.Context.CLIPBOARD_SERVICE;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.uts_pbp_d_kelompok_3.Preferences.UserPreferences;
import com.example.uts_pbp_d_kelompok_3.R;
import com.example.uts_pbp_d_kelompok_3.network.ApiClient;
import com.example.uts_pbp_d_kelompok_3.network.ApiInterface;
import com.example.uts_pbp_d_kelompok_3.network.Response;
import com.google.android.material.textfield.TextInputEditText;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;

public class RateFragment extends Fragment {
    int price = 0;
    UserPreferences preferences;

    public RateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rate, container, false);
        preferences = new UserPreferences(requireContext());
        String[] origin = {"Jogjakarta", "Bali", "Jakarta", "Bandung"};
        String[] destination = {"Jogjakarta", "Bali", "Jakarta", "Bandung"};

        Button buttonCek = view.findViewById(R.id.cekTarif);
        Button buttonOrder = view.findViewById(R.id.order);
        TextView textViewTarif = view.findViewById(R.id.hasilTarif);
        AutoCompleteTextView autoCompleteTextView, autoCompleteTextView1;
        TextView textResi = view.findViewById(R.id.textResi);

        ArrayAdapter<String> itemAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_item, origin);
        ArrayAdapter<String> itemAdapter1 = new ArrayAdapter<>(getActivity(), R.layout.list_item, destination);

        TextInputEditText weightInput = view.findViewById(R.id.weightInput);
        autoCompleteTextView = view.findViewById(R.id.asal);
        autoCompleteTextView1 = view.findViewById(R.id.tujuan);

        autoCompleteTextView.setAdapter(itemAdapter);
        autoCompleteTextView1.setAdapter(itemAdapter1);
        buttonCek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String origin = autoCompleteTextView.getText().toString();
                String destination = autoCompleteTextView1.getText().toString();

                if (origin.isEmpty() || destination.isEmpty()) {
                    Toast.makeText(requireContext(), "Pilih asal dan tujuan!", Toast.LENGTH_SHORT).show();
                    return;
                }

                price = new Random().nextInt(40001) + 10000;
                NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
                textViewTarif.setText(formatter.format(price) + "/kg");

                buttonOrder.setVisibility(View.VISIBLE);
                weightInput.setVisibility(View.VISIBLE);
            }
        });
        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String origin = autoCompleteTextView.getText().toString();
                String destination = autoCompleteTextView1.getText().toString();

                if (origin.isEmpty() || destination.isEmpty()) {
                    Toast.makeText(requireContext(), "Pilih asal dan tujuan!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (weightInput.getText().toString().isEmpty()) {
                    Toast.makeText(requireContext(), "Masukkan berat!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int weight = Integer.parseInt(weightInput.getText().toString());

                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<Response<String>> call = apiInterface.createDelivery(
                        preferences.getUserLogin().getId(), origin, destination, weight, price
                );
                call.enqueue(new Callback<Response<String>>() {
                    @Override
                    public void onResponse(Call<Response<String>> call, retrofit2.Response<Response<String>> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().getStatus().equalsIgnoreCase("success")) {
                            Toast.makeText(requireContext(), "Berhasil membuat permintaan!", Toast.LENGTH_SHORT).show();
                            String resi = response.body().getData();
                            textResi.setText("Resi: " + resi + "\n(Tekan untuk menyalin)");
                            textResi.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
                                    ClipData clip = ClipData.newPlainText("resi", resi);
                                    clipboard.setPrimaryClip(clip);
                                }
                            });
                        } else {
                            Toast.makeText(requireContext(), "Gagal membuat permintaan!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Response<String>> call, Throwable t) {
                        Toast.makeText(requireContext(), "Gagal membuat permintaan!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        // Inflate the layout for this fragment
        return view;
    }


}