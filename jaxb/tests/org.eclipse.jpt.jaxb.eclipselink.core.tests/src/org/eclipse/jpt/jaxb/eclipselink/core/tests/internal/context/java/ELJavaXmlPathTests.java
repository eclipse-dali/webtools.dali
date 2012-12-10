/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.ArrayIterator;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.java.JavaClass;
import org.eclipse.jpt.jaxb.core.context.java.JavaClassMapping;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELXmlElementMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELXmlPath;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java.ELJavaXmlElementMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlPathAnnotation;
import org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.context.ELJaxbContextModelTestCase;

@SuppressWarnings("nls")
public class ELJavaXmlPathTests
		extends ELJaxbContextModelTestCase {
	
	public ELJavaXmlPathTests(String name) {
		super(name);
	}
	
	
	private ICompilationUnit createTypeWithXmlPath() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TYPE, ELJaxb.XML_PATH);
			}
			
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlPath");
			}
		});
	}
	
	
	public void testModifyValue() throws Exception {
		createTypeWithXmlPath();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		ELXmlElementMapping mapping = (ELXmlElementMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = mapping.getPersistentAttribute().getJavaResourceAttribute();
		ELXmlPath xpath = mapping.getXmlPath();
		XmlPathAnnotation annotation = (XmlPathAnnotation) resourceAttribute.getAnnotation(0, ELJaxb.XML_PATH);
		
		assertNull(annotation.getValue());
		assertNull(xpath.getValue());
		
		xpath.setValue("foo");
		
		assertEquals("foo", annotation.getValue());
		assertEquals("foo", xpath.getValue());
		
		xpath.setValue("");
		
		assertEquals("", annotation.getValue());
		assertEquals("", xpath.getValue());
		
		xpath.setValue(null);
		
		assertNull(annotation.getValue());
		assertNull(xpath.getValue());
	}

	public void testUpdateValue() throws Exception {
		createTypeWithXmlPath();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		ELJavaXmlElementMapping mapping = (ELJavaXmlElementMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = mapping.getPersistentAttribute().getJavaResourceAttribute();
		ELXmlPath xpath = mapping.getXmlPath();
		XmlPathAnnotation annotation = (XmlPathAnnotation) resourceAttribute.getAnnotation(0, ELJaxb.XML_PATH);
		
		assertNull(annotation.getValue());
		assertNull(xpath.getValue());
		
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				ELJavaXmlPathTests.this.setMemberValuePair(
						declaration, ELJaxb.XML_PATH, "foo");
			}
		});
		
		assertEquals("foo", annotation.getValue());
		assertEquals("foo", xpath.getValue());
		
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				ELJavaXmlPathTests.this.setMemberValuePair(
						declaration, ELJaxb.XML_PATH, "");
			}
		});
		
		assertEquals("", annotation.getValue());
		assertEquals("", xpath.getValue());
		
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				ELJavaXmlPathTests.this.removeMemberValuePair(
						declaration, ELJaxb.XML_PATH);
			}
		});
		
		assertNull(annotation.getValue());
		assertNull(xpath.getValue());
	}
}
