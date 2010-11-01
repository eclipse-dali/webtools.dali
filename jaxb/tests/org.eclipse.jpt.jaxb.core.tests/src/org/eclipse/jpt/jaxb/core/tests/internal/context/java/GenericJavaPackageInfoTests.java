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
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jpt.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.XmlAccessOrder;
import org.eclipse.jpt.jaxb.core.context.XmlAccessType;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorOrderAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorTypeAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaAnnotation;
import org.eclipse.jpt.jaxb.core.tests.internal.context.JaxbContextModelTestCase;
import org.eclipse.jpt.utility.internal.CollectionTools;


@SuppressWarnings("nls")
public class GenericJavaPackageInfoTests extends JaxbContextModelTestCase
{
	
	public GenericJavaPackageInfoTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createPackageInfoWithAccessorType() throws CoreException {
		return createTestPackageInfo(
				"@XmlAccessorType(value = XmlAccessType.PROPERTY)",
				JAXB.XML_ACCESS_TYPE, JAXB.XML_ACCESSOR_TYPE);
	}
	
	private ICompilationUnit createPackageInfoWithAccessorOrder() throws CoreException {
		return createTestPackageInfo(
				"@XmlAccessorOrder(value = XmlAccessOrder.ALPHABETICAL)",
				JAXB.XML_ACCESS_ORDER, JAXB.XML_ACCESSOR_ORDER);
	}
	
	public void testModifyAccessType() throws Exception {
		createPackageInfoWithAccessorType();
		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getRootContextNode().getPackages(), 0).getPackageInfo();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();
	
		assertEquals(XmlAccessType.PROPERTY, contextPackageInfo.getSpecifiedAccessType());
		assertEquals(XmlAccessType.PROPERTY, contextPackageInfo.getAccessType());
		assertEquals(XmlAccessType.PUBLIC_MEMBER, contextPackageInfo.getDefaultAccessType());
		
		contextPackageInfo.setSpecifiedAccessType(XmlAccessType.FIELD);
		XmlAccessorTypeAnnotation accessorTypeAnnotation = (XmlAccessorTypeAnnotation) resourcePackage.getAnnotation(XmlAccessorTypeAnnotation.ANNOTATION_NAME);
		assertEquals(org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType.FIELD, accessorTypeAnnotation.getValue());
		assertEquals(XmlAccessType.FIELD, contextPackageInfo.getAccessType());

		contextPackageInfo.setSpecifiedAccessType(XmlAccessType.PUBLIC_MEMBER);
		assertEquals(org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType.PUBLIC_MEMBER, accessorTypeAnnotation.getValue());
		assertEquals(XmlAccessType.PUBLIC_MEMBER, contextPackageInfo.getAccessType());

		contextPackageInfo.setSpecifiedAccessType(XmlAccessType.NONE);
		assertEquals(org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType.NONE, accessorTypeAnnotation.getValue());
		assertEquals(XmlAccessType.NONE, contextPackageInfo.getAccessType());
		
