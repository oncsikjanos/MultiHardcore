package com.github.oncsikjanos.multiHardcore.sharedHp.commands.team;

import com.github.oncsikjanos.multiHardcore.sharedHp.team.TeamHandler;

public class TeamCommand {
    protected static TeamHandler teamHandler;

    public TeamCommand(TeamHandler teamHandler) {
        this.teamHandler = teamHandler;
    }
}
