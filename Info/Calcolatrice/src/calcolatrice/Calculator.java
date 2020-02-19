/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calcolatrice;

/**
 *
 * @author Michele
 */
public class Calculator {

    private String operation;
    private double firstNumber;
    private double secondNumber;
    private double result;
    private boolean filledZero ;
    private boolean point;

    public Calculator() {
        allClear();
    }

    public String getOperation() {
        return operation;
    }

    public double getFirstNumber() {
        return firstNumber;
    }

    public double getSecondNumber() {
        return secondNumber;
    }

    public double getResult() {
        return result;
    }

    public boolean isFilledZero() {
        return filledZero;
    }

    public boolean isPoint() {
        return point;
    }
    

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setFirstNumber(double firstNumber) {
        this.firstNumber = firstNumber;
    }

    public void setSecondNumber(double secondNumber) {
        this.secondNumber = secondNumber;
    }
    
    public void setFilledZero(boolean filledZero) {
        this.filledZero = filledZero;
    }
    
    public void setPoint(boolean point) {
        this.point = point;
    }
    
    public void result(){
        switch(operation){
            case "add":
                addition();
                System.out.println("add");
                break;
            case "sub":
                substraction();
                System.out.println("sub");
                break;
            case "mult":
                multiplication();
                System.out.println("mult");
                break;
            case "div":
                division();
                System.out.println("div");
                break;
        }
        System.out.println(firstNumber);
        System.out.println(secondNumber);
        operation = "";
        firstNumber = 0;
        secondNumber = 0;
        
    }
    public void addition() {
        result = firstNumber + secondNumber;
    }
    public void substraction() {
        result = firstNumber - secondNumber;
    }
    public void multiplication() {
        result = firstNumber * secondNumber;
    }
    public void division(){
        result = firstNumber / secondNumber;
    }
    
    public void allClear(){
        operation = "";
        firstNumber = 0;
        secondNumber = 0;
        result = 0;
        filledZero = true;
        point = false;
    }
    
    /**
     * @param args the command line arguments
     */
}
