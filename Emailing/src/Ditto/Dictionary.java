/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ditto;

import java.util.ArrayList;
import java.util.Locale;

/**
 *
 * @author Elizabeth
 */
public class Dictionary {

    public static ArrayList<String> defineWord(String word) {
        word = word.toUpperCase(Locale.ENGLISH);
        ArrayList<String> arr = FolderC.readFile(System.getProperty("user.dir") + System.getProperty("file.separator") + "SystemFiles" + System.getProperty("file.separator") + "Dictionary.txt");
        int max = arr.size() - 1;
        ArrayList<String> arr2 = new ArrayList<String>();
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).equals(word)) {
                arr2.add(arr.get(i));
                i++;
                while (i != max && arr.get(i).isEmpty() || i != max && arr.get(i) != arr.get(i).toUpperCase(Locale.ENGLISH)) {
                    arr2.add(arr.get(i));
                    i++;
                }
            }
        }
        if (arr2.isEmpty()){
            arr2.add(word + " could not be found.");
        }
        return Convert.removeLast(arr2);
    }
}
