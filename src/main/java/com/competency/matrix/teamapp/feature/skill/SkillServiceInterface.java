package com.competency.matrix.teamapp.feature.skill;

import com.competency.matrix.teamapp.feature.skill.dto.SkillDto;

import java.util.List;

public interface SkillServiceInterface {
    List<SkillDto> getSkills();

    void addSkills(List<SkillDto> skills);

    void addSkill(SkillDto skill);

    List<String> getSkillLevels();

}
