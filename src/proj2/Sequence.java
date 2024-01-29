package proj2;
/**
 * An abstract data type. is a sequence of strings.
 * @author Jack Lynch
 * @version 1/19/2024
 * 
 */
public class Sequence
{
    /**
     * Creates a new sequence with initial capacity 10.
     */
    private final int INITIALCAPACITY = 10;
    private final int NO_ELEMENT = -1;
    private int pointer; // the current element. when not pointing at anything set to -1
    private int elements; // how many elements in the array are filled? 
    private String[] data;

    public Sequence() {
    	// REMOVE THIS:
    	//
    	// capacity is reflected in the length of the 
    	// internal array
        data = new String[INITIALCAPACITY];

        elements = 0; 
        pointer = NO_ELEMENT;
    }
    

    /**
     * Creates a new sequence.
     * 
     * @param initialCapacity the initial capacity of the sequence.
     */
    public Sequence(int initialCapacity){
        if (initialCapacity >= 0) data = new String[initialCapacity];
        else throw new IllegalArgumentException("Initial Capacity is Negative: " + initialCapacity);

        elements = 0;
        pointer = NO_ELEMENT;
    	//
    	// capacity is reflected in the length of the 
    	// internal array
    }
    


    /**
     * Adds a string to the sequence in the location before the
     * current element. If the sequence has no current element, the
     * string is added to the beginning of the sequence.
     *
     * The added element becomes the current element.
     *
     * If the sequences's capacity has been reached, the sequence will
     * expand to twice its current capacity plus 1.
     *
     * @param value the string to add.
     */
    public void addBefore(String value)
    {
        if (size() == getCapacity()) {
            doubleCapacity();
        }

        if (size() == 0){ // if there are no elements, start at the beginning
            pointer = 0;
            data[pointer]=value;
        }

        else{
            if (isCurrent()){ // there are elements, and this is a element in the sequence
                if (data[0] == null){
                    pointer -= 1;
                    moveDown(getStart() - 1, pointer);
                    data[pointer] = value;
                }
                else {
                    moveUp(pointer, getStart()+size());
                    data[pointer] = value;

                }
            }
            else{ // there are elements, pointer is -1. find the start of the sequence and insert it there.
                start();
                if (pointer == 0) moveUp(pointer, getStart()+size());
                else {
                    pointer -= 1;
                }
                data[pointer]=value;
            }
        }

        elements += 1;
    }

    /**
     * Adds a string to the sequence in the location after the current
     * element. If the sequence has no current element, the string is
     * added to the end of the sequence.
     *
     * The added element becomes the current element.
     *
     * If the sequences's capacity has been reached, the sequence will
     * expand to twice its current capacity plus 1.
     *
     * @param value the string to add.
     */
    public void addAfter(String value)
    {
        if (size() == getCapacity()) {
            doubleCapacity();
        }

        if (size() == 0){ // if there are no elements, add at the end
            pointer = getCapacity()-1;
            data[pointer]=value;
        }

        else{
            if (isCurrent()){ // there are elements, and the current element is an element in the sequence
                if (data[0] == null){
                    moveDown(getStart() - 1, pointer);
                    data[pointer] = value;
                }
                else {
                    pointer +=1;
                    moveUp(pointer, getStart()+size());
                    data[pointer] = value;
                }
            }
            else{ // there are elements, pointer is -1. find the start of the sequence and insert it there.
                end();
                if (pointer == getCapacity() -1) moveDown(getStart()-1, getCapacity() - 1);
                else {
                    pointer += 1;
                    data[pointer]=value;
                }
            }
        }
        elements += 1;
    }
    /**
     * helper method
     * moves the desired section of data up by 1.
     * @param indexStart the start of the index.
     * @param indexEnd the end of the index. assumed empty.
     */
    private void moveUp(int indexStart, int indexEnd){ // 0, 1
        for (int i = indexEnd-1; i >= indexStart; i--){
            data[i+1] = data[i];
        }
        data[indexStart] = null;
    }

