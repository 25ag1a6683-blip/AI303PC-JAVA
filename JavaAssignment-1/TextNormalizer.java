public class TextNormalizer {

    public static String normalizeAndValidate(String input) {
    
        if (input == null) {
            return "EMPTY_STRING";
        }
        input = input.trim();

        if (input.isEmpty()) {
            return "EMPTY_STRING";
        }

        if (input.equalsIgnoreCase("admin")) {
            return "RESERVED_WORD";
        }

        input = input.replace("_", " ");

        String lowerCaseInput = input.toLowerCase();
        if (lowerCaseInput.startsWith("temp") && lowerCaseInput.endsWith("user")) {
            input += "_FLAGGED";
        }

        return input;
    }

    public static void main(String[] args) {
        // Test cases from the sample input/output
        System.out.println(normalizeAndValidate("  temp_John_user  ")); 
        System.out.println(normalizeAndValidate("  ADMIN  "));         
        System.out.println(normalizeAndValidate("   "));               
    }
}