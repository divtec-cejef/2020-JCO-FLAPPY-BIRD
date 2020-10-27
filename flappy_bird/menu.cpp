#include "menu.h"
#include "ui_menu.h"
#include <QDir>
#include <QDebug>

Menu::Menu(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::Menu)
{
    ui->setupUi(this);
}

void Menu::on_BTNplay_clicked(){
    flappybird->show();
    this->close();

}

Menu::~Menu()
{
    delete ui;
}
