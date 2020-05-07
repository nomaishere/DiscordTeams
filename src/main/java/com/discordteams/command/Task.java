package com.discordteams.command;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class Task extends BasicCommandAbstractClass implements BasicCommand {

    public Task(JDA jda, Guild guild, User user, List<Role> userRoles, TextChannel textChannel, Message message, MongoClient mongoClient, MongoDatabase mongoDatabase) {
        super(jda, guild, user, userRoles, textChannel, message, mongoClient, mongoDatabase);
        this.typeSelector();
    }


    @Override
    public void typeSelector() {
        if(args.length == 1) {
            this.read();
            return;
        }
        switch (args[1]) {
            case "add":
                this.add();
                break;
            case "test":
                break;
            default:
                break;
        }


    }

    @Override
    public void read() {

    }

    @Override
    public void add() {
        List<String> targetRolesId = new ArrayList<String>();

        if(args[2].equals("@everyone")) {
            targetRolesId.add("everyone");
        }
        else {
            List<Role> mentionedRoles = message.getMentionedRoles();
            for (Role eachRole : mentionedRoles) {
                targetRolesId.add(eachRole.getId());
            }
        }

        // Document 생성 및 주
        Document doc = new Document("taskId",message.getId())
                .append("taskTarget", targetRolesId)
                .append("taskValue",this.value)
                .append("taskCreateTime",message.getTimeCreated().toString());
        collection.updateOne(eq("doctype", "task"),
                new Document("$push", new Document("onTaskQueue", doc)));

        textChannel.sendMessage("added task successfully. view notice list to \"!task\" command").queue();

        // Embed 만들기 + private 으로 전송
        EmbedBuilder privateNoticeEmbedBuilder = new EmbedBuilder();
        privateNoticeEmbedBuilder.setAuthor("Discord Teams", "http://drive.google.com/uc?export=view&id=109kUq116PcKf9MTSiKwcNVYYXE_eSTfC", "http://drive.google.com/uc?export=view&id=1kBh7SNBjI_IFKOL1XyfGX-H8P8IdFwCh");
        //privateNoticeEmbedBuilder.setThumbnail("http://drive.google.com/uc?export=view&id=109kUq116PcKf9MTSiKwcNVYYXE_eSTfC");
        privateNoticeEmbedBuilder.setTitle("New Task From " + guild.getName());
        privateNoticeEmbedBuilder.setColor(15877125);
        privateNoticeEmbedBuilder.addField("Created) " + message.getTimeCreated().toString().split("T")[0], this.value,false );
        MessageEmbed privateNoticeEmbed = privateNoticeEmbedBuilder.build();
        sendPrivateMessage(targetRolesId, privateNoticeEmbed);

    }

    @Override
    public void delete() {

    }

}
