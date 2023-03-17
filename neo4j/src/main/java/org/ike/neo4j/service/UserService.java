package org.ike.neo4j.service;

import org.ike.neo4j.model.node.UserNode;
import org.ike.neo4j.repository.node.UserNodeRepo;
import org.ike.neo4j.repository.relation.UserRelationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserNodeRepo userNodeRepo;

    @Autowired
    private UserRelationRepo userRelationRepo;

    public void addUserNode(UserNode userNode) {
        userNodeRepo.addUserNodeList(userNode.getUserId(), userNode.getName(), userNode.getAge());
    }

    public void addRelationship(String firstId, String secondId) {
        userRelationRepo.addUserRelation(firstId, secondId);
    }

    public void findUserRelationByEachId(String firstId, String secondId) {
        userRelationRepo.findUserRelationByEachId(firstId, secondId);
    }
}
