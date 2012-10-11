/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
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
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.ArrayIterator;
import org.eclipse.jpt.jaxb.core.context.JaxbClass;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java.ELJavaXmlInverseReferenceMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlInverseReferenceAnnotation;
import org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.context.ELJaxbContextModelTestCase;


public class ELJavaXmlInverseReferenceMappingTests
		extends ELJaxbContextModelTestCase {
	
	public ELJavaXmlInverseReferenceMappingTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTypeWithXmlInverseReference() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TYPE, ELJaxb.XML_INVERSE_REFERENCE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType");
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlInverseReference");
			}
		});
	}
	
	
	public void testModifyMappedBy() throws Exception {
		createTypeWithXmlInverseReference();
		
		JaxbClass jaxbClass = (JaxbClass) IterableTools.get(getContextRoot().getTypes(), 0);
		JaxbClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		ELJavaXmlInverseReferenceMapping mapping = (ELJavaXmlInverseReferenceMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = mapping.getPersistentAttribute().getJavaResourceAttribute();
		XmlInverseReferenceAnnotation annotation = (XmlInverseReferenceAnnotation) resourceAttribute.getAnnotation(ELJaxb.XML_INVERSE_REFERENCE);
		
		assertNull(annotation.getMappedBy());
		assertNull(mapping.getMappedBy());
		
		mapping.setMappedBy("foo");
		
		assertEquals("foo", annotation.getMappedBy());
		assertEquals("foo", mapping.getMappedBy());
		
		mapping.setMappedBy(null);
		
		assertNull(annotation.getMappedBy());
		assertNull(mapping.getMappedBy());
	}

	public void testUpdateMappedBy() throws Exception {
		createTypeWithXmlInverseReference();
		
		JaxbClass jaxbClass = (JaxbClass) IterableTools.get(getContextRoot().getTypes(), 0);
		JaxbClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		ELJavaXmlInverseReferenceMapping mapping = (ELJavaXmlInverseReferenceMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = mapping.getPersistentAttribute().getJavaResourceAttribute();
		XmlInverseReferenceAnnotation annotation = (XmlInverseReferenceAnnotation) resourceAttribute.getAnnotation(ELJaxb.XML_INVERSE_REFERENCE);
		
		assertNull(annotation.getMappedBy());
		assertNull(mapping.getMappedBy());
		
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				ELJavaXmlInverseReferenceMappingTests.this.addMemberValuePair(
						(MarkerAnnotation) declaration.getAnnotationNamed(ELJaxb.XML_INVERSE_REFERENCE),
						ELJaxb.XML_INVERSE_REFERENCE__MAPPED_BY, "foo");
			}
		});
		
		assertEquals("foo", annotation.getMappedBy());
		assertEquals("foo", mapping.getMappedBy());
		
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation annotation = (NormalAnnotation) declaration.getAnnotationNamed(ELJaxb.XML_INVERSE_REFERENCE);
				ELJavaXmlInverseReferenceMappingTests.this.values(annotation).remove(0);
			}
		});
		
		assertNull(annotation.getMappedBy());
		assertNull(mapping.getMappedBy());
	}
}
