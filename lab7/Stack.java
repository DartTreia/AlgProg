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
    public static Stack stackPop(Stack stack){
        if(stack == null) {
            return null;
        }
        if(stack.next == null) {
            stack.head=null;
            return stack;
        }

        Stack temp = stack;
        while(temp.next.next != null){
            temp = temp.next;
        }
        Stack returnTmp = temp.next;
        temp.next = null;
        stack.head = temp;
        return returnTmp;
    }
    public static Stack stackPush(Stack stack, Vertex vert){
        if(stack == null) {
            Stack temp = new Stack(vert, null);
            temp.head = temp;
            return temp;
        }
        Stack temp = stack;
        temp.head = temp;
        while(temp.next != null){
            temp = temp.next;
        }
        Stack tmpStack = new Stack(vert,null);
        temp.next = tmpStack;
        return stack;
    }
    public static Stack stackPush(Stack stack, int index){
        if(stack == null) {
            Stack temp = new Stack(index, null);
            temp.head = temp;
            return temp;
        }
        Stack temp = stack;
        temp.head = temp;
        while(temp.next != null){
            temp = temp.next;
        }
        Stack tmpStack = new Stack(index,null);
        temp.next = tmpStack;
        return stack;
    }
}
