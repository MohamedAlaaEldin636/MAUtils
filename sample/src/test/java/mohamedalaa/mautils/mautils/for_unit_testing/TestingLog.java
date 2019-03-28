/*
 * Copyright (c) 2018 Mohamed Alaa (https://github.com/MohamedAlaaEldin636)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package mohamedalaa.mautils.mautils.for_unit_testing;

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 11/29/2018.
 *
 * <br></br>
 * <br></br>
 *
 * <b>Usage</b> <br></br>
 * 1- In unit tests you cannot use Log class unless mocked, so easier to use this class,
 * <br></br>
 * Also note it colors the console messages, see sample module unit tests for an example isa.
 *
 * <br></br>
 * <br></br>
 *
 * <b>Notes</b> <br></br>
 * 1- For logging in real application it's better to use either {@link android.util.Log} class or
 * libraries that make logging easier, My recommendation is Timber library.
 *
 * @see ConsoleColors
 */
@SuppressWarnings("SameParameterValue")
public class TestingLog {

    /**
     * Used if you don't need any colors at all isa.
     * <p>
     * Difference between this and {@link #log(String)} is that this method doesn't use {@link ConsoleColors}
     * <br></br>
     * so if any problem is happening in coloring you can use this method instead isa.
     * </p>
     * @param msg text to be logged in console isa.
     * @see #log(String)
     */
    public static void l(String msg){
        if (msg.isEmpty()){
            return;
        }

        StackTraceElement element = Thread.currentThread().getStackTrace()[2];
        String className = element.getClassName();
        if (className.lastIndexOf(".") != -1
                && className.indexOf(".") != className.lastIndexOf(".")){
            String temp = className.substring(0, className.lastIndexOf("."));
            int index = temp.lastIndexOf(".") + 1;
            className = className.substring(index);
        }else {
            className = className.substring((className.lastIndexOf(".") + 1));
        }
        String methodName = element.getMethodName();

        System.out.println(className + "/" + methodName + ": " + msg);
    }

    /**
     * Used if you want white color in console.
     * @param msg text to be logged in console isa.
     */
    public static void log(String msg){
        println(ConsoleColors.RESET, msg);
    }

    /**
     * Used if you want red color in console, so it kinda replace error log.
     * @param msg text to be logged in console isa.
     */
    public static void e(String msg){
        println(ConsoleColors.RED, msg);
    }

    /**
     * Used if you want purple color in console, so it kinda replace verbose log.
     * @param msg text to be logged in console isa.
     */
    public static void v(String msg){
        println(ConsoleColors.PURPLE, msg);
    }

    /**
     * Used if you want yellow color in console, so it kinda replace info log.
     * @param msg text to be logged in console isa.
     */
    public static void i(String msg){
        println(ConsoleColors.YELLOW, msg);
    }

    /**
     * Used if you want blue color in console, so it kinda replace warn log.
     * @param msg text to be logged in console isa.
     */
    public static void w(String msg){
        println(ConsoleColors.BLUE, msg);
    }

    // ---- Private Methods

    private static void println(String consoleColor, String msg){
        if (consoleColor == null){
            consoleColor = "";
        }

        StackTraceElement element = Thread.currentThread().getStackTrace()[3];
        int tempIndex = 4;
        while (element.getClassName().equals(TestingLog.class.getName())){
            element = Thread.currentThread().getStackTrace()[tempIndex++];
        }
        String className = element.getClassName();
        if (className.lastIndexOf(".") != -1
                && className.indexOf(".") != className.lastIndexOf(".")){
            String temp = className.substring(0, className.lastIndexOf("."));
            int index = temp.lastIndexOf(".") + 1;
            className = className.substring(index);
        }else {
            className = className.substring((className.lastIndexOf(".") + 1));
        }
        String methodName = element.getMethodName();

        System.out.println(consoleColor + className + "/" + methodName + ": " + msg + ConsoleColors.RESET);
    }

}
