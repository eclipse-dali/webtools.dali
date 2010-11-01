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
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jpt.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.XmlAccessType;
import org.eclipse.jpt.jaxb.core.context.XmlNsForm;
import org.eclipse.jpt.jaxb.core.context.XmlSchema;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorOrderAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaAnnotation;
import org.eclipse.jpt.jaxb.core.tests.internal.context.JaxbContextModelTestCase;
import org.eclipse.jpt.utility.internal.CollectionTools;


@SuppressWarnings("nls")
public class GenericJavaXmlSchemaTests extends JaxbContextModelTestCase
{
	
	public GenericJavaXmlSchemaTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createPackageInfoWithXmlSchema() throws CoreException {
		return createTestPackageInfo(
				"@XmlSchema",
				JAXB.XML_SCHEMA);
	}
	
	public void testModifyNamespace() throws Exception {
		createPackageInfoWithXmlSchema();
		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getRootContextNode().getPackages(), 0).getPackageInfo();
		XmlSchema contextXmlSchema = contextPackageInfo.getXmlSchema();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();
	
		assertNull(contextXmlSchema.getNamespace());
		
		contextXmlSchema.setNamespace("foo");
		XmlSchemaAnnotation schemaAnnotation = (XmlSchemaAnnotation) resourcePackage.getAnnotation(XmlSchemaAnnotation.ANNOTATION_NAME);
		assertEquals("foo", schemaAnnotation.getNamespace());
		assertEquals("foo", contextXmlSchema.getNamespace());
		
		 //set another annotation so the context model is not blown away by removing the XmlSchema annotation
		contextPackageInfo.setSpecifiedAccessType(XmlAccessType.FIELD);
		contextXmlSchema.setNamespace(null);
		schemaAnnotation = (XmlSchemaAnnotation) resourcePackage.getAnnotation(XmlSchemaAnnotation.ANNOTATION_NAME);
		assertNull(schemaAnnotation);
		assertNull(contextXmlSchema.getNamespace());
		
