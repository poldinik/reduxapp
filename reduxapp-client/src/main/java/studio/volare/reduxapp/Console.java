package studio.volare.reduxapp;

import jsinterop.annotations.JsType;

@JsType(
        namespace = "<global>",
        isNative = true,
        name = "console"
)
public abstract class Console {
    private Console() {
    }

    public static native void log(Object var0);

    public static native void log(Object var0, Object var1);

    public static native void log(Object var0, Object var1, Object var2);

    public static native void log(Object var0, Object var1, Object var2, Object var3);
}
