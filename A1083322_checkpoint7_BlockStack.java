import java.util.*;

public class A1083322_checkpoint7_BlockStack implements A1083322_checkpoint7_Fringe {
    Stack<A1083322_checkpoint7_Block> stack;
    //Description : the constuctor of BlockQueue.
    public A1083322_checkpoint7_BlockStack(){
        //The TODO(3)
        stack = new Stack<A1083322_checkpoint7_Block>();
    }
    public void add(A1083322_checkpoint7_Block block){
        //The TODO(3)
        stack.add(block);
    }
    public A1083322_checkpoint7_Block remove(){
        //The TODO(3)
        return stack.pop();
    }
    public boolean isEmpty(){
        //The TODO(3)
        return stack.empty();
    }
}