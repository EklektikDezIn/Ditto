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
public class MathWorks {

    public static String toForm(Boolean ShowEq, String Formula) {
        Formula = Formula.replace(" ", "");
        if (!Formula.contains("=") && !checkSym(Formula)) {
            Formula = Formula + "=x";
        }

        Formula = Formula.replace("^", " ^ ").replace("*", " * ").replace("/", " / ").replace("+", " + ").replace("-", " - ").replace("=", " = ").replace("(", " * (");
        Formula = Formula.replace(" ^  * ", " ^ ").replace(" *  * ", " * ").replace(" /  * ", " / ").replace(" +  * ", " + ").replace(" -  * ", " - ");
        Formula = modPower(Formula);
        if (Formula.substring(0, 3).equals(" - ")) {
            Formula = "-" + Formula.substring(3);
        }
        if (Formula.substring(0, 4).equals(" * (") && Formula.charAt(Formula.indexOf(")") + 4) == '(') {
            Formula = Formula.substring(2);
            String temp = SolveEq(false, getPar(Formula)).toString();
            if (temp.contains("=")) {
                Formula = Formula.replace("(" + getPar(Formula) + ")", temp.substring(temp.indexOf("= ") + 2));
            } else {
                Formula = Formula.replace("(" + getPar(Formula) + ")", temp);

            }
        }
        Formula = addOne(Formula);
        if (ShowEq) {
            System.out.println(Formula);
        }

        while (Formula.contains("(")) {
            if (Formula.indexOf("(") > 1 && (Formula.charAt(Formula.indexOf("(") - 2) == '*' || Formula.charAt(Formula.indexOf("(") - 2) == '/')) {
                if (Formula.contains(") * (")) {
                    // String temp = Formula.su
                    // Formula = Formula.replace("(" + getPar(Formula) + ")",Foil(getPar(Formula),Formula.substring(Formula.indexOf("(") - 4,Formula.indexOf("("))));
                } else {
                    Formula = Formula.replace(Formula.substring(Formula.indexOf("(") - 4, Formula.indexOf("(")) + "(" + getPar(Formula) + ")", "(" + multiPar(getPar(Formula), Formula.substring(Formula.indexOf("(") - 4, Formula.indexOf("("))) + ")");
                }
            }
            String temp = SolveEq(false, getPar(Formula)).toString();
            if (temp.contains("=")) {
                Formula = Formula.replace("(" + getPar(Formula) + ")", temp.substring(temp.indexOf("= ") + 2));
            } else {
                Formula = Formula.replace("(" + getPar(Formula) + ")", temp);

            }
        }

        return Formula;
    }

