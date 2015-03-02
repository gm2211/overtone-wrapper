package overtone.utils;

import java.io.File;
import java.io.IOException;

import com.google.common.io.Files;

public class ResourceUtils {

    private ResourceUtils() {
        // Utils classes should not be instantiated
    }

    /**
     * Copies all the resources in the provided resource directory to a temporary directory and returns its absolute path
     * @param resourceDirPath Path to the resource directory to be copied
     * @return Absolute path of the temp resource directory
     */
    public static String copyResourcesToTempDir(String resourceDirPath) throws IOException {
        File tempDir = Files.createTempDir();
        tempDir.deleteOnExit();

        File resourceDir = new File(ResourceUtils.class.getResource(resourceDirPath).getPath());

        for (File file : Files.fileTreeTraverser().breadthFirstTraversal(resourceDir).filter(File::isFile)) {
            File dest = new File(tempDir.getAbsolutePath(), file.getName());
            Files.copy(file, dest);
        }

        return tempDir.getAbsolutePath();
    }
}
