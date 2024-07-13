package banquemisr.challenge05.tasks.management.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import banquemisr.challenge05.tasks.management.demo.dtos.RequestBodyDTO;
import banquemisr.challenge05.tasks.management.demo.dtos.TaskDTO;
import banquemisr.challenge05.tasks.management.demo.models.Task;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RequestBodyMapper {
    RequestBodyMapper INSTANCE = Mappers.getMapper(RequestBodyMapper.class);


    @Mapping(source = "status", target = "status.id")
 
    @Mapping(source = "priority", target = "priority.id")

    @Mapping(source = "user", target = "userId.email")
    Task requestBodyDTOToTask(RequestBodyDTO task);


   
    TaskDTO TaskTorequestBodyDTO(Task task);
}
