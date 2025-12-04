const API_BASE = "http://localhost:8080/api/game";


let statusDiv;
let boardDiv;
let resetBtn;
let currentPlayerBadge;
let gameOver = false;

async function loadGameState() {
    const response = await fetch(API_BASE);
    const data = await response.json();
    handleGameResponse(data);
}

async function sendMove(row, col) {
    if (gameOver) return;

    const response = await fetch(API_BASE + "/move", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ row: row, col: col })
    });

    const data = await response.json();
    handleGameResponse(data);
}

async function resetGame() {
    const response = await fetch(API_BASE + "/reset", { method: "POST" });
    const data = await response.json();
    gameOver = false;
    handleGameResponse(data);
}

function handleGameResponse(data) {
    const { board, currentPlayer, status } = data;
    renderBoard(board, status);

    currentPlayerBadge.textContent = currentPlayer;
    currentPlayerBadge.classList.remove("player-x", "player-o");
    currentPlayerBadge.classList.add(currentPlayer === "X" ? "player-x" : "player-o");

    statusDiv.className = "";
    if (status === "IN_PROGRESS") {
        statusDiv.textContent = `Player ${currentPlayer}'s turn.`;
        gameOver = false;
    } else if (status === "WIN") {
        statusDiv.textContent = `Player ${currentPlayer} wins!`;
        statusDiv.classList.add("status-win");
        gameOver = true;
    } else if (status === "DRAW") {
        statusDiv.textContent = "It's a draw!";
        statusDiv.classList.add("status-draw");
        gameOver = true;
    } else {
        statusDiv.textContent = "Invalid move!";
        statusDiv.classList.add("status-error");
    }
}

function renderBoard(board, status) {
    const cells = boardDiv.querySelectorAll(".cell");
    const freeze = status === "WIN" || status === "DRAW";

    cells.forEach(cell => {
        const row = parseInt(cell.getAttribute("data-row"));
        const col = parseInt(cell.getAttribute("data-col"));

        cell.textContent = board[row][col] === " " ? "" : board[row][col];
        cell.className = "cell";

        if (board[row][col] === "X") cell.classList.add("x");
        if (board[row][col] === "O") cell.classList.add("o");
        if (freeze) cell.classList.add("disabled");
    });
}

function setupBoardClicks() {
    boardDiv.querySelectorAll(".cell").forEach(cell => {
        cell.addEventListener("click", () => {
            console.log("Cell clicked:", cell.dataset.row, cell.dataset.col);  // debug
            if (cell.textContent !== "" || gameOver) return;
            sendMove(cell.dataset.row, cell.dataset.col);
        });
    });
}


window.addEventListener("load", () => {
    statusDiv = document.getElementById("status");
    boardDiv = document.getElementById("board");
    resetBtn = document.getElementById("resetBtn");
    currentPlayerBadge = document.getElementById("currentPlayerBadge");

    resetBtn.addEventListener("click", resetGame);
    setupBoardClicks();
    loadGameState();
});
