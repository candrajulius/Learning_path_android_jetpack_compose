package com.candra.listpahlawan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.candra.listpahlawan.repository.HeroRepository

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: HeroRepository):
        ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JetHeroesViewModel::class.java)){
            return JetHeroesViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}
