import java.util.Scanner;

public class GamePlay {
    Scanner scnr = new Scanner(System.in);
    public static Piece[][] board;
    private static boolean movedViaEnPassant = false; //used on the rare occasion en passant actually happens
    private static boolean castledLastMove = false; //Used when a player castles to allow the rook to move as well
    public static boolean resigned = false;
    
    public void resetBoard() {
        //Make a clear board
        board = new Piece[8][8];

        Pawn pawn;
        Bishop bishop;
        Knight knight;
        Rook rook;
        Queen queen;
        King king;
        //Place white pawns
        for (int i = 0; i < 8; i++) {
            pawn = new Pawn('W', i, 1);
            board[1][i] = pawn;
        }
        //Place black pawns
        for (int i = 0; i < 8; i++) {
            pawn = new Pawn('B', i, 6);
            board[6][i] = pawn;
        }
        //Place white bishops
        for (int i = 2; i < 8; i += 3) {
            bishop = new Bishop('W', i, 0);
            board[0][i] = bishop;
        }
        //Place black bishops
        for (int i = 2; i < 8; i += 3) {
            bishop = new Bishop('B', i, 7);
            board[7][i] = bishop;
        }
        //Place white knights
        for (int i = 1; i < 8; i += 5) {
            knight = new Knight('W', i, 0);
            board[0][i] = knight;
        }
        //Place black knights
        for (int i = 1; i < 8; i += 5) {
            knight = new Knight('B', i, 7);
            board[7][i] = knight;
        }
        //Place white rooks
        for (int i = 0; i < 8; i += 7) {
            rook = new Rook('W', i, 0);
            board[0][i] = rook;
        }
        //Place black rooks
        for (int i = 0; i < 8; i += 7) {
            rook = new Rook('B', i, 7);
            board[7][i] = rook;
        }
        //Place white queen
        queen = new Queen('W', 4, 0);
        board[0][4] = queen;
        //Place black queen
        queen = new Queen('B', 4, 7);
        board[7][4] = queen;
        //Place white king
        king = new King('W', 3, 0);
        board[0][3] = king;
        //Place black king
        king = new King('B', 3, 7);
        board[7][3] = king;
    }

    private void printBlackBoard() {
        String boardToPrint = "";
        int rowNumber = 1;

        boolean firstSquareIsWhite = true;  //keeps track of the color of the first square of the row

        boardToPrint += "         h          g          f          e          d          c          b          a\n\n";
        //iterates through rows 1-8
        for (int y = 0; y < board.length; y++) { 
            int boardLineNum;       //keeps track of the lines printed for the whole row
            int pieceLineNum = 0;   //keeps track of the lines printed to display a piece and resets to zero every row

            //Alternates the first square of row being white or black
            if (firstSquareIsWhite) {
                firstSquareIsWhite = false;
            } else {
                firstSquareIsWhite = true;
            }

            //iterates through all 5 lines that make up a square
            for (boardLineNum = 0; boardLineNum < 5; boardLineNum++) {
                if (boardLineNum == 0 || boardLineNum == 4) {   //if looking at top or bottom line of the row
                    if (firstSquareIsWhite) {
                        boardToPrint += "               ###########           ###########           ###########           ###########   ";
                    } else {
                        boardToPrint += "    ###########           ###########           ###########           ###########              ";
                    }
                } else { //if one of the middle 3 lines of a row
                    for (int x = 0; x < board[0].length; x++) { //need to check each square for a piece
                        if (boardLineNum == 2 && x == 0) {  //If the first middle line of a square, we need the row letter and extra spaces
                            boardToPrint += rowNumber + "   ";
                        } else if (boardLineNum != 2 && x ==0) {    //If any other first square, we only need extra spaces
                            boardToPrint += "    ";
                        }

                        if (board[y][x] == null) {  //if there is no piece
                            if ((firstSquareIsWhite && (x % 2 == 0)) || (!firstSquareIsWhite && (x % 2 != 0))) {    //Determines color of current square
                                boardToPrint += "           ";
                            } else {
                                boardToPrint += "###########";
                            }
                        } else { //there is a piece on the square
                            String backgroundColor = board[y][x].getBackgroundColor();  //Stores an ANSI escape code to change the outputed text beckground
                            String textColor = board[y][x].getTextColor();
                            String RESET = "\u001B[0m"; //ANSI escape code to reset colored backgrounds to normal

                            if ((firstSquareIsWhite && (x % 2 == 0)) || (!firstSquareIsWhite && (x % 2 != 0))) {    //Determines color of current square
                                boardToPrint += ("  " + backgroundColor + "  " + textColor + board[y][x].customLines[pieceLineNum] + "  " + RESET + "  ");
                            } else {                                
                                boardToPrint += ("##" + backgroundColor + "  " + textColor + board[y][x].customLines[pieceLineNum] + "  " + RESET + "##");
                            }
                        }

                        if (boardLineNum == 2 && x == 7) {  //If the last middle line of a square, we need the row number and extra spaces
                            boardToPrint += "   " + rowNumber;
                        }                        
                    }
                    pieceLineNum++;
                }
                boardToPrint += "\n";

            }
            rowNumber++;
        }
        boardToPrint += "\n         h          g          f          e          d          c          b          a\n";
        System.out.print(boardToPrint);
    }

