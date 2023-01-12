package com.candra.listpahlawan.repository

import com.candra.listpahlawan.model.Hero
import com.candra.listpahlawan.model.HeroesData

class HeroRepository {
    fun getHeroes(): List<Hero>{
        return HeroesData.heroes
    }

    fun searchHeroes(query: String): List<Hero>{
        return HeroesData.heroes.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }
}