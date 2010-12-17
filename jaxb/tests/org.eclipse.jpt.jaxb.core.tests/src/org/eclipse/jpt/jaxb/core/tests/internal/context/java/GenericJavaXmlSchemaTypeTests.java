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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jpt.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.XmlSchemaType;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorOrderAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaTypeAnnotation;
import org.eclipse.jpt.jaxb.core.tests.internal.context.JaxbContextModelTestCase;
import org.eclipse.jpt.utility.internal.CollectionTools;


@SuppressWarnings("nls")
public class GenericJavaXmlSchemaTypeTests extends JaxbContextModelTestCase
{
	
	public GenericJavaXmlSchemaTypeTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createPackageInfoWithXmlSchemaType() throws CoreException {
		return createTestPackageInfo(
				"@XmlSchemaType",
				JAXB.XML_SCHEMA_TYPE);
	}
	
	public void testModifyName() throws Exception {
		this.createPackageInfoWithXmlSchemaType();
		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getContextRoot().getPackages(), 0).getPackageInfo();
		XmlSchemaType contextXmlSchemaType = contextPackageInfo.getXmlSchemaTypes().iterator().next();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();

		assertNull(contextXmlSchemaType.getName());
		
		contextXmlSchemaType.setName("foo");
		XmlSchemaTypeAnnotation schemaTypeAnnotation = (XmlSchemaTypeAnnotation) resourcePackage.getAnnotation(0, XmlSchemaTypeAnnotation.ANNOTATION_NAME);
		assertEquals("foo", schemaTypeAnnotation.getName());
		assertEquals("foo", contextXmlSchemaType.getName());
		
