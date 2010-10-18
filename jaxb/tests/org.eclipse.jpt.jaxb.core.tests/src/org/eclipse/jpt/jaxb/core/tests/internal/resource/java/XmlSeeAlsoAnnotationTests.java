/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal.resource.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSeeAlsoAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class XmlSeeAlsoAnnotationTests extends JaxbJavaResourceModelTestCase {


	public XmlSeeAlsoAnnotationTests(String name) {
		super(name);
	}

	private ICompilationUnit createTestXmlSeeAlso() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_SEE_ALSO);
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
				return new ArrayIterator<String>(JAXB.XML_SEE_ALSO);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlSeeAlso(value = {Foo.class, Bar.class})");
			}
		});
	}


	public void testGetNull() throws Exception {
		ICompilationUnit cu = this.createTestXmlSeeAlso();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 

		XmlSeeAlsoAnnotation xmlSeeAlsoAnnotation = (XmlSeeAlsoAnnotation) typeResource.getAnnotation(JAXB.XML_SEE_ALSO);
		assertTrue(xmlSeeAlsoAnnotation != null);
		assertEquals(0, xmlSeeAlsoAnnotation.getClassesSize());
	}

	public void testGetClasses() throws Exception {
		ICompilationUnit cu = this.createTestXmlSeeAlsoWithValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 

		XmlSeeAlsoAnnotation xmlSeeAlsoAnnotation = (XmlSeeAlsoAnnotation) typeResource.getAnnotation(JAXB.XML_SEE_ALSO);
		assertTrue(xmlSeeAlsoAnnotation != null);
		ListIterator<String> classes = xmlSeeAlsoAnnotation.getClasses().iterator();
		assertEquals("Foo", classes.next());
		assertEquals("Bar", classes.next());
	}

	public void testGetClassesSize() throws Exception {
		ICompilationUnit cu = this.createTestXmlSeeAlsoWithValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 

		XmlSeeAlsoAnnotation xmlSeeAlsoAnnotation = (XmlSeeAlsoAnnotation) typeResource.getAnnotation(JAXB.XML_SEE_ALSO);
		assertTrue(xmlSeeAlsoAnnotation != null);
		assertEquals(2, xmlSeeAlsoAnnotation.getClassesSize());
	}

	public void testAddClass() throws Exception {
		ICompilationUnit cu = this.createTestXmlSeeAlso();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 

		XmlSeeAlsoAnnotation xmlSeeAlsoAnnotation = (XmlSeeAlsoAnnotation) typeResource.getAnnotation(JAXB.XML_SEE_ALSO);
		assertTrue(xmlSeeAlsoAnnotation != null);

		xmlSeeAlsoAnnotation.addClass("Fooo");
		xmlSeeAlsoAnnotation.addClass("Barrr");

		assertSourceContains("@XmlSeeAlso({ Fooo.class, Barrr.class })", cu);
	}

	public void testAddClassIndex() throws Exception {
		ICompilationUnit cu = this.createTestXmlSeeAlso();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 

		XmlSeeAlsoAnnotation xmlSeeAlsoAnnotation = (XmlSeeAlsoAnnotation) typeResource.getAnnotation(JAXB.XML_SEE_ALSO);
		assertTrue(xmlSeeAlsoAnnotation != null);

		xmlSeeAlsoAnnotation.addClass(0, "Fooo");
		xmlSeeAlsoAnnotation.addClass(0, "Barr");
		xmlSeeAlsoAnnotation.addClass(1, "Blah");

		assertSourceContains("@XmlSeeAlso({ Barr.class, Blah.class, Fooo.class })", cu);
	}

	public void testRemoveClass() throws Exception {
		ICompilationUnit cu = this.createTestXmlSeeAlsoWithValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 

		XmlSeeAlsoAnnotation xmlSeeAlsoAnnotation = (XmlSeeAlsoAnnotation) typeResource.getAnnotation(JAXB.XML_SEE_ALSO);
		assertTrue(xmlSeeAlsoAnnotation != null);

		xmlSeeAlsoAnnotation.removeClass("Foo");
		assertSourceContains("@XmlSeeAlso(value = Bar.class)", cu);

		xmlSeeAlsoAnnotation.removeClass("Bar");
		assertSourceContains("@XmlSeeAlso", cu);
		assertSourceDoesNotContain("value", cu);
	}

	public void testRemoveClassIndex() throws Exception {
		ICompilationUnit cu = this.createTestXmlSeeAlsoWithValue();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 

		XmlSeeAlsoAnnotation xmlSeeAlsoAnnotation = (XmlSeeAlsoAnnotation) typeResource.getAnnotation(JAXB.XML_SEE_ALSO);
		assertTrue(xmlSeeAlsoAnnotation != null);

		xmlSeeAlsoAnnotation.removeClass(0);
		assertSourceContains("@XmlSeeAlso(value = Bar.class)", cu);

		xmlSeeAlsoAnnotation.removeClass(0);
		assertSourceContains("@XmlSeeAlso", cu);
		assertSourceDoesNotContain("value", cu);
	}

	public void testMoveClass() throws Exception {
		ICompilationUnit cu = this.createTestXmlSeeAlso();
		JavaResourcePersistentType typeResource = buildJavaTypeResource(cu); 

		XmlSeeAlsoAnnotation xmlSeeAlsoAnnotation = (XmlSeeAlsoAnnotation) typeResource.getAnnotation(JAXB.XML_SEE_ALSO);
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
