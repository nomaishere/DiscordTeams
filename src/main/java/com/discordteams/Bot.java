package com.discordteams;

import com.mongodb.client.*;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.JDA;

import com.mongodb.client.MongoClient;

import com.mongodb.client.MongoDatabase;

import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Bot {

    public static JDA jda;

    public static void main(String[] args) throws Exception {
        File file = new File("/Users/plantstoen/Dev/DiscordTeams/src/main/resources/token.txt");
        FileReader filereader = new FileReader(file);
        BufferedReader bufReader = new BufferedReader(filereader);

        JDABuilder jb = new JDABuilder(AccountType.BOT);
        jb.setAutoReconnect(true);
        jb.setStatus(OnlineStatus.ONLINE);
        jb.setToken(bufReader.readLine());
        Listener listener = new Listener();
        jb.addEventListeners(listener);

        try {
            MongoClient mongoClient = MongoClients.create(
                    "mongodb+srv://plantstoen:sangmin0917@discordteamscluster-046lc.gcp.mongodb.net/test?retryWrites=true&w=majority");
            MongoDatabase database = mongoClient.getDatabase("discordteams_data");

            /*
            MongoIterable<String> collection = database.listCollectionNames();
            List<String> tables = new ArrayList<String>();
            MongoCursor<String> cursor = collection.iterator();
            while(cursor.hasNext()) {
                String table = cursor.next();
                System.out.println(table);
            }
           */

            jda = jb.build();
            listener.addJDAObject(jda);
            listener.addMongoDBObject(mongoClient, database);


        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

}