    private void printWhiteBoard() {
        String boardToPrint = "";
        int rowNumber = 8;

        boolean firstSquareIsWhite = false;  //keeps track of the color of the first square of the row

        boardToPrint += "         a          b          c          d          e          f          g          h\n\n";
        //iterates through rows 1-8
        for (int y = board.length - 1; y >= 0; y--) { 
            int boardLineNum;       //keeps track of the lines printed for the whole row
            int pieceLineNum = 0;   //keeps track of the lines printed to display a piece and resets to zero every row

            //Alternates the first square of row being white or black
            if (firstSquareIsWhite) {
                firstSquareIsWhite = false;
            } else {
                firstSquareIsWhite = true;
            }

            //iterates through all 5 lines that make up a square
            for (boardLineNum = 0; boardLineNum < 5; boardLineNum++) {
                if (boardLineNum == 0 || boardLineNum == 4) {   //if looking at top or bottom line of the row
                    if (firstSquareIsWhite) {
                        boardToPrint += "    ###########           ###########           ###########           ###########              ";
                    } else {
                        boardToPrint += "               ###########           ###########           ###########           ###########   ";
                    }
                } else { //if one of the middle 3 lines of a row
                    for (int x = board[0].length - 1; x >= 0; x--) { //need to check each square for a piece
                        if (boardLineNum == 2 && x == 7) {  //If the first middle line of a square, we need the row letter and extra spaces
                            boardToPrint += rowNumber + "   ";
                        } else if (boardLineNum != 2 && x == 7) {    //If any other first square, we only need extra spaces
                            boardToPrint += "    ";
                        }

                        if (board[y][x] == null) {  //if there is no piece
                            if ((firstSquareIsWhite && (x % 2 == 0)) || (!firstSquareIsWhite && (x % 2 != 0))) {    //Determines color of current square
                                boardToPrint += "           ";
                            } else {
                                boardToPrint += "###########";
                            }
                        } else { //there is a piece on the square
                            String backgroundColor = board[y][x].getBackgroundColor();  //Stores an ANSI escape code to change the outputed text beckground
                            String textColor = board[y][x].getTextColor();
                            String RESET = "\u001B[0m"; //ANSI escape code to reset colored backgrounds to normal
                            if ((firstSquareIsWhite && (x % 2 == 0)) || (!firstSquareIsWhite && (x % 2 != 0))) {    //Determines color of current square                                
                                boardToPrint += ("  " + backgroundColor + "  " + textColor + board[y][x].customLines[pieceLineNum] + "  " + RESET + "  ");
                            } else {                                
                                boardToPrint += ("##" + backgroundColor + "  " + textColor + board[y][x].customLines[pieceLineNum] + "  " + RESET + "##");
                            }
                        }

                        if (boardLineNum == 2 && x == 0) {  //If the last middle line of a square, we need the row number and extra spaces
                            boardToPrint += "   " + rowNumber;
                        }                        
                    }
                    pieceLineNum++;
                }
                boardToPrint += "\n";

            }
            rowNumber--;
        }
        boardToPrint += "\n         a          b          c          d          e          f          g          h\n";
        System.out.print(boardToPrint);
    }

