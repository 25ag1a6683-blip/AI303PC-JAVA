import java.util.Scanner;

public class StringAnalyzer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Input : ");
        String sentence = scanner.nextLine();

        System.out.println("Output:");

        String[] words = sentence.split(" ");
        int wordCount = words.length;
        System.out.println("Word Count: " + wordCount);

        int firstSpaceIdx = sentence.indexOf(" ");
        int lastSpaceIdx = sentence.lastIndexOf(" ");
        
        String firstWord = sentence.substring(0, firstSpaceIdx);
        String lastWord = sentence.substring(lastSpaceIdx + 1);
        
        System.out.println("First Word: " + firstWord);
        System.out.println("Last Word: " + lastWord);

        char targetChar = 'J';
        int firstJIndex = sentence.indexOf(targetChar);
        int lastJIndex = sentence.lastIndexOf(targetChar);
        
        System.out.println("First '" + targetChar + "' index: " + firstJIndex);
        System.out.println("Last '" + targetChar + "' index: " + lastJIndex);

        int comparisonResult = firstWord.compareTo(lastWord);
        System.out.println("Comparison (first vs last word): " + comparisonResult);

        char[] charArray = sentence.toCharArray();
        char firstChar = sentence.charAt(0);
        
        int asciiValue = (int) firstChar;
        String asciiString = String.valueOf(asciiValue);
        
        System.out.println("First character ASCII value: " + asciiString);

        scanner.close();
    }
}