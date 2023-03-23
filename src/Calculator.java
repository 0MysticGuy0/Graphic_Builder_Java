import java.util.ArrayList;

public class Calculator {
    //static final char []AviableOperations ={'*','/','+','-','^','s','(',')','P','i','c','f','a','X','Y'};//s-корень i-синус c - косинус P-ПИ a-модуль f-факториал
    static final Operation []AviableOperations = //Все доступные операции/переменные
            {
                    new Operation('*',OperationType.Binary,         "*",    "Умножение"),
                    new Operation('/',OperationType.Binary,         "/",    "Деление"),
                    new Operation('+',OperationType.Binary,         "+",    "Сумма"),
                    new Operation('-',OperationType.Binary,         "-",    "Разность"),
                    new Operation('^',OperationType.Binary,         "^",    "Возведение в степень"),
                    new Operation('s',OperationType.Unary_prefix,   "sqrt", "Квадратный корень"),
                    new Operation('(',OperationType.Other,          "(",    "Скобка"),
                    new Operation(')',OperationType.Other,          ")",    "Скобка"),
                    new Operation('P',OperationType.Variable,       "pi",   "Число ПИ"),
                    new Operation('i',OperationType.Unary_prefix,   "sin",  "Синус (в градусах)"),
                    new Operation('c',OperationType.Unary_prefix,   "cos",  "Косинус (в градусах)"),
                    new Operation('f',OperationType.Unary_postfix,  "!",    "Факториал числа"),
                    new Operation('a',OperationType.Unary_prefix,   "abs",  "Модуль числа"),
                    new Operation('X',OperationType.Variable,       "x",    "Переменная X"),
                    new Operation('Y',OperationType.Variable,       "y",    "Переменная Y"),

            };
    static double calculateSimple(double a,char c,double b){//действия над двумя числами
        switch(c){
            case 'a': return Math.abs(b);
            case 'f': return fact(a);
            case 'c': return Math.cos(Math.toRadians(b));
            case 'i': return Math.sin(Math.toRadians(b));
            case 's': return Math.sqrt(b);
            case '^': return Math.pow(a,b);
            case '*': return a*b;
            case '/': return a/b;
            case '+': return a+b;
            case '-': return a-b;
            default: return 0.0;
        }
    }
    public Operation getOperation(char operation)//получить объект операции
    {
        for(int i=0;i<AviableOperations.length;i++)
            if (AviableOperations[i].sign == operation) return AviableOperations[i];
        return null;
    }

    ArrayList<String> ExprStack; //массив с эелементами выражения
    public Calculator()
    {
        ExprStack=new ArrayList<>();
    }
    public static void printInfo(){
        System.out.println("|-------------------------------|");
        System.out.println("| Доступные операции: ");
        System.out.println("| * - умножение");
        System.out.println("| / - деление");
        System.out.println("| + - сложение");
        System.out.println("| - - вычитание");
        System.out.println("| ^ - возведение в степень");
        System.out.println("| sqrt - квадратный корень");
        System.out.println("| (...) - скобки");
        System.out.println("| PI - число ПИ");
        System.out.println("| sin(...) - синус(в градусах!)");
        System.out.println("| cos(...) - косинус(в градусах!)");
        System.out.println("| (...)! - факториал");
        System.out.println("| abs(...) - модуль");
        System.out.println("|-------------------------------|");
    }
    public double solve(String expr){//результат
        SetExpr(expr);
        System.out.println(ExprStack);
        solveOperations(ExprStack,'(');
        CalculateByPriority(ExprStack);
        return Double.parseDouble(ExprStack.get(0));
    }

    public double getPointY(double x) //получить Y по X
    {
        ArrayList<String> copyExpr=new ArrayList<>(ExprStack);//копируем уравнение для дальнейшего восстановления
        for(int i=0;i<copyExpr.size();i++)//замена х на число
        {
            if(ExprStack.get(i).equals("X")) ExprStack.set(i,Double.toString(x));
        }
        solveOperations(ExprStack,'(');
        CalculateByPriority(ExprStack);
        double res;
        if(ExprStack.size()>0)
          res=Double.parseDouble(ExprStack.get(0));
        else res=0.0;
        ExprStack.clear();
        ExprStack=copyExpr;
        return res;
    }
    private void CalculateByPriority(ArrayList<String> expArr){//просчитать основные действия по приоритету
        solveOperations(expArr,'a');
        solveOperations(expArr,'i','c');
        solveOperations(expArr,'f');
        solveOperations(expArr,'^','s');
        solveOperations(expArr,'*','/');
        solveOperations(expArr,'+','-');
        if(expArr.size()==1 && expArr.get(0).equals("P")) expArr.set(0,Double.toString(Math.PI));
    }

