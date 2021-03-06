package es.upm.dit.isst.amigos;
/**
 * @author Clara
 *
 */
import java.io.IOException;
import java.util.List;

import javax.servlet.http.*;

import es.upm.dit.isst.amigos.logic.*;
import es.upm.dit.isst.amigos.dao.*;
import es.upm.dit.isst.amigos.model.*;

@SuppressWarnings("serial")
public class Logica_SorteoGruposServlet extends HttpServlet {
	
	AgrupacionesDAO agrupacionesdao = AgrupacionesDAOImpl.getInstance();
	GrupoDAO grupodao = GrupoDAOImpl.getInstance();
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		
		Long id = Long.valueOf(req.getParameter("grupo_id"));
		
		Grupo grupo = grupodao.getGrupoById(id);
		
		String mod_name = grupo.getModerador();
		String money = grupo.getPreciomax();
		String date = grupo.getFecha();
		String msg = grupo.getMsg();
		
		List<Agrupaciones> agrupaciones = agrupacionesdao.getAgrupacionesByGrupo(id);
		
		int participants_int = agrupaciones.size();
		
		String[] usernames = new String[participants_int];
		String[] emails = new String[agrupaciones.size()];
		String[] usernames_excls = new String[participants_int];

		int n = 0;
		for (Agrupaciones a: agrupaciones){
			usernames[n] = a.getUser();
			emails[n] = a.getUser()+"@gmail.com";
			usernames_excls[n] = a.getExclusion();
			n++;
		}
		
		for(int i = 0; i < usernames.length; i++){
			for(int j = i+1; j < usernames.length; j++){
				if(usernames[i].equals(usernames[j])){
					req.getSession().setAttribute("error", "�Has introducido dos nombres iguales!");
					resp.sendRedirect("avisos.jsp");
					return;
				}
			}
		}
		for(int i = 0; i < emails.length; i++){
			for(int j = i+1; j < emails.length; j++){
				if(emails[i].equals(emails[j])){
					req.getSession().setAttribute("error", "�Has introducido dos email iguales!");
					resp.sendRedirect("avisos.jsp");
					return;
				}
			}
		}
		
		String[] randomizedArray = Functions.getInstance().asignador(usernames, usernames_excls);		
		if (randomizedArray.equals(null)){
			resp.sendRedirect("https://www.youtube.com/watch?v=TJL4Y3aGPuA"); // TROLOLOLO
			return;
		}
		
		for(int i=0; i<participants_int; i++) {
			for (Agrupaciones b: agrupaciones){
				if (randomizedArray[i] == b.getUser()) {
					if (i == participants_int-1) {
						b.setAmigoinv(randomizedArray[0]);
					}
					else {
						b.setAmigoinv(randomizedArray[i+1]);
					}
					agrupacionesdao.updateAgrupacion(b);
				} 
			}
		}
			
		Functions.getInstance().enviarEmail(randomizedArray, msg, money, date, mod_name, emails, usernames);
		
		resp.sendRedirect("/Grupos");
	}
}
