# Makefile for java programs

default: build

all: build view-func1 view-func2 run-part1 run-part2 view-part1 view-par2

clean:
	rm -f part1/parser.java part1/sym.java part1/Yylex.java* part1/*.class
	rm -f part2/parser.java part2/sym.java part2/Yylex.java* part2/*.class
	rm -f *.class mini.jar mini_gc.jar Yylex.java~ *.java *.class

build:
	java -jar lib/java-cup-11a.jar interpreterext.cup
	jflex interpreterext.flex
	cp parser.java sym.java Yylex.java part1/
	cp parser.java sym.java Yylex.java part2/
#	export CLASSaaaPATH=$CLASSPATH:java-cup-11a.jar
	javac part1/*.java -cp $CLASSPATH:lib/java-cup-11a.jar
	jar xf lib/java-cup-11a.jar
	jar cmf mainClass mini.jar -C part1/ .  java_cup
	javac part2/*.java -cp $CLASSPATH:lib/java-cup-11a.jar
	jar cmf mainClass mini_gc.jar -C part2/ .  java_cup
	rm -R java_cup META-INF

view-func1: lengthItr
	more lengthItr

view-func2: lengthRec
	more lengthRec

view-part1: part1
	more interpreterext.flex interpreterext.cup part1/Program.java part1/Element.java

view-part2: part2
	more interpreterext.flex interpreterext.cup part2/Program.java part2/Element.java

run-part1: mini.jar
	java -jar mini.jar

run-part2: mini_gc.jar
	java -jar mini_gc.jar
