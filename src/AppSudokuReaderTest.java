import java.net.URL;

public class AppSudokuReaderTest {
    public static void main(String[] args) throws Exception {
        
        SudokuReader reader = new SudokuReader();
        URL url = reader.getClass().getResource("sudoku01.sdk");
        SudokuModel model = reader.readFromfile(url);
        System.out.println(model);
    }
}
