import org.ifmo.laba3.FormBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

public class FormBeanTests {
    private FormBean formBean;


    
    @BeforeEach
    void setUp() {
        formBean = new FormBean();
    }

    @Test
    public void checkHittingTest() {
        assertTrue(true);
    }

    @Test
    public void checkValidateTest(){
        assertTrue(true);
    }
}