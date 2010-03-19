------------------------------------------------------------------------------
                                    GWTLIB
                         Copyright 2008 Sander Berents
                Licensed under the Apache License, Version 2.0
                               (see LICENSE.TXT)
------------------------------------------------------------------------------

GWTLib (http://www.gwtlib.org) is a GWT widget library. 



USING GWTLIB
------------------------------------------------------------------------------

To use GWTLib, add the gwtlib.jar file to your classpath and the line

  <inherits name="org.gwtlib.GWTLib"/>

to your module XML file.

The image and stylesheet resources from the example projects are not included in the 
gwtlib.jar file. Please include equivalent resources in your project.



CHANGES IN THIS RELEASE
------------------------------------------------------------------------------

Please see CHANGES.TXT for details.



ABOUT THE EXAMPLES
------------------------------------------------------------------------------

The included examples are:

  * MinimalTable
    A minimalistic example which you should study first before any other table example.

  * RPCTable
    A basic example which demonstrates RPC usage.

  * PagingTable
    A demonstration of many paging table features, e.g. sorting, selection, paging, etc.

  * MenuBar
    A demonstration of menu item toggling and enabling/disabling menu items.


BUILDING GWTLIB
------------------------------------------------------------------------------

The prerequisites for building GWTLib are GWT 1.5 and ant. Create a file named 
"build.properties" in the base directory, or a file named "gwtlib.build.properties" 
in your home directory.

In this file, add the line

gwtdir=<PATH TO YOUR GWT DIRECTORY>

For Linux, also add the line

platform=linux

For MacOSX, add

platform=mac

instead.

If you now run ant using bin/build.xml, it will create the jar file as well as
the zip file containing the complete distribution. If you only want to generate the
jar file, run ant with target "jar". Use target "clean" for cleanup.

