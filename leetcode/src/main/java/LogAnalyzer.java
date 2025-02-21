import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/*
    grep "iOS" access.log | awk -f "|" '{print $1}' | sort | uniq -c
 */
public class LogAnalyzer {

    public static void main(String[] args) {
        // 文件路径
        String filePath = "leetcode/src/main/resources/access.log";

        // 用于存储字段及其出现次数的 Map
        Map<String, Integer> fieldCountMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // 逐行读取文件
            while ((line = br.readLine()) != null) {
                // 检查是否包含 "iOS"
                if (line.contains("iOS")) {
                    // 以 "|" 分割行
                    String[] fields = line.split("\\|");
                    if (fields.length > 0) {
                        // 提取第一个字段
                        String firstField = fields[0].trim();
                        // 更新字段计数
                        fieldCountMap.put(firstField, fieldCountMap.getOrDefault(firstField, 0) + 1);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 对字段按字母顺序排序
        TreeMap<String, Integer> sortedMap = new TreeMap<>(fieldCountMap);

        // 输出结果
        for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
            System.out.println(entry.getValue() + " " + entry.getKey());
        }
    }
}

/*
public class LogAnalyzer{

    public static void main(String[] args) {
        String filePath = "leetcode/src/main/resources/access.log";

        // 使用 ConcurrentHashMap 支持多线程
        Map<String, Long> fieldCountMap = new ConcurrentHashMap<>();

        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            lines.parallel() // 启用并行流
                    .filter(line -> line.contains("iOS")) // 过滤包含 "iOS" 的行
                    .map(line -> line.split("\\|")[0].trim()) // 提取第一个字段
                    .forEach(field -> fieldCountMap.merge(field, 1L, Long::sum)); // 统计字段出现次数

            // 按字段排序并输出
            fieldCountMap.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(entry -> System.out.println(entry.getValue() + " " + entry.getKey()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

 */