public class A1083322_checkpoint7_RouteLinkedList{
    private A1083322_checkpoint7_Node head;
    //Description : the constructor of leading the head Node as null.
    public A1083322_checkpoint7_RouteLinkedList(){
        this.head = null;
    }
    //Description : the constructor of input a Node as the head node.
    public A1083322_checkpoint7_RouteLinkedList(A1083322_checkpoint7_Node head){
        this.head = head;
    }
    public void delete(int axis, int direction){
        /**** TODO 7 ****/
        //若刪除頭
        if( head.getAxis()==axis && head.getDirection()==direction )
        {
            head = head.getNext();
        } 

        A1083322_checkpoint7_Node previous = head;
        A1083322_checkpoint7_Node current = head.getNext();

        while(current != null)
        {
            if( current.getAxis()==axis && current.getDirection()==direction )
                break;
            previous = previous.getNext();
            current = current.getNext();
        }
        previous.setNext(current.getNext());
        current.setNext(null);
    }

    public A1083322_checkpoint7_Node search(int axis, int direction){ 
        /**** TODO 8 ****/
        A1083322_checkpoint7_Node current = head;
        while(current != null)
        {
            if( current.getAxis()==axis && current.getDirection()==direction )
                break;
            current = current.getNext();
        }
        return current;
    }
    public void insert(int referenceAxis, int referenceDirection, int axis, int direction){ 
        /**** TODO 9 ****/
        A1083322_checkpoint7_Node current = head;
        while(current != null)
        {
            if( current.getAxis()==axis && current.getDirection()==direction )
            {
                A1083322_checkpoint7_Node newNode = new A1083322_checkpoint7_Node(direction, axis);
                newNode.setNext(current.getNext());
                current.setNext(newNode);
                return;
            }
            current = current.getNext();
        }
        System.out.println("Insertion null");;

    }
    public int length(){
        /**** TODO 10 ****/
        if(head == null)
            return 0;
        int ans = 0;
        A1083322_checkpoint7_Node current = head;
        while(current != null)
        {
            current = current.getNext();
            ans = ans+1;
        }
        return ans-1;
        
    }
    public void append(int axis, int direction){
        A1083322_checkpoint7_Node current = head;
        A1083322_checkpoint7_Node newNode = new A1083322_checkpoint7_Node(direction,axis);
        if(head == null){
            head = newNode;
        }else {
            while(current.getNext() != null){
                current=current.getNext();
            }
            current.setNext(newNode);
        }
    }
    public A1083322_checkpoint7_Node getHead(){
        return this.head;
    }
    public void setHead(A1083322_checkpoint7_Node head){
        this.head = head;
    }
}
    

