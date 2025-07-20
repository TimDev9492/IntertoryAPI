package me.timwastaken.intertoryapi.common;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {
    public static List<String> wrapText(String text, int maxLineLength) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split("\\s+");

        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            if (currentLine.isEmpty()) {
                // Start a new line
                currentLine.append(word);
            } else if (currentLine.length() + 1 + word.length() <= maxLineLength) {
                // Append to current line with space
                currentLine.append(" ").append(word);
            } else {
                // Line is full; add to list and start a new one
                lines.add(currentLine.toString());
                currentLine = new StringBuilder(word);
            }
        }

        // Add the last line if it has content
        if (!currentLine.isEmpty()) {
            lines.add(currentLine.toString());
        }

        return lines;
    }
}
