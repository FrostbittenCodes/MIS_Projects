
//Quick and dirty java implementation of phase one for CSE 408 project 1
//Needs some cleanup, addition of other color space traversal methods, better error
// handling, finish parsing command line options, and various other things (see TODO's)
package mis.project1;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main {

    public static void main(String[] args) 
    {
        //Declarations and Initializations (To defaults if no command line args used to override)
        BufferedReader input1;
        BufferedReader input2;
        String f1 = "Data/sampledata/X/1.csv";
        String f2 = "Data/sampledata/X/2.csv";
        String outputfile = "test.bmp";
        String line;
        double[][] data1 = new double[20][];
        double[][] data2 = new double[20][];
        double[][] diff = new double[20][];
        int B = 1;
        int maxlength;
        int[] imagedata;
        int width;
        int padding = 20;
        int thickness = 10;
        int height = thickness*20 + 22*padding;
        int color1 = 0x000000;
        int color2 = 0xffffff;
        BufferedImage output;
        int background = 0xffffff;
        
        //Parse command line arguments
        //TODO: Add man page type thing on -h
        for(int i = 0; i < args.length; i++)
        {
            switch(args[i])
            {
                case "-d1":
                    f1 = args[i+1];
                    i++;
                    break;
                case "-d2":
                    f2 = args[i+1];
                    i++;
                    break;
                case "-B":
                    B = Integer.parseInt(args[i+1]);
                    i++;
                    break;
                case "-b":
                    background = 0x000000;
                    break;
                case "-c":
                    //color space argument, reserved
                    i++;
                    break;
                case "-u":
                    //TODO: Needs to check for validity in input
                    color1 = Integer.decode(args[i+1]);
                    i++;
                    break;
                case "-l":
                    //TODO: Needs to check for validity in input
                    color2 = Integer.decode(args[i+1]);
                    i++;
                    break;
                default:
                    System.err.println("Unrecognized argument");
                    return;
            }
        }
        
        //Open the two file readers
        try
        {
            input1 = new BufferedReader(new FileReader(f1));
            input2 = new BufferedReader(new FileReader(f2));
        }
        catch(FileNotFoundException e)
        {
            System.err.println(e);
            return;
        }
        
        //Read and parse the data for files
        try
        {
            //Executes 20 times (for the given data sets)
            for(int j = 0; j < 20; j++)
            {
                //Read and parse data for file 1
                line = input1.readLine();
                String temp[] = line.split(",");
                data1[j] = new double[temp.length];
                for(int i = 0; i < temp.length; i++)
                {
                    data1[j][i] = Double.parseDouble(temp[i]);
                }
                //Read and parse data for file 2
                line = input2.readLine();
                temp = line.split(",");
                data2[j] = new double[temp.length];
                for(int i = 0; i < temp.length; i++)
                {
                    data2[j][i] = Double.parseDouble(temp[i]);
                }
            }
        }
        catch(IOException e)
        {
            System.err.println(e);
        }
        
        //Figure out which data set is longer
        if(data1[0].length > data1[0].length)
            maxlength = data1[0].length;
        else
            maxlength = data2[0].length;
        
        //Calculate the difference and perform scaling (outer loop runs 20 times, inner runs variable (maxlength) amount)
        for(int j = 0; j < 20; j++)
        {
            double max = 0;
            diff[j] = new double[maxlength];
            for(int i = 0; i < maxlength; i++)
            {
                if((data1[j].length > i) && (data2[j].length > i))
                {
                    diff[j][i] = Math.abs(data1[j][i]-data2[j][i]);
                }
                else if(data1[j].length > i)
                {
                    diff[j][i] = Math.abs(data1[j][i]);
                }
                else
                {
                    diff[j][i] = Math.abs(data2[j][i]);
                }
                if(diff[j][i] > max)
                    max = diff[j][i];
            }
            //scale by max in sensor set
            for(int i = 0; i < maxlength; i++)
            {
                diff[j][i] = diff[j][i]/max;
                diff[j][i] = Math.pow(diff[j][i], B);
            }
        }
        
        //Convert to RGB values, this is the point that changes for other colorspaces
        //Call other color space converter instead of convertColor which is RGB
        //TODO: come up with better method naming scheme
        int temp[] = new int[maxlength*20];
        for(int j = 0; j < 20; j++)
        {
            for(int i = 0; i < diff[j].length; i++)
            {
                //TODO: add other colorspace support
                temp[(maxlength*j)+i] = convertColor(color1, color2, diff[j][i]);
            }
        }
        
        //Set image width and instantiate imagedata/image
        width = maxlength*thickness + 2*padding;
        imagedata = new int[width*height];
        output = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        //Draw background then populate imagedata
        for(int i = 0; i < imagedata.length; i++)
            imagedata[i] = background;
        for(int j = 0; j < 20; j++)
        {
            int r,q;
            for(int i = 0; i < diff[j].length; i++)
            {
                //Sets integers in appropriate width x width area
                // to the specified color rgb value calculated from above
                for(r = 0; r < thickness; r++)
                    for(q = 0; q < thickness; q++)
                        imagedata[(padding + j*(thickness+padding)+r)*width + (padding+i*thickness + q)] = temp[(maxlength*j)+i];
            }
        }
        
        //Create image from data and write to file
        output.setRGB(0, 0, width, height, imagedata, 0, width);
        try
        {
            ImageIO.write(output, "bmp", new File(outputfile));
            System.out.println("Image successfully generated");
        }
        catch(IOException e)
        {
            System.err.println(e);
        }
    }
    
    //Takes two packed integers (RGB, 8 bits each) and a double (the diff val) and returns
    // another packed integer in RGB (also 8 bits each).
    //Works by drawing a vector between the two points in 3d space and scales using the in value
    // where 0 is the first point and 1 is the second point.
    public static int convertColor(int c1, int c2, double in)
    {
        int c1r, c1b, c1g;
        int c2r, c2b, c2g;
        int c12r, c12b, c12g;
        int result;
        
        //unpack components in c1/c2
        c1g = c1&0x0000ff;
        c2g = c2&0x0000ff;
        c1b = (c1>>8)&0x0000ff;
        c2b = (c2 >> 8)&0x0000ff;
        c1r = (c1>>16)&0x0000ff;
        c2r = (c2 >> 16)&0x0000ff;
        
        //calculate direction vector
        c12r = c2r-c1r;
        c12g = c2g-c1g;
        c12b = c2b-c1b;
        
        //scale, repack, and return
        result = (int) (c1r+in*c12r);
        result = (result << 8) | (int) (c1g+in*c12g);
        result = (result << 8) | (int) (c1b+in*c12b); 
        
        return result;
    }
}
