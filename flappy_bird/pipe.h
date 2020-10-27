#ifndef PIPE_H
#define PIPE_H


class Pipe
{
public:
    // Constructor
    Pipe(int posX, int posY, int angleRota);

    // Fonctions
    int getPosX();
    int getPosY();
    void setPosX(int posX);
    void setPosY(int posY);

private:
    // Variables membres
    int posX;
    int posY;
    int angleRota;

    // Fonctions
    void setRota(int angle);


};

#endif // PIPE_H
