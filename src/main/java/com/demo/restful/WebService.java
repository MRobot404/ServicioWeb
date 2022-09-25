
package com.demo.restful;

import com.demo.bean.Persona;
import com.demo.queue.QueueUtil;
import com.google.gson.Gson;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author mmendez
 */
@Path("/")
public class WebService {

    @GET
    @Path("/datosPersona")
    @Produces(MediaType.APPLICATION_JSON)
    public Persona getDatosPersona() {
        Persona persona = new Persona();
        persona.setId(1);
        persona.setNombre("Manuel");
        persona.setEdad(31);
        return persona;
    }

    
    @POST
    @Path("/enviarDatos")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Persona setDatosPersona(Persona persona) {
        //persona.setNombre("modificacion");
        String nombreCola = "test";
        String nombreServicio = "EjemploCola";
        String serverLocation = "failover:(tcp://172.17.0.2:61616)?timeout=3000";
        Gson gson = new Gson();

        String message = gson.toJson(persona);

        try {
            QueueUtil.send(nombreCola, true, true, 0, nombreServicio, message, serverLocation);

            System.out.println("Enviando mensaje....");
            Thread.sleep(500);

        } catch (Exception e) {
            System.out.println("Error....");
            e.printStackTrace();
        }

        System.out.println("persona:" +persona);
        return persona;
    }

}
