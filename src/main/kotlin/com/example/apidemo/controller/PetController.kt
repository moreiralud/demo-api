package com.example.apidemo.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("api/pets")
class PetController {

    var pets: MutableList<Pet> = mutableListOf(
        Pet(UUID.randomUUID().toString(), "Millie")
    )

    @GetMapping
    fun getUserList(): ResponseEntity<List<Pet>> {
        return ResponseEntity.ok().body(pets)
    }

    @GetMapping("/id/{id}")
    fun getById(@PathVariable id: String): ResponseEntity<Pet> {
        pets.forEach {
            if (it.id == id) {
                return ResponseEntity.ok().body(it)
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
    }

    @PostMapping
    fun createUser(@RequestBody pet: Pet): ResponseEntity<Any> {
        pets.forEach {
            if (it.id == pet.id) {
                return ResponseEntity.badRequest().body("Algo errado de aconteceu")
            }
        }

        pet.id = UUID.randomUUID().toString()
        pets.add(pet)

        return ResponseEntity.ok().build()
    }


    @DeleteMapping("id/{id}")
    fun deletePet(@PathVariable id: String): ResponseEntity<Any> {
        var userFound: Pet? = null
        pets.forEach {
            if (it.id == id) {
                userFound = it
            }
        }

        pets.remove(userFound)
        return ResponseEntity.ok().build()
    }

    @PutMapping("/id/{id}")
    fun fullUpdateUser(@PathVariable("id") id: String, @RequestBody user: Pet): ResponseEntity<Any> {
        var petFound: Pet? = null
        pets.forEach {
            if (it.id == id) {
                petFound = it
                it.id = user.id
                it.name = user.name

                return ResponseEntity.ok().body(it)
            }
        }
        return ResponseEntity.notFound().build()
    }
}