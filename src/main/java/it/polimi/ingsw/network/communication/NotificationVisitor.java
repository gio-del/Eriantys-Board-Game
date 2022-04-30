package it.polimi.ingsw.network.communication;

import it.polimi.ingsw.network.communication.notification.*;

/**
 * This interface is used to implement the visitor pattern. Both in client and
 * server side a {@link Notification} is filtered by a {@link NotificationVisitor}
 * that updates view or model.
 */
public interface NotificationVisitor {
    void visit(LoginNotification msg);
    void visit(WinNotification msg);
    void visit(CloudsNotification msg);
    void visit(MoveStudentNotification msg);
    void visit(MoveMNNotification msg);
    void visit(ChooseCloudNotification msg);
    void visit(ChooseGameModeNotification msg);
    void visit(SchoolNotification msg);
    void visit(DisconnectionNotification msg);
    void visit(ChooseWizAndTowerColorNotification msg);
    void visit(ChooseAssistantNotification msg);
    void visit(NicknameErrorNotification msg);
    void visit(EventNotification msg);
    void visit(GameStartedNotification msg);
    void visit(GenericMessageNotification msg
    );
}
