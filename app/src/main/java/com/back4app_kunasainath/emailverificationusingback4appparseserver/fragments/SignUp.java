package com.back4app_kunasainath.emailverificationusingback4appparseserver.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.back4app_kunasainath.emailverificationusingback4appparseserver.MainActivity;
import com.back4app_kunasainath.emailverificationusingback4appparseserver.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUp extends Fragment {

    public interface SignUpInterface{
        public void signUpCompleted(String title, String message, final boolean error, final boolean txtLoginClicked);
    }

    private SignUpInterface mSignUpInterface;

    private EditText edtEmail, edtUsername, edtCreatePassword, edtConfirmPassword;
    private Button btnSignUp;
    private TextView txtLogin;

    public SignUp() {
        // Required empty public constructor
    }

    public static SignUp newInstance() {
        SignUp fragment = new SignUp();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mSignUpInterface = (MainActivity) getActivity();

        getActivity().setTitle(getString(R.string.sign_up));

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSignUpInterface.signUpCompleted(null, null, false, true);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String username = edtUsername.getText().toString();
                String createdPassword = edtCreatePassword.getText().toString();
                String confirmedPassword = edtConfirmPassword.getText().toString();

                if(email.length() == 0){
                    edtEmail.setError("Invalid email");
                    return;
                }

                if(username.length() == 0){
                    edtUsername.setError("Invalid username");
                    return;
                }

                if(createdPassword.length() <= 5){
                    edtCreatePassword.setError("Password minimum length should be 6 letters");
                    return;
                }

                if(!createdPassword.equals(confirmedPassword)){
                    Toast.makeText(getActivity(), "Created password is not matched with confirmed password", Toast.LENGTH_LONG).show();
                    return;
                }




                //PARSE SERVER EMAIL VERIFICATION SETUP

                try {
                    // Sign up with Parse
                    ParseUser user = new ParseUser();
                    user.setUsername(username);
                    user.setPassword(createdPassword);
                    user.setEmail(email);

                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                ParseUser.logOut();
                                mSignUpInterface.signUpCompleted("Account Created Successfully!", "Please verify your email before Login", false, false);
                            } else {
                                ParseUser.logOut();
                                mSignUpInterface.signUpCompleted("Error Account Creation failed", "Account could not be created" + " :" + e.getMessage(), true, false);
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        edtEmail = view.findViewById(R.id.edt_email);
        edtUsername = view.findViewById(R.id.edt_username);
        edtCreatePassword = view.findViewById(R.id.edt_create_password);
        edtConfirmPassword = view.findViewById(R.id.edt_confirm_password);
        btnSignUp = view.findViewById(R.id.btn_signup);
        txtLogin = view.findViewById(R.id.txt_login);

        return view;
    }
}