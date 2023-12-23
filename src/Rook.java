public class Rook extends Piece {
    public Rook(char color, int xPos, int yPos) {
        super(color, xPos, yPos);
        customLines[0] = ".-.";
        customLines[1] = "|< ";
        customLines[2] = "' `";
    }

    public String toString() {
        return "rook";
    }

    public boolean isValidMove() {
        int xDistance;
        int yDistance;

        xDistance = Math.abs(this.xPos - this.xPosToMove);
        yDistance = Math.abs(this.yPos - this.yPosToMove);

        if (!((xDistance == 0) || (yDistance == 0))) { //can only move up/down OR left/right
            return false;
        }

        if (movesThroughPiece(xDistance, yDistance)) {
            return false;
        }
        
        return true;
    }
}
