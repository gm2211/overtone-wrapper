package overtone.wrapper;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;

import clojure.java.api.Clojure;
import clojure.lang.Compiler;
import clojure.lang.IFn;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import overtone.utils.ResourceUtils;

public class OvertoneWrapper {
    public static final String USE_OVERTONE_LIVE = "(use 'overtone.live)";
    public static final String OVERTONE_LIVE = "overtone.live";
    public static final String JAVA_LIBRARY_PATH = "java.library.path";
    public static final String DEFAULT_CONFIG = "wrapper-config.yml";

    private final OvertoneWrapperConfig config;

    public OvertoneWrapper() throws IOException {
        config = readConfigFile();
        initOvertoneServer();
    }

    private OvertoneWrapperConfig readConfigFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        URL defaultConfigFilePath = getClass().getClassLoader().getResource(DEFAULT_CONFIG);
        return objectMapper.readValue(defaultConfigFilePath, OvertoneWrapperConfig.class);
    }

    public OvertoneWrapper(OvertoneWrapperConfig config) throws IOException {
        this.config = config;
        initOvertoneServer();
    }

    private void initOvertoneServer() throws IOException {
        String nativeLibrariesPath = ResourceUtils.copyResourcesToTempDir(config.getNativeLibrariesPath());
        System.setProperty(JAVA_LIBRARY_PATH, nativeLibrariesPath);
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