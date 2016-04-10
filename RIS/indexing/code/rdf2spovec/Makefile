TARGET = rdf2spovec
INCLUDES = `pkg-config raptor2 --cflags`
LIBS = `pkg-config raptor2 --libs`
CC = g++
CFLAGS = -O3 -std=c++0x -w -fpermissive -D_USEHASHIRRPOLY_
#CFLAGS = -g -std=c++0x -w -fpermissive -D_USEHASHIRRPOLY_
#CFLAGS = -O3 -std=c++0x -w -fpermissive -D_USEMAP_
#CFLAGS =  -g -w -fpermissive
#CFLAGS = -pg -g -Wall -fpermissive

default: $(TARGET)
all: default

OBJECTS = $(patsubst %.cc, %.o, $(wildcard *.cc))
HEADERS = $(wildcard *.h)

%.o: %.cc $(HEADERS)
	$(CC) $(CFLAGS) $(INCLUDES) -c $< -o $@

.PRECIOUS: $(TARGET) $(OBJECTS)

$(TARGET): $(OBJECTS)
	$(CC) $(CFLAGS) $(OBJECTS) $(LIBS) -o $@

clean:
	-rm -f *.o
	-rm -f $(TARGET)
