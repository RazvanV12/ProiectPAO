import java.sql.*;
import java.util.*;

import com.mysql.cj.jdbc.Driver;


// Tipuri Obiecte - Curs, Utilizator, Instructor, Cursant, Quiz, Lectie, RaportPerformanta, GrupDeStudiu
// Actiuni/Interogari ( se gasesc in clasa E_LearningServices ):
/*
    - Adaugare Curs
    - Stergere Curs
    - Inrolarea unui Cursant
    - Inrolarea unui Instructor
    - Obtinerea listei de cursuri disponibile pentru un Cursant (listaCursuri)
    - Cautarea unui Curs dupa Titlu sau Categorie
    - Crearea unui grup de studiu cu un instructor care preda cursul pentru lectiile introduse
    - Obtinerea Quizurilor unui Instructor dat
    - Adaugarea unui lectii date intr-un curs dat

    listaCursanti si listaInstructori sunt sortate dupa nume
 */

// Clasa Curs
class Curs {
    private  String titlu;
    private  String categorie;
    private List<Quiz> quizList;
    private List<Lectie> lectieList;
    private Instructor instructor;
    public Instructor getInstructor() {
        return instructor;
    }
    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }
    public List<Lectie> getLectieList() {
        return lectieList;
    }
    public void setLectieList(List<Lectie> lectieList) {
        this.lectieList = lectieList;
    }

    public void addLectie(Lectie lectie) {
        lectieList.add(lectie);
    }

    public void setTitlu(String titlu){
        this.titlu = titlu;
    }
    public void setCategorie(String categorie){
        this.categorie = categorie;
    }

    public void removeLectie(Lectie lectie) {
        lectieList.remove(lectie);
    }
    public Curs(String titlu, String categorie) {
        this.titlu = titlu;
        this.categorie = categorie;
        quizList = new ArrayList<>();
        lectieList = new ArrayList<>();
        instructor = null;
    }

    public Curs(String titlu, String categorie, Instructor instructor) {
        this.titlu = titlu;
        this.categorie = categorie;
        quizList = new ArrayList<>();
        lectieList = new ArrayList<>();
        this.instructor = instructor;
    }

    public Curs(String titlu, String categorie, List<Quiz> quizList, List<Lectie> lectieList, Instructor instructor) {
        this.titlu = titlu;
        this.categorie = categorie;
        this.quizList = quizList;
        this.lectieList = lectieList;
        this.instructor = instructor;
    }
    public String getTitlu() {
        return titlu;
    }
    public String getCategorie() {
        return categorie;
    }
    public List<Quiz> getQuizList() {
        return quizList;
    }
    public void setQuizList(List<Quiz> quizList) {
        this.quizList = quizList;
    }
    public void addQuiz(Quiz quiz) {
        quizList.add(quiz);
    }
    public void printCurs() {
        System.out.println("Titlu: " + titlu);
        System.out.println("Categorie: " + categorie);
        System.out.println("Instructor: " + instructor.getNume() + " " + instructor.getPrenume());
        System.out.println("Lectii: ");
        for (Lectie lectie : lectieList) {
            System.out.println(lectie.getContinutLectie());
        }
        System.out.println("Quizuri: ");
        for (Quiz quiz : quizList) {
            System.out.println(quiz.getNume());
        }
    }
}

// Clasa Utilizator
class Utilizator {
    private String nume;
    private String prenume;
    public Utilizator(String nume, String prenume) {
        this.nume = nume;
        this.prenume = prenume;
    }
    public String getNume() {
        return nume;
    }
    public String getPrenume() {
        return prenume;
    }
    public void setNume(String nume) {
        this.nume = nume;
    }
    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }
    public void printUtilizator() {
        System.out.println("Nume: " + nume);
        System.out.println("Prenume: " + prenume);
    }
}

// Clasa Instructor, moștenită din Utilizator
class Instructor extends Utilizator {
    private List<Curs> cursuriPredate;
    public List<Curs> getCursuri() {
        return cursuriPredate;
    }
    public void setCursuri(List<Curs> cursuri) {
        this.cursuriPredate = cursuri;
    }
    public Instructor(String nume, String prenume) {
        super(nume, prenume);
        cursuriPredate = new ArrayList<>();
    }
    public void addCurs(Curs curs) {
        cursuriPredate.add(curs);
    }
    public void removeCurs(Curs curs) {
        cursuriPredate.remove(curs);
    }
    public void printInstructor() {
        System.out.println("Nume: " + getNume());
        System.out.println("Prenume: " + getPrenume());
        System.out.println("Cursuri predate: ");
        for (Curs curs : cursuriPredate) {
            System.out.println(curs.getTitlu());
        }
    }
}

// Clasa Cursant, moștenită din Utilizator
class Cursant extends Utilizator {
    private List<Curs> cursuriInscrise;
    private int notaCurenta;
    public int getNotaCurenta() {
        return notaCurenta;
    }
    public void setNotaCurenta(int notaCurenta) {
        this.notaCurenta = notaCurenta;
    }
    public List<Curs> getCursuri() {
        return cursuriInscrise;
    }
    public void setCursuri(List<Curs> cursuri) {
        this.cursuriInscrise = cursuri;
    }
    public Cursant(String nume, String prenume) {
        super(nume, prenume);
        cursuriInscrise = new ArrayList<>();
        notaCurenta = 0;
    }
    public void addCurs(Curs curs) {
        cursuriInscrise.add(curs);
    }
    public void removeCurs(Curs curs) {
        cursuriInscrise.remove(curs);
    }
    public void printCursant() {
        System.out.println("Nume: " + getNume());
        System.out.println("Prenume: " + getPrenume());
        System.out.println("Cursuri inscrise: ");
        for (Curs curs : cursuriInscrise) {
            System.out.println(curs.getTitlu());
        }
        System.out.println("Nota curenta: " + notaCurenta);
    }
}

