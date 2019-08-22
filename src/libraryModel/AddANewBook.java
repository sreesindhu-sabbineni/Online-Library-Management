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

public class AddANewBook {
	
	public void add() throws SQLException, InterruptedException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Scanner sc = new Scanner(System.in);
		Connection connect = SqlAccess.getConnection();
		long bookId;
		System.out.println("Enter the Book ID");
		if(sc.hasNextLong())
			bookId = sc.nextLong();
		else
			bookId = 0;
		sc.nextLine();
		System.out.println("Enter Author Name");
		String author = sc.nextLine();
		System.out.println("Enter the Book Title");
		String title = sc.nextLine();
		System.out.println("Enter the Name of the Publisher");
		String publisherName = sc.nextLine();
		System.out.println("Enter the address of the publisher");
		String publisherAddress = sc.nextLine();
		System.out.println("Enter the publisher phone number");
		String publisherPhone = sc.nextLine();
		String publishers = "Insert into Publishers (Name, Address, Phone) values (?, ?, ?)";
		preparedStatement = connect.prepareStatement(publishers);
        preparedStatement.setString(1, publisherName);
        preparedStatement.setString(2, publisherAddress);
        preparedStatement.setString(3, publisherPhone);
        preparedStatement.executeUpdate();
        String books = "Insert into Books (BookId, AuthorName, Title, Name) values (?, ?, ?, ?)";
        preparedStatement = connect.prepareStatement(books);
        preparedStatement.setLong(1, bookId);
        preparedStatement.setString(2, author);
        preparedStatement.setString(3, title);
        preparedStatement.setString(4, publisherName);
        preparedStatement.executeUpdate();
		String BranchNames = "Select BranchName,BranchId from Library_Branches;";
		preparedStatement = connect.prepareStatement(BranchNames);
		resultSet = preparedStatement.executeQuery();
		int NoOfCopies;
        while(resultSet.next())
        {
        		System.out.println("Enter the number of copies for "+resultSet.getString(1)+":whose BranchId is "+resultSet.getInt(2)); 
        		NoOfCopies = sc.nextInt();
        if(NoOfCopies>0) {
        String bookCopies = "Insert into Book_Copies (BookCopyId, NoOfCopies, BookId, BranchId) values (?, ? ,?, ?)";
        preparedStatement = connect.prepareStatement(bookCopies);
        Random rand = new Random();
        long bookCopyId = rand.nextLong();
        List<Long> usedValues = new ArrayList<Long>(Arrays.asList(1L, 2L, 3L));
        usedValues.add(bookCopyId);
        while(usedValues.contains(bookCopyId)) {
        	bookCopyId = rand.nextLong();
        }
        preparedStatement.setLong(1, bookCopyId);
        preparedStatement.setInt(2, NoOfCopies);
        preparedStatement.setLong(3, bookId);
        preparedStatement.setInt(4, resultSet.getInt(2));
        preparedStatement.executeUpdate();
        
        }
	}
//sc.close();
        System.out.println("New Book added to the system successfully");
}
}

