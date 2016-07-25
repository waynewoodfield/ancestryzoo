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
    String sql = "select a.animal_id, a.name, s.species_id, s.name as species, count(f.feeding_id) as times_fed from feeding f" +
      " inner join animal a on f.animal_id = a.animal_id" +
      " inner join species s on a.species_id = s.species_id" +
      " group by a.animal_id, a.name, s.species_id, s.name, f.feeding_date";
    sql = "select animal_id, name, species_id, species, ave(times_fed) from (" + sql + ") group by animal_id, name, species_id, species";
    ResultSet rs = runReport(sql);
    request.setAttribute("resultSet", rs);
    return mapping.findForward("success");
  }

  public ActionForward feedingByDaySpecies(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    String sql = "select s.species_id, s.name, count(f.feeding_id) as times_fed from feeding f" +
      " inner join animal a on f.animal_id = a.animal_id" +
      " inner join species s on a.species_id = s.species_id" +
      " group by s.species_id, s.name, f.feeding_date";
    sql = "select species_id, name, ave(times_fed) from (" + sql + ") group by animal_id, name";
    ResultSet rs = runReport(sql);
    request.setAttribute("resultSet", rs);
    return mapping.findForward("success");
  }

  public ActionForward zooPercentOfNormal(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    String speciesSql = "select s.species_id, s.name as species, count(f.feeding_id) as times_fed from feeding f" +
      " inner join animal a on f.animal_id = a.animal_id" +
      " inner join species s on a.species_id = s.species_id" +
      " group by s.species_id, s.name, f.feeding_date";
    speciesSql = "select species_id, species, ave(times_fed) from (" + speciesSql + ") group by animal_id, name";

    String zooSql = "select z.zoo_id, z.name zoo, s.species_id, s.name as species, count(f.feeding_id) as times_fed from feeding f" +
      " inner join animal a on f.animal_id = a.animal_id" +
      " inner join species s on a.species_id = s.species_id" +
      " inner join zoo z on a.zoo_id = z.zoo_id" +
      " group by z.zoo_id, z.name, s.species_id, s.name, f.feeding_date";
    zooSql = "select zoo_id, zoo, species_id, species, ave(times_fed) from (" + zooSql + ") group by zoo_id, zoo, species_id, species";

    String sql = "select species.species, species.times_fed as average_for_species, zoo.zoo, zoo.times_fed as average_at_zoo, " +
      " (zoo.times_fed * 100 / species.times_fed)||'% of normal' as percent_of_normal " +
      " from (" + zooSql + ") zoo " +
      " inner join (" + speciesSql + ") species on animal.species_id = species.species_id";
    ResultSet rs = runReport(sql);
    request.setAttribute("resultSet", rs);
    return mapping.findForward("success");
  }

}
