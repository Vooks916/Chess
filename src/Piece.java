public class Piece {
    private char color;
    protected int xPos;
    protected int yPos;
    protected int xPosToMove;
    protected int yPosToMove;
    protected boolean hasMoved = false;
    protected boolean eligibleForEnPassant = false;
    private String backgroundColor;
    private String textColor;
    protected String[] customLines = new String[3];

    public Piece(char color, int xPos, int yPos) {
        this.color = color;
        this.xPos = xPos;
        this.yPos = yPos;

        //Set ANSI escape codes for displaying piece color when printing board
        if (this.color == 'B') {
            this.backgroundColor = "\u001B[40m";
            this.textColor = "\u001B[37m";
        } else {
            this.backgroundColor = "\u001B[47m";
            this.textColor = "\u001B[30m";
        }
    }

    public void movePiece(int xPos, int yPos) {
        int oldX = this.xPos;
        int oldY = this.yPos;

        this.xPos = xPos;
        this.yPos = yPos;

        GamePlay.board[yPos][xPos] = GamePlay.board[oldY][oldX];
        GamePlay.board[oldY][oldX] = null;

        if (GamePlay.getEnPassantStatus()) { //If a pawn moved via En Passant, take remove the opposing pawn from the board
            GamePlay.board[oldY][xPos] = null;
        }

        clearEnPassantStatus();

        if (this.toString().equals("pawn")) { 
            this.eligibleForEnPassant = eligibleForEnPassant(oldY, yPos); //Check if the pawn is elligible to be taken via En Passant on the next move
        }

        if (GamePlay.getCastleStatus()) {
            if (this.xPos == 1) {
                GamePlay.board[this.yPos][0] = null;
                GamePlay.board[this.yPos][2] = new Rook(this.color, 2, this.yPos);
            } else {
                GamePlay.board[this.yPos][7] = null;
                GamePlay.board[this.yPos][4] = new Rook(this.color, 4, this.yPos);
            }
        }
    }

    public String getBackgroundColor() {
        return this.backgroundColor;
    }

    public String getTextColor() {
        return this.textColor;
    }

    public char getColor() {
        return this.color;
    }

    public void setXPosToMove(int xPosToMove) {
        this.xPosToMove = xPosToMove;
    }

    public void setYPosToMove(int yPosToMove) {
        this.yPosToMove = yPosToMove;
    }

    public int getXPosToMove() {
        return this.xPosToMove;
    }

    public int getYPosToMove() {
        return this.yPosToMove;
    }

    public int getXPos() {
        return this.xPos;
    }

    public int getYPos() {
        return this.yPos;
    }

    public String currentLocationToString() {
        String currentLocation = "";

        switch (this.xPos) {
            case 0:
                currentLocation += "h";
                break;
            case 1:
                currentLocation += "g";
                break;
            case 2:
                currentLocation += "f";
                break;
            case 3:
                currentLocation += "e";
                break;
            case 4:
                currentLocation += "d";
                break;
            case 5:
                currentLocation += "c";
                break;
            case 6:
                currentLocation += "b";
                break;
            case 7:
                currentLocation += "a";
                break;
            default:
                System.out.println("This should never happen. Something went really wrong...");
                break;
        }

        currentLocation += (this.yPos + 1);

        return currentLocation;
    }

    public String locationToMoveToString() {
        String locationToMove = "";

        switch (this.xPosToMove) {
            case 0:
                locationToMove += "h";
                break;
            case 1:
                locationToMove += "g";
                break;
            case 2:
                locationToMove += "f";
                break;
            case 3:
                locationToMove += "e";
                break;
            case 4:
                locationToMove += "d";
                break;
            case 5:
                locationToMove += "c";
                break;
            case 6:
                locationToMove += "b";
                break;
            case 7:
                locationToMove += "a";
                break;
            default:
                System.out.println("This should never happen. Something went really wrong...");
                break;
        }

        locationToMove += (this.yPosToMove + 1);

        return locationToMove;
    }

    public boolean isValidMove() {
        System.out.println("This should never occur. You really broke something bad.");
        return false;        
    }

    public boolean isInCheck() {
        System.out.println("This should never occur. You really broke something bad.");
        return true;
    }

    public void promotePawn() {
        System.out.println("This should never occur. You really broke something bad.");
    }

    protected boolean movesThroughPiece(int xDistance, int yDistance) {
        //Note: at this point, pieces only move laterally or diagnally (no knights)
        int i;
        int moveNegativeX; //this can be 1 or -1 to reverse the direction of travel
        int moveNegativeY; //this can be 1 or -1 to reverse the direction of travel

        if (this.yPosToMove > this.yPos) { //piece is moving upwards
                moveNegativeY = 1;
            } else {
                moveNegativeY = -1;
            }

        if (this.xPosToMove > this.xPos) {
            moveNegativeX = 1;
        } else {
            moveNegativeX = -1;
        }

        if (xDistance == 0) { //piece is moving up/down
            for (i = 1; i < yDistance; i++) { //Move one space at a time and check for any pieces (don't check last space)
                if (GamePlay.board[this.yPos + (i * moveNegativeY)][this.xPos] != null) { //if the checked space has a piece, return true
                    return true;
                }
            }
        } else if (yDistance == 0) { //piece is moving left/right
            for (i = 1; i < xDistance; i++) { //Move one space at a time and check for any pieces (don't check last space)
                if (GamePlay.board[this.yPos][this.xPos + (i * moveNegativeX)] != null) { //if the checked space has a piece, return true
                    return true;
                }
            }
        } else { //piece is moving diagnally
            //xDistance will always equal yDistance
            for (i = 1; i < xDistance; i++) { //Move one space at a time and check for any pieces (don't check last space)
                if (GamePlay.board[this.yPos + (i * moveNegativeY)][this.xPos + (i * moveNegativeX)] != null) { //if the checked space has a piece, return true
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean eligibleForEnPassant(int oldY, int yPos) {
        //If the piece is not a pawn, this value will not effect it
        return false;
    }

    private void clearEnPassantStatus() {
        for (int y = 0; y < GamePlay.board.length; y++) {
            for (int x = 0; x < GamePlay.board[0].length; x++) {
                if (GamePlay.board[y][x] != null) {
                    if (GamePlay.board[y][x].getColor() == this.getColor()) {
                        GamePlay.board[y][x].eligibleForEnPassant = false;
                    }                    
                }
            }
        }
    }
}
