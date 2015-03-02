package overtone.wrapper;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;

import clojure.java.api.Clojure;
import clojure.lang.Compiler;
import clojure.lang.IFn;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class OvertoneWrapper {
    public static final String USE_OVERTONE_LIVE = "(use 'overtone.live)";
    public static final String OVERTONE_LIVE = "overtone.live";
    public static final String JAVA_LIBRARY_PATH = "java.library.path";
    public static final String DEFAULT_CONFIG = "wrapper-config.yml";

    private final OvertoneWrapperConfig config;

    public OvertoneWrapper() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        URL defaultConfigFilePath = getClass().getClassLoader().getResource(DEFAULT_CONFIG);
        config = objectMapper.readValue(defaultConfigFilePath, OvertoneWrapperConfig.class);
        initOvertoneServer();
    }

    public OvertoneWrapper(OvertoneWrapperConfig config) {
        this.config = config;
        initOvertoneServer();
    }

    private void initOvertoneServer() {
        System.setProperty(JAVA_LIBRARY_PATH, config.getNativeLibrariesPath());
        importLibrary(OVERTONE_LIVE);
        sendCommand(USE_OVERTONE_LIVE);
    }

    private void importLibrary(String library) {
        IFn require = Clojure.var("clojure.core", "require");
        require.invoke(Clojure.read(library));
    }

    public void sendCommand(String command) {
        Compiler.load(new StringReader(command));
    }
}