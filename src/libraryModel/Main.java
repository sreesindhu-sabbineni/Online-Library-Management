package libraryModel;

import java.awt.print.Book;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) throws SQLException, InterruptedException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Choose the action you would like to perform by entering the number given in () "
				+ "against each option:\n1.Add a new book to the system(1)\n"
				+ "2.Check out a copy of a book(2)\n3.Return a book(3)\n4.Display the books at the library branch"
				+ "(4)\n5.Display the books checked out to a borrower(5)\n6.Copies of a book checked out from "
				+ "each branch(6)");
		
		int option = sc.nextInt();
		sc.nextLine();
		switch(option) {
		case 1:{
			AddANewBook add = new AddANewBook();
			add.add();
			break;
		}
		case 2:{
			CheckOutACopyOfBook checkout = new CheckOutACopyOfBook();
			checkout.checkOut();
			break;
		}
		case 3:{
			ReturnABook returnBook = new ReturnABook();
			returnBook.returnBook();
			break;
		}
		case 4:{
			DisplayBooksAtABranch displayBooks = new DisplayBooksAtABranch();
			displayBooks.displayBooks();
			break;
		}
		case 5:{
			BooksCheckedOutToABorrower booksCheckedOut = new BooksCheckedOutToABorrower();
			booksCheckedOut.booksCheckedout();
			break;
		}
		case 6:{
			CopiesCheckedOutFromEachBranchForABook copies = new CopiesCheckedOutFromEachBranchForABook();
			copies.numberOfCheckedOutCopies();
			break;
		}
		default:{
			System.out.println("Invalid option chosen. Enter the correct one");
			break;
		}
		}
		//sc.close();
	}

}

