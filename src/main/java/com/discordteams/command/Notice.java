package com.discordteams.command;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.*;
import org.bson.Document;



import java.util.*;

import static com.mongodb.client.model.Filters.eq;

public class Notice extends BasicCommandAbstractClass implements BasicCommand{

    public Notice(JDA jda, Guild guild, User user, List<Role> userRoles, TextChannel textChannel, Message message, MongoClient mongoClient, MongoDatabase mongoDatabase) {
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
        Document noticeDocument = (Document) collection.find(eq("doctype", "notice")).first();
        List<Object> onNoticeQueue = (List<Object>) noticeDocument.get("onNoticeQueue");

        EmbedBuilder publicNoticeEmbedBuilder = new EmbedBuilder();
        publicNoticeEmbedBuilder.setAuthor("Discord Teams", "http://drive.google.com/uc?export=view&id=109kUq116PcKf9MTSiKwcNVYYXE_eSTfC", "http://drive.google.com/uc?export=view&id=1kBh7SNBjI_IFKOL1XyfGX-H8P8IdFwCh");
        publicNoticeEmbedBuilder.setThumbnail("http://drive.google.com/uc?export=view&id=109kUq116PcKf9MTSiKwcNVYYXE_eSTfC");
        publicNoticeEmbedBuilder.setTitle("Notice List");
        publicNoticeEmbedBuilder.setColor(15877125);

        for(Object eachNotice : onNoticeQueue) {
            Document eachNoticeDoc = (Document) eachNotice;
            List<Role> eachTargetRoles = (List<Role>) eachNoticeDoc.get("noticeTarget");
            for(Role eachUserRole : userRoles) {
                if(eachTargetRoles.contains(eachUserRole.getId()) || eachTargetRoles.contains("everyone")) {
                    publicNoticeEmbedBuilder.addField(
                            "Created) " + eachNoticeDoc.getString("noticeCreateTime").split("T")[0],
                            eachNoticeDoc.getString("noticeValue"),
                            false );
                    break;
                }
            }
        }
        textChannel.sendMessage(publicNoticeEmbedBuilder.build()).queue();
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

        // Document 생성 및 주입
        Document doc = new Document("noticeId",message.getId())
                .append("noticeTarget", targetRolesId)
                .append("noticeValue",this.value)
                .append("noticeCreateTime",message.getTimeCreated().toString());
        collection.updateOne(eq("doctype", "notice"),
                new Document("$push", new Document("onNoticeQueue", doc)));

        // Embed 만들기 + private 으로 전송
        textChannel.sendMessage("added notice successfully. view notice list to \"!notice\" command").queue();
        EmbedBuilder privateNoticeEmbedBuilder = new EmbedBuilder();
        privateNoticeEmbedBuilder.setAuthor("Discord Teams", "http://drive.google.com/uc?export=view&id=109kUq116PcKf9MTSiKwcNVYYXE_eSTfC", "http://drive.google.com/uc?export=view&id=1kBh7SNBjI_IFKOL1XyfGX-H8P8IdFwCh");
        //privateNoticeEmbedBuilder.setThumbnail("http://drive.google.com/uc?export=view&id=109kUq116PcKf9MTSiKwcNVYYXE_eSTfC");
        privateNoticeEmbedBuilder.setTitle("New Notice From " + guild.getName());
        privateNoticeEmbedBuilder.setColor(15877125);
        privateNoticeEmbedBuilder.addField("Created) " + message.getTimeCreated().toString().split("T")[0], this.value,false );
        MessageEmbed privateNoticeEmbed = privateNoticeEmbedBuilder.build();
        sendPrivateMessage(targetRolesId, privateNoticeEmbed);
    }

    @Override
    public void delete() {

    }

}
