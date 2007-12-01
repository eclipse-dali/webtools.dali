/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context.persistence;

import org.eclipse.jpt.core.internal.context.base.IMappingFileRef;
import org.eclipse.jpt.core.internal.context.base.IPersistenceUnit;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

public class MappingFileRefTests extends ContextModelTestCase
{
	public MappingFileRefTests(String name) {
		super(name);
	}
	
	protected IMappingFileRef mappingFileRef() {
		return persistenceUnit().mappingFileRefs().next();
	}
	
	public void testUpdateFileName() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		IPersistenceUnit persistenceUnit = persistenceUnit();
		
		// add mapping file ref
		XmlMappingFileRef xmlFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		xmlFileRef.setFileName("foo.xml");
		xmlPersistenceUnit.getMappingFiles().add(xmlFileRef);
		IMappingFileRef fileRef = persistenceUnit.mappingFileRefs().next();
		
		// test that file names are initially equal
		assertEquals(fileRef.getFileName(), xmlFileRef.getFileName());
		
		// set xml to different file name, test equality
		xmlFileRef.setFileName("bar.xml");
		
		assertEquals(fileRef.getFileName(), xmlFileRef.getFileName());
		
		// set file name to empty string, test equality
		xmlFileRef.setFileName("");
		
		assertEquals(fileRef.getFileName(), xmlFileRef.getFileName());
		
		// set file name to null, test equality
		xmlFileRef.setFileName(null);
		
		assertEquals(fileRef.getFileName(), xmlFileRef.getFileName());
		
		// set file name back to non-null, test equality
		xmlFileRef.setFileName("baz.xml");
		
		assertEquals(fileRef.getFileName(), xmlFileRef.getFileName());
	}
	
	public void testModifyFileName() {
		XmlPersistenceUnit xmlPersistenceUnit = xmlPersistenceUnit();
		IPersistenceUnit persistenceUnit = persistenceUnit();
		
		// add mapping file ref
		XmlMappingFileRef xmlFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		xmlFileRef.setFileName("foo.xml");
		xmlPersistenceUnit.getMappingFiles().add(xmlFileRef);
		IMappingFileRef fileRef = persistenceUnit.mappingFileRefs().next();
		
		// test that file names are initially equal
		assertEquals(fileRef.getFileName(), xmlFileRef.getFileName());
		
		// set context to different file name, test equality
		fileRef.setFileName("bar.xml");
		
		assertEquals(fileRef.getFileName(), xmlFileRef.getFileName());
		
		// set file name to empty string, test equality
		fileRef.setFileName("");
		
		assertEquals(fileRef.getFileName(), xmlFileRef.getFileName());
		
		// set file name to null, test equality
		fileRef.setFileName(null);
		
		assertEquals(fileRef.getFileName(), xmlFileRef.getFileName());
		
		// set file name back to non-null, test equality
		fileRef.setFileName("baz.xml");
		
		assertEquals(fileRef.getFileName(), xmlFileRef.getFileName());
	}
}
