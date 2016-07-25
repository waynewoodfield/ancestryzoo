package action;

import com.sun.rowset.CachedRowSetImpl;
import woodyware.struts.DispatchMethodAction;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;
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
      ps = con.prepareStatement("select nextval('" + sequence + "')");
      rs = ps.executeQuery();
      return rs.next() ? rs.getInt(1) : 0;
    }
    finally
    {
      close(null, ps, rs);
    }
  }

  protected ResultSet loadToCachedRowSet(String sql, Object... args) throws SQLException
  {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try
    {
      con = dataSource.getConnection();
      ps = con.prepareStatement(sql);
      int i = 0;
      for (Object arg : args)
        ps.setObject(++i, arg);

      rs = ps.executeQuery();

      CachedRowSet crs = new CachedRowSetImpl();
      crs.populate(rs);
      return crs;
    }
    finally
    {
      close(con, ps, rs);
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

  public static void close(Connection con, PreparedStatement ps)
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
