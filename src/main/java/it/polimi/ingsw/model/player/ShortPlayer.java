package it.polimi.ingsw.model.player;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public final class ShortPlayer implements Serializable {
    @Serial
    private static final long serialVersionUID = 6360991580516027489L;

    private final String playerName;
    private final Wizard wizard;
    private final TowerColor color;

    public ShortPlayer(String playerName, Wizard wizard, TowerColor color) {
        this.playerName = playerName;
        this.wizard = wizard;
        this.color = color;
    }

    public ShortPlayer(Player player) {
        this.playerName = player.getPlayerName();
        this.wizard = player.getWizard();
        this.color = player.getColor();
    }

    public String name() {
        return playerName;
    }

    public Wizard wizard() {
        return wizard;
    }

    public TowerColor color() {
        return color;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ShortPlayer) obj;
        return Objects.equals(this.playerName, that.playerName) &&
                Objects.equals(this.wizard, that.wizard) &&
                Objects.equals(this.color, that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerName, wizard, color);
    }

    @Override
    public String toString() {
        return "ShortPlayer[" +
                "name=" + playerName + ", " +
                "wizard=" + wizard + ", " +
                "color=" + color + ']';
    }

}
