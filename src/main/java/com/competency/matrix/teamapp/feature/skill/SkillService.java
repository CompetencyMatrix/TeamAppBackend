package com.competency.matrix.teamapp.feature.skill;

import com.competency.matrix.teamapp.feature.employeeSkill.EmployeeSkillLevel;
import com.competency.matrix.teamapp.feature.skill.dto.SkillDto;
import com.competency.matrix.teamapp.feature.skill.dto.SkillMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillService implements SkillServiceInterface {
    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;

    public List<SkillDto> getSkills() {
        return skillMapper.entityToDto(skillRepository.findAll());
    }

    public void addSkills(List<SkillDto> skills) {
        skillRepository.saveAll(skillMapper.dtoToEntity(skills));
    }

    public void addSkill(SkillDto skill) {
        skillRepository.save(skillMapper.dtoToEntity(skill));
    }

    public List<String> getSkillLevels() {
        return Arrays.stream(EmployeeSkillLevel.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
