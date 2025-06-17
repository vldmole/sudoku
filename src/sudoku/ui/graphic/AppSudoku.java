package sudoku.ui.graphic;

import javax.swing.JFrame;

public class AppSudoku {

    public static void main(String args[])
    {
        SudokuMainWindow sudokuMainWindow = new SudokuMainWindow("Sudoku", 9);
        sudokuMainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
