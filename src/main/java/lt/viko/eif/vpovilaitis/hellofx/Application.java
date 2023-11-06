package lt.viko.eif.vpovilaitis.hellofx;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {

    private char[][] board;
    private char currentPlayer;
    private char playerChoice = 'X';
    private int boardSize = 3;
    private GridPane gridPane;
    private Stage primaryStage;
    private boolean gameEnded;

    public Application() {
        currentPlayer = 'X';
        initializeGame();
    }

    private void initializeGame() {
        board = new char[boardSize][boardSize];
        gameEnded = false;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = ' ';
            }
        }
    }

    private void displayBoard() {
        gridPane.getChildren().clear();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Button button = new Button(String.valueOf(board[i][j]));
                button.setPrefSize(100, 100);
                int row = i;
                int col = j;
                button.setOnAction(e -> onButtonClick(button, row, col));
                gridPane.add(button, j, i);
            }
        }
    }

    private void onButtonClick(Button button, int row, int col) {
        if (board[row][col] == ' ' && !gameEnded) {
            board[row][col] = currentPlayer;
            button.setText(String.valueOf(currentPlayer));
            if (isGameWon(currentPlayer)) {
                System.out.println(currentPlayer + " wins!");
                gameEnded = true;
                disableAllButtons();
            } else if (isGameDraw()) {
                System.out.println("It's a draw!");
                gameEnded = true;
            } else {
                switchPlayer();
            }
        }
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    private boolean isGameWon(char player) {

        for (int i = 0; i < boardSize; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true;
            }
        }

        if (boardSize == 3 && ((board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
                (board[0][2] == player && board[1][1] == player && board[2][0] == player))) {
            return true;
        } else if (boardSize == 4 && ((board[0][0] == player && board[1][1] == player && board[2][2] == player && board[3][3] == player) ||
                (board[0][3] == player && board[1][2] == player && board[2][1] == player && board[3][0] == player))) {
            return true;
        } else if (boardSize == 5 && ((board[0][0] == player && board[1][1] == player && board[2][2] == player && board[3][3] == player && board[4][4] == player) ||
                (board[0][4] == player && board[1][3] == player && board[2][2] == player && board[3][1] == player && board[4][0] == player))) {
            return true; 
        }
        return false;
    }

    private boolean isGameDraw() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private void selectPlayerSymbol(char choice) {
        playerChoice = choice;
        currentPlayer = playerChoice;
        initializeGame();
        displayBoard();
        primaryStage.setScene(new Scene(gridPane, boardSize * 100, boardSize * 100));
    }

    private void selectBoardSize(int size) {
        boardSize = size;
        initializeGame();
        displayBoard();
        primaryStage.setScene(new Scene(gridPane, boardSize * 100, boardSize * 100));
    }

    private void disableAllButtons() {
        for (int i = 0; i < gridPane.getChildren().size(); i++) {
            Button button = (Button) gridPane.getChildren().get(i);
            button.setDisable(true);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        gridPane = new GridPane();

        Button selectX = new Button("Play as X");
        selectX.setOnAction(e -> selectPlayerSymbol('X'));

        Button selectO = new Button("Play as O");
        selectO.setOnAction(e -> selectPlayerSymbol('O'));

        Button select3x3 = new Button("3x3");
        select3x3.setOnAction(e -> selectBoardSize(3));

        Button select4x4 = new Button("4x4");
        select4x4.setOnAction(e -> selectBoardSize(4));

        Button select5x5 = new Button("5x5");
        select5x5.setOnAction(e -> selectBoardSize(5));

        VBox selectionBox = new VBox(10, selectX, selectO, select3x3, select4x4, select5x5);
        selectionBox.setAlignment(Pos.CENTER);

        Scene selectionScene = new Scene(selectionBox, 200, 200);
        primaryStage.setTitle("Choose X or O and Board Size");
        primaryStage.setScene(selectionScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}