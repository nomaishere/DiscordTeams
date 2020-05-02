package com.discordteams.command;

public class Schedule implements BasicCommand {
    String commandType;
    String value;

    public Schedule(String commandType, String value) {
        this.commandType = commandType;
        this.value = value;
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
