/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ditto;

import Jama.Matrix;
import java.util.ArrayList;

/**
 *
 * @author student
 */
public class Encryption {
    //**ArrayList refered to as "Array" unless otherwise specified**
    //Provides encryption methods for data transfers

    public static Matrix matx;
    private static Matrix I;
    public static final Character[] basealpha = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '`', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '-', '=', '~', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '+', '[', ']', '{', '}', ';', '\'', ':', '"', '|', ',', '.', '/', '<', '>', '?', '\\', ' ', '\n'};
    public static Character temps[] = {'f', 'c', 'L', 'M', '8', 'b', 'S', '.', 'r', '{', 'd', 'q', 'w', 'Q', '2', 'F', 'x', '\'', 'i', ';', '6', '|', 'I', 'E', 'V', '!', '[', 'u', 'z', 'n', '~', 'R', '\\', 'g', 'X', 'e', 'J', 'B', '#', 'W', 'D', 'y', 'k', '<', '?', 'v', 'A', '4', 'o', '0', 'Z', 'K', '7', '`', '9', 'l', ']', 'C', 'h', 'T', 'P', 't', 's', '$', '"', '-', 'm', ':', '=', 'p', ')', 'a', '@', 'N', '^', 'U', '>', '}', '*', '+', '%', '_', '(', 'O', ',', 'Y', '/', '&', ' ', 'j', '3', '5', 'H', 'G', '1', '\n'};
    public static String leet[] = {"4", "B", "(", "D", "3", "F", "9", "H", "1", "J", "|{", "L", "M", "N", "0", "P", "&", "R", "$", "7", "U", "V", "W", "}{", "Y", "2", "4", "b", "(", "d", "3", "f", "9", "h", "1", "j", "|{", "l", "m", "n", "0", "p", "&", "r", "$", "7", "u", "v", "w", "}{", "Y", "2", "`", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "-", "=", "~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "_", "+", "[", "]", "{", "}", ";", "\"", ":", "\"", "|", ",", ".", "/", "<", ">", "?", "\\", " ", "\n"};

    

    public Encryption(Matrix matxs) {
        //creates a new Encryption base
        matx = matxs;
        double[][] Is = new double[][]{
            {1, 0, 0},
            {0, 1, 0},
            {0, 0, 1}
        };
        I = new Matrix(Is);

    }

    @Override
    public String toString() {
        //Converts an Encryption object to a String
        double[][] temp = matx.getArray();
        String tempstr = "[";
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp.length; j++) {
                tempstr += (int) temp[i][j] + ", ";
            }
            tempstr = tempstr.substring(0, tempstr.length() - 2);
            tempstr += "]\n[";
        }
        return tempstr.substring(0, tempstr.length() - 2);
    }

    public static int randint(int min, int max) {
        //Generates a random integer
        return (int) ((((Math.random()) * 100) % (max - min + 1)) + min);
    }

    public static Character[] shuffle(Character[] list) {
        //shuffels a list of characters
        int x;
        Character[] shuffle2 = new Character[list.length];
        ArrayList<Integer> safety = new ArrayList<Integer>();
        for (int i = 0; i < list.length; i++) {
            x = randint(0, list.length - 1);
            while (safety.contains(x)) {
                x = randint(0, list.length - 1);
            }
            shuffle2[i] = list[x];
            safety.add(x);
        }
        return shuffle2;
    }

    public boolean validate() {
        //Validates that a Matrix possesses a valid inverse
        System.out.println();
        System.out.println(new Encryption(I).toString());
        return compMatrix(matx.times(matx.inverse()), I);
    }

    public boolean compMatrix(Matrix m1, Matrix m2) {
        //Compares two matrixes and reports whether they are the identical
        if (m1.getRowDimension() != m2.getRowDimension() || m1.getColumnDimension() != m2.getColumnDimension()) {
            return false;
        } else {
            for (int i = 0; i < m1.getRowDimension(); i++) {
                for (int j = 0; j < m1.getColumnDimension(); j++) {
                    if (m1.get(i, j) != m2.get(i, j)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static ArrayList<String> encode(ArrayList<String> List) {
        //Encrypts an Array of Strings
        String tempstr;
        for (int i = 0; i < List.size(); i++) {
            tempstr = "";
            for (int j = 0; j < List.get(i).length(); j++) {
                for (int k = 0; k < basealpha.length; k++) {
                    if (List.get(i).charAt(j) == basealpha[k]) {
                        tempstr += temps[k];
                    }
                }
            }
            List.set(i, tempstr);
        }
        return List;
    }

    public static String encode(String List) {
        //Encrypts an Array of Strings
        String tempstr = "";
        for (int j = 0; j < List.length(); j++) {
            for (int k = 0; k < basealpha.length; k++) {
                if (List.charAt(j) == basealpha[k]) {
                    tempstr += temps[k];
                }
            }
        }
        return tempstr;
    }

    public static ArrayList<String> decode(ArrayList<String> List) {
        //Decrypts an Array of Strings
        String tempstr;
        for (int i = 0; i < List.size(); i++) {
            tempstr = "";
            for (int j = 0; j < List.get(i).length(); j++) {
                for (int k = 0; k < temps.length; k++) {
                    if (List.get(i).charAt(j) == temps[k]) {
                        tempstr += basealpha[k];
                    }
                }
            }
            List.set(i, tempstr);
        }
        return List;
    }

    public static String decode(String List) {
        //Decrypts an Array of Strings
        String tempstr = "";
        for (int j = 0; j < List.length(); j++) {
            for (int k = 0; k < temps.length; k++) {
                if (List.charAt(j) == temps[k]) {
                    tempstr += basealpha[k];
                }
            }
        }
        return tempstr;
    }

    public static ArrayList<String> Leet(ArrayList<String> List) {
        String tempstr;
        for (int i = 0; i < List.size(); i++) {
            tempstr = "";
            for (int j = 0; j < List.get(i).length(); j++) {
                for (int k = 0; k < temps.length; k++) {
                    if (List.get(i).charAt(j) == basealpha[k]) {
                        tempstr += leet[k];
                    }
                }
            }
            List.set(i, tempstr);
        }
        return List;
    }
}
//////char temp[] = shuffle(basealpha);
//////        for (int i = 0; i<temp.length;i++){
//////            System.out.print(temp[i]+"','");
//////        }