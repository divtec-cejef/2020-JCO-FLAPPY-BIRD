package sample;

public class PipeCouple {
    public Pipe pipe1;
    public Pipe pipe2;
    private int spaceBetween = 200;
    private boolean finish = false;


    public PipeCouple(Pipe pipe1, Pipe pipe2){
        this.pipe1 = pipe1;
        this.pipe2 = pipe2;

        this.pipe1.setWidth(60);
        this.pipe1.setHeight(700);

        this.pipe2.setWidth(60);
        this.pipe2.setHeight(700);

        formatCouples();


    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void createSpace(){
        int rndNum = getRandomNumber(0,200);
        this.pipe1.setTranslateY(this.pipe1.getTranslateY() - rndNum);
        this.pipe2.setTranslateY(this.pipe2.getTranslateY() + (200-rndNum));
    }

    public void move(){
        if(finish){
            formatCouples();
            finish = !finish;

        }else{
            this.pipe1.moveLeft(5);
            this.pipe2.moveLeft(5);
            if(isOut()){
                finish = true;
            }
        }
    }

    public boolean isOut(){
        return this.pipe1.getTranslateX() < -600;
    }

    public void formatCouples(){
        //en haut
        this.pipe1.setTranslateX(550);
        this.pipe1.setTranslateY(-350);

        //en bas
        this.pipe2.setTranslateX(550);
        this.pipe2.setTranslateY(350);

        createSpace();
    }
}
