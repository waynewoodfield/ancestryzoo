package action;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;

public class ReportAction extends BaseAction
{
  public ActionForward feedingByDayAnimal(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    String sql = "select a.animal_id, a.name, s.name as species, count(f.feeding_id) as times_fed from feeding f" +
      " inner join animal a on f.animal_id = a.animal_id" +
      " inner join species s on a.species_id = s.species_id" +
      " group by a.animal_id, a.name, s.species_id, s.name, f.feeding_date";
    sql = "select animal_id, name, species, round(avg(times_fed), 2) as average_times_fed from (" + sql + ") innersql group by animal_id, name, species";
    ResultSet rs = loadToCachedRowSet(sql);
    request.setAttribute("resultSet", rs);
    return mapping.findForward("success");
  }

  public ActionForward feedingByDaySpecies(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    String sql = "select s.species_id, s.name as species, count(f.feeding_id) as times_fed from feeding f" +
      " inner join animal a on f.animal_id = a.animal_id" +
      " inner join species s on a.species_id = s.species_id" +
      " group by s.species_id, s.name, f.feeding_date";
    sql = "select species_id, species, round(avg(times_fed), 2) as average_times_fed from (" + sql + ") innersql group by species_id, species";
    ResultSet rs = loadToCachedRowSet(sql);
    request.setAttribute("resultSet", rs);
    return mapping.findForward("success");
  }

  public ActionForward zooPercentOfNormal(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    String speciesSql = "select s.species_id, s.name as species, count(f.feeding_id) as times_fed from feeding f" +
      " inner join animal a on f.animal_id = a.animal_id" +
      " inner join species s on a.species_id = s.species_id" +
      " group by s.species_id, s.name, f.feeding_date";
    speciesSql = "select species_id, species, avg(times_fed) as times_fed from (" + speciesSql + ") innersql group by species_id, species";

    String zooSql = "select z.zoo_id, z.name zoo, s.species_id, s.name as species, count(f.feeding_id) as times_fed from feeding f" +
      " inner join animal a on f.animal_id = a.animal_id" +
      " inner join species s on a.species_id = s.species_id" +
      " inner join zoo z on a.zoo_id = z.zoo_id" +
      " group by z.zoo_id, z.name, s.species_id, s.name, f.feeding_date";
    zooSql = "select zoo_id, zoo, species_id, species, avg(times_fed) as times_fed from (" + zooSql + ") innersql group by zoo_id, zoo, species_id, species";

    String sql = "select species.species, round(species.times_fed, 2) as average_for_species, zoo.zoo, round(zoo.times_fed, 2) as average_at_zoo, " +
      " round(zoo.times_fed * 100 / species.times_fed, 2)||'% of normal' as percent_of_normal " +
      " from (" + zooSql + ") zoo " +
      " inner join (" + speciesSql + ") species on zoo.species_id = species.species_id";
    ResultSet rs = loadToCachedRowSet(sql);
    request.setAttribute("resultSet", rs);
    return mapping.findForward("success");
  }

}
