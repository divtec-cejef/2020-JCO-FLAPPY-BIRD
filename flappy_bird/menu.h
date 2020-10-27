#ifndef MENU_H
#define MENU_H

#include <QMainWindow>
#include "flappybird.h"

namespace Ui {
class Menu;
}

class Menu : public QMainWindow
{
    Q_OBJECT

public:
    explicit Menu(QWidget *parent = nullptr);
    ~Menu();

private:
    Ui::Menu *ui;
    FlappyBird *flappybird = new FlappyBird();

private slots:
    void on_BTNplay_clicked();
};

#endif // MENU_H
