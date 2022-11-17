package com.degrau.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.degrau.R;
import com.degrau.network.ApiClient;
import com.degrau.network.ApiService;
import com.degrau.utilities.Constrants;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IncomingInvitationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_invitation);

        ImageView imageMeetingType = findViewById(R.id.imageAcceptInvitation);
        String meetingType = getIntent().getStringExtra(Constrants.REMOTE_MSG_MEETING_TYPE);
        if(meetingType != null) {
            if(meetingType.equals("video")) {
                imageMeetingType.setImageResource(R.drawable.ic_video);
            }
        }
        TextView textFirstChar = findViewById(R.id.textFirstChar);
        TextView textUsername = findViewById(R.id.textUsername);
        TextView textEmail = findViewById(R.id.textEmail);

        String nomeCompleto = getIntent().getStringExtra(Constrants.KEY_NOMECOMPLETO);
        if(textFirstChar != null){
            textUsername.setText(nomeCompleto.substring(0,1));
        }
        textEmail.setText(getIntent().getStringExtra(Constrants.KEY_EMAIL));
        ImageView imageAcceptInvitation = findViewById(R.id.imageAcceptInvitation);
        imageAcceptInvitation.setOnClickListener(v -> sendInitationResponse(
                Constrants.REMOTE_MSG_INVITATION_REJECTED,
                getIntent().getStringExtra(Constrants.REMOTE_MSG_INVITER_TOKEN)
                )
        );
    }

    private void sendInitationResponse(String type, String reciverToken){
            try {
                JSONArray tokens = new JSONArray();
                tokens.put(reciverToken);

                JSONObject body = new JSONObject();
                JSONObject data = new JSONObject();

                data.put(Constrants.REMOTE_MSG_MEETING_TYPE, Constrants.REMOTE_MSG_INVITATION_RESPONSE);
                data.put(Constrants.REMOTE_MSG_INVITATION_RESPONSE, type);

                data.put(Constrants.REMOTE_MSG_DATA, data);
                body.put(Constrants.REMOTE_MSG_REGISTRATION_IDS, tokens);

                sendRemoteMessage(body.toString(), type);
            }catch (Exception exception){
                Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
    }
    private void sendRemoteMessage(String remoteMessageBody, String type) {

        ApiClient.getClient().create(ApiService.class).sendRemoteMessage(
                Constrants.getRemoteMessageHeaders(), remoteMessageBody
        ).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if(response.isSuccessful()) {
                    if(type.equals(Constrants.REMOTE_MSG_INVITATION_ACCEPTED)){
                        Toast.makeText(IncomingInvitationActivity.this, "Invitation Accepted", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(IncomingInvitationActivity.this, "Invitation Rejected", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(IncomingInvitationActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
                finish();
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Toast.makeText(IncomingInvitationActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    private BroadcastReceiver invitationResponseReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String type = getIntent().getStringExtra(Constrants.REMOTE_MSG_INVITATION_RESPONSE);
            if (type != null) {
                if (type.equals(Constrants.REMOTE_MSG_INVITATION_CANCELLED)) {
                    Toast.makeText(context, "Invitation Cancelled", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    };
    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(
                invitationResponseReceiver,
                new IntentFilter(Constrants.REMOTE_MSG_INVITATION_RESPONSE)
        );
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(
                invitationResponseReceiver
        );
    }
}