    public void printBoard(char color) {
        if (color == 'W') {
            printWhiteBoard();
        } else if (color == 'B') {
            printBlackBoard();
        }
    }

    public Piece getPieceToMove(char player, String playerInput) {
        Piece pieceToMove;
        int currentXLocation;
        int currentYLocation;
        int charToInt; //Used as a placeholder when converting a char to an int

        //Convert letter input to board array index
        switch (playerInput.charAt(0)) {
            case 'a':
            case 'A':
                if (player == 'W') {
                    currentXLocation = 7;
                    break;
                } else {
                    currentXLocation = 7;
                    break;
                }
            case 'b':
            case 'B':
                currentXLocation = 6;
                break;
            case 'c':
            case 'C':
                currentXLocation = 5;
                break;
            case 'd':
            case 'D':
                currentXLocation = 4;
                break;
            case 'e':
            case 'E':
                currentXLocation = 3;
                break;
            case 'f':
            case 'F':
                currentXLocation = 2;
                break;
            case 'g':
            case 'G':
                currentXLocation = 1;
                break;
            case 'h':
            case 'H':
                currentXLocation = 0;
                break;
            default:
                System.out.println("Enter a valid piece. (Ex. e2 or e2 e4)");
                return null;
        }
            //Ensure number input falls within legal values
        try {
            charToInt = Character.getNumericValue(playerInput.charAt(1));
            if (charToInt >=1 && charToInt <= 8) {
                currentYLocation = charToInt - 1;
            } else {
                System.out.println("Enter a valid piece. (Ex. e2 or e2 e4)");
                return null;
            }
        }
        catch (Exception e) {
            System.out.println("Enter a valid piece. (Ex. e2 or e2 e4)");
            return null;
        }

        if (board[currentYLocation][currentXLocation] == null) { //Check if there is a piece on the selected spot
            System.out.println("There is no piece there. Please select a valid location.");
            return null;
        }

        pieceToMove = board[currentYLocation][currentXLocation];

        if (pieceToMove.getColor() == player) { //Make sure the player is trying to move their own piece
            return pieceToMove;
        }

        System.out.println("That is not your piece. Please select a valid location.");
        return null;
    }

    public boolean isProperLocation(String playerInput, Piece pieceToMove) {
        int charToInt; //Used as a placeholder when converting a char to an int
        int xLocation;
        int yLocation;

        //Convert letter input to board array index
        switch (playerInput.charAt(0)) {
            case 'a':
            case 'A':
                xLocation = 7;
                break;
            case 'b':
            case 'B':
                xLocation = 6;
                break;
            case 'c':
            case 'C':
                xLocation = 5;
                break;
            case 'd':
            case 'D':
                xLocation = 4;
                break;
            case 'e':
            case 'E':
                xLocation = 3;
                break;
            case 'f':
            case 'F':
                xLocation = 2;
                break;
            case 'g':
            case 'G':
                xLocation = 1;
                break;
            case 'h':
            case 'H':
                xLocation = 0;
                break;
            default:
                System.out.println("Enter a valid location.");
                return false;
        }
        //Ensure number input falls within legal values
        try {
            charToInt = Character.getNumericValue(playerInput.charAt(1));
            if (!(charToInt >=1 && charToInt <= 8)) {
                System.out.println("Enter a valid location.");
                return false;
            }
            yLocation = charToInt - 1;
        }
        catch (Exception e) {
            System.out.println("Enter a valid location.");
            return false;
        }

        if (board[yLocation][xLocation] != null) { //Don't get a null pointer exception error
            if (board[yLocation][xLocation].getColor() == pieceToMove.getColor()) {
                System.out.println("You cannot attack your own piece. Please enter a valid location");
                return false;
            }
        }
        //Set location to move for the piece object
        pieceToMove.setXPosToMove(xLocation);
        pieceToMove.setYPosToMove(yLocation);
        return true;
    }

