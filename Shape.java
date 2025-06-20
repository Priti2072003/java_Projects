 class Shape {
    void draw(){
        System.out.println("I'm drawing Rectangle.");
    }
    
}
class Traingle extends Shape {
    @Override
    void draw(){
        System.out.println("I'm drawing Triangle.");
    }
    
}
class Circle extends Shape {
     @Override
    void draw(){
        System.out.println("I'm drawing Circle.");
    }
    
}
class Polygon extends Shape {
     @Override
    void draw(){
        System.out.println("I'm drawing Polygon.");
    }
    
}
class Main {
    public static void main (String[] args){
        Shape ob1=new Shape();
        ob1.draw();
        Circle ob2=new Circle();
        ob2.draw();
        Traingle ob3= new Traingle();
        ob3.draw();
        Polygon ob4= new Polygon();
        ob4.draw();
    }
    
}



