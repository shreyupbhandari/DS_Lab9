public class MemoryManager
{
   protected MemoryAllocation head;
    
   protected final String Free = "Free";


    /* size- how big is the memory space.  
     *  Memory starts at 0
     *
     */
   public MemoryManager(long size)
   {
      head = new MemoryAllocation(Free, 0, 0);
      head.next = head;
      head.prev = head;

      MemoryAllocation available = new MemoryAllocation(Free, 0, size);
      available.free = true;
      available.manager = this;
      available.prev = head;
      available.next = head;
      head.next = available;
      head.prev = available;
   }



    /**
       takes the size of the requested memory and a string of the process requesting the memory
       returns a memory allocation that satisfies that request, or
       returns null if the request cannot be satisfied.
     */
    
   public MemoryAllocation requestMemory(long size,String requester)
   {
      if (size <= 0)//Memory smaller than size 0 can not be given.
      {
         return null;
      }
      for (MemoryAllocation block = head.next; block != head; block = block.next)
      {
         if (block.free && block.len >= size)
         {
            if (block.len > size)
            {
               MemoryAllocation remainder = new MemoryAllocation(Free,
                     block.pos + size, block.len - size);
               remainder.free = true;
               remainder.manager = this;
               remainder.prev = block;
               remainder.next = block.next;
               block.next.prev = remainder;
               block.next = remainder;
               block.len = size;
            }

            block.owner = requester;
            block.free = false;
            return block;
         }
      }
      return null;
   }


    
    /**
       takes a memoryAllcoation and "returns" it to the system for future allocations.
       Assumes that memory allocations are only returned once.       

     */
   public void returnMemory(MemoryAllocation mem) throws IllegalArgumentException
   {
      if (mem == null || mem.manager != this)
         throw new IllegalArgumentException("Allocation does not belong to this manager");

      mem.owner = Free;
      mem.free = true;

     if(mem.prev!=head && mem.prev.free)
     {
    	 MemoryAllocation left= mem.prev;;
    	 left.len+=mem.len;
    	 left.next=mem.next;
    	 mem.next.prev=left;
    	 mem=left;
     }
     
     if(mem.next!=head&&mem.next.free)
     {
    	 MemoryAllocation right= mem.next;
    	 mem.len+=right.len;
    	 mem.next=right.next;
    	 right.next.prev=mem;
     }
     }
    



}