    public static String SolveEq(Boolean ShowEq, String Formula) {
        Formula = toForm(ShowEq, Formula);
        ArrayList<String> arr = clean(Convert.stringToArray(Formula, " "));
        for (int i = 1; i < arr.size(); i += 2) {
            String tempstr = "";
            if (arr.get(i).equals("^")) {
                if (checkSym(arr.get(i - 1)) || checkSym(arr.get(i + 1))) {
                    if (checkSym(arr.get(i - 1))) {
                        String str = sepSym(arr.get(i - 1));
                        arr.set(i - 1, arr.get(i - 1).replace(str.substring(str.indexOf(" ") + 1), ""));
                        Double temp = Math.pow(Convert.toDouble(arr.get(i - 1)), Convert.toDouble(arr.get(i + 1)));
                        if (temp.isInfinite()) {
                            Double temps = Double.MAX_VALUE;
                            return "Calculations Exceed " + temps.toString().replaceAll("E", "*10^");
                        }
                        tempstr = temp.toString() + str.substring(str.indexOf(" ") + 1);

                    } else if (checkSym(arr.get(i + 1))) {
                        String str = sepSym(arr.get(i + 1));
                        arr.set(i + 1, arr.get(i + 1).replace(str.substring(str.indexOf(" ") + 1), ""));
                        Double temp = Math.pow(Convert.toDouble(arr.get(i - 1)), Convert.toDouble(arr.get(i + 1)));
                        if (temp.isInfinite()) {
                            Double temps = Double.MAX_VALUE;
                            return "Calculations Exceed " + temps.toString().replaceAll("E", "*10^");
                        }
                        tempstr = temp.toString() + str.substring(str.indexOf(" ") + 1 + 1);

                    }
                } else {
                    Double temp = Math.pow(Convert.toDouble(arr.get(i - 1)), Convert.toDouble(arr.get(i + 1)));
                    if (temp.isInfinite()) {
                        Double temps = Double.MAX_VALUE;
                        return "Calculations Exceed " + temps.toString().replaceAll("E", "*10^");
                    }
                    tempstr = temp.toString();

                }
                arr.set(i - 1, tempstr);
                arr.remove(i);
                arr.remove(i);
                i -= 2;
            }
        }
        for (int i = 1; i < arr.size(); i += 2) {
            if (arr.get(i).equals("*")) {
                String tempstr = "";
                if (checkSym(arr.get(i - 1)) && !checkSym(arr.get(i + 1))) {
                    String str = sepSym(arr.get(i - 1));
                    arr.set(i - 1, arr.get(i - 1).replace(str.substring(str.indexOf(" ") + 1), ""));
                    Double temp = Convert.toDouble(arr.get(i - 1)) * Convert.toDouble(arr.get(i + 1));
                    if (temp.isInfinite()) {
                        Double temps = Double.MAX_VALUE;
                        return "Calculations Exceed " + temps.toString().replaceAll("E", "*10^");
                    }
                    tempstr = temp.toString() + str.substring(str.indexOf(" ") + 1);

                } else if (!checkSym(arr.get(i - 1)) && checkSym(arr.get(i + 1))) {
                    String str = sepSym(arr.get(i + 1));
                    arr.set(i + 1, arr.get(i + 1).replace(str.substring(str.indexOf(" ") + 1), ""));
                    Double temp = Convert.toDouble(arr.get(i - 1)) * Convert.toDouble(arr.get(i + 1));
                    if (temp.isInfinite()) {
                        Double temps = Double.MAX_VALUE;
                        return "Calculations Exceed " + temps.toString().replaceAll("E", "*10^");
                    }
                    tempstr = temp.toString() + str.substring(str.indexOf(" ") + 1);

                } else if (checkSym(arr.get(i - 1)) && checkSym(arr.get(i + 1))) {
                    String str1 = sepSym(arr.get(i - 1));
                    String str2 = sepSym(arr.get(i + 1));
                    arr.set(i - 1, arr.get(i - 1).replace(str1.substring(str1.indexOf(" ") + 1), ""));
                    arr.set(i + 1, arr.get(i + 1).replace(str2.substring(str2.indexOf(" ") + 1), ""));
                    Double temp = Convert.toDouble(arr.get(i - 1)) * Convert.toDouble(arr.get(i + 1));
                    if (temp.isInfinite()) {
                        Double temps = Double.MAX_VALUE;
                        return "Calculations Exceed " + temps.toString().replaceAll("E", "*10^");
                    }
                    tempstr = temp.toString() + str1.substring(str1.indexOf(" ") + 1) + str2.substring(str2.indexOf(" ") + 1);

                } else {
                    Double temp = Convert.toDouble(arr.get(i - 1)) * Convert.toDouble(arr.get(i + 1));
                    if (temp.isInfinite()) {
                        Double temps = Double.MAX_VALUE;
                        return "Calculations Exceed " + temps.toString().replaceAll("E", "*10^");
                    }
                    tempstr = temp.toString();

                }
                arr.set(i - 1, tempstr);
                arr.remove(i);
                arr.remove(i);
                i -= 2;
            } else if (arr.get(i).equals("/")) {
                String tempstr = "";
                if (checkSym(arr.get(i - 1)) && !checkSym(arr.get(i + 1))) {
                    String str = sepSym(arr.get(i - 1));
                    arr.set(i - 1, arr.get(i - 1).replace(str.substring(str.indexOf(" ") + 1), ""));
                    Double temp = Convert.toDouble(arr.get(i - 1)) / Convert.toDouble(arr.get(i + 1));
                    if (temp.isInfinite()) {
                        Double temps = Double.MAX_VALUE;
                        return "Calculations Exceed " + temps.toString().replaceAll("E", "*10^");
                    }
                    tempstr = temp.toString() + str.substring(str.indexOf(" ") + 1);

                } else if (!checkSym(arr.get(i - 1)) && checkSym(arr.get(i + 1))) {
                    String str = sepSym(arr.get(i + 1));
                    arr.set(i + 1, arr.get(i + 1).replace(str.substring(str.indexOf(" ") + 1), ""));
                    Double temp = Convert.toDouble(arr.get(i - 1)) / Convert.toDouble(arr.get(i + 1));
                    if (temp.isInfinite()) {
                        Double temps = Double.MAX_VALUE;
                        return "Calculations Exceed " + temps.toString().replaceAll("E", "*10^");
                    }
                    tempstr = temp.toString() + str.substring(str.indexOf(" ") + 1);

                } else if (checkSym(arr.get(i - 1)) && checkSym(arr.get(i + 1))) {
                    String str1 = sepSym(arr.get(i - 1));
                    String str2 = sepSym(arr.get(i + 1));
                    arr.set(i - 1, arr.get(i - 1).replace(str1.substring(str1.indexOf(" ") + 1), ""));
                    arr.set(i + 1, arr.get(i + 1).replace(str2.substring(str2.indexOf(" ") + 1), ""));
                    Double temp = Convert.toDouble(arr.get(i - 1)) / Convert.toDouble(arr.get(i + 1));
                    if (temp.isInfinite()) {
                        Double temps = Double.MAX_VALUE;
                        return "Calculations Exceed " + temps.toString().replaceAll("E", "*10^");
                    }
                    tempstr = temp.toString() + str1.substring(str1.indexOf(" ") + 1) + "/" + str2.substring(str2.indexOf(" ") + 1);

                } else {
                    Double temp = Convert.toDouble(arr.get(i - 1)) / Convert.toDouble(arr.get(i + 1));
                    if (temp.isInfinite()) {
                        Double temps = Double.MAX_VALUE;
                        return "Calculations Exceed " + temps.toString().replaceAll("E", "*10^");
                    }
                    tempstr = temp.toString();

                }
                arr.set(i - 1, tempstr);
                arr.remove(i);
                arr.remove(i);
                i -= 2;
            }
        }

        //System.out.println(Convert.arrayToString(arr, " "));

        if (checkSym(Formula)) {
            if (Formula.contains("=")) {
                arr = xLeft(arr);
            } else {
                arr = xLessLeft(arr);
            }
        }


        arr = fillPlus(arr);
        if (ShowEq) {
            System.out.println(Convert.arrayToString(arr, " "));
        }
        for (int i = 1; i < arr.size(); i += 2) {



            if (arr.get(i).equals("+")) {
                String tempstr = "";
                if (checkSym(arr.get(i - 1)) && checkSym(arr.get(i + 1))) {
                    String str = sepSym(arr.get(i - 1));
                    arr.set(i - 1, arr.get(i - 1).replace(str.substring(str.indexOf(" ") + 1), ""));
                    str = sepSym(arr.get(i + 1));
                    arr.set(i + 1, arr.get(i + 1).replace(str.substring(str.indexOf(" ") + 1), ""));
                    Double temp = Convert.toDouble(arr.get(i - 1)) + Convert.toDouble(arr.get(i + 1));
                    if (temp.isInfinite()) {
                        Double temps = Double.MAX_VALUE;
                        return "Calculations Exceed " + temps.toString().replaceAll("E", "*10^");
                    }
                    tempstr = temp.toString() + str.substring(str.indexOf(" ") + 1);

                    arr.set(i - 1, tempstr);
                    arr.remove(i);
                    arr.remove(i);
                    i -= 2;
                } else if (!checkSym(arr.get(i - 1)) && !checkSym(arr.get(i + 1))) {
                    Double temp = Convert.toDouble(arr.get(i - 1)) + Convert.toDouble(arr.get(i + 1));
                    if (temp.isInfinite()) {
                        Double temps = Double.MAX_VALUE;
                        return "Calculations Exceed " + temps.toString().replaceAll("E", "*10^");
                    }
                    tempstr = temp.toString();

                    arr.set(i - 1, tempstr);
                    arr.remove(i);
                    arr.remove(i);
                    i -= 2;
                }


            } else if (arr.get(i).equals("-")) {
                String tempstr = "";
                if (checkSym(arr.get(i - 1)) && checkSym(arr.get(i + 1))) {
                    String str = sepSym(arr.get(i - 1));
                    arr.set(i - 1, arr.get(i - 1).replace(str.substring(str.indexOf(" ") + 1), ""));
                    str = sepSym(arr.get(i + 1));
                    arr.set(i + 1, arr.get(i + 1).replace(str.substring(str.indexOf(" ") + 1), ""));
                    Double temp = Convert.toDouble(arr.get(i - 1)) - Convert.toDouble(arr.get(i + 1));
                    if (temp.isInfinite()) {
                        Double temps = Double.MAX_VALUE;
                        return "Calculations Exceed " + temps.toString().replaceAll("E", "*10^");
                    }
                    tempstr = temp.toString() + str.substring(str.indexOf(" ") + 1);

                    arr.set(i - 1, tempstr);
                    arr.remove(i);
                    arr.remove(i);
                    i -= 2;
                } else if (!checkSym(arr.get(i - 1)) && !checkSym(arr.get(i + 1))) {
                    Double temp = Convert.toDouble(arr.get(i - 1)) - Convert.toDouble(arr.get(i + 1));
                    if (temp.isInfinite()) {
                        Double temps = Double.MAX_VALUE;
                        return "Calculations Exceed " + temps.toString().replaceAll("E", "*10^");
                    }
                    tempstr = temp.toString();

                    arr.set(i - 1, tempstr);
                    arr.remove(i);
                    arr.remove(i);
                    i -= 2;
                }

            }
        }
        arr = simplify(arr);
        arr = antiRedundant(arr);
        if (ShowEq) {
            System.out.println(Convert.arrayToString(arr, " ").substring(1));
        }

        return Convert.arrayToString(arr, " ").substring(1).replaceAll("\\+ -", "- ");
    }

