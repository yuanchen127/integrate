package org.ike.neo4j.repository.node;

import org.ike.neo4j.model.node.UserNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserNodeRepo extends Neo4jRepository<UserNode, Long> {

    /**
     * 查询所有节点
     * @return
     */
    @Query("MATCH (n:User) RETURN n ")
    List<UserNode> getUserNodeList();

    /**
     * 创建节点
     * @param userId
     * @param name
     * @param age
     * @return
     */
    @Query("create (n:User{userId:$userId,age:$age,name:$name}) RETURN n ")
    List<UserNode> addUserNodeList(@Param("userId") String userId, @Param("name") String name, @Param("age")int age);
}
