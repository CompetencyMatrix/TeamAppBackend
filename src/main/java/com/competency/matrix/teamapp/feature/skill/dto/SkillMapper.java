package com.competency.matrix.teamapp.feature.skill.dto;

import com.competency.matrix.teamapp.feature.skill.Skill;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SkillMapper {
    SkillDto entityToDto(Skill skill);

    List<SkillDto> entityToDto(Iterable<Skill> skill);

    Skill dtoToEntity(SkillDto skillDto);

    List<Skill> dtoToEntity(Iterable<SkillDto> skillDtos);
}
