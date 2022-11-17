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
import com.degrau.models.User;
import com.degrau.network.ApiClient;
import com.degrau.network.ApiService;
import com.degrau.utilities.Constrants;
import com.degrau.utilities.PreferenceManager;

import com.google.firebase.installations.FirebaseInstallations;


import org.json.JSONArray;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OutgoingInvitationActivity extends AppCompatActivity {
    private PreferenceManager preferenceManager;
    private String inviterToken = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outgoing_invitation);

        ImageView imageMeetingType = findViewById(R.id.imageMeetingType);
        String meetingType = getIntent().getStringExtra("type");

        preferenceManager = new PreferenceManager(getApplicationContext());

        if (meetingType != null) {
            imageMeetingType.setImageResource(R.drawable.ic_video);
        }
        TextView textFirstChar = findViewById(R.id.textFirstChar);
        TextView textUsername = findViewById(R.id.textUsername);
        TextView textEmail = findViewById(R.id.textEmail);

        User user = (User) getIntent().getSerializableExtra("user");
        if (user != null) {
            textFirstChar.setText(user.nomeCompleto.substring(0, 1));
            textUsername.setText(String.format("%s", user.nomeCompleto));
            textEmail.setText(user.email);
        }
        ImageView imageStopInvitation = findViewById(R.id.imageRejectInvitation);
        imageStopInvitation.setOnClickListener(view -> {
            if (user != null) {
                cancelledInvitation(user.token);
            }
        });
        FirebaseInstallations.getInstance().getToken(true).addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                inviterToken = task.getResult().getToken();
                if (meetingType != null && user != null) {
                    initiateMeeting(meetingType, user.token);
                }
            }
        });
    }

    private void initiateMeeting(String meetingType, String receiverToken) {
        try {
            JSONArray tokens = new JSONArray();
            tokens.put(receiverToken);

            JSONObject body = new JSONObject();
            JSONObject data = new JSONObject();

            data.put(Constrants.REMOTE_MSG_TYPE, Constrants.REMOTE_MSG_INVITATION);
            data.put(Constrants.REMOTE_MSG_MEETING_TYPE, meetingType);
            data.put(Constrants.KEY_NOMECOMPLETO, preferenceManager.getString(Constrants.KEY_NOMECOMPLETO));
            data.put(Constrants.KEY_EMAIL, preferenceManager.getString(Constrants.KEY_EMAIL));
            data.put(Constrants.REMOTE_MSG_INVITER_TOKEN, inviterToken);

            body.put(Constrants.REMOTE_MSG_DATA, data);
            body.put(Constrants.REMOTE_MSG_REGISTRATION_IDS, tokens);

            sendRemoteMessage(body.toString(), Constrants.REMOTE_MSG_INVITATION);

        } catch (Exception exception) {
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
                if (response.isSuccessful()) {
                    if (type.equals(Constrants.REMOTE_MSG_INVITATION)) {
                        Toast.makeText(OutgoingInvitationActivity.this, "Invitation sent successfully", Toast.LENGTH_SHORT).show();
                    } else if (type.equals(Constrants.REMOTE_MSG_INVITATION_RESPONSE)) {
                        Toast.makeText(OutgoingInvitationActivity.this, "Invitation cancelled", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(OutgoingInvitationActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Toast.makeText(OutgoingInvitationActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void cancelledInvitation(String receiverToken) {
        try {
            JSONArray tokens = new JSONArray();
            tokens.put(receiverToken);

            JSONObject body = new JSONObject();
            JSONObject data = new JSONObject();

            data.put(Constrants.REMOTE_MSG_MEETING_TYPE, Constrants.REMOTE_MSG_INVITATION_RESPONSE);
            data.put(Constrants.REMOTE_MSG_INVITATION_RESPONSE, Constrants.REMOTE_MSG_INVITATION_CANCELLED);

            data.put(Constrants.REMOTE_MSG_DATA, data);
            body.put(Constrants.REMOTE_MSG_REGISTRATION_IDS, tokens);

            sendRemoteMessage(body.toString(), Constrants.REMOTE_MSG_INVITATION_RESPONSE);
        } catch (Exception exception) {
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    ;
    private BroadcastReceiver invitationResponseReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String type = getIntent().getStringExtra(Constrants.REMOTE_MSG_INVITATION_RESPONSE);
            if (type != null) {
                if (type.equals(Constrants.REMOTE_MSG_INVITATION_ACCEPTED)) {
                    Toast.makeText(context, "Invitation Accepted", Toast.LENGTH_SHORT).show();
                } else if (type.equals(Constrants.REMOTE_MSG_INVITATION_REJECTED)) {
                    Toast.makeText(context, "Invitation Rejected", Toast.LENGTH_SHORT).show();
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