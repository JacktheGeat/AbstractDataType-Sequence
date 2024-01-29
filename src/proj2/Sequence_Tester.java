package proj2;

import org.junit.*;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import proj2.Sequence;

public class Sequence_Tester {
    @Rule // a test will fail if it takes longer than 1/10 of a second to run
 	public Timeout timeout = Timeout.millis(100); 

    private Sequence makeSequence( int initialCapacity) {
        Sequence newSequence;
        if (initialCapacity == -1) newSequence = new Sequence();
        else newSequence = new Sequence(initialCapacity);
        return newSequence;
    } 

    @Test
    public void test_zeroInitializer(){
        Sequence test = makeSequence(0);
        assertEquals("return an empty sequence", test.getCapacity(), 0);
    }

    @Test
    public void test_addBefore(){
        Sequence test = makeSequence(0);
        test.addBefore("1");
        test.addBefore("2");
        assertEquals("Adds 1 to the beginning, then adds 2 before 1. Capacity should be 3", "{>2, 1} (capacity = 3)", test.toString());

    }

    @Test
    public void test_addAfter(){
        Sequence test = makeSequence(0);
        test.addAfter("1");
        test.addAfter("2");
        assertEquals("Adds 1 to the end, then adds 2 after 1. Capacity should be 3", "{1, >2} (capacity = 3)", test.toString());
    }

    @Test
    public void test_isCurrent_False(){
        Sequence test = makeSequence(4);
        assertEquals("there is no current value. returns false.", false, test.isCurrent());
    }

    @Test
    public void test_isCurrent_True(){
        Sequence test = makeSequence(4);
        assertEquals("there is no current value. returns false.", false, test.isCurrent());
    }

    @Test
    public void test_getCapacity_empty(){
        Sequence test = makeSequence(0);
        assertEquals("capacity should be 0", 0, test.getCapacity());
    }

    @Test
    public void test_ensureCapacity_1(){
        Sequence test = makeSequence(0);
        test.ensureCapacity(30);
        assertEquals("capacity should be 30", 30, test.getCapacity());
    }

    @Test
    public void test_ensureCapacity_negative(){
        Sequence test = makeSequence(5);
        test.ensureCapacity(-5);
        assertEquals("capacity should be 5", 5, test.getCapacity());
    }

    @Test
    public void test_trimToSize(){
        Sequence test = makeSequence(30);
        test.trimToSize();
        assertEquals("capacity should be 0", 0, test.getCapacity());
        
    }

    @Test
    public void test_trimToSize_2(){
        Sequence test = makeSequence(10);
        test.addAfter("1");
        test.addBefore("2");
        test.addAfter("3");
        test.trimToSize();
        assertEquals("capacity should be trimmed to 3", 3, test.getCapacity());
        
    }

    @Test
    public void test_isEmpty_false(){
        Sequence test = makeSequence(10);
        test.addAfter("1");
        test.addBefore("2");
        test.addAfter("3");
        assertEquals("should be false", false, test.isEmpty());
        
    }

    @Test
    public void test_isEmpty_true(){
        Sequence test = makeSequence(10);
        assertEquals("should be true", true, test.isEmpty());
        
    }

    @Test
    public void test_equals_true(){
        Sequence test = makeSequence(10);
        test.addBefore("1");
        test.addBefore("2");
        test.addBefore("3");
        test.addAfter("4");

        Sequence test2 = makeSequence(4);
        test2.addBefore("1");
        test2.addBefore("2");
        test2.addBefore("3");
        test2.addAfter("4");
        assertEquals("should be true", true, test.equals(test2));
        
    }

    @Test
    public void test_equals_true_2(){
        Sequence test = makeSequence(4);
        test.addBefore("1");
        test.addBefore("2");
        test.addBefore("3");
        test.addAfter("4");

        Sequence test2 = makeSequence(10);
        test2.addAfter("1");
        test2.addBefore("2");
        test2.addBefore("3");
        test2.addAfter("4");
        assertEquals("should be true", true, test.equals(test2));

        assertEquals("should be true", true, test2.equals(test));
        
    }

    @Test
    public void test_clone(){
        Sequence test = makeSequence(10);
        test.addBefore("1");
        test.addBefore("2");
        test.addBefore("3");
        test.addAfter("4");
        Sequence test2 = test.clone();
        assertEquals("should be true", true, test.equals(test2));

    }

    // these were coming up false on gradescope and I could not figure out why.
    // they are working here. thats all i know.
    @Test
    public void test_two_equal_with_diff_capacity(){
        Sequence test = makeSequence(4);
        test.addAfter("1");
        test.addBefore("2");
        test.addBefore("3");
        test.addAfter("4");

        Sequence test2 = makeSequence(10);
        test2.addAfter("1");
        test2.addBefore("2");
        test2.addBefore("3");
        test2.addAfter("4");
        assertEquals("should be true", true, test.equals(test2));
        
    }

    @Test
    public void test_symmetry(){
        // this is identical to test_equals_2, but i am putting it here because it is failing in gradescope again.
        // It does work here though.
        Sequence test = makeSequence(4);
        test.addBefore("1");
        test.addBefore("2");
        test.addBefore("3");
        test.addAfter("4");

        Sequence test2 = makeSequence(4);
        test2.addAfter("1");
        test2.addBefore("2");
        test2.addBefore("3");
        test2.addAfter("4");

        assertEquals("should be true", true, test.equals(test2));
        assertEquals("should be true", true, test2.equals(test));
        
    }

    @Test
    public void test_different_pointers(){
        // this is identical to test_equals_2, but i am putting it here because it is failing in gradescope again.
        // It does work here though.
        Sequence test = makeSequence(4);
        test.addBefore("1");
        test.addBefore("2");
        test.addBefore("3");
        test.addAfter("4");
        test.advance();

        Sequence test2 = makeSequence(4);
        test2.addAfter("1");
        test2.addBefore("2");
        test2.addBefore("3");
        test2.addAfter("4");

        assertEquals("should be false. different pointers", false, test.equals(test2));

        test.advance(); test.advance(); test.advance();
        test2.advance(); test2.advance(); test2.advance(); test2.advance();

        assertEquals("should be true. neither have a pointer, both are equal.", true, test2.equals(test));
        
    }
}
