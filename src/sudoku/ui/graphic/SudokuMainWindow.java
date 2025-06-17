package sudoku.ui.graphic;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class SudokuMainWindow extends JFrame{

    public final SudokuTable sudokuTable;
    public final JButton btnNew;
    public final JButton btnReset;

    
    public SudokuMainWindow(String sudokuTitle, int sudokuNumber){
        super(sudokuTitle);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        super.setContentPane(panel);

        sudokuTable = new SudokuTable(30, 30, sudokuNumber);
        panel.add(sudokuTable, BorderLayout.CENTER);

        btnNew = new JButton("New Sudoku");
        btnReset = new JButton("Reset");
        JPanel buttoPanel = new JPanel(new FlowLayout());
        buttoPanel.add(btnNew);
        buttoPanel.add(btnReset);
        panel.add(buttoPanel, BorderLayout.SOUTH);

        super.pack();
        super.setResizable(false);
        super.setVisible(true);
    }

    public void subscribeOnButtonNewSudoku(ActionListener listener){
        btnNew.addActionListener(listener);
    }

    public void subscribeOnButtonReset(ActionListener listener){
        btnReset.addActionListener(listener);
    }
}