    /**
     * helper method
     * moves the desired section of data down by 1.
     * @param indexStart the start of the index. assummed empty
     * @param indexEnd the end of the index.
     */
    private void moveDown(int indexStart, int indexEnd){
        for (int i = indexStart; i < indexEnd; i++){
            data[i] = data[i+1];
        }
        data[indexEnd] = null;
    }


    /**
     * @return true if and only if the sequence has a current element.
     */
    public boolean isCurrent()
    {
        if (getCurrent() != null) return true;
        else return false;
    }
    
    
    /**
     * @return the capacity of the sequence.
     */
    public int getCapacity()
    {
        return this.data.length;
    }

    
    /**
     * @return the element at the current location in the sequence, or
     * null if there is no current element.
     */
    public String getCurrent()
    {
        if (pointer == NO_ELEMENT) return null;
        else return data[pointer];
    }
    

    /**
     * Increase the sequence's capacity to be
     * at least minCapacity.  Does nothing
     * if current capacity is already >= minCapacity.
     *
     * @param minCapacity the minimum capacity that the sequence
     * should now have.
     */
    public void ensureCapacity(int minCapacity)
    {
        if (getCapacity() < minCapacity) {
            String[] newData = new String[minCapacity];
            for (int i = 0; i< this.data.length; i++){
                newData[i]=this.data[i];
            }
            data = newData;
        }
    }

    /**
     * helper method
     * Doubles the capacity, and adds 1. 
     * Used by addAfter and addBefore.
     */
    private void doubleCapacity(){
        ensureCapacity(getCapacity()*2+1);
    }
    
    /**
     * Places the contents of another sequence at the end of this sequence.
     *
     * If adding all elements of the other sequence would exceed the
     * capacity of this sequence, the capacity is changed to make room for
     * all of the elements to be added.
     * 
     * Postcondition: NO SIDE EFFECTS!  the other sequence should be left
     * unchanged.  The current element of both sequences should remain
     * where they are. (When this method ends, the current element
     * should refer to the same element that it did at the time this method
     * started.)
     *
     * @param another the sequence whose contents should be added.
     */
    public void addAll(Sequence another)
    {
        if (!isCurrent()) this.pointer = NO_ELEMENT;
        int anotherOriginalPointer = another.pointer;
        int thisOriginalPointer = this.pointer;
        ensureCapacity(another.size() + this.size());
        another.start();
        this.end();
        for (int i = 0; i < another.size(); i++){
            this.addAfter(another.getCurrent());
            another.advance();
        }
        
        another.pointer = anotherOriginalPointer;
        this.pointer = thisOriginalPointer;
    }

    
    /**
     * Move forward in the sequence so that the current element is now
     * the next element in the sequence.
     *
     * If the current element was already the end of the sequence,
     * then advancing causes there to be no current element.
     *
     * If there is no current element to begin with, do nothing.
     */
    public void advance()
    {
        if (pointer != NO_ELEMENT){
            pointer += 1;
            if (pointer >= getCapacity()) pointer = NO_ELEMENT;
        }
    }

    
    /**
     * Make a copy of this sequence.  Subsequence changes to the copy
     * do not affect the current sequence, and vice versa.
     * 
     * Postcondition: NO SIDE EFFECTS!  This sequence's current
     * element should remain unchanged.  The clone's current
     * element will correspond to the same place as in the original.
     *
     * @return the copy of this sequence.
     */
    public Sequence clone()
    {
        Sequence newSequence = new Sequence(this.getCapacity());
        newSequence.addAll(this);
        System.out.println(pointer);
        newSequence.pointer = newSequence.getCapacity()-newSequence.size()+pointer;
        return newSequence;
    }
   
    
    /**
     * Remove the current element from this sequence.  The following
     * element, if there was one, becomes the current element.  If
     * there was no following element (current was at the end of the
     * sequence), the sequence now has no current element.
     *
     * If there is no current element, does nothing.
     */
    public void removeCurrent()
    {
        if (isCurrent()){
            moveDown(pointer, getStart()+size()-1);
            elements -=1;
        }
        if (isEmpty()) pointer = NO_ELEMENT;
    }

    
    /**
     * @return the number of elements stored in the sequence.
     */
    public int size()
    {
        return elements;
    }

    
    /**
     * Sets the current element to the start of the sequence.  If the
     * sequence is empty, the sequence has no current element.
     */
    public void start()
    {
        if (size() != 0) {
            pointer = 0;
            while (!isCurrent() && pointer < getCapacity()) {
                advance();
            }
        }
        else pointer = NO_ELEMENT;
    }

