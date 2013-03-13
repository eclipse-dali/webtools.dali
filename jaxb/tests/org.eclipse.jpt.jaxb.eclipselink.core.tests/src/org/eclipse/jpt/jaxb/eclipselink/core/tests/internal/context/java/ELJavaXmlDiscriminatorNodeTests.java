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

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jaxb.core.context.java.JavaClass;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELClassMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELXmlDiscriminatorNode;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.v2_2.ELJaxb_2_2_PlatformDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlDiscriminatorNodeAnnotation;
import org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.context.ELJaxbContextModelTestCase;

@SuppressWarnings("nls")
public class ELJavaXmlDiscriminatorNodeTests
		extends ELJaxbContextModelTestCase {
	
	public ELJavaXmlDiscriminatorNodeTests(String name) {
		super(name);
	}
	
	
	@Override
	protected String getPlatformID() {
		return ELJaxb_2_2_PlatformDefinition.ID;
	}
	
	
	private ICompilationUnit createTypeWithXmlDiscriminatorNode() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JAXB.XML_TYPE, ELJaxb.XML_DISCRIMINATOR_NODE);
			}
			
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType").append(CR);
				sb.append("@XmlDiscriminatorNode").append(CR);
			}
		});
	}
	
	
	public void testModifyValue() throws Exception {
		createTypeWithXmlDiscriminatorNode();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaResourceType resourceType = jaxbClass.getJavaResourceType();
		ELClassMapping classMapping = (ELClassMapping) jaxbClass.getMapping();
		ELXmlDiscriminatorNode discNode = classMapping.getXmlDiscriminatorNode();
		XmlDiscriminatorNodeAnnotation annotation = 
				(XmlDiscriminatorNodeAnnotation) resourceType.getAnnotation(ELJaxb.XML_DISCRIMINATOR_NODE);
		
		assertNull(annotation.getValue());
		assertNull(discNode.getValue());
		
		discNode.setValue("foo");
		
		assertEquals("foo", annotation.getValue());
		assertEquals("foo", discNode.getValue());
		
		discNode.setValue("");
		
		assertEquals("", annotation.getValue());
		assertEquals("", discNode.getValue());
		
		discNode.setValue(null);
		
		assertNull(annotation.getValue());
		assertNull(discNode.getValue());
	}

	public void testUpdateValue() throws Exception {
		createTypeWithXmlDiscriminatorNode();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaResourceType resourceType = jaxbClass.getJavaResourceType();
		ELClassMapping classMapping = (ELClassMapping) jaxbClass.getMapping();
		ELXmlDiscriminatorNode discNode = classMapping.getXmlDiscriminatorNode();
		XmlDiscriminatorNodeAnnotation annotation = 
				(XmlDiscriminatorNodeAnnotation) resourceType.getAnnotation(ELJaxb.XML_DISCRIMINATOR_NODE);
		
		assertNull(annotation.getValue());
		assertNull(discNode.getValue());
		
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				ELJavaXmlDiscriminatorNodeTests.this.setMemberValuePair(
						declaration, ELJaxb.XML_DISCRIMINATOR_NODE, "foo");
			}
		});
		
		assertEquals("foo", annotation.getValue());
		assertEquals("foo", discNode.getValue());
		
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				ELJavaXmlDiscriminatorNodeTests.this.setMemberValuePair(
						declaration, ELJaxb.XML_DISCRIMINATOR_NODE, "");
			}
		});
		
		assertEquals("", annotation.getValue());
		assertEquals("", discNode.getValue());
		
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				ELJavaXmlDiscriminatorNodeTests.this.removeMemberValuePair(
						declaration, ELJaxb.XML_DISCRIMINATOR_NODE);
			}
		});
		
		assertNull(annotation.getValue());
		assertNull(discNode.getValue());
	}
}
