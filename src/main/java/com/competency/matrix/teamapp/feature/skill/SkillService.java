package com.competency.matrix.teamapp.feature.skill;

import com.competency.matrix.teamapp.feature.employeeSkill.EmployeeSkillLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<String> getSkillLevels() {
        return Arrays.stream(EmployeeSkillLevel.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
