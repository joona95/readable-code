package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gamelevel.GameLevel;

import java.util.Arrays;
import java.util.Random;

public class GameBoard {

    private final Cell[][] board;
    private final int landMineCount;

    public GameBoard(GameLevel gameLevel) {
        int rowSize = gameLevel.getRowSize();
        int colSize = gameLevel.getColSize();
        this.board = new Cell[rowSize][colSize];

        landMineCount = gameLevel.getLandMineCount();
    }

    public void flag(int rowIndex, int colIndex) {
        Cell cell = findCell(rowIndex, colIndex);
        cell.flag();
    }

    public void open(int rowIndex, int colIndex) {
        Cell cell = findCell(rowIndex, colIndex);
        cell.open();
    }

    public void initializeGame() {
        int rowSize = getRowSize();
        int colSize = getColSize();

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                board[row][col] = Cell.create();
            }
        }

        for (int i = 0; i < landMineCount; i++) {
            int landMineCol = new Random().nextInt(colSize);
            int landMineRow = new Random().nextInt(rowSize);
            Cell landMineCell = findCell(landMineRow, landMineCol);
            landMineCell.turnOnLandMine();
        }

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                if (isLandMineCell(col, row)) {
                    continue;
                }
                int count = countNearbyLandMines(row, col);
                Cell cell = findCell(row, col);
                cell.updateNearbyLandMineCount(count);
            }
        }
    }

    public void openSurroundedCells(int rowIndex, int colIndex) {
        if (rowIndex < 0 || rowIndex >= getRowSize() || colIndex < 0 || colIndex >= getColSize()) {
            return;
        }

        if (isOpenedCell(rowIndex, colIndex)) {
            return;
        }

        if (isLandMineCell(colIndex, rowIndex)) {
            return;
        }

        open(rowIndex, colIndex);

        if (doesCellHaveLandMineCount(rowIndex, colIndex)) {
            return;
        }

        open(rowIndex - 1, colIndex - 1);
        open(rowIndex - 1, colIndex);
        open(rowIndex - 1, colIndex + 1);
        open(rowIndex, colIndex - 1);
        open(rowIndex, colIndex + 1);
        open(rowIndex + 1, colIndex - 1);
        open(rowIndex + 1, colIndex);
        open(rowIndex + 1, colIndex + 1);
    }

    public boolean isAllCellChecked() {
        return Arrays.stream(board)
                .flatMap(Arrays::stream)
                .allMatch(Cell::isChecked);
    }

    private boolean doesCellHaveLandMineCount(int rowIndex, int colIndex) {
        Cell cell = findCell(rowIndex, colIndex);
        return cell.hasLandMineCount();
    }

    private boolean isOpenedCell(int rowIndex, int colIndex) {
        Cell cell = findCell(rowIndex, colIndex);
        return cell.isOpened();
    }

    public boolean isLandMineCell(int selectedColIndex, int selectedRowIndex) {
        Cell cell = findCell(selectedRowIndex, selectedColIndex);
        return cell.isLandMine();
    }

    public String getSign(int rowIndex, int colIndex) {
        Cell cell = findCell(rowIndex, colIndex);
        return cell.getSign();
    }

    private Cell findCell(int rowIndex, int colIndex) {
        return board[rowIndex][colIndex];
    }

    public int getRowSize() {
        return board.length;
    }

    public int getColSize() {
        return board[0].length;
    }

    private int countNearbyLandMines(int row, int col) {
        int rowSize = getRowSize();
        int colSize = getColSize();

        int count = 0;
        if (row - 1 >= 0 && col - 1 >= 0 && isLandMineCell(col - 1, row - 1)) {
            count++;
        }
        if (row - 1 >= 0 && isLandMineCell(col, row - 1)) {
            count++;
        }
        if (row - 1 >= 0 && col + 1 < colSize && isLandMineCell(col + 1, row - 1)) {
            count++;
        }
        if (col - 1 >= 0 && isLandMineCell(col - 1, row)) {
            count++;
        }
        if (col + 1 < colSize && isLandMineCell(col + 1, row)) {
            count++;
        }
        if (row + 1 < rowSize && col - 1 >= 0 && isLandMineCell(col - 1, row + 1)) {
            count++;
        }
        if (row + 1 < rowSize && isLandMineCell(col, row + 1)) {
            count++;
        }
        if (row + 1 < rowSize && col + 1 < colSize && isLandMineCell(col + 1, row + 1)) {
            count++;
        }
        return count;
    }
}
