package com.example.demo;

public final class Bonus {



    public static Integer countBonus(Integer sum) { //Функция подсчета кол-ва полученных бонусов в зависимости от суммы покупки sum
        int bonuses = 0; //Новые бонусы пользователя (начисляющиеся от суммы покупки)

        if (sum < 500) {
            bonuses = 0;
        } else if (sum < 1000){
            bonuses += (Math.round(sum*2))/100;
        } else if (sum < 1500) {
            bonuses += (Math.round(sum*3))/100;
        } else if (sum < 2000) {
            bonuses += (Math.round(sum*4))/100;
        } else if (sum >= 2000) {
            bonuses += (Math.round(sum*5))/100;
        }

        //setBonus(bonuses); //Добавление новых бонусов пользователю
        return bonuses;
    }
}
