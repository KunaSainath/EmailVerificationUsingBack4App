package com.back4app_kunasainath.emailverificationusingback4appparseserver;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.back4app_kunasainath.emailverificationusingback4appparseserver.fragments.LogIn;
import com.back4app_kunasainath.emailverificationusingback4appparseserver.fragments.SignUp;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity implements SignUp.SignUpInterface, LogIn.LoginInterface {

    SignUp signUpFragment = SignUp.newInstance();
    LogIn loginFragment = LogIn.newInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ParseUser.getCurrentUser() == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, signUpFragment)
                    .commit();
        }else{
            startActivity(new Intent(this, DashBoardActivity.class));
        }


    }

    @Override
    public void signUpCompleted(String title, String message, final boolean error, final boolean txtLoginClicked) {
        if(txtLoginClicked){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, loginFragment)
                    .addToBackStack(null)
                    .commit();
            
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            if (!error) {
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fragment_container,loginFragment)
                                        .addToBackStack(null)
                                        .commit();
                            }
                        }
                    });
            AlertDialog ok = builder.create();
            ok.show();
        }
        
    }

    @Override
    public void createAccountClicked() {

        getSupportFragmentManager()
                .popBackStack();

    }

    @Override
    public void loginClicked(String title, String message, boolean error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if(!error) {
                            startActivity(new Intent(MainActivity.this, DashBoardActivity.class));
                        }
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }
}