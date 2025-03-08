import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Usage: java Main [原文文件] [抄袭版论文文件] [答案文件]");
            System.exit(1);
        }

        String originalFilePath = args[0];
        String plagiarizedFilePath = args[1];
        String outputFilePath = args[2];

        try {
            String originalText = readFile(originalFilePath);
            String plagiarizedText = readFile(plagiarizedFilePath);

            double similarity = calculateSimilarity(originalText, plagiarizedText);
            writeResult(outputFilePath, similarity);

            System.out.println("查重完成，重复率为: " + String.format("%.2f", similarity));
        } catch (IOException e) {
            System.err.println("文件读写错误: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("程序异常: " + e.getMessage());
            System.exit(1);
        }
    }

    public static String readFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    public static double calculateSimilarity(String originalText, String plagiarizedText) {
        Map<String, Integer> originalWordCount = getWordCount(originalText);
        Map<String, Integer> plagiarizedWordCount = getWordCount(plagiarizedText);

        int totalWords = originalWordCount.size();
        int matchedWords = 0;

        for (String word : originalWordCount.keySet()) {
            if (plagiarizedWordCount.containsKey(word)) {
                matchedWords++;
            }
        }

        return (double) matchedWords / totalWords;
    }

    private static Map<String, Integer> getWordCount(String text) {
        Map<String, Integer> wordCount = new HashMap<>();
        String[] words = text.split("\\s+");

        for (String word : words) {
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }

        return wordCount;
    }

    public static void writeResult(String filePath, double similarity) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(String.format("%.2f", similarity));
        }
    }
}