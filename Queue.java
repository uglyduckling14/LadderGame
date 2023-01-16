public class Queue<E> {
    class Qnode{
        E key;
        Qnode next;
        public Qnode(E key){
            this.key = key;
            this.next = null;
        }
    }
    Qnode begin;
    Qnode end;
    public Queue(){
        this.begin = null;
        this.end = null;
    }
    public void enqueue(E value){
        Qnode current = new Qnode(value);
        if(isEmpty()){
            //System.out.println("s");
            this.begin = current;
            this.end = current;
        }else{
            this.end.next = current;
            this.end = current;
        }
    }
    public E dequeue(){//removes first returns last
        if(this.begin==null){
            return null;
        }
        Qnode current = this.begin;
        this.begin = this.begin.next;
        System.out.println(current.key);
        return current.key;
    }
    public boolean isEmpty(){
        return this.end == null;
    }
}

