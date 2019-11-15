JFLAGS = -g
JC = javac
JVM= java
MAIN = Main
.SUFFIXES: .java .class
.java.class:
        $(JC) $(JFLAGS) $*.java

CLASSES = \
        Server.java \
        Client.java \
        FileInfo.java \
        ServerDownload.java \
		Main.java

default: classes mainobj

classes: $(CLASSES:.java=.class)

mainobj: classes
	$(JVM) $(MAIN)

clean:
        $(RM) *.class