    public static ArrayList<String> xLeft(ArrayList<String> arr) {
        boolean go = true;
        ArrayList<String> form = new ArrayList<String>();
        form.add("=");
        for (int i = 0; i < arr.size(); i++) {
            if (go) {
                if (i % 2 == 0) {
                    String tempstr = "";
                    if (!checkSym(arr.get(i))) {
                        form.add("0");
                        if (i == 0 || arr.get(i - 1).equals("=") || arr.get(i - 1).equals("+")) {
                            form.add("-");
                        } else {
                            form.add(0, "+");
                        }
                        form.add(arr.get(i));
                        arr.remove(i);
                        if (i == 0) {

                            if (arr.get(0).equals("+")) {
                                arr.remove(i);
                            } else {
                                arr.remove(i);
                                arr.set(i, "-" + arr.get(i));

                            }
                        }
                        i--;
                    } else {
                        if (i != 0) {
                            form.add(0, arr.get(i - 1));
                        }
                        form.add(0, arr.get(i));

                    }
                } else {
                    if (go && arr.get(i).contains("=")) {
                        go = false;
                    }
                }
            } else {
                if (i % 2 == 0) {
                    String tempstr = "";
                    if (checkSym(arr.get(i))) {
                        form.add(0, arr.get(i));
                        if (i == 0 || arr.get(i - 1).equals("=") || arr.get(i - 1).equals("+")) {
                            form.add(0, "-");
                        } else {
                            form.add(0, "+");
                        }
                        form.add(0, "0x");
                        arr.remove(i);
                        i--;
                    } else {
                        if (!arr.get(i - 1).equals("=")) {
                            form.add(arr.get(i - 1));
                        }
                        form.add(arr.get(i));

                    }
                }
            }
        }
        return form;
    }

