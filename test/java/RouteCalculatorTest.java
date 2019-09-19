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

        //Здесь в stationIndex добавляю все станции
        stationIndex.stations.addAll(route);
        //Создаю список из двух станций, между которыми переход
        List<Station> connection = new ArrayList<>();
        connection.add(route.stream().filter(station -> station.getName().equals("Арбузная")).iterator().next());
        connection.add(route.stream().filter(station -> station.getName().equals("Яблочная")).iterator().next());
        stationIndex.addConnection(connection);

    }

    public void testCalculateDuration()
    {
        double actual = RouteCalculator.calculateDuration(route);
        double expected = 8.5;
        assertEquals(expected,actual);
    }
/* Данный метод тестирует как getShortestRoute(), так и getRouteOnTheLine(Station from, Station to), т.к этот метод возвращает короткий путь
   и при условии, если станции находятся на одно линии
  */
    public void testGetShortestRoute()
    {
        List<Station> actual = routeCalculator.getShortestRoute(stationIndex.getStation("Морковная"),stationIndex.getStation("Яблочная"));
        List<Station> expected = new ArrayList<>();
        expected.add(stationIndex.getStation("Морковная"));
        expected.add(stationIndex.getStation("Яблочная"));
        assertEquals(expected,actual);
    }

    public void testGetRouteWithOneConnections()
    {
        List<Station> actual = routeCalculator.getShortestRoute(stationIndex.getStation("Арбузная"),stationIndex.getStation("Яблочная"));
        List<Station> expected = new ArrayList<>();
        expected.add(stationIndex.getStation("Арбузная"));
        expected.add(stationIndex.getStation("Морковная"));
        expected.add(stationIndex.getStation("Яблочная"));
        assertEquals(expected,actual);
    }

    @Override
    protected void tearDown() throws Exception {

    }











}
