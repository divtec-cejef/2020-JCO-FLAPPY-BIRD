#include "pipe.h"

//!
//! \brief Construit et initialise un tuyau
//! \param posX
//! \param posY
//! \param angleRota (permet de choisir si le tuyaux se en haut ou en bas)
//!
Pipe::Pipe(int posX, int posY, int angleRota){
    this->posX = posX;
    this->posY = posY;
    this->angleRota = angleRota;
}

int Pipe::getPosX(){
    return this->posX;
}
int Pipe::getPosY(){
    return this->posY;
}
void Pipe::setPosX(int posX){
    this->posX = posX;
}
void Pipe::setPosY(int posY){
    this->posY = posY;
}
void Pipe::setRota(int angle){
    this->angleRota = angle;
}
