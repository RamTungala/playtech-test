package com.playtech.dto;

import java.util.Objects;

public class PlayerSessionGame extends WageredWon{

    private Long sessionId;

    private String gameName;

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public Long getSessionId() {
        return sessionId;
    }

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
        PlayerSessionGame that = (PlayerSessionGame) o;
        return Objects.equals(sessionId, that.sessionId) &&
            Objects.equals(gameName, that.gameName) &&
            Objects.equals(playerId, that.getPlayerId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, gameName, playerId);
    }

    @Override
    public String toString() {
        return "PlayerSessionGame{" +
            " sessionId=" + sessionId +
            " gameName=" + gameName +
            " playerId=" + playerId +
            " Amount Wagered=" + amountWagered +
            " Amount Won=" + amountWon +
            " }";
    }
}
