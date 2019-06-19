import java.awt.Point;
import java.util.Arrays;

public class Ship {

    private ShipPiece[] pieces;
    private Point startingPosition;
    public String toString(){
        return startingPosition + Arrays.toString(pieces);
    }
    /*
     * Constructor. Sets pieces equal to the list parameter
     */
    Ship(ShipPiece[] list) {
        pieces = list;
        startingPosition = new Point(0,0);
    }

    /*
     * If all the ship pieces are dead, it returns that the ship is dead
     */
    boolean checkIfDead() {
        boolean isDead = true;
        for (ShipPiece piece : pieces) {
            if (!piece.isDestroy()) {
                isDead = false;
            }
        }
        return isDead;
    }

    /*
     * returns the array of ship pieces
     */
    ShipPiece[] getShipPieces() {
        return pieces;
    }

    void setStartingOffGridPosition(Point sp){
        startingPosition = sp;
    }

    Point getStartingOffGridPosition(){
        return startingPosition;
    }
}
