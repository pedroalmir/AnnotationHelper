package annotation.plugin;

import java.util.Collection;

import org.apache.commons.collections15.MultiMap;
import org.apache.commons.collections15.multimap.MultiHashMap;

/**
 * Loads the plugin based on Rules
 *
 * @author Ankit Gupta - ankit.gupta2801@gmail.com
 */
public class PluginLoader {

    private MultiMap<PluginRule, Class<?>> pluginMap = new MultiHashMap<PluginRule, Class<?>>();
    
    private PluginRule[] rules;

    public PluginLoader(PluginRule... pluginRules) {
    	this.rules = pluginRules;
    }

    /**
     * Scans a collection of classes to create the mapping from rules to
     * classes.
     *
     * @param classCollection the collection of classes to be scanned.
     */
    public void scanClasses(Collection<Class<?>> classCollection) {
        //TODO make this multi-threaded.
    	
        for (PluginRule rule : rules) {
            for (Class clazz : classCollection) {
                if (rule.verify(clazz)) {
                    pluginMap.put(rule, clazz);
                }
            }
        }
    }

    /**
     * Returns a map containing mapping from {@link PluginRule} to corresponding
     * classes.
     *
     * @return a multi map of plugin rules mapped to corresponding classes.
     */
    public MultiMap<PluginRule, Class<?>> getPluginMap() {
        return pluginMap;
    }
}
