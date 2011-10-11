/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
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
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jaxb.core.context.JaxbClass;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.XmlAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlJavaTypeAdapter;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;
import org.eclipse.jpt.jaxb.core.tests.internal.context.JaxbContextModelTestCase;


@SuppressWarnings("nls")
public class GenericJavaAttributeXmlJavaTypeAdapterTests extends JaxbContextModelTestCase
{

	public GenericJavaAttributeXmlJavaTypeAdapterTests(String name) {
		super(name);
	}

	private ICompilationUnit createClassWithXmlTypeAndAttributeXmlJavaTypeAdapter() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TYPE, JAXB.XML_ATTRIBUTE, JAXB.XML_JAVA_TYPE_ADAPTER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType").append(CR);
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlAttribute").append(CR);
				sb.append("@XmlJavaTypeAdapter");
			}
		});
	}

	public void testModifyValue() throws Exception {
		createClassWithXmlTypeAndAttributeXmlJavaTypeAdapter();
		
		JaxbClass jaxbClass = (JaxbClass) getContextRoot().getType(FULLY_QUALIFIED_TYPE_NAME);
		JaxbClassMapping classMapping = jaxbClass.getMapping();
		XmlAttributeMapping attributeMapping = (XmlAttributeMapping) CollectionTools.get(classMapping.getAttributes(), 0).getMapping();
		XmlJavaTypeAdapter contextXmlJavaTypeAdapter = attributeMapping.getXmlJavaTypeAdapter();
		JavaResourceAttribute resourceAttribute = attributeMapping.getJavaResourceAttribute();
		
		assertNull(contextXmlJavaTypeAdapter.getValue());

		contextXmlJavaTypeAdapter.setValue("foo");
		XmlJavaTypeAdapterAnnotation javaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceAttribute.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
		assertEquals("foo", javaTypeAdapterAnnotation.getValue());
		assertEquals("foo", contextXmlJavaTypeAdapter.getValue());

		 //verify the xml schema type annotation is not removed when the value is set to null
		contextXmlJavaTypeAdapter.setValue(null);
		javaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceAttribute.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
		assertNull(javaTypeAdapterAnnotation.getValue());
		assertNull(contextXmlJavaTypeAdapter.getValue());
	}

	public void testUpdateValue() throws Exception {
		createClassWithXmlTypeAndAttributeXmlJavaTypeAdapter();
		
		JaxbClass jaxbClass = (JaxbClass) getContextRoot().getType(FULLY_QUALIFIED_TYPE_NAME);
		JaxbClassMapping classMapping = jaxbClass.getMapping();
		XmlAttributeMapping attributeMapping = (XmlAttributeMapping) CollectionTools.get(classMapping.getAttributes(), 0).getMapping();
		XmlJavaTypeAdapter contextXmlJavaTypeAdapter = attributeMapping.getXmlJavaTypeAdapter();
		JavaResourceAttribute resourceAttribute = attributeMapping.getJavaResourceAttribute();
		
		assertNull(contextXmlJavaTypeAdapter.getValue());

		//add a value member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaAttributeXmlJavaTypeAdapterTests.this.addXmlJavaTypeAdapterTypeMemberValuePair(declaration, JAXB.XML_JAVA_TYPE_ADAPTER__VALUE, "String");
			}
		});
		assertEquals("String", contextXmlJavaTypeAdapter.getValue());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaAttributeXmlJavaTypeAdapterTests.this.removeXmlJavaTypeAdapterAnnotation(declaration);
			}
		});
		assertNull(attributeMapping.getXmlJavaTypeAdapter());
	}
	
	public void testModifyType() throws Exception {
		createClassWithXmlTypeAndAttributeXmlJavaTypeAdapter();
		
		JaxbClass jaxbClass = (JaxbClass) getContextRoot().getType(FULLY_QUALIFIED_TYPE_NAME);
		JaxbClassMapping classMapping = jaxbClass.getMapping();
		XmlAttributeMapping attributeMapping = (XmlAttributeMapping) CollectionTools.get(classMapping.getAttributes(), 0).getMapping();
		XmlJavaTypeAdapter contextXmlJavaTypeAdapter = attributeMapping.getXmlJavaTypeAdapter();
		JavaResourceAttribute resourceAttribute = attributeMapping.getJavaResourceAttribute();
		
		assertEquals("int", contextXmlJavaTypeAdapter.getType());
		assertNull(contextXmlJavaTypeAdapter.getSpecifiedType());
		assertEquals("int", contextXmlJavaTypeAdapter.getDefaultType());

		contextXmlJavaTypeAdapter.setSpecifiedType("foo");
		XmlJavaTypeAdapterAnnotation javaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceAttribute.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
		assertEquals("foo", javaTypeAdapterAnnotation.getType());
		assertEquals("foo", contextXmlJavaTypeAdapter.getSpecifiedType());
		assertEquals("int", contextXmlJavaTypeAdapter.getDefaultType());

		contextXmlJavaTypeAdapter.setSpecifiedType(null);
		javaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceAttribute.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
		assertNull(javaTypeAdapterAnnotation.getType());
		assertEquals("int", contextXmlJavaTypeAdapter.getType());
		assertNull(contextXmlJavaTypeAdapter.getSpecifiedType());
		assertEquals("int", contextXmlJavaTypeAdapter.getDefaultType());
	}

	public void testUpdateType() throws Exception {
		createClassWithXmlTypeAndAttributeXmlJavaTypeAdapter();
		
		JaxbClass jaxbClass = (JaxbClass) getContextRoot().getType(FULLY_QUALIFIED_TYPE_NAME);
		JaxbClassMapping classMapping = jaxbClass.getMapping();
		XmlAttributeMapping attributeMapping = (XmlAttributeMapping) CollectionTools.get(classMapping.getAttributes(), 0).getMapping();
		XmlJavaTypeAdapter contextXmlJavaTypeAdapter = attributeMapping.getXmlJavaTypeAdapter();
		JavaResourceAttribute resourceAttribute = attributeMapping.getJavaResourceAttribute();
		
		assertEquals("int", contextXmlJavaTypeAdapter.getType());
		assertNull(contextXmlJavaTypeAdapter.getSpecifiedType());
		assertEquals("int", contextXmlJavaTypeAdapter.getDefaultType());

		//add a type member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaAttributeXmlJavaTypeAdapterTests.this.addXmlJavaTypeAdapterTypeMemberValuePair(declaration, JAXB.XML_JAVA_TYPE_ADAPTER__TYPE, "String");
			}
		});
		assertEquals("String", contextXmlJavaTypeAdapter.getSpecifiedType());
		assertEquals("String", contextXmlJavaTypeAdapter.getType());
		assertEquals("int", contextXmlJavaTypeAdapter.getDefaultType());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaAttributeXmlJavaTypeAdapterTests.this.removeXmlJavaTypeAdapterAnnotation(declaration);
			}
		});
		assertNull(attributeMapping.getXmlJavaTypeAdapter());
	}

	protected void addXmlJavaTypeAdapterTypeMemberValuePair(ModifiedDeclaration declaration, String name, String typeName) {
		this.addMemberValuePair(
			(MarkerAnnotation) this.getXmlJavaTypeAdapterAnnotation(declaration), 
			name, 
			this.newTypeLiteral(declaration.getAst(), typeName));
	}

	protected void addXmlJavaTypeAdapterMemberValuePair(ModifiedDeclaration declaration, String name, String value) {
		this.addMemberValuePair((MarkerAnnotation) this.getXmlJavaTypeAdapterAnnotation(declaration), name, value);
	}

	protected void removeXmlJavaTypeAdapterAnnotation(ModifiedDeclaration declaration) {
		this.removeAnnotation(declaration, JAXB.XML_JAVA_TYPE_ADAPTER);		
	}

	protected Annotation getXmlJavaTypeAdapterAnnotation(ModifiedDeclaration declaration) {
		return declaration.getAnnotationNamed(JAXB.XML_JAVA_TYPE_ADAPTER);
	}
}
