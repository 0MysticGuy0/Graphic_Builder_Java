import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

////////////////////////////панель для ввода функции
public class graphInp extends JPanel
{
    public JTextField expr=new JTextField("x^2",50);
    JComboBox<String> color=new JComboBox<>();
    public JButton destroyB=new JButton("-");
    public JButton addB=new JButton("+");
    static Drawer drawer;
    public constraintsP constraints =new constraintsP();
    graphInp(Drawer d, ActionListener AL)
    {
        color.addItem("Black");
        color.addItem("Blue");
        color.addItem("Green");
        color.addItem("Red");
        color.addItem("Pink");
        drawer=d;
        expr.setSize(200,50);
        destroyB.setActionCommand("del");
        destroyB.addActionListener(AL);
        addB.setActionCommand("addInp");
        addB.addActionListener(AL);
        expr.addActionListener(AL);
        color.addActionListener(AL);
        constraints.setPreferredSize(new Dimension(200,50));

        add(expr);
        add(constraints);
        add(color);
        add(destroyB);
        add(addB);
    }

    public Color getColor()
    {
        if(color.getSelectedItem().equals("Black")) return Color.BLACK;
        else if(color.getSelectedItem().equals("Blue")) return Color.BLUE;
        else if(color.getSelectedItem().equals("Green")) return Color.GREEN;
        else if(color.getSelectedItem().equals("Red")) return Color.RED;
        else if(color.getSelectedItem().equals("Pink")) return Color.PINK;
        else return Color.YELLOW;
    }
}