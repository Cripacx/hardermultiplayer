package de.cripacx.hardermultiplayer.api;

import java.lang.reflect.InvocationTargetException;

public class HarderMultiplayerAPI {

    public static final String MOD_ID = "hardermultiplayer";

    private static final InternalMethods __internalMethods;

    static {
        try {
            __internalMethods = (InternalMethods) Class.forName("de.cripacx.hardermultiplayer.InternalMethodsImpl").getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
