package com.degrau.firebase;

import android.content.Intent;


import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.degrau.activities.IncomingInvitationActivity;
import com.degrau.utilities.Constrants;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String type = remoteMessage.getData().get(Constrants.REMOTE_MSG_TYPE);

        if(type != null) {
            if(type.equals(Constrants.REMOTE_MSG_INVITATION)){
                Intent intent = new Intent(getApplicationContext(), IncomingInvitationActivity.class);
                intent.putExtra(Constrants.REMOTE_MSG_MEETING_TYPE,
                        remoteMessage.getData().get(Constrants.REMOTE_MSG_MEETING_TYPE)
                );
                intent.putExtra(Constrants.KEY_NOMECOMPLETO,
                        remoteMessage.getData().get(Constrants.KEY_NOMECOMPLETO)
                );
                intent.putExtra(Constrants.KEY_EMAIL,
                        remoteMessage.getData().get(Constrants.KEY_EMAIL)
                );
                intent.putExtra(Constrants.REMOTE_MSG_INVITER_TOKEN,
                        remoteMessage.getData().get(Constrants.REMOTE_MSG_INVITER_TOKEN)
                );
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }else if(type.equals(Constrants.REMOTE_MSG_INVITATION_RESPONSE)){
            Intent intent = new Intent(Constrants.REMOTE_MSG_INVITATION_RESPONSE);
            intent.putExtra(
                    Constrants.REMOTE_MSG_INVITATION_RESPONSE,
                    remoteMessage.getData().get(Constrants.REMOTE_MSG_INVITATION_RESPONSE)
            );
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
        }

    }
}
