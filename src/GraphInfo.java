import java.awt.*;

public class GraphInfo
{
    public String expr;
    public Color color;
    public constraintsP constraints;
    GraphInfo()
    {
        expr="x^2";
        color=Color.BLACK;
        constraints=new constraintsP();
    }
    GraphInfo(String e,Color c,constraintsP cp)
    {
        expr=e;
        color=c;
        constraints=cp;
    }
}
///////////////////////////////////////////////////////

