import java.util.*;
import java.util.stream.Collectors;

public class MapDemo {

    static class Student {
        private final String rollNo;
        private final String name;

        Student(String rollNo, String name) { this.rollNo = rollNo; this.name = name; }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Student)) return false;
            return rollNo.equals(((Student) obj).rollNo);
        }

        @Override
        public int hashCode() { return rollNo.hashCode(); }

        @Override
        public String toString() { return rollNo + "-" + name; }
    }

    static void printMap(String title, Map<String, Integer> map) {
        StringBuilder sb = new StringBuilder(title + ": ");
        for (Map.Entry<String, Integer> e : map.entrySet()) {
            sb.append(e.getKey()).append(":").append(e.getValue()).append("  ");
        }
        System.out.println(sb.toString().trim());
    }

    public static void main(String[] args) {
        Map<String, Integer> marks = new HashMap<>();
        marks.put("S101", 75);
        marks.put("S102", 92);
        marks.put("S103", 67);
        marks.put("S104", 88);
        marks.put("S105", 79);
        System.out.println("Marks of S102 = " + marks.get("S102"));
        System.out.println("Contains S103? " + marks.containsKey("S103"));
        System.out.println("Removed S105, its marks were " + marks.remove("S105"));
        System.out.println("Size = " + marks.size());

        Iterator<String> keyIt = marks.keySet().iterator();
        System.out.print("keySet()  : ");
        while (keyIt.hasNext()) System.out.print(keyIt.next() + " ");
        System.out.println();

        Iterator<Integer> valueIt = marks.values().iterator();
        System.out.print("values()  : ");
        while (valueIt.hasNext()) System.out.print(valueIt.next() + " ");
        System.out.println();

        Iterator<Map.Entry<String, Integer>> entryIt = marks.entrySet().iterator();
        System.out.print("entrySet(): ");
        while (entryIt.hasNext()) {
            Map.Entry<String, Integer> entry = entryIt.next();
            System.out.print(entry.getKey() + "=" + entry.getValue() + " ");
        }
        System.out.println();

        String sentence = "Java is easy and Java is fun";
        Map<String, Integer> frequency = new HashMap<>();
        if (sentence != null && !sentence.trim().isEmpty()) {
            for (String word : sentence.toLowerCase().split("\\W+")) {
                frequency.put(word, frequency.getOrDefault(word, 0) + 1);
            }
        }
        Map<String, Integer> repeated = new HashMap<>();
        for (Map.Entry<String, Integer> e : frequency.entrySet()) {
            if (e.getValue() > 1) repeated.put(e.getKey(), e.getValue());
        }
        System.out.println("Word frequency (count > 1): " + repeated);

        Map<String, Integer> merit = marks.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (x, y) -> x, LinkedHashMap::new));
        printMap("Merit list (desc)", merit);

        Map<String, Integer> linked = new LinkedHashMap<>();
        Map<String, Integer> tree = new TreeMap<>();
        String[] rolls = {"S103", "S101", "S104", "S102"};
        int[] scores   = {67, 75, 88, 92};
        for (int i = 0; i < rolls.length; i++) {
            linked.put(rolls[i], scores[i]);
            tree.put(rolls[i], scores[i]);
        }
        printMap("LinkedHashMap (insertion order)", linked);
        printMap("TreeMap (sorted by roll number)", tree);
        System.out.println("For a roll-number-wise report I would choose TreeMap, because it keeps the keys");
        System.out.println("sorted automatically (O(log n) put/get), so no extra sorting step is needed.");

        Map<Student, Integer> studentMarks = new HashMap<>();
        studentMarks.put(new Student("S101", "Ravi"), 75);
        studentMarks.put(new Student("S102", "Sita"), 92);
        Student lookup = new Student("S101", "Ravi");
        System.out.println("Marks of " + lookup + " (new object) = " + studentMarks.get(lookup));
        studentMarks.put(new Student("S101", "Ravi K"), 80);
        System.out.println("After put with same roll number: size = " + studentMarks.size()
                + ", marks = " + studentMarks.get(lookup));
    }
}