/********************************************************************************
** Form generated from reading UI file 'flappybird.ui'
**
** Created by: Qt User Interface Compiler version 5.13.0
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_FLAPPYBIRD_H
#define UI_FLAPPYBIRD_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QStatusBar>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_FlappyBird
{
public:
    QWidget *centralWidget;
    QStatusBar *statusBar;
    QMenuBar *menuBar;

    void setupUi(QMainWindow *FlappyBird)
    {
        if (FlappyBird->objectName().isEmpty())
            FlappyBird->setObjectName(QString::fromUtf8("FlappyBird"));
        FlappyBird->resize(800, 600);
        centralWidget = new QWidget(FlappyBird);
        centralWidget->setObjectName(QString::fromUtf8("centralWidget"));
        FlappyBird->setCentralWidget(centralWidget);
        statusBar = new QStatusBar(FlappyBird);
        statusBar->setObjectName(QString::fromUtf8("statusBar"));
        FlappyBird->setStatusBar(statusBar);
        menuBar = new QMenuBar(FlappyBird);
        menuBar->setObjectName(QString::fromUtf8("menuBar"));
        menuBar->setGeometry(QRect(0, 0, 800, 20));
        FlappyBird->setMenuBar(menuBar);

        retranslateUi(FlappyBird);

        QMetaObject::connectSlotsByName(FlappyBird);
    } // setupUi

    void retranslateUi(QMainWindow *FlappyBird)
    {
        FlappyBird->setWindowTitle(QCoreApplication::translate("FlappyBird", "FlappyBird", nullptr));
    } // retranslateUi

};

namespace Ui {
    class FlappyBird: public Ui_FlappyBird {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_FLAPPYBIRD_H
