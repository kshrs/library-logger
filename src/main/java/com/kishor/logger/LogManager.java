package com.kishor.logger;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import java.time.LocalDate;
import java.time.LocalTime;

// LogManager Class
// NOTE: Don't create object of this class, use only the static members through the class template.
public final class LogManager {
   private static final String DBFolder = "db"; // Folder to save the csv files
   private static LocalDate dateToday = LocalDate.now(); // Current Date
   private static final String LogFile = dateToday.toString().concat(".csv"); // File Name to store the data : "YYYY-MM-DD.csv"
   private static final Path path = Paths.get(DBFolder, LogFile); // Complete Path: "db/YYYY-MM-DD.csv"

   // To create the csv file with the date as file name if it doesn't exist. Format: YYYY-MM-DD.csv
   public static void ensureFileExists() {
       if (!Files.exists(path)) {
           try {
               String header = "DATE,TIME,ENTRY_INFO,NAME,ID,CABIN\n";
               Files.writeString(path, header, StandardOpenOption.CREATE);
           } catch(IOException e) {
               System.out.println("Something went wrong, cannot create new file for log with name: " + dateToday.toString() + ".csv");
               e.printStackTrace();
           }
       }
   }

   // Appends the user entry information to the csv file of the current date
   public static void checkInUser(Student student, int cabinPos) {
       String entry = LocalDate.now() + "," + LocalTime.now() + ",CHECK_IN," + student.getName() + "," + student.getID() + "," + cabinPos;
       System.out.println(entry);
       try {
           Files.writeString(path, entry.concat("\n"), StandardOpenOption.APPEND);
       } catch(IOException e) {
           System.out.println("Cannot write to file, check error trace");
           e.printStackTrace();
       }
   }

   // Appends the user exit information to the csv file of the current date
   public static void checkOutUser(Student student, int cabinPos) {
       String entry = LocalDate.now() + "," + LocalTime.now() + ",CHECK_OUT," + student.getName() + "," + student.getID() + "," + cabinPos;
       System.out.println(entry);
       try {
           Files.writeString(path, entry.concat("\n"), StandardOpenOption.APPEND);
       } catch(IOException e) {
           System.out.println("Cannot write to file, check error trace");
           e.printStackTrace();
       }
   }
}
