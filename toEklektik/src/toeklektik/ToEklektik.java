/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package toeklektik;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Owner
 */
public class ToEklektik {

    private static final String FILELOC = "C:\\Users\\Owner\\Desktop\\Dropbox\\Ditto\\Emailing\\SystemFiles\\Phonemes.txt";
    private static ArrayList<String> Phonemes;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Phonemes = readFile(FILELOC);
        Scanner inpt = new Scanner(System.in);
        System.out.println("Enter Text:");
        String in = inpt.nextLine().replaceAll("!", "").replaceAll("\\?", "").replaceAll("\\.", "").replaceAll(",", "").replaceAll("\"", "");
        ArrayList<String> temp = stringToArray(in);
        printArray(temp);
        for (int i = 0; i < Phonemes.size(); i++) {
            if (!Phonemes.get(i).replaceAll("\\)", " ").contains("  ")) {
                Phonemes.set(i, Phonemes.get(i).replaceFirst(" ", "  "));
            }

        }
        for (int i = 0; i < temp.size(); i++) {
            int j = 0;
            while (!temp.get(i).toUpperCase().equals(Phonemes.get(j).substring(0, Phonemes.get(j).replaceAll("\\)", " ").indexOf("  "))) && j < Phonemes.size()) {
                j++;
            }
            if (j < Phonemes.size()) {
                temp.set(i, Phonemes.get(j).substring(21));
            } else {
                System.err.println(temp.get(i) + "CANNOT BE FOUND!");
            }
        }

        printArray(temp);
        temp = finalStep(temp);
        String tempstr = "";
             for (int j = 0; j < temp.size(); j++) {
                 tempstr +=temp.get(j) + " ";
             }
             System.out.println(tempstr);
    }

    public static ArrayList<String> finalStep(ArrayList<String> inpt) {
        for (int i = 0; i < inpt.size(); i++) {
            ArrayList<String> temp = stringToArray(inpt.get(i));
            for (int j = 0; j < temp.size(); j++) {
                String tempstr = temp.get(j);
                if (temp.get(j).equals("K") && temp.get(j+1).equals("W")){
                    temp.remove(j+1);
                    tempstr = "q";
                }
                if (temp.get(j).equals("Y") && temp.get(j+1).equals("UW")){
                    temp.remove(j+1);
                    tempstr = "U";
                }
                if (temp.get(j).equals("EH")){
                    tempstr = "e";
                }
                if (temp.get(j).equals("AH")){
                    tempstr = "u";
                }
                if (temp.get(j).equals("IH")){
                    tempstr = "i";
                }
                if (temp.get(j).equals("AA")){
                    tempstr = "o";
                }
                if (temp.get(j).equals("AE")){
                    tempstr = "a";
                }
                if (temp.get(j).equals("S")){
                    tempstr = "s";
                }
                if (temp.get(j).equals("F")){
                    tempstr = "f";
                }
                if (temp.get(j).equals("JH")){
                    tempstr = "j";
                }
                if (temp.get(j).equals("L")){
                    tempstr = "l";
                }
                if (temp.get(j).equals("SH")){
                    tempstr = "z";
                }
                if (temp.get(j).equals("TH")){
                    tempstr = "x";
                }
                if (temp.get(j).equals("UH")){
                    tempstr = "c";
                }
                if (temp.get(j).equals("AO")){
                    tempstr = "v";
                }
                if (temp.get(j).equals("NG")){
                    tempstr = "Q";
                }
                if (temp.get(j).equals("W")){
                    tempstr = "W";
                }
                if (temp.get(j).equals("IY")){
                    tempstr = "E";
                }
                if (temp.get(j).equals("R")){
                    tempstr = "R";
                }
                if (temp.get(j).equals("T")){
                    tempstr = "T";
                }
                if (temp.get(j).equals("Y")){
                    tempstr = "Y";
                }
                if (temp.get(j).equals("AY")){
                    tempstr = "I";
                }
                if (temp.get(j).equals("OW")){
                    tempstr = "O";
                }
                if (temp.get(j).equals("P")){
                    tempstr = "P";
                }
                if (temp.get(j).equals("EY")){
                    tempstr = "A";
                }
                if (temp.get(j).equals("Z")){
                    tempstr = "S";
                }
                if (temp.get(j).equals("D")){
                    tempstr = "D";
                }
                if (temp.get(j).equals("V")){
                    tempstr = "V";
                }
                if (temp.get(j).equals("G")){
                    tempstr = "G";
                }
                if (temp.get(j).equals("HH")){
                    tempstr = "H";
                }
                if (temp.get(j).equals("CH")){
                    tempstr = "J";
                }
                if (temp.get(j).equals("K")){
                    tempstr = "K";
                }
                if (temp.get(j).equals("JH")){
                    tempstr = "Z";
                }
                if (temp.get(j).equals("DH")){
                    tempstr = "X";
                }
                if (temp.get(j).equals("UW")){
                    tempstr = "C";
                }
                if (temp.get(j).equals("AW")){
                    tempstr = "V";
                }
                if (temp.get(j).equals("B")){
                    tempstr = "B";
                }
                if (temp.get(j).equals("N")){
                    tempstr = "N";
                }
                if (temp.get(j).equals("M")){
                    tempstr = "M";
                }
                temp.set(j, tempstr);
            }
            String tempstr = "";
             for (int j = 0; j < temp.size(); j++) {
                 tempstr +=temp.get(j);
             }
             inpt.set(i, tempstr);
        }
        return inpt;
    }

    public static ArrayList<String> stringToArray(String str) {
        //Converts a string to an array by adding an item to the array at each instance of a user selected character
        ArrayList<String> temp = new ArrayList<String>();

        while (!str.equals("") && str.contains(" ")) {
            temp.add(str.substring(0, str.indexOf(" ")));
            str = str.substring(str.indexOf(" ") + 1, str.length());
        }
        temp.add(str);
        return temp;
    }

    public static void printArray(ArrayList<String> temp) {
        //Prints out an Array with numbered items
        for (int i = 0; i < temp.size(); i++) {
            System.out.println(i + ") " + temp.get(i));
        }
    }

    public static ArrayList<String> readFile(String fileName) {

        ArrayList<String> temp = new ArrayList<String>();

        try {

            java.io.File file = new java.io.File(fileName);
            if (file.isFile()) {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    temp.add(scanner.nextLine());
                }
                scanner.close();
            } else {
                temp.add("File not found");
            }
        } catch (FileNotFoundException e) {
        }
        return temp;
    }
}
