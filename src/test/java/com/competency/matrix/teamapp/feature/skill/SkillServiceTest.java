package com.competency.matrix.teamapp.feature.skill;

import com.competency.matrix.teamapp.feature.skill.dto.SkillDto;
import com.competency.matrix.teamapp.feature.skill.dto.SkillMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SkillServiceTest {
    @Mock
    private SkillRepository skillRepository;
    @Spy
    private SkillMapper skillMapper = Mappers.getMapper(SkillMapper.class);
    @InjectMocks
    private SkillService underTest;

    @Value("#{'${tapp.skill.levels}'.split(',')}")
    private List<String> possibleSkillLevels;
    private Skill skill;
    private SkillDto skillDto;
    private UUID id;

    @BeforeEach
    void setUp() {
        id = UUID.fromString("5fc03087-d265-11e7-b8c6-83e29cd24f4c");
        skill = new Skill(id, "TestProject");
        skillDto = skillMapper.entityToDto(skill);
    }

    @Test
    void when_getSkillLevels_should_returnSkillLevels() {
        //GIVEN
        possibleSkillLevels = possibleSkillLevels == null ? List.of("JUNIOR", "MID", "SENIOR") : possibleSkillLevels;
        //WHEN
        List<String> returnedLevels = underTest.getSkillLevels();

        //THEN
        assertThat(returnedLevels).hasSameElementsAs(possibleSkillLevels);
    }

    @Test
    void when_getSkills_should_callFindAll() {
        //GIVEN
        List<Skill> skillList = List.of(skill);
        when(skillRepository.findAll()).thenReturn(skillList);

        //WHEN
        List<SkillDto> skillDtoList = this.underTest.getSkills();

        //THEN
        assertThat(skillMapper.entityToDto(skillList)).isEqualTo(skillDtoList);
        verify(this.skillRepository).findAll();
    }

    @Test
    void when_addSkills_should_callSaveAll() {
        //GIVEN
        List<SkillDto> skillDtoList = List.of(skillDto);
        ArgumentCaptor<List> projectArgumentCaptor = ArgumentCaptor.forClass(List.class);

        //WHEN
        this.underTest.addSkills(skillDtoList);

        //THEN
        verify(this.skillRepository)
                .saveAll(projectArgumentCaptor.capture());
        assertThat(skillMapper.entityToDto(projectArgumentCaptor.getValue()))
                .isEqualTo(skillDtoList);
    }

    @Test
    void when_addSkill_should_callSave() {
        //GIVEN
        ArgumentCaptor<Skill> projectArgumentCaptor = ArgumentCaptor.forClass(Skill.class);

        //WHEN
        this.underTest.addSkill(skillDto);

        //THEN
        verify(this.skillRepository)
                .save(projectArgumentCaptor.capture());
        assertThat(skillMapper.entityToDto(projectArgumentCaptor.getValue()))
                .isEqualTo(skillDto);
    }
}