public class MemoryAllocation
{
    
    String owner;  //owner of the memory
    long pos;      //starting position of the memory
    long len;      //length of the memory
    MemoryAllocation prev;
    MemoryAllocation next;
    boolean free; //state of the memory whether it is free or not
    MemoryManager manager;

    //You might want to add additional data/methods here


    //feel free to alter the constructor if you need/want to
    public MemoryAllocation(String owner, long pos, long len)

    {
	this.owner = owner;
	this.pos = pos;
	this.len=len;
    }


    public String getOwner()
    {
	return owner;
    }

    public long getPosition()
    {
	return pos;
    }

    public long getLength()
    {
	return len;
    }
    


    public String toString()
    {
	return "Alloc "+owner+" at "+pos+" for "+len; 
    }

}
