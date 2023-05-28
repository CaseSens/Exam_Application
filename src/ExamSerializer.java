//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStreamWriter;
//
//public class ExamSerializer {
//
//    public static void serializeToJson(ExamSheet examSheet, String filePath, String changeFilePath) throws IOException {
//        // Create an ObjectMapper instance
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        // Convert the examSheet to a JSON string
//        String jsonString = objectMapper.writeValueAsString(examSheet);
//
//        // Save the JSON string to a file
//        FileOutputStream fileOut = new FileOutputStream(filePath + changeFilePath + examSheet.getSelectedExam().getNameOfExam() + ".json");
//        OutputStreamWriter writer = new OutputStreamWriter(fileOut);
//        writer.write(jsonString);
//        writer.close();
//        fileOut.close();
//        System.out.println("ExamSheet saved successfully as JSON.");
//    }
//}
