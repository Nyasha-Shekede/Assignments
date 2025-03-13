import java.io.*;
import java.util.Arrays;

public class FileProcessor {
    private StatementEntry[] entries;
    private int entryCount;
    private static final int MAX_SIZE = 100_000;
    private static final String delimiter = "\t";
    private String fileStatusMessage;
    private SearchBST bst;
    private boolean useBST;

    /**
     * Constructor for array-based storage.
     */
    public FileProcessor() {
        this.entries = new StatementEntry[MAX_SIZE];
        this.entryCount = 0;
        this.fileStatusMessage = "No file processed yet.";
        this.useBST = false;
    }

    /**
     * Constructor for BST-based storage.
     */
    public FileProcessor(SearchBST bst) {
        this();
        this.bst = bst;
        this.useBST = true;
    }

    /**
     * Reads a file and stores entries in either an array or BST.
     */
    public boolean readFile(String filename) {
        if (!handleFilename(filename)) {
            return false;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                processLine(line);
            }
            fileStatusMessage = "File processed successfully!";
            return true;
        } catch (IOException e) {
            fileStatusMessage = "Error reading file: " + e.getMessage();
            return false;
        }
    }

    private boolean handleFilename(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            fileStatusMessage = "File not found!";
            return false;
        }
        return true;
    }

    /**
     * Processes a single line and stores it in the appropriate structure.
     */
    private void processLine(String line) {
        String[] parts = line.split(delimiter);
        
        // Trim extra spaces for each part
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();  // Remove extra spaces before or after the string
        }

        if (parts.length < 3) {
            System.out.println("Malformed line: " + line);  // Log the malformed line for debugging
            return;
        }
        
        String term = parts[0];
        String statement = parts[1];
        double confidence;  // Changed to double for correct confidence value handling
        try {
            confidence = Double.parseDouble(parts[2]);  // Changed from Integer to Double
        } catch (NumberFormatException e) {
            System.out.println("Invalid confidence value in line: " + line);  // Log invalid confidence
            return;
        }

        StatementEntry entry = new StatementEntry(term, statement, confidence);
        if (useBST) {
            bst.insert(entry);  // Store in BST
        } else {
            storeProcessedEntries(entry);  // Store in array
        }
    }

    /**
     * Stores a StatementEntry in the array.
     */
    private void storeProcessedEntries(StatementEntry entry) {
        for (int i = 0; i < entryCount; i++) {
            if (entries[i].getTerm().equals(entry.getTerm())) {
                if (entry.getConfidence() > entries[i].getConfidence()) {
                    entries[i] = entry;
                }
                return;
            }
        }
        // Add the new entry if it's not already in the array
        if (entryCount < MAX_SIZE) {
            entries[entryCount++] = entry;
        }
    }

    /**
     * Returns the status of file processing.
     */
    public String returnProcessingStatus() {
        return fileStatusMessage;
    }

    /**
     * Returns the processed array of StatementEntry objects.
     */
    public StatementEntry[] getEntries() {
        return useBST ? bst.toArray() : Arrays.copyOf(entries, entryCount);
    }

    /**
     * Returns the BST (only if using BST mode).
     */
    public SearchBST getBST() {
        return useBST ? bst : null;
    }
}
