package sudoku.ui.graphic.util;

import java.util.function.Consumer;

public class ForEachUtil {

    static public <T> void forEach(T[][] matrix, Consumer<T> consumer)
    {
        for(int lin=0; lin< matrix.length; lin++)
            for(int col=0; col<matrix[lin].length; col++)
                consumer.accept(matrix[lin][col]);
    } 
}
