package com.diogonunes.jcdp.tests.unit;

import com.diogonunes.jcdp.color.api.Ansi;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Tests for Ansi class.
 *
 * @author Diogo Nunes
 * @version 3.1
 */
public class TestAnsi {

    @Test
    public void GenerateCode_ZeroOptions() {
        // ARRANGE
        // ACT
        String code = Ansi.generateCode();

        // ASSERT
        String expectedCode = Ansi.PREFIX + Ansi.POSTFIX;
        assertThat(code, equalTo(expectedCode));
    }

    @Test // Addresses https://github.com/dialex/JCDP/issues/6
    public void GenerateCode_OneOption() {
        // ARRANGE
        Object[] options = new Object[]{Ansi.Attribute.BOLD};

        // ACT
        String code = Ansi.generateCode(options);

        // ASSERT
        String expectedCode = Ansi.PREFIX + options[0] + Ansi.POSTFIX;
        assertThat(code, equalTo(expectedCode));
    }

    @Test
    public void GenerateCode_MultipleOptions() {
        // ARRANGE
        Object[] options = new Object[]{Ansi.Attribute.BOLD, Ansi.FColor.CYAN};

        // ACT
        String code = Ansi.generateCode(options);

        // ASSERT
        String expectedCode = Ansi.PREFIX + options[0] + Ansi.SEPARATOR + options[1] + Ansi.POSTFIX;
        assertThat(code, equalTo(expectedCode));
    }

    @Test
    public void GenerateCode_MultipleOptionsWithValueNone() {
        // ARRANGE
        Object[] options = new Object[]{Ansi.Attribute.NONE, Ansi.FColor.NONE, Ansi.BColor.NONE};

        // ACT
        String code = Ansi.generateCode(options);

        // ASSERT
        String expectedCode = Ansi.PREFIX + Ansi.POSTFIX;
        assertThat(code, equalTo(expectedCode));
    }

    @Test // Addresses https://github.com/dialex/JCDP/issues/6
    public void GenerateCode_SomeOptionsWithValueNone() {
        // ARRANGE
        Object[] options = new Object[]{Ansi.Attribute.NONE, Ansi.FColor.CYAN, Ansi.BColor.NONE};

        // ACT
        String code = Ansi.generateCode(options);

        // ASSERT
        String expectedCode = Ansi.PREFIX + options[1] + Ansi.POSTFIX;
        assertThat(code, equalTo(expectedCode));
    }
}
