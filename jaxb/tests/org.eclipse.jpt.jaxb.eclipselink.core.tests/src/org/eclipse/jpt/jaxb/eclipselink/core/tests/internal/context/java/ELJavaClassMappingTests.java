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

import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.jaxb.core.context.JaxbClass;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDescription;
import org.eclipse.jpt.jaxb.eclipselink.core.ELJaxbPlatform;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELClassMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlDiscriminatorNodeAnnotation;
import org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.context.ELJaxbContextModelTestCase;


public class ELJavaClassMappingTests
		extends ELJaxbContextModelTestCase {
	
	public ELJavaClassMappingTests(String name) {
		super(name);
	}
	
	
	@Override
	protected JaxbPlatformDescription getPlatform() {
		return ELJaxbPlatform.VERSION_2_2;
	}
	
	public void testModifyXmlDiscriminatorNode() throws Exception {
		createClassWithXmlType();
		
		JaxbClass jaxbClass = (JaxbClass) CollectionTools.get(getContextRoot().getTypes(), 0);
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
		
		JaxbClass jaxbClass = (JaxbClass) CollectionTools.get(getContextRoot().getTypes(), 0);
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
}
