<style>
    .code-container {
        overflow-x: auto; /* Enable horizontal scrolling */
        white-space: nowrap; /* Prevent line wrapping */
        max-width: 100%; /* Ensure the code block doesn't exceed its container */
    }
</style>
<details>
<summary><strong style="color: #122C8C;">1. Basics</strong></summary>

1. What is C programming?
   * C programming is a high-level programming language that was developed in the early 1970s. It is widely used for developing applications, operating systems, and embedded systems.

2. What is a variable in C?
   * A variable in C is a named memory location that holds a value. It is used to store data that can be modified during program execution.

3. What is the difference between int and float data types?
   * The int data type is used to store whole numbers, while the float data type is used to store floating-point numbers with decimals.

4. What is the difference between while and do-while loops?
   * The while loop executes a block of code as long as the given condition is true. The do-while loop is similar, but it guarantees that the code is executed at least once, even if the condition is initially false.

5. Explain the sizeof operator in C.
   * The sizeof operator is used to determine the size, in bytes, of a data type or variable. It returns the amount of memory required to store the operand.
</details>
&nbsp;
<details>
<summary ><strong style="color: #122C8C;">2. Pointers</strong></summary>

1. What is a pointer in C?
   * A pointer is a variable that stores the memory address of another variable. It allows direct manipulation of memory, enabling efficient memory management and access.

2. How do you declare a pointer in C?
   * To declare a pointer in C, you use the * symbol. For example, int *ptr; declares a pointer named ptr that points to an integer.

3. What is the difference between a pointer and a reference?
   * In C, a pointer is a separate entity that holds the memory address of another variable. On the other hand, a reference in C++ is an alias to an existing variable.

4. How do you access the value pointed by a pointer?
   * To access the value pointed by a pointer, you use the dereference operator *. For example, int x = *ptr; assigns the value pointed by ptr to the variable x.

5. What is the purpose of the NULL pointer?
   * The NULL pointer is a special pointer value that indicates the absence of a valid memory address. It is often used to initialize pointers or check for invalid pointers.

</details>
&nbsp;

<details>
<summary><strong style="color: #122C8C;">3. Arrays and Strings</strong></summary>

1. How do you declare an array in C?
   * To declare an array in C, you specify the data type of the elements followed by the array name and its size. For example, int numbers[5]; declares an integer array named numbers with a size of 5.

2. How do you access elements in an array?
   * Elements in an array can be accessed using their index. The index starts at 0, so numbers[0] accesses the first element, numbers[1] accesses the second element, and so on.

3. What is the difference between an array and a pointer?
   * An array in C is a collection of elements stored in a contiguous block of memory. A pointer, on the other hand, is a variable that stores the memory address of another variable.

4. How do you find the length of a string in C?
   * In C, you can find the length of a string using the strlen() function from the <string.h> library. It returns the number of characters in the string, excluding the null terminator.

5. Explain the difference between strcpy() and strncpy() functions.
   * The strcpy() function is used to copy one string to another, while the strncpy() function allows you to specify the number of characters to be copied.

</details>
&nbsp;
<details>
<summary><strong style="color: #122C8C;">4. Functions</strong></summary>


1. What is a function in C?
   * A function in C is a named block of code that performs a specific task. It can be called multiple times from different parts of a program, promoting code reusability.

2. How do you declare a function in C?
   * To declare a function in C, you specify the return type, function name, and parameters (if any). For example, int sum(int a, int b); declares a function named sum that takes two integers as parameters and returns an integer.

3. What is the difference between actual parameters and formal parameters?
   * Actual parameters, also known as arguments, are the values passed to a function when it is called. Formal parameters, on the other hand, are the variables declared in the function's parameter list that receive the values of the actual parameters.

4. How do you return a value from a function in C?
   * To return a value from a function in C, you use the return statement followed by the value you want to return. For example, return x; returns the value of x from the function.

5. What is recursion in C?
   * Recursion in C is a technique where a function calls itself to solve a problem. It is particularly useful for solving problems that can be broken down into smaller, simpler versions of the same problem.
</details>
&nbsp;
<details>
<summary><strong style="color: #122C8C;">5. Structures and Pointers to Structures</strong></summary>

1. What is a structure in C?
   * A structure in C is a user-defined data type that allows you to combine different types of variables under a single name. It is used to represent a record or an entity with multiple attributes.

2. How do you declare a structure in C?
   * To declare a structure in C, you use the struct keyword followed by the structure name and its members. For example,
<div class="code-container">

