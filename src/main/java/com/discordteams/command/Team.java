package com.discordteams.command;
import com.discordteams.Listener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.GenericGuildEvent;
import net.dv8tion.jda.api.utils.cache.SnowflakeCacheView;

import net.dv8tion.jda.internal.utils.Checks;
import sun.security.krb5.Config;

import java.util.List;


public class Team extends BasicFeature {

    public Team(JDA jda, User user, TextChannel textChannel, Message message) {
        super(jda, user, textChannel, message);
    }

    public Role updateRole(Member member) {
        Checks.notNull(member, "Member object can not be null");

        List<Role> roles = member.getRoles();
        if (roles.isEmpty()) {
            return null;
        }

        return roles.stream().min((first, second) -> {
            if (first.getPosition() == second.getPosition()) {
                return 0;
            }
            return first.getPosition() > second.getPosition() ? -1 : 1;
        }).get();
    }

    public void updateMember(GenericGuildEvent event) {
        System.out.println("Getting users....");
        List<User> users = jda.getUsers();
        SnowflakeCacheView<Guild> guildIds = jda.getGuildCache();
        Guild guild = event.getGuild();

        for (User user : users) {
            System.out.println(users.size());
            System.out.println(user.isBot());
        }

        System.out.println(guild);
    }

    @Override
    public void commandSelector(String commandType) {

    }

    @Override
    public void commandSelector(String commandType, String data1) {

    }

    @Override
    public void commandSelector(String commandType, String data1, String data2) {

    }
}