    /**
     * helper method.
     * @return the integer index where the sequence starts in data
     */
    private int getStart(){
        int originalPointer = pointer;
        start();
        int returnPointer = pointer;
        pointer = originalPointer;
        return returnPointer;
    }

    /**
     * helper method.
     * moves the pointer to the end of the sequence
     */
    private void end()
    {
        if (size() != 0) {
            pointer = getCapacity()-1;
            while (!isCurrent() && pointer > 0) {
                pointer -= 1;
            }
        }
        else pointer = NO_ELEMENT;
    }

    /**
     * helper method.
     * @return the integer index where the sequence ends in data
     */
    private int getEnd(){
        int originalPointer = pointer;
        end();
        int returnPointer = pointer;
        pointer = originalPointer;
        return returnPointer;
    }
    
    /**
     * Reduce the current capacity to its actual size, so that it has
     * capacity to store only the elements currently stored.
     */
    public void trimToSize()
    {
        int originalPointer = pointer - getStart();

        String[] newData = new String[size()];
        int newPointer = 0;

        start();
        for (int i = 0; i < getCapacity(); i++){
            if(isCurrent()) {
                newData[newPointer] = getCurrent();
                newPointer++;
            }
            advance();
            
        }
        data = newData;
        pointer = originalPointer;
    }
    
    
    /**
     * Produce a string representation of this sequence.  The current
     * location is indicated by a >.  For example, a sequence with "A"
     * followed by "B", where "B" is the current element, and the
     * capacity is 5, would print as:
     * 
     *    {A, >B} (capacity = 5)
     * 
     * The string you create should be formatted like the above example,
     * with a comma following each element, no comma following the
     * last element, and all on a single line.  An empty sequence
     * should give back "{}" followed by its capacity.
     * 
     * @return a string representation of this sequence.
     */
    public String toString() 
    {
        int originalPointer = pointer;
        start();
        String returnString = "{";
        for (int i = 0; i < getCapacity(); i++){

            if (isCurrent()){
                if (i!= 0) returnString = returnString + ", ";
                if (pointer == originalPointer) returnString = returnString + ">"; 
                returnString = returnString + getCurrent();
            }
            advance();
        }
        returnString = returnString + "} (capacity = " + getCapacity() + ")";
        pointer = originalPointer;
        return returnString;
    }
    
    /**
     * Checks whether another sequence is equal to this one.  To be
     * considered equal, the other sequence must have the same size
     * as this sequence, have the same elements, in the same
     * order, and with the same element marked
     * current.  The capacity can differ.
     * 
     * Postcondition: NO SIDE EFFECTS!  this sequence and the
     * other sequence should remain unchanged, including the
     * current element.
     * 
     * @param other the other Sequence with which to compare
     * @return true iff the other sequence is equal to this one.
     */
    public boolean equals(Sequence other) 
    {
        if (this.size() != other.size()) return false;
        if ((this.pointer-this.getStart()) != (other.pointer-other.getStart())) return false;
        int thisOriginalPointer = pointer;
        int otherOriginalPointer = other.pointer;
        
        this.start();
        other.start();
        for(int i = 0; i < this.size(); i++){
            if (this.getCurrent() != other.getCurrent()) {
                this.pointer = thisOriginalPointer;
                other.pointer = otherOriginalPointer;
                return false;
            }
            this.advance(); other.advance();
        }
        this.pointer = thisOriginalPointer;
        other.pointer = otherOriginalPointer;
        return true;
    }
    
    
    /**
     * 
     * @return true if Sequence empty, else false
     */
    public boolean isEmpty()
    {
        if (size() == 0) return true;
        return false;
    }
    
    
    /**
     *  empty the sequence.  There should be no current element.
     */
    public void clear()
    {
        pointer = NO_ELEMENT;
        data = new String[getCapacity()];
        elements = 0;
    }

}