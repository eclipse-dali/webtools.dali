/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal.context.java;

import java.util.ListIterator;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.XmlAccessType;
import org.eclipse.jpt.jaxb.core.context.XmlNs;
import org.eclipse.jpt.jaxb.core.context.XmlNsForm;
import org.eclipse.jpt.jaxb.core.context.XmlSchema;
import org.eclipse.jpt.jaxb.core.context.java.JavaXmlSchema;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlNsAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaAnnotation;
import org.eclipse.jpt.jaxb.core.tests.internal.context.JaxbContextModelTestCase;


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
	
	private ICompilationUnit createPackageInfoWithAccessorType() throws CoreException {
		return createTestPackageInfo(
				"@XmlAccessorType(value = XmlAccessType.PROPERTY)",
				JAXB.XML_ACCESS_TYPE, JAXB.XML_ACCESSOR_TYPE);
	}
	
	public void testModifyNamespace() throws Exception {
		createPackageInfoWithXmlSchema();
		JaxbPackageInfo contextPackageInfo = IterableTools.get(getContextRoot().getPackages(), 0).getPackageInfo();
		XmlSchema contextXmlSchema = contextPackageInfo.getXmlSchema();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();
		
		assertEquals("", contextXmlSchema.getNamespace());
		assertNull(contextXmlSchema.getSpecifiedNamespace());
		
		contextXmlSchema.setSpecifiedNamespace("foo");
		XmlSchemaAnnotation schemaAnnotation = (XmlSchemaAnnotation) resourcePackage.getAnnotation(JAXB.XML_SCHEMA);
		assertEquals("foo", schemaAnnotation.getNamespace());
		assertEquals("foo", contextXmlSchema.getNamespace());
		assertEquals("foo", contextXmlSchema.getSpecifiedNamespace());
		
		 //set another annotation so the context model is not blown away by removing the XmlSchema annotation
		contextPackageInfo.setSpecifiedAccessType(XmlAccessType.FIELD);
		contextXmlSchema.setSpecifiedNamespace(null);
		schemaAnnotation = (XmlSchemaAnnotation) resourcePackage.getAnnotation(JAXB.XML_SCHEMA);
		assertNull(schemaAnnotation.getNamespace());
		assertEquals("", contextXmlSchema.getNamespace());
		assertNull(contextXmlSchema.getSpecifiedNamespace());
		
		//set namespace again, this time starting with no XmlSchema annotation
		contextXmlSchema.setSpecifiedNamespace("foo");
		schemaAnnotation = (XmlSchemaAnnotation) resourcePackage.getAnnotation(JAXB.XML_SCHEMA);
		assertEquals("foo", schemaAnnotation.getNamespace());
		assertEquals("foo", contextXmlSchema.getSpecifiedNamespace());
		assertEquals("foo", contextXmlSchema.getNamespace());
	}
	
	public void testUpdateNamespace() throws Exception {
		this.createPackageInfoWithXmlSchema();
		JaxbPackageInfo contextPackageInfo = IterableTools.get(getContextRoot().getPackages(), 0).getPackageInfo();
		XmlSchema contextXmlSchema = contextPackageInfo.getXmlSchema();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();
		
		assertEquals("", contextXmlSchema.getNamespace());
		assertNull(contextXmlSchema.getSpecifiedNamespace());
		
		//add a namespace member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourcePackage);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSchemaTests.this.addXmlSchemaMemberValuePair(declaration, JAXB.XML_SCHEMA__NAMESPACE, "foo");
			}
		});
		assertEquals("foo", contextXmlSchema.getNamespace());
		assertEquals("foo", contextXmlSchema.getSpecifiedNamespace());
		
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSchemaTests.this.removeXmlSchemaAnnotation(declaration);
			}
		});
		contextXmlSchema = contextPackageInfo.getXmlSchema();
		assertEquals("", contextXmlSchema.getNamespace());
		assertNull(contextXmlSchema.getSpecifiedNamespace());
	}
	
	public void testModifyLocation() throws Exception {
		createPackageInfoWithXmlSchema();
		JaxbPackageInfo contextPackageInfo = IterableTools.get(getContextRoot().getPackages(), 0).getPackageInfo();
		JavaXmlSchema contextXmlSchema = contextPackageInfo.getXmlSchema();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();
	
		assertNull(contextXmlSchema.getLocation());
		
		contextXmlSchema.setLocation("foo");
		XmlSchemaAnnotation schemaAnnotation = (XmlSchemaAnnotation) resourcePackage.getAnnotation(JAXB.XML_SCHEMA);
		assertEquals("foo", schemaAnnotation.getLocation());
		assertEquals("foo", contextXmlSchema.getLocation());
		
		 //set another annotation so the context model is not blown away by removing the XmlSchema annotation
		contextPackageInfo.setSpecifiedAccessType(XmlAccessType.FIELD);
		contextXmlSchema.setLocation(null);
		schemaAnnotation = (XmlSchemaAnnotation) resourcePackage.getAnnotation(JAXB.XML_SCHEMA);
		assertNull(schemaAnnotation.getLocation());
		assertNull(contextXmlSchema.getLocation());

		//set location again, this time starting with no XmlSchema annotation
		contextXmlSchema.setLocation("foo");
		schemaAnnotation = (XmlSchemaAnnotation) resourcePackage.getAnnotation(JAXB.XML_SCHEMA);
		assertEquals("foo", schemaAnnotation.getLocation());
		assertEquals("foo", contextXmlSchema.getLocation());
	}
	
	public void testUpdateLocation() throws Exception {
		this.createPackageInfoWithXmlSchema();
		JaxbPackageInfo contextPackageInfo = IterableTools.get(getContextRoot().getPackages(), 0).getPackageInfo();
		XmlSchema contextXmlSchema = contextPackageInfo.getXmlSchema();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();

		assertNull(contextXmlSchema.getLocation());
		
		//add a location member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourcePackage);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSchemaTests.this.addXmlSchemaMemberValuePair(declaration, JAXB.XML_SCHEMA__LOCATION, "foo");
			}
		});

		assertEquals("foo", contextXmlSchema.getLocation());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSchemaTests.this.removeXmlSchemaAnnotation(declaration);
			}
		});
		contextXmlSchema = contextPackageInfo.getXmlSchema();
		assertNull(contextXmlSchema.getLocation());
	}
	
	public void testModifyAttributeFormDefault() throws Exception {
		createPackageInfoWithXmlSchema();
		JaxbPackageInfo contextPackageInfo = IterableTools.get(getContextRoot().getPackages(), 0).getPackageInfo();
		XmlSchema contextXmlSchema = contextPackageInfo.getXmlSchema();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();
		
		assertNull(contextXmlSchema.getSpecifiedAttributeFormDefault());
		assertEquals(XmlNsForm.UNSET, contextXmlSchema.getAttributeFormDefault());
		
		contextXmlSchema.setSpecifiedAttributeFormDefault(XmlNsForm.QUALIFIED);
		XmlSchemaAnnotation schemaAnnotation = (XmlSchemaAnnotation) resourcePackage.getAnnotation(JAXB.XML_SCHEMA);
		assertEquals(org.eclipse.jpt.jaxb.core.resource.java.XmlNsForm.QUALIFIED, schemaAnnotation.getAttributeFormDefault());
		assertEquals(XmlNsForm.QUALIFIED, contextXmlSchema.getAttributeFormDefault());
		
		contextXmlSchema.setSpecifiedAttributeFormDefault(XmlNsForm.UNQUALIFIED);
		assertEquals(org.eclipse.jpt.jaxb.core.resource.java.XmlNsForm.UNQUALIFIED, schemaAnnotation.getAttributeFormDefault());
		assertEquals(XmlNsForm.UNQUALIFIED, contextXmlSchema.getAttributeFormDefault());
		
		 //set another annotation so the context model is not blown away by removing the XmlSchema annotation
		contextPackageInfo.setSpecifiedAccessType(XmlAccessType.FIELD);
		contextXmlSchema.setSpecifiedAttributeFormDefault(null);
		schemaAnnotation = (XmlSchemaAnnotation) resourcePackage.getAnnotation(JAXB.XML_SCHEMA);
		assertNull(schemaAnnotation.getAttributeFormDefault());
		assertNull(contextXmlSchema.getSpecifiedAttributeFormDefault());
		
		//set attribute form default again, this time starting with no XmlSchema annotation
		contextXmlSchema.setSpecifiedAttributeFormDefault(XmlNsForm.QUALIFIED);
		schemaAnnotation = (XmlSchemaAnnotation) resourcePackage.getAnnotation(JAXB.XML_SCHEMA);
		assertEquals(org.eclipse.jpt.jaxb.core.resource.java.XmlNsForm.QUALIFIED, schemaAnnotation.getAttributeFormDefault());
		assertEquals(XmlNsForm.QUALIFIED, contextXmlSchema.getAttributeFormDefault());
	}
	
	public void testUpdateAttributeFormDefault() throws Exception {
		this.createPackageInfoWithXmlSchema();
		JaxbPackageInfo contextPackageInfo = IterableTools.get(getContextRoot().getPackages(), 0).getPackageInfo();
		XmlSchema contextXmlSchema = contextPackageInfo.getXmlSchema();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();
		
		assertNull(contextXmlSchema.getSpecifiedAttributeFormDefault());
		assertEquals(XmlNsForm.UNSET, contextXmlSchema.getAttributeFormDefault());
		
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
		
		assertEquals(XmlNsForm.QUALIFIED, contextXmlSchema.getSpecifiedAttributeFormDefault());
		assertEquals(XmlNsForm.QUALIFIED, contextXmlSchema.getAttributeFormDefault());
		
		//set the attribute form default value to UNQUALIFIED
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSchemaTests.this.setXmlSchemaEnumMemberValuePair(
					declaration,
					JAXB.XML_SCHEMA__ATTRIBUTE_FORM_DEFAULT, 
					JAXB.XML_NS_FORM__UNQUALIFIED);
			}
		});

		assertEquals(XmlNsForm.UNQUALIFIED, contextXmlSchema.getSpecifiedAttributeFormDefault());
		assertEquals(XmlNsForm.UNQUALIFIED, contextXmlSchema.getAttributeFormDefault());
		
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSchemaTests.this.removeXmlSchemaAnnotation(declaration);
			}
		});
		
		contextXmlSchema = contextPackageInfo.getXmlSchema();
		assertNull(contextXmlSchema.getSpecifiedAttributeFormDefault());
		assertEquals(XmlNsForm.UNSET, contextXmlSchema.getAttributeFormDefault());
	}
	
	public void testModifyElementFormDefault() throws Exception {
		createPackageInfoWithXmlSchema();
		JaxbPackageInfo contextPackageInfo = IterableTools.get(getContextRoot().getPackages(), 0).getPackageInfo();
		XmlSchema contextXmlSchema = contextPackageInfo.getXmlSchema();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();
		
		assertNull(contextXmlSchema.getSpecifiedElementFormDefault());
		assertEquals(XmlNsForm.UNSET, contextXmlSchema.getElementFormDefault());
		
		contextXmlSchema.setSpecifiedElementFormDefault(XmlNsForm.QUALIFIED);
		XmlSchemaAnnotation schemaAnnotation = (XmlSchemaAnnotation) resourcePackage.getAnnotation(JAXB.XML_SCHEMA);
		assertEquals(org.eclipse.jpt.jaxb.core.resource.java.XmlNsForm.QUALIFIED, schemaAnnotation.getElementFormDefault());
		assertEquals(XmlNsForm.QUALIFIED, contextXmlSchema.getElementFormDefault());
		
		contextXmlSchema.setSpecifiedElementFormDefault(XmlNsForm.UNQUALIFIED);
		assertEquals(org.eclipse.jpt.jaxb.core.resource.java.XmlNsForm.UNQUALIFIED, schemaAnnotation.getElementFormDefault());
		assertEquals(XmlNsForm.UNQUALIFIED, contextXmlSchema.getElementFormDefault());
		
		 //set another annotation so the context model is not blown away by removing the XmlSchema annotation
		contextPackageInfo.setSpecifiedAccessType(XmlAccessType.FIELD);
		contextXmlSchema.setSpecifiedElementFormDefault(null);
		schemaAnnotation = (XmlSchemaAnnotation) resourcePackage.getAnnotation(JAXB.XML_SCHEMA);
		assertNull(schemaAnnotation.getElementFormDefault());
		assertNull(contextXmlSchema.getSpecifiedElementFormDefault());
		
		//set element form default again, this time starting with no XmlSchema annotation
		contextXmlSchema.setSpecifiedElementFormDefault(XmlNsForm.QUALIFIED);
		schemaAnnotation = (XmlSchemaAnnotation) resourcePackage.getAnnotation(JAXB.XML_SCHEMA);
		assertEquals(org.eclipse.jpt.jaxb.core.resource.java.XmlNsForm.QUALIFIED, schemaAnnotation.getElementFormDefault());
		assertEquals(XmlNsForm.QUALIFIED, contextXmlSchema.getElementFormDefault());
	}
	
	public void testUpdateElementFormDefault() throws Exception {
		this.createPackageInfoWithXmlSchema();
		JaxbPackageInfo contextPackageInfo = IterableTools.get(getContextRoot().getPackages(), 0).getPackageInfo();
		XmlSchema contextXmlSchema = contextPackageInfo.getXmlSchema();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();

		assertNull(contextXmlSchema.getSpecifiedElementFormDefault());
		assertEquals(XmlNsForm.UNSET, contextXmlSchema.getElementFormDefault());
		
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
		
		assertEquals(XmlNsForm.QUALIFIED, contextXmlSchema.getSpecifiedElementFormDefault());
		assertEquals(XmlNsForm.QUALIFIED, contextXmlSchema.getElementFormDefault());
		
		//set the element form default value to UNQUALIFIED
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSchemaTests.this.setXmlSchemaEnumMemberValuePair(
					declaration,
					JAXB.XML_SCHEMA__ELEMENT_FORM_DEFAULT, 
					JAXB.XML_NS_FORM__UNQUALIFIED);
			}
		});
		
		assertEquals(XmlNsForm.UNQUALIFIED, contextXmlSchema.getSpecifiedElementFormDefault());
		assertEquals(XmlNsForm.UNQUALIFIED, contextXmlSchema.getElementFormDefault());
		
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSchemaTests.this.removeXmlSchemaAnnotation(declaration);
			}
		});
		
		contextXmlSchema = contextPackageInfo.getXmlSchema();
		assertNull(contextXmlSchema.getSpecifiedElementFormDefault());
		assertEquals(XmlNsForm.UNSET, contextXmlSchema.getElementFormDefault());
	}
	
	public void testGetXmlNsPrefixes() throws Exception {
		this.createPackageInfoWithXmlSchema();
		JaxbPackageInfo contextPackageInfo = IterableTools.get(getContextRoot().getPackages(), 0).getPackageInfo();
		XmlSchema contextXmlSchema = contextPackageInfo.getXmlSchema();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();

		ListIterable<XmlNs> xmlNsPrefixes = contextXmlSchema.getXmlNsPrefixes();
		assertFalse(xmlNsPrefixes.iterator().hasNext());

		//add 2 XmlNs prefixes
		AnnotatedElement annotatedElement = this.annotatedElement(resourcePackage);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSchemaTests.this.addXmlNs(declaration, 0, "bar", "barPrefix");
				GenericJavaXmlSchemaTests.this.addXmlNs(declaration, 1, "foo", "fooPrefix");
			}
		});

		xmlNsPrefixes = contextXmlSchema.getXmlNsPrefixes();
		ListIterator<XmlNs> xmlNsPrefixesIterator = xmlNsPrefixes.iterator();
		assertTrue(xmlNsPrefixesIterator.hasNext());
		XmlNs xmlNsPref = xmlNsPrefixesIterator.next();
		assertEquals("bar", xmlNsPref.getNamespaceURI());
		assertEquals("barPrefix", xmlNsPref.getPrefix());
		xmlNsPref = xmlNsPrefixesIterator.next();
		assertEquals("foo", xmlNsPref.getNamespaceURI());
		assertEquals("fooPrefix", xmlNsPref.getPrefix());
		assertFalse(xmlNsPrefixesIterator.hasNext());
	}
	
	protected void addXmlNs(ModifiedDeclaration declaration, int index, String namespaceURI, String prefix) {
		NormalAnnotation arrayElement = this.newXmlNsAnnotation(declaration.getAst(), namespaceURI, prefix);
		this.addArrayElement(declaration, JAXB.XML_SCHEMA, index, JAXB.XML_SCHEMA__XMLNS, arrayElement);		
	}

	protected NormalAnnotation newXmlNsAnnotation(AST ast, String namespaceURI, String prefix) {
		NormalAnnotation annotation = this.newNormalAnnotation(ast, JAXB.XML_NS);
		this.addMemberValuePair(annotation, JAXB.XML_NS__NAMESPACE_URI, namespaceURI);
		this.addMemberValuePair(annotation, JAXB.XML_NS__PREFIX, prefix);
		return annotation;
	}

	public void testGetXmlNsPrexiesSize() throws Exception {
		this.createPackageInfoWithXmlSchema();
		JaxbPackageInfo contextPackageInfo = IterableTools.get(getContextRoot().getPackages(), 0).getPackageInfo();
		XmlSchema contextXmlSchema = contextPackageInfo.getXmlSchema();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();

		assertEquals(0, contextXmlSchema.getXmlNsPrefixesSize());

		//add 2 XmlNs prefixes
		AnnotatedElement annotatedElement = this.annotatedElement(resourcePackage);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSchemaTests.this.addXmlNs(declaration, 0, "bar", "barPrefix");
				GenericJavaXmlSchemaTests.this.addXmlNs(declaration, 1, "foo", "fooPrefix");
			}
		});
		assertEquals(2, contextXmlSchema.getXmlNsPrefixesSize());
	}

	public void testAddXmlNsPrefix() throws Exception {
		//create a package info with an annotation other than XmlSchema to test
		//adding things to the null schema annotation
		this.createPackageInfoWithAccessorType();
		JaxbPackageInfo contextPackageInfo = IterableTools.get(getContextRoot().getPackages(), 0).getPackageInfo();
		XmlSchema contextXmlSchema = contextPackageInfo.getXmlSchema();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();

		XmlNs xmlNsPrefix = contextXmlSchema.addXmlNsPrefix(0);
		xmlNsPrefix.setNamespaceURI("bar");
		xmlNsPrefix.setPrefix("barPrefix");
		xmlNsPrefix = contextXmlSchema.addXmlNsPrefix(0);
		xmlNsPrefix.setNamespaceURI("foo");
		xmlNsPrefix.setPrefix("fooPrefix");
		xmlNsPrefix = contextXmlSchema.addXmlNsPrefix(0);
		xmlNsPrefix.setNamespaceURI("baz");
		xmlNsPrefix.setPrefix("bazPrefix");

		XmlSchemaAnnotation schemaAnnotation = (XmlSchemaAnnotation) resourcePackage.getAnnotation(JAXB.XML_SCHEMA);
		ListIterator<XmlNsAnnotation> xmlNsPrefixes = schemaAnnotation.getXmlns().iterator();

		XmlNsAnnotation xmlNsAnnotation = xmlNsPrefixes.next();
		assertEquals("baz", xmlNsAnnotation.getNamespaceURI());
		assertEquals("bazPrefix", xmlNsAnnotation.getPrefix());
		xmlNsAnnotation = xmlNsPrefixes.next();
		assertEquals("foo", xmlNsAnnotation.getNamespaceURI());
		assertEquals("fooPrefix", xmlNsAnnotation.getPrefix());
		xmlNsAnnotation = xmlNsPrefixes.next();
		assertEquals("bar", xmlNsAnnotation.getNamespaceURI());
		assertEquals("barPrefix", xmlNsAnnotation.getPrefix());
		assertFalse(xmlNsPrefixes.hasNext());
	}

	public void testAddXmlNsPrefix2() throws Exception {
		this.createPackageInfoWithXmlSchema();
		JaxbPackageInfo contextPackageInfo = IterableTools.get(getContextRoot().getPackages(), 0).getPackageInfo();
		XmlSchema contextXmlSchema = contextPackageInfo.getXmlSchema();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();

		XmlNs xmlNsPrefix = contextXmlSchema.addXmlNsPrefix(0);
		xmlNsPrefix.setNamespaceURI("bar");
		xmlNsPrefix.setPrefix("barPrefix");
		xmlNsPrefix = contextXmlSchema.addXmlNsPrefix(1);
		xmlNsPrefix.setNamespaceURI("foo");
		xmlNsPrefix.setPrefix("fooPrefix");
		xmlNsPrefix = contextXmlSchema.addXmlNsPrefix(0);
		xmlNsPrefix.setNamespaceURI("baz");
		xmlNsPrefix.setPrefix("bazPrefix");

		XmlSchemaAnnotation schemaAnnotation = (XmlSchemaAnnotation) resourcePackage.getAnnotation(JAXB.XML_SCHEMA);
		ListIterator<XmlNsAnnotation> xmlNsPrefixes = schemaAnnotation.getXmlns().iterator();

		XmlNsAnnotation xmlNsAnnotation = xmlNsPrefixes.next();
		assertEquals("baz", xmlNsAnnotation.getNamespaceURI());
		assertEquals("bazPrefix", xmlNsAnnotation.getPrefix());
		xmlNsAnnotation = xmlNsPrefixes.next();
		assertEquals("bar", xmlNsAnnotation.getNamespaceURI());
		assertEquals("barPrefix", xmlNsAnnotation.getPrefix());
		xmlNsAnnotation = xmlNsPrefixes.next();
		assertEquals("foo", xmlNsAnnotation.getNamespaceURI());
		assertEquals("fooPrefix", xmlNsAnnotation.getPrefix());
		assertFalse(xmlNsPrefixes.hasNext());
	}

	public void testRemoveXmlNsPrefix() throws Exception {
		this.createPackageInfoWithXmlSchema();
		JaxbPackageInfo contextPackageInfo = IterableTools.get(getContextRoot().getPackages(), 0).getPackageInfo();
		XmlSchema contextXmlSchema = contextPackageInfo.getXmlSchema();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();

		contextXmlSchema.addXmlNsPrefix(0).setNamespaceURI("bar");
		contextXmlSchema.addXmlNsPrefix(1).setNamespaceURI("foo");
		contextXmlSchema.addXmlNsPrefix(2).setNamespaceURI("baz");

		XmlSchemaAnnotation schemaAnnotation = (XmlSchemaAnnotation) resourcePackage.getAnnotation(JAXB.XML_SCHEMA);
		assertEquals(3, schemaAnnotation.getXmlnsSize());

		contextXmlSchema.removeXmlNsPrefix(1);

		ListIterator<XmlNsAnnotation> xmlNsPrefixes = schemaAnnotation.getXmlns().iterator();
		assertEquals("bar", xmlNsPrefixes.next().getNamespaceURI());		
		assertEquals("baz", xmlNsPrefixes.next().getNamespaceURI());
		assertFalse(xmlNsPrefixes.hasNext());

		contextXmlSchema.removeXmlNsPrefix(1);
		xmlNsPrefixes = schemaAnnotation.getXmlns().iterator();
		assertEquals("bar", xmlNsPrefixes.next().getNamespaceURI());
		assertFalse(xmlNsPrefixes.hasNext());

		contextXmlSchema.removeXmlNsPrefix(0);
		xmlNsPrefixes = schemaAnnotation.getXmlns().iterator();
		assertFalse(xmlNsPrefixes.hasNext());
	}

	public void testMoveXmlNsPrefix() throws Exception {
		this.createPackageInfoWithXmlSchema();
		JaxbPackageInfo contextPackageInfo = IterableTools.get(getContextRoot().getPackages(), 0).getPackageInfo();
		XmlSchema contextXmlSchema = contextPackageInfo.getXmlSchema();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();

		contextXmlSchema.addXmlNsPrefix(0).setNamespaceURI("bar");
		contextXmlSchema.addXmlNsPrefix(1).setNamespaceURI("foo");
		contextXmlSchema.addXmlNsPrefix(2).setNamespaceURI("baz");


		XmlSchemaAnnotation schemaAnnotation = (XmlSchemaAnnotation) resourcePackage.getAnnotation(JAXB.XML_SCHEMA);

		assertEquals(3, schemaAnnotation.getXmlnsSize());		

		contextXmlSchema.moveXmlNsPrefix(2, 0);
		ListIterator<XmlNs> xmlNsPrefixes = contextXmlSchema.getXmlNsPrefixes().iterator();
		assertEquals("foo", xmlNsPrefixes.next().getNamespaceURI());
		assertEquals("baz", xmlNsPrefixes.next().getNamespaceURI());
		assertEquals("bar", xmlNsPrefixes.next().getNamespaceURI());		
		assertFalse(xmlNsPrefixes.hasNext());

		ListIterator<XmlNsAnnotation> xmlNsAnnotations = schemaAnnotation.getXmlns().iterator();
		assertEquals("foo", xmlNsAnnotations.next().getNamespaceURI());
		assertEquals("baz", xmlNsAnnotations.next().getNamespaceURI());
		assertEquals("bar", xmlNsAnnotations.next().getNamespaceURI());


		contextXmlSchema.moveXmlNsPrefix(0, 1);
		xmlNsPrefixes = contextXmlSchema.getXmlNsPrefixes().iterator();
		assertEquals("baz", xmlNsPrefixes.next().getNamespaceURI());
		assertEquals("foo", xmlNsPrefixes.next().getNamespaceURI());
		assertEquals("bar", xmlNsPrefixes.next().getNamespaceURI());		
		assertFalse(xmlNsPrefixes.hasNext());

		xmlNsAnnotations = schemaAnnotation.getXmlns().iterator();
		assertEquals("baz", xmlNsAnnotations.next().getNamespaceURI());
		assertEquals("foo", xmlNsAnnotations.next().getNamespaceURI());
		assertEquals("bar", xmlNsAnnotations.next().getNamespaceURI());
	}

	public void testSyncXmlNsPrefixes() throws Exception {
		this.createPackageInfoWithXmlSchema();
		JaxbPackageInfo contextPackageInfo = IterableTools.get(getContextRoot().getPackages(), 0).getPackageInfo();
		XmlSchema contextXmlSchema = contextPackageInfo.getXmlSchema();
		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();

		ListIterable<XmlNs> xmlNsPrefixes = contextXmlSchema.getXmlNsPrefixes();
		assertFalse(xmlNsPrefixes.iterator().hasNext());

		//add 3 XmlNs prefixes
		AnnotatedElement annotatedElement = this.annotatedElement(resourcePackage);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSchemaTests.this.addXmlNs(declaration, 0, "bar", "barPrefix");
				GenericJavaXmlSchemaTests.this.addXmlNs(declaration, 1, "foo", "fooPrefix");
				GenericJavaXmlSchemaTests.this.addXmlNs(declaration, 2, "baz", "bazPrefix");
			}
		});

		xmlNsPrefixes = contextXmlSchema.getXmlNsPrefixes();
		ListIterator<XmlNs> xmlNsPrefixesIterator = xmlNsPrefixes.iterator();
		assertTrue(xmlNsPrefixesIterator.hasNext());
		XmlNs xmlNsPref = xmlNsPrefixesIterator.next();
		assertEquals("bar", xmlNsPref.getNamespaceURI());
		assertEquals("barPrefix", xmlNsPref.getPrefix());
		xmlNsPref = xmlNsPrefixesIterator.next();
		assertEquals("foo", xmlNsPref.getNamespaceURI());
		assertEquals("fooPrefix", xmlNsPref.getPrefix());
		xmlNsPref = xmlNsPrefixesIterator.next();
		assertEquals("baz", xmlNsPref.getNamespaceURI());
		assertEquals("bazPrefix", xmlNsPref.getPrefix());
		assertFalse(xmlNsPrefixesIterator.hasNext());


		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSchemaTests.this.moveXmlNsPrefix(declaration, 2, 0);
			}
		});

		xmlNsPrefixesIterator = xmlNsPrefixes.iterator();
		assertTrue(xmlNsPrefixesIterator.hasNext());
		xmlNsPref = xmlNsPrefixesIterator.next();
		assertEquals("foo", xmlNsPref.getNamespaceURI());
		assertEquals("fooPrefix", xmlNsPref.getPrefix());
		xmlNsPref = xmlNsPrefixesIterator.next();
		assertEquals("baz", xmlNsPref.getNamespaceURI());
		assertEquals("bazPrefix", xmlNsPref.getPrefix());
		xmlNsPref = xmlNsPrefixesIterator.next();
		assertEquals("bar", xmlNsPref.getNamespaceURI());
		assertEquals("barPrefix", xmlNsPref.getPrefix());
		assertFalse(xmlNsPrefixesIterator.hasNext());


		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSchemaTests.this.moveXmlNsPrefix(declaration, 0, 1);
			}
		});

		xmlNsPrefixesIterator = xmlNsPrefixes.iterator();
		assertTrue(xmlNsPrefixesIterator.hasNext());
		xmlNsPref = xmlNsPrefixesIterator.next();
		assertEquals("baz", xmlNsPref.getNamespaceURI());
		assertEquals("bazPrefix", xmlNsPref.getPrefix());
		xmlNsPref = xmlNsPrefixesIterator.next();
		assertEquals("foo", xmlNsPref.getNamespaceURI());
		assertEquals("fooPrefix", xmlNsPref.getPrefix());
		xmlNsPref = xmlNsPrefixesIterator.next();
		assertEquals("bar", xmlNsPref.getNamespaceURI());
		assertEquals("barPrefix", xmlNsPref.getPrefix());
		assertFalse(xmlNsPrefixesIterator.hasNext());


		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSchemaTests.this.removeXmlNsPrefix(declaration, 1);
			}
		});

		xmlNsPrefixesIterator = xmlNsPrefixes.iterator();
		assertTrue(xmlNsPrefixesIterator.hasNext());
		xmlNsPref = xmlNsPrefixesIterator.next();
		assertEquals("baz", xmlNsPref.getNamespaceURI());
		assertEquals("bazPrefix", xmlNsPref.getPrefix());
		xmlNsPref = xmlNsPrefixesIterator.next();
		assertEquals("bar", xmlNsPref.getNamespaceURI());
		assertEquals("barPrefix", xmlNsPref.getPrefix());
		assertFalse(xmlNsPrefixesIterator.hasNext());


		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSchemaTests.this.removeXmlNsPrefix(declaration, 1);
			}
		});

		xmlNsPrefixesIterator = xmlNsPrefixes.iterator();
		assertTrue(xmlNsPrefixesIterator.hasNext());
		xmlNsPref = xmlNsPrefixesIterator.next();
		assertEquals("baz", xmlNsPref.getNamespaceURI());
		assertEquals("bazPrefix", xmlNsPref.getPrefix());
		assertFalse(xmlNsPrefixesIterator.hasNext());


		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlSchemaTests.this.removeXmlNsPrefix(declaration, 0);
			}
		});

		xmlNsPrefixesIterator = xmlNsPrefixes.iterator();
		assertFalse(xmlNsPrefixesIterator.hasNext());
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
		this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_ACCESSOR_ORDER);
		this.removeAnnotation(declaration, JAXB.XML_SCHEMA);
	}

	protected Annotation getXmlSchemaAnnotation(ModifiedDeclaration declaration) {
		return declaration.getAnnotationNamed(JAXB.XML_SCHEMA);
	}

	protected void moveXmlNsPrefix(ModifiedDeclaration declaration, int targetIndex, int sourceIndex) {
		this.moveArrayElement((NormalAnnotation) getXmlSchemaAnnotation(declaration), JAXB.XML_SCHEMA__XMLNS, targetIndex, sourceIndex);
	}

	protected void removeXmlNsPrefix(ModifiedDeclaration declaration, int index) {
		this.removeArrayElement((NormalAnnotation) getXmlSchemaAnnotation(declaration), JAXB.XML_SCHEMA__XMLNS, index);
	}
}
