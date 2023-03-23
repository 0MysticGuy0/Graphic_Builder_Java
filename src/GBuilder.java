import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import MyCalculator.*;

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

    JPanel inputP=new JPanel();

    ArrayList<graphInp> functionsAr=new ArrayList<>();
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
            else if(e.getActionCommand().equals("del")){
                if(functionsAr.size()>1)
                {
                    for(int i=0;i<functionsAr.size();i++)
                    {
                        if(functionsAr.get(i).destroyB==e.getSource()) {
                            inputP.remove(functionsAr.get(i));
                            functionsAr.remove(i);
                            drawerP.graphExpressions.remove(i);
                            break;
                        }
                    }
                }
            }
            else if(e.getActionCommand().equals("addInp")){
                    for(int i=0;i<functionsAr.size();i++)
                    {
                        if(functionsAr.get(i).addB==e.getSource()) {
                            functionsAr.add(i,new graphInp(drawerP,AL));
                            inputP.add(functionsAr.get(i));
                            drawerP.graphExpressions.add(i,Integer.toString(i));
                            break;
                        }
                    }
            }
            System.out.println(functionsAr.size());
            for(int i=0;i<functionsAr.size();i++)
            {
                drawerP.graphExpressions.set(i,functionsAr.get(i).expr.getText());
            }

            interactP.revalidate();
            repaint();
            //System.out.println(drawerP.graphExpressions.get(0));
        }
    };
    ////////////////////////////////
    public GBuilder()
    {
        inputP.setLayout(new BoxLayout(inputP,BoxLayout.Y_AXIS));
        setTitle("PankiHoy");
        setSize(startWith,startHeight);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setResizable(false);
        setLayout(null);
        functionsAr.add(new graphInp(drawerP,AL));

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

        interactP.add(inputP);
        inputP.setSize(200,300);
        inputP.setBorder(bord1);
        for(graphInp i:functionsAr)
        {
            System.out.println(55555);
            inputP.add(i);
        }

        add(drawerP);
        add(interactP);
        //System.out.println(10);
    }

    public void paint(Graphics g){
        super.paint(g);
    }
    public static void main(String []args)//////////////////MAIN
    {
        new GBuilder();

    }

}
///////////////////////////////////////////////////////////////////////////////////////
class Drawer extends JPanel
{
    Calculator calc;
    public int Ofat;
    public int Xoffset,Yoffset;
    public int scale;
    private int X0,Y0;
    private double drawStep=0.1;
    ArrayList<String> graphExpressions=new ArrayList();
    Drawer(){
        calc=new Calculator();
        X0=getWidth()/2+getXoffset();
        Y0=getHeight()/2-getYoffset();
        Ofat=2;
        Xoffset=0;
        Yoffset=0;
        scale=10;
        graphExpressions.add("sin(100*x)*3");
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
            p = (int)Math.round((double)p * getWidth() / getHeight());
            for (int i = 0; i < p; i++) {
                g.drawLine(getWidth() * i / p, 0, getWidth() * i / p, getHeight());
            }

        g.setColor(Color.BLACK);
        g.fillRect(X0-Ofat/2,0,Ofat,getHeight());//OY
        g.fillRect(0,Y0-Ofat/2,getWidth(),Ofat);//OX
        g.setColor(Color.BLUE);
        //drawStep=0.025;
        //graphExpression="abs( 0.25*x+3* (cos (x*100) ) * sin(x) )";
       // graphExpression="abs(sin(x*20)*4)";
        for(String exp:graphExpressions) {
            calc.SetExpr(exp);
            drawGraph(g);
            //calc.SetExpr("-abs(cos(x*10)*10)");
            //System.out.println(calc.ExprStack);
            //drawGraph(g);
        }
    }

    public static boolean nearlyEquals(double a,double b,double inaccuracy)
    {
        return ((a-b)>=-inaccuracy && (a-b)<=inaccuracy);
    }

    private void drawGraph(Graphics g){
        int xscale=scale*getWidth()/getHeight();

        for(double i=-xscale-Xoffset;i<=xscale-drawStep-Xoffset;i+=drawStep) {
                double x1 = i;
                double x2 = i + drawStep;
                double y1 = calc.getPointY(x1);
                double y2 = calc.getPointY(x2);

            if(!Double.isInfinite(y1)&& !Double.isNaN(y1) ) {
                //System.out.println(y1);
                drawPoint(g, x1, y1, 6);
                if(!Double.isInfinite(y2)&& !Double.isNaN(y2))
                    connectPoints(g, x1, y1, x2, y2);
            }
            else System.out.println("INF or NAN ("+x1+" ; "+y1+")");
        }

        /*
        double lastX=Double.NaN,lastY=Double.NaN;
        for(double i=-xscale-Xoffset;i<=xscale-drawStep-Xoffset;i+=drawStep) {
            for(double j=-scale-Yoffset;j<=scale-drawStep-Yoffset;j+=drawStep) {
                if (nearlyEquals(j,i*i,drawStep))
                {
                    drawPoint(g, i, j, 6);
                    if(!Double.isNaN(lastY) && !Double.isNaN(lastX))
                        connectPoints(g,lastX,lastY,i,j);
                    lastX=i;
                    lastY=j;
                }
            }
        }
        */
        g.setColor(Color.BLACK);
    }

    ///////////////
    private int getXoffset(){
        if(scale!=0)
        return (int)Math.round((double)Xoffset*getHeight()/(scale*2));
        else return 0;
    }

    private int getYoffset(){
        if(scale!=0)
        return (int)Math.round((double)Yoffset*getHeight()/(scale*2));
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

////////////////////////////
class graphInp extends JPanel
{
    public JTextField expr=new JTextField("x^2",50);
    public JButton destroyB=new JButton("-");
    public JButton addB=new JButton("+");
    static Drawer drawer;
    graphInp(Drawer d,ActionListener AL)
    {
        drawer=d;
        add(expr);
        add(destroyB);
        add(addB);
        expr.setSize(200,50);
        destroyB.setActionCommand("del");
        destroyB.addActionListener(AL);
        addB.setActionCommand("addInp");
        addB.addActionListener(AL);
        expr.addActionListener(AL);
    }
}
