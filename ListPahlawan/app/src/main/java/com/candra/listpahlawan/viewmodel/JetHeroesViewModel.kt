package com.candra.listpahlawan.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.candra.listpahlawan.model.Hero
import com.candra.listpahlawan.repository.HeroRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class JetHeroesViewModel(
    private val repository: HeroRepository
): ViewModel()
{
    private val _groupedHeroes = MutableStateFlow(
        repository.getHeroes()
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    )

    val groupHeroes: StateFlow<Map<Char,List<Hero>>> get() = _groupedHeroes

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String,context: Context){
        _query.value = newQuery
        if (newQuery.isEmpty()){
            Toast.makeText(context,"Pencarian $newQuery tidak ditemukan",Toast.LENGTH_SHORT).show()
        }else{
            _groupedHeroes.value = repository.searchHeroes(_query.value)
                .sortedBy { it.name }
                .groupBy { it.name[0] }
        }

    }
}