import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;



public class Solver
{
	private UpdateableMinPQ<SearchNode> priQ;
	private SearchNode goal;//
	
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
			if(costFromBeginningToHere+board.heuristicCostToGoal()<that.costFromBeginningToHere+that.board.heuristicCostToGoal())
			{
				return -1;
			}
			else if(costFromBeginningToHere+board.heuristicCostToGoal()>that.costFromBeginningToHere+that.board.heuristicCostToGoal())
			{
				return 1;
			}
			return 0;
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
				this.goal=new SearchNode(temp.board, temp.costFromBeginningToHere, temp);
				break;
			}
			else
			{
				
				for(PuzzleBoard a : temp.board.getNeighbors())
				{
					this.priQ.insert(new SearchNode(a,temp.costFromBeginningToHere+1,temp));
					
				}
				
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
		Stack<PuzzleBoard> result= new Stack<PuzzleBoard>();
		while(goal.previous!=null)
		{
			result.push(goal.board);
			goal=new SearchNode(goal.previous.board,goal.previous.costFromBeginningToHere,goal.previous.previous);
			
			
		}
		
		result.push(goal.board);
		return result;
	}
}