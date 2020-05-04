package com.discordteams.feature;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import org.w3c.dom.Text;

public abstract class BasicFeature {
    JDA jda;
    Guild guild;
    User user;
    TextChannel textChannel;
    Message message;
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;

    public BasicFeature(JDA jda, Guild guild, User user, TextChannel textChannel, Message message, MongoClient mongoClient, MongoDatabase mongoDatabase) {
        this.jda = jda;
        this.guild = guild;
        this.user = user;
        this.textChannel = textChannel;
        this.message = message;
        this.mongoClient = mongoClient;
        this.mongoDatabase = mongoDatabase;
    }

    public abstract void commandSelector();

    public void test() {
        System.out.println("-----Text Feature Class Field-----");
        System.out.println("User: " + user);
        System.out.println("TextChannel: " + textChannel);
        System.out.println("Message: " + message);
        System.out.println("-----end-----");
    }

}
