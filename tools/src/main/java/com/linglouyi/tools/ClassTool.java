package com.linglouyi.tools;

import com.google.common.primitives.Primitives;

public class ClassTool {

    public static <T> T change(Object value, Class<T> z){
        return Primitives.wrap(z).cast(value);
    }
}
