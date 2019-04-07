/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ditto;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author Elizabeth
 */
public class Control {

    public static String takeScreenShot() {
        //Takes a screen shot of the desktop and saves it to the "Exports" folder
        String tempstr = "";
        try {
            Robot robot = new Robot();
            BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            tempstr = System.getProperty("user.dir").substring(0,System.getProperty("user.dir").lastIndexOf(System.getProperty("file.separator"))+1) + System.getProperty("file.separator") + "Exports" + System.getProperty("file.separator") + Time.getInstant() + ".png";
            ImageIO.write(screenShot, "PNG", new File(tempstr));

        } catch (Exception e) {
        }
        return tempstr;
    }

    public static String takeScreenShot(String fileLoc) {
        //Takes a screen shot of the desktop and saves it to a user specified directory
        String tempstr = "";
        try {
            Robot robot = new Robot();
            BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            tempstr = fileLoc + System.getProperty("file.separator") + Time.getInstant() + ".png";
            ImageIO.write(screenShot, "PNG", new File(tempstr));
        } catch (Exception e) {
        }
        return tempstr;
    }

    public static void jump_mouse(int posx, int posy) {
        //moves the mouse to a specified position
        try {
            Robot robot = new Robot();
            robot.mouseMove(posx, posy);
        } catch (AWTException e) {
        }
    }

    public static boolean pos_check(int tarx, int tary) {
        //checks if mouse is at a certain location
        Point mousePt = MouseInfo.getPointerInfo().getLocation();
        int mouseX = Math.max(0, mousePt.x);
        int mouseY = Math.max(0, mousePt.y);

        return (mouseX == tarx && mouseY == tary);

    }

    public static boolean pos_check(int tarx1, int tarx2, int tary1, int tary2) {
        Point mousePt = MouseInfo.getPointerInfo().getLocation();
        int mouseX = Math.max(0, mousePt.x);
        int mouseY = Math.max(0, mousePt.y);

        return (mouseX > tarx1 && mouseX < tarx2 && mouseY > tary1 && mouseY < tary2);

    }

    public static String getMousePos() {
        //returns the current mouse position
        Point mousePt = MouseInfo.getPointerInfo().getLocation();
        int mouseX = Math.max(0, mousePt.x);
        int mouseY = Math.max(0, mousePt.y);

        return ("[" + mouseX + "," + mouseY + "]");

    }
}
