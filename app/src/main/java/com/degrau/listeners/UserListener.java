package com.degrau.listeners;

import com.degrau.models.User;

public interface UserListener {

    void initiateVideoMeeting (User user);
    void initiateAudioMeeting (User user);

}
