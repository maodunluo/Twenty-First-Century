package com.yyc.down21centuries.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class CharacterUtilsTest {

    @Test
    void numeric() {
        String s = "rwrwr";
        assertFalse(CharacterUtils.numeric(s));
    }
}