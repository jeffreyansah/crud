package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cruddao.*;
import model.Stuff;

/**
 * Servlet implementation class StuffController
 */
@WebServlet("/StuffController")
public class StuffController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DaoStuff StuffDao = DaoStuff.getInstance();
	private static final Logger LOGGER = Logger.getLogger(StuffController.class.getName());

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		String op = req.getParameter("op");
		
		try {
			switch (op) {
			case "new":
				showNewForm(req, resp);
				break;
			case "insert":
				insertStuff(req, resp);
				break;
			case "delet":
				deleteStuff(req, resp);
				break;
			case "edit":
				showEditForm(req, resp);
				break;

			case "update":
				updateStuff(req, resp);
				break;
			default:
				listStuff(req, resp);
				break;
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "SQL Error", e);
		}

	}

	private void updateStuff(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SQLException {

		int id = Integer.parseInt(req.getParameter("id"));
		String name = req.getParameter("name");
		String description = req.getParameter("description");
		int quantity = Integer.parseInt(req.getParameter("quantity"));
		String location = req.getParameter("location");
		Stuff stuff = new Stuff(id, name, description, quantity, location);
		StuffDao.update(stuff);
		resp.sendRedirect("list");
	}

	private void showEditForm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SQLException {
		String id = req.getParameter("id");
		Optional<Stuff> existingStuff = StuffDao.find(id);
		RequestDispatcher dispatcher = req.getRequestDispatcher("/StuffForm");
		existingStuff.ifPresent(s -> req.setAttribute("stuff", s));
		dispatcher.forward(req, resp);
	}

	private void deleteStuff(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SQLException {
		int id = Integer.parseInt(req.getParameter("id"));
		Stuff stuff = new Stuff(id);
		StuffDao.delete(stuff);
		resp.sendRedirect("list");

	}

	private void showNewForm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SQLException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("/StuffForm");
		dispatcher.forward(req, resp);

	}

	private void listStuff(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SQLException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("/StuffForm");
		List<Stuff> listStuff = StuffDao.findAll();
		req.setAttribute("listStuff", listStuff);
		dispatcher.forward(req, resp);
	}
	
	 private void forward(HttpServletRequest request, HttpServletResponse response, String page) 
		       throws ServletException, IOException
		    {
		        ServletContext sc = getServletContext();
		        RequestDispatcher rd = sc.getRequestDispatcher(page);
		        rd.forward(request,response);
		  }

	private void insertStuff(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SQLException {

		String name = req.getParameter("name");
		String description = req.getParameter("description");
		int quantity = Integer.parseInt(req.getParameter("quantity"));
		String location = req.getParameter("location");
		Stuff stuff = new Stuff(name, description, quantity, location);
		StuffDao.save(stuff);
		forward(req,resp,"/stuffForm.jsp");
	}

}
