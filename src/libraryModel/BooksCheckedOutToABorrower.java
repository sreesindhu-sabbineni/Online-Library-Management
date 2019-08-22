package libraryModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BooksCheckedOutToABorrower {
	public void booksCheckedout() throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Scanner sc = new Scanner(System.in);
		Connection connect = SqlAccess.getConnection();
		System.out.println("Enter the card No of the borrower");
		long cardNo = sc.nextLong();
		sc.nextLine();
		while(true) {
			String verifyCardNo = "Select Name from Borrowers where CardNo = ?";
			preparedStatement = connect.prepareStatement(verifyCardNo);
			preparedStatement.setLong(1, cardNo);
			resultSet = preparedStatement.executeQuery();
			if(!resultSet.isBeforeFirst()) {
				System.out.println("Invalid CardNo!!\nEnter the correct one!!");
			}
			else {
				break;
			}
			cardNo = sc.nextLong();
			sc.nextLine();
		}
			String booksCheckedOut = "select Books.BookId, Books.AuthorName, Books.Title, Books.Name, Book_Loans.CardNo from Books join Book_Loans on (Books.BookId = Book_Loans.BookId)"
					+ "where Book_Loans.CardNo = ?";
			preparedStatement = connect.prepareStatement(booksCheckedOut);
			preparedStatement.setLong(1, cardNo);
			resultSet = preparedStatement.executeQuery();
			if(!resultSet.isBeforeFirst()) {
				System.out.println("No books checked out by this borrower");
			}
			else {
				while(resultSet.next()) {
					System.out.println("BookId:"+resultSet.getLong(1)+"\nAuthorName:"+resultSet.getString(2)+"\nTitle:"
							+resultSet.getString(3)+"\nPublisherName:"+resultSet.getString(4)+"\nBorrowerCardNo:"+resultSet.getLong(5)+"\n");
				}
			}
	}
}

