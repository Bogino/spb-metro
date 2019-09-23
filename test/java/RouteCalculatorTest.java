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
    RouteCalculator routeCalculator;
    StationIndex stationIndex;

    List<Station> route2;
    RouteCalculator routeCalculator2;
    StationIndex stationIndex2;


    @Override
    protected void setUp() throws Exception
    {
        route = new ArrayList<>();

        stationIndex = new StationIndex();
        routeCalculator = new RouteCalculator(stationIndex);

        Line line1 = new Line(1,"Первая");
        Line line2 = new Line(2,"Вторая");

        route.add(new Station("Петровская", line1));
        route.add(new Station("Арбузная", line1));
        route.add(new Station("Морковная",line2));
        route.add(new Station("Яблочная",line2));

        Station s1 = new Station("Петровская", line1);
        Station s2 = new Station("Арбузная", line1);
        Station s3 = new Station("Морковная",line2);
        Station s4 = new Station("Яблочная",line2);

        line1.addStation(s1);
        line1.addStation(s2);
        line2.addStation(s3);
        line2.addStation(s4);

        stationIndex.stations.addAll(route);

        List<Station> connection = new ArrayList<>();
        connection.add(route.stream().filter(station -> station.getName().equals("Петровская")).iterator().next());
        connection.add(route.stream().filter(station -> station.getName().equals("Морковная")).iterator().next());
        stationIndex.addConnection(connection);

        List<Station> connection2 = new ArrayList<>();
        connection2.add(route.stream().filter(station -> station.getName().equals("Арбузная")).iterator().next());
        connection2.add(route.stream().filter(station -> station.getName().equals("Яблочная")).iterator().next());
        stationIndex.addConnection(connection2);
//=========================================================================================================================
        route2 = new ArrayList<>();

        stationIndex2 = new StationIndex();
        routeCalculator2 = new RouteCalculator(stationIndex2);

        Line line3 = new Line(3,"Третья");
        Line line4 = new Line(4,"Четвертая");
        Line line5 = new Line(5,"Пятая");

        Station s5 = new Station("Петровская", line3);
        Station s6 = new Station("Арбузная", line4);
        Station s7 = new Station("Морковная",line3);
        Station s8 = new Station("Яблочная",line4);
        Station s9 = new Station("Чистые пруды", line3);
        Station s10 = new Station("Зоопарк", line5);

        route2.add(s5);
        route2.add(s6);
        route2.add(s7);
        route2.add(s8);
        route2.add(s9);
        route2.add(s10);

        line3.addStation(s5);
        line4.addStation(s6);
        line3.addStation(s7);
        line4.addStation(s8);
        line5.addStation(s9);
        line5.addStation(s10);

        stationIndex2.stations.addAll(route2);

        List<Station> connection3 = new ArrayList<>();
        connection3.add(s5);
        connection3.add(s6);
        stationIndex2.addConnection(connection3);

        List<Station> connection4 = new ArrayList<>();
        connection4.add(s7);
        connection4.add(s8);
        stationIndex2.addConnection(connection4);

        List<Station> connection5 = new ArrayList<>();
        connection5.add(s9);
        connection5.add(s10);
        stationIndex2.addConnection(connection5);
    }

    public void testCalculateDuration()
    {
        double actual = RouteCalculator.calculateDuration(route);
        double expected = 8.5;
        assertEquals(expected,actual);
    }

    public void testGetShortestRoute()
    {
        List<Station> actual = routeCalculator.getShortestRoute(stationIndex.getStation("Петровская"),stationIndex.getStation("Морковная"));
        List<Station> expected = new ArrayList<>();
        expected.add(stationIndex.getStation("Петровская"));
        expected.add(stationIndex.getStation("Морковная"));
        assertEquals(expected,actual);
    }

    public void testGetRouteWithOneConnections()
    {
        List<Station> actual = routeCalculator.getShortestRoute(stationIndex.getStation("Петровская"),stationIndex.getStation("Яблочная"));
        List<Station> expected = new ArrayList<>();
        expected.add(stationIndex.getStation("Петровская"));
        expected.add(stationIndex.getStation("Морковная"));
        expected.add(stationIndex.getStation("Яблочная"));
        assertEquals(expected,actual);
    }

    public void testGetRouteWithTwoConnections()
    {
        List<Station> actual = routeCalculator2.getShortestRoute(stationIndex2.getStation("Петровская"),stationIndex2.getStation("Зоопарк"));
        List<Station> expected = new ArrayList<>();
        expected.add(stationIndex2.getStation("Петровская"));
        expected.add(stationIndex2.getStation("Арбузная"));
        expected.add(stationIndex2.getStation("Морковная"));
        expected.add(stationIndex2.getStation("Яблочная"));
        expected.add(stationIndex2.getStation("Чистые пруды"));

        assertEquals(expected,actual);
    }

    @Override
    protected void tearDown() throws Exception {

    }




}
