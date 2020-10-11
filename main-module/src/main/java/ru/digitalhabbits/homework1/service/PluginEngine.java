package ru.digitalhabbits.homework1.service;

import ru.digitalhabbits.homework1.plugin.PluginInterface;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;

public class PluginEngine {

    @Nonnull
    public <T extends PluginInterface> String applyPlugin(@Nonnull Class<T> cls, @Nonnull String text) {
        // TODO: Done
        PluginInterface pluginInterface = null;
        try {
            pluginInterface = cls.getConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return pluginInterface.apply(text);
    }
}
