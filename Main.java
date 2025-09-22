import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Basic Calculator");
        Scanner sc = new Scanner(System.in);
        int ch;
        do {
            System.out.println("======================");
            System.out.println("1. Addition");
            System.out.println("2. Subtraction");
            System.out.println("3. Multiplication");
            System.out.println("4. Division");
            System.out.println("5. Exit");
            System.out.println("======================");
            System.out.println("Enter your choice");
            ch = sc.nextInt();
            switch (ch) {
                case 1:
                    Addition addition = new Addition();
                    addition.add();
                    break;
                case 2:
                    Substraction substraction = new Substraction();
                    substraction.subs();
                    break;
                case 3:
                    Multiplication multiplication = new Multiplication();
                    multiplication.multi();
                    break;
                case 4:
                    Division division = new Division();
                    division.divi();
                    break;
                default:
                    System.out.println("Bye");
            }
        } while (ch != 5);
    }
}

class Addition{
    int num1;
    int num2;
    void add() {
        try {
            Scanner sc=new Scanner(System.in);
            System.out.println("Enter the first number");
            num1=sc.nextInt();
            System.out.println("Enter the second number");
            num2=sc.nextInt();
            int result=num1+num2;
            System.out.println("Addition of "+num1+" and "+num2+" = "+result);
        }
        catch (Exception e) {
            System.out.println("Error");
        }
    }
}
class Substraction{
    int num1;
    int num2;
    void subs() {
        Scanner sc=new Scanner(System.in);
        System.out.println("First number should getter than second number");
        System.out.println("Enter the first number");
        num1=sc.nextInt();
        System.out.println("Enter the second number");
        num2=sc.nextInt();
        if(num1<num2){
            System.out.println("First number should getter than second number");
        }
        else{
            int result=num1-num2;
            System.out.println("Substraction of "+num1+" and "+num2+" = "+result);
        }
    }
}
class Multiplication{
    int num1;
    int num2;
    void multi() {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the first number");
        num1=sc.nextInt();
        System.out.println("Enter the second number");
        num2=sc.nextInt();
        int result=num1*num2;
        System.out.println("Multiplication of "+num1+" and "+num2+" = "+result);
    }
}
class Division{
    int num1;
    int num2;
    void divi() {
        //cant divisible error
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the first number");
        num1=sc.nextInt();
        System.out.println("Enter the second number");
        num2=sc.nextInt();
        if (num2==0){
            System.out.println("Division by zero not possible");
        }
        else{
            int result= num1/num2;
            System.out.println("Division of "+num1+" and "+num2+" = "+result);
        }
    }

}
