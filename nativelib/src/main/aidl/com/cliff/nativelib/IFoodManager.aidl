// IFoodManager.aidl
package com.cliff.nativelib;

import com.cliff.nativelib.Food;

interface IFoodManager {
    void addFood(in Food food);
    void removeFood(in Food food);
}