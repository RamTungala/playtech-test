package com.playtech.dto;

import java.util.Objects;

public class PlayerGame extends WageredWon {

    private String gameName;

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerGame playerGame = (PlayerGame) o;
        return Objects.equals(gameName, playerGame.gameName) && Objects.equals(playerId, playerGame.getPlayerId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameName, playerId);
    }

    @Override
    public String toString() {
        return "PlayerGame{" +
            " gameName=" + gameName +
            " playerId=" + playerId +
            " Amount Wagered=" + amountWagered +
            " Amount Won=" + amountWon +
            " }";
    }
}
