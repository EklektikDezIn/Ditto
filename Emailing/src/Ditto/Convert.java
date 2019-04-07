/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ditto;

import java.util.ArrayList;
import java.util.Locale;
import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

/**
 *
 * @author Elizabeth
 */
public class Convert {
    //**ArrayList refered to as "Array" unless otherwise specified**
    //Converts Data from one form into another

    public static void say(ArrayList<String> textlist) {
        //Converts a string into audio text (List)
        try {
            System.out.println(arrayToString(textlist));
            System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
            Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
            Synthesizer synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
            synthesizer.allocate();
            synthesizer.resume();
            for (int i = 0; i < textlist.size(); i++) {
                synthesizer.speakPlainText(textlist.get(i), null);
            }
            synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static void say(String text) {
        //Converts a string into audio text (List)
        try {
            System.out.println(text);
            System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
            Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
            Synthesizer synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
            synthesizer.allocate();
            synthesizer.resume();
            synthesizer.speakPlainText(text, null);
            synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static Integer toInt(String str) {
        //Converts a String to an Integer
        Integer temp = 0;
        for (int i = 0; i < str.length(); i++) {
            temp *= 10;
            temp += returnNum(str.charAt(i));
        }
        return temp;
    }

    public static String toLetter(String str) {
        //Converts a string into a jumble of characters to be used for user accounts.  See loadVars() or createVars()
        String temp = "";
        for (int i = 0; i < str.length(); i++) {
            temp += returnLetter(str.charAt(i));
        }
        return temp;
    }

    public static double toDouble(String str) {
        //Converts a String to a Double
        double temp = 0;
        boolean passedpoint = false;
        boolean negative = false;
        int countpassed = 10;
        if (str.charAt(0) == '-') {
            negative = true;
            str = str.substring(1);
        }
        for (int i = 0; i < str.length(); i++) {
            Character chars = str.charAt(i);
            if (!passedpoint) {
                if (chars.equals('.')) {
                    passedpoint = true;
                } else {

                    if (str.charAt(i) == 'E') {
                        temp = SciNot(temp,str);
                    } else {
                        temp *= 10;
                        temp += returnNum(str.charAt(i));
                    }
                }
            } else {
                if (str.charAt(i) == 'E') {
                    temp = SciNot(temp,str);
                } else {
                    Double tempdou = returnNum(str.charAt(i)).doubleValue();
                    temp += tempdou / countpassed;
                    countpassed *= 10;
                }
            }
        }
        if (temp == 0 && negative) {
            temp = -1;
        } else if (negative) {
            temp *= -1;
        }
        return temp;
    }

    public static boolean toBoolean(String str) {
        //Converts a String to a Boolean
        Boolean temp = null;
        if (str.equals("True") || str.equals("true")) {
            temp = true;
        } else if (str.equals("False") || str.equals("false")) {
            temp = false;
        }
        return temp;
    }

    public static Integer returnNum(Character chara) {
        //Converts a character of a number into an integer format.  see toDouble() or toInteger()
        Integer temp = -1;
        if (chara.equals('0')) {
            temp = 0;
        } else if (chara.equals('1')) {
            temp = 1;
        } else if (chara.equals('2')) {
            temp = 2;
        } else if (chara.equals('3')) {
            temp = 3;
        } else if (chara.equals('4')) {
            temp = 4;
        } else if (chara.equals('5')) {
            temp = 5;
        } else if (chara.equals('6')) {
            temp = 6;
        } else if (chara.equals('7')) {
            temp = 7;
        } else if (chara.equals('8')) {
            temp = 8;
        } else if (chara.equals('9')) {
            temp = 9;
        }
        return temp;
    }

    public static String returnLetter(Character chara) {
        //Converts letters into random letters
        String temp = "ASF";
        if (chara.equals('0')) {
            temp = "sd456f";
        } else if (chara.equals('1')) {
            temp = "vwe7858";
        } else if (chara.equals('2')) {
            temp = "as23d";
        } else if (chara.equals('3')) {
            temp = "w234e";
        } else if (chara.equals('4')) {
            temp = "AS1Fd";
        } else if (chara.equals('5')) {
            temp = "K234tU";
        } else if (chara.equals('6')) {
            temp = "F1425G";
        } else if (chara.equals('7')) {
            temp = "B12eR";
        } else if (chara.equals('8')) {
            temp = "TxD53FU";
        } else if (chara.equals('9')) {
            temp = "BT2D";
        } else if (chara.equals('-')) {
            temp = "Sdh3";
        }
        return temp;

    }

    public static String symToText(String syms) {
        //Converts symbols to written text. see say()
        String Sentence = "";
        for (int i = 0; i < syms.length(); i++) {
            if (syms.charAt(i) == '^') {
                Sentence += " to the power of ";
            } else if (syms.charAt(i) == '*') {
                Sentence += " times ";
            } else if (syms.charAt(i) == '/') {
                Sentence += " divided by ";
            } else if (syms.charAt(i) == '+') {
                Sentence += " plus ";
            } else if (syms.charAt(i) == '-') {
                if (syms.charAt(i + 1) == ' ') {
                    Sentence += " minus ";
                } else {
                    Sentence += " negative ";
                }
            } else if (i == 0 && syms.charAt(i) == '(') {
                Sentence += " times (";
            } else if (i
                    != 0 && syms.charAt(i
                    - 1) != ' ' && syms.charAt(i) == '(') {
                Sentence += " times (";
            } else {
                Sentence += syms.charAt(i);
            }
        }
        return Sentence;
    }

    public static ArrayList convertArray(ArrayList<String> arr1, String totype) {
        //Converts a list of Strings into other data formats
        ArrayList arr2 = new ArrayList();
        if (totype.equals("Int")) {
            for (int i = 0; i < arr1.size(); i++) {
                arr2.add(toInt(arr1.get(i)));
            }
        }
        if (totype.equals("Char")) {
            for (int i = 0; i < arr1.size(); i++) {
                arr2.add(arr1.get(i).charAt(0));
            }
        }
        if (totype.equals("Double")) {
            for (int i = 0; i < arr1.size(); i++) {
                arr2.add(toDouble(arr1.get(i)));
            }
        }
        if (totype.equals("Boolean")) {
            for (int i = 0; i < arr1.size(); i++) {
                arr2.add(toBoolean(arr1.get(i)));
            }
        }
        return arr2;
    }

    public static ArrayList<String> stringToArray(String str, String chr) {
        //Converts a string to an array by adding an item to the array at each instance of a user selected character
        ArrayList<String> temp = new ArrayList<String>();

        while (!str.equals("") && str.contains(chr)) {
            temp.add(str.substring(0, str.indexOf(chr)));
            str = str.substring(str.indexOf(chr) + 1, str.length());
        }
        temp.add(str);
        return temp;
    }

    public static String arrayToString(ArrayList<String> arr) {
        //Converts an array to a string with a "new line" symbol after each array item
        String temp = "";
        for (int i = 0; i < arr.size(); i++) {
            temp = temp + System.getProperty("line.separator") + arr.get(i);
        }
        return temp;
    }

    public static String arrayToString(ArrayList<String> arr, String sym) {
        //Converts an array to a string with a user selected symbol after each array item
        String temp = "";
        for (int i = 0; i < arr.size(); i++) {
            temp = temp + sym + arr.get(i);
        }
        return temp;
    }

    public static String arrayToString(ArrayList<String> arr, int start, int end) {
        //Converts a selected portion of an array to a string with a "new line" symbol after each arry item
        String temp = "";
        for (int i = start; i < end; i++) {
            temp = temp + "\n" + arr.get(i);
        }
        return temp;
    }

    public static void printArray(ArrayList<String> temp) {
        //Prints out an Array with numbered items
        for (int i = 0; i < temp.size(); i++) {
            System.out.println(i + ") " + temp.get(i));
        }
    }
    public static void printArrayMB(ArrayList<MailBox> temp) {
        //Prints out an Array with numbered items
        for (int i = 0; i < temp.size(); i++) {
            System.out.println(i + ") " + temp.get(i).toString());
        }
    }

    public static void combineArrays(ArrayList<String> arr1, ArrayList<String> arr2) {
        //Combines to Arrays
        for (int i = 0; i < arr2.size(); i++) {
            arr1.add(arr2.get(i));
        }
    }

    public static ArrayList<String> combineArrays(ArrayList<String> arr1, ArrayList<String> arr2, int start, int end) {
        //Combines a selected portion of an array with the first Array
        for (int i = start; i < end; i++) {
            arr1.add(arr2.get(i));
        }
        return arr1;
    }

    public static ArrayList removeLast(ArrayList arr1) {
        //removes the last item in an array
        arr1.remove(arr1.size() - 1);
        return arr1;
    }
    public static double SciNot(double inpt,String power){
        return inpt*Math.pow(10,toDouble(power.substring(power.indexOf("E")+1)));
    }
}