		 //verify the xml schema type annotation is not removed when the name is set to null
		contextXmlSchemaType.setName(null);
		schemaTypeAnnotation = (XmlSchemaTypeAnnotation) resourcePackage.getAnnotation(0, XmlSchemaTypeAnnotation.ANNOTATION_NAME);
		assertNull(schemaTypeAnnotation.getName());
		assertNull(contextXmlSchemaType.getName());
	}
	
	public void testUpdateName() throws Exception {
		this.createPackageInfoWithXmlSchemaType();
		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getContextRoot().getPackages(), 0).getPackageInfo();
		XmlSchemaType contextXmlSchemaType = contextPackageInfo.getXmlSchemaTypes().iterator().next();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();

		assertNull(contextXmlSchemaType.getName());
		
		//add a name member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourcePackage);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSchemaTypeTests.this.addXmlSchemaTypeMemberValuePair(declaration, JAXB.XML_SCHEMA_TYPE__NAME, "foo");
			}
		});
		assertEquals("foo", contextXmlSchemaType.getName());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSchemaTypeTests.this.removeXmlSchemaTypeAnnotation(declaration);
			}
		});
		assertFalse(contextPackageInfo.getXmlSchemaTypes().iterator().hasNext());
	}
	
	public void testModifyNamespace() throws Exception {
		this.createPackageInfoWithXmlSchemaType();
		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getContextRoot().getPackages(), 0).getPackageInfo();
		XmlSchemaType contextXmlSchemaType = contextPackageInfo.getXmlSchemaTypes().iterator().next();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();
	
		assertNull(contextXmlSchemaType.getNamespace());
		
		contextXmlSchemaType.setNamespace("foo");
		XmlSchemaTypeAnnotation schemaTypeAnnotation = (XmlSchemaTypeAnnotation) resourcePackage.getAnnotation(0, XmlSchemaTypeAnnotation.ANNOTATION_NAME);
		assertEquals("foo", schemaTypeAnnotation.getNamespace());
		assertEquals("foo", contextXmlSchemaType.getNamespace());
		
		 //verify the xml schema type annotation is not removed when the namespace is set to null
		contextXmlSchemaType.setNamespace(null);
		schemaTypeAnnotation = (XmlSchemaTypeAnnotation) resourcePackage.getAnnotation(0, XmlSchemaTypeAnnotation.ANNOTATION_NAME);
		assertNull(schemaTypeAnnotation.getNamespace());
		assertNull(contextXmlSchemaType.getNamespace());
	}
	
	public void testUpdateNamespace() throws Exception {
		this.createPackageInfoWithXmlSchemaType();
		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getContextRoot().getPackages(), 0).getPackageInfo();
		XmlSchemaType contextXmlSchemaType = contextPackageInfo.getXmlSchemaTypes().iterator().next();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();

		assertNull(contextXmlSchemaType.getNamespace());
		
		//add a namespace member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourcePackage);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSchemaTypeTests.this.addXmlSchemaTypeMemberValuePair(declaration, JAXB.XML_SCHEMA_TYPE__NAMESPACE, "foo");
			}
		});
		assertEquals("foo", contextXmlSchemaType.getNamespace());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSchemaTypeTests.this.removeXmlSchemaTypeAnnotation(declaration);
			}
		});
		assertFalse(contextPackageInfo.getXmlSchemaTypes().iterator().hasNext());
	}
	
	public void testModifyType() throws Exception {
		this.createPackageInfoWithXmlSchemaType();
		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getContextRoot().getPackages(), 0).getPackageInfo();
		XmlSchemaType contextXmlSchemaType = contextPackageInfo.getXmlSchemaTypes().iterator().next();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();
	
		assertNull(contextXmlSchemaType.getType());
		
		contextXmlSchemaType.setType("foo");
		XmlSchemaTypeAnnotation schemaTypeAnnotation = (XmlSchemaTypeAnnotation) resourcePackage.getAnnotation(0, XmlSchemaTypeAnnotation.ANNOTATION_NAME);
		assertEquals("foo", schemaTypeAnnotation.getType());
		assertEquals("foo", contextXmlSchemaType.getType());
		
		 //verify the xml schema type annotation is not removed when the type is set to null
		contextXmlSchemaType.setType(null);
		schemaTypeAnnotation = (XmlSchemaTypeAnnotation) resourcePackage.getAnnotation(0, XmlSchemaTypeAnnotation.ANNOTATION_NAME);
		assertNull(schemaTypeAnnotation.getType());
		assertNull(contextXmlSchemaType.getType());
	}
	
	public void testUpdateType() throws Exception {
		this.createPackageInfoWithXmlSchemaType();
		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getContextRoot().getPackages(), 0).getPackageInfo();
		XmlSchemaType contextXmlSchemaType = contextPackageInfo.getXmlSchemaTypes().iterator().next();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();

		assertNull(contextXmlSchemaType.getType());
		
		//add a type member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourcePackage);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSchemaTypeTests.this.addXmlSchemaTypeTypeMemberValuePair(declaration, JAXB.XML_SCHEMA_TYPE__TYPE, "String");
			}
		});
		assertEquals("String", contextXmlSchemaType.getType());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSchemaTypeTests.this.removeXmlSchemaTypeAnnotation(declaration);
			}
		});
		assertFalse(contextPackageInfo.getXmlSchemaTypes().iterator().hasNext());
	}

	protected void addXmlSchemaTypeTypeMemberValuePair(ModifiedDeclaration declaration, String name, String typeName) {
		this.addMemberValuePair(
			(MarkerAnnotation) this.getXmlSchemaTypeAnnotation(declaration), 
			name, 
			this.newTypeLiteral(declaration.getAst(), typeName));
	}

	protected void addXmlSchemaTypeMemberValuePair(ModifiedDeclaration declaration, String name, String value) {
		this.addMemberValuePair((MarkerAnnotation) this.getXmlSchemaTypeAnnotation(declaration), name, value);
	}

	//add another package annotation so that the context model object doesn't get removed when 
	//removing the XmlSchemaType annotation. Only "annotated" packages are added to the context model
	protected void removeXmlSchemaTypeAnnotation(ModifiedDeclaration declaration) {
		this.addMarkerAnnotation(declaration.getDeclaration(), XmlAccessorOrderAnnotation.ANNOTATION_NAME);
		this.removeAnnotation(declaration, XmlSchemaTypeAnnotation.ANNOTATION_NAME);		
	}

	protected Annotation getXmlSchemaTypeAnnotation(ModifiedDeclaration declaration) {
		return declaration.getAnnotationNamed(XmlSchemaTypeAnnotation.ANNOTATION_NAME);
	}
}
