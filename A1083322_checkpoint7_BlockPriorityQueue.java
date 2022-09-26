import java.util.*;

public class A1083322_checkpoint7_BlockPriorityQueue implements A1083322_checkpoint7_Fringe {
    PriorityQueue<A1083322_checkpoint7_Block> priorityQueue;
    //Description : the constuctor of BlockPriorityQueue.
    
    public A1083322_checkpoint7_BlockPriorityQueue(Comparator<A1083322_checkpoint7_Block> c){
        //TODO(5)
        priorityQueue = new PriorityQueue<A1083322_checkpoint7_Block>(c);
    }
    public void add(A1083322_checkpoint7_Block block){
        //TODO(5)
        priorityQueue.offer(block);
    }
    public A1083322_checkpoint7_Block remove(){
        //TODO(5)
        return priorityQueue.poll();
    }
    public boolean isEmpty(){
        //TODO(5)
        return priorityQueue.isEmpty();
    }
}