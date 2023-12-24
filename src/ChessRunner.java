import java.util.Scanner;

public class ChessRunner {
    public static void main(String[] args) {
        ChessRunner chess = new ChessRunner();
        chess.start();
    }

    GamePlay game = new GamePlay();
    Piece pieceToMove;

    Scanner scnr = new Scanner(System.in);

    public void start() {
        char player = 'W';
        int xPosToMove;
        int yPosToMove;
        //These are used when printing your move confirmation
        String pieceName;
        String currentLocation;
        String locationToMove;
        String playerInput = "";

        game.resetBoard();
        game.printBoard(player);
        
        while (true) {
            if (game.isCheckmate(player)) {
                String winner;

                if (player == 'W') {
                    winner = "Black";
                } else {
                    winner = "White";
                }

                System.out.println("Checkmate!");
                System.out.println(winner + " wins!");
                System.out.println("");
                break;
            }

            if (game.isStalemate(player)) {
                System.out.println("Stalemate!");
                System.out.println("");
                break;
            }

            while (true) { //Get player's move and double check that it is correct
                pieceToMove = game.getMove(player);

                if (pieceToMove == null) {
                    break;
                }
                pieceName = pieceToMove.toString();
                currentLocation = pieceToMove.currentLocationToString();
                locationToMove = pieceToMove.locationToMoveToString();
                GamePlay.setEnPassantStatus(false); //Make sure this value cannot remain true if the player changes moves last minute
                GamePlay.setCastleStatus(false); //Make sure this value cannot remain true if the player changes moves last minute

                System.out.printf("Move the %s on %s to %s? (y/n)\n", pieceName, currentLocation, locationToMove);

                while (true) {  //Input validation
                    playerInput = scnr.nextLine();
                    playerInput = playerInput.toLowerCase();

                    if (playerInput.length() < 1) {
                        System.out.println("Please enter either 'y' or 'n'");
                        continue;
                    }

                    if (playerInput.charAt(0) != 'y' && playerInput.charAt(0) != 'n') {
                        System.out.println("Please enter either 'y' or 'n'");
                        continue;
                    }

                    break;
                }

                if (playerInput.charAt(0) == 'y') {
                    break;
                }
            }

            System.out.println(""); //just adds a little white space

            if (GamePlay.resigned == true) {
                GamePlay.resigned = false;
                String winner = "White";
                String loser = "Black";

                if (player == 'W') {
                    winner = "Black";
                    loser = "White";
                }

                System.out.println(loser + " resigned.");
                System.out.println(winner + " wins!");
                System.out.println("");
                break;
            }

            xPosToMove = pieceToMove.getXPosToMove();
            yPosToMove = pieceToMove.getYPosToMove();

            if (!pieceToMove.isValidMove()) {
                System.out.println("You cannot move there.");
                continue;
            }

            if (GamePlay.isInCheckAfterMove(pieceToMove)) {
                System.out.println("That move leaves your king in check.");
                continue;
            }

            pieceToMove.movePiece(xPosToMove, yPosToMove);
            pieceToMove.hasMoved = true;
            GamePlay.setCastleStatus(false); //This was a super painful bug. Do not remove!
            GamePlay.setEnPassantStatus(false); //Probably don't need this, but adding it because of the bug stated above

            if (pieceToMove.toString().equals("pawn")) {
                if (pieceToMove.getColor() == 'W' && pieceToMove.getYPos() == 7) {
                    pieceToMove.promotePawn();
                } else if (pieceToMove.getColor() == 'B' && pieceToMove.getYPos() == 0) {
                    pieceToMove.promotePawn();
                }
            }

            //Change turn
            if (player == 'W') {
                player = 'B';
            } else {
                player = 'W';
            }
            
            game.printBoard(player);
        }

        System.out.println("Would you like to play another game of chess? (y/n)");

        while (true) {  //Input validation
            playerInput = scnr.nextLine();
            playerInput = playerInput.toLowerCase();
            if (playerInput.length() < 1) {
                System.out.println("Please enter either 'y' or 'n'");
                continue;
            }

            if (playerInput.charAt(0) != 'y' && playerInput.charAt(0) != 'n') {
                System.out.println("Please enter either 'y' or 'n'");
                continue;
            }

            break;
        }

        if (playerInput.charAt(0) == 'y') {
            this.start();
        }
    }
}