```c

struct Person {
    char name[20];
    int age;
};

```
</div>
3. How do you access structure members in C?

* You can access structure members using the dot (.) operator. For example, person.name accesses the name member of the person structure.

4. What is a pointer to a structure?

* A pointer to a structure in C is a variable that stores the memory address of a structure. It allows indirect access to the structure members using the arrow (->) operator.

5. How do you allocate memory for a structure dynamically?

* To allocate memory for a structure dynamically, you use the malloc() function from the <stdlib.h> library. For example,

<div class="code-container">

```c

struct Person* p = (struct Person*)malloc(sizeof(struct Person));

```
</div>
</details>
&nbsp;
<details>
<summary><strong style="color: #122C8C;">6. File Handling</strong></summary>


1. How do you open a file in C?
   To open a file in C, you use the fopen() function from the <stdio.h> library. It takes two parameters: the file name and the mode in which to open the file.

2. What are the different file modes in C?
   * The different file modes in C are:
      * "r": Read mode
      * "w": Write mode (creates a new file or overwrites existing content)
      * "a": Append mode (appends data to an existing file or creates a new file)
      * "r+": Read and write mode
      * "w+": Read and write mode (creates a new file or overwrites existing content)
      * "a+": Read and append mode (appends data to an existing file or creates a new file)

3. How do you read from a file in C?
   * To read from a file in C, you use the fscanf() or fgets() function. fscanf() reads formatted data, while fgets() reads a line of text.

4. How do you write to a file in C?
   * To write to a file in C, you use the fprintf() or fputs() function. fprintf() writes formatted data, while fputs() writes a string.

5. How do you close a file in C?
   * To close a file in C, you use the fclose() function. It takes a file pointer as a parameter and releases the associated resources.
</details>
&nbsp;
<details>
<summary><strong style="color: #122C8C;">7. Memory Management</strong></summary>

1. What is dynamic memory allocation in C?
   * Dynamic memory allocation in C refers to the process of allocating memory at runtime. It allows you to allocate memory dynamically based on program needs.

2. How do you allocate memory dynamically in C?
   * To allocate memory dynamically in C, you use the malloc(), calloc(), or realloc() function from the <stdlib.h> library. malloc() allocates a block of memory, calloc() allocates and initializes a block of memory, and realloc() reallocates memory.

3. How do you deallocate memory in C?
   * To deallocate memory in C, you use the free() function. It takes a pointer to the memory block previously allocated with malloc(), calloc(), or realloc() and releases the memory.

4. What is memory leakage in C?
   * Memory leakage in C refers to the situation where allocated memory is not deallocated, resulting in memory loss. It can lead to inefficient memory usage and program crashes.

5. How do you prevent memory leakage in C?
   * To prevent memory leakage in C, you should ensure that all allocated memory is properly deallocated using the free() function when it is no longer needed.

</details>
&nbsp;
<details>
<summary><strong style="color: #122C8C;">8. Preprocessor Directives</strong></summary>


1. What are preprocessor directives in C?
   * Preprocessor directives in C are instructions that are processed by the preprocessor before the compilation of the program. They are identified by a # symbol.

2. What is the #include directive used for?
   * The #include directive is used to include the contents of another file in the current file. It is commonly used to include header files that define functions, constants, and data types.

3. What is the difference between <stdio.h> and "stdio.h" in an #include directive?
   * The <stdio.h> syntax is used to include standard library header files, while the "stdio.h" syntax is used to include user-defined header files or files in the current directory.

4. What is the purpose of the #define directive?
   * The #define directive is used to define a constant or a macro in C. It allows you to give a name to a value or a piece of code, making it easier to read and maintain.

5. How do you use conditional compilation in C?
   * Conditional compilation in C is achieved using the #ifdef, #ifndef, #if, and #endif directives. They allow you to include or exclude specific sections of code based on certain conditions.
</details>
&nbsp;
<details>
<summary><strong style="color: #122C8C;">9. Bitwise Operators</strong></summary>


1. What are bitwise operators in C?
   * Bitwise operators in C perform operations on individual bits of integer operands. They are used for tasks such as bit manipulation, flag checking, and optimizing memory usage.

2. List the bitwise operators available in C.
   * The bitwise operators available in C are:
     &: Bitwise AND
     |: Bitwise OR
     ^: Bitwise XOR
     ~: Bitwise NOT
     <<: Left shift
     >>: Right shift

