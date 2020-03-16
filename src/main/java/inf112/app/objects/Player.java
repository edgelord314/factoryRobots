package inf112.app.objects;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import inf112.app.objects.Direction.Rotation;

/**
 * Class for the player which the clients user controls,
 * not to be confused by the player character, which is the {@link Robot} class
 */
public class Player extends InputAdapter {
    private Robot character;


    /**
     * The single constructor for Player
     * @param x X-coordinate of the player character
     * @param y Y-coordinate of the player character
     */
    public Player(int x, int y){
        character = new Robot(new Position(x,y),"player");
    }

    public Robot getCharacter(){
        return character;
    }

    /**
     * Changes the players coordinates based on keypress
     * @param keycode Code for key that is being released
     * @return true if key is being released, false if not
     */
    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                character.turn(Rotation.LEFT);
                break;
            case Input.Keys.RIGHT:
                character.turn(Rotation.RIGHT);
                break;
            case Input.Keys.UP:
                character.move(1);
                break;
            case Input.Keys.SPACE:
                character.initiateRobotProgramme();
                break;
            default:
                System.out.println("Unassigned input");
                break;
        }
        return false;
    }
}
