import core.Line;
import core.Station;
import junit.framework.TestCase;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RouteCalculatorTest extends TestCase {

    List<Station> route;
    private static String dataFile = "src/main/resources/map.json";
    private static Scanner scanner;
    private static StationIndex stationIndex;
    RouteCalculator routeCalculator;

    @Override
    protected void setUp() throws Exception
    {
        route = new ArrayList<>();

        this.routeCalculator = new RouteCalculator(new StationIndex());

        Line line1 = new Line(1,"Первая");
        Line line2 = new Line(2,"Вторая");

        route.add(new Station("Петровская", line1));
        route.add(new Station("Арбузная", line2));
        route.add(new Station("Морковная",line2));
        route.add(new Station("Чистые пруды",line2));
        route.add(new Station("Яблочная",line1));
        StationIndex stationIndex = new StationIndex();
    }

    public void testCalculateDuration()
    {
        double actual = RouteCalculator.calculateDuration(route);
        double expected = 8.5;
        assertEquals(expected,actual);
    }

    public void testGetShortestRoute()
    {
        List<Station> actual = routeCalculator.getShortestRoute(route.stream().filter(station -> station.getName().equals("Петровская")).iterator().next(),
                route.stream().filter(station -> station.getName().equals("Арбузная")).iterator().next());
        List<Station> testRoute = new ArrayList<>();
        testRoute.add(route.stream().filter(station -> station.getName().equals("Петровская")).iterator().next());
        testRoute.add(route.stream().filter(station -> station.getName().equals("Яблочная")).iterator().next());
        List<Station> expected = testRoute;
        assertEquals(expected, actual);
    }

    @Override
    protected void tearDown() throws Exception {

    }











}
