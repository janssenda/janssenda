/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.dvdcollection.ui;

import com.dm.dvdcollection.dto.Title;
import java.util.List;

/**
 *
 * @author danimaetrix
 */
public class DvdCollectionView {

    // Recieve injection
    private UserIo io;

    public DvdCollectionView(UserIo io) {
        this.io = io;
    }

    // Main menu screen
    public int printMainGetChoice() {
        io.print("Welcome to C++, your modern DVD library!");
        io.print("Please choose from the following options: \n");
        io.print("1. Add a new title");
        io.print("2. Remove a title");
        io.print("3. See all titles");
        io.print("4. Update a title");
        io.print("5. Search titles");
        io.print("6. Save library");
        io.print("7. Load library");
        io.print("8. Exit");
        return io.readInt("\nEnter your choice: ", 1, 8);

    }

    // Recieve user input for search and edit
    public String getDvdTitle() {
        io.print("");
        return io.readLine("Enter the full DVD name: ");
    }

    // Save library menu - offers standard or encrypted methods
    public int showSaveMenuGetChoice() {
        io.print("You can choose to save your library as ");
        io.print("a standard library or an encrypted library. \n");
        io.print("1. Use a standard library");
        io.print("2. Use an encrypted library");
        io.print("3. Main menu");

        return io.readInt("\nPlease make a selection: ", 1, 3);
    }

    // Load library menu - offers standard or encrypted methods
    public int showLoadMenuGetChoice() {
        io.print("Please specify which type of libary ");
        io.print("to load (standard or encrypted). \n");
        io.print("1. Load a standard library");
        io.print("2. Load an encrypted library");
        io.print("3. Main menu");

        return io.readInt("\nPlease make a selection: ", 1, 3);
    }

    // Get the name of the library from user to save/load
    public String getFilename() {
        String defaultName = "defaultLibrary";
        String fileName;

        fileName = io.readLine("Please enter the library name: ");

        if (fileName.trim().isEmpty()) {
            return defaultName;
        } else {
            return fileName;
        }
    }

    // Used to get password for encryption routines.  Uses console class to hide input on screen.
    public String getUserPassword() {
        return io.readPasswordLn("Please enter your password: ");
    }

    // Confirm that the user wants to remove the DVD
    public boolean confirmRemoveTitle() {
        io.print("");
        return io.readAnswer("Are you sure you want to remove this DVD \n"
                + "from the collection (y/n)? ");
    }

    // Shows details for a single title in a nicely formatted manner
    public void showTitleInfo(Title title) {
        io.print("");

        io.print("Title: " + title.getTitle());
        io.print("Title: " + title.getDuration());
        io.print("Release Date: " + title.getReleaseDate());
        io.print("MPAA Rating: " + title.getMpaaRating());
        io.print("Your rating: " + title.getUserRating() + " stars ");
        io.print("Director: " + title.getDirector());
        io.print("Studio: " + title.getStudio());
        io.print("Additional Information: " + title.getUserNotes());

        waitOnUser();
    }

    // Menu for making changes to a title.  User may also delede title
    public int showEditMenuGetChoice(Title title) {
        io.print("");

        io.print("1. Title: " + title.getTitle());
        io.print("2. Duration: " + title.getDuration());
        io.print("3. Release Date: " + title.getReleaseDate());
        io.print("4. MPAA Rating: " + title.getMpaaRating());
        io.print("5. Your rating: " + title.getUserRating() + " stars ");
        io.print("6. Director: " + title.getDirector());
        io.print("7. Studio: " + title.getStudio());
        io.print("8. Additional Information: " + title.getUserNotes());
        io.print("9. Main Menu");
        io.print("0. Delete title");

        return io.readInt("\nWhich item would you like to edit? ", 0, 9);
    }

