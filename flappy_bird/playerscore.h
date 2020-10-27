/**
  \file
  \brief    Déclaration de la classe PlayerScore
  \author   Louis Bovay
  \date     27.10.2020
*/

#ifndef PLAYERSCORE_H
#define PLAYERSCORE_H

#include <string>

//!
//! \brief Classe qui gère un score de joueur
//!
class PlayerScore
{
public:
    // Constructor
    PlayerScore(std::string playerName);

    // Fonctions
    void addPoints(int points);
    int getPoints();

private:
    //Variables membres
    int nbPoints;
    std::string playerName;
};

#endif // PLAYERSCORE_H
