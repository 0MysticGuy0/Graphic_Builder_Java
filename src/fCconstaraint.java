import javax.swing.*;
import java.awt.event.ActionListener;

public class fCconstaraint extends  JPanel
{
    private JLabel s1=new JLabel("[");
    private JLabel s2=new JLabel(";");
    private JLabel s3=new JLabel("]");
    private JTextField minT=new JTextField("inf");
    private JTextField maxT=new JTextField("inf");
    private JButton del=new JButton("-");
    private double min;
    private double max;
    fCconstaraint(ActionListener AL)
    {
        min=Double.NaN;
        max=Double.NaN;
        minT.addActionListener(AL);
        maxT.addActionListener(AL);
        del.addActionListener(AL);
        del.setActionCommand("delConstr");
        add(s1); add(minT);add(s2);add(maxT);add(s3); add(del);
    }

    fCconstaraint(ActionListener AL,double min,double max)
    {
        this.min=min;
        this.max=max;
        minT.setText(Double.toString(min));
        maxT.setText(Double.toString(max));
        minT.addActionListener(AL);
        maxT.addActionListener(AL);
        add(s1); add(minT);add(s2);add(maxT);add(s3);
    }

    double getMin() {return min;}
    double getMax() {return max;}
    void setMin(double m)   { min=m;}
    void setMax(double m)   { max=m;}
    JButton getDelButton()  { return del;}
    void setConstraint(double min,double max)
    {
        this.min=min;
        this.max=max;
    }
    void setConstraint()
    {
        String min=minT.getText();
        String max= maxT.getText();
        min=min.replaceAll(" ","");     max=max.replaceAll(" ","");
        min=min.replaceAll(",",".");    max=max.replaceAll(",",".");
        for(int i=0;i<min.length();i++)
        {
            if( !( (min.charAt(i) >='0' &&  (min.charAt(i) <='9'))||min.charAt(i)=='.' ||min.charAt(i)=='-' )  ) { min="inf"; minT.setText("inf"); break;}
        }
        for(int i=0;i<max.length();i++)
        {
            if( !( (max.charAt(i) >='0' &&  (max.charAt(i) <='9'))||max.charAt(i)=='.'||max.charAt(i)=='-')  ) { max="inf";maxT.setText("inf"); break;}
        }

        if(min.length()<1) minT.setText("inf");
        if(max.length()<1) maxT.setText("inf");

        if(min.equals("inf"))   this.min=Double.NaN;
        else    this.min= Double.parseDouble(min);
        if(max.equals("inf"))   this.max= Double.NaN;
        else    this.max= Double.parseDouble(max);
    }
}
///////////////////////////////////////////////////////