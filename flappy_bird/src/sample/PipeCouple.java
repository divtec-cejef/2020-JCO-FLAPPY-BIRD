package sample;

/**
 * Classe qui représente un couple de tuyau
 */
public class PipeCouple {
    public Pipe pipe1;
    public Pipe pipe2;
    private boolean canGivePts = true;

    /**
     * Crée et instantie un couple de tuyau
     * @param pipe1 tuyau du haut
     * @param pipe2 tuyau du bas
     */
    public PipeCouple(Pipe pipe1, Pipe pipe2){
        this.pipe1 = pipe1;
        this.pipe2 = pipe2;

        this.pipe1.setWidth(60);
        this.pipe1.setHeight(700);

        this.pipe2.setWidth(60);
        this.pipe2.setHeight(700);

        formatCouples();
    }

    /**
     * Génère un nombre aléatoire entre une range donnée
     * @param min range inférieure
     * @param max range supérieure
     * @return un nombre aléatoire
     */
    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    /**
     * Crée un espace égale à la valeur de spaceBetween entre deux tuyaux
     */
    private void createSpace(){
        //Espace fixe entre le couple de tuyaux
        int spaceBetween = 250;
        int rndNum = getRandomNumber(0, spaceBetween);
        this.pipe1.setTranslateY(this.pipe1.getTranslateY() - rndNum);
        this.pipe2.setTranslateY(this.pipe2.getTranslateY() + (spaceBetween -rndNum));
        //Replacer les sprites sur les tuyaux
        this.pipe1.refreshPipeSprite();
        this.pipe2.refreshPipeSprite();
    }

    /**
     * Fait bouger le couple de droite à gauche, si arrivé a gauche, il se réinitialise
     */
    public void move(){
        //Arrivé
        if(isOut()){
            //Couple réinitialisé
            formatCouples();
        }
        //Pas arrivé
        else{
            this.pipe1.moveLeft(5);
            this.pipe1.refreshPipeSprite();
            this.pipe2.moveLeft(5);
            this.pipe2.refreshPipeSprite();
        }
    }

    /**
     * Observe si le couple à atteint de côté gauche
     * @return true : est arrivé / False : n'est pas arrivé
     */
    private boolean isOut(){
        return this.pipe1.getTranslateX() < -600;
    }

    /**
     * Réinitialise le couple, le replaçant à droit de l'écran et en recréant un espace entre les deux tuyaux
     */
    public void formatCouples(){
        //en haut
        this.pipe1.setTranslateX(550);
        this.pipe1.setTranslateY(-350);

        //en bas
        this.pipe2.setTranslateX(550);
        this.pipe2.setTranslateY(350);

        //Ils peuvent à nouveau donner des points
        this.canGivePts = true;

        createSpace();
    }

    /**
     * Permet de connaître si oui on non un couple de tuyau peuvent donner des points
     * @return true = le couple peut donner des points, false = ne peuvent pas donner de points
     */
    public boolean CanGivePts() {
        return canGivePts;
    }

    /**
     * Définir si oui ou non un couple peut donner des points
     * @param canGivePts true = le couple peut donner des points, false = il ne peut pas
     */
    public void setCanGivePts(boolean canGivePts) {
        this.canGivePts = canGivePts;
    }
}
