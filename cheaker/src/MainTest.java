import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainTest {

    public static void main(String[] args) {
        testCalculateSimilarityWithExactMatch();
        testCalculateSimilarityWithNoMatch();
        testCalculateSimilarityWithPartialMatch();
        testCalculateSimilarityWithEmptyText();
        testCalculateSimilarityWithReversedText();
        testReadFileWithValidPath();
        testReadFileWithInvalidPath();
        testWriteResultWithValidPath();
        testWriteResultWithInvalidPath();

        System.out.println("所有测试通过！");
    }

    static void testCalculateSimilarityWithExactMatch() {
        String originalText = "12345 abcde";
        String plagiarizedText = "12345 abcde";
        double similarity = Main.calculateSimilarity(originalText, plagiarizedText);
        assert similarity == 1.0 : "测试失败：完全相同的文本，相似度应为1.0";
        System.out.println("testCalculateSimilarityWithExactMatch 通过");
    }

    static void testCalculateSimilarityWithNoMatch() {
        String originalText = "12345 abcde";
        String plagiarizedText = "67890 fghij";
        double similarity = Main.calculateSimilarity(originalText, plagiarizedText);
        assert similarity == 0.0 : "测试失败：完全不同的文本，相似度应为0.0";
        System.out.println("testCalculateSimilarityWithNoMatch 通过");
    }

    static void testCalculateSimilarityWithPartialMatch() {
        String originalText = "12345 abcde";
        String plagiarizedText = "12345 fghij";
        double similarity = Main.calculateSimilarity(originalText, plagiarizedText);
        assert similarity > 0.0 && similarity < 1.0 : "测试失败：部分匹配的文本，相似度应在0.0到1.0之间";
        System.out.println("testCalculateSimilarityWithPartialMatch 通过");
    }

    static void testCalculateSimilarityWithEmptyText() {
        String originalText = "";
        String plagiarizedText = "12345 abcde";
        double similarity = Main.calculateSimilarity(originalText, plagiarizedText);
        assert similarity == 0.0 : "测试失败：原文为空，相似度应为0.0";
        System.out.println("testCalculateSimilarityWithEmptyText 通过");
    }

    static void testCalculateSimilarityWithReversedText() {
        String originalText = "12345 abcde";
        String plagiarizedText = "edcba 54321";
        double similarity = Main.calculateSimilarity(originalText, plagiarizedText);
        assert similarity == 0.0 : "测试失败：完全反转的文本，相似度应为0.0";
        System.out.println("testCalculateSimilarityWithReversedText 通过");
    }

    static void testReadFileWithValidPath() {
        String filePath = "test_read.txt";
        String content = "12345 abcde";
        try {
            Files.write(Paths.get(filePath), content.getBytes());
            String readContent = Main.readFile(filePath);
            assert readContent.equals(content) : "测试失败：读取的文件内容与写入内容不一致";
            System.out.println("testReadFileWithValidPath 通过");
        } catch (IOException e) {
            System.err.println("测试失败：文件读写异常 - " + e.getMessage());
        } finally {
            new File(filePath).delete();
        }
    }

    static void testReadFileWithInvalidPath() {
        String invalidFilePath = "invalid_path.txt";
        try {
            Main.readFile(invalidFilePath);
            assert false : "测试失败：读取无效路径时应抛出异常";
        } catch (IOException e) {
            System.out.println("testReadFileWithInvalidPath 通过");
        }
    }

    static void testWriteResultWithValidPath() {
        String filePath = "test_write.txt";
        double similarity = 0.75;
        try {
            Main.writeResult(filePath, similarity);
            String readContent = new String(Files.readAllBytes(Paths.get(filePath)));
            assert readContent.equals("0.75") : "测试失败：写入的文件内容与预期不一致";
            System.out.println("testWriteResultWithValidPath 通过");
        } catch (IOException e) {
            System.err.println("测试失败：文件读写异常 - " + e.getMessage());
        } finally {
            new File(filePath).delete();
        }
    }

    static void testWriteResultWithInvalidPath() {
        String invalidFilePath = "invalid/path/test_write.txt";
        double similarity = 0.75;
        try {
            Main.writeResult(invalidFilePath, similarity);
            assert false : "测试失败：写入无效路径时应抛出异常";
        } catch (IOException e) {
            System.out.println("testWriteResultWithInvalidPath 通过");
        }
    }
}