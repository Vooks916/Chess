public class Bishop extends Piece {
    public Bishop(char color, int xPos, int yPos) {
        super(color, xPos, yPos);
        customLines[0] = ".-.";
        customLines[1] = "|-<";
        customLines[2] = "`-'";
    }

    public String toString() {
        return "bishop";
    }

    public boolean isValidMove() {
        int xDistance;
        int yDistance;

        xDistance = Math.abs(this.xPos - this.xPosToMove);
        yDistance = Math.abs(this.yPos - this.yPosToMove);

        if (xDistance != yDistance) { //Diagnal lines move the same in x and y
            return false;
        }

        if (movesThroughPiece(xDistance, yDistance)) {
            return false;
        }
        
        return true;
    }
}
