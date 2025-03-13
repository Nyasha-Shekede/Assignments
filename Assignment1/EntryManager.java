import java.util.Arrays;

public class EntryManager {
    private StatementEntry[] entries;
    private int entryCount;
    private static final int MAX_SIZE = 100_000;

    public EntryManager(StatementEntry[] initialEntries, int count) {
        this.entries = Arrays.copyOf(initialEntries, MAX_SIZE);
        this.entryCount = count;
    }

    /**
     * Adds a new entry or updates an existing one if the term already exists.
     */
    public boolean addOrUpdateEntry(String term, String statement, double confidence) {
        for (int i = 0; i < entryCount; i++) {
            if (entries[i].getTerm().equals(term)) {
                if (confidence > entries[i].getConfidence()) {
                    entries[i] = new StatementEntry(term, statement, confidence);
                    return true; // Updated existing entry
                }
                return false; // No update needed
            }
        }

        // Allow adding new entries if there's space
        if (entryCount < MAX_SIZE) {
            entries[entryCount++] = new StatementEntry(term, statement, confidence);
            return true;
        }

        return false; // No room for new entries
    }

    /**
     * Searches for a term and returns the statement with the highest confidence.
     */
    public StatementEntry search(String term) {
        for (int i = 0; i < entryCount; i++) {
            if (entries[i].getTerm().equals(term)) {
                return entries[i]; // Return the entry if found
            }
        }
        return null; // Term not found
    }

    /**
     * Searches for a term AND sentence, returning confidence score if found.
     */
    public Double searchWithSentence(String term, String sentence) {
        for (int i = 0; i < entryCount; i++) {
            if (entries[i].getTerm().equals(term) && entries[i].getStatement().equals(sentence)) {
                return entries[i].getConfidence(); // Return confidence score
            }
        }
        return null; // Not found
    }

    /**
     * Returns all stored entries (excluding null values).
     */
    public StatementEntry[] getEntries() {
        return Arrays.copyOf(entries, entryCount); // Only return valid entries
    }
}
