import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamList {
    static final File examsFile = new File("Exams/EXAM-LIST.txt");
    static int numberOfExams;
    static ArrayList<String> allExamNames = new ArrayList<>();
    static ArrayList<String> allExamSubjects = new ArrayList<>();
    static HashMap<String, String> nameToSubjectMap = new HashMap<>(); //reason why not just hashmap is because I want doubles of name/subject but not together
    static ArrayList<Exam> examArrayList = new ArrayList<>();
    static HashMap<Exam, Boolean> examVisibilityMap = new HashMap<>();
    static BufferedReader readerForTxtFile;
    static BufferedReader readerForExamFolders;

    public static void createExamMap() {
        try {
            readerForTxtFile = new BufferedReader(new FileReader(examsFile));

            String line;
            int i = 1;
            while ((line = readerForTxtFile.readLine()) != null) {
                String[] parts = line.split(" ");
                String keyName = parts[0];
                String valueSubject = parts[1];
                String visibleOrNot = "";
                if (parts.length > 2) {
                    visibleOrNot = parts[2];
                }

                Exam newExam = new Exam(keyName, valueSubject);
                if (visibleOrNot.contains("VISIBLE")) {
                    examVisibilityMap.put(newExam, true);
                } else {
                    examVisibilityMap.put(newExam, false);
                }

                nameToSubjectMap.put(keyName, valueSubject);
                examArrayList.add(i - 1, newExam);
                System.out.println(examArrayList.get(i - 1).getNameOfExam());
                i++;
                nameToSubjectMap.put(keyName, valueSubject);
                allExamNames.add(keyName);
                allExamSubjects.add(valueSubject);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(nameToSubjectMap);

    }

    public static void addVisibleToDatabase(Exam exam) throws IOException {
        List<String> updatedLines = new ArrayList<>();

        String line;
        readerForTxtFile = new BufferedReader(new FileReader(examsFile));
        while ((line = readerForTxtFile.readLine()) != null) {
            String[] parts = line.split(" ");
            String keyName = parts[0];
            String valueSubject = parts[1];
            String visibleOrNot = "";
            if (parts.length > 2) {
                visibleOrNot = parts[2];
            }

            if (keyName.equals(exam.getNameOfExam()) && valueSubject.equals(exam.getSubjectOfExam()) && visibleOrNot.isBlank()) {
                line += (" VISIBLE");
            }

            updatedLines.add(line);
        }
        readerForTxtFile.close();

        //write the updated lines back to the file
        BufferedWriter writer = new BufferedWriter(new FileWriter(examsFile));
        for (String updatedLine : updatedLines) {
            writer.write(updatedLine);
            writer.newLine();
            System.out.println("Visibility status updated successfully.");
        }
        writer.close();
    }

    public Exam getExamAtIndex(int index) {
        return examArrayList.get(index);
    }

    public int getIndexOfExam(Exam exam) {
        for (int i = 0; i < examArrayList.size(); i++) {
            if (examArrayList.get(i).equals(exam)) {
                return i; // Found the index for the specified Exam object
            }
        }
        return -1; // Exam not found in the ArrayList
    }

    public boolean doesExamExist(String examName, String examSubject) {
        for (String key : nameToSubjectMap.keySet()) { //for loop to ignore case
            if (key.equalsIgnoreCase(examName) && nameToSubjectMap.get(key).equalsIgnoreCase(examSubject)) {
                return true;
            }
        }
        return false;
    }

    public void addExamToList(Exam exam) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(examsFile, true));
            writer.println(exam.getNameOfExam() + " " + exam.getSubjectOfExam());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setNumberOfExamsOnFile() {
        try {
            readerForTxtFile = new BufferedReader(new FileReader(examsFile));
            String line;
            while ((line = readerForTxtFile.readLine()) != null) {
                numberOfExams++;
            }
            System.out.println("number of exams: " + numberOfExams);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getNumberOfExamsOnFile() {
        return numberOfExams;
    }

    public String getNameOfExamAtIndex(int index) {
        return allExamNames.get(index);
    }

    public String getSubjectOfExamAtIndex(int index) {
        return allExamSubjects.get(index);
    }

    public ArrayList<Exam> getAllExamsByNumberMap() {
        return examArrayList;
    }

    /**
     * simply prints out all exams
     */
    public void examListPerLine() {
        int index = 0;
        for (Exam exam: examArrayList) {
            System.out.println("Exam " + (index + 1) + ": " + exam.getNameOfExam());
        }
    }

    public ArrayList<Exam> getAllFinishedExams() {
        ArrayList<Exam> allFinishedExams = new ArrayList<>();
        for (Map.Entry<Exam, Boolean> entry : examVisibilityMap.entrySet()) {
            Exam key = entry.getKey();
            boolean value = entry.getValue();

            if (value) {
                allFinishedExams.add(key);
            }
        }

        return allFinishedExams;
    }

    public void setExamAsFinished(Exam exam) {
        examVisibilityMap.put(exam, true);
    }

    public static void deleteFolder(Path folderPath) throws IOException {
        Files.walkFileTree(folderPath, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }


}
