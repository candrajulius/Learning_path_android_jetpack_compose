package com.candra.makealistpahlawan.data

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.candra.makealistpahlawan.model.Hero
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.lang.IllegalArgumentException

class JetHeroesViewModel(
    private val repository: HeroRepository
): ViewModel()
{
    private val _groupedHeroes = MutableStateFlow(
        repository.getHeroes()
            .sortedBy { it.name }
            .groupBy { it.name[0]}
    )
    val groupedHeroes: StateFlow<Map<Char,List<Hero>>> get() = _groupedHeroes

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String){
        _query.value = newQuery
        _groupedHeroes.value = repository.searchHeroes(_query.value)
            .sortedBy { it.name }
            .groupBy { it.name[0]}
    }
}


class ViewModelFactory(private val repository: HeroRepository):
    ViewModelProvider.NewInstanceFactory()
{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JetHeroesViewModel::class.java))
        {
            return JetHeroesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}

/*
Catatan
Pada kode diatas, kita menggunakan mekanisme backing property
supaya tercipta enkapsulasi pada data yang ada di ViewModel. Hal ini untuk menghindari
variabel tersebut dari luar ViewModel

Selain itu, data disimpan sebagai StateFlow supaya data dapat terupdate ketika terjadi
perubahan. Jika Anda sebelumnya menggunakan LiveData,

Perhatikan bahwa Anda dapat menggunakan mutableStateOf dan State untuk menyimpan data
 */