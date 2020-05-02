package com.discordteams;

import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;


public class Listener extends ListenerAdapter {

    @Override // Main MessageEventListenerd
    public void onMessageReceived(MessageReceivedEvent event) {
        User user = event.getAuthor();
        TextChannel tc = event.getTextChannel();
        Message msg = event.getMessage();
        if(user.isBot()) return;
        if(msg.getContentRaw().charAt(0) == '!') {
            String[] args = msg.getContentRaw().substring(1).split(" ");
            switch (args[0]) {
                case "help":
                    tc.sendMessage("I'll make a user guide soon..").queue();
                    break;
                case "team":
                    System.out.println("team 입력");
                    break;
                case "":
                    break;
                case "test":
                    System.out.println("test");
                default:
                    tc.sendMessage("There is not a command. Text !help to get a usable command :)").queue();
            }
        }
    }

    @Override // Launch when this bot is invited a server(guild)
    public void onGuildJoin(@Nonnull GuildJoinEvent event) {
        System.out.println("add Guild!");
    }

    @Override // Launch when this bot is banned a server(guild)
    public void onGuildLeave(@Nonnull GuildLeaveEvent event) {
        System.out.println("leave Guild!");
    }
}
