import java.util.Arrays;

public class SudokuModel {

    Integer[][] matrix = null;
    boolean[][] fixedMatrix = null;

    public SudokuModel(int nLin, int nCol){
        matrix = new Integer[nLin][nCol];
        fixedMatrix = new boolean[nLin][nCol];
    }

    public void setValue(int lin, int col, Integer value){

        if(fixedMatrix[lin][col])
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

    public String toString(){

        StringBuffer buffer = new StringBuffer();
        
        for(Integer[] line : matrix)
          buffer.append(
            (Arrays.toString(line) +"\n")
            .replace("null", " ")
            .replace(", ", " | ")
            .replace("[", "| ")
            .replace("]", " |"));

        return buffer.toString();
    }
}
