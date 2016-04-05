import java.util.ArrayList;

public class AdjacencyList {
    private int vertex;

    @Override
    public String toString() {
        return "AdjacencyList{" +
                "vertex=" + vertex +
                ", nbs=" + nbs +
                '}';
    }

    private ArrayList<Integer> nbs;  // neighbors of vertex

    public int getVertex() {
        return vertex;
    }

    public ArrayList<Integer> getNbs() {
        return nbs;
    }

    AdjacencyList(int vertex) {
        this.vertex = vertex;
        nbs = new ArrayList<Integer>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdjacencyList that = (AdjacencyList) o;

        return vertex == that.vertex;
    }

    @Override
    public int hashCode() {
        return vertex;
    }
}
