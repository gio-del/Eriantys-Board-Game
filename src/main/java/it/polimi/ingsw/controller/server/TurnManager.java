package it.polimi.ingsw.controller.server;

import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.utils.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TurnManager {
    private final List<String> playersOrder;

    public TurnManager() {
        playersOrder = new ArrayList<>();
    }

    /**
     * Set the order to the action one according to the played assistant (ascendant card's value order)
     * @param nicknameMapAssistant a list of relation between player and played assistant
     */
    public void setActionOrder(List<Pair<String,Assistant>> nicknameMapAssistant){
        List<Pair<String,Assistant>> sortedList = nicknameMapAssistant.stream().sorted(Comparator.comparingInt(o -> o.second().value())).toList();
        List<String> order = sortedList.stream().map(Pair::first).toList();
        playersOrder.clear();
        playersOrder.addAll(order);
    }

    /**
     * Set the order to the planning one according to the played assistant (the lowest card's value and so clockwise)
     * @param nicknameMapAssistant a list of relation between player and played assistant
     * @param players the order of player at the table
     */
    public void setPlanningOrder(List<Pair<String,Assistant>> nicknameMapAssistant, List<String> players){
        String minValuePlayer = nicknameMapAssistant.stream().min(Comparator.comparingInt(o -> o.second().value())).map(Pair::first).orElse("");
        List<String> order = new ArrayList<>();
        int pos = players.indexOf(minValuePlayer);
        int index;
        if(pos!=-1) {
            int i = 0;
            while(i<players.size()){
                index = pos + i;
                if(index >= players.size()){
                    index = pos + i - players.size();
                }
                order.add(players.get(index));
                i++;
            }
            playersOrder.clear();
            playersOrder.addAll(order);
        }
    }

    public List<String> getPlayersOrder() {
        return playersOrder;
    }
}
