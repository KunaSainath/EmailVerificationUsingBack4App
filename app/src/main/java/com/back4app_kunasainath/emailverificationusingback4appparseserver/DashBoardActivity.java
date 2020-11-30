package com.back4app_kunasainath.emailverificationusingback4appparseserver;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseUser;

public class DashBoardActivity extends AppCompatActivity {

    private TextView txtUserWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        txtUserWelcome = findViewById(R.id.txt_user_welcome);

        txtUserWelcome.setText("WELCOME, " + ParseUser.getCurrentUser().getUsername());

        setTitle("Dashboard");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_logout){
            ParseUser.logOut();
            startActivity(new Intent(this, MainActivity.class));
            Toast.makeText(this, "Logged out.", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}