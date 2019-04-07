/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ditto;

import java.util.ArrayList;

/**
 *
 * @author Micah Richards
 */
public class QuantumArray {

    public Integer depth;
    public ArrayList<QuantumArray> list = new ArrayList<QuantumArray>();
    public String object;

    public QuantumArray(String objects, Integer depths) {
        depth = depths;
        object = objects;
    }

    @Override
    public String toString() {
        String temp2 = toStringsub();
        return temp2.substring(1, temp2.length());
    }

    public String toStringsub() {
        String temp = "";
        for (int i = 0; i < depth; i++) {
            temp += "     ";
        }

        if (list.isEmpty()) {
            return ("\n" + temp + object.toString());
        } else {
            return ("\n" + temp + object.toString() + arrayToString(list));
        }

    }

    public String arrayToString(ArrayList<QuantumArray> list) {
        String temp = "";
        for (int i = 0; i < list.size(); i++) {
            temp += list.get(i).toStringsub();
        }
        return temp;
    }

    public static ArrayList<String> clean(ArrayList<String> list) {
        for (int i = 0; i < list.size(); i++) {
            if ("\n".equals(list.get(i))) {
                list.remove(i);
                i--;
            }
            if ("".equals(list.get(i))) {
                list.remove(i);
                i--;
            }
            for (int j = 0; j < list.size(); j++) {
                if (list.get(i).length() >= (5 * j) + 1) {
                    if (list.get(i).length() == 5 * j) {
                        list.remove(i);
                        i--;
                    }
                    if ("!".equals(list.get(i).substring((5 * j), (5 * j) + 1))) {
                        list.set(i, list.get(i).substring(0, (5 * j)) + list.get(i).substring((5 * j) + 1, list.get(i).length()));
                    }
                }
            }
        }
        return list;
    }
}
