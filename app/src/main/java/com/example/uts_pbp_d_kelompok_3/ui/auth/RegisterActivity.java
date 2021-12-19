package com.example.uts_pbp_d_kelompok_3.ui.auth;

import static com.example.uts_pbp_d_kelompok_3.PN.MyApplication.CHANNEL_1_ID;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.databinding.DataBindingUtil;

import com.example.uts_pbp_d_kelompok_3.Preferences.UserPreferences;
import com.example.uts_pbp_d_kelompok_3.R;
import com.example.uts_pbp_d_kelompok_3.databinding.ActivityRegisterBinding;
import com.example.uts_pbp_d_kelompok_3.network.ApiClient;
import com.example.uts_pbp_d_kelompok_3.network.ApiInterface;
import com.example.uts_pbp_d_kelompok_3.network.Response;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private UserPreferences userPreferences;
    private NotificationManager notificationManager;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        userPreferences = new UserPreferences(RegisterActivity.this);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateForm()) {
                    register(
                            binding.etName.getText().toString(),
                            binding.etUsername.getText().toString().trim(),
                            binding.etPhoneNumber.getText().toString().trim(),
                            binding.etPassword.getText().toString().trim()
                    );
                }
            }
        });
    }

    private void register(String name, String username, String phone, String password) {

        class RegisterUser extends AsyncTask<Void, Void, Boolean> {

            @Override
            protected Boolean doInBackground(Void... voids) {
                Call<Response<String>> call = ApiClient.getClient().create(ApiInterface.class).register(name, username, phone, password);
                call.enqueue(new Callback<Response<String>>() {
                    @Override
                    public void onResponse(Call<Response<String>> call, retrofit2.Response<Response<String>> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().getStatus().equalsIgnoreCase("success")) {
                            mAuth.createUserWithEmailAndPassword(username, password)
                                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                // Sign in success, update UI with the signed-in user's information
                                                FirebaseUser user = mAuth.getCurrentUser();
                                                if (user != null) {
                                                    user.sendEmailVerification()
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        onPostExecute(true);
                                                                    }
                                                                }
                                                            });
                                                }
                                            } else {
                                                // If sign in fails, display a message to the user.
                                                Toast.makeText(RegisterActivity.this, "Authentication failed: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                                onPostExecute(false);
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(RegisterActivity.this, "Registrasi gagal: " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Response<String>> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "Registrasi gagal: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                return false;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                if (aBoolean) {
                    showNotification();
                    Toast.makeText(RegisterActivity.this, "Berhasil mendaftar", Toast.LENGTH_SHORT).show();
                    clearField();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                }
            }
        }

        RegisterUser registerUser = new RegisterUser();
        registerUser.execute();
    }

    private void clearField() {
        binding.etName.setText("");
        binding.etUsername.setText("");
        binding.etPassword.setText("");
    }


    private boolean validateForm() {
        /* Check username & password is empty or not */
        if (binding.etUsername.getText().toString().trim().isEmpty() || binding.etPassword.getText().toString().trim().isEmpty() || binding.etName.getText().toString().trim().isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Field tidak boleh Kosong", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void showNotification() {
        Intent activityIntent = new Intent(RegisterActivity.this, RegisterActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(RegisterActivity.this,
                0, activityIntent, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupChannels(notificationManager);
        }

        Bitmap picture = BitmapFactory.decodeResource(getResources(), R.drawable.logo_ngirim);

        Notification notification = new NotificationCompat.Builder(RegisterActivity.this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_library_add_check_24)
                .setContentTitle("Registrasi Sukses")
                .setLargeIcon(picture)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(picture)
                        .bigLargeIcon(null))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build();

        notificationManager.notify(1, notification);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupChannels(NotificationManager notificationManager) {
        String channelName = "UTS PBP D Kelompok 3";
        String channelDescription = "UTS PBP D Kelompok 3";

        NotificationChannel adminChannel = new NotificationChannel(CHANNEL_1_ID, channelName, NotificationManager.IMPORTANCE_HIGH);
        adminChannel.setDescription(channelDescription);
        adminChannel.enableLights(true);
        adminChannel.enableVibration(true);
        notificationManager.createNotificationChannel(adminChannel);
    }
}