package action;

import woodyware.struts.DispatchMethodAction;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseAction extends DispatchMethodAction
{
  protected DataSource dataSource;

  public BaseAction()
  {
    try
    {
      dataSource = (DataSource)new InitialContext().lookup("java:comp/env/jdbc/zoo");
    }
    catch (Exception e)
    {
      System.err.println("Error creating data source:");
      e.printStackTrace();
    }
  }

  protected int getID(String sequence, Connection con) throws SQLException
  {
    PreparedStatement ps = null;
    ResultSet rs = null;
    try
    {
      ps = con.prepareStatement("select " + sequence + ".nextval");
      rs = ps.executeQuery();
      return rs.next() ? rs.getInt(1) : 0;
    }
    finally
    {
      close(null, ps, rs);
    }
  }

  protected void close(Connection con, PreparedStatement ps, ResultSet rs)
  {
    try
    {
      if (rs != null)
        rs.close();
    }
    catch (Exception e)
    {
      // Tried
    }
    close(con, ps);
  }

  protected void close(Connection con, PreparedStatement ps)
  {
    try
    {
      if (ps != null)
        ps.close();
    }
    catch (Exception e)
    {
      // Tried
    }
    try
    {
      if (con != null)
        con.close();
    }
    catch (Exception e)
    {
      // Tried
    }
  }
}
