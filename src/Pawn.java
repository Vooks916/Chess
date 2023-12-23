import java.util.Scanner;

public class Pawn extends Piece {
    Scanner scnr = new Scanner(System.in);

    public Pawn(char color, int xPos, int yPos) {
        super(color, xPos, yPos);
        customLines[0] = ".-.";
        customLines[1] = "|-'";
        customLines[2] = "'  ";
    }

    public String toString() {
        return "pawn";
    }

    public boolean isValidMove() {
        int xDistance;
        int yDistance;

        xDistance = Math.abs(this.xPos - this.xPosToMove);
        yDistance = this.yPos - this.yPosToMove;    //Don't take abs value because direction matters

        //First make sure the pawn only moves forward
        if (this.getColor() == 'W') {
            if (yDistance >= 0) { //if white pawn trying to move backwards
                return false;
            }
        } else {
            if (yDistance <= 0) { //if black pawn trying to move backwards
                return false;
            }
        }

        //pawns can never move more than two spaces forward
        if (Math.abs(yDistance) > 2) {
            return false;
        }

        //pawns can only move forward a distance of two on their first move
        if (Math.abs(yDistance) == 2) {
            if (this.hasMoved) {
                return false;
            }
        }

        //pawns can never move more than one space left/right
        if (xDistance > 1) {
            return false;
        }

        if ((Math.abs(yDistance) == 1 || Math.abs(yDistance) == 2) && xDistance == 0) { //pawns cannot take the piece directly ahead
            if (GamePlay.board[yPosToMove][xPosToMove] != null) {
                return false;
            }
        }

        //if pawn is trying to move left/right one space
        if (xDistance == 1) {
            //pawns can only attack when moving forward one space
            if (Math.abs(yDistance) == 2) {
                return false;
            }

            if (GamePlay.board[yPosToMove][xPosToMove] == null) { //If there is no piece on the diagnal attack
                //Check if en passant is possible
                if (GamePlay.board[this.yPos][xPosToMove] != null) { //Make sure there is a piece to the side of the pawn
                    if (GamePlay.board[this.yPos][xPosToMove].getColor() != this.getColor()) { //Make sure the piece is your opponents
                        if (GamePlay.board[this.yPos][xPosToMove].eligibleForEnPassant) { //If the piece is eligible to be taken via en passant
                            GamePlay.setEnPassantStatus(true); //Let the game know a piece could be taken next move via en passant
                            return true;
                        }
                    }
                }
                return false;   //you cannot move diagnal without attacking a piece
            }
        }

        if (movesThroughPiece(xDistance, Math.abs(yDistance))) { //must take abs value because we didn't earlier
            return false;
        }

        return true;
    }

    public void promotePawn() {
        String promotionPiece;
        int x = this.xPos;
        int y = this.yPos;
        char color = this.getColor();
        String location = this.currentLocationToString();
        System.out.printf("What piece would you like to promote your pawn on %s to?\n", location);
        System.out.print("Please enter the one letter piece abbreviation: ");

        while (true) {
            promotionPiece = scnr.nextLine();
            if (promotionPiece.length() != 1) {
                System.out.println("\nEnter a valid one letter piece abbreviation (ex. Q)");
                continue;
            }

            promotionPiece = promotionPiece.toLowerCase();

            switch (promotionPiece) {
                case "p":
                    System.out.println("\nI don't think promoting to a pawn is going to help you here...");
                    System.out.println("Enter a valid one letter piece abbreviation (ex. Q)");
                    continue;
                case "k":
                    System.out.println("\nNice try!");
                    System.out.println("Enter a valid one letter piece abbreviation (ex. Q)");
                    continue;
                case "q":
                    GamePlay.board[y][x] = new Queen(color, x, y);
                    break;
                case "r":
                    GamePlay.board[y][x] = new Rook(color, x, y);
                    break;
                case "n":
                    GamePlay.board[y][x] = new Knight(color, x, y);
                    break;
                case "b":
                    GamePlay.board[y][x] = new Bishop(color, x, y);
                    break;
                default:
                    System.out.println("\nEnter a valid one letter piece abbreviation (ex. Q)");
                    continue;
            }
            break;
        }
    }

    protected boolean eligibleForEnPassant(int oldY, int yPos) {
        //If a pawn ever moves forward two spaces, it is eligible to be taken by en passant on the next move
        if (Math.abs(oldY - yPos) == 2) {
            return true;
        }

        return false;
    }
 }
