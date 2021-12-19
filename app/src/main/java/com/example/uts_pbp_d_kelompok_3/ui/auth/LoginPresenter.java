package com.example.uts_pbp_d_kelompok_3.ui.auth;

import com.example.uts_pbp_d_kelompok_3.model.User;

public class LoginPresenter {
    private LoginView view;
    private LoginService service;
    private LoginCallback callback;

    public LoginPresenter(LoginView view, LoginService service) {
        this.view = view;
        this.service = service;
    }

    public void onLoginClicked() {
        if (view.getEmail().isEmpty()) {
            view.showLoginError("Email tidak boleh kosong");
        } else if (view.getPassword().isEmpty()) {
            view.showLoginError("Password tidak boleh kosong");
        } else {
            service.login(view, view.getEmail(), view.getPassword(), new LoginCallback() {
                @Override
                public void onSuccess(boolean value, User user) {
                    view.saveUserPreference(user);
                    view.showNotification();
                    view.startMainActivity();
                }

                @Override
                public void onError() {

                }
            });
        }
    }
}
