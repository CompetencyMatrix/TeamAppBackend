package com.competency.matrix.teamapp.feature.skill;

import com.competency.matrix.teamapp.feature.skill.dto.SkillDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("skills")
@RequiredArgsConstructor
public class SkillController {
    private final SkillServiceInterface skillService;

    @GetMapping
    public ResponseEntity<List<SkillDto>> getSkills() {
        return ResponseEntity.ok(skillService.getSkills());
    }

    @GetMapping("levels")
    public ResponseEntity<List<String>> getSkillLevels() {
        return ResponseEntity.ok(skillService.getSkillLevels());
    }
}
