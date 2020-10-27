/**
  \file
  \brief    Déclaration de la classe Bird
  \author   Louis Bovay
  \date     27.10.2020
*/

#include "bird.h"
//!
//! \brief Construit et initialise un oiseau
//! \param posX
//! \param posY
//!
Bird::Bird(int posX, int posY)
{
    this->posX = posX;
    this->posY = posY;
}

//!
//! \brief Donne la position Y de l'oiseau
//! \return la position Y de l'oiseau
//!
int Bird::getPosY(){
    return this->posY;
}

//!
//! \brief Change la position Y de l'oiseau
//! \param posY
//!
void Bird::setPosY(int posY){
    this->posY = posY;
}

//!
//! \brief déclare l'oiseau en tant que "mort"
//!
void Bird::kill(){
    this->isAlive = false;
}

//!
//! \brief regarde si l'oiseau est vivant ou mort
//! \return true = vivant / false = mort
//!
bool Bird::checkVitals(){
    return this->isAlive;
}
