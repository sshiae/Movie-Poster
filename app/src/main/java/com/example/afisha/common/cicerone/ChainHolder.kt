package com.example.afisha.common.cicerone

import androidx.fragment.app.Fragment
import java.lang.ref.WeakReference

/**
 * Хранит цепь навигации для Cicerone
 */
interface ChainHolder {

    /**
     * Непосредственно сама цепь навигации, сюда кладутся все фрагменты,
     * которые используются при навигации
     */
    val chain: MutableList<WeakReference<Fragment>>
}