package it.polimi.ingsw.network.server;

import it.polimi.ingsw.model.pawns.PawnColor;
import it.polimi.ingsw.network.communication.NotificationVisitor;
import it.polimi.ingsw.network.communication.notification.Notification;

import java.io.Serial;
import java.util.List;

public class SwapNotification extends Notification {
    @Serial
    private static final long serialVersionUID = 7965323394394654499L;

    private int swap;
    private List<PawnColor> swapList;
    public SwapNotification(int swap) {
        this.swap = swap;
    }

    public SwapNotification(List<PawnColor> swapList) {
        this.swapList = swapList;
    }

    @Override
    public void accept(NotificationVisitor visitor) {
        visitor.visit(this);
    }

    public int getSwap() {
        return swap;
    }

    public List<PawnColor> getSwapList() {
        return swapList;
    }
}
