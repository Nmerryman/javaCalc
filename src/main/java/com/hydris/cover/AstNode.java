package com.hydris.cover;

import java.util.ArrayList;
import java.util.List;

public class AstNode {

    public Token token;
    public List<AstNode> children;

    AstNode(Token token) {
        this.token = token;
        children = new ArrayList<AstNode>();
    }

    public void add(AstNode node) {
        children.add(node);
    }
    

}


