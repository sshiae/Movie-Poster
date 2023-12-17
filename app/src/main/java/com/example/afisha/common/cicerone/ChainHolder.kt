package com.example.afisha.common.cicerone

import androidx.fragment.app.Fragment
import java.lang.ref.WeakReference

/**
 * Stores the navigation chain for Cicerone.
 */
interface ChainHolder {

    /**
     * The navigation chain itself, where all fragments used in navigation are stored.
     */
    val chain: MutableList<WeakReference<Fragment>>
}