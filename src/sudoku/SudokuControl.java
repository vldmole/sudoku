package sudoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class SudokuControl {

    SudokuModel sudokuModel;

    public void startNewSudoku() throws URISyntaxException, FileNotFoundException, IOException {

        final String path = "sudokuFiles";
        URL url = getClass().getResource(path);
        File sudoDirectory = new File(url.toURI());
        String files[] = sudoDirectory.list();

        int index = (int)(Math.round(Math.random()*1_000) % files.length);
        String fileName = path+"/"+files[index];
        url = getClass().getResource(fileName);

        sudokuModel = (new SudokuReader()).readFromfile(url);
    }

    public void restartSudoku() {
        sudokuModel.reset();
    }

    public int numberOfLines() {
        return sudokuModel.numberOfLines();
    }

    public int numberOfColumns() {
        return sudokuModel.numberOfColumns();
    }

    public int getMinValue() {
        return 1;
    }

    public int getMaxValue() {
        return sudokuModel.numberOfColumns();
    }

    public void putNumber(int lin, int col, int value) {
        sudokuModel.setValue(lin, col, value);
    }

    public void removeNumber(int lin, int col) {
        sudokuModel.setValue(lin, col, 0);
    }

    public String getSudokuTextView() {
        return sudokuModel.toString();
    }
}
