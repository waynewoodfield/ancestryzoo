package action;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class FeedingAction extends BaseAction
{
  private DateFormat dateParse = new SimpleDateFormat("yyyy-MM-dd");
  private DateFormat timeParse = new SimpleDateFormat("HH:mm");

  public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    ResultSet rs = loadToCachedRowSet("select a.animal_id, a.name, s.name as species, z.name as zoo" +
      " from animal a inner join zoo z on a.zoo_id = z.zoo_id inner join species s on a.species_id = s.species_id" +
      " order by zoo, species, a.animal_id");
    Map<Integer, String> animalMap = new HashMap<>();
    List<Integer> animalOrder = new ArrayList<>();
    while (rs.next())
    {
      animalOrder.add(rs.getInt("animal_id"));
      animalMap.put(rs.getInt("animal_id"), rs.getString("name") + " - " + rs.getString("species") + " - " + rs.getString("zoo"));
    }
    request.setAttribute("animalMap", animalMap);
    request.setAttribute("animals", animalOrder);
    return mapping.findForward("success");
  }

  public ActionForward process(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    int animalId = Integer.parseInt(request.getParameter("animalId"));
    Date feedingDate = new Date(dateParse.parse(request.getParameter("date")).getTime());
    Timestamp feedingTime = new Timestamp(timeParse.parse(request.getParameter("time")).getTime());
    int quantity = Integer.parseInt(request.getParameter("quantity"));
    Connection con = null;
    PreparedStatement ps = null;
    try
    {
      con = dataSource.getConnection();
      int id = getID("feeding_seq", con);

      int i = 0;
      ps = con.prepareStatement("insert into feeding (feeding_id, animal_id, feeding_date, feeding_time, quantity) values (?,?,?,?,?)");
      ps.setInt(++i, id);
      ps.setInt(++i, animalId);
      ps.setDate(++i, feedingDate);
      ps.setTimestamp(++i, feedingTime);
      ps.setInt(++i, quantity);
      ps.executeUpdate();
    }
    finally
    {
      close(con, ps);
    }
    return mapping.findForward("success");
  }
}
