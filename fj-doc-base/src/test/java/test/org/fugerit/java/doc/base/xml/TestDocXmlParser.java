package test.org.fugerit.java.doc.base.xml;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.fugerit.java.core.function.SimpleValue;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.doc.base.config.DocInput;
import org.fugerit.java.doc.base.config.DocOutput;
import org.fugerit.java.doc.base.config.DocTypeHandler;
import org.fugerit.java.doc.base.model.DocBase;
import org.fugerit.java.doc.base.parser.DocParser;
import org.fugerit.java.doc.base.parser.DocValidationResult;
import org.fugerit.java.doc.base.typehandler.markdown.SimpleMarkdownExtTypeHandler;
import org.fugerit.java.doc.base.xml.DocXmlParser;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.org.fugerit.java.BasicTest;

public class TestDocXmlParser extends BasicTest {

	public static final boolean VALID = true;
	public static final boolean NOT_VALID = false;
	
	public static final boolean NO_EXCEPTION = false;
	public static final boolean EXCEPTION = true;
	
	private final static Logger logger = LoggerFactory.getLogger( TestDocXmlParser.class );
	
	private boolean validateWorker( String path, boolean valid, boolean exception ) {
		SimpleValue<Boolean> res = new SimpleValue<>( true );
		runTestEx( () -> {
			String fullPath = "sample/"+path+".xml";
			logger.info( "validate -> {}", fullPath );
			DocParser parser = new DocXmlParser();
			try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( fullPath ) ) {
				DocValidationResult result = parser.validateResult( new InputStreamReader( is ) );
				logger.info( "Validation result {}", result.isResultOk() );
				for ( String error : result.getErrorList() ) {
					logger.info( "Validation error {}", error );
				}
				for ( String error : result.getInfoList() ) {
					logger.info( "Validation info {}", error );
				}
				Assert.assertEquals( "Validation result" , valid, result.isResultOk() );
			}
		} );
		return res.getValue();
	}
	
	private boolean parseWorker( String path ) {
		SimpleValue<Boolean> res = new SimpleValue<>( true );
		runTestEx( () -> {
			DocTypeHandler handler = SimpleMarkdownExtTypeHandler.HANDLER;
			try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( "sample/"+path+".xml" );
					FileOutputStream fos = new FileOutputStream( "target/"+path+"."+handler.getType() ) ) {
				DocXmlParser parser = new DocXmlParser();
				DocBase docBase = parser.parse(is);
				logger.info( "docBase -> {}", docBase );
				DocInput input = DocInput.newInput( handler.getType(), docBase, null );
				DocOutput output = DocOutput.newOutput( fos );
				handler.handle( input, output );
			}
		} );
		return res.getValue();
	}

	@Test
	public void testParseCoverage01() {
		Assert.assertTrue( this.parseWorker( "default_doc" ) );
	}
	
	@Test
	public void testParseCoverage02() {
		Assert.assertTrue( this.parseWorker( "default_doc_alt" ) );
	}

	@Test
	public void testParseCoverage03() {
		Assert.assertTrue( this.parseWorker( "default_doc_fail1" ) );
	}
	
	@Test
	public void testParse01() {
		Assert.assertTrue( this.parseWorker( "doc_test_01" ) );
	}
	
	@Test
	public void testValidateOk01() {
		Assert.assertTrue( this.validateWorker( "doc_test_01", VALID, NO_EXCEPTION) );
	}
	
	@Test
	public void testValidateKo02() {
		Assert.assertTrue(  this.validateWorker( "doc_test_02_ko", NOT_VALID, NO_EXCEPTION) );
	}
	
}
