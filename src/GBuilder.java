import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GBuilder extends JFrame {
    final int startHeight=700,startWith=1400;

    ////////////////////////////////UI elements
    Border bord1=new LineBorder(Color.BLACK);
    JPanel interactP=new JPanel();
    Drawer drawerP=new Drawer();
    JPanel mainButtons=new JPanel(new GridLayout(2,3,10,10));
    JButton zoomIn=new JButton("+");
    JButton zoomOut=new JButton("-");
    JButton left=new JButton("<");
    JButton right=new JButton(">");
    JButton up=new JButton("^");
    JButton down=new JButton("V");
    ActionListener AL=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("+")){
                drawerP.scale--;
            }
            else if(e.getActionCommand().equals("-")){
                drawerP.scale++;
                System.out.println(1010101010);
            }
            else if(e.getActionCommand().equals(">")){
                drawerP.Xoffset--;
            }
            else if(e.getActionCommand().equals("<")){
                drawerP.Xoffset++;
            }
            else if(e.getActionCommand().equals("^")){
                drawerP.Yoffset--;
            }
            else if(e.getActionCommand().equals("V")){
                drawerP.Yoffset++;
            }
            repaint();
        }
    };
    ////////////////////////////////
    public GBuilder()
    {
        setTitle("PankiHoy");
        setSize(startWith,startHeight);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setResizable(false);
        setLayout(null);
        drawUI();
        setVisible(true);
    }

    public void drawUI(){//
        drawerP.setBounds(0,0,startWith,startHeight*2/3);
        drawerP.setBorder(bord1);

        interactP.setBounds(0,startHeight*2/3,startWith,startHeight/3);
        interactP.setBackground(Color.GRAY);
        interactP.setBorder(bord1);

        mainButtons.setBorder(bord1);
        interactP.add(mainButtons);

        zoomIn.addActionListener(AL);
        zoomOut.addActionListener(AL);
        left.addActionListener(AL);
        right.addActionListener(AL);
        up.addActionListener(AL);
        down.addActionListener(AL);

        zoomIn.setActionCommand("+");
        zoomOut.setActionCommand("-");
        left.setActionCommand("<");
        right.setActionCommand(">");
        up.setActionCommand("^");
        down.setActionCommand("V");

        mainButtons.add(zoomIn);    mainButtons.add(zoomOut);   mainButtons.add(up);
        mainButtons.add(left);      mainButtons.add(right);     mainButtons.add(down);

        add(drawerP);
        add(interactP);
        //System.out.println(10);
    }

    public void paint(Graphics g){
        super.paint(g);
        //drawUI();
        //drawerP.paint(g);

    }
    public static void main(String []args)
    {
        new GBuilder();

    }

}

class Drawer extends JPanel
{
    public int Ofat;
    public int Xoffset,Yoffset;
    public int scale;
    private double step=0.1;

    Drawer(){
        Ofat=2;
        Xoffset=0;
        Yoffset=0;
        scale=10;
    }
    public void paint(Graphics g)
    {

        g.setColor(Color.BLACK);
        g.drawRect(1,1,getWidth()-2,getHeight()-2);

        int X0=getWidth()/2+getXoffset();
        int Y0=getHeight()/2-getYoffset();

        //setka
        g.setColor(Color.GRAY);
        {
            int p = scale*2;
            System.out.println(scale);
            for (int i = 0; i < p; i++) {
                g.drawLine(0, getHeight() * i / p , getWidth(), getHeight() * i / p);
            }
            p = Math.round(p * getWidth() / getHeight());
            for (int i = 0; i < p; i++) {
                g.drawLine(getWidth() * i / p, 0, getWidth() * i / p, getHeight());
            }
        }

        g.setColor(Color.BLACK);
        g.fillRect(X0-Ofat,0,Ofat,getHeight());//OY
        g.fillRect(0,Y0-Ofat,getWidth(),Ofat);//OX
    }

    private int getXoffset(){
        return Math.round(Xoffset*getHeight()/(scale*2));
    }

    private int getYoffset(){
        return Math.round(Yoffset*getHeight()/(scale*2));
    }
}

