package com.diogonunes.jcolor.tests.unit;

import com.diogonunes.jcolor.Command;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Assert that each Command outputs the correct Ansi code.
 */
public class TestCommand {

    @Test
    public void Attribute_AnsiCode_ClearScreen() {
        // ARRANGE
        Command command = Command.CLEAR_SCREEN();

        // ACT
        String code = command.toString();

        // ASSERT
        String expectedAnsiCode = "2J";
        assertThat(code, equalTo(expectedAnsiCode));
    }

}
