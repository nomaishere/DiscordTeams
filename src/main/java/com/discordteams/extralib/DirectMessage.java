package com.discordteams.extralib;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

public class DirectMessage {
    Guild guild;
    Member member;
    public DirectMessage(Guild guild, Member member) {
        this.guild = guild;
        this.member = member;
    }
}
