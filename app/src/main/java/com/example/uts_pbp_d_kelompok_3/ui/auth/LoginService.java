package com.example.uts_pbp_d_kelompok_3.ui.auth;

import androidx.annotation.NonNull;

import com.example.uts_pbp_d_kelompok_3.model.User;
import com.example.uts_pbp_d_kelompok_3.network.ApiClient;
import com.example.uts_pbp_d_kelompok_3.network.ApiInterface;
import com.example.uts_pbp_d_kelompok_3.network.Response;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginService {
    public void login(LoginView view, String email, String password, LoginCallback callback) {
        Call<Response<User>> call = ApiClient.getClient().create(ApiInterface.class).login(email, password);
        call.enqueue(new Callback<Response<User>>() {
            @Override
            public void onResponse(Call<Response<User>> call, retrofit2.Response<Response<User>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getStatus().equalsIgnoreCase("success")) {
                    User user = response.body().getData();
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                        if (firebaseUser != null && firebaseUser.isEmailVerified()) {
                                            callback.onSuccess(true, user);
                                        } else {
                                            callback.onError();
                                            view.showLoginError("Email belum diverifikasi!");
                                        }
                                    } else {
                                        callback.onError();
                                        view.showLoginError("Otentikasi gagal");
                                    }
                                }
                            });
                } else {
                    callback.onError();
                    view.showLoginError("Login gagal: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Response<User>> call, Throwable t) {
                callback.onError();
                view.showLoginError("Login gagal: " + t.getMessage());
            }
        });
    }

    public Boolean getValid(LoginView view, String email, String password) {
        final Boolean[] bool = new Boolean[1];
        login(view, email, password, new LoginCallback() {
            @Override
            public void onSuccess(boolean value, User user) {
                bool[0] = true;
            }

            @Override
            public void onError() {
                bool[0] = false;
            }
        });
        return bool[0];
    }
}