    // Allow the user to make the specified changes
    public Title showEditorGetEdits(Title title, int choice) {
        String newVal;
        io.print("");

        switch (choice) {
            case 1: {
                io.print("Current title: " + title.getTitle());
                newVal = io.readLine("Enter new Title: ");
                if (!newVal.trim().isEmpty()) {
                    title.setTitle(newVal);
                }
                break;
            }
            case 2: {
                io.print("Current release date: " + title.getDuration());
                newVal = io.readLine("Enter new duration: ");
                if (!newVal.trim().isEmpty()) {
                    title.setDuration(newVal);
                }
                break;
            }
            case 3: {
                io.print("Current release date: " + title.getReleaseDate());
                newVal = io.readLine("Enter new date: ");
                if (!newVal.trim().isEmpty()) {
                    title.setReleaseDate(newVal);
                }
                break;
            }
            case 4: {
                io.print("Current MPAA rating: " + title.getMpaaRating());
                newVal = io.readLine("Enter new rating");
                if (!newVal.trim().isEmpty()) {
                    title.setMpaaRating(newVal);
                }
                break;

            }
            case 5: {
                io.print("Your current rating: " + title.getUserRating() + " stars");
                newVal = io.readLine("Enter new rating (stars): ");
                if (!newVal.trim().isEmpty()) {
                    title.setUserRating(newVal);
                }
                break;
            }
            case 6: {
                io.print("Current director: " + title.getDirector());
                newVal = io.readLine("Enter new director: ");
                if (!newVal.trim().isEmpty()) {
                    title.setDirector(newVal);
                }
                break;
            }
            case 7: {
                io.print("Current studio: " + title.getStudio());
                newVal = io.readLine("Enter new studio: ");
                if (!newVal.trim().isEmpty()) {
                    title.setStudio(newVal);
                }
                break;
            }
            case 8: {
                io.print("Current details: " + title.getUserNotes());
                newVal = io.readLine("Enter new details: ");
                if (!newVal.trim().isEmpty()) {
                    title.setUserNotes(newVal);
                }
                break;
            }
        }

        return title;

    }

    // Questions to ask user
    public boolean askAdd() {
        return io.readAnswer("\nWould you like to add another (y/n)? ");
    }

    public boolean askRemove() {
        return io.readAnswer("\nWould you like to remove another (y/n)? ");
    }

    public boolean askEdit() {
        return io.readAnswer("\nWould you like to edit another (y/n)? ");
    }

    // Short method to shorten and extend string to length l, using "..." to truncate 
    // and whitespace to extend.  Used to format fields for output to user in printAllTitles
    public String stShort(String string, int l) {
        //int l = 18;
        char[] newstr = new char[l];

        if (string.length() > l) {
            char[] str = string.toCharArray();
            System.arraycopy(str, 0, newstr, 0, l - 3);

            for (int i = 0; i < 3; i++) {
                newstr[(l - 3) + i] = '.';
            }

            String fstring = new String(newstr);
            return fstring;

        } else {
            char[] str = string.toCharArray();
            System.arraycopy(str, 0, newstr, 0, str.length);

            for (int i = str.length + 1; i < l; i++) {
                newstr[i] = ' ';

            }
            String fstring = new String(newstr);
            return fstring;
        }

    }

    // Shows all titles to user, taking advantage of stShort to format them nicely
    public void printAllTitles(List<Title> titleList) {
        int space = 1;
        io.print("");
        io.print("[Title]  \t[Duration][Year][MPAA][Rating][Director]           [Studio]         [Additional]");
        io.print("------------------------------------------------------------------------------------------------------------------------");
        for (Title currentTitle : titleList) {
            io.print(stShort(currentTitle.getTitle(), 17 + space) + " "
                    + stShort(currentTitle.getDuration(), 4 + space) + "  "
                    + stShort(currentTitle.getReleaseDate(), 4 + space) + "  "
                    + stShort(currentTitle.getMpaaRating(), 5 + space) + "  "
                    + stShort(currentTitle.getUserRating(), 3 + space) + "  "
                    + stShort(currentTitle.getDirector(), 19 + space) + "  "
                    + stShort(currentTitle.getStudio(), 14 + space) + "  "
                    + currentTitle.getUserNotes());
        }
        io.print("------------------------------------------------------------------------------------------------------------------------");

    }

