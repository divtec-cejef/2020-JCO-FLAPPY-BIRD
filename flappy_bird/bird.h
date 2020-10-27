#ifndef BIRD_H
#define BIRD_H


class Bird
{
public:
    // Constructor
    Bird(int posX, int posY);

    // Fonctions
    int getPosY();
    void setPosY(int posY);
    void kill();
    bool checkVitals();

private:
    // Variables membres
    int posX;
    int posY;
    bool isAlive;

};

#endif // BIRD_H
