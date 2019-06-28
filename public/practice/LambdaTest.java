import java.util.*;

import javax.swing.*;

import javax.swing.Timer;

public class LambdaTest {
    public static void main(String[] args) {
        // System.out.println("The time is " + new Date());
        Timer t = new Timer(1000, event -> System.out.println("The time is " + new Date()));
        t.start();
    }
}