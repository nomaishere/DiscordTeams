package com.discordteams.command;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public abstract class BasicFeature {
    JDA jda;
    User user;
    TextChannel textChannel;
    Message message;

    public BasicFeature(JDA jda, User user, TextChannel textChannel, Message message) {
        this.user = user;
        this.textChannel = textChannel;
        this.message = message;
    }

    public abstract void commandSelector(String commandType);

    public abstract void commandSelector(String commandType, String data1);

    public abstract void commandSelector(String commandType, String data1, String data2);

    public void test() {
        System.out.println("-----Text Feature Class Field-----");
        System.out.println("User: " + user);
        System.out.println("TextChannel: " + textChannel);
        System.out.println("Message: " + message);
        System.out.println("-----end-----");
    }
}
