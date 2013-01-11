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
import org.eclipse.jpt.common.utility.internal.iterator.ArrayIterator;
import org.eclipse.jpt.jaxb.core.context.java.JavaClass;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELClassMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.context.java.ELXmlDiscriminatorValue;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.v2_2.ELJaxb_2_2_PlatformDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlDiscriminatorValueAnnotation;
import org.eclipse.jpt.jaxb.eclipselink.core.tests.internal.context.ELJaxbContextModelTestCase;

@SuppressWarnings("nls")
public class ELJavaXmlDiscriminatorValueTests
		extends ELJaxbContextModelTestCase {
	
	public ELJavaXmlDiscriminatorValueTests(String name) {
		super(name);
	}
	
	
	@Override
	protected String getPlatformID() {
		return ELJaxb_2_2_PlatformDefinition.ID;
	}
	
	
	private ICompilationUnit createTypeWithXmlDiscriminatorValue() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TYPE, ELJaxb.XML_DISCRIMINATOR_VALUE);
			}
			
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType").append(CR);
				sb.append("@XmlDiscriminatorValue").append(CR);
			}
		});
	}
	
	
	public void testModifyValue() throws Exception {
		createTypeWithXmlDiscriminatorValue();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaResourceType resourceType = jaxbClass.getJavaResourceType();
		ELClassMapping classMapping = (ELClassMapping) jaxbClass.getMapping();
		ELXmlDiscriminatorValue discValue = classMapping.getXmlDiscriminatorValue();
		XmlDiscriminatorValueAnnotation annotation = 
				(XmlDiscriminatorValueAnnotation) resourceType.getAnnotation(ELJaxb.XML_DISCRIMINATOR_VALUE);
		
		assertNull(annotation.getValue());
		assertNull(discValue.getValue());
		
		discValue.setValue("foo");
		
		assertEquals("foo", annotation.getValue());
		assertEquals("foo", discValue.getValue());
		
		discValue.setValue("");
		
		assertEquals("", annotation.getValue());
		assertEquals("", discValue.getValue());
		
		discValue.setValue(null);
		
		assertNull(annotation.getValue());
		assertNull(discValue.getValue());
	}

	public void testUpdateValue() throws Exception {
		createTypeWithXmlDiscriminatorValue();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaResourceType resourceType = jaxbClass.getJavaResourceType();
		ELClassMapping classMapping = (ELClassMapping) jaxbClass.getMapping();
		ELXmlDiscriminatorValue discValue = classMapping.getXmlDiscriminatorValue();
		XmlDiscriminatorValueAnnotation annotation = 
				(XmlDiscriminatorValueAnnotation) resourceType.getAnnotation(ELJaxb.XML_DISCRIMINATOR_VALUE);
		
		assertNull(annotation.getValue());
		assertNull(discValue.getValue());
		
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				ELJavaXmlDiscriminatorValueTests.this.setMemberValuePair(
						declaration, ELJaxb.XML_DISCRIMINATOR_VALUE, "foo");
			}
		});
		
		assertEquals("foo", annotation.getValue());
		assertEquals("foo", discValue.getValue());
		
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				ELJavaXmlDiscriminatorValueTests.this.setMemberValuePair(
						declaration, ELJaxb.XML_DISCRIMINATOR_VALUE, "");
			}
		});
		
		assertEquals("", annotation.getValue());
		assertEquals("", discValue.getValue());
		
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				ELJavaXmlDiscriminatorValueTests.this.removeMemberValuePair(
						declaration, ELJaxb.XML_DISCRIMINATOR_VALUE);
			}
		});
		
		assertNull(annotation.getValue());
		assertNull(discValue.getValue());
	}
}
