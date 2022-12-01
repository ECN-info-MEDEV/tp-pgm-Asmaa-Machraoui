/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package org.centrale.projet.objet.tppgm;

import java.io.IOException;
import java.util.Arrays;


/**
 *
 * @author Asmaa
 */

public class TpPgm {

    public static void main(String[] argv)
    {
        PGMImage img = new PGMImage("brain.pgm");
        System.out.println("brain.pgm : " +img.getHeight()+"x"+img.getWidth());
        try{
            img.write("test.pgm");
        }
        catch(Exception e){
            System.out.println("Could not write the file");
        }

        System.out.println("----------------------------");
        // reading the testing sample :
        PGMImage instance = new PGMImage("coins.pgm");
        System.out.println("Size of the sample : "+instance.getHeight()+"x"+instance.getWidth());
        System.out.println("Number of pixels : "+instance.getImg().length);
        System.out.println(Arrays.toString(instance.getImg()));
        
        // test of write :
        String fileName = "test2.pgm";
        try {
            instance.write(fileName);
        }
        catch (IOException e) {
            System.err.println("An error has occured will writing the file.");
            e.printStackTrace(System.err) ;
        }
    }
}

