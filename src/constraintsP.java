import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class constraintsP extends JScrollPane
{
    ArrayList<fCconstaraint> constraints=new ArrayList<>();
    JPanel constrP=new JPanel();//панель с ограничителями
    JPanel resP=new JPanel();//панель с ограничителями и кнопкой
    JButton addC=new JButton("C");
    ActionListener cAl=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("AddC"))
            {
                fCconstaraint newC=new fCconstaraint(cAl);
                constraints.add(newC);
                constrP.add(newC);
            }
            else if(e.getActionCommand().equals("delConstr"))
            {
                for(int i=0;i<constraints.size();i++)
                {
                    if(e.getSource()==constraints.get(i).getDelButton())
                    {
                        constrP.remove(constraints.get(i));
                        constraints.remove(i);
                        break;
                    }
                }
            }
            for(fCconstaraint fc:constraints)
            {
                fc.setConstraint();
            }
            revalidate();
            repaint();
        }
    };

    constraintsP()
    {
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        addC.addActionListener(cAl);
        addC.setActionCommand("AddC");
        resP.add(constrP);
        resP.add(addC);
        setViewportView(resP);
    }
    public boolean isValid(double x)
    {
        if(constraints.size()==0) return true;
        for(fCconstaraint fc:constraints)
        {
            boolean res=true;
            if(!Double.isNaN(fc.getMin()))  res=(x>=fc.getMin());
            if(!Double.isNaN(fc.getMax()))  res=res &&(x<=fc.getMax());
            if(res) return true;
        }
        return false;
    }
    public void print()
    {
        for(int i=0;i<constraints.size();i++)
        {
            System.out.println(i+": ["+constraints.get(i).getMin()+" ; "+constraints.get(i).getMax());
        }
    }
    public int get_Size()
    {
        return constraints.size();
    }
}
