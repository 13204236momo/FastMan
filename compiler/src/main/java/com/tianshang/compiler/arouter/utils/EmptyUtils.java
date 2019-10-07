package com.tianshang.compiler.arouter.utils;

import java.util.Collection;
import java.util.Map;

public final class EmptyUtils {
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isEmpty(Collection<?> coll) {
        return coll == null || coll.size() == 0;
    }

    public static boolean isEmpty(final Map<?, ?> map) {
        return map == null || map.size() == 0;
    }


}
