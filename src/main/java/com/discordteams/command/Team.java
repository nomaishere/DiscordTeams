package com.discordteams.command;
import com.discordteams.Listener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.GenericGuildEvent;
import net.dv8tion.jda.api.utils.cache.SnowflakeCacheView;

import net.dv8tion.jda.internal.utils.Checks;
import net.dv8tion.jda.api.entities.User;
import sun.security.krb5.Config;

import java.util.List;


public class Team {

    private JDA jda;
    private Listener listener;


    public Team(JDA jda, Listener listener) {
        this.jda = jda;
        this.listener = listener;
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

        for(User user: users) {
            System.out.println(users.size());
            System.out.println(user.isBot());
        }

        System.out.println(guild);
    }
}
