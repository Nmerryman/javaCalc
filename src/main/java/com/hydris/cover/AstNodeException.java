package com.hydris.cover;

public class AstNodeException extends Exception {
   
    public AstNode astNode;
    public AstNode rootNode;

    AstNodeException(AstNode current, AstNode root, String message) {
        super(message);
        astNode = current;
        rootNode = root;
    }

    AstNodeException(AstNode current, String message) {
        this(current, current, message);
    }

    AstNodeException(AstNode current, AstNode root, String message, Throwable cause) {
        super(message, cause);
        astNode = current;
        rootNode = root;
    }

    public AstNode getNode() {
        return astNode;
    }

    public AstNode getRoot() {
        return rootNode;
    }

}


