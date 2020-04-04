package com.example.lesson2layoutmenuadpter;

import java.util.concurrent.TimeUnit;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

//import android.os.Handler;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lesson2layoutmenuadpter.productview.ProductGridFragment;
import com.example.lesson2layoutmenuadpter.utils.CommonUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class LoginFragment extends Fragment {

    //Handler h;
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
                if (!isPasswordValid(passwordEditText.getText())) {
                    passwordTextInput.setError("Пароль має бути мін 8 симаолів");

                } else {
                    passwordTextInput.setError(null);
                    //CommonUtils.showLoading(getActivity());

                    ((NavigationHost) getActivity()).navigateTo(new ProductGridFragment(), true); // Navigate to the next Fragment

                    //h = new Handler();
//                    uploadData();
//                    Intent intent;
//                    intent = new Intent(getActivity(), TestActivity.class);

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


        Button btnRegister = view.findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationHost) getActivity()).navigateTo(new RegisterFragment(), true); // Navigate to the next Fragment
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
                    //h.post(updateProgress);
                    CommonUtils.hideLoading();

                    //((NavigationHost) getActivity()).navigateTo(new SportNewsFragment(), false); // Navigate to the next Fragment
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        return null;
    }
    // обновление ProgressBar
//    Runnable updateProgress = new Runnable() {
//        public void run() {
//            CommonUtils.hideLoading();
//        }
//    };
}
