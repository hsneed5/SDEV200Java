import java.io.File;
import java.util.*;

public class CountKeywords {
    public static void main(String[] args) throws Exception {

        if (args.length != 1) {
            System.out.println("Usage: java CountKeywords filename.java");
            return;
        }

        File file = new File(args[0]);
        if (!file.exists()) {
            System.out.println("File " + args[0] + " does not exist");
            return;
        }

        System.out.println("The number of keywords in " + args[0] +
                " is " + countKeywords(file));
    }


    public static int countKeywords(File file) throws Exception {

        String[] keywordString = {
                "abstract", "assert", "boolean", "break", "byte", "case",
                "catch", "char", "class", "const", "continue", "default",
                "do", "double", "else", "enum", "extends", "for", "final",
                "finally", "float", "goto", "if", "implements", "import",
                "instanceof", "int", "interface", "long", "native", "new",
                "package", "private", "protected", "public", "return",
                "short", "static", "strictfp", "super", "switch",
                "synchronized", "this", "throw", "throws", "transient",
                "try", "void", "volatile", "while", "true", "false", "null"
        };

        Set<String> keywords = new HashSet<>(Arrays.asList(keywordString));
        int count = 0;

        Scanner input = new Scanner(file);

        boolean inBlockComment = false;

        while (input.hasNextLine()) {
            String line = input.nextLine();

            StringBuilder clean = new StringBuilder();
            int i = 0;

            while (i < line.length()) {

                if (inBlockComment) {
                    if (i < line.length() - 1 && line.charAt(i) == '*' && line.charAt(i+1) == '/') {
                        inBlockComment = false;
                        i += 2;
                    } else {
                        i++;
                    }
                    continue;
                }


                if (i < line.length() - 1 && line.charAt(i) == '/' && line.charAt(i+1) == '/') {
                    break; // ignore rest of line
                }

                if (i < line.length() - 1 && line.charAt(i) == '/' && line.charAt(i+1) == '*') {
                    inBlockComment = true;
                    i += 2;
                    continue;
                }


                if (line.charAt(i) == '"') {
                    i++;
                    while (i < line.length() && line.charAt(i) != '"') {

                        if (line.charAt(i) == '\\' && i < line.length() - 1)
                            i += 2;
                        else
                            i++;
                    }
                    i++; 
                    continue;
                }

                clean.append(line.charAt(i));
                i++;
            }


            Scanner wordScan = new Scanner(clean.toString());
            while (wordScan.hasNext()) {
                String word = wordScan.next();
                if (keywords.contains(word))
                    count++;
            }
        }

        return count;
    }
}
