import java.io.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        // creating the library system
        Library myLibrary = new Library();

        // creating sample data
        Author king = new Author("Steven King");
        Book book1 = new Book("The Shining", king);
        Book book2 = new Book("It", king);

        Reader sofiia = new Reader("Sofia", 101);

        // adding data to the library
        myLibrary.addBook(book1);
        myLibrary.addBook(book2);
        myLibrary.addReader(sofiia);

        // giving a book to a reader
        myLibrary.takeBook(book1, sofiia);

        System.out.println("Before serialization:");
        System.out.println(myLibrary);

        String filename = "library.dat";

        // serialization process: saving the library object to a file
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename)))
        {
            out.writeObject(myLibrary);
            System.out.println("\n[System successfully saved to file: " + filename + "]");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        // deserialization process: restoring the library object from the file
        Library restoredLibrary = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename)))
        {
            restoredLibrary = (Library) in.readObject();
            System.out.println("[System successfully restored from file]\n");
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        System.out.println("After deserialization:");
        if (restoredLibrary != null)
        {
            System.out.println(restoredLibrary);
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
    private int cardNumber;

    public Reader() {}

    public Reader(String name, int cardNumber)
    {
        this.name = name;
        this.cardNumber = cardNumber;
    }

    public String getName() { return name; }

    @Override
    public String toString() { return "Reader: " + name + " [№" + cardNumber + "]"; }
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