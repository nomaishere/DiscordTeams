package com.discordteams.extralib;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoClient;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import org.bson.Document;

import java.lang.reflect.Array;
import java.util.Arrays;

public class DatabaseSetup {
    JDA jda;
    Guild guild;
    User user;
    TextChannel textChannel;
    Message message;
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    MongoCollection collection;

    public DatabaseSetup(JDA jda, Guild guild, User user, TextChannel textChannel, Message message, com.mongodb.client.MongoClient mongoClient, MongoDatabase mongoDatabase) {
        this.jda = jda;
        this.guild = guild;
        this.user = user;
        this.textChannel = textChannel;
        this.message = message;
        this.mongoClient = mongoClient;
        this.mongoDatabase = mongoDatabase;
    }

    // 새 팀의 Collection 을 만들고 Document 구성용 함수들을 불러오는 함수
    public void createTeam() {
        mongoDatabase.createCollection(guild.getName());
        collection = mongoDatabase.getCollection(guild.getName());
        createBasicDoc();
        createTeamDoc();
        createNoticeDocument();
        createTaskDocument();
    }

    private void createBasicDoc() {
        Document doc = new Document("doctype", "basicData")
                .append("guild", new Document("guildName", guild.getName())
                        .append("guildId",guild.getId())
                        .append("guildOwnerId", guild.getOwnerId())
                );
        collection.insertOne(doc);
    }

    private void createTeamDoc() {
        Document doc = new Document("doctype","team");
        collection.insertOne(doc);
    }

    private void createNoticeDocument() {
        Document doc = new Document("doctype","notice")
                .append("onNoticeQueue", Arrays.asList())
                .append("doneNoticeQueue", Arrays.asList());
        collection.insertOne(doc);
    }

    private void createTaskDocument() {
        Document doc = new Document("doctype","task")
                .append("onTaskQueue", Arrays.asList())
                .append("doneTaskQueue", Arrays.asList());
        collection.insertOne(doc);
    }

}
