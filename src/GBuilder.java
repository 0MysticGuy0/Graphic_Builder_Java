import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import MyCalculator.*;

public class GBuilder extends JFrame {
    final int startHeight=700,startWith=1400;

    ////////////////////////////////UI elements
    Border bord1=new LineBorder(Color.BLACK);//граница
    JPanel interactP=new JPanel();//шлавная нижняя панель
    Drawer drawerP=new Drawer();//панель рисовки графика
    JPanel mainButtons=new JPanel(new GridLayout(2,3,10,10));//главные кнопки перемещения
    JButton zoomIn=new JButton("+");
    JButton zoomOut=new JButton("-");
    JButton left=new JButton("<");
    JButton right=new JButton(">");
    JButton up=new JButton("^");
    JButton down=new JButton("V");
    JButton zeroB=new JButton("0");//обнуляет позицию камеры

    JPanel inputP=new JPanel();//панель для ввода функции
    JScrollPane inpSc=new JScrollPane();

    ArrayList<graphInp> functionsAr=new ArrayList<>();//массив со всеми введенными функциями
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
            else if(e.getActionCommand().equals("0")){//обнуление позиции камеры
                drawerP.Yoffset=0;
                drawerP.Xoffset=0;
                drawerP.scale=10;
            }
            else if(e.getActionCommand().equals("del")){//удаление функции
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
            else if(e.getActionCommand().equals("addInp")){//добавление функции
                    for(int i=0;i<functionsAr.size();i++)
                    {
                        if(functionsAr.get(i).addB==e.getSource()) {
                            functionsAr.add(i+1,new graphInp(drawerP,AL));
                            inputP.add(functionsAr.get(i+1),i+1);
                            drawerP.graphExpressions.add( i+1,new GraphInfo( Integer.toString(i),functionsAr.get(i).getColor(),functionsAr.get(i).constraints ) );
                            break;
                        }
                    }
            }
           // System.out.println(functionsAr.size());
            for(int i=0;i<functionsAr.size();i++)
            {
                drawerP.graphExpressions.get(i).expr=functionsAr.get(i).expr.getText();
                drawerP.graphExpressions.get(i).color=functionsAr.get(i).getColor();
                drawerP.graphExpressions.get(i).constraints=functionsAr.get(i).constraints;
            }

            interactP.revalidate();
            repaint();
            //System.out.println(drawerP.graphExpressions.get(0));
        }
    };
    ////////////////////////////////
    public GBuilder()
    {
        setTitle("PankiHoy");
        setSize(startWith,startHeight+37);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setResizable(false);
        setLayout(null);
        inputP.setLayout(new BoxLayout(inputP,BoxLayout.Y_AXIS));
        functionsAr.add(new graphInp(drawerP,AL));
        inpSc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        inpSc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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

        zeroB.addActionListener(AL);
        zeroB.setActionCommand("0");
        interactP.add(zeroB);

        inputP.setBorder(bord1);
        inpSc.setPreferredSize(new Dimension(interactP.getWidth()*2/3,interactP.getHeight()-40));
        inpSc.setViewportView(inputP);
        for(graphInp i:functionsAr)
        {
            System.out.println(55555);
            inputP.add(i);
        }

        interactP.add(inpSc);
        add(drawerP);
        add(interactP);
    }

    public void paint(Graphics g){
        super.paint(g);
    }
    public static void main(String []args)//////////////////MAIN
    {
        new GBuilder();
    }

}

