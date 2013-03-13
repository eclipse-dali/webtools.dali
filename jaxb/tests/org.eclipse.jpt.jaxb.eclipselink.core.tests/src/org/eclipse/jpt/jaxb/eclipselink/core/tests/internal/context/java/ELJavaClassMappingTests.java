/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.context.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jaxb.core.context.java.JavaClass;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELClassMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.v2_2.ELJaxb_2_2_PlatformDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlDiscriminatorNodeAnnotation;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlDiscriminatorValueAnnotation;
import org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.context.ELJaxbContextModelTestCase;


public class ELJavaClassMappingTests
		extends ELJaxbContextModelTestCase {
	
	public ELJavaClassMappingTests(String name) {
		super(name);
	}
	
	
	@Override
	protected String getPlatformID() {
		return ELJaxb_2_2_PlatformDefinition.ID;
	}
	
	public void testModifyXmlDiscriminatorNode() throws Exception {
		createClassWithXmlType();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		ELClassMapping classMapping = (ELClassMapping) jaxbClass.getMapping();
		JavaResourceType resourceType = jaxbClass.getJavaResourceType();
		
		XmlDiscriminatorNodeAnnotation annotation = 
				(XmlDiscriminatorNodeAnnotation) resourceType.getAnnotation(ELJaxb.XML_DISCRIMINATOR_NODE);
		assertNull(classMapping.getXmlDiscriminatorNode());
		assertNull(annotation);
		
		classMapping.addXmlDiscriminatorNode();
		annotation = (XmlDiscriminatorNodeAnnotation) resourceType.getAnnotation(ELJaxb.XML_DISCRIMINATOR_NODE);
		assertNotNull(classMapping.getXmlDiscriminatorNode());
		assertNotNull(annotation);
		
		classMapping.removeXmlDiscriminatorNode();
		annotation = (XmlDiscriminatorNodeAnnotation) resourceType.getAnnotation(ELJaxb.XML_DISCRIMINATOR_NODE);
		assertNull(classMapping.getXmlDiscriminatorNode());
		assertNull(annotation);
	}
	
	public void testUpdateXmlDiscriminatorNode() throws Exception {
		createClassWithXmlType();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		ELClassMapping classMapping = (ELClassMapping) jaxbClass.getMapping();
		JavaResourceType resourceType = jaxbClass.getJavaResourceType();
		
		XmlDiscriminatorNodeAnnotation annotation = 
				(XmlDiscriminatorNodeAnnotation) resourceType.getAnnotation(ELJaxb.XML_DISCRIMINATOR_NODE);
		assertNull(classMapping.getXmlDiscriminatorNode());
		assertNull(annotation);
		
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						ELJavaClassMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), ELJaxb.XML_DISCRIMINATOR_NODE);
					}
				});
		annotation = (XmlDiscriminatorNodeAnnotation) resourceType.getAnnotation(ELJaxb.XML_DISCRIMINATOR_NODE);
		assertNotNull(classMapping.getXmlDiscriminatorNode());
		assertNotNull(annotation);
		
		annotatedElement.edit(
				new Member.Editor() {
					public void edit(
						ModifiedDeclaration declaration) {
							ELJavaClassMappingTests.this.removeAnnotation(declaration, ELJaxb.XML_DISCRIMINATOR_NODE);
						}
					});
		annotation = (XmlDiscriminatorNodeAnnotation) resourceType.getAnnotation(ELJaxb.XML_DISCRIMINATOR_NODE);
		assertNull(classMapping.getXmlDiscriminatorNode());
		assertNull(annotation);
	}
	
	public void testModifyXmlDiscriminatorValue() throws Exception {
		createClassWithXmlType();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		ELClassMapping classMapping = (ELClassMapping) jaxbClass.getMapping();
		JavaResourceType resourceType = jaxbClass.getJavaResourceType();
		
		XmlDiscriminatorValueAnnotation annotation = 
				(XmlDiscriminatorValueAnnotation) resourceType.getAnnotation(ELJaxb.XML_DISCRIMINATOR_VALUE);
		assertNull(classMapping.getXmlDiscriminatorValue());
		assertNull(annotation);
		
		classMapping.addXmlDiscriminatorValue();
		annotation = (XmlDiscriminatorValueAnnotation) resourceType.getAnnotation(ELJaxb.XML_DISCRIMINATOR_VALUE);
		assertNotNull(classMapping.getXmlDiscriminatorValue());
		assertNotNull(annotation);
		
		classMapping.removeXmlDiscriminatorValue();
		annotation = (XmlDiscriminatorValueAnnotation) resourceType.getAnnotation(ELJaxb.XML_DISCRIMINATOR_VALUE);
		assertNull(classMapping.getXmlDiscriminatorValue());
		assertNull(annotation);
	}
	
	public void testUpdateXmlDiscriminatorValue() throws Exception {
		createClassWithXmlType();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		ELClassMapping classMapping = (ELClassMapping) jaxbClass.getMapping();
		JavaResourceType resourceType = jaxbClass.getJavaResourceType();
		
		XmlDiscriminatorValueAnnotation annotation = 
				(XmlDiscriminatorValueAnnotation) resourceType.getAnnotation(ELJaxb.XML_DISCRIMINATOR_VALUE);
		assertNull(classMapping.getXmlDiscriminatorValue());
		assertNull(annotation);
		
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						ELJavaClassMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), ELJaxb.XML_DISCRIMINATOR_VALUE);
					}
				});
		annotation = (XmlDiscriminatorValueAnnotation) resourceType.getAnnotation(ELJaxb.XML_DISCRIMINATOR_VALUE);
		assertNotNull(classMapping.getXmlDiscriminatorValue());
		assertNotNull(annotation);
		
		annotatedElement.edit(
				new Member.Editor() {
					public void edit(
						ModifiedDeclaration declaration) {
							ELJavaClassMappingTests.this.removeAnnotation(declaration, ELJaxb.XML_DISCRIMINATOR_VALUE);
						}
					});
		annotation = (XmlDiscriminatorValueAnnotation) resourceType.getAnnotation(ELJaxb.XML_DISCRIMINATOR_VALUE);
		assertNull(classMapping.getXmlDiscriminatorValue());
		assertNull(annotation);
	}
}
