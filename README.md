# Unimined Modloader Template

This is a template for making b1.7.3 jar mods using Unimined. You can put other jar mods in the `jarmods` folder for use as dependencies.

## Base class edits

Editing vanilla base classes is actually super easy. In IntelliJ, go to External Libraries, open the `net.minecraft:minecraft:b1.7.3` dependency, find the base class you want, copy the generated source, and put this class in your source folder. Unimined will correctly map the class and put it in your jar, so you can add it as a jar mod in, say, Prism.

## JarModAgent

JarModAgent is a Java agent that can be used to apply class transforms. In Prism, for example, go to Edit... -> Version -> Add Agent, then add [the JarModAgent jar](https://maven.wagyourtail.xyz/releases/xyz/wagyourtail/unimined/jarmod-agent/0.1.3/jarmod-agent-0.1.3-all.jar). The example mod has an example transform to look at. If you don't need this, just get rid of the `.transform` file, the `transforms` call in `build.gradle` and the manifest.

There is currently an issue where you need to manually edit the refmap file because it will generate, for example, `"initGui": "Lfu;b()V"` when it should be `"initGui": "b()V"`. I don't know how to fix this.
