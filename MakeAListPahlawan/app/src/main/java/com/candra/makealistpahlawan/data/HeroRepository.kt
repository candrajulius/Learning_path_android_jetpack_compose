package com.candra.makealistpahlawan.data

import com.candra.makealistpahlawan.model.Hero
import com.candra.makealistpahlawan.model.HeroesData

class HeroRepository {
    fun getHeroes(): List<Hero> {
        return HeroesData.heroes
    }
    fun searchHeroes(query: String): List<Hero>{
        return HeroesData.heroes.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }
}