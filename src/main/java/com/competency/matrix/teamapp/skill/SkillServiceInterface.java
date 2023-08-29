package com.competency.matrix.teamapp.skill;

import java.util.List;

public interface SkillServiceInterface {
    List<Skill> getSkills();

    void addSkills(List<Skill> skills);

    void addSkill(Skill skill);

}
