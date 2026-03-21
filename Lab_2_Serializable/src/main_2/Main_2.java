package main_2;

import java.io.*;

public class Main_2
{
    static void main()
    {
        // creating the library system for the second task
        Library myLibrary = new Library();

        // these objects belong to classes without 'Serializable' interface
        Author king = new Author("Stephen King");
        Book book1 = new Book("The Shining", king);
        Book book2 = new Book("It", king);

        Reader sofiia = new Reader("Sofia", 101);

        // adding data to the library
        myLibrary.addBook(book1);
        myLibrary.addBook(book2);
        myLibrary.addReader(sofiia);

        // giving a book to a reader
        myLibrary.takeBook(book1, sofiia);

        System.out.println("Before serialization");
        System.out.println(myLibrary);

        String filename = "library_2.dat";

        // saving the system using our custom writeObject logic
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename)))
        {
            out.writeObject(myLibrary);
            System.out.println("\n[Saved using manual writeObject method]");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        // restoring the system using our custom readObject logic
        Library restoredLibrary = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename)))
        {
            restoredLibrary = (Library) in.readObject();
            System.out.println("[Restored using manual readObject method]\n");
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        System.out.println("After serialization");
        if (restoredLibrary != null)
        {
            System.out.println(restoredLibrary);
        }
    }
}

// not serializable
class Author
{
    private final String name;

    public Author(String name) { this.name = name; }

    public String getName() { return name; }

    @Override
    public String toString() { return "Author: " + name; }
}

// not serializable
class Book
{
    private final String title;
    private final Author author;

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

// not serializable
class Reader
{
    private final String name;
    private final int cardNumber;

    public Reader(String name, int cardNumber)
    {
        this.name = name;
        this.cardNumber = cardNumber;
    }

    public String getName() { return name; }

    public int getId() { return cardNumber; }

    @Override
    public String toString() { return "Reader: " + name + " [№" + cardNumber + "]"; }
}

class Loan implements Serializable
{
    private String bookTitle;
    private String readerName;

    public Loan(Book book, Reader reader)
    {
        this.bookTitle = book.getTitle();
        this.readerName = reader.getName();
    }

    @Override
    public String toString() { return readerName + " borrowed " + bookTitle; }
}