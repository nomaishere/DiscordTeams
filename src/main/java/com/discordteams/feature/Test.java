package com.discordteams.feature;

import com.discordteams.extralib.DatabaseSetup;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class Test extends BasicFeature{
    public Test(JDA jda, Guild guild, User user, TextChannel textChannel, Message message, MongoClient mongoClient, MongoDatabase mongoDatabase) {
        super(jda, guild, user, textChannel, message, mongoClient, mongoDatabase);
        this.commandSelector();
    }

    @Override
    public void commandSelector() {
        String[] args = message.getContentRaw().substring(1).split(" ");
        switch (args[1]) {
            case "createCollection":
                textChannel.sendMessage("start creating collection...").queue();
                DatabaseSetup setup = new DatabaseSetup(jda,guild, user, textChannel, message, mongoClient, mongoDatabase);
                setup.createTeam();
                break;
            case "deleteCollection":
                textChannel.sendMessage("start deleting collection...").queue();
            default:
                textChannel.sendMessage("No Test Command ").queue();
                break;
        }
    }
}
