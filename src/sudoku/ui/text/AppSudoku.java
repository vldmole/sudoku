package sudoku.ui.text;

import sudoku.game.SudokuService;

public class AppSudoku {

    public static void main(String[] args){

        SudokuService control = new SudokuService();
        SudokuTextUI tui = new SudokuTextUI(control);
        tui.go();
    }

}
