import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 * Create a GUI for a Library Management System
 * The LMS System asks users to input title, author, and isbn information which can then be used tto add a book to the collection,
 * check in/out a book, or remove a certain book based off of its title or isbn.
 * @author McKenna Pasquale
 */


public class LibraryGuiApplication extends JFrame {
    private JTextField titleField, authorField, isbnField;
    private JTextArea bookListArea;
    private ArrayList<Book> books = new ArrayList<>();



    public LibraryGuiApplication() {
        /*
        CREATE GUI

         * This method creates the overall look and outline of the GUI using a grid layout,
         * Buttons needed are: AddBook, CheckInBook, CheckOutBook, RemoveBookByTitle, RemoveBookByBarcode, and Cancel
         */
        setTitle("Library Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        inputPanel.add(new JLabel("Title:"));
        titleField = new JTextField();
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Author:"));
        authorField = new JTextField();
        inputPanel.add(authorField);
        inputPanel.add(new JLabel("ISBN:"));
        isbnField = new JTextField();
        inputPanel.add(isbnField);

        /*
        BUTTONS!
         */
        //add books to collection button
        JButton addButton = new JButton("Add Book");
        addButton.setMargin(new Insets(5,10,5,10));
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });
        inputPanel.add(addButton);

        //check out books button
        JButton CheckOutButton = new JButton("Check Out Book");
        CheckOutButton.setMargin(new Insets(5,10,5,10));
        CheckOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CheckOutBook();
            }
        });
        inputPanel.add(CheckOutButton);

        //check in books button
        JButton CheckInButton = new JButton("Check In Book");
        CheckInButton.setMargin(new Insets(5,10,5,10));
        CheckInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CheckInBook();
            }
        });
        inputPanel.add(CheckInButton);

        //remove books by barcode button
        JButton RemoveBarcodeButton = new JButton("Remove Book by Barcode");
        RemoveBarcodeButton.setMargin(new Insets(5,10,5,10));
        RemoveBarcodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeByBarcode();
            }
        });
        inputPanel.add(RemoveBarcodeButton);


        //remove books by title button
        JButton RemoveTitleButton = new JButton("Remove Book by Title");
        RemoveTitleButton.setMargin(new Insets(5,10,5,10));
        RemoveTitleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeByTitle();
            }
        });
        inputPanel.add(RemoveTitleButton);

        //cancel and quit the program button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setMargin(new Insets(5,10,5,10));
        cancelButton.addActionListener(new ActionListener() {
        @Override

        public void actionPerformed(ActionEvent e){
            dispose();
        }
    });
        inputPanel.add(cancelButton);

        /*
        LAYOUT OF GUI
         */
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        bookListArea = new JTextArea(); //display collection
        mainPanel.add(new JScrollPane(bookListArea), BorderLayout.CENTER);
        add(mainPanel);
        setVisible(true);
    }


    private void removeByTitle() {
        /*
         * Allows user to enter title, searches for a book matching that title,
         *  removes it if found and outputs to the user if it was deleted,
         *  or if it was not found.
         *
         * Boolean originally set to false and returns true if the title is found.
         * @return if the book is found, the title from user input has been deleted successfully.
         * @return also refreshes the list of books in the collection and will not include the title deleted.
         *
         */




        String searchTerm = JOptionPane.showInputDialog(this, "Enter Title: ");
        boolean found = false;
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(searchTerm)) {
                found = true;
                books.remove(book);
                JOptionPane.showMessageDialog(this, "Book has been deleted successfully!");
                refreshBookList();
                break;
            }
        }
        if (!found){
            JOptionPane.showMessageDialog(this, "Book not found, please try again.");
        }
    }



    private void removeByBarcode() {
        /*
          Allows user to enter the isbn, searches for a book matching that isbn,
           removes it if found and outputs to the user if it was deleted,
          or if it was not found.
          Boolean originally set to false and returns true if the title is found.
          @return if the book is found, the isbn from user input has been deleted successfully.
         * @return also refreshes the list of books in the collection and will not include the isbn recently deleted.

         */
        String searchTerm = JOptionPane.showInputDialog(this, "Enter ISBN:");
        boolean found = false;
        for (Book book : books) {
            if (book.getIsbn().equals(searchTerm)) {
                found = true;
                books.remove(book);
                JOptionPane.showMessageDialog(this, "Book has been deleted successfully!");
                refreshBookList();
                break;
            }
        }
        if (!found){
            JOptionPane.showMessageDialog(this, "Book not found, please try again.");
        }
    }



    private void addBook() {
        /*
            info is collected from user and displays the book information in the larger text area.
                  Adds a book to the list of books.
           variables of a book needed: title, author, and isbn.
           assigning variables to a book value
          @return new book added by user to the list of books, updates the list each time book is added.

         */
        String title = titleField.getText();
        String author = authorField.getText();
        String isbn = isbnField.getText();

        Book newBook = new Book(title, author, isbn);
        books.add(newBook);

        refreshBookList();
    }

    private void CheckOutBook() {
        /*
          User enters a title or isbn to search through the list of books,
          and mark the match (if found) as checked out;
          displays message to the user saying it was checked out or if it is unavailable at this time.

          iterates through list of books to find the book inputted by user to check out.

          @return if the book was found, checked out successfully, refreshes the list of available books in the collection
         * @return if the book was not found, prompts user to search a different book.

         */
        String searchTerm = JOptionPane.showInputDialog(this, "Enter title or ISBN:");
        boolean found = false;
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(searchTerm) || book.getIsbn().equals(searchTerm)) {
                found = true;
                book.setCheckedOut(true); //set book to be checked out.
                JOptionPane.showMessageDialog(this, "Book checked out successfully!");
                refreshBookList();
                break;
            }
            else{
                JOptionPane.showMessageDialog(this, "Book is unavailable to be checked out at this time.");
            }
        }
        if (!found){
            JOptionPane.showMessageDialog(this, "Book not found, please try again.");
        }

    }


    private void CheckInBook() {
        /*
          User enters a title or isbn to search through the list of books,
          and mark the match (if found) as checked in;
          displays message to the user saying it was checked in or if they need to see a librarian.
          iterates through list of books to find the book inputted by user to check in.

          @return if the book was found, checked in successfully, refreshes the list of available books in the collection including the book just added back
         @return if the book was not found, prompts user to search a different book or see a librarian.

         */
        String searchTerm = JOptionPane.showInputDialog(this, "Enter title or ISBN:");
        boolean found = false;
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(searchTerm) || book.getIsbn().equals(searchTerm)) {
                found = true;
                book.setCheckedOut(true);
                JOptionPane.showMessageDialog(this, "Book checked in successfully!");
                refreshBookList();
                break;
            }
            else{
                JOptionPane.showMessageDialog(this, "Book is unavailable to be checked in at this time, please see Librarian!");
            }
        }
        if (!found){
            JOptionPane.showMessageDialog(this, "Book not found, please try again.");
        }

    }


    private void refreshBookList() {
        /*
          iterates over each book in the list of books and displays the most recent collection of books

          @return nothing, but called on at the end of all methods.
         */
        StringBuilder sb = new StringBuilder();
        for (Book book : books) {
            sb.append(book).append("\n");
        }
        bookListArea.setText(sb.toString());
    }


    public static void main(String[] args) {
        /*
          main method which makes use of the addBook, removeBookByTitle/Barcode, CHeckInBook, CheckOutBook methods.
         */
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LibraryGuiApplication();
            }
        });
    }
}


/*
Creates, retrieves, and modifies book(s) elements. Getters & Setters.
 */


class Book {
    private String title;
    private String author;
    private String isbn;
    private boolean checkedOut;

    /**
     *
     * @param title
     * @param author
     * @param isbn
     */
    public Book(String title, String author, String isbn) {

        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }




    public String getTitle() {
        return title;
    }

    /**
     *
      * @return
     */
    public String getAuthor() {
        return author;
    }

    /**
     *
     * @return
     */
    public String getIsbn() {
        return isbn;
    }


    /**
     *
     * @return
     */
    public boolean isCheckedOut() {
        return checkedOut;   }

    /**
     *
     * @param checkedOut
     */
    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", ISBN: " + isbn;
    }
}




