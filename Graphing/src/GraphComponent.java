import java.util.ArrayList;

public class GraphComponent {
	private int id;  // eg root node
	private ArrayList<AdjacencyList> nodes;
	
	public int getId() {return id;}
	public ArrayList<AdjacencyList> getNodes() {return nodes;}
	
	GraphComponent(int id) {
		this.id = id;
		nodes = new ArrayList<AdjacencyList>();
	}

	@Override
	public String toString() {
		return "GraphComponent{" +
				"id=" + id +
				", nodes=" + nodes +
				'}';
	}
}
