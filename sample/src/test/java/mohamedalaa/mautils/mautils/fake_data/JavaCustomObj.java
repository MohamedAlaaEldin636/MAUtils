package mohamedalaa.mautils.mautils.fake_data;

import androidx.annotation.NonNull;

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 2/27/2019.
 */
public class JavaCustomObj {

    public String name;
    public int age;

    public JavaCustomObj(){
        // no-args constructor
    }

    public JavaCustomObj(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @NonNull
    @Override
    public String toString() {
        return "JavaCustomObj(name = " + name + ", age = " + age + ")";
    }
}
