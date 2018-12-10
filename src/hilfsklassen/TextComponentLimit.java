package hilfsklassen;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;
import java.awt.*;

/**
 * 
 * Legt für JTextField die Anzahl der erfassbaren Zeichen fest 
 * 
 * @version 1.0 2018-12-01
 * @author Ueli
 *
 */

public class TextComponentLimit extends PlainDocument
{
    private int charactersLimit;

    private TextComponentLimit(int charactersLimit)
    {
        this.charactersLimit = charactersLimit;
    }

    @Override
    public void insertString(int offset, String input, AttributeSet attributeSet) throws BadLocationException
    {
        if (isAllowed(input))
        {
            super.insertString(offset, input, attributeSet);
        } else
        {
            Toolkit.getDefaultToolkit().beep();
        }
    }

    private boolean isAllowed(String string)
    {
        return (getLength() + string.length()) <= charactersLimit;
    }

    public static void addTo(JTextComponent textComponent, int charactersLimit)
    {
        TextComponentLimit textFieldLimit = new TextComponentLimit(charactersLimit);
        textComponent.setDocument(textFieldLimit);
    }
}
