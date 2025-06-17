package sudoku.ui.graphic;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.function.Consumer;

import javax.swing.JOptionPane;

import sudoku.game.ReadOnlySudokuModel;
import sudoku.game.SudokuService;
import sudoku.game.exception.SudokuFixedValueException;
import sudoku.game.exception.SudokuInvalidValueException;
import sudoku.game.exception.SudokuRepeatedValueException;
import sudoku.ui.graphic.SudokuTable.SudokuFieldChangeEvent;

public class SudokuControl {

    final SudokuMainWindow mainWindow; 
    final SudokuService sudokuService;

    public SudokuControl(SudokuMainWindow mainWindow, SudokuService sudokuService){
        this.mainWindow = mainWindow;
        this.sudokuService = sudokuService;
        
        this.mainWindow.subscribeOnButtonNewSudoku(createNewSudokuHandler());
        this.mainWindow.subscribeOnButtonReset(createSodokuResetHandler());
        this.mainWindow.sudokuTable.subscribe(createSudokyFieldChangeEvent());
    }

    private ActionListener createNewSudokuHandler(){

        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try {
                    sudokuService.startNewSudoku();
                    ReadOnlySudokuModel model = sudokuService.getReadOnlySudokuModel();
                    mainWindow.sudokuTable.initializeFromModel(model);
                } catch (URISyntaxException | IOException e1) {
                    JOptionPane.showConfirmDialog(mainWindow, e, "ERROR", JOptionPane.CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                }
            }
        };
    }

    private ActionListener createSodokuResetHandler(){
         return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sudokuService.restartSudoku();
                ReadOnlySudokuModel model = sudokuService.getReadOnlySudokuModel();
                mainWindow.sudokuTable.initializeFromModel(model);
            }
        };
    }

    private Consumer<SudokuFieldChangeEvent> createSudokyFieldChangeEvent() {
        
        return (event)-> {
            try{
                if(!event.sudokuField().isEditable())
                    return;
               
                if ((event.oldValue()==event.newValue()) && event.oldValue()==0)
                    return;

                sudokuService.putNumber(event.line(), event.column(), event.newValue());
            }
            catch(SudokuInvalidValueException | SudokuRepeatedValueException e){
                event.sudokuField().setBackground(Color.RED);
            } catch (SudokuFixedValueException e) {
                e.printStackTrace();
            }
        };
    }
}
