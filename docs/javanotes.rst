++++++++++++++++++++++++++++++++++++
Learning Java by Porting Aristoxenus
++++++++++++++++++++++++++++++++++++

By translating the functions in my Python 'Aristoxenus' program, I will teach myself Java language.

This file wil serve as a little brainbin where I keep some notes about the problems 
I encountered and the solutions I found to them.



Introduction
============

In contrast to Python, Java lacks functions of any sort. The main thread of any Java program must be a class, 
and each Java file represents 1 and only 1 class (although a class may have other classes nested within it in
the same file).

Java also lacks operator overloading of any sort. This means that certain semantic shortcuts that are idiomatic in 
Python are unavailable to us in Java. Instead, we must call methods (often static methods) borrowed from classes,
which often must be imported. A few examples:


.. code-block:: python
    :caption: We often use list comprehensions instead of ``map``, ``filter``, and ``reduce``.

    ACCIDENTAL_SYMBOLS: list[str] = [SHARP_SYMBOL, FLAT_SYMBOL]

    SHARPS: tuple[str, ...] = tuple(note + SHARP_SYMBOL for note in NATURALS
                       if note not in HALFSTEPS)

    BINOMIALS: tuple[str, ...] = tuple(sharp + BINOMIAL_DIVIDER_SYMBOL + flat
                          for flat in FLATS for sharp in SHARPS
                          if FLATS.index(flat) == SHARPS.index(sharp))


.. code-block:: java
    :caption: We call the ``of`` method on the imported ``List`` class to create an immutable list. The ``List`` class is really just an interface, so it is the underlying type of array that determines the list's mutability.
    
    public static final List<String> ACCIDENTAL_SYMBOLS = List.of(SHARP_SYMBOL, FLAT_SYMBOL);


.. code-block:: java
    :caption: The stream() method of the list ``NATURALS`` returns a Stream object, which supplies ``map`` and ``filter``. Then, the stream's ``collect`` method supplies the ``toList`` static method from the ``Collectors`` class to organize the stream into a ``List`` object.
    :linenos: 
    :emphasize-lines: 1, 9

    public static final List<String> SHARPS = NATURALS.stream()
        .filter(i -> ! HALFSTEPS.containsKey(i))
        .map(i -> i + SHARP_SYMBOL)
        .collect(Collectors.toList())
    ;

    public static final List<String> BINOMIALS = SHARPS.stream()
        .map(i -> i + BINOMIAL_DIVIDER_SYMBOL + FLATS.get(SHARPS.indexOf(i)))
        .collect(Collectors.toList())
    ;


Notice that the way we access an item in a list in Python (``list[index]``) does not work in Java because the ``[]`` operator cannot be overloaded. Instead we call the list's ``get`` method, which accepts an index as parameter.


Lambda Expressions
==================

Java's lambda syntax is very succinct. You can see a few examples in the code above, where the ``->`` operator separates the lambda parameter(s) from the lambda expression.
Unlike Python, lambda expressions can contain conditional pathways.


.. code-block:: java
   :caption: Lambda can have an empty parameter:

   () -> System.out.println("Hello")


.. code-block:: java
    :caption: Lambda can have multiple parameters:

    () -> System.out.println("Hello")


.. code-block:: java
    :caption: Lambda can have a code block as the expression

    (int i) -> {
    System.out.println("Hello");
    return i;
    }


.. code-block:: java
    :caption: Lambda can have a condition in a code block:

    (int i) -> {
    if (i <= 7) {
        System.out.println("Hello");
    }
    else {
    return i;
    }


Ternary Operator
================

A lambda could also be made more succinct by using the ternary assignment operators ``?`` (if/true) and ``:`` (elsefalse), which are, however, not exclusive to lambda expressions.

.. code-block:: java
    :caption: Ternary assignment operators make succinct conditions

    int y = (x == 1) ? 61: 90; 


This code means "let y be equal to 61 if x==1, else let y be equal to 90". In Python, this looks like:


.. code-block:: python
    :caption: Ternary assignment operator

    y: int = 61 if x ==1 else 90