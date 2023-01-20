public class Queue<E> {
    class Qnode{
        E key;
        Qnode next;
        public Qnode(E key){
            this.key = key;
            this.next = null;
        }
    }
    Qnode begin = null;
    Qnode end = null;
    public Queue(){
        this.begin = null;
        this.end = null;
    }
    public void enqueue(E value){
        Qnode current = new Qnode(value);
        if(isEmpty()){
            //System.out.println("s");
            this.end = current;
            this.begin = current;
            return;
        }
        //System.out.println(current.key);
        this.end.next = current;
        this.end = current;
    }
    public E dequeue(){//removes first returns first
        if(this.begin==null){
            return null;
        }
        Qnode current = this.begin;
        this.begin = this.begin.next;
        //System.out.println(current.key);
        return current.key;
    }
    public int getSize(){
        Qnode temp = this.begin;
        int count = 0;
        while(temp != null){
            count++;
            temp = temp.next;
        }
        return count;
    }
    public boolean isEmpty(){
        return this.begin == null;
    }
}