    public static ArrayList<String> xLessLeft(ArrayList<String> arr) {
        boolean go = true;
        ArrayList<String> form = new ArrayList<String>();
        for (int i = 0; i < arr.size(); i++) {
            if (go) {
                if (i % 2 == 0) {
                    String tempstr = "";
                    if (!checkSym(arr.get(i))) {
                        form.add("0");
                        if (i == 0 || arr.get(i - 1).equals("=") || arr.get(i - 1).equals("+")) {
                            form.add("+");
                        } else {
                            form.add("-");
                        }
                        form.add(arr.get(i));
                        arr.remove(i);
                        if (arr.size() > 1) {
                            if (i == 0) {
                                if (arr.get(0).equals("+")) {
                                    arr.remove(i);
                                } else {
                                    arr.remove(i);
                                    arr.set(i, "-" + arr.get(i));
                                }
                            }
                            i--;
                        }
                    } else {
                        if (i != 0) {
                            form.add(0, arr.get(i - 1));
                        }
                        form.add(0, arr.get(i));

                    }
                } else {
                    if (go && arr.get(i).contains("=")) {
                        go = false;
                    }
                }
            } else {
                if (i % 2 == 0) {
                    String tempstr = "";
                    if (checkSym(arr.get(i))) {
                        form.add(0, arr.get(i));
                        if (i == 0 || arr.get(i - 1).equals("=") || arr.get(i - 1).equals("+")) {
                            form.add(0, "+");
                        } else {
                            form.add(0, "-");
                        }
                        form.add(0, "0x");
                        arr.remove(i);
                        i--;
                    } else {
                        if (!arr.get(i - 1).equals("=")) {
                            form.add(arr.get(i - 1));
                        }
                        form.add(arr.get(i));

                    }
                }
            }
        }
        return form;
    }

