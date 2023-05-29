# Exam_Application
A tool for creating exams as an administrator, and taking them as a student.

Hey Everyone,
This is the first big project i've started, about 1 month into my Introduction to OOP Intensive Program.
I try to work on it as much as possible in my free time. <br>

A lot of the techniques & libraries used are things I haven't been taught in school, so I had to research on my own.
It is very likely parts of the code look quite amateur-ish, but regardless I am still proud of what I've been able to accomplish so far,
and I keep improving both the code and myself every day that I work on it.

# How It Works
This program uses Swing as a GUI, not by choice but because it was the only GUI i knew the basics of when I first started this project. <br>

User information is, as of *Version 11* which is when I'm currently writing this, stored in .txt file locally. I plan on switching this to
an online database in the future. <br>

When the program runs, it will ask the USER to login or register.
You can register as either an Admin or Student. Admins require an Admin Key (temp: 12345). <br>

Admins have access to Exam Creation tools, where they can create a new blank Exam and give it a name/subject. <br>
Once the exam is created, the admin can then click on the exam, and add questions/answers to the ExamSheet.
They can then save the exam as either visible or non-visible to students. <br>

Exams are stored as a .json file containing an ArrayList<Question> of all the exam's questions, where the Question class <br>
contains the question Name, answers, question type, right answer (as an index), and its real question number (essentially the question number
shown on the exam, normally index + 1 as question 1 is index 0). <br>
  
If an exam is saved as visible to students, it will store the .json in the FinishedExams folder.<br>
If an exam is saved as non-visible to students, it will store the .json in the UnfinishedExams folder.<br>
  
Student users can only see Exams that are marked as VISIBLE in the EXAM-LIST.txt.
