package com.playtech.service;

import com.playtech.dto.PlayerGame;
import com.playtech.dto.PlayerSession;
import com.playtech.dto.PlayerSessionGame;
import com.playtech.dto.WageredWon;
import com.playtech.util.OutputColumnNames;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.playtech.util.OutputFileNames.GAME_WAGERED_WON;
import static com.playtech.util.OutputFileNames.SESSION_GAME_WAGERED_WON;
import static com.playtech.util.OutputFileNames.SESSION_WAGERED_WON;
import static com.playtech.util.OutputFileNames.TOP5_PROFITABLE_PLAYERS;

public class WriteDataToCSV {

    private static final String CSV_SEPARATOR = ",";

    public boolean writePlayerSessionToCSV(Set<PlayerSession> playerSessionSet)
    {
        List<String> titleColumnsList = Arrays.asList(OutputColumnNames.PLAYER_ID.name(),
            OutputColumnNames.SESSION_ID.name(),
            OutputColumnNames.AMOUNT_WAGERED.name(),
            OutputColumnNames.AMOUNT_WON.name());
        String titleColumns = String.join(",", titleColumnsList);

        try(BufferedWriter bw = new BufferedWriter(
            new OutputStreamWriter(
                new FileOutputStream(SESSION_WAGERED_WON.getFileName()), StandardCharsets.UTF_8)))
        {
            bw.write(titleColumns);
            bw.newLine();
            for (PlayerSession playerSession : playerSessionSet)
            {
                String oneLine = playerSession.getPlayerId() +
                    CSV_SEPARATOR +
                    playerSession.getSessionId() +
                    CSV_SEPARATOR +
                    (playerSession.getAmountWagered() != null ? playerSession.getAmountWagered() : "") +
                    CSV_SEPARATOR +
                    (playerSession.getAmountWon() != null ? playerSession.getAmountWon() : "");
                bw.write(oneLine);
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean writePlayerGameToCSV(Set<PlayerGame> playerGameSet) {
        List<String> titleColumnsList = Arrays.asList(OutputColumnNames.PLAYER_ID.name(),
            OutputColumnNames.GAME_NAME.name(),
            OutputColumnNames.AMOUNT_WAGERED.name(),
            OutputColumnNames.AMOUNT_WON.name());
        String titleColumns = String.join(",", titleColumnsList);

        try(BufferedWriter bw = new BufferedWriter(
            new OutputStreamWriter(
                new FileOutputStream(GAME_WAGERED_WON.getFileName()), StandardCharsets.UTF_8)))
        {
            bw.write(titleColumns);
            bw.newLine();
            for (PlayerGame playerGame : playerGameSet)
            {
                String oneLine = playerGame.getPlayerId() +
                    CSV_SEPARATOR +
                    playerGame.getGameName() +
                    CSV_SEPARATOR +
                    (playerGame.getAmountWagered() != null ? playerGame.getAmountWagered() : "") +
                    CSV_SEPARATOR +
                    (playerGame.getAmountWon() != null ? playerGame.getAmountWon() : "");
                bw.write(oneLine);
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean writePlayerSessionGameToCSV(Set<PlayerSessionGame> playerSessionGameSet) {

        String titleColumns = Arrays.stream(OutputColumnNames.values()).sequential()
            .map(Enum::name).collect(Collectors.joining(","));
        try(BufferedWriter bw = new BufferedWriter(
            new OutputStreamWriter(
                new FileOutputStream(SESSION_GAME_WAGERED_WON.getFileName()), StandardCharsets.UTF_8)))
        {
            bw.write(titleColumns);
            bw.newLine();
            for (PlayerSessionGame playerSessionGame : playerSessionGameSet)
            {
                String oneLine = playerSessionGame.getPlayerId() +
                    CSV_SEPARATOR +
                    playerSessionGame.getSessionId() +
                    CSV_SEPARATOR +
                    playerSessionGame.getGameName() +
                    CSV_SEPARATOR +
                    (playerSessionGame.getAmountWagered() != null ? playerSessionGame.getAmountWagered() : "") +
                    CSV_SEPARATOR +
                    (playerSessionGame.getAmountWon() != null ? playerSessionGame.getAmountWon() : "");
                bw.write(oneLine);
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean writeWageredWonToCSV(Set<WageredWon> wageredWonSet) {
        List<String> titleColumnsList = Arrays.asList(OutputColumnNames.PLAYER_ID.name(),
            OutputColumnNames.AMOUNT_WAGERED.name(),
            OutputColumnNames.AMOUNT_WON.name());
        String titleColumns = String.join(",", titleColumnsList);
        try ( BufferedWriter bw = new BufferedWriter(
            new OutputStreamWriter(
                new FileOutputStream(TOP5_PROFITABLE_PLAYERS.getFileName()), StandardCharsets.UTF_8));)
        {
            bw.write(titleColumns);
            bw.newLine();
            for (WageredWon wageredWon : wageredWonSet)
            {
                String oneLine = wageredWon.getPlayerId() +
                    CSV_SEPARATOR +
                    (wageredWon.getAmountWagered() != null ? wageredWon.getAmountWagered() : "") +
                    CSV_SEPARATOR +
                    (wageredWon.getAmountWon() != null ? wageredWon.getAmountWon() : "");
                bw.write(oneLine);
                bw.newLine();
            }

            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