		//set namespace again, this time starting with no XmlSchema annotation
		contextXmlSchema.setNamespace("foo");
		schemaAnnotation = (XmlSchemaAnnotation) resourcePackage.getAnnotation(XmlSchemaAnnotation.ANNOTATION_NAME);
		assertEquals("foo", schemaAnnotation.getNamespace());
		assertEquals("foo", contextXmlSchema.getNamespace());
	}
	
	public void testUpdateNamespace() throws Exception {
		this.createPackageInfoWithXmlSchema();
		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getRootContextNode().getPackages(), 0).getPackageInfo();
		XmlSchema contextXmlSchema = contextPackageInfo.getXmlSchema();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();

		assertNull(contextXmlSchema.getNamespace());
		
		//add a namespace member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourcePackage);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSchemaTests.this.addXmlSchemaMemberValuePair(declaration, JAXB.XML_SCHEMA__NAMESPACE, "foo");
			}
		});
		assertEquals("foo", contextXmlSchema.getNamespace());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSchemaTests.this.removeXmlSchemaAnnotation(declaration);
			}
		});
		contextXmlSchema = contextPackageInfo.getXmlSchema();
		assertNull(contextXmlSchema.getNamespace());
	}
	
	public void testModifyLocation() throws Exception {
		createPackageInfoWithXmlSchema();
		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getRootContextNode().getPackages(), 0).getPackageInfo();
		XmlSchema contextXmlSchema = contextPackageInfo.getXmlSchema();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();
	
		assertEquals(XmlSchema.DEFAULT_LOCATION, contextXmlSchema.getDefaultLocation());
		assertEquals(XmlSchema.DEFAULT_LOCATION, contextXmlSchema.getLocation());
		assertNull(contextXmlSchema.getSpecifiedLocation());
		
		contextXmlSchema.setSpecifiedLocation("foo");
		XmlSchemaAnnotation schemaAnnotation = (XmlSchemaAnnotation) resourcePackage.getAnnotation(XmlSchemaAnnotation.ANNOTATION_NAME);
		assertEquals("foo", schemaAnnotation.getLocation());
		assertEquals(XmlSchema.DEFAULT_LOCATION, contextXmlSchema.getDefaultLocation());
		assertEquals("foo", contextXmlSchema.getLocation());
		assertEquals("foo", contextXmlSchema.getSpecifiedLocation());
		
		 //set another annotation so the context model is not blown away by removing the XmlSchema annotation
		contextPackageInfo.setSpecifiedAccessType(XmlAccessType.FIELD);
		contextXmlSchema.setSpecifiedLocation(null);
		schemaAnnotation = (XmlSchemaAnnotation) resourcePackage.getAnnotation(XmlSchemaAnnotation.ANNOTATION_NAME);
		assertNull(schemaAnnotation);
		assertEquals(XmlSchema.DEFAULT_LOCATION, contextXmlSchema.getDefaultLocation());
		assertEquals(XmlSchema.DEFAULT_LOCATION, contextXmlSchema.getLocation());
		assertNull(contextXmlSchema.getSpecifiedLocation());

		//set location again, this time starting with no XmlSchema annotation
		contextXmlSchema.setSpecifiedLocation("foo");
		schemaAnnotation = (XmlSchemaAnnotation) resourcePackage.getAnnotation(XmlSchemaAnnotation.ANNOTATION_NAME);
		assertEquals("foo", schemaAnnotation.getLocation());
		assertEquals(XmlSchema.DEFAULT_LOCATION, contextXmlSchema.getDefaultLocation());
		assertEquals("foo", contextXmlSchema.getLocation());
		assertEquals("foo", contextXmlSchema.getSpecifiedLocation());
	}
	
	public void testUpdateLocation() throws Exception {
		this.createPackageInfoWithXmlSchema();
		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getRootContextNode().getPackages(), 0).getPackageInfo();
		XmlSchema contextXmlSchema = contextPackageInfo.getXmlSchema();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();

		assertEquals(XmlSchema.DEFAULT_LOCATION, contextXmlSchema.getDefaultLocation());
		assertEquals(XmlSchema.DEFAULT_LOCATION, contextXmlSchema.getLocation());
		assertNull(contextXmlSchema.getSpecifiedLocation());
		
		//add a location member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourcePackage);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSchemaTests.this.addXmlSchemaMemberValuePair(declaration, JAXB.XML_SCHEMA__LOCATION, "foo");
			}
		});

		assertEquals(XmlSchema.DEFAULT_LOCATION, contextXmlSchema.getDefaultLocation());
		assertEquals("foo", contextXmlSchema.getLocation());
		assertEquals("foo", contextXmlSchema.getSpecifiedLocation());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSchemaTests.this.removeXmlSchemaAnnotation(declaration);
			}
		});
		contextXmlSchema = contextPackageInfo.getXmlSchema();
		assertEquals(XmlSchema.DEFAULT_LOCATION, contextXmlSchema.getDefaultLocation());
		assertEquals(XmlSchema.DEFAULT_LOCATION, contextXmlSchema.getLocation());
		assertNull(contextXmlSchema.getSpecifiedLocation());
	}
	
	public void testModifyAttributeFormDefault() throws Exception {
		createPackageInfoWithXmlSchema();
		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getRootContextNode().getPackages(), 0).getPackageInfo();
		XmlSchema contextXmlSchema = contextPackageInfo.getXmlSchema();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();
	
		assertEquals(XmlNsForm.UNSET, contextXmlSchema.getDefaultAttributeFormDefault());
		assertEquals(XmlNsForm.UNSET, contextXmlSchema.getAttributeFormDefault());
		assertNull(contextXmlSchema.getSpecifiedAttributeFormDefault());
		
		contextXmlSchema.setSpecifiedAttributeFormDefault(XmlNsForm.QUALIFIED);
		XmlSchemaAnnotation schemaAnnotation = (XmlSchemaAnnotation) resourcePackage.getAnnotation(XmlSchemaAnnotation.ANNOTATION_NAME);
		assertEquals(org.eclipse.jpt.jaxb.core.resource.java.XmlNsForm.QUALIFIED, schemaAnnotation.getAttributeFormDefault());
		assertEquals(XmlNsForm.UNSET, contextXmlSchema.getDefaultAttributeFormDefault());
		assertEquals(XmlNsForm.QUALIFIED, contextXmlSchema.getAttributeFormDefault());
		assertEquals(XmlNsForm.QUALIFIED, contextXmlSchema.getSpecifiedAttributeFormDefault());
		
		contextXmlSchema.setSpecifiedAttributeFormDefault(XmlNsForm.UNQUALIFIED);
		assertEquals(org.eclipse.jpt.jaxb.core.resource.java.XmlNsForm.UNQUALIFIED, schemaAnnotation.getAttributeFormDefault());
		assertEquals(XmlNsForm.UNSET, contextXmlSchema.getDefaultAttributeFormDefault());
		assertEquals(XmlNsForm.UNQUALIFIED, contextXmlSchema.getAttributeFormDefault());
		assertEquals(XmlNsForm.UNQUALIFIED, contextXmlSchema.getSpecifiedAttributeFormDefault());
		
		 //set another annotation so the context model is not blown away by removing the XmlSchema annotation
		contextPackageInfo.setSpecifiedAccessType(XmlAccessType.FIELD);
		contextXmlSchema.setSpecifiedAttributeFormDefault(null);
		schemaAnnotation = (XmlSchemaAnnotation) resourcePackage.getAnnotation(XmlSchemaAnnotation.ANNOTATION_NAME);
		assertNull(schemaAnnotation);
		assertEquals(XmlNsForm.UNSET, contextXmlSchema.getDefaultAttributeFormDefault());
		assertEquals(XmlNsForm.UNSET, contextXmlSchema.getAttributeFormDefault());
		assertNull(contextXmlSchema.getSpecifiedAttributeFormDefault());

		//set attribute form default again, this time starting with no XmlSchema annotation
		contextXmlSchema.setSpecifiedAttributeFormDefault(XmlNsForm.QUALIFIED);
		schemaAnnotation = (XmlSchemaAnnotation) resourcePackage.getAnnotation(XmlSchemaAnnotation.ANNOTATION_NAME);
		assertEquals(org.eclipse.jpt.jaxb.core.resource.java.XmlNsForm.QUALIFIED, schemaAnnotation.getAttributeFormDefault());
		assertEquals(XmlNsForm.UNSET, contextXmlSchema.getDefaultAttributeFormDefault());
		assertEquals(XmlNsForm.QUALIFIED, contextXmlSchema.getAttributeFormDefault());
		assertEquals(XmlNsForm.QUALIFIED, contextXmlSchema.getSpecifiedAttributeFormDefault());
	}
	
	public void testUpdateAttributeFormDefault() throws Exception {
		this.createPackageInfoWithXmlSchema();
		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getRootContextNode().getPackages(), 0).getPackageInfo();
		XmlSchema contextXmlSchema = contextPackageInfo.getXmlSchema();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();

		assertEquals(XmlNsForm.UNSET, contextXmlSchema.getDefaultAttributeFormDefault());
		assertEquals(XmlNsForm.UNSET, contextXmlSchema.getAttributeFormDefault());
		assertNull(contextXmlSchema.getSpecifiedAttributeFormDefault());
		
		//set the attribute form default value to QUALIFIED
		AnnotatedElement annotatedElement = this.annotatedElement(resourcePackage);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSchemaTests.this.addXmlSchemaEnumMemberValuePair(
					declaration,
					JAXB.XML_SCHEMA__ATTRIBUTE_FORM_DEFAULT, 
					JAXB.XML_NS_FORM__QUALIFIED);
			}
		});

		assertEquals(XmlNsForm.UNSET, contextXmlSchema.getDefaultAttributeFormDefault());
		assertEquals(XmlNsForm.QUALIFIED, contextXmlSchema.getAttributeFormDefault());
		assertEquals(XmlNsForm.QUALIFIED, contextXmlSchema.getSpecifiedAttributeFormDefault());

		
		//set the attribute form default value to UNQUALIFIED
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSchemaTests.this.setXmlSchemaEnumMemberValuePair(
					declaration,
					JAXB.XML_SCHEMA__ATTRIBUTE_FORM_DEFAULT, 
					JAXB.XML_NS_FORM__UNQUALIFIED);
			}
		});

		assertEquals(XmlNsForm.UNSET, contextXmlSchema.getDefaultAttributeFormDefault());
		assertEquals(XmlNsForm.UNQUALIFIED, contextXmlSchema.getAttributeFormDefault());
		assertEquals(XmlNsForm.UNQUALIFIED, contextXmlSchema.getSpecifiedAttributeFormDefault());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSchemaTests.this.removeXmlSchemaAnnotation(declaration);
			}
		});

		contextXmlSchema = contextPackageInfo.getXmlSchema();
		assertEquals(XmlNsForm.UNSET, contextXmlSchema.getDefaultAttributeFormDefault());
		assertEquals(XmlNsForm.UNSET, contextXmlSchema.getAttributeFormDefault());
		assertNull(contextXmlSchema.getSpecifiedAttributeFormDefault());
	}
	
	public void testModifyElementFormDefault() throws Exception {
		createPackageInfoWithXmlSchema();
		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getRootContextNode().getPackages(), 0).getPackageInfo();
		XmlSchema contextXmlSchema = contextPackageInfo.getXmlSchema();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();
	
		assertEquals(XmlNsForm.UNSET, contextXmlSchema.getDefaultElementFormDefault());
		assertEquals(XmlNsForm.UNSET, contextXmlSchema.getElementFormDefault());
		assertNull(contextXmlSchema.getSpecifiedElementFormDefault());
		
		contextXmlSchema.setSpecifiedElementFormDefault(XmlNsForm.QUALIFIED);
		XmlSchemaAnnotation schemaAnnotation = (XmlSchemaAnnotation) resourcePackage.getAnnotation(XmlSchemaAnnotation.ANNOTATION_NAME);
		assertEquals(org.eclipse.jpt.jaxb.core.resource.java.XmlNsForm.QUALIFIED, schemaAnnotation.getElementFormDefault());
		assertEquals(XmlNsForm.UNSET, contextXmlSchema.getDefaultElementFormDefault());
		assertEquals(XmlNsForm.QUALIFIED, contextXmlSchema.getElementFormDefault());
		assertEquals(XmlNsForm.QUALIFIED, contextXmlSchema.getSpecifiedElementFormDefault());
		
		contextXmlSchema.setSpecifiedElementFormDefault(XmlNsForm.UNQUALIFIED);
		assertEquals(org.eclipse.jpt.jaxb.core.resource.java.XmlNsForm.UNQUALIFIED, schemaAnnotation.getElementFormDefault());
		assertEquals(XmlNsForm.UNSET, contextXmlSchema.getDefaultElementFormDefault());
		assertEquals(XmlNsForm.UNQUALIFIED, contextXmlSchema.getElementFormDefault());
		assertEquals(XmlNsForm.UNQUALIFIED, contextXmlSchema.getSpecifiedElementFormDefault());
		
		 //set another annotation so the context model is not blown away by removing the XmlSchema annotation
		contextPackageInfo.setSpecifiedAccessType(XmlAccessType.FIELD);
		contextXmlSchema.setSpecifiedElementFormDefault(null);
		schemaAnnotation = (XmlSchemaAnnotation) resourcePackage.getAnnotation(XmlSchemaAnnotation.ANNOTATION_NAME);
		assertNull(schemaAnnotation);
		assertEquals(XmlNsForm.UNSET, contextXmlSchema.getDefaultElementFormDefault());
		assertEquals(XmlNsForm.UNSET, contextXmlSchema.getElementFormDefault());
		assertNull(contextXmlSchema.getSpecifiedElementFormDefault());

		//set element form default again, this time starting with no XmlSchema annotation
		contextXmlSchema.setSpecifiedElementFormDefault(XmlNsForm.QUALIFIED);
		schemaAnnotation = (XmlSchemaAnnotation) resourcePackage.getAnnotation(XmlSchemaAnnotation.ANNOTATION_NAME);
		assertEquals(org.eclipse.jpt.jaxb.core.resource.java.XmlNsForm.QUALIFIED, schemaAnnotation.getElementFormDefault());
		assertEquals(XmlNsForm.UNSET, contextXmlSchema.getDefaultElementFormDefault());
		assertEquals(XmlNsForm.QUALIFIED, contextXmlSchema.getElementFormDefault());
		assertEquals(XmlNsForm.QUALIFIED, contextXmlSchema.getSpecifiedElementFormDefault());
	}
	
	public void testUpdateElementFormDefault() throws Exception {
		this.createPackageInfoWithXmlSchema();
		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getRootContextNode().getPackages(), 0).getPackageInfo();
		XmlSchema contextXmlSchema = contextPackageInfo.getXmlSchema();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();

		assertEquals(XmlNsForm.UNSET, contextXmlSchema.getDefaultElementFormDefault());
		assertEquals(XmlNsForm.UNSET, contextXmlSchema.getElementFormDefault());
		assertNull(contextXmlSchema.getSpecifiedElementFormDefault());
		
		//set the element form default value to QUALIFIED
		AnnotatedElement annotatedElement = this.annotatedElement(resourcePackage);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSchemaTests.this.addXmlSchemaEnumMemberValuePair(
					declaration,
					JAXB.XML_SCHEMA__ELEMENT_FORM_DEFAULT, 
					JAXB.XML_NS_FORM__QUALIFIED);
			}
		});

		assertEquals(XmlNsForm.UNSET, contextXmlSchema.getDefaultElementFormDefault());
		assertEquals(XmlNsForm.QUALIFIED, contextXmlSchema.getElementFormDefault());
		assertEquals(XmlNsForm.QUALIFIED, contextXmlSchema.getSpecifiedElementFormDefault());

		
		//set the element form default value to UNQUALIFIED
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSchemaTests.this.setXmlSchemaEnumMemberValuePair(
					declaration,
					JAXB.XML_SCHEMA__ELEMENT_FORM_DEFAULT, 
					JAXB.XML_NS_FORM__UNQUALIFIED);
			}
		});

		assertEquals(XmlNsForm.UNSET, contextXmlSchema.getDefaultElementFormDefault());
		assertEquals(XmlNsForm.UNQUALIFIED, contextXmlSchema.getElementFormDefault());
		assertEquals(XmlNsForm.UNQUALIFIED, contextXmlSchema.getSpecifiedElementFormDefault());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSchemaTests.this.removeXmlSchemaAnnotation(declaration);
			}
		});

		contextXmlSchema = contextPackageInfo.getXmlSchema();
		assertEquals(XmlNsForm.UNSET, contextXmlSchema.getDefaultElementFormDefault());
		assertEquals(XmlNsForm.UNSET, contextXmlSchema.getElementFormDefault());
		assertNull(contextXmlSchema.getSpecifiedElementFormDefault());
	}

	protected void addXmlSchemaEnumMemberValuePair(ModifiedDeclaration declaration, String elementName, String value) {
		this.addEnumMemberValuePair((MarkerAnnotation) this.getXmlSchemaAnnotation(declaration), elementName, value);
	}

	protected void setXmlSchemaEnumMemberValuePair(ModifiedDeclaration declaration, String elementName, String enumValue) {
		this.setEnumMemberValuePair((NormalAnnotation) this.getXmlSchemaAnnotation(declaration), elementName, enumValue);
	}

	protected void addXmlSchemaMemberValuePair(ModifiedDeclaration declaration, String name, String value) {
		this.addMemberValuePair((MarkerAnnotation) this.getXmlSchemaAnnotation(declaration), name, value);
	}

	//add another package annotation so that the context model object doesn't get removed when 
	//removing the XmlSchema annotation. Only "annotated" packages are added to the context model
	protected void removeXmlSchemaAnnotation(ModifiedDeclaration declaration) {
		this.addMarkerAnnotation(declaration.getDeclaration(), XmlAccessorOrderAnnotation.ANNOTATION_NAME);
		this.removeAnnotation(declaration, XmlSchemaAnnotation.ANNOTATION_NAME);		
	}

	protected Annotation getXmlSchemaAnnotation(ModifiedDeclaration declaration) {
		return declaration.getAnnotationNamed(XmlSchemaAnnotation.ANNOTATION_NAME);
	}

}
