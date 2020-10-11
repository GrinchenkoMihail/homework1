package ru.digitalhabbits.homework1.service;


import org.slf4j.Logger;
import ru.digitalhabbits.homework1.plugin.PluginInterface;


import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;
import static org.slf4j.LoggerFactory.getLogger;

public class PluginLoader {
    private static final Logger logger = getLogger(PluginLoader.class);

    private static final String PLUGIN_EXT = ".jar";
    private static final String PACKAGE_TO_SCAN = "ru.digitalhabbits.homework1.plugin";

    @Nonnull
    public List<Class<? extends PluginInterface>> loadPlugins(@Nonnull String pluginDirName) {
        // TODO: Done
        List<Class<? extends PluginInterface>> plugins = null;

        File pluginDir = new File(pluginDirName);
        File[] files = pluginDir.listFiles((dir, name) -> name.endsWith(PLUGIN_EXT));

        if (files.length > 0 && files != null) {

            List<String> classes = getClassesFromPlugins(files);
            List<URL> urls = getUrlAddresses(files);
            plugins = getListPlugins(classes, urls);

        }
        return plugins;
    }

    private List<String> getClassesFromPlugins(File[] files) {
        List<String> classes = new ArrayList<>();
        for (File file : files) {
            try {
                JarFile jar = new JarFile(file);
                jar.stream().forEach(jarEntry -> {
                    if (jarEntry.getName().endsWith(".class")) {
                        classes.add(jarEntry.getName());
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return classes;
    }

    private List<URL> getUrlAddresses(File[] files) {
        List<URL> urls = new ArrayList<>();
        for (File file : files) {
            try {
                URL url = file.toURI().toURL();
                urls.add(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }

    private List<Class<? extends PluginInterface>> getListPlugins(List<String> classes, List<URL> urls) {
        List<Class<? extends PluginInterface>> plugins = new ArrayList<>();
        URLClassLoader urlClassLoader = new URLClassLoader(urls.toArray(new URL[urls.size()]));
        classes.forEach(className -> {
            try {
                Class cls = urlClassLoader.loadClass(className
                        .replaceAll("/", ".")
                        .replace(".class", ""));

                Class[] interfaces = cls.getInterfaces();
                for (Class intrface : interfaces) {
                    if (intrface.equals(PluginInterface.class)) {
                        plugins.add(cls);
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        return plugins;
    }


}
