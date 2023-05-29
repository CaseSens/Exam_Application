import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ExamSerializer {

    /**
     * Serializes an ArrayList<Question> as a JSON file
     * @param selectedExam
     * @param filePath
     * @throws IOException
     */
    public static void serializeQuestionsToJson(Exam selectedExam, String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                System.out.println("Folder created: " + filePath);
            } catch (IOException e) {
                System.err.println("Failed to create folder: " + filePath);
                e.printStackTrace();
                return;
            }
        } else {
            System.out.println("Folder found");
        }
        ObjectMapper objectMapper = new ObjectMapper();

        // Convert the examSheet to a JSON string
        String jsonString = objectMapper.writeValueAsString(selectedExam.getAllExamQuestions());

        // Save the JSON string to a file
        FileOutputStream fileOut = new FileOutputStream(filePath + selectedExam.getNameOfExam() + ".json");
        OutputStreamWriter writer = new OutputStreamWriter(fileOut);
        writer.write(jsonString);
        writer.close();
        fileOut.close();
        System.out.println("Exam Question/Answers saved successfully as JSON.");
    }

    /**
     * Deserializes JSON file as an ArrayList<Question>.
     * @param filepath
     * @return
     * @throws IOException
     */
    public static ArrayList<Question> deserializeQuestionsFromJson(String filepath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        try (FileInputStream fileIn = new FileInputStream(filepath);
             InputStreamReader reader = new InputStreamReader(fileIn)) {
            System.out.println(filepath);
                ArrayList<Question> questions = objectMapper.readValue(reader, new TypeReference<>() {});
                System.out.println("Deserialization successful");
                return questions;

        } catch (IOException e) {
            System.out.println("Deserialization failed.");
            throw e;
        }
    }
}
