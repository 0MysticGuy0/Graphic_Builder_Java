import MyCalculator.Calculator;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

///////////////////////////////////////////////////////////////////////////////////////
public class Drawer extends JPanel
{
    Calculator calc;
    public int Ofat;
    public int Xoffset,Yoffset;
    public int scale;
    private int X0,Y0;
    private double drawStep=0.1;
    ArrayList<GraphInfo> graphExpressions=new ArrayList<>();
    Drawer(){
        calc=new Calculator();
        X0=getWidth()/2+getXoffset();
        Y0=getHeight()/2-getYoffset();
        Ofat=2;
        Xoffset=0;
        Yoffset=0;
        scale=10;
        graphExpressions.add(new GraphInfo());
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
        // System.out.println(scale);
        for (int i = 0; i < p; i++) {//Y
            g.drawLine(0, getHeight() * i / p , getWidth(), getHeight() * i / p);
        }
        p = (int)Math.round((double)p * getWidth() / getHeight());
        for (int i = 0; i < p; i++) {//X
            g.drawLine(getWidth() * i / p, 0, getWidth() * i / p, getHeight());
            //if(i==0 || i==p-1)
        }

        g.setColor(Color.BLACK);
        g.fillRect(X0-Ofat/2,0,Ofat,getHeight());//OY
        g.fillRect(0,Y0-Ofat/2,getWidth(),Ofat);//OX

        for(GraphInfo gi:graphExpressions) {//Рисуем графики
            g.setColor(gi.color);
            calc.SetExpr(gi.expr);
            //System.out.println(gi.constraints.get_Size());
            drawGraph(g,gi.constraints);
        }
        g.setColor(Color.black);
    }

    private void drawGraph(Graphics g,constraintsP cp){
        int xscale=scale*getWidth()/getHeight();
        for(double i=-xscale-Xoffset;i<=xscale-drawStep-Xoffset;i+=drawStep) {
            double x1 = i;
            double x2 = i + drawStep;
            double y1 = calc.getPointY(x1);
            double y2 = calc.getPointY(x2);

            if (cp.isValid(x1)) {
                if (!Double.isInfinite(y1) && !Double.isNaN(y1))
                {
                    drawPoint(g, x1, y1, 6);

                    if (!Double.isInfinite(y2) && !Double.isNaN(y2) && cp.isValid(x2))
                        connectPoints(g, x1, y1, x2, y2);
                }
                else System.out.println("INF or NAN (" + x1 + " ; " + y1 + ")");
            }

/*
        double lastX=Double.NaN,lastY=Double.NaN;
        for(double i=-xscale-Xoffset;i<=xscale-drawStep-Xoffset;i+=drawStep) {
            for(double j=-scale-Yoffset;j<=scale-drawStep-Yoffset;j+=drawStep) {
                    if(calc.canDraw(i,j,0.01)) {
                        drawPoint(g, i, j, 6);
                    }
                    //if(!Double.isNaN(lastY) && !Double.isNaN(lastX))
                        //connectPoints(g,lastX,lastY,i,j);
                    //lastX=i;
                   // lastY=j;
            }
        }
*/
        }
       // cp.print();
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
