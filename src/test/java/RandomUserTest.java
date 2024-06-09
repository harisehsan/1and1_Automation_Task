import config.RandomUserConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import java.util.*;
import java.util.stream.Collectors;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;


public class RandomUserTest extends RandomUserConfig {

    @Test
    public void testSingleUser() {
        Response response = given()
//               .queryParam("nat","US")
//                .queryParam("password","special,1-8")
//                .queryParam("format","xml")
                .when()
                .get();
        String country = response.jsonPath().getString("results[0].location.country");
        int age = response.jsonPath().getInt("results[0].dob.age");
        String password = response.jsonPath().getString("results[0].login.password");
//        String country = response.xmlPath().getString("user[0].results.location.country");
//        int age = Integer.parseInt(response.xmlPath().getString("user[0].results.dob.age"));
//        String password = response.xmlPath().getString("user[0].results.login.password");
        StringBuilder errorMessage = new StringBuilder();
        boolean testResult = true;
        if (age <= 40) {
            errorMessage.append("Age Check Failed! Expected age to be greater than 40, but was ").append(age).append("\n");
            testResult = false;
        }
        if (!country.equalsIgnoreCase("United States")) {
            errorMessage.append("Country Check Failed! Expected nationality to be United States, but was ").append(country).append("\n");
            testResult = false;
        }
        if (!password.matches("^[a-zA-Z0-9]*$")) {
            errorMessage.append("Password Check Failed! Expected password to only contain letters and numbers, but was ").append(password).append("\n");
            testResult = false;
        }
        assertTrue(String.valueOf(errorMessage), testResult);
    }

    public List<Map<String, Object>> filterAndSortByFirstNameofRandomUsers() {
        Response response = given()
                .queryParam("results", 100)
                .queryParam("inc", "name")
                .when()
                .get();
        JsonPath jsonPath = response.jsonPath();
        List<Map<String, Object>> results = jsonPath.getList("results");
        List<Map<String, Object>> processedResults = new ArrayList<>();
        for (Map<String, Object> result : results) {
            Map<String, Object> name = (Map<String, Object>) result.get("name");
            if (name != null && name.get("first") != null && name.get("first").toString().startsWith("F")) {
                processedResults.add(result);
            }
        }
        processedResults = processedResults.stream()
                .sorted(Comparator.comparing(result -> ((Map<String, Object>) result.get("name")).get("first").toString()))
                .collect(Collectors.toList());
        return processedResults;
    }

    @Test
    public void unitTestForFilteredAndSortedByFirstName()
    {
        List<Map<String, Object>> processedResults = filterAndSortByFirstNameofRandomUsers();
        assertThat("No entry was found starting with 'F' for the first name", processedResults, not(empty()));
        List<String> firstNames = new ArrayList<>();
        for (Map<String, Object> result : processedResults) {
            Map<String, Object> nameMap = (Map<String, Object>) result.get("name");
            String firstName = (String) nameMap.get("first");
            firstNames.add(firstName);
        }
        StringBuilder errorMessage = new StringBuilder();
        boolean testResult = true;

        if(!firstNames.stream().sorted().toList().equals(firstNames))
        {
            errorMessage.append("Sorting Check Failed! Names are not sorted, The names were ").append(firstNames).append("\n");
            testResult = false;
        }

        if (firstNames.stream().noneMatch(s -> s.startsWith("F")))
        {
            errorMessage.append("Name starts-with check failed! Not all names starts with 'F', The names were ").append(firstNames).append("\n");
            testResult = false;
        }
        assertTrue(String.valueOf(errorMessage), testResult);
    }

}



