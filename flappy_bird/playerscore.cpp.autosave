/**
  \file
  \brief    Déclaration de la classe PlayerScore
  \author   Louis Bovay
  \date     27.10.2020
*/

#include "playerscore.h"

//!
//! \brief Construit et initialise le score d'un joueur
//! \param playerName le nom du joueur
//!
PlayerScore::PlayerScore(std::string playerName)
{
    this->playerName = playerName;
    this->nbPoints = 0;
}

//!
//! \brief Ajoute des points au score du joueur
//! \param points nombre de points à ajouter
//!
void PlayerScore::addPoints(int points){
    this->nbPoints += points;
}

//!
//! \brief Retourne le nombre de point du joueur
//! \return le nombre de points du joueur
//!
int PlayerScore::getPoints(){
    return this->nbPoints;
}
