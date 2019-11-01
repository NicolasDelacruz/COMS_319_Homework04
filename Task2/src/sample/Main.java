/**
 * @author Nicolas De la Cruz
 * @date October 31st, 2019
 */
package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {
    /**
     * playerTurn tracks current players turn
     */
    int playerTurn = 1;

    /**
     * buttonsClicked keeps track of which buttons are disabled for next turns
     */
    ArrayList<Button> buttonsClicked = new ArrayList<>();

    /**
     * buttonsList is a list of all the buttons used for tic tac toe in-game
     */
    ArrayList<Button> buttonsList = new ArrayList<>();

    /**
     * board is a 2d array used to keep track of board state
     */
    int board[][] = new int[3][3];

    /**
     * score is a text view used to display win, draw, and current turn messages
     */
    Text score = new Text();

    /**
     * givenStage is a reference to the primaryStage
     */
    Stage givenStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(new Scene(root, 600, 700));
        givenStage = primaryStage;

        score = new Text();
        score.prefHeight(100);
        score.setText("Current turn: " + playerTurn);

        Button button1 = new Button();
        button1.setPrefWidth(200);
        button1.setPrefHeight(200);
        button1.setId("1");
        buttonsList.add(button1);

        Button button2 = new Button();
        button2.setPrefWidth(200);
        button2.setPrefHeight(200);
        button2.setId("2");
        buttonsList.add(button2);

        Button button3 = new Button();
        button3.setPrefWidth(200);
        button3.setPrefHeight(200);
        button3.setId("3");
        buttonsList.add(button3);

        Button button4 = new Button();
        button4.setPrefWidth(200);
        button4.setPrefHeight(200);
        button4.setId("4");
        buttonsList.add(button4);

        Button button5 = new Button();
        button5.setPrefWidth(200);
        button5.setPrefHeight(200);
        button5.setId("5");
        buttonsList.add(button5);

        Button button6 = new Button();
        button6.setPrefWidth(200);
        button6.setPrefHeight(200);
        button6.setId("6");
        buttonsList.add(button6);

        Button button7 = new Button();
        button7.setPrefWidth(200);
        button7.setPrefHeight(200);
        button7.setId("7");
        buttonsList.add(button7);

        Button button8 = new Button();
        button8.setPrefWidth(200);
        button8.setPrefHeight(200);
        button8.setId("8");
        buttonsList.add(button8);

        Button button9 = new Button();
        button9.setPrefWidth(200);
        button9.setPrefHeight(200);
        button9.setId("9");
        buttonsList.add(button9);

        for(Button button : buttonsList){
            setButtonEventHandler(button, score);
        }

        GridPane gridPane = new GridPane();

        gridPane.add(button1, 0, 0, 1, 1);
        gridPane.add(button2, 1, 0, 1, 1);
        gridPane.add(button3, 2, 0, 1, 1);
        gridPane.add(button4, 0, 1, 1, 1);
        gridPane.add(button5, 1, 1, 1, 1);
        gridPane.add(button6, 2, 1, 1, 1);
        gridPane.add(button7, 0, 2, 1, 1);
        gridPane.add(button8, 1, 2, 1, 1);
        gridPane.add(button9, 2, 2, 1, 1);
        gridPane.add(score, 0, 3, 1, 1);

        Scene grid = new Scene(gridPane, 500, 500);
        primaryStage.setScene(grid);

        primaryStage.show();
    }

    /**
     * setButtonEventHandler sets all the handlers for the buttons for tic tac toe game
     * @param currentButton
     * @param givenScore
     */
    public void setButtonEventHandler(Button currentButton, Text givenScore){
        currentButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if(playerTurn == 1 && !buttonsClicked.contains(currentButton)){
                    Image image = new Image(getClass().getResourceAsStream("/img/x.jpg"), 100, 100, true, true);
                    currentButton.setGraphic(new ImageView(image));
                    buttonsClicked.add(currentButton);
                    updateBoard(currentButton, playerTurn);
                    try {
                        checkWin();
                        checkDraw();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    playerTurn = 2;
                }
                else if(playerTurn == 2 && !buttonsClicked.contains(currentButton)){
                    Image image = new Image(getClass().getResourceAsStream("/img/o.jpg"), 100, 100, true, true);
                    currentButton.setGraphic(new ImageView(image));
                    buttonsClicked.add(currentButton);
                    updateBoard(currentButton, playerTurn);
                    try {
                        checkWin();
                        checkDraw();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    playerTurn = 1;
                }

                givenScore.setText("Current turn: " + playerTurn);
            }
        });
    }

    /**
     * updateBoard updates the 2d array of the board with the most recent move
     * @param givenButton
     * @param givenTurn
     */
    public void updateBoard(Button givenButton, int givenTurn){
        int id = Integer.valueOf(givenButton.getId());
        switch (id){
            case 1:
                board[0][0] = givenTurn;
                break;
            case 2:
                board[0][1] = givenTurn;
                break;
            case 3:
                board[0][2] = givenTurn;
                break;
            case 4:
                board[1][0] = givenTurn;
                break;
            case 5:
                board[1][1] = givenTurn;
                break;
            case 6:
                board[1][2] = givenTurn;
                break;
            case 7:
                board[2][0] = givenTurn;
                break;
            case 8:
                board[2][1] = givenTurn;
                break;
            case 9:
                board[2][2] = givenTurn;
                break;
        }
    }

    /**
     * checkWin checks for win condition
     * @throws Exception
     */
    public void checkWin() throws Exception {
        int streak = 0;
        //horizontal win
        for(int i = 0; i < board.length; ++i){
            for(int j = 0; j < board.length; ++j){
                if(board[i][j] == playerTurn)
                    streak++;
            }
            if(streak == 3){
                handleWin();
                return;
            }
            else{
                streak = 0;
            }
        }

        //horizontal win
        for(int i = 0; i < board.length; ++i){
            for(int j = 0; j < board.length; ++j){
                if(board[j][i] == playerTurn)
                    streak++;
            }
            if(streak == 3){
                handleWin();
                return;
            }
            else{
                streak = 0;
            }
        }

        //diagonal win
        for(int i = 0; i < board.length; ++i){
            if(board[i][i] == playerTurn)
                streak++;
        }
        if(streak == 3){
            handleWin();
            return;
        }
        else{
            streak = 0;
        }

        //diagonal win
        for(int i = 0; i < board.length; ++i){
            if(board[2-i][i] == playerTurn)
                streak++;
        }
        if(streak == 3){
            handleWin();
            return;
        }
    }

    /**
     * checkDraw checks for draw condition
     * @throws Exception
     */
    public void checkDraw() throws Exception{
        int counter = 0;
        for(int i = 0; i < board.length; ++i){
            for(int j = 0; j < board.length; ++j){
                if(board[i][j] == 1 || board[i][j] == 2){
                    counter ++;
                }
            }
        }
        if(counter == 9){
            handleDraw();
        }
    }

    /**
     * handlesWin handles win scenario
     * @throws Exception
     */
    public void handleWin() throws Exception {
        restart(false);
        playerTurn = 1;
        resetBoard();
    }

    /**
     * handleDraw handles draw scenario
     * @throws Exception
     */
    public void handleDraw() throws Exception {
        restart(true);
        playerTurn = 1;
        resetBoard();
    }

    /**
     * resetBoard resets the 2d array game board
     * @throws Exception
     */
    public void resetBoard() throws Exception {
        for(int i = 0; i < board.length; ++i){
            for(int j = 0; j < board[0].length; ++j){
                board[i][j] = 0;
            }
        }

        for(Button b: buttonsList){
            b.setGraphic(null);
        }

        score.setText("Current turn: " + playerTurn);
    }

    /**
     * restart displays play again prompt and handles play again scenario
     * @param draw
     * @throws IOException
     */
    public void restart(boolean draw) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        givenStage.setScene(new Scene(root, 600, 700));

        Text gameOver = new Text();
        gameOver.prefHeight(100);
        if(draw){
            gameOver.setText("Game over, it is a draw! Play again?");
        }
        else {
            gameOver.setText("Game over, player " + playerTurn + " won, play again?");
        }

        Button button1 = new Button();
        button1.setPrefWidth(100);
        button1.setPrefHeight(100);
        button1.setId("playAgain");
        button1.setText("Play again!");

        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                try {
                    start(givenStage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        GridPane gridPane = new GridPane();
        gridPane.addRow(0,gameOver);
        gridPane.addRow(1, button1);

        // spacing the buttons
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        Scene grid = new Scene(gridPane, 300, 300);
        givenStage.setScene(grid);
        givenStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