    public static ArrayList<String> clean(ArrayList<String> arr1) {
        for (int i = 0; i < arr1.size(); i++) {
            arr1.set(i, arr1.get(i).replaceAll(" ", ""));
            if ((arr1.get(i).isEmpty())) {
                arr1.remove(i);
                i--;
            }
        }
        return arr1;
    }

    public static String getPar(String eq) {
        eq = eq.substring(eq.indexOf("(") + 1);
        int counter = 0;
        int is = 0;
        while (eq.charAt(is) != ')') {
            if (eq.charAt(is) == '(') {
                counter++;
            }
            is++;
        }
        String temp = eq.substring(0, eq.indexOf(")"));
        for (int i = counter; i > 0; i--) {
            String temp2 = eq.substring(eq.indexOf(")"));
            temp += temp2.substring(0, temp2.indexOf(")") + 1);
        }
        return temp;
    }

    public static String Foil(String Par1, String Par2) {
        ArrayList<String> arr = clean(Convert.stringToArray(Par1, " "));
        String Num = Par2.substring(0, Par2.indexOf(" "));
        String numx = "";
        Double temp = 0.0;
        if (checkSym(Num)) {
            String tempstr = sepSym(Num);
            Num = Num.replace(tempstr.substring(tempstr.indexOf(" ")), "");
            numx = tempstr.substring(0, tempstr.indexOf(" "));

        }
        if (Par2.contains("*")) {
            String tempstr;
            for (int i = 0; i < arr.size(); i += 2) {
                if (arr.get(i).contains("(")) {
                    arr.set(i, Par2 + arr.get(i));
                    while (!arr.get(i).contains(")")) {
                        i++;
                    }
                } else if (checkSym(arr.get(i)) && numx.equals("") || !checkSym(arr.get(i)) && !numx.equals("")) {
                    if (checkSym(arr.get(i))) {
                        String str = sepSym(arr.get(i));
                        arr.set(i, arr.get(i).replace(str.substring(str.indexOf(" ") + 1), ""));
                        Double temps = Convert.toDouble(arr.get(i)) * Convert.toDouble(Num);
                        tempstr = temps.toString() + str.substring(str.indexOf(" ") + 1);
                        arr.set(i, tempstr);
                    } else {
                        temp = Convert.toDouble(arr.get(i)) * Convert.toDouble(Num);
                        tempstr = temp.toString() + numx;
                        arr.set(i, tempstr);
                    }
                } else if (!checkSym(arr.get(i)) && numx.equals("")) {
                    temp = Convert.toDouble(arr.get(i)) * Convert.toDouble(Num);
                    tempstr = temp.toString();
                    arr.set(i, tempstr);
                }
            }
        } else {
            String tempstr;
            for (int i = 0; i < arr.size(); i += 2) {
                if (checkSym(arr.get(i)) && !numx.equals("") || !checkSym(arr.get(i)) && numx.equals("")) {
                    String str = sepSym(arr.get(i - 1));
                    arr.set(i, arr.get(i).replace(str.substring(str.indexOf(" ") + 1), ""));
                    temp = Convert.toDouble(arr.get(i)) / Convert.toDouble(Num);
                    tempstr = temp.toString() + numx;
                    arr.set(i, tempstr);
                } else if (!checkSym(arr.get(i)) && !numx.equals("")) {
                    temp = Convert.toDouble(arr.get(i)) / Convert.toDouble(Num);
                    tempstr = temp.toString();
                    arr.set(i, tempstr);
                }
            }
        }
        return Convert.arrayToString(arr, " ");
    }

