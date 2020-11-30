package com.back4app_kunasainath.emailverificationusingback4appparseserver.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.back4app_kunasainath.emailverificationusingback4appparseserver.MainActivity;
import com.back4app_kunasainath.emailverificationusingback4appparseserver.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class LogIn extends Fragment {

    public interface LoginInterface{
        public void createAccountClicked();
        public void loginClicked(String title, String message, final boolean error);
    }

    private EditText edtUsername, edtPassword;
    private TextView txtSignUp;
    private Button btnLogin;

    LoginInterface mLoginInterface;

    public LogIn() {
        // Required empty public constructor
    }

    public static LogIn newInstance() {
        LogIn fragment = new LogIn();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mLoginInterface = (MainActivity) getActivity();

        getActivity().setTitle("Log In");

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginInterface.createAccountClicked();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();

                ParseUser.logInInBackground(username, password, new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {
                        if (parseUser != null) {
                            if(parseUser.getBoolean("emailVerified")) {
                                mLoginInterface.loginClicked("Login Sucessful", "Welcome, " + username + "!", false);

                            }
                            else
                            {
                                ParseUser.logOut();
                                mLoginInterface.loginClicked("Login Fail", "Please Verify Your Email first", true);
                            }
                        } else {
                            ParseUser.logOut();
                            mLoginInterface.loginClicked("Login Fail", e.getMessage() + " Please re-try", true);
                        }
                    }
                });
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);

        edtUsername = view.findViewById(R.id.edt_login_username);
        edtPassword = view.findViewById(R.id.edt_login_password);
        txtSignUp = view.findViewById(R.id.txt_create_account);
        btnLogin = view.findViewById(R.id.btn_login);

        return view;
    }
}