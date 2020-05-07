package com.discordteams.command;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;

import java.util.List;

public abstract class BasicCommandAbstractClass {
    JDA jda;
    Guild guild;
    User user;
    List<Role> userRoles;
    TextChannel textChannel;
    Message message;
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    MongoCollection collection;
    String[] args;
    String value;

    public BasicCommandAbstractClass(JDA jda, Guild guild, User user, List<Role> userRoles, TextChannel textChannel, Message message, MongoClient mongoClient, MongoDatabase mongoDatabase) {
        this.jda = jda;
        this.guild = guild;
        this.user = user;
        this.userRoles = userRoles;
        this.textChannel = textChannel;
        this.message = message;
        this.mongoClient = mongoClient;
        this.mongoDatabase = mongoDatabase;
        this.args = message.getContentRaw().substring(1).split(" ");
        this.collection = mongoDatabase.getCollection(guild.getName());
        commandValidation();
    }

    private void commandValidation() {
        setValue();
    }

    private void setValue() {
        if(args.length >= 4) {
            String[] valueSlicer = message.getContentRaw().substring(1).split("\"");
            this.value = valueSlicer[1];
        }
    }

    public abstract void typeSelector();


    protected void sendPrivateMessage(List<String> targetRolesId, MessageEmbed privateNoticeEmbed) {
        if(targetRolesId.get(0).equals("everyone")) {
            List<Member> members = guild.getMembers();
            for(Member eachMember : members) {
                User eachUser = eachMember.getUser();
                if(eachUser.isBot())
                    continue;
                eachUser.openPrivateChannel().complete().sendMessage(privateNoticeEmbed).queue();
            }
        }
        else {
            List<Member> members = guild.getMembersWithRoles(message.getMentionedRoles());
            for(Member eachMember : members) {
                User eachUser = eachMember.getUser();
                if(eachUser.isBot())
                    continue;
                eachUser.openPrivateChannel().complete().sendMessage(privateNoticeEmbed).queue();
            }
        }
    }

}
