CLASSPATH = lib/*:.

test: MarkdownParse.class MarkdownParseTest.class
	java -cp $(CLASSPATH) org.junit.runner.JUnitCore MarkdownParseTest

# Use -g for jdb
MarkdownParse.class: MarkdownParse.java
	javac MarkdownParse.java

# Use -g for jdb
MarkdownParseTest.class: MarkdownParseTest.java
	javac -cp $(CLASSPATH) MarkdownParseTest.java

TryCommonMark.class: TryCommonMark.java
	javac -g -cp $(CLASSPATH) TryCommonMark.java

clean:
	rm *.class