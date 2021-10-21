package com.example.tiemchungdientu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MySort {
    public static void sortByDate () {

    }

    public static void sortByCreateAt (ArrayList arrayList, Object object) {
        Collections.sort(arrayList, new Comparator<Object>() {
            @Override
            public int compare(Object o, Object t1) {
                return 0;
            }


        });
    }
}
