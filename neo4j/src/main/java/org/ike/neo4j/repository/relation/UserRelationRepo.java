package org.ike.neo4j.repository.relation;

import org.ike.neo4j.model.relation.UserRelation;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRelationRepo extends Neo4jRepository<UserRelation, Long> {

    /**
     * 通过id 查询关系
     * @param firstUserId
     * @param secondUserId
     * @return
     */
    @Query("match p=(n:User)<-[r:FRIEND]->(n1:User) where n.userId=$firstUserId and n1.userId=$secondUserId return p")
    List<UserRelation> findUserRelationByEachId(@Param("firstUserId") String firstUserId, @Param("secondUserId") String secondUserId);

    /**
     * 添加关系
     * @param firstUserId
     * @param secondUserId
     * @return
     */
    @Query("match (fu:User),(su:User) where fu.userId=$firstUserId and su.userId=$secondUserId create p=(fu)-[r:FRIEND]->(su) return p")
    List<UserRelation> addUserRelation(@Param("firstUserId") String firstUserId, @Param("secondUserId") String secondUserId);
}
