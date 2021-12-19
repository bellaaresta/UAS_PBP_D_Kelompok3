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
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;

import com.example.uts_pbp_d_kelompok_3.MainActivity;
import com.example.uts_pbp_d_kelompok_3.Preferences.UserPreferences;
import com.example.uts_pbp_d_kelompok_3.R;
import com.example.uts_pbp_d_kelompok_3.databinding.ActivityLoginBinding;
import com.example.uts_pbp_d_kelompok_3.model.User;

public class LoginActivity extends AppCompatActivity implements LoginView {
    private ActivityLoginBinding binding;
    private UserPreferences userPreferences;
    private NotificationManager notificationManager;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        userPreferences = new UserPreferences(LoginActivity.this);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        presenter = new LoginPresenter(this, new LoginService());
        /* Apps will check the login first from shared preferences */
        checkLogin();

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });

        /* to clear the field just set text to "" */
        binding.btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.inputLayoutUsername.setText("");
                binding.inputLayoutPassword.setText("");
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateForm()) {
                    presenter.onLoginClicked();
                }
            }
        });

    }

    private boolean validateForm() {
        /* Check username & password is empty or not */
        if (binding.inputLayoutUsername.getText().toString().trim().isEmpty() || binding.inputLayoutPassword.getText().toString().trim().isEmpty()) {
            Toast.makeText(LoginActivity.this, "Username Atau Password Kosong", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void checkLogin() {
        if (userPreferences.checkLogin()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    public String getEmail() {
        return binding.inputLayoutUsername.getText().toString().trim();
    }

    @Override
    public void showEmailError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getPassword() {
        return binding.inputLayoutPassword.getText().toString().trim();
    }

    @Override
    public void showPasswordError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void saveUserPreference(User user) {
        UserPreferences userPreferences = new UserPreferences(this);
        userPreferences.setUser(user.getId(), user.getName(), user.getUsername(), user.getPassword());
    }

    @Override
    public void showNotification() {
        Intent activityIntent = new Intent(LoginActivity.this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(LoginActivity.this,
                0, activityIntent, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupChannels(notificationManager);
        }

        Bitmap picture = BitmapFactory.decodeResource(getResources(), R.drawable.logo_ngirim);

        Notification notification = new NotificationCompat.Builder(LoginActivity.this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_library_add_check_24)
                .setContentTitle("Berhasil Login")
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

    @Override
    public void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void showLoginError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorResponse(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onStart() {
        super.onStart();
        UserPreferences userPreferences = new UserPreferences(this);
        if (userPreferences.checkLogin()) {
            startMainActivity();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        UserPreferences userPreferences = new UserPreferences(this);
        if (userPreferences.checkLogin()) {
            startMainActivity();
        }
    }
}