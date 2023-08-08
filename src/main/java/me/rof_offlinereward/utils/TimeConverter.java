package me.rof_offlinereward.utils;

public class TimeConverter {

        public static long convertToMilliseconds(String timeSinceLogin) {
            String[] components = timeSinceLogin.split("\\s+");

            long totalMilliseconds = 0;

            for (String component : components) {
                int value;
                String unit;

                if (component.length() >= 2) {
                    value = Integer.parseInt(component.substring(0, component.length() - 1));
                    unit = component.substring(component.length() - 1).toLowerCase();
                } else {
                    // Invalid format, skip this component
                    continue;
                }

                // Convert to millisecond based on the unit
                switch (unit) {
                    case "d":
                        totalMilliseconds += value * 24 * 60 * 60 * 1000L;
                        break;
                    case "h":
                        totalMilliseconds += value * 60 * 60 * 1000L;
                        break;
                    case "m":
                        totalMilliseconds += value * 60 * 1000L;
                        break;
                    case "s":
                        totalMilliseconds += value * 1000L;
                        break;
                    default:
                        // Unknown unit, skip this component
                        break;
                }
            }

            return totalMilliseconds;
        }
}