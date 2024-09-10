package dev.hydris.cover;

import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Map;

public class Calculator {

    private String toParse;
    private List<Token> tokenList;
    private AstNode astRoot;
    private Integer crunched;

    private static Map<String, Integer> priorityMap = Map.of(
            "+", 1,
            "-", 1,
            "*", 2,
            "/", 2
            );

    Calculator() {}

    public int eval(String s, boolean printAst) throws ParsingException, TokenException {
        // System.out.println(s.length() == 0);
        if (s.length() == 0) {
            throw new ParsingException(s, "Unable to parse an empty string.");
        }
        toParse = s;
        tokenList = new ArrayList<Token>();
        
        Token t = new Token(toParse);
        while (t.getUnParsed().length() > 0) {
            t.parse();
            tokenList.add(t.clone());
        }
        
        validateTokens();
        createAst();
        if (printAst) {
            System.out.println(astRoot.serialize());
        }
        evalAst();
        
        return crunched;
    }

    public int eval(String s) throws ParsingException, TokenException {
        return eval(s, false);
    }

    private void validateTokens() throws ParsingException {
        // The only way to be valid is to be alternating between values and ops
        // Also the first and last must be values
   
        // Ensure first and last are valid
        
        if (tokenList.get(0).kind != TokenKind.value) {
            throw new ParsingException(tokenList.get(0).getUnParsed(), "First token is not a valid value.");
        } else if (tokenList.get(tokenList.size() - 1).kind != TokenKind.value) {
            throw new ParsingException(tokenList.get(tokenList.size() - 1).getUnParsed(), "Last token is not a valid value.");
        }       
        
        
        // Ensure that the tokens are alternating.
        TokenKind expecting = TokenKind.value;
 
        for (ListIterator<Token> iter = tokenList.listIterator(); iter.hasNext();) {
            Token t = iter.next();

            if (t.kind != expecting) {
                throw new ParsingException(t.getUnParsed(), "Missmatch between expected value or operator kind.");
            }

            // Update the expected value
            switch (expecting) {
                case value:
                    expecting = TokenKind.operator;
                    break;
                case operator:
                    expecting = TokenKind.value;
                    break;
            }
        }
    }

    private int getLevel(AstNode node) throws TokenException {
        if (node.token.kind == TokenKind.value) {
            return 999; // This is just a high value that is high for the sake of being high. It shouldn't actually matter.
        } else {
            return priorityMap.get(node.token.getOperator());
        }
    }
	
    private void createAst() throws TokenException {
    
        // Handle the case where there is only one node with no operators.
        if (tokenList.size() == 1) {
            astRoot = new AstNode(tokenList.get(0));
            return;
        }

        // We have at least 3 tokens, one of which is an operator.
        // The actual length of these arrays don't really matter as long as all priority levels can fit in it.
        Integer listSize = 4;  // This should be large enough to hold all roots for all priority trees.
   
        List<AstNode> priorityRoot = new ArrayList<AstNode>(4);          
        List<AstNode> priorityLast = new ArrayList<AstNode>(4);

        for (int i = 0; i < listSize; i++) {
            priorityRoot.add(null);
            priorityLast.add(null);
        }

        AstNode lastNode = null;


        int lookingIndex = 0;
        while (lookingIndex < tokenList.size()) {
            // if (lastNode != null) {
            //     System.out.println(lastNode.serialize());
            // } else {
            //     System.out.println("null");
            // }
            if (lookingIndex + 1 == tokenList.size()) {  // We have reached the end which is also an only value.
                lastNode.children.add(new AstNode(tokenList.get(lookingIndex)));
                
                break;
            }
            
            AstNode nextVal = new AstNode(tokenList.get(lookingIndex));
            AstNode nextOp = new AstNode(tokenList.get(lookingIndex + 1));
        
            if (lastNode == null) { // This only happens on the very first node.
                    nextOp.children.add(nextVal);
                    lastNode = nextOp;
                    priorityRoot.set(getLevel(nextOp), nextOp);
                    priorityLast.set(getLevel(nextOp), nextOp);
            } else if (getLevel(nextOp) >= getLevel(lastNode)) {
                nextOp.children.add(nextVal);
                lastNode.children.add(nextOp);
                lastNode = nextOp;
                priorityLast.set(getLevel(nextOp), nextOp);
                if (priorityRoot.get(getLevel(nextOp)) == null) {
                    priorityRoot.set(getLevel(nextOp), nextOp);
                }
            } else {  // The next op isn't as importart so the value binds left instead of the normal right
                lastNode.children.add(nextVal);

                // Try to grab the last node that either matches the nextOp priority or is lower
                int lastPrioCheckIndex = getLevel(nextOp);
                AstNode lastLowerPrio = null;

                while (lastPrioCheckIndex >= 0 && lastLowerPrio == null) {
                    lastLowerPrio = priorityLast.get(lastPrioCheckIndex);
                    lastPrioCheckIndex--;
                }
                
                if (lastLowerPrio != null) {  // There is a place for diff to attach to
                    AstNode temp = lastLowerPrio.children.remove(lastLowerPrio.children.size() - 1);
                    lastLowerPrio.children.add(nextOp);
                    nextOp.children.add(temp);
                    lastNode = nextOp;
                    priorityLast.set(getLevel(nextOp), nextOp);
                } else { // There is no last seen node for the lower level prio
                    // This handles the case of 
                    AstNode tempRoot = priorityRoot.get(getLevel(lastNode));
                    nextOp.children.add(tempRoot);
                    lastNode = nextOp;
                    priorityLast.set(getLevel(nextOp), nextOp);
                    priorityRoot.set(getLevel(nextOp), nextOp);
                }
                
            }

            lookingIndex += 2;

        }

        // Now that we are done, grad the lowest priority root
        for (AstNode node: priorityRoot) {
            if (node != null) {
                astRoot = node;
                return;
            }
        }

    }

    private void evalAst() throws TokenException {
        crunched = evalNode(astRoot);
    }

    private Integer evalNode(AstNode node) throws TokenException {
        if (node.token.kind == TokenKind.value) {
            return node.token.getValue();
        }
        
        // This must be an operator
        // Optimization because we know that it must be a binary operator here
        Integer lhs = evalNode(node.children.get(0));
        Integer rhs = evalNode(node.children.get(1));
        switch (node.token.getOperator()) {
            case "+":
                return lhs + rhs;
            case "-":
                return lhs - rhs;
            case "*":
                return lhs * rhs;
            case "/":
                return lhs / rhs;
            default:
                throw new TokenException(node.token, "Invalid operator in token.");
                
        }
    }

}


