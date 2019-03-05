package mohamedalaa.mautils.mautils;

import org.junit.Test;

import mohamedalaa.mautils.mautils.lifecycle_extensions.YourViewModel;

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 3/5/2019.
 */
public class GeneralJava {

    @Test
    public void getTypeParam() {
        CheckTypeParam<YourViewModel> checkTypeParam = new CheckTypeParam<YourViewModel>(){};
        checkTypeParam.performPrintln();
    }

}
