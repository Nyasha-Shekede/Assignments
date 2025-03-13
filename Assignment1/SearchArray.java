import java.util.Arrays;

public class SearchArray {
    private StatementEntry[] entries;
    private int entryCount;

    public SearchArray(StatementEntry[] entries, int entryCount) {
        this.entries = Arrays.copyOf(entries, entryCount); // Copy only valid entries
        this.entryCount = entryCount;
    }

    /**
     * Performs a linear search for an exact term match.
     */
    public StatementEntry searchByTerm(String term) {
        for (int i = 0; i < entryCount; i++) {
            if (entries[i].getTerm().equals(term)) {
                return entries[i];
            }
        }
        return null; // Not found
    }

    /**
     * Searches for terms that contain the given partial term.
     */
    public StatementEntry[] searchByPartialTerm(String partialTerm) {
        StatementEntry[] results = new StatementEntry[entryCount];
        int count = 0;

        for (int i = 0; i < entryCount; i++) {
            if (entries[i].getTerm().contains(partialTerm)) {
                results[count++] = entries[i];
            }
        }
        return Arrays.copyOf(results, count); // Return only valid matches
    }

    /**
     * Finds and returns the StatementEntry with the highest confidence score.
     */
    public StatementEntry getHighestConfidenceEntry() {
        if (entryCount == 0) return null;
        
        StatementEntry maxEntry = entries[0];
        for (int i = 1; i < entryCount; i++) {
            if (entries[i].getConfidence() > maxEntry.getConfidence()) {
                maxEntry = entries[i];
            }
        }
        return maxEntry;
    }

    /**
     * Returns the top N entries sorted by confidence score in descending order.
     */
    public StatementEntry[] getTopNConfidenceEntries(int n) {
        StatementEntry[] sortedEntries = Arrays.copyOf(entries, entryCount);
        Arrays.sort(sortedEntries, (a, b) -> Double.compare(b.getConfidence(), a.getConfidence())); // Compare confidence as doubles
        return Arrays.copyOf(sortedEntries, Math.min(n, entryCount));
    }
}
