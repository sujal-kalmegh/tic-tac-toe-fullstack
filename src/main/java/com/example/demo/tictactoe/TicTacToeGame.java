package com.example.demo.tictactoe;

public class TicTacToeGame {

    private final char[][] board;
    private char currentPlayer;

    public TicTacToeGame() {
        board = new char[3][3];
        reset();
    }

    public void reset() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
        currentPlayer = 'X';
    }

    public char[][] getBoard() {
        return board;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public GameStatus makeMove(int row, int col) {
        if (row < 0 || row >= 3 || col < 0 || col >= 3) {
            return GameStatus.INVALID_MOVE;
        }

        if (board[row][col] != ' ') {
            return GameStatus.INVALID_MOVE;
        }

        board[row][col] = currentPlayer;

        if (checkWin()) {
            return GameStatus.WIN;
        }

        if (checkDraw()) {
            return GameStatus.DRAW;
        }

        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        return GameStatus.IN_PROGRESS;
    }

    private boolean checkWin() {
        char p = currentPlayer;

        for (int i = 0; i < 3; i++) {
            if (board[i][0] == p && board[i][1] == p && board[i][2] == p) {
                return true;
            }
        }

        for (int j = 0; j < 3; j++) {
            if (board[0][j] == p && board[1][j] == p && board[2][j] == p) {
                return true;
            }
        }

        if (board[0][0] == p && board[1][1] == p && board[2][2] == p) {
            return true;
        }

        if (board[0][2] == p && board[1][1] == p && board[2][0] == p) {
            return true;
        }

        return false;
    }

    private boolean checkDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
}
