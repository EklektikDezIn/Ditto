/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ditto;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Elizabeth
 */
public class FolderC {

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

    public static QuantumArray getFiles(final java.io.File folder, int depth, int maxDepth) {
        depth = 1;
        QuantumArray temparr = new QuantumArray(folder.getAbsolutePath() + System.getProperty("file.separator"), depth - 1);
        //System.out.println(folder.listFiles());
        try {
            for (final java.io.File fileEntry : folder.listFiles()) {
                if (fileEntry.isFile() && folder.exists()) {
                    try {
                        temparr.list.add(new QuantumArray(fileEntry.getName(), depth));
                    } catch (NullPointerException e) {
                    }
                }
            }
            for (final java.io.File fileEntry : folder.listFiles()) {
                if (fileEntry.isDirectory() && depth != maxDepth) {
                    temparr.list.add(getFiles(fileEntry, depth + 1, maxDepth));
                } else if (fileEntry.isDirectory()) {
                    temparr.list.add(new QuantumArray(fileEntry + System.getProperty("file.separator"), depth));
                }
            }
        } catch (NullPointerException e) {
            temparr.list.add(new QuantumArray("No such file exists.", depth));
            //throw new RuntimeException(e);
        }
        return temparr;
    }

    public static QuantumArray getFiles(final java.io.File folder, int depth, int maxDepth, String type) {
        QuantumArray temparr = new QuantumArray(folder.getAbsolutePath() + System.getProperty("file.separator"), depth - 1);
        String temp;
        type = type.toLowerCase();
        //System.out.println(folder.listFiles());
        try {
            for (final java.io.File fileEntry : folder.listFiles()) {
                temp = fileEntry.getName();
                if (fileEntry.isFile() && folder.exists()) {
                    if ((temp.substring(temp.lastIndexOf('.') + 1, temp.length()).toLowerCase()).equals(type)) {
                        if (!temparr.object.substring(0, 1).contentEquals("!")) {
                            temparr.object = "!" + temparr.object;
                        }
                        try {
                            temparr.list.add(new QuantumArray(fileEntry.getName(), depth));
                        } catch (NullPointerException e) {
                        }
                    }
                }
            }
            for (final java.io.File fileEntry : folder.listFiles()) {
                if (fileEntry.isDirectory() && depth != maxDepth) {
                    temparr.list.add(getFiles(fileEntry, depth + 1, maxDepth, type));
                } else if (fileEntry.isDirectory()) {
                    temparr.list.add(new QuantumArray(fileEntry + System.getProperty("file.separator"), depth));
                }
            }
        } catch (NullPointerException e) {
            temparr.list.add(new QuantumArray("No such file exists.", depth));
            //throw new RuntimeException(e);
        }
        for (int i = 0; i < temparr.list.size(); i++) {
            if (temparr.list.get(i).object.length() > 0) {
                if (temparr.list.get(i).object.contains(type) || temparr.list.get(i).object.substring(0, 1).contentEquals("!")) {
                    return temparr;
                }
            }
        }

        return new QuantumArray("", -1);
    }

    public static ArrayList<String> deleteFile(String fileloc) {
        ArrayList temps = new ArrayList<String>();
        File file = new File(fileloc);

        if (file.isDirectory()) {

            //if directory is empty, delete it
            if (file.list().length == 0) {
                file.delete();
                temps.add("Directory " + file.getAbsolutePath() + " is deleted.");

            } else {
                //list directory contents

                String files[] = file.list();

                for (String temp : files) {
                    File fileDelete = new File(file, temp);

                    // delete files
                    deleteFile(fileDelete.getAbsolutePath());
                }

                //check  directory , if empty ==> delete 
                if (file.list().length == 0) {
                    file.delete();
                    temps.add("Directory " + file.getAbsolutePath() + " is deleted.");
                }
            }
        } else {
            //if file ==> delete 
            file.delete();
            temps.add("File " + file.getAbsolutePath() + " is deleted.");
        }
        return temps;
    }

    public static String moveFile(String fileloc1, String fileloc2) {
        String temp = "";

        try {

            File afile = new File(fileloc1);

            if (afile.renameTo(new File(fileloc2 + afile.getName()))) {
                temp = (fileloc2 + afile.getName());
            } else {
                return "Error";
            }
            afile.deleteOnExit();

        } catch (Exception e) {
        }
        return temp;
    }

