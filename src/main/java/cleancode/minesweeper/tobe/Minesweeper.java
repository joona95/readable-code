package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.config.GameConfig;
import cleancode.minesweeper.tobe.game.GameInitializable;
import cleancode.minesweeper.tobe.game.GameRunnable;
import cleancode.minesweeper.tobe.io.InputHandler;
import cleancode.minesweeper.tobe.io.OutputHandler;
import cleancode.minesweeper.tobe.io.UserAction;
import cleancode.minesweeper.tobe.position.CellPosition;

public class Minesweeper implements GameInitializable, GameRunnable {

    private final GameBoard gameBoard;
    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    private GameStatus gameStatus2;

    public Minesweeper(GameConfig gameConfig) {
        gameBoard = new GameBoard(gameConfig.getGameLevel());
        this.inputHandler = gameConfig.getInputHandler();
        this.outputHandler = gameConfig.getOutputHandler();
        gameStatus2 = GameStatus.IN_PROGRESS;
    }

    @Override
    public void initialize() {
        gameBoard.initializeGame();
        gameStatus2 = GameStatus.IN_PROGRESS;
    }

    @Override
    public void run() {
        outputHandler.showGameStartComments();

        while (true) {
            try {
                outputHandler.showBoard(gameBoard);

                if (doesUserWinTheGame()) {
                    outputHandler.showGameWinningComment();
                    break;
                }
                if (doesUserLoseTheGame()) {
                    outputHandler.showGameLosingComment();
                    break;
                }

                CellPosition cellPosition = getCellInputFromUser();
                UserAction userActionInput = getUserActionInputFromUser();
                actOnCell(cellPosition, userActionInput);
            } catch (GameException e) {
                outputHandler.showExceptionMessage(e);
            } catch (Exception e) {
                outputHandler.showSimpleMessage("프로그램에 문제가 생겼습니다.");
            }
        }
    }


    private void actOnCell(CellPosition cellPosition, UserAction userActionInput) {
        if (doesUserChooseToPlantFlag(userActionInput)) {
            gameBoard.flagAt(cellPosition);
            checkIfGameIsOver();
            return;
        }

        if (doesUserChooseToOpenCell(userActionInput)) {
            if (gameBoard.isLandMineCellAt(cellPosition)) {
                gameBoard.openAt(cellPosition);
                changeGameStatusToLose();
                return;
            }

            gameBoard.openSurroundedCells(cellPosition);
            checkIfGameIsOver();
            return;
        }
        System.out.println("잘못된 번호를 선택하셨습니다.");
    }

    private void changeGameStatusToLose() {
        gameStatus2 = GameStatus.LOSE;
    }

    private boolean doesUserChooseToOpenCell(UserAction userAction) {
        return userAction == UserAction.OPEN;
    }

    private boolean doesUserChooseToPlantFlag(UserAction userAction) {
        return userAction == UserAction.FLAG;
    }

    private UserAction getUserActionInputFromUser() {
        outputHandler.showCommentForUserAction();
        return inputHandler.getUserActionFromUser();
    }

    private CellPosition getCellInputFromUser() {
        outputHandler.showCommentForSelectingCell();
        CellPosition cellPosition = inputHandler.getCellPositionFromUser();
        if (gameBoard.isInvalidCellPosition(cellPosition)) {
            throw new GameException("잘못된 좌표를 선택하셨습니다.");
        }

        return cellPosition;
    }

    private boolean doesUserLoseTheGame() {
        return gameStatus2 == GameStatus.LOSE;
    }

    private boolean doesUserWinTheGame() {
        return gameStatus2 == GameStatus.WIN;
    }

    private void checkIfGameIsOver() {
        if (gameBoard.isAllCellChecked()) {
            changeGameStatusToWin();
        }
    }

    private void changeGameStatusToWin() {
        gameStatus2 = GameStatus.WIN;
    }
}
