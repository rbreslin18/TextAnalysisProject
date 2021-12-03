
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;
/**
 * Final Project Text Analysis Tool
 * Using TEXTIo from java.
 * Rodney Breslin
 */
public class TextAnalysisTool {

private static TreeMap<String, TreeSet<Integer>>  concordance;



public static void main(String[] args) throws Exception{
    System.out.print("Text Analysis Tool Final Project ");
   System.out.print("Press return to begin.");
   
   TextIO.getln();  // Wait for user to press return.
   
   try {
      
      // Let user select the input file.  If the user cancels,
      // the program ends immediately.
  
      if (TextIO.readUserSelectedFile() == false) {
         System.out.println("No input file selected.  Exiting.");
         System.exit(0);
      }
         
      // Create the data structure that will hold the concordance.
 
      concordance = new TreeMap<String, TreeSet<Integer>>();
  
      int lineNum = 1;  // The number of the line in the input
                        // file that is currently begin processed.
      
      // Read words from the file until end of file is reached,
      // and add each word to the data.

      while (true) {
         char ch = TextIO.peek(); // Look ahead at next character
         while ( ch != TextIO.EOF && ! Character.isLetter(ch) ) {
                   // Skip over non-letter characters, stopping when 
                   // end-of-file (TextIO.EOF) or a letter is seen.  If the
                   // character is an end-of-line character, add 1
                   // to lineNum to reflect that fact that we are moving
                   // on to the next line in the file.
            TextIO.getAnyChar();  // Reads the next character, which is junk.
            if (ch == '\n') {
               lineNum++;  // Start of a new line.
            }
            ch = TextIO.peek();  // Look at the next character.
         }
         if (ch == TextIO.EOF) {
                 // The end-of-file has been reached, so exit from the loop.
            break;
         }
         String word = readWord();  // The next word from the file.
         word = word.toLowerCase();
         if (word.length() > 2  && !word.equalsIgnoreCase("the")) {
                 // Add the reference to word to the concordance, unless
                 // the word is "the" or the word has length <= 2.
            addReference(word,lineNum);
         }
      }
      
      // Write the data to a user-selected file, or to standard
      // output if the user does not select an output file.

      System.out.println(concordance.size() + " distinct words were found in the file.\n");
      System.out.println();
      if (concordance.size() == 0) {
         System.out.println("No words found in file.");
         System.out.println("Exiting without saving data.");
         System.exit(0);
      }

      TextIO.writeUserSelectedFile(); // If user cancels, output automatically
                                      // goes to standard output.
  
      printConcordance();  // Print the data to the output file.
 
   }
   catch (IllegalArgumentException e) {
      System.out.println( "Sorry, some error occurred:  " + e.getMessage() );
   }
    java.io.File file = new File("samp_prvr.txt");
        Scanner input = new Scanner(file);
        String text = "";
        ArrayList<String> keys = new ArrayList<String>();
        java.io.File file2 = new java.io.File("FRQ.txt");
        if(file2.exists()){
        System.out.println("This file already exists");
        System.exit(1);
        }
        String[] uniqueKeys;
        while(input.hasNext()){
            text = input.next();
        keys.add(text);
        }
        int count = 0;
        System.out.println(text);
        uniqueKeys = getUniqueKeys(keys);
java.io.PrintWriter output = new java.io.PrintWriter(file2);
        for(String key: uniqueKeys)
        {
            if(null == key)
            {
                break;
            }           
            for(String s : keys)
            {
                if(key.equals(s))
                {
                    count++;
                }               
            }
            
            output.println("Count of ["+key+"] is : "+count);
            
            count=0;
            
        }
        output.close();
} 
private static String[] getUniqueKeys(ArrayList<String> keys)
    {
        String[] uniqueKeys = new String[keys.size()];

        uniqueKeys[0] = keys.get(0);
        int uniqueKeyIndex = 1;
        boolean keyAlreadyExists = false;

        for(int i=1; i<keys.size() ; i++)
        {
            for(int j=0; j<=uniqueKeyIndex; j++)
            {
                if(keys.get(i).equals(uniqueKeys[j]))
                {
                    keyAlreadyExists = true;
                }
            }           

            if(!keyAlreadyExists)
            {
                uniqueKeys[uniqueKeyIndex] = keys.get(i);
                uniqueKeyIndex++;               
            }
            keyAlreadyExists = false;
        }       
        return uniqueKeys;
    }

/**
 * Prints concordance to text file of choosing
 */
private static void printConcordance() {
   
   for ( Map.Entry<String, TreeSet<Integer>>  entry :  concordance.entrySet() ) {
    
      String term = entry.getKey();
      TreeSet<Integer> pageSet = entry.getValue();

      TextIO.put( term + " " );
      for ( int page : pageSet ) {
         TextIO.put( page + " " );
      }
      TextIO.putln();
   
    }
}


/**
 * Add a word reference to the concordance.
 */
private static void addReference(String word, int lineNum) {
   TreeSet<Integer> references; // The set of lines where we have
                                //    previously found the word.
   references = concordance.get(word);
   if (references == null){
          // This is the first reference that we have
          // found for the word.  Make a new set containing
          // the line number and add it to the concordance, with
          // the word as the key.
       TreeSet<Integer> firstRef = new TreeSet<Integer>();
       firstRef.add( lineNum );  // lineNum is "autoboxed" to give an Integer!
       concordance.put(word,firstRef);
   }
   else {
         // The variable references is the set of line references
         // that we have found previously for the word.
         // Add the new line number to that set.  This
         // set is already associated to word in the concordance.
      references.add( lineNum ); // pageNum is "autoboxed" to give an Integer!
   }
}


/**
 * Read the next word from TextIO
 */
private static String readWord() {
   char ch = TextIO.peek(); // Look at next character in input.
   assert Character.isLetter(ch);
   String word = "";  // This will be the word that is read.
   while (true) {
      word += TextIO.getAnyChar();  // Append the letter onto word.
      ch = TextIO.peek();  // Look at next character.
      if ( ch == '\'' ) {
            
         TextIO.getAnyChar();   
         ch = TextIO.peek();    // Look at char that follows apostrophe.
         if (Character.isLetter(ch)) {
            word += "\'" + TextIO.getAnyChar();
            ch = TextIO.peek();  // Look at next char.
         }
         else
            break;
      }
      if ( ! Character.isLetter(ch) ) {
            
         break;
      }
      
   }
   return word;  
}


} 
