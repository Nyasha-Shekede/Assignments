import java.util.Scanner;

public class GenericsKbArrayUI {
    private FileProcessor fileProcessor;
    private SearchArray searchArray;
    private EntryManager entryManager;
    private Scanner scanner;

    public GenericsKbArrayUI() {
        fileProcessor = new FileProcessor();
        scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Welcome to GenericsKbArrayApp!");
        boolean running = true;
        
        while (running) {
            displayMenu();
            int choice = getUserChoice();
            
            switch (choice) {
                case 1:
                    loadKnowledgeBase();
                    break;
                case 2:
                    addStatement();
                    break;
                case 3:
                    searchByTerm();
                    break;
                case 4:
                    searchByTermAndSentence();
                    break;
                case 5:
                    System.out.println("Thank you for using GenericsKbArrayApp!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Load a knowledge base from a file");
        System.out.println("2. Add a new statement to the knowledge base");
        System.out.println("3. Search for a statement by term");
        System.out.println("4. Search for a statement by term and sentence");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private int getUserChoice() {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private void loadKnowledgeBase() {
    System.out.print("Enter file name: ");
    String filename = scanner.next();
    
    if (fileProcessor.readFile(filename)) {
        StatementEntry[] entries = fileProcessor.getEntries();
        if (entries == null || entries.length == 0) {
            System.out.println("No entries found in file.");
            return;
        }
        searchArray = new SearchArray(entries, entries.length);
        entryManager = new EntryManager(entries, entries.length);
        System.out.println("File loaded successfully!");
    } else {
        System.out.println("Failed to load file: " + fileProcessor.returnProcessingStatus());
    }
}

    private void addStatement() {
        System.out.print("Enter term: ");
        String term = scanner.next();
        
        System.out.print("Enter statement: ");
        scanner.nextLine();  // Consume leftover newline
        String statement = scanner.nextLine();
        
        System.out.print("Enter confidence score: ");
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid input. Please enter a valid confidence score: ");
            scanner.next();
        }
        double confidence = scanner.nextDouble();  // Change to double
        
        if (entryManager != null && entryManager.addOrUpdateEntry(term, statement, confidence)) {
            System.out.println("Statement added/updated successfully!");
        } else {
            System.out.println("Failed to add/update statement.");
        }
    }

    private void searchByTerm() {
    if (searchArray == null) {
        System.out.println("No knowledge base loaded. Please load a file first.");
        return;
    }
    
    System.out.print("Enter term to search: ");
    scanner.nextLine();  // Consume newline
    String term = scanner.nextLine(); // Allows multi-word terms
    
    StatementEntry result = searchArray.searchByTerm(term);
    if (result != null) {
        System.out.println("Found: " + result);
    } else {
        System.out.println("No matching statement found.");
    }
}
    private void searchByTermAndSentence() {
        if (entryManager == null) {
            System.out.println("No knowledge base loaded. Please load a file first.");
            return;
        }
        
        System.out.print("Enter term: ");
        String term = scanner.next();
        
        System.out.print("Enter sentence: ");
        scanner.nextLine();  // Consume leftover newline
        String sentence = scanner.nextLine();
        
        Double confidence = entryManager.searchWithSentence(term, sentence);  // Changed to Double
        if (confidence != null) {
            System.out.println("Confidence score: " + confidence);
        } else {
            System.out.println("No matching entry found.");
        }
    }

    public static void main(String[] args) {
        GenericsKbArrayUI ui = new GenericsKbArrayUI();
        ui.start();
    }
}
