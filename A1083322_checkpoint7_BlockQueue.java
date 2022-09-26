import java.util.*;

public class A1083322_checkpoint7_BlockQueue implements A1083322_checkpoint7_Fringe {
    Queue<A1083322_checkpoint7_Block> queue;
    //Description : the constuctor of BlockQueue.
    public A1083322_checkpoint7_BlockQueue(){
        //The TODO(4)
        queue = new LinkedList<A1083322_checkpoint7_Block>();
    }
    //Description : add the input object into Fringe
    public void add(A1083322_checkpoint7_Block block){
        //The TODO(4)
        queue.add(block);
    }
    //Description : return and remove the object from Fringe.
    public A1083322_checkpoint7_Block remove(){
        //The TODO(4)
        if( queue.isEmpty() )
            return null;
        else
            return queue.remove();
    }
    //Description : Check if the Fringe has a object at least. if it is empty return true, vice versa. 
    public boolean isEmpty(){
        //The TODO(4)
        return queue.isEmpty();

    }
}