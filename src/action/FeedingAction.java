package action;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class FeedingAction extends BaseAction
{
  private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

  public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    return mapping.findForward("success");
  }

  public ActionForward process(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
  {
    int animalId = Integer.parseInt(request.getParameter("animal"));
    Timestamp feedingTime = new Timestamp(df.parse(request.getParameter("time")).getTime());
    int quantity = Integer.parseInt(request.getParameter("quantity"));
    Connection con = null;
    PreparedStatement ps = null;
    try
    {
      con = dataSource.getConnection();
      int id = getID("feeding_seq", con);

      ps = con.prepareStatement("insert into feeding (feeding_id, animal_id, feeding_time, quantity) values (?,?,?,?)");
      ps.setInt(1, id);
      ps.setInt(2, animalId);
      ps.setTimestamp(3, feedingTime);
      ps.setInt(4, quantity);
      ps.executeUpdate();
    }
    finally
    {
      close(con, ps);
    }
    return mapping.findForward("success");
  }
}
