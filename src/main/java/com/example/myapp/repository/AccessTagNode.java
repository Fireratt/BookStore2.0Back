package com.example.myapp.repository;

import java.util.List;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import com.example.myapp.data.TagNode;

import static org.neo4j.cypherdsl.core.StatementCatalog.Clause.MATCH;
public interface AccessTagNode extends Neo4jRepository<TagNode , Long>{
    @Query(value = "MATCH(n:Tag) Where id(n) = $0 OPTIONAL MATCH (n) - [:Contain]->(first) OPTIONAL MATCH (first) - [:Contain]->(second) RETURN Collect(Distinct first) + Collect(Distinct second) + Collect(Distinct n)")
	List<TagNode> findByContainList(int tagId);
}
