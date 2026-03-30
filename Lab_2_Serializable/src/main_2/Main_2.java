package main_2;

import java.io.*;

public class Main_2
{
    public static void main(String[] args)
    {
        // creating the Library_2 system for the second task
        Library_2 my_Library_2 = new Library_2();

        // these objects belong to classes without 'Serializable' interface
        Author king = new Author("Stephen King");
        Book book1 = new Book("The Shining", king);
        Book book2 = new Book("It", king);

        Reader sofiia = new Reader("Sofia", 101);

        // adding data to the Library_2
        my_Library_2.addBook(book1);
        my_Library_2.addBook(book2);
        my_Library_2.addReader(sofiia);

        // giving a book to a reader
        my_Library_2.takeBook(book1, sofiia);

        System.out.println("Before serialization");
        System.out.println(my_Library_2);

        String filename = "Library_2.dat";

        // saving the system using our custom writeObject logic
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename)))
        {
            out.writeObject(my_Library_2);
            System.out.println("\n[Saved using manual writeObject method]");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        // restoring the system using our custom readObject logic
        Library_2 restored_Library_2 = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename)))
        {
            restored_Library_2 = (Library_2) in.readObject();
            System.out.println("[Restored using manual readObject method]\n");
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        System.out.println("After deserialization");
        if (restored_Library_2 != null)
        {
            System.out.println(restored_Library_2);
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
    private final int card_number;

    public Reader(String name, int card_number)
    {
        this.name = name;
        this.card_number = card_number;
    }

    public String getName() { return name; }

    public int getId() { return card_number; }

    @Override
    public String toString() { return "Reader: " + name + " [№" + card_number + "]"; }
}

class Loan implements Serializable
{
    private String book_title;
    private String reader_name;

    public Loan(Book book, Reader reader)
    {
        this.book_title = book.getTitle();
        this.reader_name = reader.getName();
    }

    @Override
    public String toString() { return reader_name + " borrowed " + book_title; }
}