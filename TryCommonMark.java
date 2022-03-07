import java.util.ArrayList;

import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

class TryCommonMark {
    public static void main(String[] args) {
        // This part actually does the computation
        Parser parser = Parser.builder().build();
        Node node = parser.parse("[a link](something.com())[a link](something.com())");
        LinkVisitor visitor = new LinkVisitor();
        node.accept(visitor);
        System.out.println(visitor.links);
    }
}

// This class can be defined anywhere in the file
class LinkVisitor extends AbstractVisitor {
    ArrayList<String> links = new ArrayList<>();

    @Override
    public void visit(Link link) {
        // This is called for all Link nodes

        // Add current link to list
        links.add(link.getDestination());

        // Descend into children (could be omitted in this case because Link nodes can't have Link nodes)
        visitChildren(link);
    }
}
