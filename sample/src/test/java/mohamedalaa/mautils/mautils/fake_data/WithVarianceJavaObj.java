package mohamedalaa.mautils.mautils.fake_data;

import androidx.annotation.NonNull;

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 2/27/2019.
 */
public class WithVarianceJavaObj<T extends JavaCustomObj, R> {

    public T javaCustomObj;
    public R integer;

    public WithVarianceJavaObj() {
        // no-args
    }

    public WithVarianceJavaObj(T javaCustomObj, R integer) {
        this.javaCustomObj = javaCustomObj;
        this.integer = integer;
    }

    @NonNull
    @Override
    public String toString() {
        return "WithVarianceJavaObj(javaCustomObj = " + javaCustomObj.toString() + ", integer = " + integer.toString() + ")";
    }
}
