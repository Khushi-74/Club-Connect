package com.example.clubconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class userActions extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.useractions);

        ImageButton viewclubbtn = findViewById(R.id.ViewClubimageButton);
        ImageButton vieweventbtn = findViewById(R.id.ViewEventimageButton);

        viewclubbtn.setOnClickListener(v -> {
            Intent collegeclubs = new Intent(userActions.this, UserActivity.class);
            startActivity(collegeclubs);
        });
        vieweventbtn.setOnClickListener(v -> {
            Intent collegeevents = new Intent(userActions.this, EventView.class);
            startActivity(collegeevents);
        });
        ImageButton logoutbtn = findViewById(R.id.logoutbtn);
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(userActions.this,MainActivity.class);
                startActivity(in);
            }
        });
    }

}
