package jasper.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import jasper.entity.User;

@Mapper
public interface UserMapper {
	User getUser(int i);

	List<User> getUsers(Map<String, Object> parameters);
}
