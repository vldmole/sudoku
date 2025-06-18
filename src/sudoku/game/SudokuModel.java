package sudoku.game;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import sudoku.game.exception.SudokuFixedValueException;
import sudoku.ui.text.BoardTemplate;


public class SudokuModel implements ReadOnlySudokuModel{

    int[][] matrix = null;
    boolean[][] fixedMatrix = null;
    int sectorSize;

    public SudokuModel(int sudokuNumber){

        matrix = new int[sudokuNumber][sudokuNumber];
        fixedMatrix = new boolean[sudokuNumber][sudokuNumber];
        sectorSize = (int) Math.sqrt(sudokuNumber);
        reset();
    }

    @Override
    public int numberOfLines() {
        return matrix.length;
    }

    @Override
    public int numberOfColumns() {
        return matrix[0].length;
    }
    
    public void setValue(int lin, int col, int value) throws SudokuFixedValueException{

        if(isFixed(lin,col))
            throw new SudokuFixedValueException("Can't set a fixed cell!");

        matrix[lin][col] = value;
    }

    @Override
    public int getValue(int lin, int col){
        return matrix[lin][col];
    }

    public void setFixed(int lin, int col){
        fixedMatrix[lin][col] = true;
    }
    
    @Override
    public boolean isFixed(int lin, int col){
        return fixedMatrix[lin][col];
    }

    public void reset(){

        for(int lin=0; lin<matrix.length; lin++){
            for(int col=0; col<matrix[lin].length; col++){
                if(!isFixed(lin, col))
                    matrix[lin][col]= 0;
            }
        }
    }

    @Override
    public boolean isValid(int value){
        return (value >= 0 && value <= matrix.length);
    }
    
    @Override
    public boolean haveBlank(){

        for(int[] line : matrix)
            for(int value : line)
                if(value == 0)
                    return true;

        return false;
    }

    @Override
    public boolean isPresentAtLine(int lin, int value) {
        
        for(int current : matrix[lin])
            if(current == value)
                return true;
        return false;
    }

    @Override
    public boolean isPresentAltColumn(int column, int value){

        for(int[] line : matrix)
            if(line[column] == value)
                return true;
        return false;
    }

    @Override
    public void forEach(Consumer<Integer> consumer){

        for(int[] line : matrix)
            for(int val : line)
                consumer.accept(val);
    }

    @Override
    public String toString(){

        List<String> values = new ArrayList<>();
        this.forEach(val -> values.add( val == 0 ? " " : ""+val));

        return String.format(BoardTemplate.TEMPLATE_9_9, values.toArray());
    }

    public boolean isPresentAtSector(int lin, int col, int value) {
        int sectorLin = 0 + (lin / sectorSize);
        int sectorCol = 0 + (col / sectorSize);
        int[][] sectorValues = new int[sectorSize][sectorSize];

        for(int l=0; l<sectorValues.length; l++)
            for(int c=0; c<sectorValues[l].length; c++)
                if(value == matrix[sectorLin * sectorSize + l][sectorCol * sectorSize + c])
                    return true;
        return false;            
    }
}
