package com.github.oncsikjanos.multiHardcore.sharedHp.database.dao;

import com.github.oncsikjanos.multiHardcore.sharedHp.team.Team;


public interface TeamDAO {
    Team get();
    int add(String creatorName);
    boolean remove(Team team);
    boolean update(Team team);
}
