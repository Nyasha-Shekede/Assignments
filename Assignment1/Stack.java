import java.util.Stack;

public class StackExample {
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        
        // Push elements
        stack.push(10);
        stack.push(20);
        stack.push(30);
        
        // Peek at the top element
        System.out.println("Top element: " + stack.peek());
        
           }
}
