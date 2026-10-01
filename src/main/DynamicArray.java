package main;

import static java.lang.Math.ceil;
import static java.lang.Math.max;


public class DynamicArray {


    private String[] data;
    private int capacity;
    private int size;


    // Constructor with specified initial capacity
    public DynamicArray(int capacity){
        data = new String[capacity];
        size = 0;
    }


    // Default constructor
    public DynamicArray(){
        this(10);
    }


    // Get the value at a specific index
    public String get(int index) {
        checkIndex(index);
        return data[index];
    }


    // Set the value at a specific index
    public void set(int index, String value) {
        checkIndex(index);
        data[index] = value;
    }


    // Append a new value to the end
    public void append(String value) {
        ensureCapacity();
        data[size] = value;
        size++;
    }

    public int size(){
        return size;
    }


    // Insert value at given index and shift other elements
    public void insert(int index, String value) {
        ensureCapacity();
        for (int i = size; i > index; --i) {
            data[i] = data[i-1];
        }
        data[index] = value;
        size++;
    }


    //delete value at specified index and shift all other elements
    public void pop(int index){
        checkIndex(index);
        size = size - 1;
        for (int i = index; i < size; ++i) {
            data[i] = data[i+1];
        }
    }


    // Ensure there is room for another element; grow if needed
    private void ensureCapacity() {
        // Do this first; you'll need it for some of the remaining functions
        if (size < capacity) {
            return;
        }
        // Grow by 50% percent, but if capacity is 0 make sure to add at least 1
        capacity = max((int)ceil(capacity * 1.5), 1);
        String[] newdata = new String[capacity];
        for (int i = 0; i < size; ++i) {
            newdata[i] = data[i];
        }
        data = newdata;
    }


    // Helper to check bounds
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for size " + size);
        }
    }


    public static void main(String[] args) {
        // Not a comprehensive test script.  Could give you the expected answer even if you have some errors.
        // Feel free to add to this.
        System.out.println("Testing Dynamic Array");
        DynamicArray testArray = new DynamicArray(2);
        testArray.insert(0, "A");
        testArray.insert(0, "B");
        testArray.insert(0, "C");
        System.out.println("Should contain CBA, in that order.");
        for (int i = 0; i < testArray.size; i++){
            System.out.println("Index=" + i + ", Value=" +  testArray.get(i));
        }
        System.out.println();
        testArray.pop(0);
        for (int i = 0; i < testArray.size; i++){
            System.out.println("Index=" + i + ", Value=" +  testArray.get(i));
        }
    }




}
