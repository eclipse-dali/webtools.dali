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
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentClass;
import org.eclipse.jpt.jaxb.core.context.XmlJavaTypeAdapter;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorOrderAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;
import org.eclipse.jpt.jaxb.core.tests.internal.context.JaxbContextModelTestCase;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;


@SuppressWarnings("nls")
public class GenericJavaTypeXmlJavaTypeAdapterTests extends JaxbContextModelTestCase
{

	public GenericJavaTypeXmlJavaTypeAdapterTests(String name) {
		super(name);
	}

	private ICompilationUnit createTypeWithXmlTypeWithXmlJavaTypeAdapter() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TYPE, JAXB.XML_JAVA_TYPE_ADAPTER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType").append(CR);
				sb.append("@XmlJavaTypeAdapter");
			}
		});
	}

	public void testModifyValue() throws Exception {
		this.createTypeWithXmlTypeWithXmlJavaTypeAdapter();
		JaxbPersistentClass contextPersistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		XmlJavaTypeAdapter contextXmlJavaTypeAdapter = contextPersistentClass.getXmlJavaTypeAdapter();
		JavaResourceType resourceType = contextPersistentClass.getJavaResourceType();

		assertNull(contextXmlJavaTypeAdapter.getValue());

		contextXmlJavaTypeAdapter.setValue("foo");
		XmlJavaTypeAdapterAnnotation javaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceType.getAnnotation(0, XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
		assertEquals("foo", javaTypeAdapterAnnotation.getValue());
		assertEquals("foo", contextXmlJavaTypeAdapter.getValue());

		 //verify the xml schema type annotation is not removed when the value is set to null
		contextXmlJavaTypeAdapter.setValue(null);
		javaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceType.getAnnotation(0, XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
		assertNull(javaTypeAdapterAnnotation.getValue());
		assertNull(contextXmlJavaTypeAdapter.getValue());
	}

	public void testUpdateValue() throws Exception {
		this.createTypeWithXmlTypeWithXmlJavaTypeAdapter();
		JaxbPersistentClass contextPersistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		XmlJavaTypeAdapter contextXmlJavaTypeAdapter = contextPersistentClass.getXmlJavaTypeAdapter();
		JavaResourceType resourceType = contextPersistentClass.getJavaResourceType();

		assertNull(contextXmlJavaTypeAdapter.getValue());

		//add a value member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaTypeXmlJavaTypeAdapterTests.this.addXmlJavaTypeAdapterTypeMemberValuePair(declaration, JAXB.XML_JAVA_TYPE_ADAPTER__VALUE, "String");
			}
		});
		assertEquals("String", contextXmlJavaTypeAdapter.getValue());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaTypeXmlJavaTypeAdapterTests.this.removeXmlJavaTypeAdapterAnnotation(declaration);
			}
		});
		assertNull(contextPersistentClass.getXmlJavaTypeAdapter());
	}

	public void testModifyType() throws Exception {
		this.createTypeWithXmlTypeWithXmlJavaTypeAdapter();
		JaxbPersistentClass contextPersistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		XmlJavaTypeAdapter contextXmlJavaTypeAdapter = contextPersistentClass.getXmlJavaTypeAdapter();
		JavaResourceType resourceType = contextPersistentClass.getJavaResourceType();

		assertEquals(FULLY_QUALIFIED_TYPE_NAME, contextXmlJavaTypeAdapter.getType());
		assertNull(contextXmlJavaTypeAdapter.getSpecifiedType());
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, contextXmlJavaTypeAdapter.getDefaultType());

		contextXmlJavaTypeAdapter.setSpecifiedType("foo");
		XmlJavaTypeAdapterAnnotation javaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceType.getAnnotation(0, XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
		assertEquals("foo", javaTypeAdapterAnnotation.getType());
		assertEquals("foo", contextXmlJavaTypeAdapter.getSpecifiedType());
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, contextXmlJavaTypeAdapter.getDefaultType());

		contextXmlJavaTypeAdapter.setSpecifiedType(null);
		javaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceType.getAnnotation(0, XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
		assertNull(javaTypeAdapterAnnotation.getType());
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, contextXmlJavaTypeAdapter.getType());
		assertNull(contextXmlJavaTypeAdapter.getSpecifiedType());
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, contextXmlJavaTypeAdapter.getDefaultType());
	}

	public void testUpdateType() throws Exception {
		this.createTypeWithXmlTypeWithXmlJavaTypeAdapter();
		JaxbPersistentClass contextPersistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		XmlJavaTypeAdapter contextXmlJavaTypeAdapter = contextPersistentClass.getXmlJavaTypeAdapter();
		JavaResourceType resourceType = contextPersistentClass.getJavaResourceType();

		assertEquals(FULLY_QUALIFIED_TYPE_NAME, contextXmlJavaTypeAdapter.getType());
		assertNull(contextXmlJavaTypeAdapter.getSpecifiedType());
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, contextXmlJavaTypeAdapter.getDefaultType());

		//add a type member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaTypeXmlJavaTypeAdapterTests.this.addXmlJavaTypeAdapterTypeMemberValuePair(declaration, JAXB.XML_JAVA_TYPE_ADAPTER__TYPE, "String");
			}
		});
		assertEquals("String", contextXmlJavaTypeAdapter.getSpecifiedType());
		assertEquals("String", contextXmlJavaTypeAdapter.getType());
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, contextXmlJavaTypeAdapter.getDefaultType());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaTypeXmlJavaTypeAdapterTests.this.removeXmlJavaTypeAdapterAnnotation(declaration);
			}
		});
		assertNull(contextPersistentClass.getXmlJavaTypeAdapter());
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

	//add another package annotation so that the context model object doesn't get removed when 
	//removing the XmlJavaTypeAdapter annotation. Only "annotated" packages are added to the context model
	protected void removeXmlJavaTypeAdapterAnnotation(ModifiedDeclaration declaration) {
		this.addMarkerAnnotation(declaration.getDeclaration(), XmlAccessorOrderAnnotation.ANNOTATION_NAME);
		this.removeAnnotation(declaration, XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);		
	}

	protected Annotation getXmlJavaTypeAdapterAnnotation(ModifiedDeclaration declaration) {
		return declaration.getAnnotationNamed(XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
	}
}
