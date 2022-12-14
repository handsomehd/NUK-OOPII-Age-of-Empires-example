//Description : the class in order to implement LinkedList.
public class A1083322_checkpoint7_Node{
    private A1083322_checkpoint7_Node Next;
    // axis: 0 --> x
    //       1 --> y
    // e.g. axis = 0, direction = +1 --> right
    //      axis = 0, direction = -1 --> left
    //      axis = 1, direction = +1 --> down
    //      axis = 1, direction = -1 --> up
    private int direction; 
    private int axis;
    public A1083322_checkpoint7_Node(int direction, int axis){
        this.direction = direction;
        this.axis = axis;
        this.Next = null;
    }
    public void setDirection(int direction){
        //TODO(6) set the direction via this function.
        this.direction = direction;
    }
    public int getDirection(){
        //TODO(6) get the direction via this function.
        return direction;
    }
    public void setAxis(int axis){
        //TODO(6) set the axis via this function.
        this.axis = axis;
    }
    public int getAxis(){
        //TODO(6) get the axis via this function.
        return axis;
    }
    public void setNext(A1083322_checkpoint7_Node Next){
        //TODO(6) set the Next via this function.
        this.Next = Next;
    }
    public A1083322_checkpoint7_Node getNext(){
        //TODO(6) get the Next via this function.
        return Next;
    }
} 
