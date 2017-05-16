package ru.spbau.mit;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class Script {
        public Script(String name, String path) {
            this.name = name;
            this.path = path;
            Path p = Paths.get(path + "/" + name);
            fullPath = p.toAbsolutePath().getParent().toString();
        }

        public String getName() {
            return name;
        }

        public String getPath() {
            return path;
        }

        public String fullPath() {
            return fullPath + "/" + name + ".py";
        }

        private final String name;
        private final String path;
        private final String fullPath;
}
