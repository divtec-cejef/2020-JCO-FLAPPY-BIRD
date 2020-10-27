/**
  \file
  \brief    Déclaration de la classe Pipe
  \author   Louis Bovay
  \date     27.10.2020
*/

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

//!
//! \brief Donne la position X du tuyau
//! \return la position X du tuyau
//!
int Pipe::getPosX(){
    return this->posX;
}

//!
//! \brief Donne la position Y du tuyau
//! \return la position Y du tuyau
//!
int Pipe::getPosY(){
    return this->posY;
}

//!
//! \brief Change la position X du tuyau
//! \param posX
//!
void Pipe::setPosX(int posX){
    this->posX = posX;
}

//!
//! \brief Change la position Y du tuyau
//! \param posY
//!
void Pipe::setPosY(int posY){
    this->posY = posY;
}

//!
//! \brief change l'angle de rotation du tuyau
//! \param angle (0 - 360)
//!
void Pipe::setRota(int angle){
    this->angleRota = angle;
}
