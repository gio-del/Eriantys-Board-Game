module it.polimi.ingsw.am23 {
    requires javafx.controls;
    requires javafx.fxml;


    opens it.polimi.ingsw.am23 to javafx.fxml;
    exports it.polimi.ingsw.am23;
}