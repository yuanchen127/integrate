package org.ike.neo4j.model.relation;

import lombok.Data;
import org.ike.neo4j.model.node.UserNode;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * @Author ike
 * 用户关系
 */
@RelationshipEntity(type = "UserRelation")
@Data
public class UserRelation {

    @Id
    private Long id;

    @StartNode
    private UserNode startNode;

    @EndNode
    private UserNode endNode;
}
