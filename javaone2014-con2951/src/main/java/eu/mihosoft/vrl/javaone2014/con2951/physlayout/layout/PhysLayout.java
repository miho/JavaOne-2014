package eu.mihosoft.vrl.javaone2014.con2951.physlayout.layout;

import eu.mihosoft.vrl.javaone2014.con2951.physlayout.physics.ForceField;
import eu.mihosoft.vrl.javaone2014.con2951.physlayout.physics.Spring;
import eu.mihosoft.vrl.javaone2014.con2951.physlayout.physics.Tether;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyMapWrapper;
import javafx.beans.property.ReadOnlySetWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Pair;


public class PhysLayout {

    private final Pane root;
    private final ReadOnlySetWrapper<Node> nodes;
    private final ReadOnlyMapWrapper<Node, Double> masses;
    private final Set<ForceField> fields;
    private final Map<Pair<Node, Node>, Set<Spring>> connections;
    private final Map<Node, Set<Tether>> tethers;
    private final Map<Node, Set<Node>> neighbors;

    public PhysLayout(Pane root) {
        this.root = root;
        nodes = new ReadOnlySetWrapper<>(FXCollections.observableSet());
        connections = new HashMap<>();
        tethers = new HashMap<>();
        neighbors = new HashMap<>();
        masses = new ReadOnlyMapWrapper(FXCollections.observableMap(new HashMap<>()));
        fields = new HashSet<>();
    }

    public Set<Spring> getConnections(Node a, Node b) {
        return connections.get(new Pair<>(a, b));
    }

    /**
     * Set the mass of a node. If set to infinity, the node will be fixed in
     * place.
     *
     * @param a
     * @param m
     */
    public void setMass(Node a, double m) {
        addNode(a);
        masses.put(a, m);
    }

    public double getMass(Node a) {
        return masses.getOrDefault(a, 1.0);
    }

    public ObservableMap<Node, Double> getMasses() {
        return masses.getReadOnlyProperty();
    }

    public void addNode(Node a) {
        nodes.add(a);
    }

    public void removeNode(Node a) {
        nodes.remove(a);
        masses.remove(a);
        Set<Node> nA = neighbors.get(a);
        if (nA != null) {
            nA.stream().forEach((b) -> {
                clearConnections(a, b);
            });
        }
    }

    public void addConnection(Node a, Node b, Spring... s) {
        addNode(a);
        addNode(b);

        Set<Spring> cAB = getConnections(a, b);
        Set<Spring> cBA = getConnections(b, a);
        if (cAB == null) {
            cAB = new HashSet<>();
            connections.put(new Pair<>(a, b), cAB);
        }
        if (cBA == null) {
            cBA = new HashSet<>();
            connections.put(new Pair<>(b, a), cBA);
        }

        cAB.addAll(Arrays.asList(s));
        cBA.addAll(Arrays.asList(s).stream().map((x) -> {
            return x.reverse();
        }).collect(Collectors.toList()));

        Set<Node> nA = neighbors.get(a);
        Set<Node> nB = neighbors.get(b);
        if (nA == null) {
            nA = new HashSet<>();
            neighbors.put(a, nA);
        }
        if (nB == null) {
            nB = new HashSet<>();
            neighbors.put(b, nB);
        }
        nA.add(b);
        nB.add(a);
    }

    public void removeConnection(Node a, Node b, Spring s) {
        Set<Spring> cAB = connections.get(new Pair<>(a, b));
        Set<Spring> cBA = connections.get(new Pair<>(b, a));
        if (cAB != null) {
            cAB.remove(s);
            if (cAB.isEmpty()) {
                clearConnections(a, b);
            } else {
                cBA.remove(s.reverse());
            }
        }
    }

    public void addTether(Node node, Tether... tether) {
        addNode(node);
        Set<Tether> t = tethers.get(node);
        if (t == null) {
            t = new HashSet<>();
            tethers.put(node, t);
        }
        t.addAll(Arrays.asList(tether));
    }

    public void removeTether(Node node, Tether tether) {
        Set<Tether> t = tethers.get(node);
        if (t != null) {
            t.remove(tether);
            if (t.isEmpty()) {
                clearTethers(node);
            }
        }
    }

    public void clearConnections(Node a, Node b) {
        connections.remove(new Pair<>(a, b));
        connections.remove(new Pair<>(b, a));
        Set<Node> nA = neighbors.get(a);
        Set<Node> nB = neighbors.get(b);
        if (nA != null) {
            nA.remove(b);
            nB.remove(a);
        }
    }

    public void clearAllConnections() {
        connections.clear();
        neighbors.clear();
    }

    public void clearTethers(Node node) {
        tethers.remove(node);
    }

    public void clearAllTethers() {
        tethers.clear();
    }

    public void clearAllMasses() {
        masses.clear();
    }

    public Set<Node> getNeighbors(Node a) {
        return neighbors.get(a);
    }

    public ObservableSet<Node> getNodes() {
        return nodes.getReadOnlyProperty();
    }

    public Set<Entry<Pair<Node, Node>, Set<Spring>>> getAllConnections() {
        return connections.entrySet();
    }

    public Set<Entry<Node, Set<Tether>>> getAllTethers() {
        return tethers.entrySet();
    }

    public void addField(ForceField... field) {
        fields.addAll(Arrays.asList(field));
    }

    public void removeField(ForceField field) {
        fields.remove(field);
    }

    public Collection<ForceField> getFields() {
        return fields;
    }

}
