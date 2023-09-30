package org.fugerit.java.doc.mod.poi;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.IndexedColorMap;

public class WorkbookHelper implements Serializable, Closeable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6808451802334455726L;

	public WorkbookHelper(Workbook workbook, IndexedColorMap indexedColorMap) {
		super();
		this.workbook = workbook;
		this.indexedColorMap = indexedColorMap;
	}

	private Workbook workbook;

	private IndexedColorMap indexedColorMap;
	
	public Workbook getWorkbook() {
		return workbook;
	}

	public IndexedColorMap getIndexedColorMap() {
		return indexedColorMap;
	}

	@Override
	public void close() throws IOException {
		this.workbook.close();
	}

}
