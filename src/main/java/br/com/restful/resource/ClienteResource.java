package br.com.restful.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.restful.controller.ClienteController;
import br.com.restful.model.Cliente;

/**
 * Classe responsavel por conter os metodos REST de acesso ao webservice
 */
@Path("/cliente")
public class ClienteResource {
	
	@GET
	@Path("/")
	@Produces({MediaType.TEXT_HTML})
	public InputStream viewHome() throws FileNotFoundException
	{
	   File f = new File("../../../../../webapp/index.html");
	   //File f = getFileFromSomewhere("tml");
	   return new FileInputStream(f);
	}
	
	@GET
	@Path("/listarTodos")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Cliente> listarTodos() {
		System.out.println("Clientes encontrados no banco");
		return new ClienteController().listarTodos();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/id/{id}")
	public Response getById(@PathParam("id") Long id) {
		Cliente cliente = new ClienteController().buscarPorId(id);
		if (cliente != null) {
			return Response.ok().type(MediaType.APPLICATION_JSON)
					.entity(cliente).build();
		} else {
			return Response.status(404).entity("Cliente nao encontrado")
					.build();
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/salvar")
	public Response salvarClienteJson(Cliente cliente) {
		boolean isClienteGravado = new ClienteController()
				.gravarCliente(cliente);
		if (isClienteGravado == true) {
			return Response.ok().entity(cliente).build();
		} else {
			return Response.status(500)
					.entity("Erro no servidor  ao gravar cliente").build();
		}

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/atualizar")
	public Response altualizarCliente(Cliente cliente) {
		boolean isClienteAtualizado = new ClienteController()
				.atualizarCliente(cliente);

		if (isClienteAtualizado == true) {
			return Response.ok().entity(cliente).build();
		} else {
			return Response.status(500)
					.entity("Erro no servidor  ao atualizar cliente").build();
		}

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/deletar")
	public Response deletarCliente(Cliente cliente) {

		boolean isClienteDeletado = new ClienteController().deletarCliente(cliente);
		if (isClienteDeletado == true) {
			System.out.println("Cliente " + cliente.getNome() + " deletado");
			return Response.ok().entity(cliente).build();
		} else {
			System.out.println("Erro no servidor  ao deletar cliente: "
					+ cliente.getNome());
			return Response
					.status(500)
					.entity("Erro no servidor  ao deletar cliente: "
							+ cliente.getNome()).build();
		}

	}

}
