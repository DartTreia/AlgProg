public class MyQueue {
    Vertex vert;
    int index;
    MyQueue next;
    MyQueue head;
    public MyQueue(Vertex vert, MyQueue next) {
        head=this;
        this.vert = vert;
        this.next = next;
    }
    public MyQueue(int index, MyQueue next){
        head=this;
        this.index=index;
        this.next=next;
    }
    public MyQueue QueuePop(){
        if(this.next == null) {
            this.head=null;
            return this;
        }
        else {
            this.head=this.next;
            return this;
        }
    }
    public MyQueue QueuePush(Vertex vert){
        if(this.vert == null) {
            MyQueue temp = new MyQueue(vert, null);
            temp.head = temp;
            return temp;
        }
        MyQueue temp = this;
        temp.head = temp;
        while(temp.next != null){
            temp = temp.next;
        }
        MyQueue tmpQueue = new MyQueue(vert,null);
        temp.next = tmpQueue;
        return this;
    }
    public MyQueue QueuePush(int index){
        if(this.index == 0) {
            MyQueue temp = new MyQueue(index, null);
            temp.head = temp;
            return temp;
        }
        MyQueue temp = this;
        temp.head = temp;
        while(temp.next != null){
            temp = temp.next;
        }
        MyQueue tmpQueue = new MyQueue(index,null);
        temp.next = tmpQueue;
        return this;
    }
}
