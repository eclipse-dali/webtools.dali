/*******************************************************************************
 *  Copyright (c) 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.context.oxm;

import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELJaxbContextRoot;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmFile;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlSeeAlso;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlSeeAlso;

public class OxmXmlSeeAlsoTests
		extends OxmContextModelTestCase {
	
	public OxmXmlSeeAlsoTests(String name) {
		super(name);
	}
	
	
	protected void addOxmFile() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append(CR);
		sb.append("<xml-bindings").append(CR);
		sb.append("    version=\"2.4\"").append(CR);
		sb.append("    xmlns=\"http://www.eclipse.org/eclipselink/xsds/persistence/oxm\"").append(CR);
		sb.append("    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"").append(CR);
		sb.append("    xsi:schemaLocation=\"http://www.eclipse.org/eclipselink/xsds/persistence/oxm http://www.eclipse.org/eclipselink/xsds/eclipselink_oxm_2_4.xsd\"").append(CR);
		sb.append("    package-name=\"").append(PACKAGE_NAME).append("\">").append(CR);
		sb.append("    <java-types>").append(CR);
		sb.append("        <java-type name=\"").append(TYPE_NAME).append("\">").append(CR);
		sb.append("            <xml-see-also></xml-see-also>").append(CR);
		sb.append("        </java-type>").append(CR);
		sb.append("    </java-types>").append(CR);
		sb.append("</xml-bindings>").append(CR);
		addOxmFile("oxm.xml", sb);
	}
	
	public void testUpdateClasses() throws Exception {
		addOxmFile();
		ELJaxbContextRoot root = (ELJaxbContextRoot) getJaxbProject().getContextRoot();
		OxmFile oxmFile = root.getOxmFile(PACKAGE_NAME);
		OxmXmlSeeAlso xmlSeeAlso = oxmFile.getXmlBindings().getJavaType(0).getXmlSeeAlso();
		JptXmlResource oxmResource = oxmFile.getOxmResource();
		EXmlBindings eXmlBindings = (EXmlBindings) oxmResource.getRootObject();
		EXmlSeeAlso eXmlSeeAlso = eXmlBindings.getJavaTypes().get(0).getXmlSeeAlso();
		
		assertNotNull(eXmlSeeAlso);
		assertNotNull(xmlSeeAlso);
		assertTrue(StringTools.isBlank(eXmlSeeAlso.getClasses()));
		assertTrue(IterableTools.isEmpty(eXmlSeeAlso.getClassesList()));
		assertTrue(IterableTools.isEmpty(xmlSeeAlso.getClasses()));
		
		eXmlSeeAlso.setClasses("foo");
		oxmResource.save();
		
		assertEquals("foo", eXmlSeeAlso.getClasses());
		assertEquals(1, IterableTools.size(eXmlSeeAlso.getClassesList()));
		assertEquals("foo", IterableTools.get(eXmlSeeAlso.getClassesList(), 0));
		assertEquals(1, IterableTools.size(xmlSeeAlso.getClasses()));
		assertEquals("foo", IterableTools.get(xmlSeeAlso.getClasses(), 0));
		
		eXmlSeeAlso.setClasses("bar baz");
		oxmResource.save();
		
		assertEquals("bar baz", eXmlSeeAlso.getClasses());
		assertEquals(2, IterableTools.size(eXmlSeeAlso.getClassesList()));
		assertEquals("bar", IterableTools.get(eXmlSeeAlso.getClassesList(), 0));
		assertEquals("baz", IterableTools.get(eXmlSeeAlso.getClassesList(), 1));
		assertEquals(2, IterableTools.size(xmlSeeAlso.getClasses()));
		assertEquals("bar", IterableTools.get(xmlSeeAlso.getClasses(), 0));
		assertEquals("baz", IterableTools.get(xmlSeeAlso.getClasses(), 1));
		
		eXmlSeeAlso.setClasses(null);
		oxmResource.save();
		
		assertTrue(StringTools.isBlank(eXmlSeeAlso.getClasses()));
		assertEquals(0, IterableTools.size(eXmlSeeAlso.getClassesList()));
	}
	
	public void testModifyClasses() throws Exception {
		addOxmFile();
		ELJaxbContextRoot root = (ELJaxbContextRoot) getJaxbProject().getContextRoot();
		OxmFile oxmFile = root.getOxmFile(PACKAGE_NAME);
		OxmXmlSeeAlso xmlSeeAlso = oxmFile.getXmlBindings().getJavaType(0).getXmlSeeAlso();
		JptXmlResource oxmResource = oxmFile.getOxmResource();
		EXmlBindings eXmlBindings = (EXmlBindings) oxmResource.getRootObject();
		EXmlSeeAlso eXmlSeeAlso = eXmlBindings.getJavaTypes().get(0).getXmlSeeAlso();
		
		assertNotNull(eXmlSeeAlso);
		assertNotNull(xmlSeeAlso);
		assertTrue(StringTools.isBlank(eXmlSeeAlso.getClasses()));
		assertTrue(IterableTools.isEmpty(eXmlSeeAlso.getClassesList()));
		assertTrue(IterableTools.isEmpty(xmlSeeAlso.getClasses()));
		
		xmlSeeAlso.addClass(0, "foo");
		oxmResource.save();
		
		assertEquals("foo", eXmlSeeAlso.getClasses());
		assertEquals(1, IterableTools.size(eXmlSeeAlso.getClassesList()));
		assertEquals("foo", IterableTools.get(eXmlSeeAlso.getClassesList(), 0));
		assertEquals(1, IterableTools.size(xmlSeeAlso.getClasses()));
		assertEquals("foo", IterableTools.get(xmlSeeAlso.getClasses(), 0));
		
		xmlSeeAlso.addClass(0, "bar");
		oxmResource.save();
		
		assertEquals("bar foo", eXmlSeeAlso.getClasses());
		assertEquals(2, IterableTools.size(eXmlSeeAlso.getClassesList()));
		assertEquals("bar", IterableTools.get(eXmlSeeAlso.getClassesList(), 0));
		assertEquals("foo", IterableTools.get(eXmlSeeAlso.getClassesList(), 1));
		assertEquals(2, IterableTools.size(xmlSeeAlso.getClasses()));
		assertEquals("bar", IterableTools.get(xmlSeeAlso.getClasses(), 0));
		assertEquals("foo", IterableTools.get(xmlSeeAlso.getClasses(), 1));
		
		xmlSeeAlso.moveClass(0, 1);
		oxmResource.save();
		
		assertEquals("foo bar", eXmlSeeAlso.getClasses());
		assertEquals(2, IterableTools.size(eXmlSeeAlso.getClassesList()));
		assertEquals("foo", IterableTools.get(eXmlSeeAlso.getClassesList(), 0));
		assertEquals("bar", IterableTools.get(eXmlSeeAlso.getClassesList(), 1));
		assertEquals(2, IterableTools.size(xmlSeeAlso.getClasses()));
		assertEquals("foo", IterableTools.get(xmlSeeAlso.getClasses(), 0));
		assertEquals("bar", IterableTools.get(xmlSeeAlso.getClasses(), 1));
		
		xmlSeeAlso.removeClass(0);
		oxmResource.save();
		
		assertEquals("bar", eXmlSeeAlso.getClasses());
		assertEquals(1, IterableTools.size(eXmlSeeAlso.getClassesList()));
		assertEquals("bar", IterableTools.get(eXmlSeeAlso.getClassesList(), 0));
		assertEquals(1, IterableTools.size(xmlSeeAlso.getClasses()));
		assertEquals("bar", IterableTools.get(xmlSeeAlso.getClasses(), 0));
		
		xmlSeeAlso.removeClass(0);
		oxmResource.save();
		
		assertTrue(StringTools.isBlank(eXmlSeeAlso.getClasses()));
		assertTrue(IterableTools.isEmpty(eXmlSeeAlso.getClassesList()));
		assertTrue(IterableTools.isEmpty(xmlSeeAlso.getClasses()));
	}
}
