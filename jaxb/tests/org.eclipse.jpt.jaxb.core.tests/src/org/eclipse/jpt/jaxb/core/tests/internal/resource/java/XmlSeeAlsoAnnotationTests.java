/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal.resource.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSeeAlsoAnnotation;

@SuppressWarnings("nls")
public class XmlSeeAlsoAnnotationTests
		extends JaxbJavaResourceModelTestCase {

	public XmlSeeAlsoAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestXmlSeeAlso() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JAXB.XML_SEE_ALSO);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlSeeAlso");
			}
		});
	}

	private ICompilationUnit createTestXmlSeeAlsoWithValue() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JAXB.XML_SEE_ALSO);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlSeeAlso(value = {Foo.class, Bar.class})");
			}
		});
	}


	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestXmlSeeAlso();
		JavaResourceType resourceType = buildJavaResourceType(cu); 

		XmlSeeAlsoAnnotation xmlSeeAlsoAnnotation = (XmlSeeAlsoAnnotation) resourceType.getAnnotation(JAXB.XML_SEE_ALSO);
		assertTrue(xmlSeeAlsoAnnotation != null);
		assertEquals(0, xmlSeeAlsoAnnotation.getClassesSize());
	}

	public void testGetClasses() throws Exception {
		ICompilationUnit cu = this.createTestXmlSeeAlsoWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 

		XmlSeeAlsoAnnotation xmlSeeAlsoAnnotation = (XmlSeeAlsoAnnotation) resourceType.getAnnotation(JAXB.XML_SEE_ALSO);
		assertTrue(xmlSeeAlsoAnnotation != null);
		ListIterator<String> classes = xmlSeeAlsoAnnotation.getClasses().iterator();
		assertEquals("Foo", classes.next());
		assertEquals("Bar", classes.next());
	}

	public void testGetClassesSize() throws Exception {
		ICompilationUnit cu = this.createTestXmlSeeAlsoWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 

		XmlSeeAlsoAnnotation xmlSeeAlsoAnnotation = (XmlSeeAlsoAnnotation) resourceType.getAnnotation(JAXB.XML_SEE_ALSO);
		assertTrue(xmlSeeAlsoAnnotation != null);
		assertEquals(2, xmlSeeAlsoAnnotation.getClassesSize());
	}

	public void testAddClass() throws Exception {
		ICompilationUnit cu = this.createTestXmlSeeAlso();
		JavaResourceType resourceType = buildJavaResourceType(cu); 

		XmlSeeAlsoAnnotation xmlSeeAlsoAnnotation = (XmlSeeAlsoAnnotation) resourceType.getAnnotation(JAXB.XML_SEE_ALSO);
		assertTrue(xmlSeeAlsoAnnotation != null);

		xmlSeeAlsoAnnotation.addClass("Fooo");
		xmlSeeAlsoAnnotation.addClass("Barrr");

		assertSourceContains("@XmlSeeAlso({ Fooo.class, Barrr.class })", cu);
	}

	public void testAddClassIndex() throws Exception {
		ICompilationUnit cu = this.createTestXmlSeeAlso();
		JavaResourceType resourceType = buildJavaResourceType(cu); 

		XmlSeeAlsoAnnotation xmlSeeAlsoAnnotation = (XmlSeeAlsoAnnotation) resourceType.getAnnotation(JAXB.XML_SEE_ALSO);
		assertTrue(xmlSeeAlsoAnnotation != null);

		xmlSeeAlsoAnnotation.addClass(0, "Fooo");
		xmlSeeAlsoAnnotation.addClass(0, "Barr");
		xmlSeeAlsoAnnotation.addClass(1, "Blah");

		assertSourceContains("@XmlSeeAlso({ Barr.class, Blah.class, Fooo.class })", cu);
	}
	
	public void testRemoveClass() throws Exception {
		ICompilationUnit cu = this.createTestXmlSeeAlsoWithValue();
		JavaResourceType resourceType = buildJavaResourceType(cu); 

		XmlSeeAlsoAnnotation xmlSeeAlsoAnnotation = (XmlSeeAlsoAnnotation) resourceType.getAnnotation(JAXB.XML_SEE_ALSO);
		assertTrue(xmlSeeAlsoAnnotation != null);

		xmlSeeAlsoAnnotation.removeClass(0);
		assertSourceContains("@XmlSeeAlso(value = Bar.class)", cu);

		xmlSeeAlsoAnnotation.removeClass(0);
		assertSourceContains("@XmlSeeAlso", cu);
		assertSourceDoesNotContain("value", cu);
	}

	public void testMoveClass() throws Exception {
		ICompilationUnit cu = this.createTestXmlSeeAlso();
		JavaResourceType resourceType = buildJavaResourceType(cu); 

		XmlSeeAlsoAnnotation xmlSeeAlsoAnnotation = (XmlSeeAlsoAnnotation) resourceType.getAnnotation(JAXB.XML_SEE_ALSO);
		assertTrue(xmlSeeAlsoAnnotation != null);

		xmlSeeAlsoAnnotation.addClass("Fooo");
		xmlSeeAlsoAnnotation.addClass("Barr");
		xmlSeeAlsoAnnotation.addClass("Blah");
		assertSourceContains("@XmlSeeAlso({ Fooo.class, Barr.class, Blah.class })", cu);

		xmlSeeAlsoAnnotation.moveClass(0, 1);
		assertSourceContains("@XmlSeeAlso({ Barr.class, Fooo.class, Blah.class })", cu);

		xmlSeeAlsoAnnotation.moveClass(2, 1);
		assertSourceContains("@XmlSeeAlso({ Barr.class, Blah.class, Fooo.class })", cu);
	}
}
