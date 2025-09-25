import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

class Book {
    private String title;
    private String author;
    private boolean isIssued;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isIssued = false;
    }

    public String getTitle() {
        return title;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void issue() {
        isIssued = true;
    }

    public void returnBook() {
        isIssued = false;
    }

    @Override
    public String toString() {
        return title + " by " + author + " (" + (isIssued ? "Issued" : "Available") + ")";
    }
}

class User {
    private String name;
    private ArrayList<Book> borrowedBooks;

    public User(String name) {
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void borrowBook(Book book) {
        if (!book.isIssued()) {
            book.issue();
            borrowedBooks.add(book);
            System.out.println(name + " borrowed '" + book.getTitle() + "'.");
        } else {
            System.out.println("Sorry, '" + book.getTitle() + "' is already issued.");
        }
    }

    public void returnBook(Book book) {
        if (borrowedBooks.contains(book)) {
            book.returnBook();
            borrowedBooks.remove(book);
            System.out.println(name + " returned '" + book.getTitle() + "'.");
        } else {
            System.out.println(name + " does not have '" + book.getTitle() + "'.");
        }
    }

    public void showBorrowedBooks() {
        System.out.println("\nBooks borrowed by " + name + ":");
        if (borrowedBooks.isEmpty()) {
            System.out.println(" - No books borrowed.");
        } else {
            for (Book b : borrowedBooks) {
                System.out.println(" - " + b);
            }
        }
    }
}

class Library {
    private ArrayList<Book> books;
    private ArrayList<User> users;

    public Library() {
        books = new ArrayList<>();
        users = new ArrayList<>();
    }
    public void addBook(String title, String author) {
        books.add(new Book(title, author));
        System.out.println("Book '" + title + "' added successfully!");
    }

    public void showAllBooks() {
        System.out.println("\nBooks in Library:");
        if (books.isEmpty()) {
            System.out.println(" - No books available.");
        } else {
            for (Book book : books) {
                System.out.println(" - " + book);
            }
        }
    }

    public void addUser(String name) {
        users.add(new User(name));
        System.out.println("User '" + name + "' added successfully!");
    }
    public void showAllUsers() {
        System.out.println("\nRegistered Users:");
        if (users.isEmpty()) {
            System.out.println(" - No users found.");
        } else {
            for (User u : users) {
                System.out.println(" - " + u.getName());
            }
        }
    }

    private Book findBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    private User findUser(String name) {
        for (User u : users) {
            if (u.getName().equalsIgnoreCase(name)) {
                return u;
            }
        }
        return null;
    }

    // Issue (borrow) a book
    public void issueBook(String username, String bookTitle) {
        User user = findUser(username);
        Book book = findBook(bookTitle);

        if (user == null) {
            System.out.println("User not found!");
            return;
        }
        if (book == null) {
            System.out.println("Book not found!");
            return;
        }
        user.borrowBook(book);
    }

    // Return a book
    public void returnBook(String username, String bookTitle) {
        User user = findUser(username);
        Book book = findBook(bookTitle);

        if (user == null) {
            System.out.println("User not found!");
            return;
        }
        if (book == null) {
            System.out.println("Book not found!");
            return;
        }
        user.returnBook(book);
    }
}

public class LibraryManagement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library l1 = new Library();

        System.out.println("Welcome to the Mini Library Management System");
        System.out.println("----------------------------------------------");
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();

        if (choice == 1) {
            System.out.print("Please enter your username: ");
            String username = sc.next();
            System.out.print("Please enter your password: ");
            String password = sc.next();

            if (Objects.equals(username, "admin") && Objects.equals(password, "1234")) {
                System.out.println("Welcome, " + username + "!");
                int ch;
                do {
                    System.out.println("\n--- Library Menu ---");
                    System.out.println("1. Add new book");
                    System.out.println("2. Show all books");
                    System.out.println("3. Add new user");
                    System.out.println("4. Show all users");
                    System.out.println("5. Issue book");
                    System.out.println("6. Return book");
                    System.out.println("7. Exit");
                    System.out.print("Enter choice: ");
                    ch = sc.nextInt();

                    switch (ch) {
                        case 1:
                            System.out.print("Enter book title: ");
                            String title = sc.next();
                            System.out.print("Enter author name: ");
                            String author = sc.next();
                            l1.addBook(title, author);
                            break;
                        case 2:
                            l1.showAllBooks();
                            break;
                        case 3:
                            System.out.print("Enter user name: ");
                            String uname = sc.next();
                            l1.addUser(uname);
                            break;
                        case 4:
                            l1.showAllUsers();
                            break;
                        case 5:
                            System.out.print("Enter user name: ");
                            String uName = sc.next();
                            System.out.print("Enter book title: ");
                            String bTitle = sc.next();
                            l1.issueBook(uName, bTitle);
                            break;
                        case 6:
                            System.out.print("Enter user name: ");
                            String rUser = sc.next();
                            System.out.print("Enter book title: ");
                            String rBook = sc.next();
                            l1.returnBook(rUser, rBook);
                            break;
                        case 7:
                            System.out.println("Exiting... Have a nice day!");
                            break;
                        default:
                            System.out.println("Invalid choice");
                    }
                } while (ch != 7);
            } else {
                System.out.println("Invalid username or password!");
            }
        } else if (choice == 2) {
            System.out.println("Have a nice day!");
        }
    }
}
