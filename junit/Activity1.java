import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;

public class Activity1 {

    static ArrayList<String> list;

    @BeforeEach
    public void setUp(){
        // Initialize a new ArrayList
        list = new ArrayList<>();

        // Add values to the list
        list.add("alpha"); // at index 0
        list.add("beta"); // at index 1
    }

    @Test
    public void insertTest(){
       assertEquals(2,list.size(),"List size is expected 2, but  actual size is "+list.size());
       list.add("third value");
       assertEquals(3,list.size(),"List size is expected 3, but  actual size is "+list.size());

        // Assert individual elements
        assertEquals("alpha", list.get(0), "Expected Value alpha");
        assertEquals("beta", list.get(1), "Expected Value beta");
        assertEquals("third value", list.get(2), "Expected Value third value");
    }

    @Test
    public void replaceTest(){
        list.set(1, "changed value");
        assertEquals(2, list.size(), "List size is expected 2, but  actual size is "+list.size());
        assertEquals("alpha", list.get(0), "Expected Value alpha");
        assertEquals("changed value", list.get(1), "Expected Value changed value");
    }
}
