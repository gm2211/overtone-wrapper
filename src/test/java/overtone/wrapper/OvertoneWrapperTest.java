package overtone.wrapper;

import java.io.IOException;

import org.junit.Test;
import org.junit.experimental.categories.Category;

public class OvertoneWrapperTest {

    private OvertoneWrapper overtoneWrapper;

    public void setupWrapper() throws IOException {
        overtoneWrapper = new OvertoneWrapper();
    }

    @Category(SlowTest.class)
    @Test
    public void shouldBeepFiveTimes() throws InterruptedException, IOException {
        setupWrapper();
        for (int i = 0; i < 5; i++) {
            overtoneWrapper.sendCommand("(demo (sin-osc))");
            Thread.sleep(2000);
        }
    }
}
