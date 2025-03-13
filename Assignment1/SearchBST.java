public class SearchBST {
    private class Node {
        StatementEntry entry;
        Node left, right;

        Node(StatementEntry entry) {
            this.entry = entry;
            this.left = this.right = null;
        }
    }

    private Node root;
    private int size;

    public SearchBST() {
        root = null;
        size = 0;
    }

    public void insert(StatementEntry entry) {
        root = insertRecursive(root, entry);
    }

    private Node insertRecursive(Node root, StatementEntry entry) {
        if (root == null) {
            size++;
            return new Node(entry);
        }

        int compare = entry.getTerm().compareTo(root.entry.getTerm());

        if (compare < 0) {
            root.left = insertRecursive(root.left, entry);
        } else if (compare > 0) {
            root.right = insertRecursive(root.right, entry);
        } else if (entry.getConfidence() > root.entry.getConfidence()) {
            root.entry = entry;
        }

        return root;
    }

    public StatementEntry search(String term) {
        return searchRecursive(root, term);
    }

    private StatementEntry searchRecursive(Node root, String term) {
        if (root == null) return null;

        int compare = term.compareTo(root.entry.getTerm());
        if (compare == 0) return root.entry;
        if (compare < 0) return searchRecursive(root.left, term);
        return searchRecursive(root.right, term);
    }

    public void inOrderTraversal() {
        inOrderRecursive(root);
    }

    private void inOrderRecursive(Node root) {
        if (root != null) {
            inOrderRecursive(root.left);
            System.out.println(root.entry);
            inOrderRecursive(root.right);
        }
    }
}
