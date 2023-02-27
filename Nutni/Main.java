package Nutni;
import javax.swing.*;
public class Main{
    //สร้างหน้าจอเกม
    public static void main(String[] args) {
        JFrame window = new JFrame("2D GAME");
        window.setSize(600,700);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        window.add(new Game());
        window.setVisible(true);
    }
}