3. What is the purpose of the << operator in C?
   * The << operator in C performs a left shift operation on the bits of an integer. It moves the bits to the left by a specified number of positions, effectively multiplying the integer by 2.

4. What is the purpose of the >> operator in C?
   * The >> operator in C performs a right shift operation on the bits of an integer. It moves the bits to the right by a specified number of positions, effectively dividing the integer by 2.

5. How do you set a particular bit in C?
   * To set a particular bit in C, you use the bitwise OR operator (|). For example, x = x | (1 << n); sets the nth bit of x to 1.
</details>
&nbsp;
<details>
<summary><strong style="color: #122C8C;">10. Enumerations</strong></summary>

1. What is an enumeration in C?
   * An enumeration in C is a user-defined data type that consists of a set of named constants. It allows you to create a list of meaningful identifiers for a set of related values.

2. How do you declare an enumeration in C?
   * To declare an enumeration in C, you use the enum keyword followed by the enumeration name and its list of values. For example,

<div class="code-container">

```c

enum Day {
    SUNDAY,
    MONDAY,
    TUESDAY,
    // ...
};
```
</div>
3. How do you assign values to enumeration constants?

* By default, the first constant in an enumeration has a value of 0, the second constant has a value of 1, and so on. However, you can assign specific values to enumeration constants. For example,

<div class="code-container">

```c

    enum Month {
        JANUARY = 1,
        FEBRUARY,
        MARCH,
        // ...
    };
```
</div>
4. How do you access enumeration constants in C?
    * You can access enumeration constants using the dot (.) operator. For example, enumName.constant accesses the constant within the enumeration.

5. Can you perform arithmetic operations on enumeration constants?
   * Yes, you can perform arithmetic operations on enumeration constants. The constants are treated as integers, allowing you to perform addition, subtraction, and other arithmetic operations on them.
</details>
&nbsp;
<details>
<summary><strong style="color: #122C8C;">11. Memory Layout and Alignment</strong></summary>

1. What is the memory layout of a C program?
   * The memory layout of a C program consists of several sections, including:
      * Text (Code) Section: Contains the compiled code of the program.
      * Data Section: Contains global and static variables.
      * Stack: Stores local variables and function call information.
      * Heap: Dynamically allocated memory for objects and data structures.

2. What is the size of the int data type in C?
   * The size of the int data type in C is compiler-dependent. It is typically 4 bytes on most modern systems, but it can be different on different platforms.

3. What is the difference between stack and heap memory?
   * Stack memory is used for storing local variables and function call information. It has a fixed size and follows a last-in-first-out (LIFO) order. Heap memory, on the other hand, is used for dynamic memory allocation. It has a variable size and is managed manually.

4. What is the purpose of the sizeof operator in C?
   * The sizeof operator in C is used to determine the size, in bytes, of a data type or variable. It returns the amount of memory required to store the operand.

5. What is structure padding in C?
   * Structure padding in C refers to the insertion of unused bytes between structure members to ensure proper alignment. It is done to improve memory access efficiency and align members on boundaries that are multiples of their sizes.
</details>
&nbsp;
<details>
<summary><strong style="color: #122C8C;">12. Recursion</strong></summary>

1. What is recursion in C?
   * Recursion in C is a technique where a function calls itself to solve a problem. It is particularly useful for solving problems that can be broken down into smaller, simpler versions of the same problem.

2. What is the base case in recursion?
   * The base case in recursion is a condition that determines when the recursion should stop. It is the simplest form of the problem that can be solved directly without further recursive calls.

3. What is the difference between direct recursion and indirect recursion?
   * Direct recursion occurs when a function directly calls itself. Indirect recursion, on the other hand, occurs when multiple functions call each other in a cycle.

4. What are the advantages of using recursion in C?
   * The advantages of using recursion in C include:
      * Simplicity: Recursive solutions can often be more concise and easier to understand.
      * Problem solving: Some problems are naturally recursive and can be solved more elegantly using recursion.
      * Function composition: Recursion allows you to break down complex problems into smaller, more manageable subproblems.

5. What are the limitations of using recursion in C?
   * The limitations of using recursion in C include:
      * Stack overflow: Recursion can consume a large amount of stack memory, leading to a stack overflow error if the recursion depth is too high.
      * Performance: Recursive solutions may be less efficient compared to iterative solutions in some cases.
      * Code readability: Recursive code can be more challenging to understand and debug for some programmers.
</details>
&nbsp;
<details>
<summary><strong style="color: #122C8C;">13. Error Handling</strong></summary>


