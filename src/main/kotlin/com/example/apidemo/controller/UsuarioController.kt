package com.example.apidemo.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("api/users")
class UsuarioController {

    var usuarios: MutableList<Usuario> = mutableListOf(
        Usuario(UUID.randomUUID().toString(), "arturito", "123456", "arturitoti@gmail.com")
    )

    @GetMapping
    fun getUserList(): ResponseEntity<List<Usuario>> {
        return ResponseEntity.ok().body(usuarios)
    }

    @GetMapping("/id/{id}")
    fun getByEmail(@PathVariable id: String): ResponseEntity<Usuario> {
        usuarios.forEach {
            if (it.id == id) {
                return ResponseEntity.ok().body(it)
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
    }

    @PostMapping
    fun createUser(@RequestBody usuario: Usuario): ResponseEntity<Any> {
        usuarios.forEach {
            if (it.email == usuario.email) {
                return ResponseEntity.badRequest().body("Email já existente")
            }
        }

        usuario.id = UUID.randomUUID().toString()
        usuarios.add(usuario)

        return ResponseEntity.ok().build()
    }


    @DeleteMapping("id/{id}")
    fun deleteUser(@PathVariable id: String): ResponseEntity<Any> {
        var userFound: Usuario? = null
        usuarios.forEach {
            if (it.id == id) {
                userFound = it
            }
        }

        usuarios.remove(userFound)
        return ResponseEntity.ok().build()
    }

    @PutMapping("/id/{id}")
    fun fullUpdateUser(@PathVariable("id") id: String, @RequestBody user: Usuario): ResponseEntity<Any> {
        var userFound: Usuario? = null
        usuarios.forEach {
            if (it.id == id) {
                userFound = it
                it.email = user.email
                it.username = user.username
                it.password = user.password

                return ResponseEntity.ok().body(it)
            }
        }
        return ResponseEntity.notFound().build()
    }
}