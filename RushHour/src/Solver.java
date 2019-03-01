import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;



public class Solver
{
	private UpdateableMinPQ<SearchNode> priQ;
	
	
	private static class SearchNode implements Comparable<SearchNode>
	{
		// Important!! Do not change the names or types of these fields!
		private PuzzleBoard board;
		private int costFromBeginningToHere;
		private SearchNode previous;

		public SearchNode(PuzzleBoard board, int costFromBeginningToHere, SearchNode previous)
		{
			this.board=board;
			this.costFromBeginningToHere=costFromBeginningToHere;
			this.previous=previous;
		}
		public int compareTo(SearchNode that)
		{
			return previous.compareTo(that);
		}

		@Override
		public int hashCode()
		{
			// DO NOT MODIFY THIS METHOD

			final int prime = 31;
			int result = 1;
			result = prime * result + ((board == null) ? 0 : board.hashCode());
			result = prime * result + costFromBeginningToHere;
			result = prime * result + ((previous == null) ? 0 : previous.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj)
		{
			// DO NOT MODIFY THIS METHOD

			if (this == obj)
			{
				return true;
			}
			if (obj == null)
			{
				return false;
			}
			if (getClass() != obj.getClass())
			{
				return false;
			}
			SearchNode other = (SearchNode) obj;
			if (board == null)
			{
				if (other.board != null)
				{
					return false;
				}
			} 
			else if (!board.equals(other.board))
			{
				return false;
			}
			if (costFromBeginningToHere != other.costFromBeginningToHere)
			{
				return false;
			}
			if (previous == null)
			{
				if (other.previous != null)
				{
					return false;
				}
			}
			else if (!previous.equals(other.previous))
			{
				return false;
			}
			return true;
		}
	}

	public Solver(PuzzleBoard initial)
	{
		this.priQ=new UpdateableMinPQ<SearchNode>();
		SearchNode ori= new SearchNode(initial,0,null);
		this.priQ.insert(ori);
		while(!priQ.isEmpty())
		{
			SearchNode temp=this.priQ.delMin();
			if(temp.board.isGoal())
			{
				break;
			}
		}
		
	}

	
	
	
	public Solver(PuzzleBoard initial, boolean extraCredit)
	{
		// DO NOT TOUCH unless you are passing all of the tests and wish to
		// attempt the extra credit.
		throw new UnsupportedOperationException();
	}

	public Iterable<PuzzleBoard> getPath()
	{
		throw new UnsupportedOperationException();
	}
}