1. How do you handle errors in C?
   * In C, errors can be handled using error codes, return values, and error-checking conditions. Functions can return error codes or special values to indicate errors, and the calling code can check for errors and take appropriate actions.

2. What is the purpose of the errno variable in C?
   * The errno variable in C is used to store error codes generated by library functions. It is defined in the <errno.h> header file and can be accessed after a function call to determine the cause of an error.

3. How do you handle runtime errors in C?
   * Runtime errors in C can be handled using exception handling mechanisms such as try-catch blocks or using error-checking conditions and proper error handling code. Additionally, defensive programming techniques can be used to prevent runtime errors.

4. What is the purpose of the assert() function in C?
   * The assert() function in C is used to check if a given condition is true. If the condition is false, the function terminates the program and prints an error message. It is primarily used for debugging purposes.

5. How do you use the setjmp() and longjmp() functions for error handling in C?
   * The setjmp() and longjmp() functions in C are used for non-local jumps and error handling. setjmp() sets a jump point and returns zero, while longjmp() performs a jump to the specified jump point and returns to the point of the setjmp() call.
</details>
&nbsp;
<details>
<summary><strong style="color: #122C8C;">14. Multithreading</strong></summary>

1. What is multithreading in C?
   * Multithreading in C refers to the concurrent execution of multiple threads within a single program. Threads are lightweight execution units that share the same memory space and can perform tasks independently.

2. How do you create and manage threads in C?
   * Threads in C can be created and managed using threading libraries such as pthread. Functions like pthread_create(), pthread_join(), and synchronization primitives like mutexes and condition variables are used to create, join, and synchronize threads.

3. What is thread synchronization in C?
   * Thread synchronization in C refers to the coordination and ordering of thread execution to prevent data races and ensure correct program behavior. Techniques like mutexes, condition variables, and semaphores are used to synchronize threads.

4. What are mutexes in C?
   * Mutexes (mutual exclusion) in C are synchronization primitives used to protect shared resources from simultaneous access by multiple threads. They ensure that only one thread can acquire the mutex at a time, preventing data races.

5. What is the purpose of the pthread_join() function in C?
   * The pthread_join() function in C is used to wait for a thread to complete its execution before continuing the execution of the calling thread. It allows for synchronization between threads and ensures that the result of the joined thread is available.
</details>
&nbsp;
<details>
<summary><strong style="color: #122C8C;">15. String Manipulation</strong></summary>

1. How do you find the length of a string in C?
   * To find the length of a string in C, you can use the strlen() function from the <string.h> library. It returns the number of characters in a string, excluding the null-terminating character.

2. How do you concatenate strings in C?
   * To concatenate strings in C, you can use the strcat() function from the <string.h> library. It appends the content of one string to the end of another.

3. How do you compare strings in C?
   * To compare strings in C, you can use the strcmp() function from the <string.h> library. It returns a value less than, equal to, or greater than zero based on the lexicographic comparison of the strings.

4. How do you copy strings in C?
   * To copy strings in C, you can use the strcpy() function from the <string.h> library. It copies the contents of one string to another.

5. What is the purpose of the strtok() function in C?
   * The strtok() function in C is used to tokenize a string into smaller tokens based on a specified delimiter. It is typically used in parsing tasks.
</details>
&nbsp;
<details>
<summary><strong style="color: #122C8C;">16. Command-Line Arguments</strong></summary>

1. How do you pass command-line arguments to a C program?
   * Command-line arguments can be passed to a C program by specifying them after the program name when running it from the command line. They are accessible to the program via the argc and argv parameters of the main() function.

2. What is the argc parameter in the main() function?
   * The argc parameter in the main() function represents the number of command-line arguments passed to the program, including the program name itself.

3. What is the argv parameter in the main() function?
   * The argv parameter in the main() function is an array of strings that represents the command-line arguments passed to the program. argv[0] is the program name, and subsequent elements are the arguments.

4. How do you convert command-line arguments to integers in C?
   * Command-line arguments can be converted to integers in C using functions like atoi() or strtol() from the <stdlib.h> library. These functions convert a string representation of a number to its integer value.

5. How do you handle missing or invalid command-line arguments in C?
   * Missing or invalid command-line arguments can be handled by checking the argc value and validating the argv contents within the program. Error messages can be displayed, and default values can be used as fallbacks.
</details>
&nbsp;
<details>
<summary><strong style="color: #122C8C;">17. File Handling</strong></summary>

1. How do you open a file in C?
   * To open a file in C, you use the fopen() function from the <stdio.h> library. It takes the file path and the mode as parameters and returns a file pointer.

