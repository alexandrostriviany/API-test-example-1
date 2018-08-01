import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Created by BDSM on 25.07.2018.
 */
public class KinoteatrsLoadTest extends BaseTest {

    private static final String CINEMAS_PATH = "ajax/kinoteatrs_load";
    private static final String CINEMAS_SESSION = "ajax/kinoteatr_sessions_load";

    @Test
    public void checkResponseIsOk() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(CINEMAS_PATH)
                .then()
                .statusCode(200);
    }

    @Test
    public void checkMultiplexLavinaMallInTheList() {
        Response response = given()
                .when()
                .get(CINEMAS_PATH);

        ResponseBody body = response.getBody();
        String bodyStringValue = body.asString();

        Assert.assertTrue(bodyStringValue.contains("\"name\":\"Multiplex Lavina Mall\""));
    }

    @Test
    public void KinoteatrSessionsLoadTest() {
        given()
                .contentType(ContentType.JSON)
                .queryParam("kinoteatr", "256")
                .when()
                .get(CINEMAS_SESSION)
                .then()
                .body("id",hasItem("256"));;
    }
}