// Clasa Quiz
class Quiz {
    private String nume;
    private List<String> questions;
    private List<String> answers;
    public List<String> getQuestions() {
        return questions;
    }
    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }
    public List<String> getAnswers() {
        return answers;
    }
    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public Quiz(String nume, List<String> questions, List<String> answers) {
        this.nume = nume;
        this.questions = questions;
        this.answers = answers;
    }
    public Quiz(String nume) {
        this.nume = nume;
        questions = new ArrayList<>();
        answers = new ArrayList<>();
    }
    public String getNume() {
        return nume;
    }
    public void addQuestion(String question) {
        questions.add(question);
    }
    public void addAnswer(String answer) {
        answers.add(answer);
    }
    public void removeQuestion(String question) {
        questions.remove(question);
    }
    public void removeAnswer(String answer) {
        answers.remove(answer);
    }
    public void printQuiz() {
        System.out.println("Nume: " + nume);
        System.out.println("Intrebari: ");
        for (String question : questions) {
            System.out.println(question);
        }
        System.out.println("Raspunsuri: ");
        for (String answer : answers) {
            System.out.println(answer);
        }
    }
}

// Clasa E_LearningService - serviciul care expune operațiile sistemului
class E_LearningService {
    private static E_LearningService instance;
    private List<Curs> listaCursuri;
    private Set<Utilizator> listaUtilizatori;

    private List<Cursant> listaCursanti;

    private List<Instructor> listaInstructori;
    private Map<Curs, List<Quiz>> mapQuizuri;
    public E_LearningService() {
        listaCursuri = new ArrayList<>();
        listaUtilizatori = new HashSet<>();
        mapQuizuri = new HashMap<>();
        listaInstructori = new ArrayList<>();
        listaCursanti = new ArrayList<>();
    }
    public static synchronized E_LearningService getInstance() {
        if (instance == null) {
            instance = new E_LearningService();
        }
        return instance;
    }

