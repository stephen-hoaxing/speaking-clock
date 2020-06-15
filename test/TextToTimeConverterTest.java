import converter.TextToTimeConverter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TextToTimeConverterTest {

    private TextToTimeConverter textToTimeConverter;

    @Before
    public void init() {
        this.textToTimeConverter = new TextToTimeConverter();
    }

    @Test()
    public void exception_thrown_when_hour_is_invalid_test() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> textToTimeConverter.convertToTime("25:30"));
        assertTrue(exception.getMessage().contains("Hour and / or minute is not in a correct format. 25:30"));
    }

    @Test()
    public void exception_thrown_when_minute_is_invalid_test() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> textToTimeConverter.convertToTime("9:62"));
        assertTrue(exception.getMessage().contains("Hour and / or minute is not in a correct format. 9:62"));
    }

    @Test()
    public void exception_thrown_when_hour_and_minute_are_invalid_test() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> textToTimeConverter.convertToTime("25:62"));
        assertTrue(exception.getMessage().contains("Hour and / or minute is not in a correct format. 25:62"));
    }

    @Test
    public void text_is_midnight_with_leading_zero_test() {
        String time = textToTimeConverter.convertToTime("00:00");
        assertEquals("It's Midnight", time);
    }

    @Test
    public void text_is_midnight_without_leading_zero_test() {
        String time = textToTimeConverter.convertToTime("0:00");
        assertEquals("It's Midnight", time);
    }

    @Test
    public void text_is_midday_test() {
        String time = textToTimeConverter.convertToTime("12:00");
        assertEquals(time, "It's Midday", time);
    }

    @Test
    public void minutes_are_less_than_thirty_test() {
        String time = textToTimeConverter.convertToTime("12:25");
        assertEquals("It's twenty-five past twelve", time);
    }

    @Test
    public void minutes_are_greater_than_thirty_test() {
        String time = textToTimeConverter.convertToTime("18:36");
        assertEquals("It's twenty-four to nineteen", time);
    }

    @Test
    public void sharp_time_pm_with_leading_zeros_test() {
        String time = textToTimeConverter.convertToTime("08:00");
        assertEquals("It's eight AM", time);
    }

    @Test
    public void sharp_time_pm_without_leading_zeros_test() {
        String time = textToTimeConverter.convertToTime("8:00");
        assertEquals("It's eight AM", time);
    }

    @Test
    public void sharp_time_pm_test() {
        String time = textToTimeConverter.convertToTime("21:00");
        assertEquals("It's nine PM", time);
    }

    @Test
    public void minutes_to_midnight_test() {
        String time = textToTimeConverter.convertToTime("23:55");
        assertEquals("It's five to Midnight", time);
    }

    @Test
    public void thirty_minutes_after_test() {
        String time = textToTimeConverter.convertToTime("15:30");
        assertEquals("It's half past fifteen", time);
    }

    @Test
    public void quarter_to_hour_test() {
        String time = textToTimeConverter.convertToTime("9:45");
        assertEquals("It's quarter to ten", time);
    }

    @Test
    public void quarter_past_hour_test() {
        String time = textToTimeConverter.convertToTime("9:15");
        assertEquals("It's quarter past nine", time);
    }
}