    // Pause for user
    public void waitOnUser() {
        io.readLine("\nPress enter to continue...");
    }

    // Allows user to add new titles to collection
    public Title addTitle() {
        Title title = new Title("");
        String titlename, duration, mpaa, rating, year, director, studio, notes;

        showAddTitleBanner();
        io.print("");
        io.print("Please enter the DVD information. Blank \n"
                + "fields can be edited later to add/update entries.");

        titlename = io.readLine("\nTitle:  ");
        duration = io.readLine("Duration: ");
        year = io.readLine("Release year: ");
        mpaa = io.readLine("MPAA Rating:  ");
        rating = io.readLine("Your rating:  ");
        director = io.readLine("Director:     ");
        studio = io.readLine("Production Studio:      ");
        notes = io.readLine("Additional Information: ");

        // Check if user has skipped an entry - if so, use the default value to 
        // avoid null references
        if (!titlename.trim().isEmpty()) {
            title.setTitle(titlename);
        }
        if (!duration.trim().isEmpty()) {
            title.setDuration(duration);
        }
        if (!year.trim().isEmpty()) {
            title.setReleaseDate(year);
        }
        if (!mpaa.trim().isEmpty()) {
            title.setMpaaRating(mpaa);
        }
        if (!rating.trim().isEmpty()) {
            title.setUserRating(rating);
        }
        if (!director.trim().isEmpty()) {
            title.setDirector(director);
        }
        if (!studio.trim().isEmpty()) {
            title.setStudio(studio);
        }
        if (!notes.trim().isEmpty()) {
            title.setUserNotes(notes);
        }       

        return title;

    }

    // Banners
    public void showMainMenuBanner() {
        clearScreen();
        io.print("*******************");
        io.print("*    MAIN MENU    *");
        io.print("*******************");
    }

    public void showAddTitleBanner() {
        clearScreen();
        io.print("*******************");
        io.print("*    ADD A DVD    *");
        io.print("*******************");
    }

    public void showRemoveTitleBanner() {
        //clearScreen();
        io.print("*******************");
        io.print("*   REMOVE DVD    *");
        io.print("*******************");
    }

    public void showSearchTitlesBanner() {
        clearScreen();
        io.print("*******************");
        io.print("* SEARCH LIBRARY  *");
        io.print("*******************");
    }

    public void showSaveLibraryBanner() {
        clearScreen();
        io.print("*******************");
        io.print("*   SAVE LIBRARY  *");
        io.print("*******************");
    }

    public void showLoadLibraryBanner() {
        clearScreen();
        io.print("*******************");
        io.print("*   LOAD LIBRARY  *");
        io.print("*******************");
    }

    public void showEditTitleBanner() {
        //clearScreen();
        io.print("*******************");
        io.print("* Update a Title  *");
        io.print("*******************");
    }

    // Additional user messages
    public void showExitMessage() {
        io.print("\n");
        io.print("GOODBYE!!");
        io.print("\n");
    }

    public void editTitleSuccess() {
        io.print("\n<--- Title updated sucessfully!!! --->");
        //waitOnUser();
    }
    public void addTitleSuccess() {
        io.print("\n<--- Title added sucessfully!!! --->");
        //waitOnUser();
    }

    public void librarySaveSuccess() {
        io.print("\n<--- Library saved sucessfully!!! --->");
        waitOnUser();
    }

    public void libraryLoadSuccess() {
        io.print("\n<--- Library Loaded sucessfully!!! --->");
        waitOnUser();
    }

    public void removalSuccess() {
        io.print("\n<--- Removal was a success!!! --->");
        //waitOnUser();
    }

    public void noEntryException() {
        io.print("No entry was found! Please enter the EXACT "
                + "\ntitle.");
        waitOnUser();
    }

    public void showException(String msg) {
        io.print("\n" + msg);
        waitOnUser();
    }

