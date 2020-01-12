package com.tech.oauth.mapper;

import com.tech.oauth.entity.AuthClientDetails;
import com.tech.oauth.entity.AuthClientDetailsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AuthClientDetailsMapper {
    long countByExample(AuthClientDetailsExample example);

    int deleteByExample(AuthClientDetailsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AuthClientDetails record);

    int insertSelective(AuthClientDetails record);

    List<AuthClientDetails> selectByExample(AuthClientDetailsExample example);

    AuthClientDetails selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AuthClientDetails record, @Param("example") AuthClientDetailsExample example);

    int updateByExample(@Param("record") AuthClientDetails record, @Param("example") AuthClientDetailsExample example);

    int updateByPrimaryKeySelective(AuthClientDetails record);

    int updateByPrimaryKey(AuthClientDetails record);

    /**
     * 通过clientId查询接入的客户端详情
     *
     */
    AuthClientDetails selectByClientId(@Param("clientId") String clientId);
}