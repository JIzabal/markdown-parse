import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

class TryCommonMark {
    public static void main(String[] args) {
        // this part actually does the computation
        Parser parser = Parser.builder().build();
        Node node = parser.parse("[a link](something.com())");
        LinkVisitor visitor = new LinkVisitor();
        node.accept(visitor);
        System.out.println(visitor.linkCount);;  // 4
    }
}

// this class can be defined anywhere in the file
class LinkVisitor extends AbstractVisitor {
    int linkCount = 0;

    @Override
    public void visit(Link link) {
        // This is called for all Text nodes. Override other visit methods for other node types.

        // Count words (this is just an example, don't actually do it this way for various reasons).
        linkCount++;

        // Descend into children (could be omitted in this case because Text nodes don't have children).
        visitChildren(link);
    }
}
