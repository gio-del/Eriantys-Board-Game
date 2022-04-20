package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.view.View;

/**
 * The command line interface implementation of the game
 */
public class Cli /*extends Observable*/implements View {
    //TODO
    public void initialize(){
        System.out.println(Color.GREEN);
        System.out.println("""
                  ______      _             _            \s
                 |  ____|    (_)           | |           \s
                 | |__   _ __ _  __ _ _ __ | |_ _   _ ___\s
                 |  __| | '__| |/ _` | '_ \\| __| | | / __|
                 | |____| |  | | (_| | | | | |_| |_| \\__ \\
                 |______|_|  |_|\\__,_|_| |_|\\__|\\__, |___/
                                                 __/ |   \s
                                                |___/    \s

                 by Giovanni De Lucia, Lorenzo Battiston, Lorenzo Dell'Era""");
        //TODO: askConnectionInfo()
    }
}
