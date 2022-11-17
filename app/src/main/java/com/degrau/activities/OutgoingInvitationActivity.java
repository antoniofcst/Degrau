package com.degrau.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.degrau.R;
import com.degrau.models.User;

public class OutgoingInvitationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outgoing_invitation);

        ImageView imageMeetingType = findViewById(R.id.imageMeetingType);
        String meetingType = getIntent().getStringExtra("type");

        if ( meetingType != null) {
            imageMeetingType.setImageResource(R.drawable.ic_video);
        }
        TextView textFirstChar = findViewById(R.id.textFirstChar);
        TextView textUsername = findViewById(R.id.textUsername);
        TextView textEmail = findViewById(R.id.textEmail);

        User user = (User) getIntent().getSerializableExtra("user");
        if(user != null) {
            textFirstChar.setText(user.nomeCompleto.substring(0,1));
            textUsername.setText(String.format("%s", user.nomeCompleto));
            textEmail.setText(user.email);
        }
        ImageView imageStopInvitation = findViewById(R.id.imageRejectInvitation);
        imageStopInvitation.setOnClickListener(view -> {
            onBackPressed();
        });
    }

}