/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentClass;
import org.eclipse.jpt.jaxb.core.context.XmlSeeAlso;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSeeAlsoAnnotation;
import org.eclipse.jpt.jaxb.core.tests.internal.context.JaxbContextModelTestCase;


public class GenericJavaXmlSeeAlsoTests
		extends JaxbContextModelTestCase {
	
	public GenericJavaXmlSeeAlsoTests(String name) {
		super(name);
	}
	
	
	private ICompilationUnit createAnnotatedPersistentClassWithXmlSeeAlso() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TYPE, JAXB.XML_SEE_ALSO);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType" + CR);
				sb.append("@XmlSeeAlso");
			}
		});
	}
	
	public void testModifyClasses() throws Exception {
		createAnnotatedPersistentClassWithXmlSeeAlso();
		JaxbPersistentClass contextPersistentClass = getContextRoot().getPersistentClass(FULLY_QUALIFIED_TYPE_NAME);
		XmlSeeAlso contextXmlSeeAlso = contextPersistentClass.getXmlSeeAlso();
		JavaResourceType resourceType = contextPersistentClass.getJavaResourceType();
		
		assertEquals(0, contextXmlSeeAlso.getClassesSize());
		
		// add a class
		contextXmlSeeAlso.addClass(0, "foo");
		XmlSeeAlsoAnnotation annotation = (XmlSeeAlsoAnnotation) resourceType.getAnnotation(JAXB.XML_SEE_ALSO);
		assertEquals(1, annotation.getClassesSize());
		assertTrue(CollectionTools.contains(annotation.getClasses(), "foo"));
		assertFalse(CollectionTools.contains(annotation.getClasses(), "bar"));
		
		// add another
		contextXmlSeeAlso.addClass(0, "bar");
		annotation = (XmlSeeAlsoAnnotation) resourceType.getAnnotation(JAXB.XML_SEE_ALSO);
		assertEquals(2, annotation.getClassesSize());
		assertTrue(CollectionTools.contains(annotation.getClasses(), "foo"));
		assertTrue(CollectionTools.contains(annotation.getClasses(), "bar"));
		
		 // remove one
		contextXmlSeeAlso.removeClass(1);
		annotation = (XmlSeeAlsoAnnotation) resourceType.getAnnotation(JAXB.XML_SEE_ALSO);
		assertEquals(1, annotation.getClassesSize());
		assertFalse(CollectionTools.contains(annotation.getClasses(), "foo"));
		assertTrue(CollectionTools.contains(annotation.getClasses(), "bar"));
		
		// remove the other
		contextXmlSeeAlso.removeClass(0);
		annotation = (XmlSeeAlsoAnnotation) resourceType.getAnnotation(JAXB.XML_SEE_ALSO);
		assertEquals(0, annotation.getClassesSize());
		assertFalse(CollectionTools.contains(annotation.getClasses(), "bar"));
		assertFalse(CollectionTools.contains(annotation.getClasses(), "foo"));
	}
	
	public void testUpdateClasses() throws Exception {
		createAnnotatedPersistentClassWithXmlSeeAlso();
		JaxbPersistentClass contextPersistentClass = getContextRoot().getPersistentClass(FULLY_QUALIFIED_TYPE_NAME);
		XmlSeeAlso contextXmlSeeAlso = contextPersistentClass.getXmlSeeAlso();
		JavaResourceType resourceType = contextPersistentClass.getJavaResourceType();
		AnnotatedElement annotatedElement = annotatedElement(resourceType);
		
		assertEquals(0, contextXmlSeeAlso.getClassesSize());
		
		// add a class
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSeeAlsoTests.this.addClass(declaration, 0, "foo");
			}
		});
		assertEquals(1, contextXmlSeeAlso.getClassesSize());
		assertTrue(CollectionTools.contains(contextXmlSeeAlso.getClasses(), "foo"));
		assertFalse(CollectionTools.contains(contextXmlSeeAlso.getClasses(), "bar"));
		
		// add another
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSeeAlsoTests.this.addClass(declaration, 0, "bar");
			}
		});
		assertEquals(2, contextXmlSeeAlso.getClassesSize());
		assertTrue(CollectionTools.contains(contextXmlSeeAlso.getClasses(), "foo"));
		assertTrue(CollectionTools.contains(contextXmlSeeAlso.getClasses(), "bar"));
		
		 // remove one
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSeeAlsoTests.this.removeClass(declaration, 1);
			}
		});
		assertEquals(1, contextXmlSeeAlso.getClassesSize());
		assertFalse(CollectionTools.contains(contextXmlSeeAlso.getClasses(), "foo"));
		assertTrue(CollectionTools.contains(contextXmlSeeAlso.getClasses(), "bar"));
		
		// remove the other
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSeeAlsoTests.this.removeClass(declaration, 0);
			}
		});
		assertEquals(0, contextXmlSeeAlso.getClassesSize());
		assertFalse(CollectionTools.contains(contextXmlSeeAlso.getClasses(), "bar"));
		assertFalse(CollectionTools.contains(contextXmlSeeAlso.getClasses(), "foo"));
	}
	
	protected void addClass(ModifiedDeclaration declaration, int index, String clazz) {
		addArrayElement(declaration, JAXB.XML_SEE_ALSO, index, JAXB.XML_SEE_ALSO__VALUE, newTypeLiteral(declaration.getAst(), clazz));		
	}
	
	protected void removeClass(ModifiedDeclaration declaration, int index) {
		removeArrayElement((NormalAnnotation) getXmlSeeAlsoAnnotation(declaration), JAXB.XML_SEE_ALSO__VALUE, index);
	}
	
	protected Annotation getXmlSeeAlsoAnnotation(ModifiedDeclaration declaration) {
		return declaration.getAnnotationNamed(JAXB.XML_SEE_ALSO);
	}
}
