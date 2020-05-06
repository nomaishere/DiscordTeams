package com.discordteams.command;

import com.discordteams.extralib.DataCasting;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.parser.JSONParser;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.*;
import org.apache.commons.collections4.Bag;
import org.bson.Document;



import java.util.*;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.text;

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
            case "delete":
                this.delete();
                break;
            case "test":
                this.test();
                break;
            default:
                textChannel.sendMessage("commandType select error!").queue();
                break;
        }

    }

    public void test() {
    }

    @Override
    public void read() {
        Document noticeDocument = (Document) collection.find(eq("doctype", "notice")).first();
        List<Object> onNoticeQueue = (List<Object>) noticeDocument.get("onNoticeQueue");

        int counter = 1;
        List<String> sendTextList = new ArrayList<String>();
        sendTextList.add("```**Notice List:**\n");
        for(Object eachNotice : onNoticeQueue) {
            Document eachNoticeDoc = (Document) eachNotice;
            List<Role> eachTargetRoles = (List<Role>) eachNoticeDoc.get("noticeTarget");
            for(Role eachUserRole : userRoles) {
                if(eachTargetRoles.contains(eachUserRole.getId())) {
                    sendTextList.add(counter + " : " +eachNoticeDoc.getString("noticeValue") + "\n");
                    counter++;
                    break;
                }
            }
        }

        sendTextList.add("```");
        String sendText = sendTextList.stream().map(n->String.valueOf(n)).collect(Collectors.joining());
        textChannel.sendMessage(sendText).queue();
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


        Document doc = new Document("noticeId",message.getId())
                .append("noticeTarget", targetRolesId)
                .append("noticeValue",this.value)
                .append("noticeCreateTime",message.getTimeCreated().toString());


        collection.updateOne(eq("doctype", "notice"),
                new Document("$push", new Document("onNoticeQueue", doc)));

        textChannel.sendMessage("added notice successfully. view notice list to \"!notice\" command").queue();
        //user.openPrivateChannel().complete().sendMessage("Notice from " + "[" + guild.getName() + "]" + " : " + value).queue();


    }

    @Override
    public void delete() {

    }
}
