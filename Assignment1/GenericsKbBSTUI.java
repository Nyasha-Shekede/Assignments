import java.util.Scanner;

public class GenericsKbBSTUI {
    private SearchBST searchBST;
    private FileProcessor fileProcessor;
    private Scanner scanner;

    public GenericsKbBSTUI() {
        this.searchBST = new SearchBST();
        this.fileProcessor = new FileProcessor(searchBST);
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Welcome to Generics Knowledge Base (BST Version)");
        boolean running = true;
        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. Load data from file");
            System.out.println("2. Search for a statement");
            System.out.println("3. Update a statement");
            System.out.println("4. Display all statements");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    loadData();
                    break;
                case 2:
                    searchStatement();
                    break;
                case 3:
                    updateStatement();
                    break;
                case 4:
                    displayAllStatements();
                    break;
                case 5:
                    running = false;
                    System.out.println("Exiting program...");
                    break;
                default:
                   4 System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private void loadData() {
        System.out.print("Enter filename: ");
        String filename = scanner.nextLine();
        if (fileProcessor.readFile(filename)) {
            System.out.println("Data loaded successfully into BST.");
        } else {
            System.out.println("Failed to load data.");
        }
    }

    private void searchStatement() {
        System.out.print("Enter term to search: ");
        String term = scanner.nextLine();
        StatementEntry result = searchBST.search(term);
        if (result != null) {
            System.out.println("Statement found in BST: " + result);
        } else {
            System.out.println("Statement not found.");
        }
    }

    private void updateStatement() {
        System.out.print("Enter term to update: ");
        String term = scanner.nextLine();
        
        StatementEntry entry = searchBST.search(term);
        if (entry == null) {
            System.out.println("Statement not found in BST.");
            return;
        }

        System.out.print("Enter new statement: ");
        String newStatement = scanner.nextLine();
        entry.setStatement(newStatement); // Update statement

        System.out.println("Statement updated successfully.");
    }

    private void displayAllStatements() {
        System.out.println("Statements in BST:");
        searchBST.inOrderTraversal();
    }

    public static void main(String[] args) {
        GenericsKbBSTUI app = new GenericsKbBSTUI();
        app.start();
    }
}
