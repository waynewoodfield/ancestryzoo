package action;

import com.sun.rowset.CachedRowSetImpl;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.CachedRowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportAction extends BaseAction
{
  private ResultSet runReport(String sql) throws SQLException
  {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try
    {
      con = dataSource.getConnection();
      ps = con.prepareStatement(sql);
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

  public ActionForward feedingByDayAnimal(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    String sql = "select a.animal_id, a.name, count(feeding_id) as times_fed from feeding f" +
      " inner join animal a on f.animal_id = a.animal_id" +
      " group by a.animal_id, a.name, f.feeding_date";
    sql = "select animal_id, name, ave(times_fed) from (" + sql + ") group by animal_id, name";
    ResultSet rs = runReport(sql);
    request.setAttribute("resultSet", rs);
    return mapping.findForward("success");
  }

  public ActionForward feedingByDaySpecies(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    String sql = "select a.animal_id, a.name, count(feeding_id) as times_fed from feeding f" +
      " inner join animal a on f.animal_id = a.animal_id" +
      " inner join species s on a.species_id = s.species_id" +
      " group by s.species_id, s.name, f.feeding_date";
    sql = "select species_id, name, ave(times_fed) from (" + sql + ") group by animal_id, name";
    ResultSet rs = runReport(sql);
    request.setAttribute("resultSet", rs);
    return mapping.findForward("success");
  }

}
