package main_1;

import java.io.*;

import java.io.Serializable;

public class Main_1
{
    public static void main(String[] args)
    {
        // creating the library system
        Library_1 my_library = new Library_1();

        // creating sample data
        Author king = new Author("Steven King");
        Book book1 = new Book("The Shining", king);
        Book book2 = new Book("It", king);

        Reader sofiia = new Reader("Sofia", 101);

        // adding data to the library
        my_library.addBook(book1);
        my_library.addBook(book2);
        my_library.addReader(sofiia);

        // giving a book to a reader
        my_library.takeBook(book1, sofiia);

        System.out.println("Before serialization:");
        System.out.println(my_library);

        String filename = "library_1.dat";

        // serialization process: saving the library object to a file
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename)))
        {
            out.writeObject(my_library);
            System.out.println("\n[System successfully saved to file: " + filename + "]");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        // deserialization process: restoring the library object from the file
        Library_1 restored_library = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename)))
        {
            restored_library = (Library_1) in.readObject();
            System.out.println("[System successfully restored from file]\n");
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        System.out.println("After deserialization:");
        if (restored_library != null)
        {
            System.out.println(restored_library);
        }
    }
}

// all classes implement Serializable interface to allow byte-stream conversion
class Author implements Serializable
{
    private String name;

    public Author() {}

    public Author(String name) { this.name = name; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    @Override
    public String toString() { return "Author: " + name; }
}

// Клас Книга
class Book implements Serializable
{
    private String title;
    private Author author;

    public Book() {}

    public Book(String title, Author author)
    {
        this.title = title;
        this.author = author;
    }

    public String getTitle() { return title; }

    public Author getAuthor() { return author; }

    @Override
    public String toString() { return "Book '" + title + "' (" + author.getName() + ")"; }
}

// Клас Читач
class Reader implements Serializable
{
    private String name;
    private int card_number;

    public Reader() {}

    public Reader(String name, int card_number)
    {
        this.name = name;
        this.card_number = card_number;
    }

    public String getName() { return name; }

    @Override
    public String toString() { return "Reader: " + name + " [№" + card_number + "]"; }
}

class Loan implements Serializable
{
    private Book book;
    private Reader reader;

    public Loan(Book book, Reader reader)
    {
        this.book = book;
        this.reader = reader;
    }

    @Override
    public String toString() { return reader.getName() + " borrowed " + book.getTitle(); }
}