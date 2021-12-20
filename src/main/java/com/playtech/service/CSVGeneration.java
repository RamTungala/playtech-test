package com.playtech.service;

public interface CSVGeneration {
    String GAME_EVENT_DATA_CSV = "Game_event_data.csv";

    boolean generateCSV();
}
