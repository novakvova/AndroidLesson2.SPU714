package com.example.lesson2layoutmenuadpter;

import java.io.IOException;
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
import android.widget.TextView;

import com.example.lesson2layoutmenuadpter.account.AccountService;
import com.example.lesson2layoutmenuadpter.account.JwtServiceHolder;
import com.example.lesson2layoutmenuadpter.account.LoginDTO;
import com.example.lesson2layoutmenuadpter.account.LoginDTOBadRequest;
import com.example.lesson2layoutmenuadpter.account.TokenDTO;
import com.example.lesson2layoutmenuadpter.productview.ProductGridFragment;
import com.example.lesson2layoutmenuadpter.utils.CommonUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
        final TextInputLayout emailTextInput = view.findViewById(R.id.email_text_input);
        final TextInputEditText emailEditText = view.findViewById(R.id.email_edit_text);
        TextView errorMessage = view.findViewById(R.id.error_message);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!isPasswordValid(passwordEditText.getText())) {
                    passwordTextInput.setError("Пароль має бути мін 8 симаолів");

                } else {
                    passwordTextInput.setError(null);
                    //CommonUtils.showLoading(getActivity());
                    String email = emailEditText.getText().toString();
                    String password = passwordEditText.getText().toString();
                    LoginDTO loginDTO = new LoginDTO(email, password);
                    errorMessage.setText("");
                    AccountService.getInstance()
                            .getJSONApi()
                            .loginRequest(loginDTO)
                            .enqueue(new Callback<TokenDTO>() {
                                @Override
                                public void onResponse(Call<TokenDTO> call, Response<TokenDTO> response) {
                                    if (response.isSuccessful()){
                                        TokenDTO token = response.body();
                                        Log.i("-----------", token.toString());
                                        ((JwtServiceHolder) getActivity()).saveJWTToken(token.getToken()); // Navigate to the register Fragment
                                        ((NavigationHost) getActivity()).navigateTo(new ProductGridFragment(), false); // Navigate to the products Fragment

                                    }else {
                                        Log.e("------!!!-----", "bad request");
                                        try {
                                            String json = response.errorBody().string();
                                            Gson gson = new Gson();
                                            LoginDTOBadRequest resultBad = gson.fromJson(json, LoginDTOBadRequest.class);
                                            //Log.d(TAG,"++++++++++++++++++++++++++++++++"+response.errorBody().string());
                                            errorMessage.setText(resultBad.getInvalid());

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<TokenDTO> call, Throwable t) {

                                }
                            });
//                    ((NavigationHost) getActivity()).navigateTo(new ProductGridFragment(), true); // Navigate to the next Fragment

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
