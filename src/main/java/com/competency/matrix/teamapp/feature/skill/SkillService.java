package com.competency.matrix.teamapp.feature.skill;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService implements SkillServiceInterface {
    private final SkillRepository skillRepository;

    public List<Skill> getSkills() {
        return skillRepository.findAll();
    }

    public void addSkills(List<Skill> skills) {
        skillRepository.saveAll(skills);
    }

    public void addSkill(Skill skill) {
        skillRepository.save(skill);
    }
}
