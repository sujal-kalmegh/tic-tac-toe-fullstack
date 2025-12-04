package com.example.demo.tictactoe;

public class GameStateResponse {

    private char[][] board;
    private char currentPlayer;
    private GameStatus status;

    public GameStateResponse(char[][] board, char currentPlayer, GameStatus status) {
        this.board = board;
        this.currentPlayer = currentPlayer;
        this.status = status;
    }

    public char[][] getBoard() {
        return board;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public GameStatus getStatus() {
        return status;
    }
}
