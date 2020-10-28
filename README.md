# 2020-JCO-FLAPPY-BIRD

## But :
un oiseau vole entre l'espace créé par un tuyau supérieur et un tuyau inférieur, s'il en touche un il meurt.

l'oiseau tombe en continue vers le bas, s'il touche le bas il meurt.

si le joueur appuie sur une touche il battera une fois des ails se qui le fera monter un peu, avant de recommencer à descendre, si le joueur fait monter l'oiseau trop haut,
l'oiseau meurt.

le but est de passer le plus de couple de tuyaux

## Les tuyaux :

les tuyaux détectent si l'oiseau passe dessus, si oui, c'est perdu.

immaginons que de base le tuyaux de haut et du bas se touche au centre de l'écran.

il doit y avoir un écart de 300 entre les deux tuyaux, a chaque apparition de couple de tuyau un chiffre aléatoir de 0 à 300 est tiré.

depuis le millieu de l'écran en augmente la valeur Y du tuyau du haut par la valeur tirée,
et on réduit la valeur Y du tuyau du bas par 300 - la valeur tirée, ce qui donnera un écart de 300 + un position aléatoir de l'espace.

```java
/**
 * Crée un espace égale à la valeur de spaceBetween entre deux tuyaux
 */
public void createSpace(){
int rndNum = getRandomNumber(0,spaceBetween);
this.pipe1.setTranslateY(this.pipe1.getTranslateY() - rndNum);
this.pipe2.setTranslateY(this.pipe2.getTranslateY() + (spaceBetween-rndNum));
}
```

## L'oiseau :
-l'oiseau tombe de base, en appuyant sur un touche, il monte progressivement d'une valeur fixe vers le haut, puis recommence a tombé

```java
/**
 * L'oiseau monte l'axe Y en fonction de sa force
 * @param strengh force de l'oiseau, plus elle est haute, plus il montera haut
 */
public void flap(int strengh) {
    this.setTranslateY(this.getTranslateY() - strengh);
}

/**
 * fait subir à l'oiseau une force de gravité, ce qui le poussera a tomber en continu
 * plus la gravité est élevée, plus il tombera vite
 * @param gravity force de gravité
 */
public void undergoGravity(int gravity) {
        this.moveDown(gravity);
}
```


## La scene :
la scène est un simple rectangle.

le bas et le haut tue l'oiseau.

la droite donne un point d'apparaission des tuyaux, la gauche donne un point de destruction des tuyaux.

## Les classes :
UML des classes
