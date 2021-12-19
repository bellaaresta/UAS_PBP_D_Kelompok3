package com.example.uts_pbp_d_kelompok_3;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.uts_pbp_d_kelompok_3.Preferences.UserPreferences;
import com.example.uts_pbp_d_kelompok_3.databinding.ActivityMainBinding;
import com.example.uts_pbp_d_kelompok_3.ui.auth.LoginActivity;
import com.example.uts_pbp_d_kelompok_3.ui.main.HomeFragment;
import com.example.uts_pbp_d_kelompok_3.ui.main.RateFragment;
import com.example.uts_pbp_d_kelompok_3.ui.main.TrackingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private UserPreferences userPreferences;
    private NotificationManagerCompat notificationManager;
    private MediaSessionCompat mediaSession;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        notificationManager = NotificationManagerCompat.from(this);
        userPreferences = new UserPreferences(this);

        mediaSession = new MediaSessionCompat(this, "tag");

        changeFragment(new HomeFragment());
        setTitle("Home");

        binding.bottomNavigation.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    changeFragment(new HomeFragment());
                    setTitle("Home");
                } else if (item.getItemId() == R.id.shippingRate) {
                    changeFragment(new RateFragment());
                    setTitle("Shipping Rate");
                } else if (item.getItemId() == R.id.tracking) {
                    changeFragment(new TrackingFragment());
                    setTitle("Tracking");
                } else if (item.getItemId() == R.id.menu_logout) {
                    onOptionsItemSelected(item);
                }
                return true;
            }
        });
    }

    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_fragment, fragment)
                .commit();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Disini kita menghubungkan menu yang telah kita buat dengan activity ini
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.home_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home) {
            changeFragment(new HomeFragment());
            setTitle("Home");
            return true;
        } else if (item.getItemId() == R.id.shippingRate) {
            changeFragment(new RateFragment());
            setTitle("Shipping Rate");
            return true;
        } else if (item.getItemId() == R.id.tracking) {
            changeFragment(new TrackingFragment());
            setTitle("Tracking");
            return true;
        } else if (item.getItemId() == R.id.menu_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Apakah mau melanjutkan logout?")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            userPreferences.logout();
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            finishAndRemoveTask();
                        }
                    })
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    public View.OnClickListener btnAboutUs = new View.OnClickListener(){
//        @Override
//        public void onClick(View view){
//            Intent mainActivity = new Intent(MainActivity.this, TampilDataKita.class);
//            startActivity(mainActivity);
//        }
//    };

}
