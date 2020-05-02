package com.discordteams.command;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.internal.utils.Checks;
import net.dv8tion.jda.api.entities.User;

import java.util.List;


public class Team {

    JDA jda;

    public Team(JDA jda) {
        this.jda = jda;
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

    public void getMember() {
        System.out.println("Getting users....");
        List<User> users = jda.getUsers();
        System.out.println(users);
    }
}
