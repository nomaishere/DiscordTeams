package com.discordteams;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.JDA;

import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;

public class Bot {

    public static JDA jda;

    public static void main(String[] args) throws Exception {
        JDABuilder jb = new JDABuilder(AccountType.BOT);
        jb.setAutoReconnect(true);
        jb.setStatus(OnlineStatus.DO_NOT_DISTURB);
        jb.setToken("NzA1MzQ1NzUxOTI4MDEyODI5.XqrAMg.HdRzlD5NGYuslFdVDgXpNeX3iug");

        try {
            jda = jb.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

}
