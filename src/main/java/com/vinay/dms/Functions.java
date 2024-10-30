package com.vinay.dms;

import java.util.UUID;

public class Functions {

    public static String UuidGenerator() {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        return uuidAsString;

    }

}
