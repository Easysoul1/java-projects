import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Custom exception for when a book is not available for borrowing.

class BookNotAvailableException extends Exception {
    public BookNotAvailableException(String message) {
        super(message);
    }
}

/**
 * Represents a single book in the library.
 * Encapsulates book data and its availability status.
 */
class Book {
    // --- Encapsulated Fields ---
    private String title;
    private String author;
    private boolean isAvailable;

    // --- Constructor ---
    public Book(String title, String author) {
        if (title == null || title.trim().isEmpty() || author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Book title and author cannot be empty.");
        }
        this.title = title;
        this.author = author;
        this.isAvailable = true; // A new book is always available by default.
    }

    // --- Getters ---
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    // --- State-changing Methods ---

    /**
     * Marks the book as borrowed (not available).
     */
    public void borrowBook() {
        this.isAvailable = false;
    }

    /**
     * Marks the book as returned (available).
     */
    public void returnBook() {
        this.isAvailable = true;
    }

    @Override
    public String toString() {
        return "'" + title + "' by " + author + " (Available: " + isAvailable + ")";
    }
}

/**
 * Represents a library member.
 * Manages the list of books borrowed by this member.
 */
class Member {
    // --- Encapsulated Fields ---
    private String name;
    private String memberId;
    private List<Book> borrowedBooks; // Association: A Member has a list of borrowed Books.

    // --- Constructor ---
    public Member(String name, String memberId) {
        this.name = name;
        this.memberId = memberId;
        this.borrowedBooks = new ArrayList<>(); // Initialize the collection.
    }

    // --- Getters ---
    public String getName() {
        return name;
    }

    public String getMemberId() {
        return memberId;
    }

    // --- Core Logic ---

    /**
     * Adds a book to the member's list of borrowed books.
     * @param book The book to be borrowed.
     */
    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    /**
     * Removes a book from the member's list of borrowed books.
     * @param book The book to be returned.
     */
    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }

    /**
     * (Bonus) Displays all books currently borrowed by this member.
     */
    public void displayBorrowedBooks() {
        System.out.println("--- Books borrowed by " + name + " (" + memberId + ") ---");
        if (borrowedBooks.isEmpty()) {
            System.out.println("  None.");
        } else {
            for (Book book : borrowedBooks) {
                // We only need the title and author here, not the availability status.
                System.out.println("  - '" + book.getTitle() + "' by " + book.getAuthor());
            }
        }
        System.out.println("--------------------------------------");
    }

    @Override
    public String toString() {
        return "Member: " + name + " (ID: " + memberId + ")";
    }
}

/**
 * Manages the entire library's collection of books and members.
 * Handles the main logic for borrowing and returning books.
 */
class Library {
    // --- Encapsulated Fields ---
    private List<Book> books; // Association: A Library has a list of Books.
    private List<Member> members;

    // --- Constructor ---
    public Library() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
    }

    // --- Management Methods ---
    public void addBook(Book book) {
        this.books.add(book);
    }

    public void addMember(Member member) {
        this.members.add(member);
    }

    /**
     * Finds a book in the library's collection by its title.
     * @param title The title of the book to find.
     * @return An Optional containing the Book if found, otherwise an empty Optional.
     */
    private Optional<Book> findBookByTitle(String title) {
        return books.stream()
                    .filter(book -> book.getTitle().equalsIgnoreCase(title))
                    .findFirst();
    }

    /**
     * Orchestrates the process of a member borrowing a book.
     * @param member The member borrowing the book.
     * @param title The title of the book to borrow.
     * @throws BookNotAvailableException if the book is already borrowed or does not exist.
     */
    public void borrowBook(Member member, String title) throws BookNotAvailableException {
        // Find the book in the library's master list.
        Book bookToBorrow = findBookByTitle(title)
            .orElseThrow(() -> new BookNotAvailableException("Error: Book with title '" + title + "' not found in the library."));

        // Check for availability (Requirement #2: Exception Handling).
        if (!bookToBorrow.isAvailable()) {
            throw new BookNotAvailableException("Error: '" + title + "' is currently unavailable.");
        }

        // Update the state of both the Book and the Member.
        bookToBorrow.borrowBook(); // Mark book as unavailable.
        member.borrowBook(bookToBorrow); // Add book to member's list.
        System.out.println("Success: " + member.getName() + " has borrowed '" + title + "'.");
    }

    /**
     * Orchestrates the process of a member returning a book.
     * @param member The member returning the book.
     * @param title The title of the book to return.
     */
    public void returnBook(Member member, String title) {
        Optional<Book> bookToReturnOpt = findBookByTitle(title);

        if (bookToReturnOpt.isPresent()) {
            Book bookToReturn = bookToReturnOpt.get();
            bookToReturn.returnBook(); // Mark book as available.
            member.returnBook(bookToReturn); // Remove book from member's list.
            System.out.println("Success: " + member.getName() + " has returned '" + title + "'.");
        } else {
            System.out.println("Warning: Could not process return for a book titled '" + title + "' as it does not belong to this library.");
        }
    }
}

/**
 * Main class to demonstrate the Library Management System.
 */
public class LibraryManagementSystem {
    public static void main(String[] args) {
        // 1. Setup the Library
        Library myLibrary = new Library();
        myLibrary.addBook(new Book("The Hobbit", "J.R.R. Tolkien"));
        myLibrary.addBook(new Book("1984", "George Orwell"));
        myLibrary.addBook(new Book("To Kill a Mockingbird", "Harper Lee"));

        Member member1 = new Member("Alice", "M001");
        Member member2 = new Member("Bob", "M002");
        myLibrary.addMember(member1);
        myLibrary.addMember(member2);

        System.out.println("--- Library Initialized ---\n");

        // 2. Demonstrate successful borrowing and bonus feature
        try {
            myLibrary.borrowBook(member1, "The Hobbit");
        } catch (BookNotAvailableException e) {
            System.out.println(e.getMessage());
        }
        member1.displayBorrowedBooks(); // Bonus feature

        // 3. Demonstrate exception handling for an unavailable book
        System.out.println("\n--- Attempting to borrow an already borrowed book ---");
        try {
            myLibrary.borrowBook(member2, "The Hobbit");
        } catch (BookNotAvailableException e) {
            System.out.println(e.getMessage()); // Expected output
        }

        // 4. Demonstrate returning a book
        System.out.println("\n--- Returning a book ---");
        myLibrary.returnBook(member1, "The Hobbit");
        member1.displayBorrowedBooks();

        // 5. Demonstrate that the book is now available again
        System.out.println("\n--- Borrowing the newly returned book ---");
        try {
            myLibrary.borrowBook(member2, "The Hobbit");
        } catch (BookNotAvailableException e) {
            System.out.println(e.getMessage());
        }
        member2.displayBorrowedBooks();
    }
}