2. What are the different file modes in C?
   * The different file modes in C are:
      * "r": Read mode (opens a file for reading)
      * "w": Write mode (opens a file for writing, overwrites existing file or creates a new file)
      * "a": Append mode (opens a file for writing, appends data to an existing file or creates a new file)
      * "r+": Read and write mode (opens a file for both reading and writing)
      * "w+": Read and write mode (opens a file for reading and writing, overwrites existing file or creates a new file)
      * "a+": Read and append mode (opens a file for reading and writing, appends data to an existing file or creates a new file)

3. How do you read from a file in C?
   * To read from a file in C, you use the fscanf() or fgets() function. fscanf() reads formatted data, while fgets() reads a line of text.

4. How do you write to a file in C?
   * To write to a file in C, you use the fprintf() or fputs() function. fprintf() writes formatted data, while fputs() writes a string.

5. How do you close a file in C?
   * To close a file in C, you use the fclose() function. It takes a file pointer as a parameter and releases the associated resources.
</details>
&nbsp;
<details>
<summary><strong style="color: #122C8C;">18. Memory Management</strong></summary>

1. What is dynamic memory allocation in C?
   * Dynamic memory allocation in C refers to the process of allocating memory at runtime. It allows you to allocate memory dynamically based on program needs.

2. How do you allocate memory dynamically in C?
   * To allocate memory dynamically in C, you use the malloc(), calloc(), or realloc() function from the <stdlib.h> library. malloc() allocates a block of memory, calloc() allocates and initializes a block of memory, and realloc() reallocates memory.

3. How do you deallocate memory in C?
   * To deallocate memory in C, you use the free() function. It takes a pointer to the memory block previously allocated with malloc(), calloc(), or realloc() and releases the memory.

4. What is memory leakage in C?
   * Memory leakage in C refers to the situation where allocated memory is not deallocated, resulting in memory loss. It can lead to inefficient memory usage and program crashes.

5. How do you prevent memory leakage in C?
   * To prevent memory leakage in C, you should ensure that all allocated memory is properly deallocated using the free() function when it is no longer needed.
</details>
&nbsp;
<details>
<summary><strong style="color: #122C8C;">19. Preprocessor Directives</strong></summary>

1. What are preprocessor directives in C?
   * Preprocessor directives in C are instructions that are processed by the preprocessor before the compilation of the program. They are identified by a # symbol.

2. What is the #include directive used for?
   * The #include directive is used to include the contents of another file in the current file. It is commonly used to include header files that define functions, constants, and data types.

3. What is the purpose of the #define directive in C?
   * The #define directive in C is used to define a macro. It allows you to create symbolic constants or create short, reusable code snippets.

4. How do you use conditional compilation in C?
   * Conditional compilation in C is performed using the #if, #else, and #endif directives. It allows you to include or exclude blocks of code based on compile-time conditions.

5. What is the purpose of the #ifdef directive in C?
   * The #ifdef directive in C is used to check if a macro or symbol is defined. It allows you to conditionally include or exclude code based on the existence of the specified macro or symbol.
</details>
&nbsp;
<details>
<summary><strong style="color: #122C8C;">20. Miscellaneous</strong></summary>

1. What is a function pointer in C?
   - A function pointer in C is a variable that can store the address of a function. It allows you to call a function indirectly through the pointer.

2. How do you declare a function pointer in C?
   - To declare a function pointer in C, you specify the return type of the function and the parameter types. For example, int (*ptr)(int, int); declares a function pointer named ptr that points to a function with two int parameters and returns an int.

3. What is the purpose of the typedef keyword in C?
   - The typedef keyword in C is used to create a new name (alias) for an existing data type. It allows you to define more readable and meaningful names for complex data types or function pointers.

4. What are the bitwise shift operators used for in C?
   - The bitwise shift operators (<< and >>) in C are used to shift the bits of an integer to the left or right, respectively. They are often used in bitwise operations and low-level bit manipulation.

5. What is the purpose of the const keyword in C?
   - The const keyword in C is used to declare constants. It indicates that a variable's value cannot be modified once it is assigned. It provides immutability and enables the compiler to perform optimizations.

6. What is the difference between NULL and nullptr in C?
   - In C, NULL is a macro defined as an implementation-defined null pointer constant, usually 0 or ((void *)0). In C++, nullptr is a keyword that represents a null pointer value. It is recommended to use nullptr in C++ as it provides better type safety.

</details>

