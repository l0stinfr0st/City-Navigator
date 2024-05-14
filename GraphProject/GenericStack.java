package GraphProject;

public class GenericStack<Item> {

    private Item[] a;  
    private int top;     

    public GenericStack(int capacity) {
        a = (Item[]) new Object[capacity];  
    }

    public boolean isEmpty() {
        return top == 0;
    }

    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        System.arraycopy(a, 0, temp, 0, top);
        a = temp;
    }

    public void push(Item item) {
        if (top == a.length) {
            resize(2 * a.length);
        }
        a[top++] = item;
    }

    public Item pop() {
        return a[--top];
    }

    @Override
    public String toString() {
        if (!isEmpty()) {
            int x = top;
            String str = "";
            while (x > 0) {
                x--;
                str += a[x].toString() + "\n";
            }
            return str;
        }
        return "Stack is empty!";
    }

 
   

    
}

