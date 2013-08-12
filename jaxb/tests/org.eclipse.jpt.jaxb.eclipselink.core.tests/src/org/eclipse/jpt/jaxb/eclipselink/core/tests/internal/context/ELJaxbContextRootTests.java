/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.context;

import java.io.ByteArrayInputStream;
import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELJaxbContextRoot;

@SuppressWarnings("nls")
public class ELJaxbContextRootTests
		extends ELJaxbContextModelTestCase {
	
	public ELJaxbContextRootTests(String name) {
		super(name);
	}
	
	
	protected void addOxmFile(String fileName, String packageName) throws Exception {
		IFile oxmFile = getJavaProjectTestHarness().getProject().getFolder("src").getFile(fileName);
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append(CR);
		sb.append("<xml-bindings package-name=\"").append(packageName).append("\"").append(CR);
		sb.append("    xmlns=\"http://www.eclipse.org/eclipselink/xsds/persistence/oxm\"").append(CR);
		sb.append("    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"").append(CR);
		sb.append("    xsi:schemaLocation=\"http://www.eclipse.org/eclipselink/xsds/persistence/oxm http://www.eclipse.org/eclipselink/xsds/eclipselink_oxm_2_4.xsd\"").append(CR);
		sb.append("    version=\"2.4\"/>").append(CR);
		oxmFile.create(new ByteArrayInputStream(sb.toString().getBytes()), true, null);
	}
	
	public void testOxmFiles() throws Exception {
		ELJaxbContextRoot root = (ELJaxbContextRoot) getJaxbProject().getContextRoot();
		
		assertEquals(0, root.getOxmFilesSize());
		
		addOxmFile("oxm.xml", "test.oxm");
		
		assertEquals(1, root.getOxmFilesSize());
		assertNull(root.getOxmFile("fake.pkg"));
		assertNotNull(root.getOxmFile("test.oxm"));
		
		addOxmFile("oxm2.xml", "test.oxm");
		
		assertEquals(2, root.getOxmFilesSize());
		assertNotNull(root.getOxmFile("test.oxm"));
		
		addOxmFile("oxm3.xml", "test.oxm2");
		assertEquals(3, root.getOxmFilesSize());
		assertNotNull(root.getOxmFile("test.oxm2"));
		
		getJavaProjectTestHarness().getProject().getFolder("src").getFile("oxm.xml").delete(true, null);
		
		assertEquals(2, root.getOxmFilesSize());
		assertNotNull(root.getOxmFile("test.oxm"));
		
		getJavaProjectTestHarness().getProject().getFolder("src").getFile("oxm2.xml").delete(true, null);
		
		assertEquals(1, root.getOxmFilesSize());
		assertNull(root.getOxmFile("test.oxm"));
		
		getJavaProjectTestHarness().getProject().getFolder("src").getFile("oxm3.xml").delete(true, null);
		
		assertEquals(0, root.getOxmFilesSize());
	}
}
