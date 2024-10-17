package com.pvpgame.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("enemies")
@OpenAPIDefinition(info = @Info(title = "PVP-Game API", version = "1.0"))
@Tag(name = "Enemy Controller", description = "API to manage enemies and their actions")
public class EnemyController {
}
