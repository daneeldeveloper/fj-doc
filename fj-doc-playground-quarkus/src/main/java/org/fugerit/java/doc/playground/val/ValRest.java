package org.fugerit.java.doc.playground.val;

import java.io.FileInputStream;

import org.fugerit.java.doc.playground.RestHelper;
import org.fugerit.java.doc.val.core.DocTypeValidationResult;
import org.fugerit.java.doc.val.core.DocValidatorFacade;
import org.fugerit.java.doc.val.core.basic.ImageValidator;
import org.fugerit.java.doc.val.pdf.box.PdfboxValidator;
import org.fugerit.java.doc.val.poi.DocValidator;
import org.fugerit.java.doc.val.poi.DocxValidator;
import org.fugerit.java.doc.val.poi.XlsValidator;
import org.fugerit.java.doc.val.poi.XlsxValidator;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@Path("/val")
public class ValRest {

	private static final DocValidatorFacade facade = DocValidatorFacade.newFacadeStrict(ImageValidator.JPG_VALIDATOR,
			ImageValidator.PNG_VALIDATOR, ImageValidator.TIFF_VALIDATOR, PdfboxValidator.DEFAULT, DocxValidator.DEFAULT,
			DocValidator.DEFAULT, XlsxValidator.DEFAULT, XlsValidator.DEFAULT);

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/check")
	public Response check( ValInput input) {
		return RestHelper.defaultHandle( () -> {
			ValOutput output = null;
			FileUpload file = input.getFile();
			log.info( "file -> {} -> {}", file.fileName(), file.uploadedFile() );
			try ( FileInputStream fis = new FileInputStream( file.uploadedFile().toFile() ) ) {
				DocTypeValidationResult result = facade.validate( file.fileName(), fis );
				if (result.isResultOk()) {
					output = new ValOutput(true, "Input is valid");
				} else {
					output = new ValOutput(false, "Input is not valid");
				}	
			}
			Response res = null;
			if (output != null) {
				if (output.isValid()) {
					res = Response.ok().entity(output).build();
				} else {
					res = Response.status(Response.Status.BAD_REQUEST).entity(output).build();
				}
			}
			return res;
		} );
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/supported_extensions")
	public Response supportedExtensions() {
		return Response.ok().entity( facade.getSupportedExtensions() ).build();
	}

}