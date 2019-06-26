package com.revature.util;

import java.sql.Statement;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CloseStreams {

	public static void close(FileInputStream resource) {
		if (resource != null)
		{
			try {
				resource.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close(Statement resource) {
		if (resource != null)
		{
			try {
				resource.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close(Connection resource) {
		if (resource != null)
		{
			try {
				resource.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close(ResultSet resource) {
		if (resource != null)
		{
			try {
				resource.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

