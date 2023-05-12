public class Main {
    public static void main(String[] args)
    {
        Exam examInfo = new Exam();
        examInfo.setNumberOfExamsOnFile();
        UserFileMap userFileMap = new UserFileMap();
        System.out.println(userFileMap.getStudentUsers().toString());
        LogOrRegister buildGUI = new LogOrRegister();
        buildGUI.buildLoginPage();

        while (!buildGUI.isLoggedIn) {
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("broken the loop");

    }
}