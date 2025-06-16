package sudoku.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import sudoku.game.exception.SudokuInvalidValueException;
import sudoku.game.exception.SudokuRepeatedLineValueException;

public class SudokuService {

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

    public void putNumber(int lin, int col, int value) throws SudokuInvalidValueException, 
                                                              SudokuRepeatedLineValueException {
        
        if(!sudokuModel.isValid(value))
            throw new SudokuInvalidValueException("Invalid Value: "+ value);
            
        if(sudokuModel.isPresentAtLine(lin, value))
            throw new SudokuRepeatedLineValueException("invalid value '" + value + "'");

        sudokuModel.setValue(lin, col, value);
    }

    public void removeNumber(int lin, int col) {
        sudokuModel.setValue(lin, col, 0);
    }

    public String getSudokuTextView() {
        return sudokuModel.toString();
    }

    public int getValue(int lin, int col) {
        return sudokuModel.getValue(lin, col);
    }

    public boolean isFixed(int lin, int col) {
        return sudokuModel.isFixed(lin, col);
    }
}
