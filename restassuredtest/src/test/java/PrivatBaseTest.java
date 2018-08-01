import io.restassured.RestAssured;
import org.junit.BeforeClass;

public class PrivatBaseTest {

    @BeforeClass
    public static void initBaseUrl(){
        RestAssured.baseURI= "https://api.privatbank.ua/";
    }
}
