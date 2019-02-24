import java.util.ArrayList;
import java.util.Arrays;

public class PuzzleBoard
{
	// Do not change the name or type of this field
	private Vehicle[] idToVehicle;
	private LinearProbingHashST<Integer, LinearProbingHashST<Integer,Vehicle>> board;
	
	// You may add additional private fields here
	
	public PuzzleBoard(Vehicle[] idToVehicleP)
	{
		this.idToVehicle= new Vehicle[idToVehicleP.length];
		this.board= new LinearProbingHashST<Integer, LinearProbingHashST<Integer,Vehicle>>();

		for(int i=0; i<idToVehicleP.length; i++)
		{
			this.idToVehicle[i]=idToVehicleP[i];
			if(idToVehicle[i]!=null)
			{
				if(idToVehicle[i].getIsHorizontal())
				{
					
					int row=idToVehicle[i].getLeftTopRow();
						int col=idToVehicle[i].getLeftTopColumn();
						if(board.contains(row))
						{
							for(int j=0; j<idToVehicle[i].getLength(); j++)
							{
								board.get(row).put(col+j, idToVehicle[i]);
							}
						}
						else
						{
							LinearProbingHashST<Integer,Vehicle> colid=new LinearProbingHashST<Integer,Vehicle>();
							for(int j=0; j<idToVehicle[i].getLength(); j++)
							{

								colid.put(col+j, idToVehicle[i]);
							}
							board.put(row, colid);

						}
						
						
						
					
				}
				if(idToVehicle[i].getIsHorizontal()==false)
				{

					int row=idToVehicle[i].getLeftTopRow();
					int col=idToVehicle[i].getLeftTopColumn();
					LinearProbingHashST<Integer,Vehicle> colid=new LinearProbingHashST<Integer,Vehicle>();
					colid.put(col, idToVehicle[i]);
					for(int j=0; j<idToVehicle[i].getLength(); j++)
					{
						if(board.contains(row+j))
						{
							board.get(row+j).put(col, idToVehicle[i]);
						}
						else
						{
							board.put(row+j, colid);
						}

					}
	
					
				}
			}
				
			
		}
		
	}
	
	public Vehicle getVehicle(int id)
	{
		return idToVehicle[id];
	}

	public Vehicle getVehicle(int row, int column)
	{
		if(!board.contains(row))
		{
			return null;
		}
		else if(!board.get(row).contains(column))
		{
			return null;
		}
		return board.get(row).get(column);
	}
	
	public int heuristicCostToGoal()
	{
		throw new UnsupportedOperationException();
	}
	
	public boolean isGoal()
	{
		return (idToVehicle[0].getLeftTopColumn()+idToVehicle[0].getLength()-1)==6;
	}
	
	public Iterable<PuzzleBoard> getNeighbors()
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String toString()
	{
		// You do not need to modify this code, but you can if you really
		// want to.  The automated tests will not use this method, but
		// you may find it useful when testing within Eclipse
		
		String ret = "";
		for (int row=0; row < PuzzleManager.NUM_ROWS; row++)
		{
			for (int col=0; col < PuzzleManager.NUM_COLUMNS; col++)
			{
				Vehicle vehicle = getVehicle(row, col);
				if (vehicle == null)
				{
					ret += " . ";
				}
				else
				{
					int id = vehicle.getId(); 
					ret += " " + id;
					if (id < 10)
					{
						ret += " ";
					}
				}
			}
			ret += "\n";
		}
		
		for (int id = 0; id < PuzzleManager.MAX_NUM_VEHICLES; id++)
		{
			Vehicle v = getVehicle(id);
			if (v != null)
			{
				ret += "id " + v.getId() + ": " + 
						(v.getIsHorizontal() ? "h (" : "v (") + 
						v.getLeftTopRow() + "," + v.getLeftTopColumn() + "), " + v.getLength() + "  \n";
			}
		}
		
		return ret;
	}
	
	@Override
	public int hashCode()
	{
		// DO NOT MODIFY THIS METHOD
		
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(idToVehicle);
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
		
		PuzzleBoard other = (PuzzleBoard) obj;
		if (!Arrays.equals(idToVehicle, other.idToVehicle))
		{
			return false;
		}
		return true;
	}
}
