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
                if(drawerP.scale>1)
                drawerP.scale--;
            }
            else if(e.getActionCommand().equals("-")){
                drawerP.scale++;
                //System.out.println(1010101010);
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
    private int X0,Y0;
    private double drawStep=0.25;
    Drawer(){
        X0=getWidth()/2+getXoffset();
        Y0=getHeight()/2-getYoffset();
        Ofat=2;
        Xoffset=0;
        Yoffset=0;
        scale=10;
    }
    public void paintComponent(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.drawRect(1,1,getWidth()-2,getHeight()-2);

         X0=getWidth()/2+getXoffset();
         Y0=getHeight()/2-getYoffset();

        //setka
        g.setColor(Color.GRAY);

            int p = scale*2;
            System.out.println(scale);
            for (int i = 0; i < p; i++) {
                g.drawLine(0, getHeight() * i / p , getWidth(), getHeight() * i / p);
            }
            p = Math.round(p * getWidth() / getHeight());
            for (int i = 0; i < p; i++) {
                g.drawLine(getWidth() * i / p, 0, getWidth() * i / p, getHeight());
            }

        g.setColor(Color.BLACK);
        g.fillRect(X0-Ofat/2,0,Ofat,getHeight());//OY
        g.fillRect(0,Y0-Ofat/2,getWidth(),Ofat);//OX
        g.setColor(Color.BLUE);

        int xscale=scale*getWidth()/getHeight();
        for(double i=-xscale-Xoffset;i<=xscale-drawStep-Xoffset;i+=drawStep) {

            double x1=i;
            double x2=i+drawStep;
            double y1=func(i);
            double y2=func(i+drawStep);
            drawPoint(g,x1,y1,6);
            connectPoints(g,x1,y1,x2,y2);
        }
        g.setColor(Color.BLACK);

    }

    private double func(double x)
    {
        return Math.sin(Math.toRadians(x*100))*3;
    }
    private int getXoffset(){
        if(scale!=0)
        return Math.round(Xoffset*getHeight()/(scale*2));
        else return 0;
    }

    private int getYoffset(){
        if(scale!=0)
        return Math.round(Yoffset*getHeight()/(scale*2));
        else return 0;
    }

    private void drawPoint(Graphics g,double x,double y, int fat)
    {
        g.fillOval((int)(X0+x*getHeight()/(scale*2)-fat/2),(int)(Y0-y*getHeight()/(scale*2)-fat/2),fat,fat);
        //repaint();
    }
    private void connectPoints(Graphics g,double x1,double y1,double x2,double y2)
    {
        g.drawLine((int)(X0+x1*getHeight()/(scale*2)),(int)(Y0-y1*getHeight()/(scale*2)),(int)(X0+x2*getHeight()/(scale*2)),(int)(Y0-y2*getHeight()/(scale*2)));
    }
}

