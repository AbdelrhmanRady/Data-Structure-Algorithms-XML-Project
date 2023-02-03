package validation;



public class ArrStack {
    private Object[] data;
    private int size=0;
    private int index=0;

    public ArrStack() {
        this.size = 100;
        data = new Object[this.size];
    }
    public void push(String o) {
        if (index >= size) {
            this.increaseSize();
        }
        this.data[index] = o;
        index++;
    }

    public Object pop() {
        if (index != 0) {
            Object obj = data[index - 1];
            this.data[index - 1] = null;
            index--;
            return obj;
        } else {
            return null;
        }
    }

    public Object top() throws RuntimeException {
        if (index != 0) {
            return this.data[index - 1];
        } else {
            return null;
        }
    }

    public boolean isEmpty() {
        return index == 0;
    }

    private void increaseSize() {
        Object[] temp = new Object[size];
        size = size * 2;
        for (int i = 0; i < index; i++) {
            temp[i] = this.data[i];
        }
        this.data = new Object[this.size];
        for (int i = 0; i < index; i++) {
            this.data[i] = temp[i];
        }
    }
}
