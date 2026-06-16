package api;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TeamsApiTest {

    private static final String BASE_URL = "https://qa-assignment.dev1.whalebone.io/api/teams";
    private static final int EXPECTED_TEAM_COUNT = 32;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void testTeamCount() {
        Response response = RestAssured.get();

        assertEquals(response.getStatusCode(), 200);

        int teamCount = response.jsonPath().getList("teams").size();
        assertEquals(teamCount, EXPECTED_TEAM_COUNT, "Expected " + EXPECTED_TEAM_COUNT + " teams in total");
    }

    @Test
    public void testOldestTeamIsMontrealCanadiens() {
        Response response = RestAssured.get();

        assertEquals(response.getStatusCode(), 200);

        List<Map<String, Object>> teams = response.jsonPath().getList("teams");
        Map<String, Object> oldestTeam = teams.stream()
                .min((a, b) -> Integer.compare((int) a.get("founded"), (int) b.get("founded")))
                .orElseThrow(() -> new AssertionError("No teams found"));

        assertEquals(oldestTeam.get("name"), "Montreal Canadiens",
                "The oldest team should be Montreal Canadiens (founded " + oldestTeam.get("founded") + ")");
    }

    @Test
    public void testCityWithMultipleTeams() {
        Response response = RestAssured.get();

        assertEquals(response.getStatusCode(), 200);

        List<Map<String, Object>> teams = response.jsonPath().getList("teams");
        Map<String, List<Map<String, Object>>> teamsByLocation = teams.stream()
                .collect(Collectors.groupingBy(t -> (String) t.get("location")));

        Map.Entry<String, List<Map<String, Object>>> cityWithMultipleTeams = teamsByLocation.entrySet().stream()
                .filter(e -> e.getValue().size() > 1)
                .findFirst()
                .orElseThrow(() -> new AssertionError("No city with more than 1 team found"));

        assertTrue(cityWithMultipleTeams.getValue().size() > 1,
                "City " + cityWithMultipleTeams.getKey() + " should have more than 1 team");

        List<String> teamNames = cityWithMultipleTeams.getValue().stream()
                .map(t -> (String) t.get("name"))
                .sorted()
                .collect(Collectors.toList());

        assertEquals(teamNames.size(), 2,
                cityWithMultipleTeams.getKey() + " should have exactly 2 teams");
        assertTrue(teamNames.contains("New York Islanders"),
                cityWithMultipleTeams.getKey() + " should have the Islanders");
        assertTrue(teamNames.contains("New York Rangers"),
                cityWithMultipleTeams.getKey() + " should have the Rangers");
    }

    @Test
    public void testMetropolitanDivisionTeams() {
        Response response = RestAssured.get();

        assertEquals(response.getStatusCode(), 200);

        List<Map<String, Object>> teams = response.jsonPath().getList("teams");
        List<String> metropolitanTeams = teams.stream()
                .filter(t -> {
                    Map<String, Object> division = (Map<String, Object>) t.get("division");
                    return "Metropolitan".equals(division.get("name"));
                })
                .map(t -> (String) t.get("name"))
                .sorted()
                .collect(Collectors.toList());

        assertEquals(metropolitanTeams.size(), 8, "Metropolitan division should have 8 teams");

        List<String> expectedTeams = List.of(
                "Carolina Hurricanes",
                "Columbus Blue Jackets",
                "New Jersey Devils",
                "New York Islanders",
                "New York Rangers",
                "Philadelphia Flyers",
                "Pittsburgh Penguins",
                "Washington Capitals"
        );

        assertEquals(metropolitanTeams, expectedTeams, "Metropolitan division teams should match expected list");
    }
}
