import java.util.*;

import javax.swing.text.AttributeSet;
import javax.swing.text.html.HTML.Tag;

import java.io.*;

public class HashTable {

    private int size;
    private LinkedList<String>[] buckets;

    public HashTable() {
        buckets = new LinkedList[100];
    }

    // Where collision setup has to be?
    // Chaining create a linked list withing each index spot
    public void put(String key) {
        int hashCode = key.hashCode();
        int arrayIndex = Math.abs(hashCode) % buckets.length;

        if (buckets[arrayIndex] == null) {
            buckets[arrayIndex] = new LinkedList<String>();
        }
        buckets[arrayIndex].add(key);
        size++;

        if (size > ((buckets.length * 2) / 3)) {

            // take everything and re add it to a new table that's twice as big
            LinkedList<String>[] temp = buckets;
            buckets = new LinkedList[buckets.length * 2];
            // loop through all the temp elements
            // loop through the inner lists
            // call put on all of them

            for (int i = 0; i < temp.length; i++) {
                if (temp[i] != null) {
                    if (buckets[arrayIndex] == null) {
                        buckets[arrayIndex] = new LinkedList<String>();
                    }
                    buckets[arrayIndex].add(key);
                }
            }
        }
    }

    // Has to think about collisions here
    public String get(String key) {
        int hashCode = key.hashCode();
        int arrayIndex = Math.abs(hashCode) % buckets.length;

        if (buckets[arrayIndex] != null) {
            int index = 0;
            while (index < buckets[arrayIndex].size() && !buckets[arrayIndex].get(index).equals(key)) {
                index++;
            }
            if (buckets[arrayIndex].get(index).equals(key)) {
                return "found";
            } else {
                return "not found";
            }
        }
        return null;
    }

    public Integer remove(String key) {
        int hashCode = key.hashCode();
        int arrayIndex = Math.abs(hashCode) % buckets.length;

        if (buckets[arrayIndex] != null && buckets[arrayIndex].contains(key)) {
            buckets[arrayIndex].remove(key);
            return 1;
        }
        return 0;
    }

    public Iterator keys() {
        return new MyIterator();
    }

    public void print() {
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] != null) {
                for (int x = 0; x < buckets[i].size(); x++) {
                    String key = buckets[i].get(x);
                    System.out.println(key);
                }
            }
        }
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
        Iterator<String> iterator;

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
        while (iterator.hasNext()) {
            String key = iterator.next();
            printWriter.println(key);
            String value = get(key);
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

    private class MyIterator implements Iterator {
        int index = 0;

        @Override
        public String next() {
            if (hasNext()) {
                for (int i = 0; i < buckets.length; i++) {
                    index++;
                    if (buckets[i] != null) {
                        return buckets[i].get(index);
                    }
                }
            } 
            return null;
        }

        public boolean hasNext() {
            if (buckets != null) {
                for (int i = 0; i < buckets.length; i++) {
                    if (buckets[i] != null && buckets[i].size() > 0) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
