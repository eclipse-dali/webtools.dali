/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.XmlJavaTypeAdapter;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorOrderAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;
import org.eclipse.jpt.jaxb.core.tests.internal.context.JaxbContextModelTestCase;


@SuppressWarnings("nls")
public class GenericJavaPackageXmlJavaTypeAdapterTests extends JaxbContextModelTestCase
{

	public GenericJavaPackageXmlJavaTypeAdapterTests(String name) {
		super(name);
	}

	private ICompilationUnit createPackageInfoWithXmlJavaTypeAdapter() throws CoreException {
		return createTestPackageInfo(
				"@XmlJavaTypeAdapter",
				JAXB.XML_JAVA_TYPE_ADAPTER);
	}


	public void testModifyValue() throws Exception {
		this.createPackageInfoWithXmlJavaTypeAdapter();
		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getContextRoot().getPackages(), 0).getPackageInfo();
		XmlJavaTypeAdapter contextXmlJavaTypeAdapter = contextPackageInfo.getXmlJavaTypeAdapters().iterator().next();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();

		assertNull(contextXmlJavaTypeAdapter.getValue());

		contextXmlJavaTypeAdapter.setValue("foo");
		XmlJavaTypeAdapterAnnotation javaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourcePackage.getAnnotation(0, XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
		assertEquals("foo", javaTypeAdapterAnnotation.getValue());
		assertEquals("foo", contextXmlJavaTypeAdapter.getValue());

		 //verify the xml schema type annotation is not removed when the value is set to null
		contextXmlJavaTypeAdapter.setValue(null);
		javaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourcePackage.getAnnotation(0, XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
		assertNull(javaTypeAdapterAnnotation.getValue());
		assertNull(contextXmlJavaTypeAdapter.getValue());
	}

	public void testUpdateValue() throws Exception {
		this.createPackageInfoWithXmlJavaTypeAdapter();
		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getContextRoot().getPackages(), 0).getPackageInfo();
		XmlJavaTypeAdapter contextXmlJavaTypeAdapter = contextPackageInfo.getXmlJavaTypeAdapters().iterator().next();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();

		assertNull(contextXmlJavaTypeAdapter.getValue());

		//add a value member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourcePackage);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPackageXmlJavaTypeAdapterTests.this.addXmlJavaTypeAdapterTypeMemberValuePair(declaration, JAXB.XML_JAVA_TYPE_ADAPTER__VALUE, "String");
			}
		});
		assertEquals("String", contextXmlJavaTypeAdapter.getValue());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPackageXmlJavaTypeAdapterTests.this.removeXmlJavaTypeAdapterAnnotation(declaration);
			}
		});
		assertFalse(contextPackageInfo.getXmlJavaTypeAdapters().iterator().hasNext());
	}

	public void testModifyType() throws Exception {
		this.createPackageInfoWithXmlJavaTypeAdapter();
		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getContextRoot().getPackages(), 0).getPackageInfo();
		XmlJavaTypeAdapter contextXmlJavaTypeAdapter = contextPackageInfo.getXmlJavaTypeAdapters().iterator().next();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();

		assertNull(contextXmlJavaTypeAdapter.getType());
		assertNull(contextXmlJavaTypeAdapter.getSpecifiedType());
		assertNull(contextXmlJavaTypeAdapter.getDefaultType());

		contextXmlJavaTypeAdapter.setSpecifiedType("foo");
		XmlJavaTypeAdapterAnnotation javaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourcePackage.getAnnotation(0, XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
		assertEquals("foo", javaTypeAdapterAnnotation.getType());
		assertEquals("foo", contextXmlJavaTypeAdapter.getType());

		 //verify the xml schema type annotation is not removed when the type is set to null
		contextXmlJavaTypeAdapter.setSpecifiedType(null);
		javaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourcePackage.getAnnotation(0, XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
		assertNull(javaTypeAdapterAnnotation.getType());
		assertNull(contextXmlJavaTypeAdapter.getType());
		assertNull(contextXmlJavaTypeAdapter.getSpecifiedType());
		assertNull(contextXmlJavaTypeAdapter.getDefaultType());
	}

	public void testUpdateType() throws Exception {
		this.createPackageInfoWithXmlJavaTypeAdapter();
		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getContextRoot().getPackages(), 0).getPackageInfo();
		XmlJavaTypeAdapter contextXmlJavaTypeAdapter = contextPackageInfo.getXmlJavaTypeAdapters().iterator().next();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();

		assertNull(contextXmlJavaTypeAdapter.getType());
		assertNull(contextXmlJavaTypeAdapter.getSpecifiedType());
		assertNull(contextXmlJavaTypeAdapter.getDefaultType());

		//add a type member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourcePackage);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPackageXmlJavaTypeAdapterTests.this.addXmlJavaTypeAdapterTypeMemberValuePair(declaration, JAXB.XML_JAVA_TYPE_ADAPTER__TYPE, "String");
			}
		});
		assertEquals("String", contextXmlJavaTypeAdapter.getType());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPackageXmlJavaTypeAdapterTests.this.removeXmlJavaTypeAdapterAnnotation(declaration);
			}
		});
		assertFalse(contextPackageInfo.getXmlJavaTypeAdapters().iterator().hasNext());
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