    public E_LearningService(List<Curs> listaCursuri, Set<Utilizator> listaUtilizatori, List<Cursant> listaCursanti,
            List<Instructor> listaInstructori, Map<Curs, List<Quiz>> mapQuizuri) {
        this.listaCursuri = listaCursuri;
        this.listaUtilizatori = listaUtilizatori;
        this.listaCursanti = listaCursanti;
        this.listaInstructori = listaInstructori;
        this.mapQuizuri = mapQuizuri;

        listaCursanti.sort((o1, o2) -> {
            int nume = o1.getNume().compareTo(o2.getNume());
            if (nume == 0) {
                return o1.getPrenume().compareTo(o2.getPrenume());
            }
            return nume;
        });

        listaInstructori.sort((o1, o2) -> {
            int nume = o1.getNume().compareTo(o2.getNume());
            if (nume == 0) {
                return o1.getPrenume().compareTo(o2.getPrenume());
            }
            return nume;
        });
    }
    public List<Curs> getListaCursuri() {
        return listaCursuri;
    }
    public void setListaCursuri(List<Curs> listaCursuri) {
        this.listaCursuri = listaCursuri;
    }
    public Set<Utilizator> getListaUtilizatori() {
        return listaUtilizatori;
    }
    public void setListaUtilizatori(Set<Utilizator> listaUtilizatori) {
        this.listaUtilizatori = listaUtilizatori;
    }
    public List<Cursant> getListaCursanti() {
        return listaCursanti;
    }
    public void setListaCursanti(List<Cursant> listaCursanti) {
        this.listaCursanti = listaCursanti;
    }
    public List<Instructor> getListaInstructori() {
        return listaInstructori;
    }
    public void setListaInstructori(List<Instructor> listaInstructori) {
        this.listaInstructori = listaInstructori;
    }
    public Map<Curs, List<Quiz>> getMapQuizuri() {
        return mapQuizuri;
    }
    public void setMapQuizuri(Map<Curs, List<Quiz>> mapQuizuri) {
        this.mapQuizuri = mapQuizuri;
    }
//    public int getCursantId(String nume, String prenume) {
//        int cursantId = -1;
//        String url = "jdbc:mysql://localhost:3306/paoproiect";
//        String username = "root";
//        String password = "Muffin2325";
//
//        try (Connection connection = DriverManager.getConnection(url, username, password)) {
//            String schemaName = "paoproiect";
//            connection.setSchema(schemaName);
//            String query = "SELECT id FROM cursanti WHERE nume = ? AND prenume = ?";
//            try (PreparedStatement statement = connection.prepareStatement(query)) {
//                statement.setString(1, nume);
//                statement.setString(2, prenume);
//                try (ResultSet resultSet = statement.executeQuery()) {
//                    if (resultSet.next()) {
//                        cursantId = resultSet.getInt("id");
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return cursantId;
//    }
//    public int getUtilizatorId(String nume, String prenume) {
//        int utilizatorId = -1;
//        String url = "jdbc:mysql://localhost:3306/paoproiect";
//        String username = "root";
//        String password = "Muffin2325";
//
//        try (Connection connection = DriverManager.getConnection(url, username, password)) {
//            String schemaName = "paoproiect";
//            connection.setSchema(schemaName);
//            String query = "SELECT id FROM Utilizatori WHERE nume = ? AND prenume = ?";
//            try (PreparedStatement statement = connection.prepareStatement(query)) {
//                statement.setString(1, nume);
//                statement.setString(2, prenume);
//                try (ResultSet resultSet = statement.executeQuery()) {
//                    if (resultSet.next()) {
//                        utilizatorId = resultSet.getInt("id");
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return utilizatorId;
//    }
//    public int getInstructorId(String nume, String prenume) {
//        int instructorId = -1;
//        String url = "jdbc:mysql://localhost:3306/paoproiect";
//        String username = "root";
//        String password = "Muffin2325";
//
//        try (Connection connection = DriverManager.getConnection(url, username, password)) {
//            String schemaName = "paoproiect";
//            connection.setSchema(schemaName);
//            String query = "SELECT id FROM Instructori WHERE nume = ? AND prenume = ?";
//            try (PreparedStatement statement = connection.prepareStatement(query)) {
//                statement.setString(1, nume);
//                statement.setString(2, prenume);
//                try (ResultSet resultSet = statement.executeQuery()) {
//                    if (resultSet.next()) {
//                        instructorId = resultSet.getInt("id");
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return instructorId;
//    }
//    public int getCursId(String categorie, String titlu) {
//        int cursId = -1;
//        String url = "jdbc:mysql://localhost:3306/paoproiect";
//        String username = "root";
//        String password = "Muffin2325";
//
//        try (Connection connection = DriverManager.getConnection(url, username, password)) {
//            String schemaName = "paoproiect";
//            connection.setSchema(schemaName);
//            String query = "SELECT id FROM Cursuri WHERE categorie = ? AND titlu = ?";
//            try (PreparedStatement statement = connection.prepareStatement(query)) {
//                statement.setString(1, categorie);
//                statement.setString(2, titlu);
//                try (ResultSet resultSet = statement.executeQuery()) {
//                    if (resultSet.next()) {
//                        cursId = resultSet.getInt("id");
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return cursId;
//    }
//
//    public void adaugaCurs(List<Curs> cursuri) {
//        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS Cursuri (" +
//                "id INT AUTO_INCREMENT PRIMARY KEY," +
//                "categorie VARCHAR(50) NOT NULL," +
//                "titlu VARCHAR(50) NOT NULL" +
//                ")";
//
//        String sqlInsert = "INSERT INTO Cursuri (categorie, titlu) VALUES (?, ?)";
//        String url = "jdbc:mysql://localhost:3306/paoproiect";
//        String username = "root";
//        String password = "Muffin2325";
//
//        try (Connection connection = DriverManager.getConnection(url, username, password);
//             PreparedStatement createTableStatement = connection.prepareStatement(sqlCreateTable);
//             PreparedStatement insertStatement = connection.prepareStatement(sqlInsert)) {
//            String schemaName = "paoproiect";
//            connection.setSchema(schemaName);
//
//            // Creare tabela Cursuri (dacă nu există deja)
//            createTableStatement.executeUpdate();
//
//            // Adăugare cursuri în tabela cursuri
//            for (Curs curs : cursuri) {
//                insertStatement.setString(1, curs.getCategorie());
//                insertStatement.setString(2, curs.getTitlu());
//                insertStatement.executeUpdate();
//            }
//
//            System.out.println("Utilizatorii au fost adăugați în tabela Utilizatori.");
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    public void adaugaUtilizator(List<Utilizator> utilizatori) {
//        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS Utilizatori (" +
//                "id INT AUTO_INCREMENT PRIMARY KEY," +
//                "nume VARCHAR(50) NOT NULL," +
//                "prenume VARCHAR(50) NOT NULL" +
//                ")";
//
//        String sqlInsert = "INSERT INTO Utilizatori (nume, prenume) VALUES (?, ?)";
//        String url = "jdbc:mysql://localhost:3306/paoproiect";
//        String username = "root";
//        String password = "Muffin2325";
//
//        try (Connection connection = DriverManager.getConnection(url, username, password);
//             PreparedStatement createTableStatement = connection.prepareStatement(sqlCreateTable);
//             PreparedStatement insertStatement = connection.prepareStatement(sqlInsert)) {
//            String schemaName = "paoproiect";
//            connection.setSchema(schemaName);
//
//            // Creare tabela Utilizatori (dacă nu există deja)
//            createTableStatement.executeUpdate();
//
//            // Adăugare utilizator în tabela Utilizatori
//            for (Utilizator user : utilizatori) {
//                insertStatement.setString(1, user.getNume());
//                insertStatement.setString(2, user.getPrenume());
//                insertStatement.executeUpdate();
//            }
//
//            System.out.println("Utilizatorii au fost adăugați în tabela Utilizatori.");
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    public void adaugaCursantiInTabela(List<Cursant> cursanti) {
//        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS Cursanti (" +
//                "id INT AUTO_INCREMENT PRIMARY KEY," +
//                "nume VARCHAR(50) NOT NULL," +
//                "prenume VARCHAR(50) NOT NULL" +
//                ")";
//
//        String sqlInsert = "INSERT INTO Cursanti (nume, prenume) VALUES (?, ?)";
//        String url = "jdbc:mysql://localhost:3306/paoproiect";
//        String username = "root";
//        String password = "Muffin2325";
//
//        try (Connection connection = DriverManager.getConnection(url, username, password);
//             PreparedStatement createTableStatement = connection.prepareStatement(sqlCreateTable);
//             PreparedStatement insertStatement = connection.prepareStatement(sqlInsert)) {
//            String schemaName = "paoproiect";
//            connection.setSchema(schemaName);
//
//            // Creare tabela Cursanti (dacă nu există deja)
//            createTableStatement.executeUpdate();
//
//            // Adăugare cursanți în tabela Cursanti
//            for (Cursant cursant : cursanti) {
//                insertStatement.setString(1, cursant.getNume());
//                insertStatement.setString(2, cursant.getPrenume());
//                insertStatement.executeUpdate();
//            }
//
//            System.out.println("Cursanții au fost adăugați în tabela Cursanti.");
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    public void deleteCursant(Cursant cursant) {
//        String sqlDelete = "DELETE FROM Cursanti WHERE nume = ? AND prenume = ?";
//        String url = "jdbc:mysql://localhost:3306/paoproiect";
//        String username = "root";
//        String password = "Muffin2325";
//
//        try (Connection connection = DriverManager.getConnection(url, username, password);
//             PreparedStatement deleteStatement = connection.prepareStatement(sqlDelete)) {
//            String schemaName = "paoproiect";
//            connection.setSchema(schemaName);
//
//            deleteStatement.setString(1, cursant.getNume());
//            deleteStatement.setString(2, cursant.getPrenume());
//            int rowsDeleted = deleteStatement.executeUpdate();
//
//            if (rowsDeleted > 0) {
//                System.out.println("Cursantul " + cursant.getNume() + " " + cursant.getPrenume() + " a fost șters cu succes.");
//            } else {
//                System.out.println("Nu există cursant cu numele " + cursant.getNume() + " " + cursant.getPrenume() + ".");
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    public void deleteUtilizator(Utilizator utilizator) {
//        String sqlDelete = "DELETE FROM Utilizatori WHERE nume = ? AND prenume = ?";
//        String url = "jdbc:mysql://localhost:3306/paoproiect";
//        String username = "root";
//        String password = "Muffin2325";
//
//        try (Connection connection = DriverManager.getConnection(url, username, password);
//             PreparedStatement deleteStatement = connection.prepareStatement(sqlDelete)) {
//            String schemaName = "paoproiect";
//            connection.setSchema(schemaName);
//
//            deleteStatement.setString(1, utilizator.getNume());
//            deleteStatement.setString(2, utilizator.getPrenume());
//            int rowsDeleted = deleteStatement.executeUpdate();
//
//            if (rowsDeleted > 0) {
//                System.out.println("Cursantul " + utilizator.getNume() + " " + utilizator.getPrenume() + " a fost șters cu succes.");
//            } else {
//                System.out.println("Nu există cursant cu numele " + utilizator.getNume() + " " + utilizator.getPrenume() + ".");
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void deleteInstructor(Instructor instructor) {
//        String sqlDelete = "DELETE FROM Instructori WHERE nume = ? AND prenume = ?";
//        String url = "jdbc:mysql://localhost:3306/paoproiect";
//        String username = "root";
//        String password = "Muffin2325";
//
//        try (Connection connection = DriverManager.getConnection(url, username, password);
//             PreparedStatement deleteStatement = connection.prepareStatement(sqlDelete)) {
//            String schemaName = "paoproiect";
//            connection.setSchema(schemaName);
//
//            deleteStatement.setString(1, instructor.getNume());
//            deleteStatement.setString(2, instructor.getPrenume());
//            int rowsDeleted = deleteStatement.executeUpdate();
//
//            if (rowsDeleted > 0) {
//                System.out.println("Cursantul " + instructor.getNume() + " " + instructor.getPrenume() + " a fost șters cu succes.");
//            } else {
//                System.out.println("Nu există cursant cu numele " + instructor.getNume() + " " + instructor.getPrenume() + ".");
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    public void deleteCurs(Curs curs) {
//        String sqlDelete = "DELETE FROM Cursuri WHERE categorie = ? AND titlu = ?";
//        String url = "jdbc:mysql://localhost:3306/paoproiect";
//        String username = "root";
//        String password = "Muffin2325";
//
//        try (Connection connection = DriverManager.getConnection(url, username, password);
//             PreparedStatement deleteStatement = connection.prepareStatement(sqlDelete)) {
//            String schemaName = "paoproiect";
//            connection.setSchema(schemaName);
//
//            deleteStatement.setString(1, curs.getCategorie());
//            deleteStatement.setString(2, curs.getTitlu());
//            int rowsDeleted = deleteStatement.executeUpdate();
//
//            if (rowsDeleted > 0) {
//                System.out.println("Cursantul " + curs.getCategorie() + " " + curs.getTitlu() + " a fost șters cu succes.");
//            } else {
//                System.out.println("Nu există cursant cu numele " + curs.getCategorie() + " " + curs.getTitlu() + ".");
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//
//    public void adaugaInstructor(List<Instructor> instructori) {
//        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS Utilizatori (" +
//                "id INT AUTO_INCREMENT PRIMARY KEY," +
//                "nume VARCHAR(50) NOT NULL," +
//                "prenume VARCHAR(50) NOT NULL" +
//                ")";
//
//        String sqlInsert = "INSERT INTO Instructori (nume, prenume) VALUES (?, ?)";
//        String url = "jdbc:mysql://localhost:3306/paoproiect";
//        String username = "root";
//        String password = "Muffin2325";
//
//        try (Connection connection = DriverManager.getConnection(url, username, password);
//             PreparedStatement createTableStatement = connection.prepareStatement(sqlCreateTable);
//             PreparedStatement insertStatement = connection.prepareStatement(sqlInsert)) {
//            String schemaName = "paoproiect";
//            connection.setSchema(schemaName);
//
//            // Creare tabela Instructori (dacă nu există deja)
//            createTableStatement.executeUpdate();
//
//            // Adăugare instrcutor în tabela Instructori
//            for (Instructor instructor : instructori) {
//                insertStatement.setString(1, instructor.getNume());
//                insertStatement.setString(2, instructor.getPrenume());
//                insertStatement.executeUpdate();
//            }
//
//            System.out.println("Utilizatorii au fost adăugați în tabela Utilizatori.");
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void adaugaQuizuriInTabela(List<Quiz> quizList) {
//        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS Quiz (" +
//                "id INT AUTO_INCREMENT PRIMARY KEY," +
//                "nume VARCHAR(100) NOT NULL" +
//                ")";
//
//        String sqlInsert = "INSERT INTO Quiz (nume) VALUES (?)";
//        String url = "jdbc:mysql://localhost:3306/paoproiect";
//        String username = "root";
//        String password = "Muffin2325";
//
//        try (Connection connection = DriverManager.getConnection(url, username, password);
//             PreparedStatement createTableStatement = connection.prepareStatement(sqlCreateTable);
//             PreparedStatement insertStatement = connection.prepareStatement(sqlInsert)) {
//            String schemaName = "paoproiect";
//            connection.setSchema(schemaName);
//
//            // Creare tabela Quiz (dacă nu există deja)
//            createTableStatement.executeUpdate();
//
//            // Adăugare quiz-uri în tabela Quiz
//            for (Quiz quiz : quizList) {
//                insertStatement.setString(1, quiz.getNume());
//                insertStatement.executeUpdate();
//            }
//
//            System.out.println("Quiz-urile au fost adăugate în tabela Quiz.");
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void updateCursant(Cursant cursant, String numeNou, String prenumeNou) {
//        String url = "jdbc:mysql://localhost:3306/paoproiect";
//        String username = "root";
//        String password = "Muffin2325";
//        try (Connection connection = DriverManager.getConnection(url, username, password)) {
//            String schemaName = "paoproiect";
//            connection.setSchema(schemaName);
//            String sql = "UPDATE Cursanti SET nume = ?, prenume = ? WHERE id = ?";
//            PreparedStatement statement = connection.prepareStatement(sql);
//            statement.setString(1, numeNou);
//            statement.setString(2, prenumeNou);
//            statement.setInt(3, getCursantId(cursant.getNume(), cursant.getPrenume()));
//
//            int rowsUpdated = statement.executeUpdate();
//            if (rowsUpdated > 0) {
//                System.out.println("Cursantul a fost actualizat cu succes în baza de date.");
//            } else {
//                System.out.println("Nu s-a găsit cursantul în baza de date sau nu s-au făcut modificări.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    public void updateUtilizator(Utilizator utilizator, String numeNou, String prenumeNou) {
//        String url = "jdbc:mysql://localhost:3306/paoproiect";
//        String username = "root";
//        String password = "Muffin2325";
//        try (Connection connection = DriverManager.getConnection(url, username, password)) {
//            String schemaName = "paoproiect";
//            connection.setSchema(schemaName);
//            String sql = "UPDATE Utilizatori SET nume = ?, prenume = ? WHERE id = ?";
//            PreparedStatement statement = connection.prepareStatement(sql);
//            statement.setString(1, numeNou);
//            statement.setString(2, prenumeNou);
//            statement.setInt(3, getUtilizatorId(utilizator.getNume(), utilizator.getPrenume()));
//
//            int rowsUpdated = statement.executeUpdate();
//            if (rowsUpdated > 0) {
//                System.out.println("Utilizatorul a fost actualizat cu succes în baza de date.");
//            } else {
//                System.out.println("Nu s-a găsit Utilizatorul în baza de date sau nu s-au făcut modificări.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    public void updateInstructor(Instructor instructor, String numeNou, String prenumeNou) {
//        String url = "jdbc:mysql://localhost:3306/paoproiect";
//        String username = "root";
//        String password = "Muffin2325";
//        try (Connection connection = DriverManager.getConnection(url, username, password)) {
//            String schemaName = "paoproiect";
//            connection.setSchema(schemaName);
//            String sql = "UPDATE Instructori SET nume = ?, prenume = ? WHERE id = ?";
//            PreparedStatement statement = connection.prepareStatement(sql);
//            statement.setString(1, numeNou);
//            statement.setString(2, prenumeNou);
//            statement.setInt(3, getInstructorId(instructor.getNume(), instructor.getPrenume()));
//
//            int rowsUpdated = statement.executeUpdate();
//            if (rowsUpdated > 0) {
//                System.out.println("Instructorul a fost actualizat cu succes în baza de date.");
//            } else {
//                System.out.println("Nu s-a găsit Instructorul în baza de date sau nu s-au făcut modificări.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    public void updateCurs(Curs curs, String categorieNoua, String titluNou) {
//        String url = "jdbc:mysql://localhost:3306/paoproiect";
//        String username = "root";
//        String password = "Muffin2325";
//        try (Connection connection = DriverManager.getConnection(url, username, password)) {
//            String schemaName = "paoproiect";
//            connection.setSchema(schemaName);
//            String sql = "UPDATE Cursuri SET titlu = ?, categorie = ? WHERE id = ?";
//            PreparedStatement statement = connection.prepareStatement(sql);
//            statement.setString(1, titluNou);
//            statement.setString(2, categorieNoua);
//            statement.setInt(3, getInstructorId(curs.getTitlu(), curs.getCategorie()));
//
//            int rowsUpdated = statement.executeUpdate();
//            if (rowsUpdated > 0) {
//                System.out.println("Instructorul a fost actualizat cu succes în baza de date.");
//            } else {
//                System.out.println("Nu s-a găsit Instructorul în baza de date sau nu s-au făcut modificări.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public Cursant getCursant(int cursantId) {
//        Cursant cursant = null;
//        String url = "jdbc:mysql://localhost:3306/paoproiect";
//        String username = "root";
//        String password = "Muffin2325";
//
//        try (Connection connection = DriverManager.getConnection(url, username, password)) {
//            String schemaName = "paoproiect";
//            connection.setSchema(schemaName);
//            String query = "SELECT nume, prenume FROM Cursanti WHERE id = ?";
//            try (PreparedStatement statement = connection.prepareStatement(query)) {
//                statement.setInt(1, cursantId);
//                try (ResultSet resultSet = statement.executeQuery()) {
//                    if (resultSet.next()) {
//                        String nume = resultSet.getString("nume");
//                        String prenume = resultSet.getString("prenume");
//                        cursant = new Cursant(nume, prenume);
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return cursant;
//    }
//    public Utilizator getUtilizator(int utilizatorId) {
//        Utilizator utilizator = null;
//        String url = "jdbc:mysql://localhost:3306/paoproiect";
//        String username = "root";
//        String password = "Muffin2325";
//
//        try (Connection connection = DriverManager.getConnection(url, username, password)) {
//            String schemaName = "paoproiect";
//            connection.setSchema(schemaName);
//            String query = "SELECT nume, prenume FROM Utilizatori WHERE id = ?";
//            try (PreparedStatement statement = connection.prepareStatement(query)) {
//                statement.setInt(1, utilizatorId);
//                try (ResultSet resultSet = statement.executeQuery()) {
//                    if (resultSet.next()) {
//                        String nume = resultSet.getString("nume");
//                        String prenume = resultSet.getString("prenume");
//                        utilizator = new Utilizator(nume, prenume);
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return utilizator;
//    }
//    public Instructor getInstructor(int instructorId) {
//        Instructor instructor = null;
//        String url = "jdbc:mysql://localhost:3306/paoproiect";
//        String username = "root";
//        String password = "Muffin2325";
//
//        try (Connection connection = DriverManager.getConnection(url, username, password)) {
//            String schemaName = "paoproiect";
//            connection.setSchema(schemaName);
//            String query = "SELECT nume, prenume FROM Instructor WHERE id = ?";
//            try (PreparedStatement statement = connection.prepareStatement(query)) {
//                statement.setInt(1, instructorId);
//                try (ResultSet resultSet = statement.executeQuery()) {
//                    if (resultSet.next()) {
//                        String nume = resultSet.getString("nume");
//                        String prenume = resultSet.getString("prenume");
//                        instructor = new Instructor(nume, prenume);
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return instructor;
//    }
//    public Curs getCurs(int cursId) {
//        Curs curs = null;
//        String url = "jdbc:mysql://localhost:3306/paoproiect";
//        String username = "root";
//        String password = "Muffin2325";
//
//        try (Connection connection = DriverManager.getConnection(url, username, password)) {
//            String schemaName = "paoproiect";
//            connection.setSchema(schemaName);
//            String query = "SELECT titlu, categorie FROM Cursuri WHERE id = ?";
//            try (PreparedStatement statement = connection.prepareStatement(query)) {
//                statement.setInt(1, cursId);
//                try (ResultSet resultSet = statement.executeQuery()) {
//                    if (resultSet.next()) {
//                        String titlu = resultSet.getString("titlu");
//                        String categorie = resultSet.getString("categorie");
//                        curs = new Curs(titlu, categorie);
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return curs;
//    }
    public void adaugaCursant(Cursant cursant) {
        listaCursanti.add(cursant);
    }
    public void adaugaUtilizator(Utilizator utilizator) {
        listaUtilizatori.add(utilizator);
    }
    public void adaugaInstructor(Instructor instructor) {
        listaInstructori.add(instructor);
    }
    public void adaugaCurs(Curs curs) {
        listaCursuri.add(curs);
    }
    public void stergeCursant(Cursant cursant) {
        listaCursanti.remove(cursant);
    }
    public void stergeUtilizator(Utilizator utilizator) {
        listaUtilizatori.remove(utilizator);
    }
    public void stergeInstructor(Instructor instructor) {
        listaInstructori.remove(instructor);
    }
    public void stergeCurs(Curs curs) {
        listaCursuri.remove(curs);
    }
    public void updateCursant(Cursant cursant, String nume, String prenume){
        cursant.setNume(nume);
        cursant.setPrenume(prenume);
    }
    public void updateUtilizator(Utilizator utilizator, String nume, String prenume){
        utilizator.setNume(nume);
        utilizator.setPrenume(prenume);
    }
    public void updateInstructor(Instructor instructor, String nume, String prenume){
        instructor.setNume(nume);
        instructor.setPrenume(prenume);
    }
    public void updateCurs(Curs curs, String titlu, String categorie){
        curs.setTitlu(titlu);
        curs.setCategorie(categorie);
    }



    public void adaugaQuiz(Curs curs, Quiz quiz) {
        if (mapQuizuri.containsKey(curs)) {
            mapQuizuri.get(curs).add(quiz);
        } else {
            List<Quiz> quizuri = new ArrayList<>();
            quizuri.add(quiz);
            mapQuizuri.put(curs, quizuri);
        }
        curs.addQuiz(quiz);
    }
    public void adaugaLectie(Curs curs, Lectie lectie) {
        curs.addLectie(lectie);
    }
    public List<Curs> getCursuriDisponibile() {
        return listaCursuri;
    }
    public List<Quiz> getQuizuriInstructor(Curs curs) {
        if (mapQuizuri.containsKey(curs)) {
            return mapQuizuri.get(curs);
        }
        return new ArrayList<>();
    }
    public void removeCurs(Curs curs) {
        listaCursuri.remove(curs);
    }
    public void quizuriPromovate(Curs curs, Cursant cursant, List<List<String>> raspunsuri) {
        RaportPerformanta raport = new RaportPerformanta(cursant, mapQuizuri.get(curs), 0);
        for(Quiz quiz : mapQuizuri.get(curs)) {
            for(int i = 0; i < quiz.getAnswers().size(); i++) {
                for(List<String> raspuns : raspunsuri) {
                    if (raport.calcScorQuiz(raspuns, quiz.getNume()) >= 5)
                        raport.setPromovationCount(raport.getPromovationCount() + 1);
                }
            }
        }
    }
    public Curs cautaCursDupaTitlu(String titlu) {
        for (Curs curs : listaCursuri) {
            if (curs.getTitlu().equals(titlu)) {
                return curs;
            }
        }
        return null;
    }
    public List<Curs> cautaCursuriDupaCategorie(String categorie) {
        List<Curs> cursuri = new ArrayList<>();
        for (Curs curs : listaCursuri) {
            if (curs.getCategorie().equals(categorie)) {
                cursuri.add(curs);
            }
        }
        return cursuri;
    }
    public GrupDeStudiu creazaGrupDeStudiu(List<Cursant> cursanti, List<Lectie> lectii) {
        int max = 0;
        Curs curs = null;
        for(Curs curs1 : listaCursuri) {
            int k = 0;
            for(Lectie lectie : lectii) {
                if(curs1.getLectieList().contains(lectie)) {
                    k++;
                }
            }
            if(k > max) {
                max = k;
                curs = curs1;
            }
        }
        GrupDeStudiu grupDeStudiu = new GrupDeStudiu(curs.getInstructor(), cursanti, lectii);
        return grupDeStudiu;
    }

    public void asigneazaInstructorCurs(Curs curs1, Instructor instructor1) {
        curs1.setInstructor(instructor1);
        instructor1.addCurs(curs1);
    }

    // Creaza o metoda care afiseaza proprietatile clasei
    public void afiseazaProprietati() {
        System.out.println("Lista cursuri: ");
        for(Curs curs : listaCursuri) {
            curs.printCurs();
        }
        System.out.println("Lista utilizatori: ");
        for(Utilizator utilizator : listaUtilizatori) {
            utilizator.printUtilizator();
        }
        System.out.println("Lista cursanti: ");
        for(Cursant cursant : listaCursanti) {
            cursant.printCursant();
        }
        System.out.println("Lista instructori: ");
        for(Instructor instructor : listaInstructori) {
            instructor.printInstructor();
        }
        System.out.println("Map quizuri: ");
        for(Curs curs : mapQuizuri.keySet()) {
            curs.printCurs();
            for(Quiz quiz : mapQuizuri.get(curs)) {
                quiz.printQuiz();
            }
        }
    }
}

//Clasa Lectie
class Lectie {
    private List<String> continutLectie;
    public Lectie(List<String> continutLectie) {
        this.continutLectie = continutLectie;
    }
    public List<String> getContinutLectie() {
        return continutLectie;
    }
    public void setContinutLectie(List<String> continutLectie) {
        this.continutLectie = continutLectie;
    }
    public void afiseazaLectie() {
        for (String string : continutLectie) {
            System.out.println(string);
        }
    }
    public void printLectie() {
        System.out.println("Continut lectie: ");
        for(String string : continutLectie) {
            System.out.println(string);
        }
    }
}

// Clasa StudiuDeGrup
class GrupDeStudiu {
    private Instructor instructor;
    private List<Cursant> cursanti;
    private List<Lectie> lectii;
    public GrupDeStudiu(Instructor instructor, List<Cursant> cursanti, List<Lectie> lectii) {
        this.instructor = instructor;
        this.cursanti = cursanti;
        this.lectii = lectii;
    }
    public Instructor getInstructor() {
        return instructor;
    }
    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }
    public List<Cursant> getCursanti() {
        return cursanti;
    }
    public void setCursanti(List<Cursant> cursanti) {
        this.cursanti = cursanti;
    }
    public List<Lectie> getLectii() {
        return lectii;
    }
    public void setLectii(List<Lectie> lectii) {
        this.lectii = lectii;
    }
    public void addCursant(Cursant cursant) {
        cursanti.add(cursant);
    }
    public void removeCursant(Cursant cursant) {
        cursanti.remove(cursant);
    }
    public void addLectie(Lectie lectie) {
        lectii.add(lectie);
    }
    public void removeLectie(Lectie lectie) {
        lectii.remove(lectie);
    }
    public void printGrupDeStudiu() {
        System.out.println("Instructor: ");
        instructor.printInstructor();
        System.out.println("Lista cursanti: ");
        for(Cursant cursant : cursanti) {
            cursant.printCursant();
        }
        System.out.println("Lista lectii: ");
        for(Lectie lectie : lectii) {
            lectie.printLectie();
        }
    }

}

//Clasa RaportPerformanta
class RaportPerformanta {

    private Cursant cursant;
    private List<Quiz> quizList;
    private int promovationCount = 0;
    public RaportPerformanta(Cursant cursant, List<Quiz> quizList, int promovationPercentage) {
        this.cursant = cursant;
        this.quizList = quizList;
        this.promovationCount = promovationCount;
    }
    public Cursant getCursant() {
        return cursant;
    }
    public void setCursant(Cursant cursant) {
        this.cursant = cursant;
    }
    public List<Quiz> getQuizList() {
        return quizList;
    }
    public void setQuizList(List<Quiz> quizList) {
        this.quizList = quizList;
    }
    public int getPromovationCount() {
        return promovationCount;
    }
    public void setPromovationCount(int promovationPercentage) {
        this.promovationCount = promovationPercentage;
    }
    public int calcScorQuiz(List<String> raspunsuri, String quizName) {
        int scor = 0;
        Quiz quizAux = null;
        // Scrie for care gaseste quiz-ul cu numele quizName in variabila quizAux
        for(Quiz quiz : quizList) {
            if(quiz.getNume().equals(quizName)) {
                quizAux = quiz;
            }
        }

        for (int i = 0; i < raspunsuri.size(); i++) {
            if (raspunsuri.get(i).equals(quizAux.getAnswers().get(i))) {
                scor++;
            }
        }
        return scor;
    }
    public void printRaportPerformanta() {
        System.out.println("Cursant: ");
        cursant.printCursant();
        System.out.println("Lista quizuri: ");
        for(Quiz quiz : quizList) {
            quiz.printQuiz();
        }
        System.out.println("Procentaj promovare: " + promovationCount);
    }
}

// Clasa Main
public class Main {
    public static void main(String[] args) {
        E_LearningService e_learningService = new E_LearningService();
        //Adaugare a 3 cursuri noi
        Curs curs1 = new Curs("Java Programming", "Programare");
        Curs curs2 = new Curs("Web Development", "IT");
        Curs curs3 = new Curs("Mathematics", "Matematica");
        e_learningService.adaugaCurs(curs1);
        e_learningService.adaugaCurs(curs2);
        e_learningService.adaugaCurs(curs3);

        //Adaugare a 2 utilizatori noi
        Utilizator utilizator1 = new Instructor("John" , "Doe");
        Utilizator utilizator2 = new Cursant("Jane", "Smith");
        e_learningService.adaugaUtilizator(utilizator1);
        e_learningService.adaugaUtilizator(utilizator2);

        //Adaugare a 3 instructori noi
        Instructor instructor1 = new Instructor("Andrei", "Ion");
        Instructor instructor2 = new Instructor("Marius", "Preda");
        Instructor instructor3 = new Instructor("Bogdan", "Anghel");
        e_learningService.adaugaInstructor(instructor1);
        e_learningService.adaugaInstructor(instructor2);
        e_learningService.adaugaInstructor(instructor3);
        e_learningService.asigneazaInstructorCurs(curs1, instructor1);
        e_learningService.asigneazaInstructorCurs(curs2, instructor2);
        e_learningService.asigneazaInstructorCurs(curs3, instructor3);

        //Adaugare a 6 cursanti noi
        Cursant cursant1 = new Cursant("Alex", "Popescu");
        Cursant cursant2 = new Cursant("Maria", "Ionescu");
        Cursant cursant3 = new Cursant("Andreea", "Popa");
        Cursant cursant4 = new Cursant("Mihai", "Georgescu");
        Cursant cursant5 = new Cursant("Ioana", "Pop");
        Cursant cursant6 = new Cursant("Ana", "Goergeta");
        e_learningService.adaugaCursant(cursant1);
        e_learningService.adaugaCursant(cursant2);
        e_learningService.adaugaCursant(cursant3);
        e_learningService.adaugaCursant(cursant4);
        e_learningService.adaugaCursant(cursant5);
        e_learningService.adaugaCursant(cursant6);

        List<String> continutLectie1 = new ArrayList<String>();
        continutLectie1.add("Capitol1.1");
        continutLectie1.add("Capitol1.2");
        continutLectie1.add("Capitol1.3");

        List<String> continutLectie2 = new ArrayList<String>();
        continutLectie2.add("Capitol2.1");
        continutLectie2.add("Capitol2.2");
        continutLectie2.add("Capitol2.3");

        List<String> continutLectie3 = new ArrayList<String>();
        continutLectie3.add("Capitol3.1");
        continutLectie3.add("Capitol3.2");
        continutLectie3.add("Capitol3.3");

        List<String> continutLectie4 = new ArrayList<String>();
        continutLectie4.add("Capitol4.1");
        continutLectie4.add("Capitol4.2");
        continutLectie4.add("Capitol4.3");

        List<String> continutLectie5 = new ArrayList<String>();
        continutLectie5.add("Capitol5.1");
        continutLectie5.add("Capitol5.2");
        continutLectie5.add("Capitol5.3");

        List<String> continutLectie6= new ArrayList<String>();
        continutLectie5.add("Capitol6.1");
        continutLectie5.add("Capitol6.2");
        continutLectie5.add("Capitol6.3");

        //Adaugare a 2 lectii noi pentru fiecare curs in parte
        Lectie lectie1 = new Lectie(continutLectie1);
        Lectie lectie2 = new Lectie(continutLectie2);
        Lectie lectie3 = new Lectie(continutLectie3);
        Lectie lectie4 = new Lectie(continutLectie4);
        Lectie lectie5 = new Lectie(continutLectie5);
        Lectie lectie6 = new Lectie(continutLectie6);

        e_learningService.adaugaLectie(curs1, lectie1);
        e_learningService.adaugaLectie(curs1, lectie2);
        e_learningService.adaugaLectie(curs2, lectie3);
        e_learningService.adaugaLectie(curs2, lectie4);
        e_learningService.adaugaLectie(curs3, lectie5);
        e_learningService.adaugaLectie(curs3, lectie6);

        //Adaugare a 3 Quizuri noi, unu pentru fiecare curs
        List<String> Questions1 = new ArrayList<String>();
        Questions1.add("Question1.1");
        Questions1.add("Question1.2");

        List<String> Questions2 = new ArrayList<String>();
        Questions2.add("Question2.1");
        Questions2.add("Question2.2");

        List<String> Questions3 = new ArrayList<String>();
        Questions3.add("Question3.1");
        Questions3.add("Question3.2");

        List<String> Answers1 = new ArrayList<String>();
        Answers1.add("Answer1.1");
        Answers1.add("Answer1.2");

        List<String> Answers2 = new ArrayList<String>();
        Answers2.add("Answer2.1");
        Answers2.add("Answer2.2");

        List<String> Answers3 = new ArrayList<String>();
        Answers3.add("Answer3.1");
        Answers3.add("Answer3.2");

        Quiz quiz1 = new Quiz("Quiz1", Questions1, Answers1);
        Quiz quiz2 = new Quiz("Quiz2", Questions2, Answers2);
        Quiz quiz3 = new Quiz("Quiz3", Questions3, Answers3);

        e_learningService.adaugaQuiz(curs1, quiz1);
        e_learningService.adaugaQuiz(curs2, quiz2);
        e_learningService.adaugaQuiz(curs3, quiz3);

        // Adaugare 1 studiu de grup
        List<Lectie> lectiiStudiuDeGrup = new ArrayList<Lectie>();
        lectiiStudiuDeGrup.add(lectie1);
        lectiiStudiuDeGrup.add(lectie2);
        GrupDeStudiu grupDeStudiu = new GrupDeStudiu(instructor2, e_learningService.getListaCursanti(), lectiiStudiuDeGrup);

        // Adaugare 2 raport de performanta
        RaportPerformanta newRaport = new RaportPerformanta(cursant2, curs1.getQuizList(), 0);
        RaportPerformanta newRaport2 = new RaportPerformanta(cursant3, curs2.getQuizList(), 0);

        for(Curs curs : e_learningService.getListaCursuri()) {
            curs.printCurs();
        }

        //e_learningService.adaugaCursantiInTabela(cursanti);
        //e_learningService.deleteCursant(cursant2);
    }
}
