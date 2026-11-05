import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MemoryManagerTest {

    @Test
    void allocationAndSplitting() {
        MemoryManager manager = new MemoryManager(100);

        MemoryAllocation first = manager.requestMemory(20, "A");
        MemoryAllocation second = manager.requestMemory(30, "B");

        assertNotNull(first);
        assertEquals("A", first.getOwner());
        assertEquals(0, first.getPosition());
        assertEquals(20, first.getLength());
        assertNotNull(second);
        assertEquals("B", second.getOwner());
        assertEquals(20, second.getPosition());
        assertEquals(30, second.getLength());
    }

    @Test
    void firstFitAndRejectsARequest() {
        MemoryManager manager = new MemoryManager(100);
        MemoryAllocation a = manager.requestMemory(10, "A");
        MemoryAllocation b = manager.requestMemory(15, "B");
        manager.requestMemory(5, "C");
        MemoryAllocation d = manager.requestMemory(20, "D");
        manager.requestMemory(50, "E");

        manager.returnMemory(b);
        manager.returnMemory(d);

        assertNull(manager.requestMemory(30, "TooLarge"));
        MemoryAllocation small = manager.requestMemory(5, "Small");
        assertNotNull(small);
        assertEquals(10, small.getPosition()); // first free block, not the later one
        assertEquals(5, small.getLength());
    }

    @Test
    void joinsFreeBlocksOnBothSides() {
        MemoryManager manager = new MemoryManager(60);
        MemoryAllocation left = manager.requestMemory(20, "Left");
        MemoryAllocation middle = manager.requestMemory(15, "Middle");
        MemoryAllocation right = manager.requestMemory(25, "Right");

        manager.returnMemory(left);
        manager.returnMemory(right);
        manager.returnMemory(middle);

        MemoryAllocation whole = manager.requestMemory(60, "Whole");
        assertNotNull(whole);
        assertEquals(0, whole.getPosition());
        assertEquals(60, whole.getLength());
        assertNull(manager.requestMemory(1, "Extra"));
    }
}
