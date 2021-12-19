package com.example.uts_pbp_d_kelompok_3.ui.auth;

import com.example.uts_pbp_d_kelompok_3.model.User;

public interface LoginView {
    String getEmail();

    void showEmailError(String message);

    String getPassword();

    void showPasswordError(String message);

    void saveUserPreference(User user);

    void showNotification();

    void startMainActivity();

    void showLoginError(String message);

    void showErrorResponse(String message);
}
