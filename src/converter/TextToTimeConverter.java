package converter;

import com.google.common.collect.Maps;

import java.util.Map;

public class TextToTimeConverter {

    private Map<Integer, String> numberMap = setUpNumberMap();
    private Map<Integer, String> minuteMap = setUpSpecialMinuteMap();


    public String convertToTime(String text) {
        StringBuilder sb = new StringBuilder();
        sb.append("It's ");

        String hours = text.split(":")[0];
        String minutes = text.split(":")[1];

        // edge case - invalid hour or minute
        if (Integer.parseInt(hours) > 23 || Integer.parseInt(minutes) > 59) {
            throw new IllegalArgumentException("Hour and / or minute is not in a correct format. " + hours + ":" + minutes);
        }

        // hour contains leading zeros - get rid of them
        if (hours.length() == 2 && hours.charAt(0) == '0') {
            hours = hours.substring(1, 2);
        }

        // edge case - midnight
        if ("0".equals(hours) && "00".equals(minutes)) {
            return sb.append("Midnight").toString();
        }

        // edge case midday
        if ("12".equals(hours) && "00".equals(minutes)) {
            return sb.append("Midday").toString();
        }

        boolean to = 60 - Integer.parseInt(minutes) < 30;

        String hourPart = handleHourPart(hours, to);

        // edge case - append PM / AM to the end
        if ("00".equals(minutes)) {
            int h = Integer.parseInt(hours);
            String hourString = numberMap.get(h % 12);
            if (h < 12) {
                sb.append(hourString);
                sb.append(" AM");
            } else {
                sb.append(hourString);
                sb.append(" PM");
            }
            return sb.toString();
        }

        String minutePart = handleMinutePart(minutes);
        sb.append(minutePart);

        if (to) {
            sb.append(" to ");
        } else {
            sb.append(" past ");
        }

        sb.append(hourPart);

        return sb.toString();
    }

    private String handleMinutePart(String minutes) {
        int m = Integer.parseInt(minutes);
        if (60 - m >= 30) {
            // past
            // is there any special mnemonics?
            if (minuteMap.containsKey(m)) {
                return minuteMap.get(m);
            }
            // 1 digit
            if (m / 10 < 1) {
                return numberMap.get(m);
            }
            int last = m % 10;
            if (last == 0) {
                return numberMap.get(last);
            }
            // 20-something
            return numberMap.get(20) + "-" + numberMap.get(last);
        }
        // to
        int diff = 60 - m;
        if (minuteMap.containsKey(diff)) {
            return minuteMap.get(diff);
        } else {
            if (diff <= 20) {
                return numberMap.get(diff);
            }
            int last = diff % 10;
            return numberMap.get(20) + "-" + numberMap.get(last);
        }
    }

    // to if minutes > 30 - adding 1 hour to the current hour
    private String handleHourPart(String hours, boolean to) {
        int h = Integer.parseInt(hours);
        if (to) {
            h++;
        }

        if (h == 24) {
            return "Midnight";
        }

        // multiplies of 10 or between 10 and 20
        if (h % 10 == 0 || h / 10 == 1) {
            return numberMap.get(h);
        }

        if (h < 20) {
            return numberMap.get(h);
        }

        // 20-something
        int last = h % 10;
        return numberMap.get(20) + "-" + numberMap.get(last);
    }

    private Map<Integer, String> setUpNumberMap() {
        Map<Integer, String> numberMap = Maps.newHashMap();

        numberMap.put(1, "one");
        numberMap.put(2, "two");
        numberMap.put(3, "three");
        numberMap.put(4, "four");
        numberMap.put(5, "five");
        numberMap.put(6, "six");
        numberMap.put(7, "seven");
        numberMap.put(8, "eight");
        numberMap.put(9, "nine");
        numberMap.put(10, "ten");
        numberMap.put(11, "eleven");
        numberMap.put(12, "twelve");
        numberMap.put(13, "thirteen");
        numberMap.put(14, "fourteen");
        numberMap.put(15, "fifteen");
        numberMap.put(16, "sixteen");
        numberMap.put(17, "seventeen");
        numberMap.put(18, "eighteen");
        numberMap.put(19, "nineteen");
        // nomenclature from here on
        numberMap.put(20, "twenty");
        numberMap.put(30, "thirty");
        numberMap.put(40, "forty");
        numberMap.put(50, "fifty");

        return numberMap;
    }

    // we will use the hourMap, but we need quarter, 45 and half for 15, 45 and 30
    private Map<Integer, String> setUpSpecialMinuteMap() {
        Map<Integer, String> minuteMap = Maps.newHashMap();
        minuteMap.put(15, "quarter");
        minuteMap.put(30, "half");
        minuteMap.put(45, "quarter to");

        return minuteMap;
    }

}
