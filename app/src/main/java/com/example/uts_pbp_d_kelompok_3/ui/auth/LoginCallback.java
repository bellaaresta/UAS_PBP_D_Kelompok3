package com.example.uts_pbp_d_kelompok_3.ui.auth;

import com.example.uts_pbp_d_kelompok_3.model.User;

public interface LoginCallback {
    void onSuccess(boolean value, User user);

    void onError();
}
