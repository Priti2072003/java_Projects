public class variableEx {
    public static void printAr(String ...args) {
        for(String arg:args){
            System.out.println(arg);
        }
    }
    public static void main(String[] args) {
        printAr("tt","ttt","yuu");
    }
}

