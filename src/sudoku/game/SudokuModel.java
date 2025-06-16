package sudoku.game;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import sudoku.ui.text.BoardTemplate;


public class SudokuModel {

    int[][] matrix = null;
    boolean[][] fixedMatrix = null;

    public SudokuModel(int nLin, int nCol){

        matrix = new int[nLin][nCol];
        fixedMatrix = new boolean[nLin][nCol];

        reset();
    }

    public int numberOfLines() {
        return matrix.length;
    }

    public int numberOfColumns() {
        return matrix[0].length;
    }
    
    public void setValue(int lin, int col, int value){

        if(isFixed(lin,col))
            throw new RuntimeException("Can't set a fixed cell!");

        matrix[lin][col] = value;
    }

    public Integer getValue(int lin, int col){
        return matrix[lin][col];
    }

    public void setFixed(int lin, int col){
        fixedMatrix[lin][col] = true;
    }
    
    public boolean isFixed(int lin, int col){
        return fixedMatrix[lin][col];
    }

    public void reset(){
        for(int lin=0; lin<matrix.length; lin++){
            for(int col=0; col<matrix[lin].length; col++){
                if(isFixed(lin, col))
                    continue;

                setValue(lin, col, 0);
            }
        }
    }

    public boolean isValid(int value){
        return (value > 0 && value <= matrix.length);
    }
    
    public boolean haveBlank(){

        for(int[] line : matrix)
            for(int value : line)
                if(value == 0)
                    return true;

        return false;
    }

    public boolean isPresentAtLine(int lin, int value) {
        
        for(int current : matrix[lin])
            if(current == value)
                return true;
        return false;
    }

    public boolean isPresentAltColumn(int column, int value){

        for(int[] line : matrix)
            if(line[column] == value)
                return true;
        return false;
    }

    public void forEach(Consumer<Integer> consumer){

        for(int[] line : matrix)
            for(int val : line)
                consumer.accept(val);
    }

    public String toString(){

        List<String> values = new ArrayList<>();
        this.forEach(val -> values.add( val == 0 ? " " : ""+val));

        return String.format(BoardTemplate.TEMPLATE_9_9, values.toArray());
    }

   
}
