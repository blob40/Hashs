import java.util.*;

import javax.swing.text.AttributeSet;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTMLDocument.Iterator;


import java.io.*;

public class HashTable {

    int capacity = 128;
    float loadFactor = .75f;
    private LinkedList <String> [] buckets;

    // Where collision setup has to be?
    //Chaining create a linked list withing each index spot
    public void put(String key) {
        int hashCode = key.hashCode();
        int arrayIndex = Math.abs(hashCode) % capacity;
     
       
        if (buckets[arrayIndex] == null){
            buckets[arrayIndex] = new LinkedList<String>();
        } 
            buckets[arrayIndex].add(key);
        
       

        if (capacity > ((capacity*2)/3)){
            capacity*=2;
        
            int newHashCode = key.hashCode();
            int newArrayIndex = Math.abs(newHashCode) % capacity;

        if (buckets[newArrayIndex] != null && buckets[newArrayIndex].contains(key)) {
            // Key already exists in new bucket
        } else {
            buckets[newArrayIndex].add(key);
        }
            //use iterator to reput things in
        }
    }

    // Has to think about collisions here
    public String get(String key) {
        int hashCode = key.hashCode();
        int arrayIndex = Math.abs(hashCode) % capacity;

        if (buckets[arrayIndex] != null) {
            return buckets[arrayIndex].get(0); // Return first element in bucket
        }
        return null;
    }

    public Integer remove(String key) {
        int hashCode = key.hashCode();
        int arrayIndex = Math.abs(hashCode) % capacity;

        if (buckets[arrayIndex] != null && buckets[arrayIndex].contains(key)) {
            buckets[arrayIndex].remove(key);
            return 1; 
        }
        return 0; 
    }

    public Iterator keys() {
        return null;
    }

    public void print() {
    }

    /**
     * Loads this HashTable from a file named "Lookup.dat".
     */
    public void load() {
        FileReader fileReader;
        BufferedReader bufferedReader = null;

        // Open the file for reading
        try {
            File f = new File(System.getProperty("user.home"), "Lookup.dat");
            fileReader = new FileReader(f);
            bufferedReader = new BufferedReader(fileReader);
        } catch (FileNotFoundException e) {
            System.err.println("Cannot find input file \"Lookup.dat\"");
        }

        // Read the file contents and save in the HashTable
        try {
            while (true) {
                String key = bufferedReader.readLine();
                if (key == null)
                    return;
                String value = bufferedReader.readLine();
                if (value == null) {
                    System.out.println("Error in input file");
                    System.exit(1);
                }
                String blankLine = bufferedReader.readLine();
                if (!"".equals(blankLine)) {
                    System.out.println("Error in input file");
                    System.exit(1);
                }
                put(key);
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }

        // Close the file when we're done
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    /**
     * Saves this HashTable onto a file named "Lookup.dat".
     */
    public void save() {
        FileOutputStream stream;
        PrintWriter printWriter = null;
        Iterator iterator;

        // Open the file for writing
        try {
            File f = new File(System.getProperty("user.home"), "Lookup.dat");
            stream = new FileOutputStream(f);
            printWriter = new PrintWriter(stream);
        } catch (Exception e) {
            System.err.println("Cannot use output file \"Lookup.dat\"");
        }

        // Write the contents of this HashTable to the file
        iterator = keys();
        while (((HashTable.MyIterator) iterator).hasNext()) {
            String key = (String) iterator.next();
            printWriter.println(key);
            String value = (String) get(key);
            value = removeNewlines(value);
            printWriter.println(value);
            printWriter.println();
        }

        // Close the file when we're done
        printWriter.close();
    }

    /**
     * Replaces all line separator characters (which vary from one platform
     * to the next) with spaces.
     * 
     * @param value The input string, possibly containing line separators.
     * @return The input string with line separators replaced by spaces.
     */
    private String removeNewlines(String value) {
        return value.replaceAll("\r|\n", " ");
    }

    private class MyIterator extends Iterator {
    
         @Override
        public void next() {
            if (hasNext()){
                return table.keys().nextElement();
            }  else {
                throw new NoSuchElementException();
            }
        }

         public boolean hasNext() {
            if (table.size() > 0){
                return true;
            }
            return false;
        }


        @Override
        public AttributeSet getAttributes() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getAttributes'");
        }

        @Override
        public int getStartOffset() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getStartOffset'");
        }

        @Override
        public int getEndOffset() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getEndOffset'");
        }

       
        @Override
        public boolean isValid() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'isValid'");
        }

        @Override
        public Tag getTag() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getTag'");
        }

    }
}
