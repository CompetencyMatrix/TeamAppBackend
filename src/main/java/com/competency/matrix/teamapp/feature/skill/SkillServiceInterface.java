package com.competency.matrix.teamapp.feature.skill;

import java.util.List;

public interface SkillServiceInterface {
    List<Skill> getSkills();

    void addSkills(List<Skill> skills);

    void addSkill(Skill skill);

    List<String> getSkillLevels();

}
