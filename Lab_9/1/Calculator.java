public class Calculator {
    private char op;
    private double a,b;
    

    public Calculator() {
        op = ' ';
        a = 0;
        b = 0;
       
    }

    public void set_op(char op) {
        this.op = op;
    }
    
    public void set_ab(String aString, String bString) {
        a = Double.parseDouble(aString);
        b = Double.parseDouble(bString);
    }
    

    public String calculate() {
        String result = "";
        
        switch (this.op) {
            case '+': result = Double.toString(a + b);               
                break;
            case '-': result = Double.toString(a - b);
                break;
            case '/': if(b==0) result = "Not possible"; else result = Double.toString(a / b);
                break;
            case '*': result = Double.toString(a * b);
                break;        
            default: result = "Unknown opperation";
                break;
        }
        
        return result;
    }
    
}
