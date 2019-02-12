/*******************************************************************************
 *  Copyright (c) 2007, 2011 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License 2.0 
 *  which accompanies this distribution, and is available at 
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context.persistence;

import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class MappingFileRefTests extends ContextModelTestCase
{
	public MappingFileRefTests(String name) {
		super(name);
	}
	
	protected MappingFileRef mappingFileRef() {
		return getPersistenceUnit().getMappingFileRefs().iterator().next();
	}
	
	public void testUpdateFileName() {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// add mapping file ref
		XmlMappingFileRef xmlFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		xmlFileRef.setFileName("foo.xml");
		xmlPersistenceUnit.getMappingFiles().add(xmlFileRef);
		MappingFileRef fileRef = persistenceUnit.getSpecifiedMappingFileRefs().iterator().next();
		
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
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// add mapping file ref
		XmlMappingFileRef xmlFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		xmlFileRef.setFileName("foo.xml");
		xmlPersistenceUnit.getMappingFiles().add(xmlFileRef);
		MappingFileRef fileRef = persistenceUnit.getSpecifiedMappingFileRefs().iterator().next();
		
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
