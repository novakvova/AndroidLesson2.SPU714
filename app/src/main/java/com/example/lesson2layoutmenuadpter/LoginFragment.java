package com.example.lesson2layoutmenuadpter;

import java.util.concurrent.TimeUnit;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lesson2layoutmenuadpter.utils.CommonUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class LoginFragment extends Fragment {

    Handler h;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Button loginButton = view.findViewById(R.id.btn_login);
        final TextInputLayout passwordTextInput = view.findViewById(R.id.password_text_input);
        final TextInputEditText passwordEditText = view.findViewById(R.id.password_edit_text);


        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //CommonUtils.hideLoading();
                if (!isPasswordValid(passwordEditText.getText())) {
                    passwordTextInput.setError("Пароль має бути мін 8 симаолів");

                } else {
                    passwordTextInput.setError(null);
                    CommonUtils.showLoading(getActivity());
                    //h = new Handler();
                    uploadData();

                }
            }
        });

        // Clear the error once more than 8 characters are typed.
        passwordEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (isPasswordValid(passwordEditText.getText())) {
                    passwordTextInput.setError(null); //Clear the error
                    //return true;
                }
                return false;
            }
        });

        return view;
    }

    private boolean isPasswordValid(@Nullable Editable text) {
        return text != null && text.length() >= 8;
    }

    public String uploadData() {
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    Log.d("Loging", "=---------Hello--------");
                    TimeUnit.MILLISECONDS.sleep(1000);
                    // обновляем ProgressBar
                    // обновляем ProgressBar
                    //h.post(updateProgress);
                    CommonUtils.hideLoading();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        return null;
    }
    // обновление ProgressBar
    Runnable updateProgress = new Runnable() {
        public void run() {
            CommonUtils.hideLoading();
        }
    };
}
