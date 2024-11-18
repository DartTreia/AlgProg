public class Stack {
    Vertex vert;
    int index;
    Stack next;
    Stack head;
    public Stack(Vertex vert, Stack next) {
        head=this;
        this.vert = vert;
        this.next = next;
    }
    public Stack(int index, Stack next){
        head=this;
        this.index=index;
        this.next=next;
    }
    public  Stack stackPop(){
        if(this.next == null) {
            this.head=null;
            return this;
        }

        Stack temp = this;
        while(temp.next.next != null){
            temp = temp.next;
        }
        Stack returnTmp = temp.next;
        temp.next = null;
        this.head = temp;
        return returnTmp;
    }
    public Stack stackPush(Vertex vert){
        if(this.vert == null) {
            Stack temp = new Stack(vert, null);
            temp.head = temp;
            return temp;
        }
        Stack temp = this;
        temp.head = temp;
        while(temp.next != null){
            temp = temp.next;
        }
        Stack tmpStack = new Stack(vert,null);
        temp.next = tmpStack;
        return this;
    }
    public  Stack stackPush(int index){
        if(this.index == 0) {
            Stack temp = new Stack(index, null);
            temp.head = temp;
            return temp;
        }
        Stack temp = this;
        temp.head = temp;
        while(temp.next != null){
            temp = temp.next;
        }
        Stack tmpStack = new Stack(index,null);
        temp.next = tmpStack;
        return this;
    }
}
