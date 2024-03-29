/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal.context.java;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.XmlSchemaType;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaTypeAnnotation;
import org.eclipse.jpt.jaxb.core.tests.internal.context.JaxbContextModelTestCase;
import org.eclipse.xsd.util.XSDConstants;


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
		JaxbPackageInfo contextPackageInfo = IterableTools.get(getContextRoot().getPackages(), 0).getPackageInfo();
		XmlSchemaType contextXmlSchemaType = contextPackageInfo.getXmlSchemaTypes().iterator().next();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();

		assertNull(contextXmlSchemaType.getQName().getName());
		
		contextXmlSchemaType.getQName().setSpecifiedName("foo");
		XmlSchemaTypeAnnotation schemaTypeAnnotation = (XmlSchemaTypeAnnotation) resourcePackage.getAnnotation(0, JAXB.XML_SCHEMA_TYPE);
		assertEquals("foo", schemaTypeAnnotation.getName());
		assertEquals("foo", contextXmlSchemaType.getQName().getName());
		
		 //verify the xml schema type annotation is not removed when the name is set to null
		contextXmlSchemaType.getQName().setSpecifiedName(null);
		schemaTypeAnnotation = (XmlSchemaTypeAnnotation) resourcePackage.getAnnotation(0, JAXB.XML_SCHEMA_TYPE);
		assertNull(schemaTypeAnnotation.getName());
		assertNull(contextXmlSchemaType.getQName().getName());
	}
	
	public void testUpdateName() throws Exception {
		this.createPackageInfoWithXmlSchemaType();
		JaxbPackageInfo contextPackageInfo = IterableTools.get(getContextRoot().getPackages(), 0).getPackageInfo();
		XmlSchemaType contextXmlSchemaType = contextPackageInfo.getXmlSchemaTypes().iterator().next();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();

		assertNull(contextXmlSchemaType.getQName().getName());
		
		//add a name member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourcePackage);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSchemaTypeTests.this.addXmlSchemaTypeMemberValuePair(declaration, JAXB.XML_SCHEMA_TYPE__NAME, "foo");
			}
		});
		assertEquals("foo", contextXmlSchemaType.getQName().getName());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSchemaTypeTests.this.removeXmlSchemaTypeAnnotation(declaration);
			}
		});
		assertFalse(contextPackageInfo.getXmlSchemaTypes().iterator().hasNext());
	}
	
	public void testModifyNamespace() throws Exception {
		this.createPackageInfoWithXmlSchemaType();
		JaxbPackageInfo contextPackageInfo = IterableTools.get(getContextRoot().getPackages(), 0).getPackageInfo();
		XmlSchemaType contextXmlSchemaType = contextPackageInfo.getXmlSchemaTypes().iterator().next();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();
	
		assertNull(contextXmlSchemaType.getQName().getSpecifiedNamespace());
		assertEquals(XSDConstants.SCHEMA_FOR_SCHEMA_URI_2001, contextXmlSchemaType.getQName().getDefaultNamespace());
		assertEquals(XSDConstants.SCHEMA_FOR_SCHEMA_URI_2001, contextXmlSchemaType.getQName().getNamespace());
		
		contextXmlSchemaType.getQName().setSpecifiedNamespace("foo");
		XmlSchemaTypeAnnotation schemaTypeAnnotation = (XmlSchemaTypeAnnotation) resourcePackage.getAnnotation(0, JAXB.XML_SCHEMA_TYPE);
		assertEquals("foo", schemaTypeAnnotation.getNamespace());
		assertEquals("foo", contextXmlSchemaType.getQName().getSpecifiedNamespace());
		assertEquals("foo", contextXmlSchemaType.getQName().getNamespace());
		
		 //verify the xml schema type annotation is not removed when the namespace is set to null
		contextXmlSchemaType.getQName().setSpecifiedNamespace(null);
		schemaTypeAnnotation = (XmlSchemaTypeAnnotation) resourcePackage.getAnnotation(0, JAXB.XML_SCHEMA_TYPE);
		assertNull(schemaTypeAnnotation.getNamespace());
		assertNull(contextXmlSchemaType.getQName().getSpecifiedNamespace());
		assertEquals(XSDConstants.SCHEMA_FOR_SCHEMA_URI_2001, contextXmlSchemaType.getQName().getNamespace());
	}
	
	public void testUpdateNamespace() throws Exception {
		this.createPackageInfoWithXmlSchemaType();
		JaxbPackageInfo contextPackageInfo = IterableTools.get(getContextRoot().getPackages(), 0).getPackageInfo();
		XmlSchemaType contextXmlSchemaType = contextPackageInfo.getXmlSchemaTypes().iterator().next();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();

		assertNull(contextXmlSchemaType.getQName().getSpecifiedNamespace());
		assertEquals(XSDConstants.SCHEMA_FOR_SCHEMA_URI_2001, contextXmlSchemaType.getQName().getDefaultNamespace());
		assertEquals(XSDConstants.SCHEMA_FOR_SCHEMA_URI_2001, contextXmlSchemaType.getQName().getNamespace());
		
		//add a namespace member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourcePackage);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSchemaTypeTests.this.addXmlSchemaTypeMemberValuePair(declaration, JAXB.XML_SCHEMA_TYPE__NAMESPACE, "foo");
			}
		});
		assertEquals("foo", contextXmlSchemaType.getQName().getSpecifiedNamespace());
		assertEquals("foo", contextXmlSchemaType.getQName().getNamespace());
		
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSchemaTypeTests.this.removeXmlSchemaTypeAnnotation(declaration);
			}
		});
		assertFalse(contextPackageInfo.getXmlSchemaTypes().iterator().hasNext());
	}
	
	public void testModifyType() throws Exception {
		this.createPackageInfoWithXmlSchemaType();
		JaxbPackageInfo contextPackageInfo = IterableTools.get(getContextRoot().getPackages(), 0).getPackageInfo();
		XmlSchemaType contextXmlSchemaType = contextPackageInfo.getXmlSchemaTypes().iterator().next();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();
	
		assertNull(contextXmlSchemaType.getType());
		
		contextXmlSchemaType.setType("foo");
		XmlSchemaTypeAnnotation schemaTypeAnnotation = (XmlSchemaTypeAnnotation) resourcePackage.getAnnotation(0, JAXB.XML_SCHEMA_TYPE);
		assertEquals("foo", schemaTypeAnnotation.getType());
		assertEquals("foo", contextXmlSchemaType.getType());
		
		 //verify the xml schema type annotation is not removed when the type is set to null
		contextXmlSchemaType.setType(null);
		schemaTypeAnnotation = (XmlSchemaTypeAnnotation) resourcePackage.getAnnotation(0, JAXB.XML_SCHEMA_TYPE);
		assertNull(schemaTypeAnnotation.getType());
		assertNull(contextXmlSchemaType.getType());
	}
	
	public void testUpdateType() throws Exception {
		this.createPackageInfoWithXmlSchemaType();
		JaxbPackageInfo contextPackageInfo = IterableTools.get(getContextRoot().getPackages(), 0).getPackageInfo();
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
		this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_ACCESSOR_ORDER);
		this.removeAnnotation(declaration, JAXB.XML_SCHEMA_TYPE);		
	}

	protected Annotation getXmlSchemaTypeAnnotation(ModifiedDeclaration declaration) {
		return declaration.getAnnotationNamed(JAXB.XML_SCHEMA_TYPE);
	}
}
