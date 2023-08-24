package com.competency.matrix.teamapp.skill;

import java.util.List;

public interface SkillServiceInterface {
    public List<Skill> getSkills();
    public void addSkills(List<Skill> skills);
    public void addSkill(Skill skill);

}
