package banquemisr.challenge05.tasks.management.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import banquemisr.challenge05.tasks.management.demo.dtos.TaskDTO;
import banquemisr.challenge05.tasks.management.demo.models.Task;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface TaskMapper {
 
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);


    @Mapping(source = "status.id", target = "status.id")
    @Mapping(source = "status.value", target = "status.value")
    @Mapping(source = "priority.id", target = "priority.id")
    @Mapping(source = "priority.value", target = "priority.value")
    @Mapping(source = "userId.email", target = "user.email")
    TaskDTO taskToTaskDTO(Task task);

    @Mapping(source = "status.id", target = "status.id")
    @Mapping(source = "priority.id", target = "priority.id")
    @Mapping(source = "user.email", target = "userId.email")
    Task taskDTOToTask(TaskDTO taskDTO);

  


}
