package sudoku.test;
import java.net.URL;

import sudoku.game.SudokuModel;
import sudoku.game.SudokuReader;


public class AppSudokuReaderTest {
    public static void main(String[] args) throws Exception {
        
        SudokuReader reader = new SudokuReader();
        URL url = reader.getClass().getResource("sudoku01.sdk");
        SudokuModel model = reader.readFromfile(url);
        System.out.println(model);
    }
}