    public static void writeToFile(String fileloc, String inpt, boolean append) {
        try {
            FileUtils.writeStringToFile(new File(fileloc), inpt, append);
        } catch (IOException e) {
        }
    }

    public static String createFile(String fileloc) {
        boolean temp = false;
        try {
            File file = new File(fileloc);
            if (file.createNewFile()) {
                return "File creation successful";
            } else {
                return "Error during file creation";
            }
        } catch (IOException e) {
        }
        return "Error during file creation";
    }

    public static void writeToClipboard(String writeMe) {
        // get the system clipboard
        Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        // set the textual content on the clipboard to our 
        // Transferable object
        // we use the
        Transferable transferableText = new StringSelection(writeMe);
        systemClipboard.setContents(transferableText, null);
    }

    public static String pasteFromClipboard() {
        String temp = "";
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable clipData = clipboard.getContents(null);
        if (clipData != null) {
            try {
                if (clipData.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    temp = (String) (clipData.getTransferData(DataFlavor.stringFlavor));
                }
            } catch (UnsupportedFlavorException ufe) {
                temp = "Flavor unsupported: " + ufe;
            } catch (IOException ioe) {
                temp = "Data not available: " + ioe;
            }
        }
        return temp;
    }

    public static ArrayList<String> unZipIt(String zipFile) {

        ArrayList<String> temp = new ArrayList<String>();
        String outputFolder = zipFile.substring(0, zipFile.lastIndexOf("."));
        byte[] buffer = new byte[1024];

        try {

            //create output directory is not exists
            File folder = new File(outputFolder);
            if (!folder.exists()) {
                folder.mkdir();
            }

            //get the zip file content
            ZipInputStream zis =
                    new ZipInputStream(new FileInputStream(zipFile));
            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();

            while (ze != null) {

                String fileName = ze.getName();
                File newFile = new File(outputFolder + File.separator + fileName);

                temp.add("File unzip: " + newFile.getAbsoluteFile());

                //create all non exists folders
                //else you will hit FileNotFoundException for compressed folder
                new File(newFile.getParent()).mkdirs();

                FileOutputStream fos = new FileOutputStream(newFile);

                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                fos.close();
                ze = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();

            System.out.println("Done");

        } catch (IOException ex) {
        }
        return temp;
    }

    public static void Zip(String fileloc) {
        byte[] buffer = new byte[1024];

        try {

            FileOutputStream fos = new FileOutputStream(fileloc + ".zip");
            ZipOutputStream zos = new ZipOutputStream(fos);
            ZipEntry ze = new ZipEntry(fileloc.substring(fileloc.lastIndexOf(System.getProperty("file.separator"))));
            zos.putNextEntry(ze);
            FileInputStream in = new FileInputStream(fileloc);

            int len;
            while ((len = in.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }

            in.close();
            zos.closeEntry();

            //remember close it
            zos.close();

            System.out.println("Done");

        } catch (IOException ex) {
        }
    }

    public static String getOS() {
        String OS = System.getProperty("os.name").toLowerCase();
        if (OS.contains("win")) {
            return ("Windows");
        } else if (OS.contains("mac")) {
            return ("Mac");
        } else if (OS.contains("nix") || OS.contains("nux") || OS.contains("aix")) {
            return ("Unix/Linux");
        } else {
            return ("Not supported");
        }
    }

    public static int runGhost(String[] fileloc) {
        Runtime run = Runtime.getRuntime();
        try {

            Process pp = run.exec(fileloc);
            BufferedReader in = new BufferedReader(new InputStreamReader(pp.getErrorStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            int exitVal = pp.waitFor();
            return exitVal;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public static int run(String fileloc) {
        String file = "";
        if (getOS().equals("Windows")) {
            file = System.getProperty("user.dir").substring(0, System.getProperty("user.dir").lastIndexOf(System.getProperty("file.separator")) + 1) + "Emailing" + System.getProperty("file.separator") + "Pacman" + System.getProperty("file.separator") + "4_Clyde.bat";
            writeToFile(file, "@echo off\n" + fileloc + "\nexit", false);
        } else if (getOS().equals("Mac")) {
            file = System.getProperty("user.dir").substring(0, System.getProperty("user.dir").lastIndexOf(System.getProperty("file.separator")) + 1) + "Emailing" + System.getProperty("file.separator") + "Pacman" + System.getProperty("file.separator") + "2_Blinky.sh";
            writeToFile(file, "#!/bin/bash" + "\nopen \"" + fileloc + "\"", false);
        }
        String[] temp = new String[1];
        temp[0] = file;
        return runGhost(temp);
    }
    public static String userDrive(String folder){
        String temp1, temp2, temp3;
        temp1 = folder.substring(0, folder.indexOf("\\")+1);
        folder = folder.substring(folder.indexOf("\\")+1);
        temp2 = folder.substring(0, folder.indexOf("\\")+1);
        folder = folder.substring(folder.indexOf("\\")+1);
        temp3 = folder.substring(0, folder.indexOf("\\")+1);
        return temp1+temp2+temp3;
    }
    public static int fileTree() {
        String file = "";
        String file2 = System.getProperty("user.dir").substring(0, System.getProperty("user.dir").lastIndexOf(System.getProperty("file.separator")) + 1) + "Emailing" + System.getProperty("file.separator") + "SystemFiles" + System.getProperty("file.separator") + "FileTree.txt";
        if (getOS().equals("Windows")) {
            file = System.getProperty("user.dir").substring(0, System.getProperty("user.dir").lastIndexOf(System.getProperty("file.separator")) + 1) + "Emailing" + System.getProperty("file.separator") + "Pacman" + System.getProperty("file.separator") + "4_Clyde.bat";
            writeToFile(file, "@echo off" + System.getProperty("line.separator") + "dir /a /s /b " + userDrive(file2) + " > " + file2 + System.getProperty("line.separator") + "\nexit", false);
        } else if (getOS().equals("Mac")) {
            file = System.getProperty("user.dir").substring(0, System.getProperty("user.dir").lastIndexOf(System.getProperty("file.separator")) + 1) + "Emailing" + System.getProperty("file.separator") + "Pacman" + System.getProperty("file.separator") + "2_Blinky.sh";
            writeToFile(file, file.substring(0, file.indexOf("/")) + ">" + file2 + "-R", false);
        }
        String[] temp = new String[1];
        temp[0] = file;
        return runGhost(temp);

    }

    public static void FileTree(String TFile, final java.io.File folder, int depth) {
        String spaces = "\n";
        for (int i = 0; i < depth; i++) {
            spaces = spaces + "     ";
        }
        System.out.print(spaces + folder.getAbsolutePath() + System.getProperty("file.separator"));
        writeToFile(TFile, spaces + folder.getAbsolutePath() + System.getProperty("file.separator"), true);
        spaces = spaces + "     ";
        try {
            for (final java.io.File fileEntry : folder.listFiles()) {
                if (fileEntry.isFile() && folder.exists()) {
                    try {
                        System.out.print(spaces + fileEntry.getName());
                        writeToFile(TFile, spaces + fileEntry.getName(), true);
                    } catch (NullPointerException e) {
                    }
                } else if (fileEntry.isDirectory()) {
                    FileTree(TFile, fileEntry, depth + 1);
                }
            }
        } catch (NullPointerException e) {
            System.out.print(spaces + "No such file exists.");
            FolderC.writeToFile(TFile, Convert.arrayToString(FolderC.readFile(TFile)) + spaces + "No such file exists.", true);
            //throw new RuntimeException(e);
        }
    }
}
//////import java.util.*;
//////
//////public class changer
//////{
//////    public static native int SystemParametersInfo(int uiAction,int uiParam,String pvParam,int fWinIni);
//////
//////    static
//////    {
//////        System.loadLibrary("user32");
//////    }
//////
//////    public int Change(String path)
//////    {
//////       return SystemParametersInfo(20, 0, path, 0);
//////    }
//////
//////    public static void main(String args[])
//////    {
//////        String wallpaper_file = "c:\\wallpaper.jpg";
//////        changer mychanger = new changer();
//////        mychanger.Change(wallpaper_file);
//////    }
//////
//////}
////////    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
////////        public void run() {
////////            System.out.println("In shutdown hook");
////////        }
////////    }, "Shutdown-thread"));
