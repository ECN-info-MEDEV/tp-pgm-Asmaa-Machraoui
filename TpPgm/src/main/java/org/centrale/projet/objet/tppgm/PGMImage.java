/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.centrale.projet.objet.tppgm;
import java.awt.Color;
import java.awt.Graphics;
import java.io.*;
import java.util.Scanner;
/**
 *
 * @author asmaa
 */
public class PGMImage {
    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    
    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    
    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    
    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    
    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    
    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    
    /**
     * @return the img
     */
    public int[] getImg() {
        return img;
    }

    
    /**
     * @param img the img to set
     */
    public void setImg(int[] img) {
        this.img = img;
    }
    
    
    private int height;
    private int width;
    private int[] img;
    private String fileName;
    
    
    /**
     * PGMImage constructor that loads an image from a fileName.
     * @param fileName The complete path to the image to load.
     */
    public PGMImage(String fileName){
        this.fileName=fileName;
        this.read(this.fileName);
    }
    
    
    /**
     * PGM Image constructor that allow you to create an empty image of a given 
     * size.
     * @param name The name of the file where the image could be saved.
     * @param height The height of the image.
     * @param width The width of the image.
     */
    public PGMImage(String name, int height, int width)
    {
        this.fileName = name ;
        this.height = height ;
        this.width = width ;
        this.img = new int[width*height];
    }
    
    
    /**
     * PGM Image constructor that allow you to create an image of a given size
     * with a unique value on all the pixels.
     * @param name The name of the file where the image could be saved.
     * @param height The height of the image.
     * @param width The width of the image.
     * @param value The value of the pixels.
     */
    public PGMImage(String name, int height, int width, int value)
    {
        this.fileName = name ;
        this.height = height ;
        this.width = width ;
        this.img = new int[width*height];
        for(int i = 0; i < height*width; i++){
            this.img[i] = value;
        }
    }
    
    
    /**
     * PGMImage copy constructor.
     * @param other The PGMImage to be copied.
     */
    public PGMImage(PGMImage other)
    {
        this.height = other.getHeight();
        this.width = other.getWidth();
        this.fileName = other.getFileName();
        this.img = new int[width*height];
        for(int i = 0 ; i < this.height*this.width ; i ++)
        {
            this.img[i]=other.getImg()[i];
        }
    }
    
    
    /** 
     * A method that copy the current PGMImage.
     * @return 
     */
    public PGMImage copy()
    {
        return new PGMImage(this);
    }
    
    
    public void read(String fileName){
        
        try {
            InputStream f = new FileInputStream(fileName);
            Scanner sc = new Scanner(f);
            
            String p2 = sc.nextLine();    // first line contains P2
            if(!p2.equals("P2")){
                throw new PGMFormatException();
            }
            
            // first line containes a comment
            sc.nextLine();     
            
            // second line contains height and width
            this.width = sc.nextInt();
            this.height = sc.nextInt();
            this.img = new int[width*height];
            
            sc.nextLine();// third line contains maxVal
            
            sc.nextLine();

            
            int b = 0;
            for (int count= 0 ; count < height*width ; count ++) {
                b = sc.nextInt() ;
                img[count]=b;
                
            }
        }
        catch(FileNotFoundException e)
        {
            System.err.println("File " +fileName+" not found.");
        }
        catch(PGMFormatException e) {
            System.err.println("An error has occured will reading the file.");
        }
        
    }
    
    
    /**
     * Write the image en PGM format.
     * @param fileName The name (with total path) to the file where the image 
     * will be saved.
     * @throws IOException If the file can't be write.
     */
    public void write(String fileName) throws IOException 
    {
        String path = fileName;
        if( ! (fileName.substring(fileName.length()-4).equals(".pgm")) ) {
            path = fileName + ".pgm";
        }
        
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        
        writer.write("P2");
        writer.newLine();
        
        writer.write("# ");
        writer.newLine();
        
        writer.write(""+this.width+" "+this.height);//cast in string.
        System.out.println(this.width);
        System.out.println(this.height);
        writer.newLine();      

        
        writer.write(""+255);//cast in string.
        writer.newLine();
        
        int nbPixel = this.height * this.width;
        int nbChar = 0;
        
        for(int i = 0 ; i < nbPixel ; i ++)
        {
            int px = this.img[i];
            if(px < 10){
                writer.write(px+"   ");
            }
            else if(px < 100) {
                writer.write(px+"  ");
            }
            else {
                writer.write(px+" ");
            }
            nbChar += 4;
            if(nbChar > 66){
                writer.newLine();
                nbChar = 0;
            }
        }
        writer.close();
    }
    
    
    /**
     * Overwrite the current image file in PGM format.
     * @throws IOException If the file can't be write.
     */
    public void overwrite() throws IOException 
    {
        write(this.fileName);
    }
    

    /**
     * Apply a threshold to a gray scale PGM Image.
     * @param thr The threshold (0 - 255) 
     * @return A PGMImage resulting with the apply of the threshold.
     */
    public PGMImage applyThreshold(int thr)
    {
        PGMImage output = this.copy();
        
        for(int i = 0 ; i < this.height*this.width ; i++)
        {
            if(this.img[i] > thr)
            {
                output.getImg()[i] = thr;
            }
        }
        
        return output;
    }
  
  
    /**
     * Computes the histogram of gray levels of the file
     * @return 
     */
    public int[] computeHistogram(){
        
        int[] histo = new int[256];
        
        for(int i=0; i<=255;i++){
            histo[i]=0;
        }
        
        for(int i=0;i<height*width;i++){
            histo[img[i]]++;
        }
        
        return(histo);
    }
    

    
    
    public PGMImage computeDiff(PGMImage other){
        
        PGMImage result = new PGMImage("DIFF.pgm", this.height, this.width);
        
        try{
            if(this.height!=other.height || this.width!=other.width){
                throw new Exception();
            }
            
            
            for(int i=0;i<height*width;i++){
                int diff = this.img[i]-other.img[i];
                if(diff<0){
                    diff=0;
                }
                result.img[i]=diff;
            }
        }
        catch(Exception e){
            System.err.println("The two images are not the same size, can't compute the difference");
        }
        
        return(result);
    }
    
    
       
        
        
        public void drawImage(Graphics gr){
            
            

            //we draw all pixels on the graphic
            for(int y = 0; y < height; y ++){
                for(int x = 0; x < width; x ++){
                    int index = y*width + x;
                    int gray = img[index];

                    //to draw we first set the color
                    gr.setColor(new Color(gray,gray,gray));
                    

                    //then draw the pixel
                    gr.fillRect(x, y,1,1); //draw
                }
            }
            
        }
            
}
