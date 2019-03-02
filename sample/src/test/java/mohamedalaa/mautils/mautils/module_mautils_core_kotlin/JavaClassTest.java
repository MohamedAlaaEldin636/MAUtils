package mohamedalaa.mautils.mautils.module_mautils_core_kotlin;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 3/1/2019.
 */
public class JavaClassTest {

    @Test
    public void check1() {
        String s1 = "s1";
        String s2 = "s2";
        CharSequence charSequence1 = "char seq 1";

        Assert.assertTrue(s1.getClass().isAssignableFrom(s2.getClass()));
        Assert.assertTrue(charSequence1.getClass().isAssignableFrom(s2.getClass()));
        Assert.assertTrue(s2.getClass().isInstance(charSequence1));
        Assert.assertTrue(charSequence1.getClass().isInstance(charSequence1));
    }

}
