package Ditto;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Mishko
 */
public class StartUp {

    public static void loadVars() {
        //load account variables
        ArrayList<String> temp = FolderC.readFile(System.getProperty("user.dir").substring(0, System.getProperty("user.dir").lastIndexOf(System.getProperty("file.separator")) + 1) + "SystemFiles" + System.getProperty("file.separator") + "Saved Data.txt");
        String Mailloc = System.getProperty("user.dir").substring(0, System.getProperty("user.dir").lastIndexOf(System.getProperty("file.separator")) + 1) + "SystemFiles" + System.getProperty("file.separator") + "MailBox.txt";
        ArrayList<String> temp2 = FolderC.readFile(Mailloc);

        if (temp.isEmpty() || temp.size() > 0 && temp.get(0).equals("File not found")) {
            createVars();
            temp = FolderC.readFile(System.getProperty("user.dir").substring(0, System.getProperty("user.dir").lastIndexOf(System.getProperty("file.separator")) + 1) + "SystemFiles" + System.getProperty("file.separator") + "Saved Data.txt");
        }
        String str1 = "", str2 = "", str3 = "", str4 = "";
        for (int i = 0; i < temp.size(); i++) {

            if (temp.get(i).contains(": ")) {
                String tempstr = temp.get(i).substring(0, temp.get(i).indexOf(": "));
                if (tempstr.equals("MouseLock")) {
                    Ditto.mouseLock = Convert.toBoolean(temp.get(i).substring(temp.get(i).indexOf(": ") + 2));
                } else if (tempstr.equals("Username")) {
                    str1 = temp.get(i).substring(temp.get(i).indexOf(": ") + 2);
                } else if (tempstr.equals("ID")) {
                    str2 = temp.get(i).substring(temp.get(i).indexOf(": ") + 2);
                } else if (tempstr.equals("Address")) {
                    str3 = temp.get(i).substring(temp.get(i).indexOf(": ") + 2);
                } else if (tempstr.equals("Password")) {
                    str4 = temp.get(i).substring(temp.get(i).indexOf(": ") + 2);
                }
            }
        }
        if (!str1.equals(str2) && !str2.equals(str3) && !str3.equals(str4) && !str4.equals("")) {
            Ditto.User = new Email(str1, str2, str3, str4);
            Ditto.Mail.add(new MailBox("Home", Ditto.User.ID, Ditto.User.address));
            Ditto.Signature = "Sent by: " + Ditto.User.userName + System.getProperty("line.separator") + "ID: " + Ditto.User.ID + System.getProperty("line.separator") + "*******************Begin Message********************" + System.getProperty("line.separator");

        } else {
            System.err.println("File \"Saved Data \" Corrupted, rebooting account...");
            createVars();
        }
        if (!temp2.isEmpty() && temp2.size() >= 3) {
            for (int i = 0; i < temp2.size(); i += 3) {
                if (temp2.get(i).substring(0, temp2.get(i).indexOf(": ")).endsWith("Name") && temp2.get(i + 1).substring(0, temp2.get(i + 1).indexOf(": ")).endsWith("ID") && temp2.get(i + 2).substring(0, temp2.get(i + 2).indexOf(": ")).endsWith("Address")) {
                    Ditto.Mail.add(new MailBox(temp2.get(i).substring(temp2.get(i).indexOf(": ") + 2), temp2.get(i + 1).substring(temp2.get(i + 1).indexOf(": ") + 2), temp2.get(i + 2).substring(temp2.get(i + 2).indexOf(": ") + 2)));
                } else {
                    System.err.println("File \"MailBox\" Corrupted, displaying file...");
                    Convert.printArray(FolderC.readFile(Mailloc));
                    System.err.println("Clearing \"MailBox\"...");
                    FolderC.writeToFile(Mailloc, "", false);
                    i = temp.size() - 1;
                }
            }
        } else if (!temp2.isEmpty() && temp.size()<3){
            System.err.println("File \"MailBox\" Corrupted, displaying file...");
            Convert.printArray(FolderC.readFile(Mailloc));
            System.err.println("Clearing \"MailBox\"...");
            FolderC.writeToFile(Mailloc, "", false);
        }
    }

    public static void createVars() {
        //Create new account variables
        FolderC.createFile(System.getProperty("user.dir").substring(0, System.getProperty("user.dir").lastIndexOf(System.getProperty("file.separator")) + 1) + "SystemFiles" + System.getProperty("file.separator") + "Saved Data.txt");
        FolderC.writeToFile(System.getProperty("user.dir").substring(0, System.getProperty("user.dir").lastIndexOf(System.getProperty("file.separator")) + 1) + "SystemFiles" + System.getProperty("file.separator") + "Saved Data.txt", Convert.arrayToString(newAccount()), false);
        FolderC.fileTree();
        loadVars();
    }

    public static ArrayList<String> newAccount() {
        //sub-routine of createVars()
        Scanner scanner = new Scanner(System.in);
        boolean pass = false;
        ArrayList<String> temp = new ArrayList<String>();
        temp.add("Username: " + System.getProperty("user.name"));
        temp.add("MouseLock: false");
        while (!pass) {
            String tempstr;
            System.out.println("Please input a Gmail address:");
            tempstr = scanner.next();
            if (tempstr.contains("@gmail.com")) {
                temp.add("Address: " + tempstr);
                pass = true;
            } else {
                System.err.println("Input not accepted. Please try again.");
            }
        }
        pass = false;
        while (!pass) {
            String tempstr;
            String tempstr2;
            System.out.println("Please enter your Gmail password:");
            tempstr = scanner.next();
            System.out.println("Please confirm your password:");
            tempstr2 = scanner.next();
            if (tempstr.equals(tempstr2)) {
                temp.add("Password: " + tempstr);
                pass = true;
            } else {
                System.err.println("Passwords do not match. Please try again.");
            }
        }
        pass = false;
        Integer randint = Convert.toInt(Control.getMousePos().replaceAll("\\[", "").replaceAll("]", "").replaceAll(",", "")) + Encryption.randint(Integer.MIN_VALUE, Integer.MAX_VALUE);
        temp.add("ID: " + Convert.toLetter(randint.toString()));
        while (!pass) {
            System.out.println("Is this information correct?");
            System.out.println(Convert.arrayToString(temp).substring(1));
            System.out.println("0) Yes");
            System.out.println("1) No");
            String inpt = scanner.next();
            if (inpt.equals("0")) {
                return temp;
            } else if (inpt.equals("1")) {
                temp = newAccount();
                return temp;
            } else {
                System.out.println("That is not an option.");
            }
        }
        temp.clear();
        return temp;
    }
}
