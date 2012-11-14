package gui;

import latex.parser.ExpressionParser;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Evgeniy
 * Date: 15.11.12
 * Time: 0:03
 */
public class GuiUtils {
    public static final Insets INSETS = new Insets(5, 5, 5, 5);
    public static final ExpressionParser PARSER_FOR_GUI = new ExpressionParser();

    public static Icon generateIcon(String formula) {
        return new TeXFormula(formula).createTeXIcon(TeXConstants.STYLE_DISPLAY, 20);
    }
}
