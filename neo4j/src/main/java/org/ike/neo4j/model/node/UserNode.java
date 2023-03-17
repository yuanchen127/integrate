package org.ike.neo4j.model.node;

import lombok.Data;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

/**
 * @Author ike
 * 用户信息
 */
@NodeEntity(label = "UserNode")
@Data
public class UserNode {

    @Id
    private Long nodeId;

    @Property
    private String userId;

    @Property
    private String name;

    @Property
    private int age;
}
