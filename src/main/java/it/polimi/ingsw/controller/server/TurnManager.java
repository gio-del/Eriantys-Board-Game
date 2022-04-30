package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.utils.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TurnManager {
    private final List<String> playerOrder;

    public TurnManager() {
        playerOrder = new ArrayList<>();
    }

    public void setActionOrder(List<Pair<String,Assistant>> nicknameMapAssistant){
        List<Pair<String,Assistant>> sortedList = nicknameMapAssistant.stream().sorted(Comparator.comparingInt(o -> o.second().value())).toList();
        List<String> order = sortedList.stream().map(Pair::first).toList();
        playerOrder.clear();
        playerOrder.addAll(order);
    }

    public List<String> getPlayerOrder() {
        return playerOrder;
    }
}
