import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;


public class SudokuReader {

    public SudokuModel readFromfile(URL url) throws FileNotFoundException, IOException, URISyntaxException {
        
        Scanner scanner = new Scanner(new FileReader(new File(url.toURI())));
        int nLin = scanner.nextInt();
        int nCol = scanner.nextInt();
        
        SudokuModel model = new SudokuModel(nLin, nCol);
        
        for(int lin=0; lin<nLin; lin++){
            for(int col=0; col<nCol; col++){
                int value = scanner.nextInt();
                if(value == 0)
                    continue;
                model.setValue(lin, col, value);
                model.setFixed(lin, col);
            }
        }
        scanner.close();

        return model;
    }
}
