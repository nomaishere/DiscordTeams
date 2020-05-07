package com.discordteams.command;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;

import java.util.List;

public class Help extends BasicCommandAbstractClass implements BasicCommand{
    public Help(JDA jda, Guild guild, User user, List<Role> userRoles, TextChannel textChannel, Message message, MongoClient mongoClient, MongoDatabase mongoDatabase) {
        super(jda, guild, user, userRoles, textChannel, message, mongoClient, mongoDatabase);
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

    }

    @Override
    public void delete() {

    }


}
