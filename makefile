# 313263493
# mizraco1

compile: bin
	javac -cp biuoop-1.4.jar:src -d bin src/*.java src/Ass7Game.java

jar:
	jar cfm ass7game.jar Manifest.mf -C bin . -C resources . 

run:
	java -cp biuoop-1.4.jar:bin:resources Ass7Game

bin:
	mkdir bin
