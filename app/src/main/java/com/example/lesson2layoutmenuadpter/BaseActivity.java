package com.example.lesson2layoutmenuadpter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.lesson2layoutmenuadpter.application.MyApplication;

public abstract class BaseActivity extends AppCompatActivity implements NavigationHost {

    public BaseActivity() {
        MyApplication myApp = (MyApplication)MyApplication.getAppContext();
        myApp.setCurrentActivity(this);
    }

    @Override
    public void navigateTo(Fragment fragment, boolean addToBackstack) {
        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, fragment);

        if (addToBackstack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

}
