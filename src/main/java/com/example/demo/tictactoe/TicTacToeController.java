package com.example.demo.tictactoe;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
@CrossOrigin("*")
public class TicTacToeController {

    private final TicTacToeGame game = new TicTacToeGame();

    @GetMapping
    public GameStateResponse getGameState() {
        return new GameStateResponse(game.getBoard(), game.getCurrentPlayer(), GameStatus.IN_PROGRESS);
    }

    @PostMapping("/move")
    public GameStateResponse makeMove(@RequestBody MoveRequest request) {
        GameStatus status = game.makeMove(request.getRow(), request.getCol());
        return new GameStateResponse(game.getBoard(), game.getCurrentPlayer(), status);
    }

    @PostMapping("/reset")
    public GameStateResponse reset() {
        game.reset();
        return new GameStateResponse(game.getBoard(), game.getCurrentPlayer(), GameStatus.IN_PROGRESS);
    }
}
