public class Knight extends Piece {
    public Knight(char color, int xPos, int yPos) {
        super(color, xPos, yPos);
        customLines[0] = ". .";
        customLines[1] = "|\\|";
        customLines[2] = "' `";
    }

    public String toString() {
        return "knight";
    }

    public boolean isValidMove() {
        int xDistance;
        int yDistance;

        xDistance = Math.abs(this.xPos - this.xPosToMove);
        yDistance = Math.abs(this.yPos - this.yPosToMove);

        if (!((xDistance == 2 || yDistance == 2) && (xDistance == 1 || yDistance ==1))) { //Checks for the L shape
            return false;
        }
        return true;
    }
}
