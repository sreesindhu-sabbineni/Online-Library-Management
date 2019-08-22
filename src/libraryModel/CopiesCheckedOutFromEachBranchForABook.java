package libraryModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CopiesCheckedOutFromEachBranchForABook {
	public void numberOfCheckedOutCopies() throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Scanner sc = new Scanner(System.in);
		Connection connect = SqlAccess.getConnection();
		System.out.println("Enter the BookId to display the number of copies checked out for each branch");
		long bookId = sc.nextLong();
		sc.nextLine();
		while(true) {
			String verifyBookId = "Select Title from Books where BookId = ?";
			preparedStatement = connect.prepareStatement(verifyBookId);
			preparedStatement.setLong(1, bookId);
			resultSet = preparedStatement.executeQuery();
			if(!resultSet.isBeforeFirst()) {
				System.out.println("Invalid BookId!!\nEnter the correct one!!");
			}
			else {
				break;
			}
			bookId = sc.nextLong();
			sc.nextLine();
		}
		String query = "select Books.BookId, Books.Title, Book_Loans.BranchId, count(Book_Loans.BookLoanId) as NoOfCopies from (Book_Loans join Books "
				+ "on (Book_Loans.BookId = Books.BookId)) where Books.BookId = ? group by (Book_Loans.BranchId)";
		preparedStatement = connect.prepareStatement(query);
		preparedStatement.setLong(1, bookId);
		resultSet = preparedStatement.executeQuery();
		while(resultSet.next()) {
			System.out.println("BookId:"+resultSet.getLong(1)+"\nTitle:"+resultSet.getString(2)+"\nBranchId:"
					+resultSet.getLong(3)+"\nNoOfCopies:"+resultSet.getInt(4)+"\n");
		}
	}
}

