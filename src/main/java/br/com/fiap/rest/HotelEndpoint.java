package br.com.fiap.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.fiap.dao.DAO;
import br.com.fiap.model.Hotel;

@Path("/hotels")
@Produces(MediaType.APPLICATION_JSON)
public class HotelEndpoint {
	
	DAO<Hotel> dao = new DAO<Hotel>(Hotel.class);
	
	@GET
	public Response index() {
		List<Hotel> hotels = dao.getAll();
		return Response.status(Response.Status.OK).entity(hotels).build();
	}
	
	@GET
	@Path("{id}")
	public Response show(@PathParam("id") Long id) {
		Hotel hotel = dao.findById(id);
		
		if(hotel == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		return Response.status(Response.Status.OK).entity(hotel).build();
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(Hotel hotel) {
		if (hotel == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
			
		try {
			dao.save(hotel);
			return Response.status(Response.Status.CREATED).entity(hotel).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") Long id, Hotel hotel) {
		hotel.setId(id);
		dao.update(hotel);
		return Response.status(Response.Status.OK).entity(hotel).build();

	}

	
	
	
	
	
	
	
	
	
	
	
	
}
