package dev.hydris.cover;

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
    
    public String serialize() throws TokenException {
        if (token.kind == TokenKind.value) {
            return String.valueOf(token.getValue());
        } else {
            String result = "(";
            result += token.getOperator(); 
            for (AstNode child: children) {
                result += " " + child.serialize();
            }
            result += ")";
            return result;
        }
    }

}


