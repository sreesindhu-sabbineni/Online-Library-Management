package libraryModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DisplayBooksAtABranch {
	public void displayBooks() throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Scanner sc = new Scanner(System.in);
		Connection connect = SqlAccess.getConnection();
		System.out.println("Enter the Branch Id to display books at that branch");
		long branchId = sc.nextLong();
		sc.nextLine();
		while(true) {
			String verifyBranchId = "Select BranchName from Library_Branches where BranchId = ?";
			preparedStatement = connect.prepareStatement(verifyBranchId);
			preparedStatement.setLong(1, branchId);
			resultSet = preparedStatement.executeQuery();
			if(!resultSet.isBeforeFirst()) {
				System.out.println("Invalid BranchId!!\nEnter the correct one!!");
			}
			else {
				break;
			}
			branchId = sc.nextLong();
			sc.nextLine();
		}
		String displayBooks = "select Books.* from (Books join Book_Copies on (Books.BookId = Book_Copies.BookId))"
				+ "where Book_Copies.BranchId = ?";
		preparedStatement = connect.prepareStatement(displayBooks);
		preparedStatement.setLong(1, branchId);
		resultSet = preparedStatement.executeQuery();
		if(!resultSet.isBeforeFirst()) {
			System.out.println("No Books in this branch");
		}
		else {
			while(resultSet.next()) {
				System.out.println("BookId:"+resultSet.getLong(1)+"\nAuthorName:"+resultSet.getString(2)+"\nTitle:"
						+ resultSet.getString(3)+"\nPublisherName:"+resultSet.getString(4)+"\n\n");
				
			}
		}
	}
}


