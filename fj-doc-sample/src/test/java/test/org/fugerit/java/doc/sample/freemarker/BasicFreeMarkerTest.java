package test.org.fugerit.java.doc.sample.freemarker;

import java.io.InputStream;
import java.io.Reader;

import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.util.filterchain.MiniFilterChain;
import org.fugerit.java.doc.base.config.DocConfig;
import org.fugerit.java.doc.base.facade.DocFacade;
import org.fugerit.java.doc.base.model.DocBase;
import org.fugerit.java.doc.base.process.DocProcessConfig;
import org.fugerit.java.doc.base.process.DocProcessContext;
import org.fugerit.java.doc.base.process.DocProcessData;
import org.fugerit.java.doc.base.xml.DocValidator;

import test.org.fugerit.java.doc.sample.facade.BasicFacadeTest;

public class BasicFreeMarkerTest extends BasicFacadeTest {

	public BasicFreeMarkerTest() {
		this( "basic", DocConfig.TYPE_PDF, DocConfig.TYPE_XLS, DocConfig.TYPE_RTF, DocConfig.TYPE_HTML );
	}

	protected BasicFreeMarkerTest(String nameBase, String... typeList) {
		super(nameBase, typeList);
	}

	private static DocProcessConfig PROCESS_CONFIG = null;

	
	static {
		try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( "config/doc-process-sample.xml" ) ) {
			PROCESS_CONFIG = DocProcessConfig.loadConfig( is );
		} catch (Exception e) {
			throw new RuntimeException( e ); 
		}
	}

	@Override
	protected DocBase getDocBase() throws Exception {
		MiniFilterChain chain = PROCESS_CONFIG.getChainCache( this.getNameBase() );
		DocProcessContext context = new DocProcessContext();
		DocProcessData data = new DocProcessData();
		int res = chain.apply( context , data );
		logger.info( "RES {} ", res );
		DocBase docBase = null;
		try ( Reader input = data.getCurrentXmlReader() ) {
			DocValidator.logValidation( input , logger );
		}
		try ( Reader input = data.getCurrentXmlReader() ) {
			 docBase = DocFacade.parse( input );
		}
		return docBase;
	}
	
}
