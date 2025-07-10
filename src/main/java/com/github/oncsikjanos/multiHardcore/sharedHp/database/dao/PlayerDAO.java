package com.github.oncsikjanos.multiHardcore.sharedHp.database.dao;


public interface PlayerDAO {
    String get();
    int add(int teamId, String playerName);
    boolean remove(String player);
}
