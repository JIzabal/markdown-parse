// File reading code from https://howtodoinjava.com/java/io/java-read-file-to-string-examples/
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.commonmark.node.*;
import org.commonmark.parser.Parser;

public class MarkdownParse {

    static int findCloseParen(String markdown, int openParen) {
        int closeParen = openParen + 1;
        int openParenCount = 1;
        while (openParenCount > 0 && closeParen < markdown.length()) {
            if (markdown.charAt(closeParen) == '(') {
                openParenCount++;
            } else if (markdown.charAt(closeParen) == ')') {
                openParenCount--;
            }
            closeParen++;
        }
        if(openParenCount == 0) {
          return closeParen - 1;
        }
        else {
          return -1;
        }

    }
    public static Map<String, List<String>> getLinks(File dirOrFile) throws IOException {
        Map<String, List<String>> result = new HashMap<>();
        if(dirOrFile.isDirectory()) {
            for(File f: dirOrFile.listFiles()) {
                result.putAll(getLinks(f));
            }
            return result;
        }
        else {
            Path p = dirOrFile.toPath();
            int lastDot = p.toString().lastIndexOf(".");
            if(lastDot == -1 || !p.toString().substring(lastDot).equals(".md")) {
                return result;
            }
            ArrayList<String> links = getLinks(Files.readString(p));
            result.put(dirOrFile.getPath(), links);
            return result;
        }
    }
    public static ArrayList<String> getLinks(String markdown) {
        Parser parser = Parser.builder().build();
        Node node = parser.parse(markdown);
        LinkVisitor visitor = new LinkVisitor();
        node.accept(visitor);
        return visitor.links;
    }
    public static void main(String[] args) throws IOException {
        File input = new File(args[0]);

        if (input.isFile()) {
            Path fileName = Path.of(args[0]);
            String contents = Files.readString(fileName);
            ArrayList<String> links = getLinks(contents);
            System.out.println(links);
        } else if (input.isDirectory()) {
            Map<String, List<String>> linksMap = getLinks(input);
/*             for (Map.Entry<String, List<String>> entry : linksMap.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            } */

            linksMap.forEach((key, value) -> System.out.println(key + ": " + value));
        }
    }
}

class LinkVisitor extends AbstractVisitor {
    ArrayList<String> links = new ArrayList<>();

    @Override
    public void visit(Link link) {
        // This is called for all Link nodes

        // Add current link to list
        links.add(link.getDestination());

        // Descend into children
        visitChildren(link);
    }
}