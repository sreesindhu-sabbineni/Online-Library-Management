package libraryModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ReturnABook {
	public void returnBook() throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Scanner sc = new Scanner(System.in);
		Connection connect = SqlAccess.getConnection();
		System.out.println("Enter the Book Loan ID");
		long bookLoanId = sc.nextLong();
		sc.nextLine();
		while(true) {
		//sc.nextLine();
		String verifyLoanId = "select * from Book_Loans where BookLoanId = ?";
		preparedStatement =  connect.prepareStatement(verifyLoanId);
		preparedStatement.setLong(1, bookLoanId);
		resultSet = preparedStatement.executeQuery();
		if(!resultSet.isBeforeFirst()) {
			System.out.println("Invalid Book Loan ID. Enter the correct one!!");	
		}
		else
			break;
		bookLoanId = sc.nextLong();
		}
		System.out.println("Enter the branch Id");
		long branchId = sc.nextLong();
		sc.nextLine();
		System.out.println("Enter the Book Id");
		long bookId = sc.nextLong();
		sc.nextLine();
		String deleteLoanRecord = "delete from Book_Loans where BookLoanId = ?";
		preparedStatement = connect.prepareStatement(deleteLoanRecord);
		preparedStatement.setLong(1, bookLoanId);
		preparedStatement.executeUpdate();
		String bookCopies = "select NoOfCopies from Book_Copies where BookId = ? and BranchId = ?";
        preparedStatement = connect.prepareStatement(bookCopies);
		preparedStatement.setLong(1, bookId);
		preparedStatement.setLong(2, branchId);
		resultSet = preparedStatement.executeQuery();
		int noOfCopies = 0;
		if(resultSet.next()) {
			 noOfCopies = resultSet.getInt(1);
		}
		String updateBookCopies = "update Book_Copies set NoOfCopies = ? where BookId = ? and BranchId = ?";
		preparedStatement = connect.prepareStatement(updateBookCopies);
		preparedStatement.setInt(1, noOfCopies+1);
		preparedStatement.setLong(2, bookId);
		preparedStatement.setLong(3, branchId);
		String bookCopyIds = "select NoOfCopies from Book_Copies";
        preparedStatement = connect.prepareStatement(bookCopyIds);
        resultSet = preparedStatement.executeQuery();
		List<Long> usedValues = new ArrayList<Long>(Arrays.asList(1L, 2L, 3L));
		while(resultSet.next()) {
			usedValues.add(resultSet.getLong(1));
		}
		Random rand = new Random();
        long bookCopyId = rand.nextLong();
        usedValues.add(bookCopyId);
        while(usedValues.contains(bookCopyId)) {
        	bookCopyId = rand.nextLong();
        }
		String insertNewBookCopy = "insert into Book_Copies (BookCopyId, NoOfCopies, BookId, BranchId) "
				+ "values (?, ?, ?, ?)";
		preparedStatement = connect.prepareStatement(insertNewBookCopy);
		preparedStatement.setLong(1, bookCopyId);
		preparedStatement.setInt(2, noOfCopies+1);
		preparedStatement.setLong(3, bookId);
		preparedStatement.setLong(4, branchId);
		preparedStatement.executeUpdate();
		System.out.println("Book returned successfully");
	}
}

