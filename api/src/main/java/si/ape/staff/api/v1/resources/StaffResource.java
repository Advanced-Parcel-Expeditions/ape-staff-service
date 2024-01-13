package si.ape.staff.api.v1.resources;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.ape.staff.services.beans.StaffBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

import si.ape.staff.lib.*;

@ApplicationScoped
@Path("/staff")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StaffResource {

    private final Logger log = Logger.getLogger(StaffResource.class.getName());

    @Inject
    private StaffBean staffBean;


    @Context
    protected UriInfo uriInfo;

    @Operation(description = "Get employee by ID.", summary = "Get employee")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Employee is delivered.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = Employee.class))
            ),
            @APIResponse(responseCode = "404", description = "Employee not found .")
    })
    @GET
    @Path("/{employeeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployee(@Parameter(description = "Employee ID.", required = true) @PathParam("employeeId") Integer employeeId) {

        Employee employee = staffBean.getEmployee(employeeId);

        if (employee == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(employee).build();
    }

    @Operation(description = "Get employees at branch.", summary = "Get employees")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Employees are delivered.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = Employee.class))
            ),
            @APIResponse(responseCode = "404", description = "Employees not found .")
    })
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployees(@Parameter(description = "Branch ID.", required = true) @QueryParam("branchId") Integer branchId) {

        List<Employee> employees = staffBean.getEmployeesAtBranch(branchId);

        if (employees == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(employees).build();
    }

    @Operation(description = "Get employees with role at branch.", summary = "Get employees with role")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Employees are delivered.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = Employee.class))
            ),
            @APIResponse(responseCode = "404", description = "Employees not found .")
    })
    @GET
    @Path("/with-role")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeesWithRole(@Parameter(description = "Branch ID.", required = true) @QueryParam("branchId") Integer branchId,
                                         @Parameter(description = "Role ID.", required = true) @QueryParam("roleId") Integer roleId) {

        List<Employee> employees = staffBean.getEmployeesAtBranch(branchId, roleId);

        if (employees == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(employees).build();
    }

    @Operation(description = "Move employee to branch.", summary = "Move employee")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Employee is moved.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = Employee.class))
            ),
            @APIResponse(responseCode = "404", description = "Employee not found .")
    })
    @PUT
    @Path("/move-employee")
    @Produces(MediaType.APPLICATION_JSON)
    public Response moveEmployee(@Parameter(description = "Employee ID.", required = true) @QueryParam("employeeId") Integer employeeId,
                                 @Parameter(description = "Branch ID.", required = true) @QueryParam("branchId") Integer branchId) {

        Employee employee = staffBean.moveEmployee(employeeId, branchId);

        if (employee == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(employee).build();
    }

}
