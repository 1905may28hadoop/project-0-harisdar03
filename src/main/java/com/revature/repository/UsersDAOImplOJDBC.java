package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.exception.DepositException;
import com.revature.exception.WithdrawException;
import com.revature.model.Users;
import com.revature.util.CloseStreams;
import com.revature.util.ConnectionUtil;

public class UsersDAOImplOJDBC implements UsersDAO {

	@Override
	public Users getUsers(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Users getUsers(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createUsers(Users user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateUsers(Users user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Users> getUsers() {
		// TODO Auto-generated method stub
		List<Users> users = new ArrayList<>();
		Statement statement= null;
		ResultSet rs=null;
		
		try (Connection conn=ConnectionUtil.getConnection()) {
			
			statement = conn.createStatement();
			rs=statement.executeQuery("SELECT * FROM users");
			
			while(rs.next())
			{
				users.add(new Users (
						rs.getLong("id"),
						rs.getString(2),
						rs.getString(3),
						rs.getFloat(4)
						));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CloseStreams.close(statement);
			CloseStreams.close(rs);
		}
		
		
		return users;
	}
	public static boolean checkUsername(String username)
	{
		boolean check=false;
		PreparedStatement stmt= null;
		ResultSet rs = null;
		String query="SELECT username , password FROM users WHERE username=?";
		try {
			
			stmt=ConnectionUtil.getConnection().prepareStatement(query);
			stmt.setString(1,username);
			rs=stmt.executeQuery();
			
			if(rs.next())
			{
				check=true;
			} else {
				check = false;
			}
			
			
			} catch (SQLException e) {
				e.printStackTrace();
				return check;
			}
		return check;
		
	}
	
	public static boolean checkLogIn(String username, String password)
	{
		boolean check=false;
		PreparedStatement stmt= null;
		ResultSet rs = null;
		String query="SELECT username,password,balance FROM users WHERE username=? AND password=?";
		try {
			
			stmt=ConnectionUtil.getConnection().prepareStatement(query);
			stmt.setString(1,username);
			stmt.setString(2,password);
			rs=stmt.executeQuery();
			
			if(rs.next())
			{

				
				check=true;
			} else {
				check=false;
			}
			
			
			} catch (SQLException e) {
				e.printStackTrace();
				return check;
			} finally {
				CloseStreams.close(stmt);
				CloseStreams.close(rs);
			}
		return check;
		
	}
	
	public static float viewBalance(String username, String password) {
		PreparedStatement ps= null;
		ResultSet rs = null;
		float balance=0L;
		String query ="SELECT username,password,balance FROM users WHERE username=? AND password=?";
		try {
			ps=ConnectionUtil.getConnection().prepareStatement(query);
			ps.setString(1,username);
			ps.setString(2,password);
			rs=ps.executeQuery();
			
			if(rs.next())
			{
				 balance = rs.getFloat(3);
				return balance;
			}
			return balance;
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally {
			CloseStreams.close(ps);
			CloseStreams.close(rs);
		}
		return 0;
	}
	
	public static float deposit(String username, String password, float depositamount) throws DepositException
	{
		PreparedStatement ps= null;
		ResultSet rs = null;
		float balance=0L;
		float finalamount=0L;
		String query ="SELECT username,password,balance FROM users WHERE username=? AND password=?";
		try {
			ps=ConnectionUtil.getConnection().prepareStatement(query);
			ps.setString(1,username);
			ps.setString(2,password);
			rs=ps.executeQuery();
			
			rs.next();
			balance = rs.getLong(3);
			
			if(depositamount<0)
			{
				System.err.println("Cannot deposit a negative amount");
				throw new DepositException();
			}
			else {
			finalamount = depositamount+balance;
			}
			ps=ConnectionUtil.getConnection().prepareStatement("UPDATE users SET balance="+finalamount+"WHERE username=? AND password=?");
			ps.setString(1,username);
			ps.setString(2, password);
			ps.execute();
			
			
			
			System.out.println("The deposit was successful. Your balance is now: $"+finalamount);
			System.out.println("");
			
		
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally {
			CloseStreams.close(ps);
			CloseStreams.close(rs);
		}
		return finalamount;
		
	}
	
	public static float withdraw(String username, String password, float withdrawalamount) throws WithdrawException
	{
		PreparedStatement ps= null;
		ResultSet rs=null;
		float balance=0L;
		String query ="SELECT username,password,balance FROM users WHERE username=? AND password=?";
		float finalamount=0L;
		try {
			ps=ConnectionUtil.getConnection().prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, password);
			rs=ps.executeQuery();		
			
			rs.next();
			balance=rs.getLong(3);
			if(withdrawalamount>balance) {
				System.err.println("Cannot withdraw more than balance amount");
				throw new WithdrawException();
			}
			
			else {
				 finalamount=balance-withdrawalamount;
			}
			ps=ConnectionUtil.getConnection().prepareStatement("UPDATE users SET balance="+finalamount+"WHERE username=? AND password=?");
			ps.setString(1,username);
			ps.setString(2, password);
			ps.execute();
			
			System.out.println("The withdrawal was successful. Your balance is now: $"+finalamount);
			System.out.println("");
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally {
			CloseStreams.close(ps);
			CloseStreams.close(rs);
		}
		return finalamount;
	}
	public static void options() {
		
		System.out.println("What would you like to do? ");
		System.out.println("");
		System.out.println("Enter 1 if you would like to view your balance");
		System.out.println("Enter 2 if you would like to deposit money into your account");
		System.out.println("Enter 3 if you would like to withdraw money from your account");
		System.out.println("Enter 4 to close the application");
		
	}


}
