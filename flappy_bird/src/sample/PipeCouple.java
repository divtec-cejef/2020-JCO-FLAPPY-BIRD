package sample;

/**
 * Classe qui représente un couple de tuyau
 *
 * @author Louis Bovay
 */
public class PipeCouple {
    public Pipe topPipe;
    public Pipe bottomPipe;
    private boolean canGivePts = true;
    private final double VERTICAL_RANGE_EXPAND = 100;
    private final double VERTICAL_RANGE_SHRINK = 50;
    private final double MAX_HEIGHT = 700;

    private double startBottonPipe;
    private double endBottomPipe;
    private double startTopPipe;
    private double endTopPipe;
    private double direction;
    private boolean goDown = true;
    private boolean goUp = true;
    //Le tuyaux dominant, celui qui bougera
    private String alphaPipe = "";

    /**
     * Crée et instantie un couple de tuyau
     *
     * @param topPipe    tuyau du haut
     * @param bottomPipe tuyau du bas
     */
    public PipeCouple(Pipe topPipe, Pipe bottomPipe) {
        this.topPipe = topPipe;
        this.bottomPipe = bottomPipe;

        this.topPipe.setWidth(60);
        this.topPipe.setHeight(700);

        this.bottomPipe.setWidth(60);
        this.bottomPipe.setHeight(700);

        formatCouples();
    }

    /**
     * Génère un nombre aléatoire entre une range donnée
     *
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
    private void createSpace() {
        //Espace fixe entre le couple de tuyaux
        int spaceBetween = 200;
        // décalage automatique des tuyaux
        int rndNum = getRandomNumber(-210, 210);
        //Décale le tuyau du haut
        this.topPipe.setTranslateY(this.topPipe.getTranslateY() + rndNum - spaceBetween / 2);
        //Décale le tuyau du bas
        this.bottomPipe.setTranslateY(this.bottomPipe.getTranslateY() + rndNum + spaceBetween / 2);
        //Replacer les sprites sur les tuyaux
        this.topPipe.refreshPipeSprite();
        this.bottomPipe.refreshPipeSprite();
    }

    /**
     * Fait bouger le couple de droite à gauche, si arrivé a gauche, il se réinitialise
     *
     * @param speed Vitesse des tuyaux
     */
    public void move(int speed) {
        //Arrivé
        if (isOut()) {
            //Couple réinitialisé
            formatCouples();
        }
        //Pas arrivé
        else {
            this.topPipe.moveLeft(speed);
            this.topPipe.refreshPipeSprite();
            this.bottomPipe.moveLeft(speed);
            this.bottomPipe.refreshPipeSprite();
        }
    }

    /**
     * Observe si le couple à atteint de côté gauche
     *
     * @return true : est arrivé / False : n'est pas arrivé
     */
    private boolean isOut() {
        return this.topPipe.getTranslateX() < -600;
    }

    /**
     * Réinitialise le couple, le replaçant à droit de l'écran et en recréant un espace entre les deux tuyaux
     */
    public void formatCouples() {
        //tuyau du haut
        this.topPipe.setTranslateX(550);
        this.topPipe.setTranslateY(-350);

        //tuyau du bas
        this.bottomPipe.setTranslateX(550);
        this.bottomPipe.setTranslateY(350);

        //Ils peuvent à nouveau donner des points
        this.canGivePts = true;

        // Générer l'espace aléatoire entre les deux tuyaux
        createSpace();
        // Rafraîchir les position de départ et d'arrivée des mouvement verticaux des tuyaux
        refreshStartAndEndPipe();
        //Décide qui des deux tuyaux bougera
        generateAlpha();
    }

    /**
     * Permet de connaître si oui on non un couple de tuyau peuvent donner des points
     *
     * @return true = le couple peut donner des points, false = ne peuvent pas donner de points
     */
    public boolean CanGivePts() {
        return canGivePts;
    }

    /**
     * Définir si oui ou non un couple peut donner des points
     *
     * @param canGivePts true = le couple peut donner des points, false = il ne peut pas
     */
    public void setCanGivePts(boolean canGivePts) {
        this.canGivePts = canGivePts;
    }

    /**
     * Rafraîchit le départ et l'arrivée des mouvement verticaux des tuyaux
     */
    private void refreshStartAndEndPipe() {
        this.startBottonPipe = this.bottomPipe.getTranslateY() - VERTICAL_RANGE_SHRINK;
        this.endBottomPipe = this.bottomPipe.getTranslateY() + VERTICAL_RANGE_EXPAND;

        this.startTopPipe = this.topPipe.getTranslateY() + VERTICAL_RANGE_SHRINK;
        this.endTopPipe = this.topPipe.getTranslateY() - VERTICAL_RANGE_EXPAND;
    }

    /**
     * Fait bouger verticalement le tuyaux du bas entre deux points
     */
    private void moveBottomPipe() {
        if (this.bottomPipe.getTranslateY() > this.endBottomPipe) {
            goDown = true;
        }
        if (this.bottomPipe.getTranslateY() < this.startBottonPipe) {
            goDown = false;
        }

        if (goDown) {
            this.bottomPipe.moveUp(2);
        } else {
            this.bottomPipe.moveDown(2);
        }
    }

    /**
     * Fait bouger verticalement le tuyaux du haut entre deux points
     */
    private void moveTopPipe() {
        if (this.topPipe.getTranslateY() < this.endTopPipe) {
            goUp = false;
        }
        if (this.topPipe.getTranslateY() > this.startTopPipe) {
            goUp = true;
        }

        if (goUp) {
            this.topPipe.moveUp(2);
        } else {
            this.topPipe.moveDown(2);
        }
    }

    /**
     * Décide lequel des deux tuyaux sera le tuyau dominant
     */
    private void generateAlpha(){
        switch(getRandomNumber(1,3)){
            case 1:
                alphaPipe = "TOP";
                break;
            case 2:
                alphaPipe = "BOTTOM";
                break;
        }
    }

    /**
     * Donne accès au tuyaux dominant
     * @return le tuyaux dominant
     */
    private String getAlphaPipe() {
        return alphaPipe;
    }

    /**
     * Fait bouger le tuyau dominant verticalement
     */
    public void moveAlphaPipe(){
        switch(this.getAlphaPipe()){
            case "TOP":
                moveTopPipe();
                break;
            case "BOTTOM":
                moveBottomPipe();
                break;
        }
    }


}
