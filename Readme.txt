NAME
    MIS_Project_1 - Allows users to visualize differences in data sets

SYNOPSIS
    java -jar MIS_Project_1.jar -c <cSpace> [options] <infile 1> <infile 2>

DESCRIPTION
    Reads the data sets from two 20 line csv files, calculates the differences, and allows users
    to visualize them using different colors in different color spaces by outputting the data to a bmp file.

OPTIONS
    -h -? (Not implemented) Shows the help dialogue.

    -c <cSpace> Must be the first argument.
                Sets the color space to string cSpace, valid options are as follows:
                RGB, XYZ, Lab, YUV, YCbCr, YIQ, HSL


    -B <i> Sets the beta value to integer i for scaling, defaults to 1

    -o <outputfile> Sets the file to write the bitmap image output

    -t <i> Sets the number of pixels for line thickness to the integer i (default 10)

    -p <i> Sets the number of pixels for the padding thickness to the integer i (default 20)

    -b Toggles the background from white (default) to black

    -u <i> Sets the upper color (biggest difference) to the packed integer i (RGB and YCbCr only) range is [0x000000, 0xffffff]
                Defaults to 0x000000

    -l <i> Sets the lower color (smallest difference) to the packed integer i (RGB and YCbCr only) range is [0x000000, 0xffffff]
                Defaults to 0xffffff

    -u <f1> <f2> <f3> Sets the upper color to the three normalized color components (e.g. XYZ or YUV) range is [0,1]
                Defaults to 0.0, 0.0, 0.0

    -l <f1> <f2> <f3> Sets the upper color to the three normalized color components (e.g. XYZ or YUV) range is [0,1]
                Defaults to 1.0, 1.0, 1.0

DEPENDENCIES
    JVM 7.0+

EXAMPLES
    <Example here>

AUTHORS
    Steven Brown
    Kyle St. Leger-Barter
    
