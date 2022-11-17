package com.degrau.utilities;

import java.util.HashMap;

public class Constrants {
    public static final String KEY_COLLECTION_USERS = "users";
    public static final String KEY_NOMECOMPLETO = "nomeCompleto";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_AREAT = "areat";
    public static final String KEY_SENHA = "senha";
    public static final String KEY_NUMERO = "numero";
    public static final String KEY_SOBRE = "sobre";
    public static final String KEY_PREFERENCE_NAME = "degrauPreference";
    public static final String KEY_ESTA_LOGADO = "estaLogado";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_FCM_TOKEN = "fcm_token";

    public static final String REMOTE_MSG_AUTHORIZATION = "Authorization";
    public static final String REMOTE_MSG_CONTENT_TYPE = "Content-Type";

    public static final String REMOTE_MSG_TYPE = "type";
    public static final String REMOTE_MSG_INVITATION = "invitation";
    public static final String REMOTE_MSG_MEETING_TYPE = "meetingType";
    public static final String REMOTE_MSG_INVITER_TOKEN = "inviterToken";
    public static final String REMOTE_MSG_DATA = "data";
    public static final String REMOTE_MSG_REGISTRATION_IDS = "registration_ids";

    public static final String REMOTE_MSG_INVITATION_RESPONSE = "invitationResponse";

    public static final String REMOTE_MSG_INVITATION_ACCEPTED = "accepted";
    public static final String REMOTE_MSG_INVITATION_REJECTED = "rejected";
    public static final String REMOTE_MSG_INVITATION_CANCELLED = "cancelled";



    public static HashMap<String, String> getRemoteMessageHeaders(){
        HashMap<String, String> headers = new HashMap<>();
        headers.put(
                Constrants.REMOTE_MSG_AUTHORIZATION,
                "key=AAAA-W4Fw4Q:APA91bFGDOmhxuO9AO9daRedCsqRdCtqbW6zbO3oP6n_6_FCqIoeDuGrp-vnNZzsvV3uqSwS61m1dNjgo5yCIsgQCqVW3YimwQ0pXmEyY2wjxouNovJG73yPpOIscqpxKY3qI9tPdZ8B"
        );
        headers.put(Constrants.REMOTE_MSG_CONTENT_TYPE,"application/json");
        return headers;
    }


}
