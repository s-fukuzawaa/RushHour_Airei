import java.util.ArrayList;
import java.util.Arrays;

public class PuzzleBoard
{
	// Do not change the name or type of this field
	private Vehicle[] idToVehicle;
	private LinearProbingHashST<Integer, LinearProbingHashST<Integer,Vehicle>> boardhori;
	private LinearProbingHashST<Integer, LinearProbingHashST<Integer,Vehicle>> boardverti;
	
	// You may add additional private fields here
	
	public PuzzleBoard(Vehicle[] idToVehicleP)
	{
		this.idToVehicle= new Vehicle[idToVehicleP.length];
		this.boardhori= new LinearProbingHashST<Integer, LinearProbingHashST<Integer,Vehicle>>();
		this.boardverti= new LinearProbingHashST<Integer, LinearProbingHashST<Integer,Vehicle>>();


		for(int i=0; i<idToVehicleP.length; i++)
		{
			this.idToVehicle[i]=idToVehicleP[i];
			if(idToVehicle[i]!=null)
			{
				if(idToVehicle[i].getIsHorizontal())
				{
					
					int row=idToVehicle[i].getLeftTopRow();
						int col=idToVehicle[i].getLeftTopColumn();
						if(boardhori.contains(row))
						{
							for(int j=0; j<idToVehicle[i].getLength(); j++)
							{
								boardhori.get(row).put(col+j, idToVehicle[i]);
							}
						}
						else
						{
							LinearProbingHashST<Integer,Vehicle> colid=new LinearProbingHashST<Integer,Vehicle>();
							for(int j=0; j<idToVehicle[i].getLength(); j++)
							{

								colid.put(col+j, idToVehicle[i]);
							}
							boardhori.put(row, colid);

						}
						
						
						
					
				}
				if(idToVehicle[i].getIsHorizontal()==false)
				{
					int row=idToVehicle[i].getLeftTopRow();
					int col=idToVehicle[i].getLeftTopColumn();
						if(boardverti.contains(col))
						{
							for(int j=0; j<idToVehicle[i].getLength(); j++)
							{
								boardverti.get(col).put(row+j, idToVehicle[i]);
							}
						}
						else
						{
							LinearProbingHashST<Integer,Vehicle> rowid=new LinearProbingHashST<Integer,Vehicle>();
							for(int j=0; j<idToVehicle[i].getLength(); j++)
							{
								rowid.put(row+j, idToVehicle[i]);
							}
							boardverti.put(col, rowid);

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
		if(!boardhori.contains(row)&&!boardverti.contains(column))
		{
			return null;
		}
		if(boardhori.contains(row)&&boardhori.get(row).contains(column))
		{
			return boardhori.get(row).get(column);
		}
		if(boardverti.contains(column)&&boardverti.get(column).contains(row))
		{
			return boardverti.get(column).get(row);
		}
		
		return null;
	}
	
	public int heuristicCostToGoal()
	{ 
		int sum=0;
		for(int i=idToVehicle[0].getLeftTopColumn()+idToVehicle[0].getLength(); i<6;i++)
		{
			if(collide(idToVehicle[0].getLeftTopRow(),i))
			{
				sum++;
			}
		}
		return 5-(idToVehicle[0].getLeftTopColumn()+idToVehicle[0].getLength()-1)+sum;
	}
	
	public boolean isGoal()
	{
		return (idToVehicle[0].getLeftTopColumn()+idToVehicle[0].getLength()-1)==5;
	}
	
	
	private boolean collide(int row, int column)
	{
		if(!boardhori.contains(row)&&!boardverti.contains(column))
		{
			return false;
		}
		if(boardhori.contains(row)&&boardhori.get(row).contains(column))
		{
			return true;
		}
		if(boardverti.contains(column)&&boardverti.get(column).contains(row))
		{
			return true;
		}
		
		return false;
	}
	
	
	public Iterable<PuzzleBoard> getNeighbors()
	{
		Stack<PuzzleBoard> result= new Stack<PuzzleBoard>();
		
		Vehicle[] modi= new Vehicle[idToVehicle.length];
		for(int i=0; i<idToVehicle.length; i++)
		{
			modi[i]=idToVehicle[i];
		}
		
		
		
		for(int i=0; i<idToVehicle.length; i++)
		{
			if(idToVehicle[i]==null)
			{
				i++;
			}
			else
			{
				int row= idToVehicle[i].getLeftTopRow();
				int col= idToVehicle[i].getLeftTopColumn();
				int length= idToVehicle[i].getLength();
				if(idToVehicle[i].getIsHorizontal())
				{
					if(col+length!=6&&(col==0||collide(row,col-1))&&!collide(row,col+length))
					{
						Vehicle change= new Vehicle(idToVehicle[i].getId(),idToVehicle[i].getIsHorizontal(),idToVehicle[i].getLeftTopRow(),idToVehicle[i].getLeftTopColumn()+1,idToVehicle[i].getLength());
						modi[i]=change;
						PuzzleBoard pass= new PuzzleBoard(modi);
						result.push(pass);
						modi[i]=idToVehicle[i];
					}
					else if(col-1!=-1&&(col+length-1==5||collide(row,col+length))&&!collide(row,col-1))
					{
						Vehicle change= new Vehicle(idToVehicle[i].getId(),idToVehicle[i].getIsHorizontal(),idToVehicle[i].getLeftTopRow(),idToVehicle[i].getLeftTopColumn()-1,idToVehicle[i].getLength());
						modi[i]=change;
						PuzzleBoard pass= new PuzzleBoard(modi);
						result.push(pass);
						modi[i]=idToVehicle[i];
					}
					
					else if(col-1!=-1&&col+length!=6&&!collide(row,col-1)&&!collide(row,col+length))
					{
						Vehicle change= new Vehicle(idToVehicle[i].getId(),idToVehicle[i].getIsHorizontal(),idToVehicle[i].getLeftTopRow(),idToVehicle[i].getLeftTopColumn()-1,idToVehicle[i].getLength());
						modi[i]=change;
						PuzzleBoard pass= new PuzzleBoard(modi);
						result.push(pass);
						Vehicle change2= new Vehicle(idToVehicle[i].getId(),idToVehicle[i].getIsHorizontal(),idToVehicle[i].getLeftTopRow(),idToVehicle[i].getLeftTopColumn()+1,idToVehicle[i].getLength());
						modi[i]=change2;
						PuzzleBoard pass2= new PuzzleBoard(modi);
						result.push(pass2);
						modi[i]=idToVehicle[i];
					}
				}
				else
				{
					if(row+length!=6&&(row==0||collide(row-1,col))&&!collide(row+length,col))
					{
						Vehicle change= new Vehicle(idToVehicle[i].getId(),idToVehicle[i].getIsHorizontal(),idToVehicle[i].getLeftTopRow()+1,idToVehicle[i].getLeftTopColumn(),idToVehicle[i].getLength());
						modi[i]=change;
						PuzzleBoard pass= new PuzzleBoard(modi);
						result.push(pass);
						modi[i]=idToVehicle[i];
					}
					else if(row-1!=-1&&(row+length-1==5||collide(row+length,col))&&!collide(row-1,col))
					{
						Vehicle change= new Vehicle(idToVehicle[i].getId(),idToVehicle[i].getIsHorizontal(),idToVehicle[i].getLeftTopRow()-1,idToVehicle[i].getLeftTopColumn(),idToVehicle[i].getLength());
						modi[i]=change;
						PuzzleBoard pass= new PuzzleBoard(modi);
						result.push(pass);
						modi[i]=idToVehicle[i];
					}
					else if(row-1!=-1&&row+length!=6&&!collide(row-1,col)&&!collide(row+length,col))
					{
						Vehicle change= new Vehicle(idToVehicle[i].getId(),idToVehicle[i].getIsHorizontal(),idToVehicle[i].getLeftTopRow()-1,idToVehicle[i].getLeftTopColumn(),idToVehicle[i].getLength());
						modi[i]=change;
						PuzzleBoard pass= new PuzzleBoard(modi);
						result.push(pass);
						Vehicle change2= new Vehicle(idToVehicle[i].getId(),idToVehicle[i].getIsHorizontal(),idToVehicle[i].getLeftTopRow()+1,idToVehicle[i].getLeftTopColumn(),idToVehicle[i].getLength());
						modi[i]=change2;
						PuzzleBoard pass2= new PuzzleBoard(modi);
						result.push(pass2);
						modi[i]=idToVehicle[i];
					}
				}
			}
			
		}
		
		return result;
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
