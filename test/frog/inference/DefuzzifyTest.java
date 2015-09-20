package frog.inference;

import frog.fuzzyset.FuzzySet;
import frog.fuzzyset.Triangle;
import junit.framework.TestCase;

/**
 * Tests the defuzzification methods
 */
public class DefuzzifyTest extends TestCase {

    private FuzzySet[] fsets;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        this.fsets = new FuzzySet[]{
                new Triangle(0, 1, 2),
                new Triangle(1, 2, 3)
        };
    }

    public void testCenterOfGravity() {
        assertEquals(1.5, Defuzzify.centerOfGravity(fsets));
    }
}
