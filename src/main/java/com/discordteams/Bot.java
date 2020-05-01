package com.discordteams;

import com.mongodb.client.*;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.JDA;

import com.mongodb.client.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import java.util.Arrays;
import com.mongodb.Block;

import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Bot {

    public static JDA jda;

    public static void main(String[] args) throws Exception {
        File file = new File("/Users/plantstoen/Dev/DiscordTeams/src/main/resources/token.txt");
        FileReader filereader = new FileReader(file);
        BufferedReader bufReader = new BufferedReader(filereader);

        JDABuilder jb = new JDABuilder(AccountType.BOT);
        jb.setAutoReconnect(true);
        jb.setStatus(OnlineStatus.DO_NOT_DISTURB);
        jb.setToken(bufReader.readLine());
        jb.addEventListeners(new Listener());

        try {
            MongoClient mongoClient = MongoClients.create(
                    "mongodb+srv://plantstoen:sangmin0917@discordteamscluster-046lc.gcp.mongodb.net/test?retryWrites=true&w=majority");
            MongoDatabase database = mongoClient.getDatabase("test");
            System.out.println(database.listCollections().toString());
            System.out.println("-----");
            jda = jb.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

}
