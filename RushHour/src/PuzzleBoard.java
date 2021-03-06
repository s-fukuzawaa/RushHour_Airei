import java.util.ArrayList;
import java.util.Arrays;

public class PuzzleBoard
{
	// Do not change the name or type of this field
	private Vehicle[] idToVehicle;
	private Vehicle[][] board;
	
	// You may add additional private fields here
	
	public PuzzleBoard(Vehicle[] idToVehicleP)
	{
		this.idToVehicle= new Vehicle[idToVehicleP.length];
		this.board= new Vehicle[6][6];


		for(int i=0; i<idToVehicleP.length; i++)
		{
			this.idToVehicle[i]=idToVehicleP[i];
			if(idToVehicle[i]!=null)
			{
				int row=idToVehicle[i].getLeftTopRow();
				int col=idToVehicle[i].getLeftTopColumn();
				int leng=idToVehicle[i].getLength();
				if(idToVehicle[i].getIsHorizontal())
				{
					for(int j=0; j<leng; j++)
					{
						board[row][col+j]=idToVehicle[i];
					}
					
				}
				else
				{
					for(int j=0; j<leng; j++)
					{
						board[row+j][col]=idToVehicle[i];
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
		
		return board[row][column];
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
		if(row==-1||row==6||column==-1||column==6||board[row][column]!=null)
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
			
			if(idToVehicle[i]!=null)
			{
				Vehicle temp=idToVehicle[i];

				int row= temp.getLeftTopRow();
				int col= temp.getLeftTopColumn();
				int length= temp.getLength();
				if(temp.getIsHorizontal())
				{
					if(!collide(row,col-1)&&!collide(row,col+length))
					{
						Vehicle change= new Vehicle(i,temp.getIsHorizontal(),row,col+1,length);
						modi[i]=change;
						PuzzleBoard pass= new PuzzleBoard(modi);
						result.push(pass);
						Vehicle change2= new Vehicle(i,temp.getIsHorizontal(),row,col-1,length);
						modi[i]=change2;
						PuzzleBoard pass2= new PuzzleBoard(modi);
						result.push(pass2);
						
					}
					else if(!collide(row,col+length))
					{
						Vehicle change= new Vehicle(i,temp.getIsHorizontal(),row,col+1,length);
						modi[i]=change;
						PuzzleBoard pass= new PuzzleBoard(modi);
						result.push(pass);
					}
					
					else if(!collide(row,col-1))
					{
						Vehicle change= new Vehicle(i,temp.getIsHorizontal(),row,col-1,length);
						modi[i]=change;
						PuzzleBoard pass= new PuzzleBoard(modi);
						result.push(pass);
						
					}
					
				}
				else
				{
					if(!collide(row-1,col)&&!collide(row+length,col))
					{
						Vehicle change= new Vehicle(i,temp.getIsHorizontal(),row+1,col,length);
						modi[i]=change;
						PuzzleBoard pass= new PuzzleBoard(modi);
						result.push(pass);
						Vehicle change1= new Vehicle(i,temp.getIsHorizontal(),row-1,col,length);
						modi[i]=change1;
						PuzzleBoard pass1= new PuzzleBoard(modi);
						result.push(pass1);
					}
					else if(!collide(row-1,col))
					{
						Vehicle change= new Vehicle(i,temp.getIsHorizontal(),row-1,col,length);
						modi[i]=change;
						PuzzleBoard pass= new PuzzleBoard(modi);
						result.push(pass);
					}
					else if(!collide(row+length,col))
					{
						Vehicle change= new Vehicle(i,temp.getIsHorizontal(),row+1,col,length);
						modi[i]=change;
						PuzzleBoard pass= new PuzzleBoard(modi);
						result.push(pass);
						
					}
					
				}
				modi[i]=idToVehicle[i];

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