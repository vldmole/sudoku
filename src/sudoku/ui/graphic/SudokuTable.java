package sudoku.ui.graphic;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

import sudoku.game.ReadOnlySudokuModel;
import sudoku.ui.graphic.util.ForEachUtil;
import sudoku.ui.graphic.util.NumericJTextFieldFactory;

public class SudokuTable extends JPanel{

    final JTextField[][] sudokuFields;
    
    public SudokuTable(int fieldWidth, int fieldHeight, int sudokuNumber){

        sudokuFields = new JTextField[sudokuNumber][sudokuNumber];
        createSudokuFields(fieldWidth, fieldHeight, sudokuNumber);
        configureView();
    }

    private void createSudokuFields(int fieldWidth, int fieldHeight, int sudokuNumber){

        for(int lin=0; lin<sudokuFields.length; lin++){
            for(int col=0; col< sudokuFields[lin].length; col++){
                sudokuFields[lin][col] = NumericJTextFieldFactory.createIntegerTextField(fieldWidth, fieldHeight);
                sudokuFields[lin][col].addFocusListener(this.createSudokuFieldChangeEventDispatcher(lin, col));
                sudokuFields[lin][col].setHorizontalAlignment(JTextField.CENTER);
            }
        }
    }
    
    public void initializeFromModel(ReadOnlySudokuModel sudokuModel){

        for(int lin=0; lin<sudokuFields.length; lin++){
            for(int col=0; col< sudokuFields[lin].length; col++){
                JTextField jtf = sudokuFields[lin][col];

                int value = sudokuModel.getValue(lin, col);
                jtf.setText( value == 0 ? "" : ""+value);

                jtf.setEditable(!sudokuModel.isFixed(lin, col));
            }
        }
    }

    private JTextField[][] getSudokuSector(int line, int column, int sectorSize){

        JTextField[][] sector = new JTextField[sectorSize][sectorSize];

        for(int lin=0; lin<sector.length; lin++)
            for(int col=0; col<sector[lin].length; col++)
                sector[lin][col] = sudokuFields[sectorSize * line + lin][sectorSize * column + col];

        return sector;
    }

    private JPanel createSectorPanel(JTextField[][] fields){

        GridLayout layout = new GridLayout(fields.length, fields[0].length);
        layout.setVgap(5);
        layout.setHgap(5);

        JPanel panel = new JPanel(layout);
        panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        
        ForEachUtil.forEach(fields, panel::add);

        return panel;
    } 

    private void configureView(){

        int sectorSize = (int) Math.sqrt(sudokuFields.length);

        GridLayout layout = new GridLayout(sectorSize, sectorSize);
        layout.setVgap(2);
        layout.setHgap(2);
        
        this.setLayout(layout);
        this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        for(int lin=0; lin<sectorSize; lin++)
            for(int col=0; col<sectorSize; col++)
                this.add(createSectorPanel(getSudokuSector(lin, col, sectorSize)));
    }

    //---Observable------------------------------------------------------------------
    public static record SudokuFieldChangeEvent (int line, int column, int oldValue,int newValue){}

    private List<Consumer<SudokuFieldChangeEvent>> observers = new ArrayList<>();

    public void subscribe(Consumer<SudokuFieldChangeEvent> observer){
        observers.add(observer);
    }

    public void unsubscribe(Consumer<SudokuFieldChangeEvent> observer){
        observers.remove(observer);
    }

    private FocusListener createSudokuFieldChangeEventDispatcher(int lin, int col){

        return new FocusListener(){
            int line = lin;
            int column = col;
            String oldText = "0";
            String newText = "";

            @Override
            public void focusGained(FocusEvent e) {
                oldText = ((JTextField)e.getComponent()).getText();
                oldText = ("".equals(oldText) ? "0" : oldText);
            }

            @Override
            public void focusLost(FocusEvent e) {
                try{
                    newText = ((JTextField)e.getComponent()).getText();
                    newText = ("".equals(newText) ? "0" : newText);
                    SudokuFieldChangeEvent event = new SudokuFieldChangeEvent(
                        line, column, Integer.parseInt(oldText), Integer.parseInt(newText));
                    
                    observers.forEach(observer->observer.accept(event));
                }
                catch(NumberFormatException nfe){
                    nfe.printStackTrace();
                }            
            }
        };  
    }    
}