    static double fact(double n)//Факториал
    {
        double res=1;
        for(int i=(int)n;i>=1;i--){
            res*=i;
        }
        return res;
    }
    private void solveOperations(ArrayList<String> ar,char ...operations)//сократить выражение, решив некоторые действия
    {
        for(char c:operations)
        {
            for(int i=0;i<ar.size()-1;i++)
            {
                if(i==ar.size()-2 && getOperation(c).type.equals(OperationType.Unary_postfix)) i+=1; //если в конце выражения унарная-постфиксная операция
                if( ar.get(i).equals( Character.toString(c) ) )
                {
                    if (c == '(')
                    {
                        solveBrackets(i);
                    }
                    else
                    {
                        double n1 = 1.0, n2=1.0;
                        if (!getOperation(c).type.equals(OperationType.Unary_prefix))//если не унарная-префиксная операция
                        {
                            if(ar.get(i-1).equals("P")) n1=Math.PI;
                            else       n1 = Double.parseDouble(ar.get(i - 1));
                        }
                        if(!getOperation(c).type.equals(OperationType.Unary_postfix))//если не унарная-постфиксная операция
                        {
                            if (ar.get(i + 1).equals("P")) n2 = Math.PI;
                            else n2 = Double.parseDouble(ar.get(i + 1));
                        }
                        double r = calculateSimple(n1, c, n2);
                        String res = Double.toString(r);

                        if(!getOperation(c).type.equals(OperationType.Unary_postfix))//если не унарная-постфиксная операция
                             ar.remove(i + 1);
                        if (!getOperation(c).type.equals(OperationType.Unary_prefix))//если не унарная-префиксная операция
                        {
                            ar.remove(i);
                            i -= 1;
                        }
                        //System.out.println("ggg "+ar);
                        ar.set(i, res);
                        // System.out.println(ExprStack);
                    }
                }
            }
        }
    }

    private void solveBrackets(int index)//решение скобок
    {
        ArrayList<String> subExpr=new ArrayList<>();//подвыражение
        int end;//конец подвыражения

        for(end=index+1;end<ExprStack.size();end++)
        {
            if(ExprStack.get(end).equals("("))
            {
                solveBrackets(end);
                end-=1;
            }
            else if(ExprStack.get(end).equals(")"))
            {
                break;
            }
            else
            {
                subExpr.add( ExprStack.get(end) );
            }
        }
        CalculateByPriority(subExpr);
        ExprStack.subList(index+1,end+1).clear();
        //for(int j=end;j>index;j--) ExprStack.remove(j);
        ExprStack.set(index,subExpr.get(0));
    }

    private String fixExpression(String expr){//убираем ненужное, заменяем нужное
        String fixed=expr;
        fixed =fixed.toLowerCase();
        fixed = fixed.replaceAll(" ","");//удалить пробелы
        fixed = fixed.replaceAll(",",".");//правильный вид десятичной точки
        for(int i=0;i<AviableOperations.length;i++)//Замена из пользоваткльского вида на "компьтерный"
        {
            if(!AviableOperations[i].syntax.equals(Character.toString(AviableOperations[i].sign))) //если синтаксис и вид у операции разные
                fixed=fixed.replaceAll(AviableOperations[i].syntax,Character.toString(AviableOperations[i].sign));
        }
        return fixed;
    }

    public void SetExpr(String expr){//получение выражения, разделение для дальнейших расчетов
        ExprStack.clear();//очищаем от прошлого выражения
        expr = fixExpression(expr);
        String cur="";

        for(int i=0;i<expr.length();i++)
        {
            if((expr.charAt(i) >='0' && expr.charAt(i)<='9') || expr.charAt(i)=='.')//сборка числа
            {
                if(ExprStack.size()>0 && ExprStack.get(ExprStack.size()-1).equals("-"))//Если прошлым элементом был -
                {
                    if(ExprStack.size()==1) ExprStack.remove(0); //если выражение начиналось с -, удаляем -
                    else ExprStack.set(ExprStack.size()-1,"+"); //иначе можно заменить - на +
                    ExprStack.add("-1");
                    ExprStack.add("*");
                }
                cur+=expr.charAt(i);//собираем число
                if(i==expr.length()-1){
                    ExprStack.add(cur);
                    cur = "";
                }
            }

            else//сборка операций/переменных
            {
                if(!cur.equals("")) //если прошлым элементом было число
                {
                    ExprStack.add(cur);
                    cur = "";
                }
                for(int j=0;j<AviableOperations.length;j++)//проверяем встретившиеся операции/переменный
                {
                    char s=AviableOperations[j].sign;
                    if(expr.charAt(i) == s)
                    {
                        if(ExprStack.size()>0 || AviableOperations[j].type.equals(OperationType.Unary_prefix))//Если операция в начале выражения и не является  унарной-префиксной - не добавлять
                            ExprStack.add(Character.toString(s));
                    }
                }
            }
        }
    }
}

///////////////////////////////////////////////////////

class Operation
{
    public final char sign;
    public final OperationType type;
    public final String syntax;
    public final String description;
    public Operation()
    {
        sign ='?';
        type=OperationType.Other;
        syntax="none";
        description="none";
    }

    public Operation(char operation,OperationType type,String syntax,String description)
    {
        sign =operation;
        this.type=type;
        this.syntax=syntax;
        this.description=description;
    }
}