    //Please don't touch this ever again
    public Piece getMove(char player) {
        Piece pieceToMove = null;
        String playerInput;
        String pieceToMoveInput;
        String locationToMoveInput;
        boolean isProperLocation;
        boolean resetLoop = false;

        System.out.println("Which piece would you like to move? (Ex. e2 or e2 e4)");
        System.out.println("You can type \"help\" at any time to view more turn options");
        playerInput = scnr.nextLine();
        playerInput = playerInput.replaceAll(" ", ""); //remove all spaces
        while (true) { //Get player input
            if (playerInput.toLowerCase().equals("resign")) {
                //RESIGN IS BROKEN IF FIRST INPUT IS INVALID
                System.out.println("Are you sure you want to resign? (y/n)");

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
                    GamePlay.resigned = true;
                    pieceToMove = null;
                    break; //will return pieceToMove = null;
                }

                //Get new input if they don't want to resign
                System.out.println("Which piece would you like to move? (Ex. e2 or e2 e4)");
                playerInput = scnr.nextLine();
                playerInput = playerInput.replaceAll(" ", ""); //remove all spaces
                continue;
            }
            if (playerInput.length() == 2) { //user only gave piece to move
                pieceToMoveInput = playerInput;
                locationToMoveInput = null; //resets this variable in case it has been initialized in a previous loop
                pieceToMove = getPieceToMove(player, pieceToMoveInput);
                while (pieceToMove == null) {
                    playerInput = scnr.nextLine();
                    playerInput = playerInput.replaceAll(" ", ""); //remove all spaces
                    if (playerInput.length() == 2) {
                        pieceToMoveInput = playerInput;
                        pieceToMove = getPieceToMove(player, pieceToMoveInput);
                        continue;
                    } else {
                        resetLoop = true;
                        break; //Goes back to the top of the while true loop to start the process over
                    }
                }
                if (resetLoop) {
                    resetLoop = false;
                    continue;
                }

                System.out.println("Where would you like to move the piece on " + pieceToMoveInput + "?");

                while (true) {
                    locationToMoveInput = scnr.nextLine();
                    locationToMoveInput.replaceAll(" ", ""); //remove all spaces

                    if (locationToMoveInput.length() == 2) {
                        break;
                    }

                    System.out.println("Enter a valid location to move.");
                }

                isProperLocation = isProperLocation(locationToMoveInput, pieceToMove); //returns true/false. If true, also sets location to move in the piece class

                while (!isProperLocation) {
                    playerInput = scnr.nextLine();
                    playerInput = playerInput.replaceAll(" ", ""); //remove all spaces
                    if (playerInput.length() == 2) {
                        locationToMoveInput = playerInput;
                        isProperLocation = isProperLocation(locationToMoveInput, pieceToMove);
                    } else if (playerInput.length() == 4) {
                        resetLoop = true;
                        break; //Goes back to the top of the while true loop to start the process over
                    } else {
                        System.out.println("Please enter a valid location to move.");
                    }
                }

                if (resetLoop) {
                    resetLoop = false;
                    continue;
                }
                break;
            } else if (playerInput.length() == 4) { //user gave piece and location to move
                //Split the input
                pieceToMoveInput = playerInput.substring(0, 2);
                locationToMoveInput = playerInput.substring(2);

                pieceToMove = getPieceToMove(player, pieceToMoveInput);

                while (pieceToMove == null) {
                    playerInput = scnr.nextLine();
                    playerInput = playerInput.replaceAll(" ", ""); //remove all spaces
                    if (playerInput.length() == 2 || playerInput.length() == 4) {
                        resetLoop = true;
                        break;
                    } else {
                        System.out.println("Enter a valid piece. (Ex. e2 or e2 e4)");
                    }
                }

                if (resetLoop) {
                    resetLoop = false;
                    continue;
                }

                isProperLocation = isProperLocation(locationToMoveInput, pieceToMove); //returns true/false. If true, also sets location to move in the piece class

                while (!isProperLocation) {
                    playerInput = scnr.nextLine();
                    playerInput = playerInput.replaceAll(" ", ""); //remove all spaces
                    if (playerInput.length() == 2) {
                        locationToMoveInput = playerInput;
                        isProperLocation = isProperLocation(locationToMoveInput, pieceToMove);
                    } else if (playerInput.length() == 4) {
                        resetLoop = true;
                        break; //Goes back to the top of the while true loop to start the process over
                    }
                }
                if (resetLoop) {
                    resetLoop = false;
                    continue;
                }

                break;
            } else { //Input is not 2 or 4 characters long
                System.out.println("Enter a proper input. (Ex. e2 or e2 e4)");
                playerInput = scnr.nextLine();
                playerInput = playerInput.replaceAll(" ", ""); //remove all spaces
            }
        }
        return pieceToMove;
    }

    public static boolean isInCheckAfterMove(Piece pieceToMove) { //determines if the player's king is in check after a move
        char player = pieceToMove.getColor();
        int originalXPos = pieceToMove.getXPos();
        int originalYPos = pieceToMove.getYPos();
        int xPosToMove = pieceToMove.getXPosToMove();
        int yPosToMove = pieceToMove.getYPosToMove();
        Piece playerKing = null;
        boolean isInCheck;

        Piece[][] originalBoard = new Piece[8][8];

        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[0].length; x++) {
                originalBoard[y][x] = board[y][x];
            }
        }

        pieceToMove.movePiece(xPosToMove, yPosToMove);
        
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[0].length; x++) {
                if (board[y][x] == null) {
                    continue;
                } else if (board[y][x].toString().equals("king") && board[y][x].getColor() == player) {
                    playerKing = board[y][x];
                    break;
                }
            }
        }

        isInCheck = playerKing.isInCheck();

        //return piece back to original position (Updates values in piece class, do not remove)
        pieceToMove.movePiece(originalXPos, originalYPos);

        //return board back to normal
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[0].length; x++) {
                board[y][x] = originalBoard[y][x];
            }
        }

        return isInCheck;
    }

    public static void setEnPassantStatus(boolean enPassantStatus) {
        GamePlay.movedViaEnPassant = enPassantStatus;
    }

    public static boolean getEnPassantStatus() {
        return GamePlay.movedViaEnPassant;
    }

    public static void setCastleStatus(boolean castleStatus) {
        GamePlay.castledLastMove = castleStatus;
    }

    public static boolean getCastleStatus() {
        return GamePlay.castledLastMove;
    }

    public boolean isCheckmate(char color) {

        //check all pieces on the board
        for (Piece[] rows : board) {
            for (Piece piece : rows) {
                //Check every space on the board, skipping spaces that have nothing
                if (piece == null) {
                    continue;
                }

                if (piece.getColor() != color) { //Don't worry about the other side's pieces
                    continue;
                }

                if (piece.toString().equals("king")) {
                    if (!piece.isInCheck()) {//If you are not in check, you cannot be in checkmate
                        return false;
                    }
                }

                
                //Loop through all spaces on the chessboard
                for (int y = 0; y < 8; y++) {
                    piece.yPosToMove = y;
                    for (int x = 0; x < 8; x++) {
                        piece.xPosToMove = x;
                        if (!piece.isValidMove()) { //Skip if the move is not legal
                            continue;
                        }
                        if (board[y][x] != null) { //Don't get a null pointer exception error
                            if (board[y][x].getColor() == color) { //Make sure the piece isn't taking its own color
                                continue;
                            }
                        }
                        if (!isInCheckAfterMove(piece)) { //Return false if the move can stop checkmate
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean isStalemate(char color) {
        for (Piece[] rows : board) {
            for (Piece piece : rows) {
                //Check every space on the board, skipping spaces that have nothing or are of opposite color
                if (piece == null || piece.getColor() != color) {
                    continue;
                }
                //Loop through all spaces on the chessboard
                for (int y = 0; y < 8; y++) {
                    piece.setYPosToMove(y);
                    for (int x = 0; x < 8; x++) {
                        piece.setXPosToMove(x);
                        if (piece.isValidMove()) { //Find a legal move
                            if (board[y][x] != null) { //Don't get a null pointer exception error
                                if (board[y][x].getColor() != color) { //Make sure the piece isn't taking its own color
                                    if (!isInCheckAfterMove(piece)) { //Make sure the move won't put you in check
                                        return false; //If there is a move to be played, return false
                                    }
                                }
                            } else { //There is no piece on the square
                                if (!isInCheckAfterMove(piece)) { //Make sure the move won't put you in check
                                        return false; //If there is a move to be played, return false
                                    }
                            }
                        }
                    }
                }
            }
        }
        return true; //Returns true if there are no legal moves
    }
}