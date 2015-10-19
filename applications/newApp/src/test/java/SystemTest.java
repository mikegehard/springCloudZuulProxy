import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import newApp.NewAppApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.net.ConnectException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = NewAppApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")

public class SystemTest {

    @Value("${local.server.port}")
    private int port;

    Process startOldApp;

    OkHttpClient client = new OkHttpClient();

    @Before
    public void setup() throws IOException {
        startOldApp = new ProcessBuilder(
                "java",
                "-jar",
                "../oldApp/build/libs/oldApp-0.0.1-SNAPSHOT.jar"
        )
                .inheritIO()
                .start();

        waitForOldAppToBoot();
    }

    @After
    public void teardown() {
        startOldApp.destroy();
    }

    @Test
    public void systemTest() throws IOException {
        Request request = new Request.Builder()
                .url("http://localhost:" + port + "/two")
                .build();

        Response response = client.newCall(request).execute();

        assertEquals(200, response.code());
        assertEquals("Two from old application", response.body().string());

        request = new Request.Builder()
                .url("http://localhost:" + port + "/one/nested")
                .build();

        response = client.newCall(request).execute();

        assertEquals(200, response.code());
        assertEquals("Nested One from new application", response.body().string());

        request = new Request.Builder()
                .url("http://localhost:" + port + "/one")
                .build();

        response = client.newCall(request).execute();

        assertEquals(200, response.code());
        assertEquals("One from new application", response.body().string());
    }

    private void waitForOldAppToBoot() throws IOException {
        Response response = null;
        Request request;
        ConnectException connectionException = null;

        do {
            request = new Request.Builder()
                    .url("http://localhost:9090/two")
                    .build();

            try{
                response = client.newCall(request).execute();
                connectionException = null;
            } catch (ConnectException e) {
                connectionException = e;
            }

        } while (connectionException != null || response.code() != 200);
    }
}
