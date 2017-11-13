13th Floor
Danimae Janssen - 11/11/2017

An imaginary flooring company has been tasked with providing a wide range of flooring options in a fantasy world.  The data set in this demonstration consists of 13000+ orders spanning a wide range of details.  

The implementation is java via standard Spring MVC/AOP structure, with a pure file IO back end. Orders are stored on a file-per-day basis (as defined in the spec).  A safe data scheme is employed wherein each order is copied to a subdirectory before changes are made.  If the changes are verified, the old file is removed.  Otherwise, the original file is restored intact and the app lets the user know.

Notes***
Use 13thFloor-BlackWhite.exe if running on windows, since the windows console does not support ANSI color codes. Please make sure to expand your console window once the program launches, since the search option requires a large width!!! (On windows, a console width of 150 should suffice).

Jar versions (colored and black and white) are provided for linux and mac deployment. Use the color version where possible for the best experience!

floor13.cfg provides the app with details such as file directory location for the orders and meta data, and a training mode option that can be enabled.  In training mode, all changes to data are retained in memory for the duration of the run process, but are never persisted to the file base.

JRE v1.8+ is REQUIRED to run this app becuase it makes heavy use of lamba/stream functions.

