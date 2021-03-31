import forwardmodelslim.level.LevelPart;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * code source:
 * http://www.java2s.com/example/java-utility-method/byte-array-copy/memcpy-object-to-object-from-int-size-3e6c7.html
 */

public class PerformanceTests {
    private static Unsafe unsafe;

    private static Unsafe getUnsafe() {
        if (unsafe == null) {
            Class<?> clz = Unsafe.class;
            Field[] fields = clz.getDeclaredFields();
            for (Field f : fields) {
                if (!f.getType().equals(Unsafe.class)) {
                    continue;
                }
                try {
                    f.setAccessible(true);
                    Unsafe unf = (Unsafe) f.get(null);
                    if (unf != null) {
                        return (unsafe = unf);
                    }
                } catch (Throwable ignored) {
                }
            }
        }
        return unsafe;
    }

    public static void main(String[] args) {
        unsafe = getUnsafe();
        LevelPart[] levelParts = new LevelPart[400];
        int[] ints = new int[400];
        byte[] bytes = new byte[400];
        boolean[] bools = new boolean[400];
        float[] floats = new float[400];
        for (int i = 0; i < 400; i++) {
            levelParts[i] = LevelPart.PIPE_TOP_LEFT_WITHOUT_FLOWER;
            ints[i] = 50;
            bytes[i] = 50;
            bools[i] = true;
            floats[i] = 1.654168541f;
        }
        LevelPart[] levelPartsCopy = new LevelPart[400];
        int[] intsCopy = new int[400];
        byte[] bytesCopy = new byte[400];
        boolean[] boolsCopy = new boolean[400];
        float[] floatsCopy = new float[400];

        // bools, arraycopy
        for (int i = 0; i < 10000; i++) {
            System.arraycopy(bools, 0, boolsCopy, 0, bools.length);
        }
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            System.arraycopy(bools, 0, boolsCopy, 0, bools.length);
        }
        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println("bools - arraycopy: " + time);

        // bytes, arraycopy
        for (int i = 0; i < 10000; i++) {
            System.arraycopy(bytes, 0, bytesCopy, 0, bytes.length);
        }
        start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            System.arraycopy(bytes, 0, bytesCopy, 0, bytes.length);
        }
        end = System.currentTimeMillis();
        time = end - start;
        System.out.println("bytes - arraycopy: " + time);

        // ints, arraycopy
        for (int i = 0; i < 10000; i++) {
            System.arraycopy(ints, 0, intsCopy, 0, ints.length);
        }
        start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            System.arraycopy(ints, 0, intsCopy, 0, ints.length);
        }
        end = System.currentTimeMillis();
        time = end - start;
        System.out.println("ints - arraycopy: " + time);

        // level parts, arraycopy
        for (int i = 0; i < 10000; i++) {
            System.arraycopy(levelParts, 0, levelPartsCopy, 0, levelParts.length);
        }
        start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            System.arraycopy(levelParts, 0, levelPartsCopy, 0, levelParts.length);
        }
        end = System.currentTimeMillis();
        time = end - start;
        System.out.println("levelParts - arraycopy: " + time);

        // floats, arraycopy
        for (int i = 0; i < 10000; i++) {
            System.arraycopy(floats, 0, floatsCopy, 0, floats.length);
        }
        start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            System.arraycopy(floats, 0, floatsCopy, 0, floats.length);
        }
        end = System.currentTimeMillis();
        time = end - start;
        System.out.println("floats - arraycopy: " + time);

        System.out.println("---------------");

        // bool, copyMemory
        for (int i = 0; i < 10000; i++) {
            unsafe.copyMemory(bools, 0, boolsCopy, 0, bools.length);
        }
        start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            unsafe.copyMemory(bools, 0, boolsCopy, 0, bools.length);
        }
        end = System.currentTimeMillis();
        time = end - start;
        System.out.println("bools - copyMemory: " + time);

        // bytes, copyMemory
        for (int i = 0; i < 10000; i++) {
            unsafe.copyMemory(bytes, 0, bytesCopy, 0, bytes.length);
        }
        start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            unsafe.copyMemory(bytes, 0, bytesCopy, 0, bytes.length);
        }
        end = System.currentTimeMillis();
        time = end - start;
        System.out.println("bytes - copyMemory: " + time);

        // ints, copyMemory
        //TODO: why length + 4?
        for (int i = 0; i < 10000; i++) {
            unsafe.copyMemory(ints, 0, intsCopy, 0, (ints.length + 4) * 4);
        }
        start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            unsafe.copyMemory(ints, 0, intsCopy, 0, (ints.length + 4) * 4);
        }
        end = System.currentTimeMillis();
        time = end - start;
        System.out.println("ints - copyMemory: " + time);

        // floats, copyMemory
        //TODO: why length + 4?
        for (int i = 0; i < 10000; i++) {
            unsafe.copyMemory(floats, 0, floatsCopy, 0, (floats.length + 4) * 4);
        }
        start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            unsafe.copyMemory(floats, 0, floatsCopy, 0, (floats.length + 4) * 4);
        }
        end = System.currentTimeMillis();
        time = end - start;
        System.out.println("floats - copyMemory: " + time);

/*    // TODO: seems like only primitive types can be copied like this - not an enum - LevelPart
        // level parts, copyMemory
        for (int i = 0; i < 10000; i++) {
            //unsafe.copyMemory(levelParts, 0, levelPartsCopy, 0, levelParts.length * 4);
        }
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            //unsafe.copyMemory(levelParts, 0, levelPartsCopy, 0, levelParts.length * 4);
        }
        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println("levelParts: " + time);
*/

        // trying to prevent optimization
        ints[0] = intsCopy[0];
        levelParts[0] = levelPartsCopy[0];
        bytes[0] = bytesCopy[0];
        bools[0] = boolsCopy[0];
        floats[0] = floatsCopy[0];
    }
}
