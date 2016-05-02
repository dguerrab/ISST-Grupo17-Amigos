package es.upm.dit.isst.amigos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import es.upm.dit.isst.amigos.dao.*;
import es.upm.dit.isst.amigos.logic.Functions;
import es.upm.dit.isst.amigos.model.*;

@SuppressWarnings("serial")
public class ConversacionServlet extends HttpServlet {
	
	GrupoDAO grupodao = GrupoDAOImpl.getInstance();
	AgrupacionesDAO agrupao = AgrupacionesDAOImpl.getInstance();
	UserDAO usao = UserDAOImpl.getInstance();
	ChatDAOImpl chatdao = ChatDAOImpl.getInstance();
	
	UserService userservice = UserServiceFactory.getUserService();
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		if (req.getUserPrincipal() == null ){
			resp.getWriter().println("<p>No deber�as estar aqu�.</p>"
							+ "<a href=\"" + userservice.createLoginURL(req.getRequestURI()) +
                                     "\">Logueate</a> o <a href=\"\\\">vete</a>");
		}
		
		else {
			Long id = Long.valueOf(req.getParameter("grupo_id"));
			String user = userservice.getCurrentUser().getNickname();
			Grupo grupo = grupodao.getGrupoById(id);
			
			try {
				Chat chat_vi = chatdao.getChatByFromAndGrupo(id, user);
				String[] conver_vi = chat_vi.getConversacionParsed();
				List lista_vi = new ArrayList();
				lista_vi = Arrays.asList(conver_vi);
				req.getSession().setAttribute("conver_vi", lista_vi);
			}
			catch (Exception e) {
				String[] conver_vi = null;
				req.getSession().setAttribute("conver_vi", conver_vi);
			}
			
			try {
				Chat chat_invi = chatdao.getChatByToAndGrupo(id, user);
				String[] conver_invi = chat_invi.getConversacionParsed();
				List lista_invi = new ArrayList();
				lista_invi = Arrays.asList(conver_invi);
				req.getSession().setAttribute("conver_invi", lista_invi);
			}
			catch (Exception e) {
				String[] conver_invi = null;
				req.getSession().setAttribute("conver_invi", conver_invi);
			}
			
			req.getSession().setAttribute("grupo", grupo);
			
			resp.sendRedirect("conversacion.jsp");
			
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		String conver = req.getParameter("conver");
		Long id = Long.valueOf(req.getParameter("grupo_id"));
		String mensaje = "";
		String autor = userservice.getCurrentUser().getNickname();
		
		Agrupaciones agrupacion_vi = agrupao.getAgrupByUserAndGrupo(autor, id);
		Agrupaciones agrupacion_invi = agrupao.getAgrupByAmiInvAndGrupo(autor, id);
		
		if(conver.equals("visible")) {
			Functions.getInstance().chat(agrupacion_vi.getAmigoinv(), grupodao.getGrupoById(id).getNombre());
			Chat chat = new Chat(id, autor, autor, null, false, false);
			try {
				chat = chatdao.getChatByFromAndGrupo(id, autor);
			}
			catch (Exception e) {
				chatdao.insertChat(id, autor, agrupacion_vi.getAmigoinv(), true, true);
				chat = chatdao.getChatByFromAndGrupo(id, autor);
			}
			mensaje = req.getParameter("conv_vi");
			chatdao.insertMensaje(chat, mensaje, "an�nimo");
			
			Grupo grupo = grupodao.getGrupoById(id);
			try {
				Chat chat_vi = chatdao.getChatByFromAndGrupo(id, autor);
				String[] conver_vi = chat_vi.getConversacionParsed();
				List lista_vi = new ArrayList();
				lista_vi = Arrays.asList(conver_vi);
				req.getSession().setAttribute("conver_vi", lista_vi);
			}
			catch (Exception e) {
				String[] conver_vi = null;
				req.getSession().setAttribute("conver_vi", conver_vi);
			}
			try {
				Chat chat_invi = chatdao.getChatByToAndGrupo(id, autor);
				String[] conver_invi = chat_invi.getConversacionParsed();
				List lista_invi = new ArrayList();
				lista_invi = Arrays.asList(conver_invi);
				req.getSession().setAttribute("conver_invi", lista_invi);
			}
			catch (Exception e) {
				String[] conver_invi = null;
				req.getSession().setAttribute("conver_invi", conver_invi);
			}
			req.getSession().setAttribute("grupo", grupo);
			
			resp.sendRedirect("conversacion.jsp");
		}
		
		else if(conver.equals("invisible")) {
			Functions.getInstance().chat(agrupacion_invi.getUser(), grupodao.getGrupoById(id).getNombre());
			Chat chat = new Chat(id, autor, autor, null, false, false);
			try {
				chat = chatdao.getChatByToAndGrupo(id, autor);
			}
			catch (Exception e) {
				chatdao.insertChat(id, agrupacion_invi.getUser(), autor, true, true);
				chat = chatdao.getChatByToAndGrupo(id, autor);
			}
			mensaje = req.getParameter("conv_invi");
			chatdao.insertMensaje(chat, mensaje, autor);
			
			Grupo grupo = grupodao.getGrupoById(id);
			try {
				Chat chat_vi = chatdao.getChatByFromAndGrupo(id, autor);
				String[] conver_vi = chat_vi.getConversacionParsed();
				List lista_vi = new ArrayList();
				lista_vi = Arrays.asList(conver_vi);
				req.getSession().setAttribute("conver_vi", lista_vi);
			}
			catch (Exception e) {
				String[] conver_vi = null;
				req.getSession().setAttribute("conver_vi", conver_vi);
			}
			try {
				Chat chat_invi = chatdao.getChatByToAndGrupo(id, autor);
				String[] conver_invi = chat_invi.getConversacionParsed();
				List lista_invi = new ArrayList();
				lista_invi = Arrays.asList(conver_invi);
				req.getSession().setAttribute("conver_invi", lista_invi);
			}
			catch (Exception e) {
				String[] conver_invi = null;
				req.getSession().setAttribute("conver_invi", conver_invi);
			}
			req.getSession().setAttribute("grupo", grupo);
			
			resp.sendRedirect("conversacion.jsp");
		}
		
		else {
			req.getSession().setAttribute("error", "�Mensaje enviado!");
			resp.sendRedirect("avisos.jsp");	
		}
	}
}
