import java.util.Arrays;

public class StringProcessor {

    public static void main(String[] args) {
        String str1 = "A man a plan a canal Panama";
        String str2 = "listen";
        String str3 = "silent";
        String substringToCheck = "plan";
        String cleanedStr1 = str1.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        
        boolean isAlphanumeric = cleanedStr1.matches("^[a-zA-Z0-9]*$");
        
        String reversedStr1 = new StringBuilder(cleanedStr1).reverse().toString();
        boolean isPalindrome = cleanedStr1.equals(reversedStr1);

        boolean isAnagram = checkAnagram(str2, str3);

        boolean containsSubstring = str1.contains(substringToCheck);

        String reportLine1 = String.format("Palindrome Check: \"%s\" -> %b", str1, isPalindrome);
        String reportLine2 = String.format("Anagram Check: \"%s\" & \"%s\" -> %b", str2, str3, isAnagram);
        
        String summaryFormat = String.format("[Palindrome: %b | Anagram: %b]", isPalindrome, isAnagram);
        String reportLine3 = String.format("Formatted Report: %s", summaryFormat);
        
        String reportLine4 = String.format("Contains '%s': %b", substringToCheck, containsSubstring);

        String literalStr = "PalindromeAnagram";
        String constructedStr = new String("PalindromeAnagram");
        
        String internedStr = constructedStr.intern();
        boolean isPoolShared = (literalStr == internedStr);
        boolean hashCodesEqual = (literalStr.hashCode() == internedStr.hashCode());
        
        String reportLine5 = String.format("HashCodes equal for interned strings: %b", (hashCodesEqual && isPoolShared));

        String finalOutput = String.join(
            "\n", 
            reportLine1, 
            reportLine2, 
            reportLine3, 
            reportLine4, 
            reportLine5
        );

        System.out.println("Output:");
        System.out.println(finalOutput);
    }

    private static boolean checkAnagram(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }
        char[] array1 = s1.toLowerCase().toCharArray();
        char[] array2 = s2.toLowerCase().toCharArray();
        Arrays.sort(array1);
        Arrays.sort(array2);
        return Arrays.equals(array1, array2);
    }
}