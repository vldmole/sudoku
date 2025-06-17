package sudoku.ui.text;
import java.util.Scanner;

import sudoku.game.SudokuService;
import sudoku.game.exception.SudokuFixedValueException;
import sudoku.game.exception.SudokuInvalidValueException;
import sudoku.game.exception.SudokuRepeatedValueException;

import java.awt.*;

public class SudokuTextUI {

    SudokuService sudokuControl = null;
    Scanner scanner = new Scanner(System.in);

    public SudokuTextUI(SudokuService control) {
        
        this.sudokuControl = control;
        
        startNewSudoku();
    }

    private void showOptions(){
        System.out.println("1 -> Novo jogo");
        System.out.println("2 -> Colocar um número");
        System.out.println("3 -> Remover um número");
        System.out.println("4 -> Visualizar jogo");
        System.out.println("5 -> Reiniciar jogo");
        System.out.println("6 -> SAIR");
    }

    private int readInt(final String msg, final int min, final int max){

        int option = -1;
        while(option < min || option > max){
            try {
               System.out.printf("\n%s ", msg);
               option = scanner.nextInt();
               scanner.nextLine();
            }catch(Exception e){
                //silent
            }
        }
        
        return option;
    }

    private void errorMessage(final String msg){
        
        Toolkit.getDefaultToolkit().beep();
        
        System.out.println("\n***************************************************\n");
        System.out.println("Error: "+ msg);
        System.out.println("\n***************************************************\n");
    }

    private void doOption(final int option){
        try{ 
            switch(option){
                case 1 -> startNewSudoku();
                case 2 -> putNumber();
                case 3 -> removeNumber();
                case 4 -> showSudoku();
                case 5 -> sudokuControl.restartSudoku();
                case 6 -> System.exit(0);
                default -> System.exit(-1);
            }
        } 
        catch (Exception e) {
            errorMessage(e.getMessage());
        }
    }

    private void startNewSudoku() {
        try{
            sudokuControl.startNewSudoku();
        }
        catch(Exception e){
            System.out.println("Error: "+ e.getMessage());
            System.exit(-1);
        }
    }

    private void putNumber() throws SudokuInvalidValueException, SudokuRepeatedValueException, SudokuFixedValueException {

        System.out.println("Adicionar um número");
        int lin = readInt("Linha:", 0, sudokuControl.numberOfLines());
        int col = readInt("Coluna:", 0, sudokuControl.numberOfColumns());
        int value = readInt("Número:", sudokuControl.getMinValue(), sudokuControl.getMaxValue());
        
        sudokuControl.putNumber(lin, col, value);
    }

    private void removeNumber() {

        System.out.println("Remover um número");
        int lin = readInt("Linha:", 0, sudokuControl.numberOfLines());
        int col = readInt("Coluna:", 0, sudokuControl.numberOfColumns());

        sudokuControl.removeNumber(lin, col);
    }

    private void showSudoku() {
        System.out.println(sudokuControl.getSudokuTextView());
    }

    public void go(){

        int option;
        do {
            showOptions();
            option = readInt("Option:", 1, 6);
            doOption(option);
        } while (option != 6);
    }
}
