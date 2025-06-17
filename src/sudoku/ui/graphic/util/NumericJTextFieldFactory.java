package sudoku.ui.graphic.util;

import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;


public class NumericJTextFieldFactory {

    static public JTextField createIntegerTextField(int width, int height){
        
        Dimension dimension = new Dimension(width, height);
        JTextField jtf = new JTextField();
        jtf.setPreferredSize(dimension);
        jtf.setMinimumSize(dimension);
        AbstractDocument document = (AbstractDocument) jtf.getDocument();
        document.setDocumentFilter(new IntegerDocumentFilter());

        return jtf;
    }
}

class IntegerDocumentFilter extends DocumentFilter {

    private boolean isInteger(String text){
        try{
            Integer.parseInt(text);
            return true;
        }
        catch(NumberFormatException e){
            return false;
        }
    }

    @Override
    public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr){
        
        try {
            Document doc = fb.getDocument();
            String text = doc.getText(0, offset) + string;
            if(isInteger(text))
               super.insertString(fb, offset, string, attr);
        } 
        catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(DocumentFilter.FilterBypass fb, int offset, int length){
        try {
            Document doc = fb.getDocument();
            StringBuffer sb = new StringBuffer(doc.getText(0, doc.getLength()));
            String text = sb.replace(offset, length, "").toString();
            if(isInteger(text))
               super.remove(fb, offset, length);
        } 
        catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs){
        try {
            Document doc = fb.getDocument();
            StringBuffer sb = new StringBuffer(doc.getText(0, doc.getLength()));
            String newText = sb.replace(offset, length, text).toString();
            if(isInteger(newText))
               super.replace(fb, offset, length, text, attrs);
        } 
        catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
