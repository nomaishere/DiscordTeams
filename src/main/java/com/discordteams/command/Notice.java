package com.discordteams.command;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import org.bson.Document;

import javax.print.Doc;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.text;

public class Notice extends BasicCommandAbstractClass implements BasicCommand{
    public Notice(JDA jda, Guild guild, User user, TextChannel textChannel, Message message, MongoClient mongoClient, MongoDatabase mongoDatabase) {
        super(jda, guild, user, textChannel, message, mongoClient, mongoDatabase);
        this.collection = mongoDatabase.getCollection(guild.getName());
        this.typeSelector();
    }

    @Override
    public void typeSelector() {
        switch (args[1]) {
            case "add":
                this.add();
                break;
            case "":
                this.read();
                break;
            case "delete":
                this.delete();
                break;
            default:
                textChannel.sendMessage("commandType select error!").queue();
                break;
        }

    }

    @Override
    public void read() {

    }

    @Override
    public void add() {
        textChannel.sendMessage("add notice...").queue();

        Document doc = new Document("noticeId",message.getId())
                .append("noticeTarget", args[2])
                .append("noticeValue",this.value)
                .append("noticeCreateTime",message.getTimeCreated().toString());


        collection.updateOne(eq("doctype", "notice"),
                new Document("$push", new Document("onNoticeQueue", doc)));

    }

    @Override
    public void delete() {

    }
}
