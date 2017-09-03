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

    private UserIo io;

    public DvdCollectionView(UserIo io) {
        this.io = io;
    }

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
        io.print("8. Exit (saves changes)");
        return io.readInt("\nEnter your choice: ", 1, 8);

    }

    public String getDvdTitle() {
        io.print("");
        return io.readLine("Enter the full DVD name: ");
    }

    public int showSaveMenuGetChoice() {
        io.print("You can choose to save your library as ");
        io.print("a standard library or an encrypted library. \n");
        io.print("1. Use a standard library");
        io.print("2. Use an encrypted library");
        io.print("3. Main menu");

        return io.readInt("\nPlease make a selection: ", 1, 3);
    }

    public int showLoadMenuGetChoice() {
        io.print("Please specify which type of libary ");
        io.print("to load (standard or encrypted). \n");
        io.print("1. Load a standard library");
        io.print("2. Load an encrypted library");
        io.print("3. Main menu");

        return io.readInt("\nPlease make a selection: ", 1, 3);
    }

    public String getFilename() {
        String defaultName = "defaultLibrary.txt";
        String fileName;

        fileName = io.readLine("Please enter the full file name: ");

        if (fileName.trim().isEmpty()) {
            return defaultName;
        } else {
            return fileName;
        }
    }

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
        io.print("MPAA Rating: " + title.getMpaaRating() + " stars ");
        io.print("Your rating: " + title.getUserRating() + " stars ");
        io.print("Director: " + title.getDirector());
        io.print("Studio: " + title.getStudio());
        io.print("Additional Information: " + title.getUserNotes());

        waitOnUser();
    }

    public int showEditMenuGetChoice(Title title) {
        io.print("");

        io.print("1. Title: " + title.getTitle());
        io.print("2. Duration: " + title.getDuration());
        io.print("3. Release Date: " + title.getReleaseDate());
        io.print("4. MPAA Rating: " + title.getMpaaRating() + " stars ");
        io.print("5. Your rating: " + title.getUserRating() + " stars ");
        io.print("6. Director: " + title.getDirector());
        io.print("7. Studio: " + title.getStudio());
        io.print("8. Additional Information: " + title.getUserNotes());
        io.print("9. Main Menu");
        io.print("0. Delete title");

        return io.readInt("\nWhich item would you like to edit? ", 0, 9);
    }

    public Title showEditorGetEdits(Title title, int choice) {
        String newVal;
        io.print("");

        switch (choice) {
            case 1: {
                io.print("Current title: " + title.getTitle());
                newVal = io.readLine("Enter new Title: ");
                title.setTitle(newVal);
                break;
            }
            case 2: {
                io.print("Current release date: " + title.getDuration());
                newVal = io.readLine("Enter new duration: ");
                title.setDuration(newVal);
                break;
            }
            case 3: {
                io.print("Current release date: " + title.getReleaseDate());
                newVal = io.readLine("Enter new date: ");
                title.setReleaseDate(newVal);
                break;
            }
            case 4: {
                io.print("Current MPAA rating: " + title.getMpaaRating() + " stars");
                newVal = io.readLine("Enter new rating (stars): ");
                title.setMpaaRating(newVal);
                break;

            }
            case 5: {
                io.print("Your current rating: " + title.getUserRating() + " stars");
                newVal = io.readLine("Enter new rating (stars): ");
                title.setUserRating(newVal);
                break;
            }
            case 6: {
                io.print("Current director: " + title.getDirector());
                newVal = io.readLine("Enter new director: ");
                title.setDirector(newVal);
                break;
            }
            case 7: {
                io.print("Current studio: " + title.getStudio());
                newVal = io.readLine("Enter new studio: ");
                title.setStudio(newVal);
                break;
            }
            case 8: {
                io.print("Current details: " + title.getUserNotes());
                newVal = io.readLine("Enter new details: ");
                title.setUserNotes(newVal);
                break;
            }
        }

        return title;

    }

    public boolean askAdd() {
        return io.readAnswer("Would you like to add another (y/n)? ");
    }

    public boolean askRemove() {
        return io.readAnswer("Would you like to remove another (y/n)? ");
    }

    public boolean askEdit() {
        return io.readAnswer("Would you like to edit another (y/n)? ");
    }

    public void printAllTitles(List<Title> titleList) {
        int i = 1;
        io.print("");
        io.print("[Title]     [Duration] [Year] [MPAA] [Rating]  [Director]\t[Studio]\t[Additional]");
        io.print("-------------------------------------------------------------------------------------------------------");
        for (Title currentTitle : titleList) {
            io.print(currentTitle.getTitle() + "\t"
                    + currentTitle.getDuration() + "  \t"
                    + currentTitle.getReleaseDate() + "\t"
                    + currentTitle.getMpaaRating() + "\t"
                    + currentTitle.getUserRating() + "\t"
                    + currentTitle.getDirector() + "\t "
                    + currentTitle.getStudio() + " \t"
                    + currentTitle.getUserNotes());
            i = i + 1;
        }
        io.print("-------------------------------------------------------------------------------------------------------");

    }

    public void waitOnUser() {
        io.readLine("\nPress enter to continue...");
    }

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

        io.print("\n");

        return title;

    }

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
        clearScreen();
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

    public void showExitMessage() {
        io.print("\n");
        io.print("GOODBYE!!");
        io.print("\n");
    }

    public void librarySaveSuccess() {
        io.print("<--- Library saved sucessfully!!! --->");
        waitOnUser();
    }

    public void libraryLoadSuccess() {
        io.print("<--- Library Loaded sucessfully!!! --->");
        waitOnUser();
    }

    public void removalSuccess() {
        io.print("<--- Removal was a success!!! --->");
        waitOnUser();
    }

    public void noEntryException() {
        io.print("No entry was found! Please enter the EXACT "
                + "\ntitle.");
        waitOnUser();
    }

    public void fileIOException() {
        io.print("Error opening file - please check the file name."
                + "\nIf file is encrypted, please use option 2...");
        waitOnUser();
    }

    public void fileEncryptionException() {
        io.print("Error decrypting file.  Wrong password or incorrect file.");
        waitOnUser();
    }

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
