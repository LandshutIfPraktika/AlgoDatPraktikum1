import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by s-gheldd on 3/27/16.
 */
public class Main {
    public static void main(String[] args) {

        for (int i = 1; i <= 5; i++) {
            final String filename = "data/Net" + i + ".txt";
            System.out.println("Parsing File: " + filename);
            final List<Point> links = FileParser.readFile(filename);
            final Map<Integer, AdjacencyList> nodes = new HashMap<>();
            for (final Point p : links) {
                if (nodes.containsKey(p.x)) {
                    nodes.get(p.x).getNbs().add(p.y);
                } else {
                    final AdjacencyList list = new AdjacencyList(p.x);
                    list.getNbs().add(p.y);
                    nodes.put(p.x, list);
                }
                if (nodes.containsKey(p.y)) {
                    nodes.get(p.y).getNbs().add(p.x);
                } else {
                    final AdjacencyList list = new AdjacencyList(p.y);
                    list.getNbs().add(p.x);
                    nodes.put(p.y, list);
                }
            }

            final Map<AdjacencyList, AdjacencyList> representatives = quickUnion(links, nodes);
            final ArrayList<GraphComponent> graph = findGraphComponents(representatives);


            graph.stream().forEach(System.out::println);
            System.out.println();
            printStatistics(graph);
            System.out.println();
        }
    }

    private static void printStatistics(final ArrayList<GraphComponent> graph) {
        System.out.println("Der Graph hat " + graph.size()+" Zusammenhangskomponenten.");
        for (int i = 0; i < graph.size(); i++) {
            System.out.println("Zusammenhangskomponente " + i + " hat " + graph.get(i).getNodes().size() + " Knoten.");
        }
    }

    private static ArrayList<GraphComponent> findGraphComponents(Map<AdjacencyList, AdjacencyList> representatives) {
        final Map<AdjacencyList, GraphComponent> ergMap = new HashMap<>();
        for (final AdjacencyList node : representatives.keySet()) {
            final AdjacencyList root = findRoot(representatives, node);
            if (!ergMap.containsKey(root)) {
                final GraphComponent component = new GraphComponent(root.getVertex());
                ergMap.put(root, component);
            }
            ergMap.get(root).getNodes().add(node);
        }
        return new ArrayList<>(ergMap.values());
    }

    private static Map<AdjacencyList, AdjacencyList> quickUnion(final List<Point> links, final Map<Integer, AdjacencyList> nodes) {

        final HashMap<AdjacencyList, AdjacencyList> representatives = new HashMap<>(nodes.size());
        for (final AdjacencyList node : nodes.values()) {
            representatives.put(node, node);
        }
        for (final Point link : links) {
            final AdjacencyList xRoot = findRoot(representatives, nodes.get(link.x));
            final AdjacencyList yRoot = findRoot(representatives, nodes.get(link.y));
            if (!xRoot.equals(yRoot)) {
                representatives.put(xRoot, yRoot);
            }
        }
        return representatives;
    }

    private static AdjacencyList findRoot(final Map<AdjacencyList, AdjacencyList> representatives, final AdjacencyList node) {
        AdjacencyList currentNode = node;
        AdjacencyList rootCandidate = representatives.get(node);
        while (!currentNode.equals(rootCandidate)) {
            currentNode = rootCandidate;
            rootCandidate = representatives.get(currentNode);
        }
        return rootCandidate;
    }


}
