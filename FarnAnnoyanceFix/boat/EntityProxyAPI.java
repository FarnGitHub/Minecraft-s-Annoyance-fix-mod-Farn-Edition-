package FarnAnnoyanceFix.boat;

import net.minecraft.src.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EntityProxyAPI {
    private static final EntityProxyAPI instance = new EntityProxyAPI();
    
    public static final EntityProxyAPI getInstance() {
        return instance;
    }

    public void overrideEntity(String name, Class originalClass, int id, Class overrideclass) {
        try {
            this.overrideEntityRaw(name, originalClass, id, overrideclass);
        } catch (Exception e) {}
    }

    private void overrideEntityRaw(String name, Class originalClass, int id, Class overrideclass) throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Get all the fields with reflection
        HashMap<String, Class> stringToClassMapping = (HashMap<String, Class>) getPrivateValue(EntityList.class, null, "a", "stringToClassMapping");
        HashMap<Class, String> classToStringMapping = (HashMap<Class, String>) getPrivateValue(EntityList.class, null, "b", "classToStringMapping");
        HashMap<Integer, Class> IDtoClassMapping = (HashMap<Integer, Class>) getPrivateValue(EntityList.class, null, "c", "IDtoClassMapping");
        HashMap<Class, Integer> classToIDMapping = (HashMap<Class, Integer>) getPrivateValue(EntityList.class, null, "d", "classToIDMapping");

        // Remove vanilla Creepers from EntityList
        stringToClassMapping.remove(name);
        classToStringMapping.remove(originalClass);
        IDtoClassMapping.remove(id);
        classToIDMapping.remove(originalClass);

        // Get the private method to re-add the Creeper override
        Method addMapping;

        try {
            addMapping = EntityList.class.getDeclaredMethod("a", Class.class, String.class, int.class);
        } catch (Exception ex) {
            try {
                addMapping = EntityList.class.getDeclaredMethod("addMapping", Class.class, String.class, int.class);
            } catch(Exception ex1) {
                throw new NoSuchMethodException("Failed to find method 'a' ('addMapping'!");
            }
        }

        // Re-add Creepers to EntityList
        addMapping.setAccessible(true);
        addMapping.invoke(null, overrideclass, name, id);
    }

    public Object getPrivateValue(Class theClass, Object instance, String obfField, String deobfField) throws NoSuchFieldException
    {
        try
        {
            return ModLoader.getPrivateValue(theClass, instance, obfField);
        }
        catch (Exception ex)
        {
            try
            {
                return ModLoader.getPrivateValue(theClass, instance, deobfField);
            }
            catch (Exception ex1)
            {
                throw new NoSuchFieldException("Failed to find field " + obfField + " (" + deobfField + ")!");
            }
        }
    }
}