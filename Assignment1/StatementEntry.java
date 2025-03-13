public class StatementEntry {
    private String term;
    private String statement;
    private double confidence;

    public StatementEntry(String term, String statement, double confidence) {
        this.term = term;
        this.statement = statement;
        this.confidence = confidence;
    }

    public String getTerm() {
        return term;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String newStatement) {
        this.statement = newStatement;
    }

    public double getConfidence() {
        return confidence;
    }

    @Override
    public String toString() {
        return term + ": " + statement + " (Confidence: " + confidence + ")";
    }
}
