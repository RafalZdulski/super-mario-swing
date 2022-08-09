package GameStates;

import Basis.GameState;
import World1.WorldState;

import java.util.ArrayList;

public class GameStateManager {

    private ArrayList<GameState> gameStates;
    private int currentState;

    public static final int MENUSTATE = 0;
    public static final int HELPSCREEN = 1;
    public static final int PLAYGAME = 2;

    public GameStateManager() {
        gameStates = new ArrayList<>();

        currentState = MENUSTATE;
        gameStates.add(new MenuState(this));
        gameStates.add(new HelpScreenState(this));
        gameStates.add(new WorldState(this));
    }

    public void setState(int state) {
        currentState = state;
        gameStates.get(currentState).init();
    }

    public void update() {
        if(gameStates.get(PLAYGAME).gameOver && currentState != PLAYGAME){
            gameStates.remove(PLAYGAME);
            gameStates.add(PLAYGAME,new WorldState(this));
        }
        gameStates.get(currentState).update();
    }
    public void draw(java.awt.Graphics2D g) {
        gameStates.get(currentState).draw(g);
    }
    public void keyPressed(int k) {
        gameStates.get(currentState).keyPressed(k);
    }
    public void keyReleased(int k) { gameStates.get(currentState).keyReleased(k);}
}
