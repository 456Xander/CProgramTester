NAME = test
SRC = $(wildcard *.c)
OBJ = $(patsubst %.c %.o)

all: $(OBJ)
	gcc $(OBJ) -o $(NAME).exe
%.o: %.c
	gcc -c $< -o $@