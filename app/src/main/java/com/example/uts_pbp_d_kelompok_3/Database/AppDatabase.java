package com.example.uts_pbp_d_kelompok_3.Database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.uts_pbp_d_kelompok_3.Dao.UserDao;
import com.example.uts_pbp_d_kelompok_3.model.User;


@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

}
