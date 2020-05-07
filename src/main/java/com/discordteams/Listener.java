package com.discordteams;

import com.discordteams.command.Notice;
import com.discordteams.command.Task;
import com.discordteams.feature.Team;
import com.discordteams.feature.Test;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;
import java.util.List;


public class Listener extends ListenerAdapter {
    JDA jda;
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;

    public void addJDAObject(JDA jda) {
        this.jda = jda;
    }

    public void addMongoDBObject(MongoClient mongoClient, MongoDatabase mongoDatabase) {
        this.mongoClient = mongoClient;
        this.mongoDatabase = mongoDatabase;
    }

    @Override // Main MessageEventListener
    public void onMessageReceived(MessageReceivedEvent event) {
        Guild guild = event.getGuild();
        User user = event.getAuthor();
        TextChannel tc = event.getTextChannel();
        Member member = event.getMember();
        List<Role> userRoles = member.getRoles();
        Message msg = event.getMessage();
        if(user.isBot()) return;
        if(msg.getContentRaw().charAt(0) == '!') {
            String[] args = msg.getContentRaw().substring(1).split(" ");
            System.out.println("Guild ID: " + guild);
            switch (args[0]) {
                case "help":
                    tc.sendMessage("I'll make a user guide soon..").queue();
                    break;
                case "team":
                    Team team = new Team(jda, guild, user, tc, msg, mongoClient, mongoDatabase);
                    break;
                case "notice":
                    Notice notice = new Notice(jda, guild, user, userRoles, tc, msg, mongoClient, mongoDatabase);
                    break;
                case "task":
                    Task task = new Task(jda, guild, user, userRoles, tc, msg, mongoClient, mongoDatabase);
                    break;
                case "":
                    break;
                case "test":
                    Test test = new Test(jda, guild, user, tc, msg, mongoClient, mongoDatabase);
                default:
                    //tc.sendMessage("There are no command. Text !help to get a usable command :) ").queue();
                    break;
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
