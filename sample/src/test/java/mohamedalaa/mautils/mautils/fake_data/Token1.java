package mohamedalaa.mautils.mautils.fake_data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 2/21/2019.
 */
public class Token1 {

    public abstract static class Goal<E> {

        public String toJsonOrNull(E element) {
            Type type = getSuperclassTypeParameter(getClass());

            return toJsonOrNullJava(element, type);
        }

        public E fromJsonOrNull(String json) {
            Type type = getSuperclassTypeParameter(getClass());

            return fromJsonOrNullJava(json, type);
        }

        private Type getSuperclassTypeParameter(Class<?> subclass) {
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class<?>) {
                throw new RuntimeException("Missing type parameter isa.");
            }

            ParameterizedType parameterizedType = (ParameterizedType) superclass;
            //noinspection ConstantConditions
            return $Gson$Types.canonicalize(parameterizedType.getActualTypeArguments()[0]);
        }

    }

    private static <E> String toJsonOrNullJava(E element, Type type) {
        try {
            return generateGson().toJson(element, type);
        }catch (Exception e) {
            return null;
        }
    }

    private static <E> E fromJsonOrNullJava(String json, Type type) {
        try {
            return generateGson().fromJson(json, type);
        }catch (Exception e) {
            return null;
        }
    }

    private static Gson generateGson() {
        return new GsonBuilder()
                .serializeNulls()
                .setLenient()
                .enableComplexMapKeySerialization()
                .create();
    }

}
