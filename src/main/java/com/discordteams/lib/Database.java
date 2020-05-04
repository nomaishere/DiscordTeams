package com.discordteams.lib;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import net.dv8tion.jda.api.entities.Guild;

public class Database {

    public void createTeam(Guild guild, MongoClient mongoClient, MongoDatabase mongoDatabase) {
        System.out.println("create");
    }
}
