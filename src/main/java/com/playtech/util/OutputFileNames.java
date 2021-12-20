package com.playtech.util;

public enum OutputFileNames {

    SESSION_WAGERED_WON("session_wagered_won.csv"),
    GAME_WAGERED_WON("game_wagered_won.csv"),
    SESSION_GAME_WAGERED_WON("session_game_wagered_won.csv"),
    TOP5_PROFITABLE_PLAYERS("top5_profitable_players.csv");

    private final String fileName;

    OutputFileNames(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
