Exercise for Redi2
==================

This project represents the code for the Redi2 interview programming exercise.

Running the Code
----------------

A compiled `jar` (using Maven) can be found under the `/dist` directory. The code is packaged with all of its dependencies internal.

```
usage: process [OPTIONS] [<minutes> <minutes> <minutes>]
 -f,--file <file>   processes billing reports based on the passed file
 -h,--help          prints this message
```

In order to run the processor with command-line inputs, you may pass them as arguments, e.g.:


```
$ java -jar redi2-1.0.0-SNAPSHOT-with-deps.jar 400 4000 400000
```

You may also run the processor with a file as the input, e.g.:

```
$ java -jar redi2-1.0.0-SNAPSHOT-with-deps.jar --file my-minutes.txt
```

If you are running the processor using a file for batch processing, minutes must be on separate lines.
