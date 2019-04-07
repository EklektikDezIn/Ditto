/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dittoupdate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Elizabeth
 */
public class DittoUpdate {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        moveFile(System.getProperty("user.dir").substring(0,System.getProperty("user.dir").lastIndexOf(System.getProperty("file.separator"))+1)+"Imports"+System.getProperty("file.separator")+"DittoUpdate.zip", System.getProperty("user.dir").substring(0,System.getProperty("user.dir").lastIndexOf(System.getProperty("file.separator"))+1));
        unZipIt(System.getProperty("user.dir").substring(0,System.getProperty("user.dir").lastIndexOf(System.getProperty("file.separator"))+1)+"DittoUpdate.zip");
        run(System.getProperty("user.dir").substring(0,System.getProperty("user.dir").lastIndexOf(System.getProperty("file.separator"))+1).substring(0, System.getProperty("user.dir").lastIndexOf(System.getProperty("file.separator")) + 1) + "Ditto" + System.getProperty("file.separator") + "Ditto.jar");
        deleteFile(System.getProperty("user.dir").substring(0,System.getProperty("user.dir").lastIndexOf(System.getProperty("file.separator"))+1)+"DittoUpdate.zip");
       
    }
    List<String> fileList;

    public static void unZipIt(String zipFile) {
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

                System.out.println("file unzip : " + newFile.getAbsoluteFile());

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
    }

    public static String moveFile(String fileloc1, String fileloc2) {
        String temp = "";

        try {

            File afile = new File(fileloc1);

            if (afile.renameTo(new File(fileloc2 + afile.getName()))) {
                temp = (fileloc2 + afile.getName());
            } else {
                System.err.println("Error");
            }
            afile.deleteOnExit();

        } catch (Exception e) {
        }
        return temp;
    }


    public static int runGhost(String fileloc) {
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
    public static int run(String fileloc){
        String file = "";
        if (getOS().equals("Windows")) {
            file = System.getProperty("user.dir").substring(0,System.getProperty("user.dir").lastIndexOf(System.getProperty("file.separator"))+1)+"Pacman"+System.getProperty("file.separator")+"4_Clyde.bat";
            writeToFile(file, "@echo off\n" + fileloc + "\nexit");
        } else if (getOS().equals("Mac")) {
            file = System.getProperty("user.dir").substring(0,System.getProperty("user.dir").lastIndexOf(System.getProperty("file.separator"))+1)+"Pacman"+System.getProperty("file.separator")+"2_Blinky.sh";
            writeToFile(file, "#!/bin/bash" + "\nopen \""+fileloc + "\"");
        }
        return runGhost(file);
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

    public static void writeToFile(String fileloc, String inpt) {
        try {
            FileUtils.writeStringToFile(new File(fileloc), inpt);
        } catch (IOException e) {
        }
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
}
