package com.discordteams.command;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public abstract class BasicCommandAbstractClass {
    JDA jda;
    Guild guild;
    User user;
    TextChannel textChannel;
    Message message;
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    MongoCollection collection;
    String[] args;

    public BasicCommandAbstractClass(JDA jda, Guild guild, User user, TextChannel textChannel, Message message, com.mongodb.client.MongoClient mongoClient, MongoDatabase mongoDatabase) {
        this.jda = jda;
        this.guild = guild;
        this.user = user;
        this.textChannel = textChannel;
        this.message = message;
        this.mongoClient = mongoClient;
        this.mongoDatabase = mongoDatabase;
        this.args = message.getContentRaw().substring(1).split(" ");
    }

    public abstract void typeSelector();

}