    public static String multiPar(String Par, String multis) {
        ArrayList<String> arr = clean(Convert.stringToArray(Par, " "));
        String Num = multis.substring(0, multis.indexOf(" "));
        String numx = "";
        Double temp = 0.0;
        if (checkSym(Num)) {
            String tempstr = sepSym(Num);
            Num = Num.replace(tempstr.substring(tempstr.indexOf(" ")), "");
            numx = tempstr.substring(0, tempstr.indexOf(" "));

        }
        if (multis.contains("*")) {
            String tempstr;
            for (int i = 0; i < arr.size(); i += 2) {
                if (arr.get(i).contains("(")) {
                    arr.set(i, multis + arr.get(i));
                    while (!arr.get(i).contains(")")) {
                        i++;
                    }
                } else if (checkSym(arr.get(i)) && numx.equals("") || !checkSym(arr.get(i)) && !numx.equals("")) {
                    if (checkSym(arr.get(i))) {
                        String str = sepSym(arr.get(i));
                        arr.set(i, arr.get(i).replace(str.substring(str.indexOf(" ") + 1), ""));
                        Double temps = Convert.toDouble(arr.get(i)) * Convert.toDouble(Num);
                        tempstr = temps.toString() + str.substring(str.indexOf(" ") + 1);
                        arr.set(i, tempstr);
                    } else {
                        temp = Convert.toDouble(arr.get(i)) * Convert.toDouble(Num);
                        tempstr = temp.toString() + numx;
                        arr.set(i, tempstr);
                    }
                } else if (!checkSym(arr.get(i)) && numx.equals("")) {
                    temp = Convert.toDouble(arr.get(i)) * Convert.toDouble(Num);
                    tempstr = temp.toString();
                    arr.set(i, tempstr);
                }
            }
        } else {
            String tempstr;
            for (int i = 0; i < arr.size(); i += 2) {
                if (checkSym(arr.get(i)) && !numx.equals("") || !checkSym(arr.get(i)) && numx.equals("")) {
                    String str = sepSym(arr.get(i - 1));
                    arr.set(i, arr.get(i).replace(str.substring(str.indexOf(" ") + 1), ""));
                    temp = Convert.toDouble(arr.get(i)) / Convert.toDouble(Num);
                    tempstr = temp.toString() + numx;
                    arr.set(i, tempstr);
                } else if (!checkSym(arr.get(i)) && !numx.equals("")) {
                    temp = Convert.toDouble(arr.get(i)) / Convert.toDouble(Num);
                    tempstr = temp.toString();
                    arr.set(i, tempstr);
                }
            }
        }
        return Convert.arrayToString(arr, " ");
    }

