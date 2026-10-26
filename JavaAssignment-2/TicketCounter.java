import java.util.*;

class Patient {
    private final String name;
    private final int emergencyLevel;

    Patient(String name, int emergencyLevel) {
        this.name = name;
        this.emergencyLevel = emergencyLevel;
    }

    int getEmergencyLevel() { return emergencyLevel; }

    @Override
    public String toString() {
        String level = emergencyLevel == 3 ? "Critical" : (emergencyLevel == 2 ? "Serious" : "Normal");
        return level + "-" + name;
    }
}

public class TicketCounter {

    static List<Patient> serveAll(Queue<Patient> queue) {
        List<Patient> order = new ArrayList<>();
        Patient p;
        while ((p = queue.poll()) != null) {
            order.add(p);
        }
        return order;
    }

    public static void main(String[] args) {
        System.out.println("--- Part 1: Ticket counter using ArrayDeque ---");
        Queue<String> counter = new ArrayDeque<>();
        counter.offer("Ravi");
        counter.offer("Sita");
        counter.offer("Anil");
        System.out.println("Queue: " + counter);
        System.out.println("Next customer: " + counter.peek());

        while (!counter.isEmpty()) {
            String served = counter.poll();
            System.out.println("Serving " + served + " -> Queue: " + counter);
        }
        String nobody = counter.poll();
        if (nobody == null) {
            System.out.println("No customer waiting (poll() returned null, no exception)");
        }

        System.out.println("\n--- Part 2: Same FIFO behaviour using LinkedList ---");
        Queue<String> linkedQueue = new LinkedList<>();
        linkedQueue.offer("Ravi");
        linkedQueue.offer("Sita");
        linkedQueue.offer("Anil");
        System.out.println("LinkedList queue: " + linkedQueue);
        System.out.println("Serving " + linkedQueue.poll() + " -> " + linkedQueue);

        Deque<String> deque = new LinkedList<>(linkedQueue);
        deque.offerFirst("VIP-Kiran");
        System.out.println("After offerFirst: " + deque);
        System.out.println("pollLast gives " + deque.pollLast() + " -> " + deque);
        System.out.println("ArrayDeque and LinkedList both implement Deque; PriorityQueue does not.");

        System.out.println("\n--- Part 3: Patients, FIFO vs PriorityQueue ---");
        Queue<Patient> fifo = new ArrayDeque<>();
        PriorityQueue<Patient> priority = new PriorityQueue<>(
                (a, b) -> Integer.compare(b.getEmergencyLevel(), a.getEmergencyLevel()));

        Patient[] arrivals = {
            new Patient("Charan", 1), new Patient("Amit", 3), new Patient("Bala", 2)
        };
        for (Patient p : arrivals) {
            fifo.offer(p);
            priority.offer(p);
        }
        System.out.println("Arrival order      : " + Arrays.toString(arrivals));
        System.out.println("Size before serving: FIFO = " + fifo.size() + ", PriorityQueue = " + priority.size());
        System.out.println("FIFO serving order : " + serveAll(fifo));
        System.out.println("Priority serving   : " + serveAll(priority));
        System.out.println("Final size         : FIFO = " + fifo.size() + ", PriorityQueue = " + priority.size());

        System.out.println("\nExplanation: FIFO serves in arrival order (first come, first served).");
        System.out.println("PriorityQueue serves by emergency level, so the Critical patient who arrived");
        System.out.println("second is served first. Both queues end with size 0 once everyone is served.");
    }
}
