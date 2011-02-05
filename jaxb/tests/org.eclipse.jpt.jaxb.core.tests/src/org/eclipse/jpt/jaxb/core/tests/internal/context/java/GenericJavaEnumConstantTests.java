/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jaxb.core.context.JaxbEnumConstant;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentEnum;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceEnum;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceEnumConstant;
import org.eclipse.jpt.jaxb.core.resource.java.XmlEnumValueAnnotation;
import org.eclipse.jpt.jaxb.core.tests.internal.context.JaxbContextModelTestCase;


@SuppressWarnings("nls")
public class GenericJavaEnumConstantTests extends JaxbContextModelTestCase
{
	
	public GenericJavaEnumConstantTests(String name) {
		super(name);
	}

	private ICompilationUnit createEnumWithXmlEnum() throws Exception {
		return createTestEnum(new DefaultEnumAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_ENUM);
			}
			@Override
			public void appendEnumAnnotationTo(StringBuilder sb) {
				sb.append("@XmlEnum").append(CR);
			}
		});
	}
	
	public void testUpdateName() throws Exception {
		createEnumWithXmlEnum();
		
		JaxbPersistentEnum contextEnum = CollectionTools.get(getContextRoot().getPersistentEnums(), 0);
		JaxbEnumConstant contextEnumConstant = CollectionTools.get(contextEnum.getEnumConstants(), 1);
		JavaResourceEnum resourceEnum = contextEnum.getJavaResourceType();
	
		assertEquals("MONDAY", contextEnumConstant.getName());
		
		
		//add a factoryClass member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceEnum);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaEnumConstantTests.this.changeEnumConstantName((EnumDeclaration) declaration.getDeclaration(), "MONDAY", "MONDAY2");
			}
		});
		contextEnumConstant = CollectionTools.get(contextEnum.getEnumConstants(), 1);
		assertEquals(2, contextEnum.getEnumConstantsSize());
		assertEquals("MONDAY2", contextEnumConstant.getName());
	}

	public void testModifyValue() throws Exception {
		createEnumWithXmlEnum();
		
		JaxbPersistentEnum contextEnum = CollectionTools.get(getContextRoot().getPersistentEnums(), 0);
		JaxbEnumConstant contextEnumConstant = CollectionTools.get(contextEnum.getEnumConstants(), 1);
		JavaResourceEnum resourceEnum = contextEnum.getJavaResourceType();
		JavaResourceEnumConstant resourceEnumConstant = CollectionTools.get(resourceEnum.getEnumConstants(), 1);
		
		assertEquals("MONDAY", contextEnumConstant.getDefaultValue());
		assertEquals("MONDAY", contextEnumConstant.getValue());
		assertNull(contextEnumConstant.getSpecifiedValue());
		
		contextEnumConstant.setSpecifiedValue("foo");
		XmlEnumValueAnnotation enumValueAnnotation = (XmlEnumValueAnnotation) resourceEnumConstant.getAnnotation(XmlEnumValueAnnotation.ANNOTATION_NAME);
		assertEquals("foo", enumValueAnnotation.getValue());
		assertEquals("MONDAY", contextEnumConstant.getDefaultValue());
		assertEquals("foo", contextEnumConstant.getValue());
		assertEquals("foo", contextEnumConstant.getSpecifiedValue());
		
		contextEnumConstant.setSpecifiedValue(null);
		enumValueAnnotation = (XmlEnumValueAnnotation) resourceEnumConstant.getAnnotation(XmlEnumValueAnnotation.ANNOTATION_NAME);
		assertNull(enumValueAnnotation.getValue());
		assertEquals("MONDAY", contextEnumConstant.getDefaultValue());
		assertEquals("MONDAY", contextEnumConstant.getValue());
		assertNull(contextEnumConstant.getSpecifiedValue());
	}
	
	public void testUpdateValue() throws Exception {
		createEnumWithXmlEnum();
		
		JaxbPersistentEnum contextEnum = CollectionTools.get(getContextRoot().getPersistentEnums(), 0);
		JaxbEnumConstant contextEnumConstant = CollectionTools.get(contextEnum.getEnumConstants(), 1);
		JavaResourceEnum resourceEnum = contextEnum.getJavaResourceType();
		JavaResourceEnumConstant resourceEnumConstant = CollectionTools.get(resourceEnum.getEnumConstants(), 1);
		
		assertEquals("MONDAY", contextEnumConstant.getDefaultValue());
		assertEquals("MONDAY", contextEnumConstant.getValue());
		assertNull(contextEnumConstant.getSpecifiedValue());
		
		//add an XmlEnumValue annotation
		AnnotatedElement annotatedElement = this.annotatedElement(resourceEnumConstant);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation enumValueAnnotation = GenericJavaEnumConstantTests.this.addNormalAnnotation(declaration.getDeclaration(), JAXB.XML_ENUM_VALUE);
				GenericJavaEnumConstantTests.this.addMemberValuePair(enumValueAnnotation, JAXB.XML_ENUM_VALUE__VALUE, "foo");
			}
		});

		assertEquals("MONDAY", contextEnumConstant.getDefaultValue());
		assertEquals("foo", contextEnumConstant.getValue());
		assertEquals("foo", contextEnumConstant.getSpecifiedValue());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaEnumConstantTests.this.removeAnnotation(declaration, JAXB.XML_ENUM_VALUE);
			}
		});
		assertEquals("MONDAY", contextEnumConstant.getDefaultValue());
		assertEquals("MONDAY", contextEnumConstant.getValue());
		assertNull(contextEnumConstant.getSpecifiedValue());
	}

}