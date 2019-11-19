/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import java.awt.AWTException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main{
    static class Patience{
        AtomicLong patience = new AtomicLong(1 * 60 * 60);
    }
    
    public static Patience limit = new Patience();
    
    private static void Message(String message, long startTime){
        String name = Thread.currentThread().getName();
        long time = (System.currentTimeMillis() - startTime) / 1000;
        int id = (int) Thread.currentThread().getId();
        System.out.format("[id: %s - %ss] %s - %s\n", id, time, name, message);
    }

    public static class InputLoop implements Runnable{
        Scanner scanner = new Scanner(System.in);
        int nString = 0;

        public void run(){
            final String EXIT_STRING = "ciao";
            String input;
            long startTime = System.currentTimeMillis();
            String message = "";
            do{
                input = scanner.nextLine();
                if(!input.equalsIgnoreCase(EXIT_STRING) && System.currentTimeMillis()-startTime < limit.patience.get() && !input.isEmpty()){
                    message = String.format("%s , stringa numero %s", input, nString);
                    Message(message, startTime);
                    nString++;
                }
            }while(!input.equalsIgnoreCase(EXIT_STRING) && System.currentTimeMillis()-startTime < limit.patience.get());
            if(System.currentTimeMillis()-startTime >= limit.patience.get())
                Message("the Main was tired to wait", startTime);
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException{
        if (args.length > 0) {
            try {
                limit.patience.set(Long.parseLong(args[0]) * 1000);
            } catch (NumberFormatException e) {
                System.err.println("Argument must be an integer.");
                System.exit(1);
            }
        }
        Thread t = new Thread(new InputLoop());
        t.start();
        t.join(limit.patience.get());
        t.interrupt();
        t.join(100);
        if(t.isAlive())
            Main.enter();
        t.join();
        Message("finally", 0);
    }
    
    public static void enter(){
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }
}