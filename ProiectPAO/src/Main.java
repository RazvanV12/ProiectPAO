import java.util.*;

// Clasa Curs
class Curs {
    private String titlu;
    private String categorie;
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

    public void removeLectie(Lectie lectie) {
        lectieList.remove(lectie);
    }

    public Curs(String titlu, String categorie) {
        this.titlu = titlu;
        this.categorie = categorie;
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
}

// Clasa Utilizator
class Utilizator {
    private String nume;

    private String prenume;

    public Utilizator(String nume) {
        this.nume = nume;
    }

    public String getNume() {
        return nume;
    }
    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
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
    public Instructor(String nume) {
        super(nume);
    }
    public void addCurs(Curs curs) {
        cursuriPredate.add(curs);
    }
    public void removeCurs(Curs curs) {
        cursuriPredate.remove(curs);
    }
}

// Clasa Cursant, moștenită din Utilizator
class Cursant extends Utilizator {

    private List<Curs> cursuriInscrise;

    public List<Curs> getCursuri() {
        return cursuriInscrise;
    }

    public void setCursuri(List<Curs> cursuri) {
        this.cursuriInscrise = cursuri;
    }
    public Cursant(String nume) {
        super(nume);
    }

    public void addCurs(Curs curs) {
        cursuriInscrise.add(curs);
    }

    public void removeCurs(Curs curs) {
        cursuriInscrise.remove(curs);
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
    public Quiz(String nume) {
        this.nume = nume;
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
}

// Clasa CursService - serviciul care expune operațiile sistemului
class CursService {
    private List<Curs> listaCursuri;
    private Set<Utilizator> listaUtilizatori;
    private Map<Utilizator, List<Quiz>> mapQuizuri;

    public CursService() {
        listaCursuri = new ArrayList<>();
        listaUtilizatori = new HashSet<>();
        mapQuizuri = new HashMap<>();
    }

    public void adaugaCurs(Curs curs) {
        listaCursuri.add(curs);
    }

    public void adaugaUtilizator(Utilizator utilizator) {
        listaUtilizatori.add(utilizator);
    }

    public void adaugaQuiz(Utilizator utilizator, Quiz quiz) {
        if (mapQuizuri.containsKey(utilizator)) {
            mapQuizuri.get(utilizator).add(quiz);
        } else {
            List<Quiz> quizuri = new ArrayList<>();
            quizuri.add(quiz);
            mapQuizuri.put(utilizator, quizuri);
        }
    }

    public List<Curs> getCursuriDisponibile() {
        return listaCursuri;
    }

    public List<Quiz> getQuizuriUtilizator(Utilizator utilizator) {
        if (mapQuizuri.containsKey(utilizator)) {
            return mapQuizuri.get(utilizator);
        }
        return new ArrayList<>();
    }
}
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
}
class RaportPerformanta {
    private List<String> raspunsuri;
    private int scor;

    public RaportPerformanta(List<String> raspunsuri, int scor) {
        this.raspunsuri = raspunsuri;
        this.scor = scor;
    }

    public List<String> getRaspunsuri() {
        return raspunsuri;
    }

    public void setRaspunsuri(List<String> raspunsuri) {
        this.raspunsuri = raspunsuri;
    }

    public int getScor() {
        return scor;
    }

    public void setScor(int scor) {
        this.scor = scor;
    }
    public int calculeazaScor(List<String> raspunsuri) {
        int scor = 0;
        for (int i = 0; i < raspunsuri.size(); i++) {
            if (raspunsuri.get(i).equals(this.raspunsuri.get(i))) {
                scor++;
            }
        }
        return scor;
    }

}

// Clasa Main
public class Main {
    public static void main(String[] args) {
        CursService cursService = new CursService();

        // Adăugare cursuri
        Curs curs1 = new Curs("Java Programming", "Programare");
        Curs curs2 = new Curs("Web Development", "IT");
        cursService.adaugaCurs(curs1);
        cursService.adaugaCurs(curs2);

        // Adăugare utilizatori
        Utilizator instructor = new Instructor("John Doe");
        Utilizator cursant = new Cursant("Jane Smith");
        cursService.adaugaUtilizator(instructor);
        cursService.adaugaUtilizator(cursant);

        // Adăugare quizuri
        Quiz quiz1 = new Quiz("Quiz 1");
        Quiz quiz2 = new Quiz("Quiz 2");
        cursService.adaugaQuiz(cursant, quiz1);
        cursService.adaugaQuiz(cursant, quiz2);

        // Obținere lista de cursuri disponibile
        List<Curs> cursuriDisponibile = cursService.getCursuriDisponibile();
        System.out.println("Cursuri disponibile:");
        for (Curs curs : cursuriDisponibile) {
            System.out.println("- " + curs.getTitlu() + " (" + curs.getCategorie() + ")");
        }

        // Obținere lista de quizuri pentru un cursant
        List<Quiz> quizuriCursant = cursService.getQuizuriUtilizator(cursant);
        System.out.println("\nQuizuri pentru cursantul " + cursant.getNume() + ":");
        for (Quiz quiz : quizuriCursant) {
            System.out.println("- " + quiz.getNume());
        }
    }
}
