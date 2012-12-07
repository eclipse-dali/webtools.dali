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
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java.ELJavaXmlAnyElementMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.context.ELJaxbContextModelTestCase;


public class ELJavaXmlAnyElementMappingTests
		extends ELJaxbContextModelTestCase {
	
	public ELJavaXmlAnyElementMappingTests(String name) {
		super(name);
	}
	
	
	private ICompilationUnit createTypeWithXmlAnyElement() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TYPE, JAXB.XML_ANY_ELEMENT);
			}
			
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlAnyElement");
			}
		});
	}
	
	
	public void testModifyXmlPath() throws Exception {
		createTypeWithXmlAnyElement();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		ELJavaXmlAnyElementMapping mapping = (ELJavaXmlAnyElementMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = mapping.getPersistentAttribute().getJavaResourceAttribute();
		
		assertEquals(0, IterableTools.size(resourceAttribute.getAnnotations(ELJaxb.XML_PATH)));
		assertNull(mapping.getXmlPath());
		
		mapping.addXmlPath();
		
		assertEquals(1, IterableTools.size(resourceAttribute.getAnnotations(ELJaxb.XML_PATH)));
		assertNotNull(mapping.getXmlPath());
		
		mapping.removeXmlPath();
		
		assertEquals(0, IterableTools.size(resourceAttribute.getAnnotations(ELJaxb.XML_PATH)));
		assertNull(mapping.getXmlPath());
	}
	
	public void testUpdateXmlPath() throws Exception {
		createTypeWithXmlAnyElement();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		ELJavaXmlAnyElementMapping mapping = (ELJavaXmlAnyElementMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = mapping.getPersistentAttribute().getJavaResourceAttribute();
		
		assertEquals(0, IterableTools.size(resourceAttribute.getAnnotations(ELJaxb.XML_PATH)));
		assertNull(mapping.getXmlPath());
		
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				ELJavaXmlAnyElementMappingTests.this.addMarkerAnnotation(
						declaration.getDeclaration(), ELJaxb.XML_PATH);
			}
		});
		
		assertEquals(1, IterableTools.size(resourceAttribute.getAnnotations(ELJaxb.XML_PATH)));
		assertNotNull(mapping.getXmlPath());
		
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				ELJavaXmlAnyElementMappingTests.this.removeAnnotation(declaration, ELJaxb.XML_PATH);
			}
		});
		
		assertEquals(0, IterableTools.size(resourceAttribute.getAnnotations(ELJaxb.XML_PATH)));
		assertNull(mapping.getXmlPath());
	}
}
