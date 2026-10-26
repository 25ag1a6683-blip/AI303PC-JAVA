import java.util.*;

public class SetDemo {

    static class Student {
        private final String name;
        private final int marks;
        Student(String name, int marks) { this.name = name; this.marks = marks; }
        String getName() { return name; }
        int getMarks()   { return marks; }
        @Override public String toString() { return name + "(" + marks + ")"; }
    }

    static Set<Integer> readSet(Scanner sc, String name, int[] defaults) {
        while (true) {
            System.out.print("Enter elements of set " + name + " separated by spaces: ");
            try {
                String line = sc.nextLine().trim();
                Set<Integer> set = new TreeSet<>();
                for (String token : line.split("[\\s,]+")) {
                    set.add(Integer.parseInt(token));
                }
                return set;
            } catch (NumberFormatException e) {
                System.out.println("Please enter whole numbers only.");
            } catch (NoSuchElementException e) {
                System.out.println("No input available, using default set " + name);
                Set<Integer> set = new TreeSet<>();
                for (int d : defaults) set.add(d);
                return set;
            }
        }
    }

    public static void main(String[] args) {
        String[] data = {"Java", "C", "Python", "C", "Java", "Go", "Rust", "Python", "C", "Kotlin"};
        Set<String> hashSet = new HashSet<>();
        Set<String> linkedHashSet = new LinkedHashSet<>();
        Set<String> treeSet = new TreeSet<>();
        for (String item : data) {
            hashSet.add(item);          
            linkedHashSet.add(item);
            treeSet.add(item);
        }
        System.out.println("HashSet       : " + hashSet + "   (order not guaranteed)");
        System.out.println("LinkedHashSet : " + linkedHashSet + "   (insertion order)");
        System.out.println("TreeSet       : " + treeSet + "   (natural sorted order)");

        List<String> languages = new ArrayList<>(Arrays.asList("Java", "C", "Python", "C", "Go", "Java", "C"));
        Set<String> seen = new HashSet<>();
        Set<String> duplicates = new LinkedHashSet<>();   
        for (String lang : languages) {
            if (!seen.add(lang)) {                        
                duplicates.add(lang);
            }
        }
        System.out.println("Duplicates in list: " + duplicates);

        Set<Integer> a, b;
        try (Scanner sc = new Scanner(System.in)) {
            a = readSet(sc, "A", new int[]{2, 4, 6, 8, 10});
            b = readSet(sc, "B", new int[]{4, 8, 12});
        }
        Set<Integer> union = new TreeSet<>(a);
        union.addAll(b);                                   
        Set<Integer> intersection = new TreeSet<>(a);
        intersection.retainAll(b);                         
        Set<Integer> difference = new TreeSet<>(a);
        difference.removeAll(b);                           
        System.out.println("A = " + a + ", B = " + b);
        System.out.println("Union        : " + union);
        System.out.println("Intersection : " + intersection);
        System.out.println("Difference A-B: " + difference);

        TreeSet<String> names = new TreeSet<>(Arrays.asList("Ravi", "Anil", "Sita", "Bala", "Charan"));
        System.out.println("Names ascending : " + names);
        System.out.println("Names descending: " + names.descendingSet());

        Set<Student> students = new TreeSet<>(
                Comparator.comparingInt(Student::getMarks).thenComparing(Student::getName));
        students.add(new Student("Ravi", 78));
        students.add(new Student("Anil", 85));
        students.add(new Student("Sita", 92));
        students.add(new Student("Bala", 85));
        students.add(new Student("Charan", 66));
        System.out.println("Students by marks: " + students);
    }
}
