jgraph-search-gui
=================

A Java GUI for testing graph search algorithms (BFS, DFS, A*, etc.)

Each algorithm should implement either `UninformedSearch` or `InformedSearch` interface.

      @ServiceProvider(service = AbstractAlgorithm.class, position = 5)
      public class DFS extends AbstractAlgorithm implements UninformedSearch {

	  private static String name = "DFS";

	  @Override
	  public String getName() {
	      return name;
	  }

	  @Override
	  public List<Node> findPath(Node startNode) {
	      
	      return null;
	  }
      }
      
      
When you add new algorithms you have to modify `META-INF/services/cz.cvut.fit.zum.api.AbstractAlgorithm` and add there your 
full class name. Then your algorithm could be in different jar, just make sure it's added to java's claspath.