package sudoku.tui;

import sudoku.SudokuControl;

public class AppSudoku {

    public static void main(String[] args){

        SudokuControl control = new SudokuControl();
        SudokuTextUI tui = new SudokuTextUI(control);
        tui.go();
    }

}
