package ru.digitalhabbits.homework1.plugin;

import java.util.Arrays;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CounterPlugin
        implements PluginInterface {

    @Nullable
    @Override
    public String apply(@Nonnull String text) {
        // TODO: Done
        long countLines = text.lines().count();
        String textWithoutSymbol = text.replaceAll("[\\W \\n]", " ");
        String[] arrWordsInText = textWithoutSymbol.split("\\s+");
        long symbolsCount = Arrays.stream(arrWordsInText).mapToInt(x -> x.length()).sum();

        return countLines + ";" + arrWordsInText.length + ";" + symbolsCount;
    }
}
