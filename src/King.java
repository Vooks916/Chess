public class King extends Piece {
    public King(char color, int xPos, int yPos) {
        super(color, xPos, yPos);
        customLines[0] = ". .";
        customLines[1] = "|< ";
        customLines[2] = "' `";
    }

    public String toString() {
        return "king";
    }

    public boolean isValidMove() {
        //King can move one space in any direction
        //TRY TO ADD CASTLING IN FUTURE

        //If king is trying to castle
        if ((Math.abs(this.xPos - this.xPosToMove) == 2) && (Math.abs(this.yPos - this.yPosToMove) == 0)) {
            if (canCastle()) {
                return true;
            }
            return false;
        }

        if (Math.abs(this.xPos - this.xPosToMove) > 1) { //If king is trying to move more than one space left/right
            return false;
        }

        if (Math.abs(this.yPos - this.yPosToMove) > 1) { //If king is trying to move more than one space up/down
            return false;
        }

        return true;
    }

    private boolean upDownCheck() {
        for (int i = this.yPos - 1; i >= 0; i--) {
            if (GamePlay.board[i][this.xPos] == null) { //If there is no piece on that space, continue
                continue;
            } else if (GamePlay.board[i][this.xPos].getColor() == this.getColor()) { //If the first piece contacted is your own, break
                break;
            } else { //The piece contacted is your opponents
                //rooks and queens can move up/down
                if (GamePlay.board[i][this.xPos].toString().equals("rook")) {
                    return true;
                }
                if (GamePlay.board[i][this.xPos].toString().equals("queen")) {
                    return true;
                }
                break;
            }
        }

        for (int i = this.yPos + 1; i < GamePlay.board.length; i++) {
            if (GamePlay.board[i][this.xPos] == null) { //If there is no piece on that space, continue
                continue;
            } else if (GamePlay.board[i][this.xPos].getColor() == this.getColor()) { //If the first piece contacted is your own, break
                break;
            } else { //The piece contacted is your opponents
                //rooks and queens can move up/down
                if (GamePlay.board[i][this.xPos].toString().equals("rook")) {
                    return true;
                }
                if (GamePlay.board[i][this.xPos].toString().equals("queen")) {
                    return true;
                }
                break;
            }
        }

        return false;
    }

    private boolean leftRightCheck() {
        for (int i = this.xPos - 1; i >= 0; i--) {
            if (GamePlay.board[this.yPos][i] == null) { //If there is no piece on that space, continue
                continue;
            } else if (GamePlay.board[this.yPos][i].getColor() == this.getColor()) { //If the first piece contacted is your own, break
                break;
            } else { //The piece contacted is your opponents
                //rooks and queens can move up/down
                if (GamePlay.board[this.yPos][i].toString().equals("rook")) {
                    return true;
                }
                if (GamePlay.board[this.yPos][i].toString().equals("queen")) {
                    return true;
                }
                break;
            }
        }

        for (int i = this.xPos + 1; i < GamePlay.board.length; i++) {
            if (GamePlay.board[this.yPos][i] == null) { //If there is no piece on that space, continue
                continue;
            } else if (GamePlay.board[this.yPos][i].getColor() == this.getColor()) { //If the first piece contacted is your own, break
                break;
            } else { //The piece contacted is your opponents
                //rooks and queens can move up/down
                if (GamePlay.board[this.yPos][i].toString().equals("rook")) {
                    return true;
                }
                if (GamePlay.board[this.yPos][i].toString().equals("queen")) {
                    return true;
                }
                break;
            }
        }

        return false;
    }

    private boolean rightDiagnalCheck() {
        int x = this.getXPos() + 1;
        int y = this.getYPos() + 1;

        while (x < 8 && y < 8) {
            if (GamePlay.board[y][x] == null) { //If there is no piece on that space, continue
                x++;
                y++;
                continue;
            } else if (GamePlay.board[y][x].getColor() == this.getColor()) { //If the first piece contacted is your own, break
                break;
            } else { //The piece contacted is your opponents
                //bishops and queens can move diagnal
                if (GamePlay.board[y][x].toString().equals("bishop")) {
                    return true;
                }
                if (GamePlay.board[y][x].toString().equals("queen")) {
                    return true;
                }
                break;
            }
        }

        x = this.getXPos() - 1;
        y = this.getYPos() - 1;

        while (x >= 0 && y >= 0) {
            if (GamePlay.board[y][x] == null) { //If there is no piece on that space, continue
                x--;
                y--;
                continue;
            } else if (GamePlay.board[y][x].getColor() == this.getColor()) { //If the first piece contacted is your own, break
                break;
            } else { //The piece contacted is your opponents
                //bishops and queens can move diagnal
                if (GamePlay.board[y][x].toString().equals("bishop")) {
                    return true;
                }
                if (GamePlay.board[y][x].toString().equals("queen")) {
                    return true;
                }
                break;
            }
        }
        return false;
    }

    private boolean leftDiagnalCheck() {
        int x = this.getXPos() - 1;
        int y = this.getYPos() + 1;

        while (x >= 0 && y < 8) {
            if (GamePlay.board[y][x] == null) { //If there is no piece on that space, continue
                x--;
                y++;
                continue;
            } else if (GamePlay.board[y][x].getColor() == this.getColor()) { //If the first piece contacted is your own, break
                break;
            } else { //The piece contacted is your opponents
                //bishops and queens can move diagnal
                if (GamePlay.board[y][x].toString().equals("bishop")) {
                    return true;
                }
                if (GamePlay.board[y][x].toString().equals("queen")) {
                    return true;
                }
                break;
            }
        }

        x = this.getXPos() + 1;
        y = this.getYPos() - 1;

        while (x < 8 && y >= 0) {
            if (GamePlay.board[y][x] == null) { //If there is no piece on that space, continue
                x++;
                y--;
                continue;
            } else if (GamePlay.board[y][x].getColor() == this.getColor()) { //If the first piece contacted is your own, break
                break;
            } else { //The piece contacted is your opponents
                //bishops and queens can move diagnal
                if (GamePlay.board[y][x].toString().equals("bishop")) {
                    return true;
                }
                if (GamePlay.board[y][x].toString().equals("queen")) {
                    return true;
                }
                break;
            }
        }
        return false;
    }

    private boolean knightCheck() {
        int x = this.xPos;
        int y = this.yPos;

        //I can't think of a better way to do this than just manually right now
        //Sorry for the mess of if-statments future me

        if (x + 2 < 8) { //Covers everything to the right of king
            if (y + 1 < 8) { //Covers the upper half
                if (GamePlay.board[y+1][x+2] != null) {
                    if (GamePlay.board[y+1][x+2].getColor() != this.getColor()) {
                        if (GamePlay.board[y+1][x+2].toString().equals("knight")) {
                            return true;
                        }
                    }
                }
            }
            if (y - 1 >= 0) { //Covers the lower half
                if (GamePlay.board[y-1][x+2] != null) {
                    if (GamePlay.board[y-1][x+2].getColor() != this.getColor()) {
                        if (GamePlay.board[y-1][x+2].toString().equals("knight")) {
                            return true;
                        }
                    }
                }
            }
        }
        if (x - 2 >= 0) { //Covers everything to the left of king
            if (y + 1 < 8) { //Covers the upper half
                if (GamePlay.board[y+1][x-2] != null) {
                    if (GamePlay.board[y+1][x-2].getColor() != this.getColor()) {
                        if (GamePlay.board[y+1][x-2].toString().equals("knight")) {
                            return true;
                        }
                    }
                }
            }
            if (y - 1 >= 0) { //Covers the lower half
                if (GamePlay.board[y-1][x-2] != null) {
                    if (GamePlay.board[y-1][x-2].getColor() != this.getColor()) {
                        if (GamePlay.board[y-1][x-2].toString().equals("knight")) {
                            return true;
                        }
                    }
                }
            }
        }
        if (y + 2 < 8) { //Covers everything above the king
            if (x + 1 < 8) { //Covers the right half
                if (GamePlay.board[y+2][x+1] != null) {
                    if (GamePlay.board[y+2][x+1].getColor() != this.getColor()) {
                        if (GamePlay.board[y+2][x+1].toString().equals("knight")) {
                            return true;
                        }
                    }
                }
            }
            if (x - 1 >= 0) { //Covers the left half
                if (GamePlay.board[y+2][x-1] != null) {
                    if (GamePlay.board[y+2][x-1].getColor() != this.getColor()) {
                        if (GamePlay.board[y+2][x-1].toString().equals("knight")) {
                            return true;
                        }
                    }
                }
            }
        }
        if (y - 2 >= 0) { //Covers everything below the king
            if (x + 1 < 8) { //Covers the right half
                if (GamePlay.board[y-2][x+1] != null) {
                    if (GamePlay.board[y-2][x+1].getColor() != this.getColor()) {
                        if (GamePlay.board[y-2][x+1].toString().equals("knight")) {
                            return true;
                        }
                    }
                }
            }
            if (x - 1 >= 0) { //Covers the left half
                if (GamePlay.board[y-2][x-1] != null) {
                    if (GamePlay.board[y-2][x-1].getColor() != this.getColor()) {
                        if (GamePlay.board[y-2][x-1].toString().equals("knight")) {
                            return true;
                        }
                    }
                }
            }
        }
        return false; //Yay, it made it through that monstrosity of a method
    }

    private boolean kingCheck() {
        int kingX = this.getXPos();
        int kingY = this.getYPos();
        int x;
        int y;

        for (x = -1; x <= 1; x++) { //Iterates from the left to the right column around the king
            if ((kingX + x) >= 0 && (kingX + x) < 8) { //Makes sure it is checking squares on the board
                for (y = -1; y <= 1; y++) { //Iterates from the bottom to the top row around the king
                    if ((kingY + y) >= 0 && (kingY + y) < 8) { //Makes sure it is checking squares on the board
                        if (GamePlay.board[kingY + y][kingX + x] != null) { //Makes sure there is a piece on the square being checked
                            if (GamePlay.board[kingY + y][kingX + x].getColor() != this.getColor()) { //Makes sure the piece is the opponents
                                if (GamePlay.board[kingY + y][kingX + x].toString().equals("king")) { //Checks if the piece is a king
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }

        return false; 
    }

    public boolean pawnCheck() {
        int sideChanger; //Yeah, that's the best name I can think of...

        //Sets the direction to check for pawn moves depending on color
        if (this.getColor() == 'W') {
            sideChanger = 1;
        } else {
            sideChanger = -1;
        }

        if (this.yPos + sideChanger >= 0 && this.yPos + sideChanger < 8) { //Make sure the square in front of the king is on the board
            for (int i = -1; i <= 1; i += 2) { //Check the front left and right squares
                if ((this.xPos + i >= 0) && (this.xPos + i < 8)) { //Make sure the square checked is on the board
                    if (GamePlay.board[this.yPos + sideChanger][this.xPos + i] != null) {
                        if (GamePlay.board[this.yPos + sideChanger][this.xPos + i].getColor() != this.getColor()) {
                            if (GamePlay.board[this.yPos + sideChanger][this.xPos + i].toString().equals("pawn")) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isInCheck() {
        //If a check is coming from any direction, return true
        if (upDownCheck() || leftRightCheck() || rightDiagnalCheck() || leftDiagnalCheck() || knightCheck() || kingCheck() || pawnCheck()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean canCastle() {
        //First find the rook it is castling with
        Piece rookToCastle;

        if (this.xPos > this.xPosToMove) {
            rookToCastle = GamePlay.board[this.yPos][0];
        } else {
            rookToCastle = GamePlay.board[this.yPos][7];
        }

        //If there is not a rook on the requested square, return false
        if (rookToCastle == null || !(rookToCastle.toString().equals("rook"))) {
            return false;
        }

        //If the king or the rook has moved, return false
        if (this.hasMoved || rookToCastle.hasMoved) {
            return false;
        }

        //If there are pieces in between the king and rook, return false
        if (this.xPos > this.xPosToMove) {
            for (int i = this.xPos - 1; i > 0; i--) {
                if (GamePlay.board[this.yPos][i] != null) {
                    return false;
                }
            }
        } else {
            for (int i = this.xPos + 1; i < 7; i++) {
                if (GamePlay.board[this.yPos][i] != null) {
                    return false;
                }
            }
        }

        //If the king is in check, return false
        if (this.isInCheck()) {
            return false;
        }

        //If the king will pass through or end in check, return false
        int originalXPos = this.xPos;
        int originalXPosToMove = this.xPosToMove;

        if (this.xPos > this.xPosToMove) {
            //preset the variables to be ready for the for loop
            this.xPosToMove = this.xPos - 1;
            if (GamePlay.isInCheckAfterMove(this)) {
                this.xPos = originalXPos;
                this.xPosToMove = originalXPosToMove;
                return false;
            }
        } else {
            //preset the variables to be ready for the for loop
            this.xPosToMove = this.xPos + 1;
            if (GamePlay.isInCheckAfterMove(this)) {
                this.xPos = originalXPos;
                this.xPosToMove = originalXPosToMove;
                return false;
            }
        }

        this.xPos = originalXPos;
        this.xPosToMove = originalXPosToMove;
        GamePlay.setCastleStatus(true); //Set castle status to true to allow the rook to move as well
        return true;
    }
}
