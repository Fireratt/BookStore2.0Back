package com.example.myapp.data ; 

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

import org.springframework.data.neo4j.core.schema.GeneratedValue;

@Node(labels = {"Tag"})
public class TagNode{
    @Id @GeneratedValue private long id;

    private String name;

	public TagNode() {
		// Empty constructor required as of No4j API 2.0.5
	};

	public TagNode(String name) {
        this.name = name;
    }

    // @Relationship(type = "Contain")
	// public Set<TagNode> teammates;

    public String getName() {
		return name;
	}
    public void setName(String name) {
		this.name = name;
	}

    public int getId(){
        return (int)id ; 
    }
}