    // Does not actually clear screen, but flushes it with characters so that it "looks like" screen is cleared.
    public void clearScreen() {
        for (int i = 0; i < 2; i++) {
            io.print("\n\n\n");
        }

        io.print("                     :+\"\"  ~<<::\"\"+:\n"
                + "                +Xi<<<<!<  `<<!?!<<<HMti%L\n"
                + "            :?HMMMM:<<<!<~ <<<!X<<<!MM88MMh?x\n"
                + "          !HMRMMRMMM:<<<!< <<<!!<<<MR88MRMMRMH?.\n"
                + "        ?NMMMMMMMMMMM<<<?<  <<!!<<XM88RMMMMMMMMM?\n"
                + "      !88888MMMMMMRMMk<<!!  <<H!<<M88MRMMRMMMRMMRM!\n"
                + "     <M8888888MMMMMMMM:<<!  <<H<<488RMMMMMMMMMMMMMM>:\n"
                + "   xHMRMMR888888RMMMMMM<<!< <!!<<988RMMMRMMRMMMMM?!<<%\n"
                + "  :XMMMMMMMM88888MMMMMMH<<~ ~~~<X8RMMMMMMMMMMM!!<~    k\n"
                + "  <<<!MMRMMRMMR8888MMP.n~       #R.#MMRMMRM?<~~   .nMMh.\n"
                + " !MMH:<<<!*MMMMMMM8Pu! n\"       \"+ \"h!MM!!~   :@MMMMMMM/\n"
                + ".HMRMMRMMMH:<<\"*RM M @             * \"   .nMMMMMMMRMMRMMk\n"
                + "MMMMMMMMMMMMMMMMx < \"      .u.        4'MMMMMMMMMMMMMMMM9\n"
                + "!RMMRMMMRMMRMMMMMX M     @P   #8     4 MMRMMMRMMRMMMMMMR<\n"
                + "!MMMMMMMMMMMMMMMMM !    '8     8!    ' MtMMMMMMMMMMMMMMM!\n"
                + "kMMRMMRMMRMMMRMMR4 H     #8.  @8     H MMMMRMMMMMMRMMRMM!\n"
                + "MMMMMMMMMMMMMMMMM>M         \"`      .~i <!?MMMMMMMMMMMMM9\n"
                + "'9MMRMMMRMMRMMP!   : %             H @ 8NRMHx<<<!!MMMMMR!\n"
                + " >MMMMMMMMM\"   <<HMk!i *u       .* x*xR88888MMMMHi<<<<~<\n"
                + "  !RMM#~   :<:MMRMMMMH.*n:      :*.HRMMMRM8888888MRMMM!\n"
                + "  !     <<:tMMMMMMMMMM8RM<::: :<<XMMMMMMMMMR88888888MM!\n"
                + "   ~ <<<XHMRMMMMMMRMM8RM<<<<< `!<<MRMMRMMRMMMRR888888#\n"
                + "     :HMMMMMMMMMMMM988MM<<X!<~'~<<<MMMMMMMMMMMMMR88#!\n"
                + "      ~MMRMMMRMMRMM88MM<<<?<<  <<<<!RMMMRMMRMMMMMM!\n"
                + "        xMMMMMMMM988MM%<<<?<<: <!<<<?MMMMMMMMMMMX\n"
                + "          !?MMMM@88MMR<<<<!<<<  <:<<<MRMMRMMMP!\n"
                + "            \"X*988RMM!<<<?!<<~  <!<<<<MMMMM?\"\n"
                + "                !X*MM<<<<H!<<`  <?<<<<<)!\n"
                + "                     \"+:uX!<<< .::+\"\"  ");

        io.print("");
        io.print(" _____       _ _           _   _                           \n"
                + "/  __ \\     | | |         | | (_)                _     _   \n"
                + "| /  \\/ ___ | | | ___  ___| |_ _  ___  _ __    _| |_ _| |_ \n"
                + "| |    / _ \\| | |/ _ \\/ __| __| |/ _ \\| '_ \\  |_   _|_   _|\n"
                + "| \\__/\\ (_) | | |  __/ (__| |_| | (_) | | | |   |_|   |_|  \n"
                + " \\____/\\___/|_|_|\\___|\\___|\\__|_|\\___/|_| |_|              \n"
                + "                                                           ");

        io.print("\n");
    }
}
