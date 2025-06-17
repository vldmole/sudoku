package sudoku.game;

import java.util.function.Consumer;

public interface ReadOnlySudokuModel {

    int numberOfLines();
    int numberOfColumns();
    int getValue(int lin, int col);
    boolean isFixed(int lin, int col);
    boolean isValid(int value);
    boolean haveBlank();
    boolean isPresentAtLine(int lin, int value);
    boolean isPresentAltColumn(int column, int value);
    void forEach(Consumer<Integer> consumer);
    String toString();
}
