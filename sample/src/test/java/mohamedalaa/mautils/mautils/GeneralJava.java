package mohamedalaa.mautils.mautils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import org.junit.Test;

import java.io.IOException;

import mohamedalaa.mautils.mautils.lifecycle_extensions.YourViewModel;

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 3/5/2019.
 */
public class GeneralJava {

    @Test
    public void getTypeParam() {
        CheckTypeParam<YourViewModel> checkTypeParam = new CheckTypeParam<YourViewModel>(){};
        checkTypeParam.performPrintln();

        TypeAdapter ta = new TypeAdapter<Integer>(){
            @Override
            public void write(JsonWriter out, Integer value) throws IOException {

            }

            @Override
            public Integer read(JsonReader in) throws IOException {
                return null;
            }
        };
    }

}