		contextPackageInfo.setSpecifiedAccessType(null);
		accessorTypeAnnotation = (XmlAccessorTypeAnnotation) resourcePackage.getAnnotation(XmlAccessorTypeAnnotation.ANNOTATION_NAME);
		assertNull(accessorTypeAnnotation);
		assertNull(contextPackageInfo.getSpecifiedAccessType());
		assertEquals(XmlAccessType.PUBLIC_MEMBER, contextPackageInfo.getAccessType());
		assertEquals(XmlAccessType.PUBLIC_MEMBER, contextPackageInfo.getDefaultAccessType());
	}
	
	public void testUpdateAccessType() throws Exception {
		createPackageInfoWithAccessorType();
		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getRootContextNode().getPackages(), 0).getPackageInfo();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();
	
		assertEquals(XmlAccessType.PROPERTY, contextPackageInfo.getSpecifiedAccessType());
		assertEquals(XmlAccessType.PROPERTY, contextPackageInfo.getAccessType());
		assertEquals(XmlAccessType.PUBLIC_MEMBER, contextPackageInfo.getDefaultAccessType());
		
		//set the accesser type value to FIELD
		AnnotatedElement annotatedElement = this.annotatedElement(resourcePackage);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPackageInfoTests.this.setEnumMemberValuePair(declaration, XmlAccessorTypeAnnotation.ANNOTATION_NAME, JAXB.XML_ACCESS_TYPE__FIELD);
			}
		});
		assertEquals(XmlAccessType.FIELD, contextPackageInfo.getAccessType());

		//set the accesser type value to PUBLIC_MEMBER
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPackageInfoTests.this.setEnumMemberValuePair(declaration, XmlAccessorTypeAnnotation.ANNOTATION_NAME, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
			}
		});
		assertEquals(XmlAccessType.PUBLIC_MEMBER, contextPackageInfo.getAccessType());

		//set the accesser type value to NONE
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPackageInfoTests.this.setEnumMemberValuePair(declaration, XmlAccessorTypeAnnotation.ANNOTATION_NAME, JAXB.XML_ACCESS_TYPE__NONE);
			}
		});
		assertEquals(XmlAccessType.NONE, contextPackageInfo.getAccessType());
		
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPackageInfoTests.this.removeXmlAccessorTypeAnnotation(declaration);
			}
		});
		assertNull(contextPackageInfo.getSpecifiedAccessType());
		assertEquals(XmlAccessType.PUBLIC_MEMBER, contextPackageInfo.getAccessType());
		assertEquals(XmlAccessType.PUBLIC_MEMBER, contextPackageInfo.getDefaultAccessType());
	}
	
	public void testModifyAccessOrder() throws Exception {
		createPackageInfoWithAccessorOrder();
		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getRootContextNode().getPackages(), 0).getPackageInfo();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();
	
		assertEquals(XmlAccessOrder.ALPHABETICAL, contextPackageInfo.getSpecifiedAccessOrder());
		assertEquals(XmlAccessOrder.ALPHABETICAL, contextPackageInfo.getAccessOrder());
		assertEquals(XmlAccessOrder.UNDEFINED, contextPackageInfo.getDefaultAccessOrder());
		
		contextPackageInfo.setSpecifiedAccessOrder(XmlAccessOrder.UNDEFINED);
		XmlAccessorOrderAnnotation accessorOrderAnnotation = (XmlAccessorOrderAnnotation) resourcePackage.getAnnotation(XmlAccessorOrderAnnotation.ANNOTATION_NAME);
		assertEquals(org.eclipse.jpt.jaxb.core.resource.java.XmlAccessOrder.UNDEFINED, accessorOrderAnnotation.getValue());
		assertEquals(XmlAccessOrder.UNDEFINED, contextPackageInfo.getAccessOrder());
		
		contextPackageInfo.setSpecifiedAccessOrder(null);
		accessorOrderAnnotation = (XmlAccessorOrderAnnotation) resourcePackage.getAnnotation(XmlAccessorOrderAnnotation.ANNOTATION_NAME);
		assertNull(accessorOrderAnnotation);
		assertNull(contextPackageInfo.getSpecifiedAccessOrder());
		assertEquals(XmlAccessOrder.UNDEFINED, contextPackageInfo.getAccessOrder());
		assertEquals(XmlAccessOrder.UNDEFINED, contextPackageInfo.getDefaultAccessOrder());
	}
	
	public void testUpdateAccessOrder() throws Exception {
		createPackageInfoWithAccessorOrder();
		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getRootContextNode().getPackages(), 0).getPackageInfo();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();
	
		assertEquals(XmlAccessOrder.ALPHABETICAL, contextPackageInfo.getSpecifiedAccessOrder());
		assertEquals(XmlAccessOrder.ALPHABETICAL, contextPackageInfo.getAccessOrder());
		assertEquals(XmlAccessOrder.UNDEFINED, contextPackageInfo.getDefaultAccessOrder());
		
		//set the access order value to UNDEFINED
		AnnotatedElement annotatedElement = this.annotatedElement(resourcePackage);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPackageInfoTests.this.setEnumMemberValuePair(declaration, XmlAccessorOrderAnnotation.ANNOTATION_NAME, JAXB.XML_ACCESS_ORDER__UNDEFINED);
			}
		});
		assertEquals(XmlAccessOrder.UNDEFINED, contextPackageInfo.getAccessOrder());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPackageInfoTests.this.removeXmlAccessorOrderAnnotation(declaration);
			}
		});
		assertNull(contextPackageInfo.getSpecifiedAccessOrder());
		assertEquals(XmlAccessOrder.UNDEFINED, contextPackageInfo.getAccessOrder());
		assertEquals(XmlAccessOrder.UNDEFINED, contextPackageInfo.getDefaultAccessOrder());
	}

	protected void setEnumMemberValuePair(ModifiedDeclaration declaration, String annotationName, String enumValue) {
		this.setEnumMemberValuePair((NormalAnnotation) declaration.getAnnotationNamed(annotationName), "value", enumValue);

	}

	//add another package annotation so that the context model object doesn't get removed when 
	//removing the XmlAccessorType annotation. Only "annotated" packages are added to the context model
	protected void removeXmlAccessorTypeAnnotation(ModifiedDeclaration declaration) {
		this.addMarkerAnnotation(declaration.getDeclaration(), XmlSchemaAnnotation.ANNOTATION_NAME);
		this.removeAnnotation(declaration, XmlAccessorTypeAnnotation.ANNOTATION_NAME);		
	}

	//add another package annotation so that the context model object doesn't get removed when 
	//removing the XmlAccessorOrder annotation. Only "annotated" packages are added to the context model
	protected void removeXmlAccessorOrderAnnotation(ModifiedDeclaration declaration) {
		this.addMarkerAnnotation(declaration.getDeclaration(), XmlSchemaAnnotation.ANNOTATION_NAME);
		this.removeAnnotation(declaration, XmlAccessorOrderAnnotation.ANNOTATION_NAME);		
	}
}
