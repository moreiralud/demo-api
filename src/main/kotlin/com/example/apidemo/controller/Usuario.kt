package com.example.apidemo.controller

import lombok.Builder

@Builder
data class Usuario (var id: String?, var username: String?, var password: String?, var email: String?)