#include "flappybird.h"
#include "ui_flappybird.h"
#include <QDir>
#include <QDebug>
#include <string>

FlappyBird::FlappyBird(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::FlappyBird)
{
    //Chemin vers le dossier res
    QDir res = QDir::currentPath();
    res.cdUp();
    res.cd("res");

    ui->setupUi(this);

        //Background
        QPixmap bkgnd(res.absolutePath() + QDir::separator() + "background.png");
        bkgnd = bkgnd.scaled(this->size(), Qt::IgnoreAspectRatio);
        QPalette palette;
        palette.setBrush(QPalette::Background, bkgnd);
        this->setPalette(palette);
}

FlappyBird::~FlappyBird()
{
    delete ui;
}
