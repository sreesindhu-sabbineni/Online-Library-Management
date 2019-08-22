package libraryModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class CheckOutACopyOfBook {
	public void checkOut() throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Scanner sc = new Scanner(System.in);
		Connection connect = SqlAccess.getConnection();
		System.out.println("Enter borrowers CardNo");
		long cardNo = sc.nextLong();
		sc.nextLine();
		String borrowersCheck = "Select * from Borrowers where CardNo = ?";
		preparedStatement = connect.prepareStatement(borrowersCheck);
		preparedStatement.setLong(1, cardNo);
		resultSet = preparedStatement.executeQuery();
		if(!resultSet.isBeforeFirst()) {
			System.out.println("Looks like a new user. Enter his details before proceeding");
			System.out.println("Enter Borrowers name");
			String borrowersName = sc.nextLine();
			System.out.println("Enter Borrowers address");
			String borrowersAddress = sc.nextLine();
			System.out.println("Enter Borrowers Phone Number");
			String borrowersPhone = sc.nextLine();
			String borrower = "Insert into Borrowers (CardNo, Name, Address, Phone) values (?, ?, ?, ?)";
			preparedStatement = connect.prepareStatement(borrower);
			preparedStatement.setLong(1, cardNo);
			preparedStatement.setString(2, borrowersName);
			preparedStatement.setString(3, borrowersAddress);
			preparedStatement.setString(4, borrowersPhone);
			preparedStatement.executeUpdate();
		}
		else {
		while(resultSet.next()) {
			System.out.println("User already in database with below details\nName:"+resultSet.getString(2)+"Address:"
					+resultSet.getString(3)+"Phone:"+resultSet.getString(4));
		}
		}
		System.out.println("Enter your BranchID");
		long branchId = sc.nextLong();
		sc.nextLine();
		String verifyBranchId = "Select BranchName from Library_Branches where BranchId = ?";
		preparedStatement = connect.prepareStatement(verifyBranchId);
		preparedStatement.setLong(1, branchId);
		resultSet = preparedStatement.executeQuery();
		if(!resultSet.isBeforeFirst()) {
			System.out.println("Invalid BranchId!!\nExiting the system...");
			System.exit(0);
		}
		System.out.println("Enter the lending Book ID");
		long bookId = sc.nextLong();
		while(true) {
		//bookId = sc.nextLong();
		//sc.nextLong();
		String verifyBookId = "Select Title from Books where BookId = ?";
		preparedStatement = connect.prepareStatement(verifyBookId);
		preparedStatement.setLong(1, bookId);
		resultSet = preparedStatement.executeQuery();
		if(!resultSet.isBeforeFirst()) {
			System.out.println("Invalid BookId!!\nEnter the correct one!!");
		bookId = sc.nextLong();
		}
		else {
			break;
		}
		}
		LocalDate dateOut = LocalDate.now();
		LocalDate dueDate = dateOut.plusDays(15);
		Random rand = new Random();
		long bookLoanId = rand.nextLong();
        List<Long> usedValues = new ArrayList<Long>(Arrays.asList(1L, 2L, 3L));
        while(usedValues.contains(bookLoanId)) {
        	bookLoanId = rand.nextLong();
        }
        String bookLoan = "insert into Book_Loans (BookLoanId, DateOut, DueDate, BranchId, BookId, CardNo) "
        		+ "values (?, ?, ?, ?, ?, ?)";
        preparedStatement = connect.prepareStatement(bookLoan);
        preparedStatement.setLong(1, bookLoanId);
        preparedStatement.setDate(2, java.sql.Date.valueOf(dateOut));
        preparedStatement.setDate(3, java.sql.Date.valueOf(dueDate));
        preparedStatement.setLong(4, branchId);
        preparedStatement.setLong(5, bookId);
        preparedStatement.setLong(6, cardNo);
        preparedStatement.executeUpdate();
        String bookCopyIds = "select BookCopyId, NoOfCopies from Book_Copies where BookId = ? and BranchId = ?";
        preparedStatement = connect.prepareStatement(bookCopyIds);
		preparedStatement.setLong(1, bookId);
		preparedStatement.setLong(2, branchId);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			String updateBookCopies1 = "delete from Book_Copies where BookCopyId = ?";
			preparedStatement = connect.prepareStatement(updateBookCopies1);
			preparedStatement.setLong(1, resultSet.getLong(1));
			preparedStatement.executeUpdate();
		}
		else {
			System.out.println("Incorrect details entered");
		}
        String updateBookCopies = "update Book_Copies set NoOfCopies = ? where BranchId = ? and BookId = ?";
        preparedStatement = connect.prepareStatement(updateBookCopies);
        preparedStatement.setInt(1, resultSet.getInt(2)-1);
		preparedStatement.setLong(2, branchId);
		preparedStatement.setLong(3, bookId);
		preparedStatement.executeUpdate(); 
		System.out.println("Book checked out successfully");
	}
}

