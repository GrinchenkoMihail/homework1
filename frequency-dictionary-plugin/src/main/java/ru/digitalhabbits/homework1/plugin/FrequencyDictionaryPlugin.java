package ru.digitalhabbits.homework1.plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FrequencyDictionaryPlugin
        implements PluginInterface {

    @Nullable
    @Override
    public String apply(@Nonnull String text) {
        // TODO: Done
        text = text.replaceAll("[\\W \\n+]", " ");
        List<String> textArrList = new ArrayList<>();
        textArrList.addAll(Arrays.asList(text.split("\\s+")));
        String result = "";
        Map<String, Integer> frequencyWordsInMap = new TreeMap<>();
        for (String word : textArrList) {
            int count = 0;
            if (!frequencyWordsInMap.containsKey(word)) {
                for (String wordNext : textArrList) {
                    if (word.equals(wordNext)) {
                        count++;
                    }
                }
                frequencyWordsInMap.put(word.toLowerCase(), count);
            }
        }
        List<Map.Entry<String, Integer>> list = frequencyWordsInMap.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .collect(Collectors.toList());

        result = list.stream()
                .map(x -> x.toString().replace("="," "))
                .collect(Collectors.joining("\n"));
        return result;
    }
}
