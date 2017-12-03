NAME = test
SRC = $(wildcard *.c)
OBJ = $(patsubst %.c,%.o,$(SRC))

all: $(OBJ)
	gcc $(OBJ) -o $(NAME).exe
%.o: %.c
	gcc -c $< -o $@