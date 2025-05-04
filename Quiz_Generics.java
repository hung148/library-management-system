public class Quiz_Generics<T> {
    private T value;

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public static void main(String[] args) {
        Quiz_Generics<Integer> quiz = new Quiz_Generics<>();
        quiz.setValue(90);
        System.out.println(quiz.getValue());

        Quiz_Generics<String> quiz2 = new Quiz_Generics<>();
        quiz2.setValue("Hello");
        System.out.println(quiz2.getValue());
    }
}
