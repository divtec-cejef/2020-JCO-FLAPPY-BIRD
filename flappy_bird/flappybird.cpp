#include "flappybird.h"
#include "ui_flappybird.h"

FlappyBird::FlappyBird(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::FlappyBird)
{
    ui->setupUi(this);
}

FlappyBird::~FlappyBird()
{
    delete ui;
}
