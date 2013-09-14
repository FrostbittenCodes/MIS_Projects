NAME
    MIS_Project_1 - Allows users to visualize differences in data sets

SYNOPSIS
    java -jar MIS_Project_1.jar [options] <infile 1> <infile 2>

DESCRIPTION
    Reads the data sets from two 20 line csv files, calculates the differences, and allows users
    to visualize them using different colors in different color spaces.

OPTIONS
    -h -? (Not implemented) Shows the help dialogue.

    -B <i> Sets the beta value to integer i for scaling

    -b Toggles the background from white (default) to black

    -c <cSpace> (No effect) Sets the color space to string cSpace, valid options are as follows:
                RGB, XYZ, Lab, YUV, YCbCr, YIQ, HSL

    -u <i> Sets the upper color (biggest difference) to the packed integer i

    -l <i> Sets the lower color (smallest difference) to the packed integer i
    
DEPENDENCIES
    JVM 7.0+

AUTHORS
    Steven Brown
