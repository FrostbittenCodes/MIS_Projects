NAME
    MIS_Project_1 - Allows users to visualize differences in data sets

SYNOPSIS
    java -jar MIS_Project_1.jar -c <cSpace> [options] <infile 1> <infile 2>

DESCRIPTION
    Reads the data sets from two 20 line csv files, calculates the differences, and allows users
    to visualize them using different colors in different color spaces by outputting the data to a bmp file.

OPTIONS
    -h Shows the help dialogue.

    -c <cSpace> Must be the first argument.
                Sets the color space to string cSpace, valid options are as follows:
                RGB, XYZ, Lab, YUV, YCbCr, YIQ, HSL


    -B <i> Sets the beta value to integer i for scaling, defaults to 1

    -o <outputfile> Sets the file to write the bitmap image output

    -t <i> Sets the number of pixels for line thickness to the integer i (default 10)

    -p <i> Sets the number of pixels for the padding thickness to the integer i (default 20)

    -b Toggles the background from white (default) to black


RGB AND YCbCr COLOR SPACE OPTIONS
    -u <i> Sets the upper color (biggest difference) to the packed integer i (RGB and YCbCr only) range is [0x000000, 0xffffff]
                Defaults to 0x000000

    -l <i> Sets the lower color (smallest difference) to the packed integer i (RGB and YCbCr only) range is [0x000000, 0xffffff]
                Defaults to 0xffffff

LAB COLOR SPACE OPTIONS
    -u <i> Sets the upper color (biggest difference) to the packed integer i (RGB and YCbCr only) range is [0x000000, 0xffffff]
                Defaults to 50, 50, 84

    -l <i> Sets the lower color (smallest difference) to the packed integer i (RGB and YCbCr only) range is [0x000000, 0xffffff]
                Defaults to 50, 90, 10

OTHER COLOR SPACE OPTIONS
    -u <f1> <f2> <f3> Sets the upper color to the three normalized color components (e.g. XYZ or YUV) range is [0,1]
                Defaults to 0.0, 0.0, 0.0

    -l <f1> <f2> <f3> Sets the upper color to the three normalized color components (e.g. XYZ or YUV) range is [0,1]
                Defaults to 1.0, 1.0, 1.0

DEPENDENCIES
    JVM 7.0+

EXAMPLES
    java -jar MIS_Project_1.jar -c Lab -b -o LabOutput.bmp Data/sampledata/X/1.csv Data/sampledata/X/2.csv
        Outputs the data to a file named LabOutput.bmp, traversing in Lab color space, has a black background instead
            of white for the output bitmap.

    java -jar MIS_Project_1.jar -c XYZ -l 0.5 0.1 0.3 -u 0.2 0.8 0.63 Data/sampledata/X/1.csv Data/sampledata/X/2.csv
        Outputs the data to a file named output.bmp, traversing in Lab color space using [0.5, 0.1, 0.3] as the lower color
            and [0.2, 0.8, 0.63] as the upper color.

AUTHORS
    Steven Brown
    Kyle St. Leger-Barter
    