    public static ArrayList<String> fillPlus(ArrayList<String> arr) {
        for (int i = 1; i < arr.size(); i += 2) {
            if (!arr.get(i).contains("+") && !arr.get(i).contains("-") && !arr.get(i).contains("=")) {
                arr.add(i, "+");
            }
        }

        return arr;
    }

    public static ArrayList<String> antiRedundant(ArrayList<String> arr) {
        for (int i = 0; i < arr.size(); i++) {
            String temp = arr.get(i);
            if (temp.contains("/") && temp.length() > 3) {
                if (temp.charAt(temp.indexOf("/") - 1) == temp.charAt(temp.indexOf("/") + 1)) {
                    String tempstr = temp.substring(temp.indexOf("/") - 1, temp.indexOf("/") + 1);
                    arr.set(i, temp.replace(tempstr, ""));
                }
            }
            String tempstr = "";
            if (arr.get(i).contains("E")) {
                arr.set(i, arr.get(i).replaceAll("E", "*10^"));
            }
            if (checkSym(temp)) {
                for (int j = 0; j < temp.length(); j++) {
                    tempstr = tempstr + temp.charAt(j) + " ";
                }
                ArrayList<String> arr2 = Convert.stringToArray(tempstr, " ");
                for (int j = 0; j < arr2.size(); j++) {
                    if (arr2.get(j).equals(arr2.get(j + 1))) {
                        arr2.set(j, arr2.get(j) + "^2");
                        arr2.remove(j + 1);
                        j++;
                    }
                }
                for (int j = 0; j < arr2.size(); j++) {

                    if (arr2.get(j).length() > 0 && !isSym(arr2.get(j).charAt(0)) && !isNum(arr2.get(j).charAt(0))) {
                        if (j > 0 && arr2.get(j - 1).equals("1")) {
                            arr2.remove(j - 1);
                            i--;
                        } else if (j > 2 && arr2.get(j - 3).equals("1") && arr2.get(j - 2).equals(".") && arr2.get(j - 1).equals("0")) {
                            arr2.remove(j - 1);
                            arr2.remove(j - 1);
                            arr2.remove(j - 1);
                            i -= 3;
                        }
                    }
                }

                arr.set(i, Convert.arrayToString(arr2, ""));
            }
        }

        return arr;
    }

    public static ArrayList<String> simplify(ArrayList<String> arr) {
        if (arr.size() == 3 && arr.get(1).equals("=")) {
            if (arr.get(0).equals("=")) {
                arr.add(0, "0x");
            } else if (arr.size() == 2) {
                arr.add("0");
            }
            String str = sepSym(arr.get(0));
            Double temp = Convert.toDouble(arr.get(2)) / Convert.toDouble(arr.get(0).replaceAll(str.substring(str.indexOf(" ") + 1), ""));
            if (temp.isInfinite()) {
                arr.clear();
                Double temps = Double.MAX_VALUE;
                arr.add("Calculations Exceed " + temps.toString().replaceAll("E", "*10^"));
            }
            arr.set(2, temp.toString());

            arr.set(0, str.substring(str.indexOf(" ")));

            if (arr.get(2).contains("-0")) {
                arr.set(2, "0");
            }
        }
        return arr;
    }

