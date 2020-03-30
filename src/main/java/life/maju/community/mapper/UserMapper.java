package life.maju.community.mapper;

import life.maju.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO user(name,account_id,token,get_create,gmt_modified) VALUE(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);
}
