#ifndef FLAPPYBIRD_H
#define FLAPPYBIRD_H

#include <QMainWindow>

namespace Ui {
class FlappyBird;
}

class FlappyBird : public QMainWindow
{
    Q_OBJECT

public:
    explicit FlappyBird(QWidget *parent = nullptr);
    ~FlappyBird();

private:
    Ui::FlappyBird *ui;
};

#endif // FLAPPYBIRD_H
