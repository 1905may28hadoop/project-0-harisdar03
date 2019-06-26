import static org.junit.Assert.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.revature.exception.WithdrawException;
import com.revature.repository.UsersDAOImplOJDBC;
import com.revature.util.CloseStreams;
import com.revature.util.ConnectionUtil;

import junit.framework.Assert;

public class BankingAppTests {
	public ExpectedException expectedException = ExpectedException.none();

//The following methods test check the functionality of my checkLogIn method.
	
	@Test
	public void successfulLogIn() {
		String username="bob03";
		String password="pword2";
		assertTrue(UsersDAOImplOJDBC.checkLogIn(username, password));
		
	}
	
	@Test
	public void failedLogIn() {
		String username = "hdar";
		String password = "123";
		assertFalse(UsersDAOImplOJDBC.checkLogIn(username, password));
	}
	
	//the following methods test the functionality of my withdraw method
	@Test
	public void successfulWithdraw() throws WithdrawException {
		String username="bob03";
		String password="pword2";
		float withdrawalamount=5000F;
		float expected= 0;
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
	
			}
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally {
			CloseStreams.close(ps);
			CloseStreams.close(rs);
		}
		expected=balance-withdrawalamount;
		assertEquals(expected,UsersDAOImplOJDBC.withdraw(username, password, withdrawalamount),0.0f);
	}
	
	@Test
	public void unsuccessfulWithdraw () {
		try {
			UsersDAOImplOJDBC.withdraw("bob03", "pword2", 1000000F);
		} catch (WithdrawException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		expectedException.expect(WithdrawException.class);
		
		
	}

}