    public static String sepSym(String symcom) {
        String temp = " ";
        for (int i = 0; i < symcom.length(); i++) {
            if (!isSym(symcom.charAt(i)) && !isNum(symcom.charAt(i)) && temp.equals(" ")) {
                temp = symcom.substring(0, i) + " " + symcom.substring(i);
            }
        }
        if (temp.substring(0, temp.indexOf(" ")).equals("")) {
            temp = "1" + temp;
        }
        return temp;
    }

    public static boolean checkSym(String symcom) {
        for (int i = 0; i < symcom.length(); i++) {
            if (!isNum(symcom.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNum(Character chr) {
        return (chr.equals('.') || chr.equals('0') || chr.equals('1') || chr.equals('2') || chr.equals('-') || chr.equals('3') || chr.equals('4') || chr.equals('5') || chr.equals('E') || chr.equals('6') || chr.equals('7') || chr.equals('8') || chr.equals('9'));
    }

    public static boolean isSym(Character chr) {
        return (chr.equals(')') || chr.equals('^') || chr.equals('*') || chr.equals('/') || chr.equals('+') || chr.equals('-') || chr.equals(' '));
    }

    public static String modPower(String Form) {
        ArrayList<String> arr = clean(Convert.stringToArray(Form, " "));
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).equals("^")) {
                if (!isNum(arr.get(i - 1).charAt(arr.get(i - 1).length() - 1)) && !isSym(arr.get(i - 1).charAt(arr.get(i - 1).length() - 1))) {
                    arr.set(i - 1, arr.get(i - 1) + "^" + arr.get(i + 1));
                }
            }
        }
        return Convert.arrayToString(arr, " ");
    }

    public static String addOne(String Eq) {
        String tempstr = Eq;
        for (int i = 0; i < Eq.length(); i++) {
//            System.out.println(Eq.charAt(i));
//            System.out.println(i <= 2 && !isNum(Eq.charAt(i)) && !isSym(Eq.charAt(i)));
//            try {
//                System.out.println(!isNum(Eq.charAt(i)) && !isSym(Eq.charAt(i)) && (Eq.charAt(i - 1) != ' ' || Eq.charAt(i - 1) != '(') && Eq.charAt(i- 2) != '*');
//            } catch (Exception e) {
//                //System.err.println(e);
//            }
//            System.out.println("************");
            if (i <= 2) {
                if (i == 0 && !isNum(Eq.charAt(i)) && !isSym(Eq.charAt(i))) {
                    tempstr = tempstr.substring(0, i) + "1" + Eq.substring(i);
                } else if ((i == 1 || i == 2) && !isNum(Eq.charAt(i)) && !isSym(Eq.charAt(i)) && (!isNum(Eq.charAt(i - 1)) && Eq.charAt(i - 1) != '(')) {
                    tempstr = tempstr.substring(0, i) + "1" + Eq.substring(i);
                }
            } else if (!isNum(Eq.charAt(i)) && !isSym(Eq.charAt(i)) && (!isNum(Eq.charAt(i - 1)) && Eq.charAt(i - 1) != '(')) {
                if (Eq.charAt(i - 2) == '*' && !isNum(Eq.charAt(i - 4)) || (Eq.charAt(i - 2) != '*' && isNum(Eq.charAt(i - 4)))) {
                    tempstr = tempstr.substring(0, i + 1) + "1" + Eq.substring(i);
                } else if (Eq.charAt(i - 2) == '/' && !isNum(Eq.charAt(i - 4)) || (Eq.charAt(i - 2) != '/' && isNum(Eq.charAt(i - 4)))) {
                    tempstr = tempstr.substring(0, i + 1) + "1" + Eq.substring(i);
                }
            }
        }
        return tempstr;
    }
}
