import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PrivatTest extends PrivatBaseTest {

    private static final String OFFICE = "p24api/pboffice";

    @Test
    public void checkResponseIsOk() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(OFFICE)
                .then()
                .statusCode(200);
    }

    @Test
    public void checkResponseContentTypeJson() {
        given()
                .contentType(ContentType.JSON)
                .queryParam("json","")
                .when()
                    .get(OFFICE)
                .then()
                    .assertThat().contentType(ContentType.JSON);
    }

    @Test
    public void checkResponseContentTypeXml() {
    given()
                .contentType(ContentType.XML)
                .when()
                    .get(OFFICE)
                .then()
                    .assertThat().contentType(ContentType.XML);

    }

    @Test
    public void findOfficesByCity() {
        given()
                .queryParam("json","","city","Львов")
                .when()
                    .get(OFFICE)
                .then()
                    .body("name",hasItem("Львовское городское отделение №4"))
                    .body("name",hasItem("Западное ГРУ, Львовское г.отд.N 11"));
    }

    @Test
    public void findOfficesByCityByContent() {
        given()
                .queryParam("json","","city","Кие")
                .when()
                .get(OFFICE)
                .then()
                .body("city", not(hasKey("Кировоград")))
                .body("city",hasItem("Киев"))
                .body("city",hasItem("Енакиево"));
    }

    @Test
    public void findOfficesByAddress() {
        given()
                .queryParam("json","","address","Шевченко")
                .when()
                    .get(OFFICE)
                .then()
                    .body("city", hasItem("Кировоград"))
                    .body("city",hasItem("Ирпень"))
                    .body("address", not(hasKey("Декабристов")));
    }

    @Test
    public void findOfficesByCityByAddress() {
        given()
                .contentType(ContentType.JSON)
                .queryParam("json", "", "city", "Кировоград", "address", "Калинина")
                .when()
                .get(OFFICE)
                .then()
                .assertThat().contentType(ContentType.JSON)
                .body("id", hasItem("2516"))
                .body("name", hasItem("Отделение \"ЦПОИК в г.Александрия\" ПриватБанка"));
    }

    @Test
    public void findAllOffices() {
        given()
                .queryParam("json","")
                .when()
                .get(OFFICE)
                .then()
                .body("city", hasItem("Алчевск"))
                .body("city",hasItem("Ялта"));
    }

}
