/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal.context;

import java.util.Iterator;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jpt.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentClass;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;


@SuppressWarnings("nls")
public class GenericRootContextNodeTests extends JaxbContextModelTestCase
{
	
	public GenericRootContextNodeTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createPackageInfoWithAccessorOrder() throws CoreException {
		return createTestPackageInfo(
				"@XmlAccessorOrder(value = XmlAccessOrder.ALPHABETICAL)",
				JAXB.XML_ACCESS_ORDER, JAXB.XML_ACCESSOR_ORDER);
	}

	private ICompilationUnit createUnannotatedPackageInfo(String packageName) throws CoreException {
		return createTestPackageInfo(packageName);
	}

	private ICompilationUnit createTypeWithXmlType() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType");
			}
		});
	}
	
	private ICompilationUnit createUnannotatedTestTypeNamed(String typeName) throws Exception {
		return this.createTestType(PACKAGE_NAME, typeName + ".java", typeName, new DefaultAnnotationWriter());
	}

	public void testGetPackages() throws Exception {
		this.createPackageInfoWithAccessorOrder();
		Iterator<JaxbPackage> packages = this.getRootContextNode().getPackages().iterator();
		assertEquals(1, this.getRootContextNode().getPackagesSize());
		assertEquals(PACKAGE_NAME, packages.next().getName());
		assertFalse(packages.hasNext());

		//add an unannotated package-info.java and make sure it's not added to the root context node
		this.createUnannotatedPackageInfo("foo");
		packages = this.getRootContextNode().getPackages().iterator();
		assertEquals(1, this.getRootContextNode().getPackagesSize());
		assertEquals(PACKAGE_NAME, packages.next().getName());
		assertFalse(packages.hasNext());

		//annotate the package-info.java and test it's added to the root context node
		JavaResourcePackage fooResourcePackage = getJaxbProject().getJavaResourcePackage("foo");
		AnnotatedElement annotatedElement = this.annotatedElement(fooResourcePackage);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericRootContextNodeTests.this.addXmlAccessorTypeAnnotation(declaration, JAXB.XML_ACCESS_TYPE__PROPERTY);
			}
		});

		packages = this.getRootContextNode().getPackages().iterator();
		assertEquals(2, this.getRootContextNode().getPackagesSize());
		assertEquals(PACKAGE_NAME, packages.next().getName());
		assertEquals("foo", packages.next().getName());
		assertFalse(packages.hasNext());

		//remove the annotation from the package-info.java and test it's removed from the root context node
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericRootContextNodeTests.this.removeXmlAccessorTypeAnnotation(declaration);
			}
		});

		packages = this.getRootContextNode().getPackages().iterator();
		assertEquals(1, this.getRootContextNode().getPackagesSize());
		assertEquals(PACKAGE_NAME, packages.next().getName());
		assertFalse(packages.hasNext());
	}
	
	protected void addXmlAccessorTypeAnnotation(ModifiedDeclaration declaration, String accessType) {
		NormalAnnotation annotation = this.addNormalAnnotation(declaration.getDeclaration(), JAXB.XML_ACCESSOR_TYPE);
		this.addEnumMemberValuePair(annotation, JAXB.XML_ACCESSOR_TYPE__VALUE, accessType);
	}

	protected void removeXmlAccessorTypeAnnotation(ModifiedDeclaration declaration) {
		this.removeAnnotation(declaration, JAXB.XML_ACCESSOR_TYPE);
	}

	public void testGetPersistentClasses() throws Exception {
		this.createTypeWithXmlType();
		Iterator<JaxbPersistentClass> persistentClasses = this.getRootContextNode().getPersistentClasses().iterator();
		assertEquals(1, this.getRootContextNode().getPersistentClassesSize());
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, persistentClasses.next().getName());
		assertFalse(persistentClasses.hasNext());

		//add an unannotated class and make sure it's not added to the root context node
		this.createUnannotatedTestTypeNamed("Foo");
		persistentClasses = this.getRootContextNode().getPersistentClasses().iterator();
		assertEquals(1, this.getRootContextNode().getPersistentClassesSize());
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, persistentClasses.next().getName());
		assertFalse(persistentClasses.hasNext());

		//annotate the class with @XmlType and test it's added to the root context node
		JavaResourceType fooResourcePackage = getJaxbProject().getJavaResourceType("test.Foo");
		AnnotatedElement annotatedElement = this.annotatedElement(fooResourcePackage);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericRootContextNodeTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_TYPE);
			}
		});

		persistentClasses = this.getRootContextNode().getPersistentClasses().iterator();
		assertEquals(2, this.getRootContextNode().getPersistentClassesSize());
		assertEquals("test.Foo", persistentClasses.next().getName());
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, persistentClasses.next().getName());
		assertFalse(persistentClasses.hasNext());

		//remove the annotation from the package-info.java and test it's removed from the root context node
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericRootContextNodeTests.this.removeAnnotation(declaration, JAXB.XML_TYPE);
			}
		});

		persistentClasses = this.getRootContextNode().getPersistentClasses().iterator();
		assertEquals(1, this.getRootContextNode().getPersistentClassesSize());
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, persistentClasses.next().getName());
		assertFalse(persistentClasses.hasNext());
	}

//	public void testGetXmlSchemaTypesSize() throws Exception {
//		this.createPackageInfoWithAccessorOrder();
//		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getRootContextNode().getPackages(), 0).getPackageInfo();
//		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();
//
//		assertEquals(0, contextPackageInfo.getXmlSchemaTypesSize());
//
//		//add 2 XmlSchemaTypes
//		AnnotatedElement annotatedElement = this.annotatedElement(resourcePackage);
//		annotatedElement.edit(new Member.Editor() {
//			public void edit(ModifiedDeclaration declaration) {
//				GenericRootContextNodeTests.this.addXmlSchemaType(declaration, 0, "bar");
//				GenericRootContextNodeTests.this.addXmlSchemaType(declaration, 1, "foo");
//			}
//		});
//		assertEquals(2, contextPackageInfo.getXmlSchemaTypesSize());
//	}
//
//	public void testAddXmlSchemaType() throws Exception {
//		//create a package info with an annotation other than XmlSchema to test
//		//adding things to the null schema annotation
//		this.createPackageInfoWithAccessorType();
//		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getRootContextNode().getPackages(), 0).getPackageInfo();
//		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();
//
//		contextPackageInfo.addXmlSchemaType(0).setName("bar");
//		contextPackageInfo.addXmlSchemaType(0).setName("foo");
//		contextPackageInfo.addXmlSchemaType(0).setName("baz");
//
//		Iterator<XmlSchemaTypeAnnotation> xmlSchemaTypes = this.getSchemaTypeAnnotations(resourcePackage);
//
//		assertEquals("baz", xmlSchemaTypes.next().getName());
//		assertEquals("foo", xmlSchemaTypes.next().getName());
//		assertEquals("bar", xmlSchemaTypes.next().getName());
//		assertFalse(xmlSchemaTypes.hasNext());
//	}
//
//	@SuppressWarnings("unchecked")
//	protected Iterator<XmlSchemaTypeAnnotation> getSchemaTypeAnnotations(JavaResourcePackage resourcePackage) {
//		return (Iterator<XmlSchemaTypeAnnotation>) resourcePackage.getAnnotations(JAXB.XML_SCHEMA_TYPE).iterator();
//	}
//
//	public void testAddXmlSchemaType2() throws Exception {
//		//create a package info with an annotation other than XmlSchema to test
//		//adding things to the null schema annotation
//		this.createPackageInfoWithAccessorType();
//		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getRootContextNode().getPackages(), 0).getPackageInfo();
//		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();
//
//		contextPackageInfo.addXmlSchemaType(0).setName("bar");
//		contextPackageInfo.addXmlSchemaType(1).setName("foo");
//		contextPackageInfo.addXmlSchemaType(0).setName("baz");
//
//		Iterator<XmlSchemaTypeAnnotation> xmlSchemaTypes = this.getSchemaTypeAnnotations(resourcePackage);
//
//		assertEquals("baz", xmlSchemaTypes.next().getName());
//		assertEquals("bar", xmlSchemaTypes.next().getName());
//		assertEquals("foo", xmlSchemaTypes.next().getName());
//		assertFalse(xmlSchemaTypes.hasNext());
//	}
//
//	public void testRemoveXmlSchemaType() throws Exception {
//		this.createPackageInfoWithAccessorType();
//		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getRootContextNode().getPackages(), 0).getPackageInfo();
//		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();
//
//		contextPackageInfo.addXmlSchemaType(0).setName("bar");
//		contextPackageInfo.addXmlSchemaType(1).setName("foo");
//		contextPackageInfo.addXmlSchemaType(2).setName("baz");
//
//		Iterator<XmlSchemaTypeAnnotation> xmlSchemaTypes = this.getSchemaTypeAnnotations(resourcePackage);
//		assertEquals("bar", xmlSchemaTypes.next().getName());		
//		assertEquals("foo", xmlSchemaTypes.next().getName());		
//		assertEquals("baz", xmlSchemaTypes.next().getName());
//		assertFalse(xmlSchemaTypes.hasNext());
//
//		contextPackageInfo.removeXmlSchemaType(1);
//
//		xmlSchemaTypes = this.getSchemaTypeAnnotations(resourcePackage);
//		assertEquals("bar", xmlSchemaTypes.next().getName());		
//		assertEquals("baz", xmlSchemaTypes.next().getName());
//		assertFalse(xmlSchemaTypes.hasNext());
//
//		contextPackageInfo.removeXmlSchemaType(1);
//		xmlSchemaTypes = this.getSchemaTypeAnnotations(resourcePackage);
//		assertEquals("bar", xmlSchemaTypes.next().getName());
//		assertFalse(xmlSchemaTypes.hasNext());
//
//		contextPackageInfo.removeXmlSchemaType(0);
//		xmlSchemaTypes = this.getSchemaTypeAnnotations(resourcePackage);
//		assertFalse(xmlSchemaTypes.hasNext());
//	}
//
//	public void testMoveXmlSchemaType() throws Exception {
//		this.createPackageInfoWithAccessorType();
//		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getRootContextNode().getPackages(), 0).getPackageInfo();
//		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();
//
//		contextPackageInfo.addXmlSchemaType(0).setName("bar");
//		contextPackageInfo.addXmlSchemaType(1).setName("foo");
//		contextPackageInfo.addXmlSchemaType(2).setName("baz");
//
//		Iterator<XmlSchemaTypeAnnotation> xmlSchemaTypes = this.getSchemaTypeAnnotations(resourcePackage);
//		assertEquals("bar", xmlSchemaTypes.next().getName());		
//		assertEquals("foo", xmlSchemaTypes.next().getName());		
//		assertEquals("baz", xmlSchemaTypes.next().getName());
//		assertFalse(xmlSchemaTypes.hasNext());
//
//		contextPackageInfo.moveXmlSchemaType(2, 0);
//		xmlSchemaTypes = this.getSchemaTypeAnnotations(resourcePackage);
//		assertEquals("foo", xmlSchemaTypes.next().getName());
//		assertEquals("baz", xmlSchemaTypes.next().getName());
//		assertEquals("bar", xmlSchemaTypes.next().getName());		
//		assertFalse(xmlSchemaTypes.hasNext());
//
//		contextPackageInfo.moveXmlSchemaType(0, 1);
//		xmlSchemaTypes = this.getSchemaTypeAnnotations(resourcePackage);
//		assertEquals("baz", xmlSchemaTypes.next().getName());
//		assertEquals("foo", xmlSchemaTypes.next().getName());
//		assertEquals("bar", xmlSchemaTypes.next().getName());		
//		assertFalse(xmlSchemaTypes.hasNext());
//	}
//
//	public void testSyncXmlSchemaTypes() throws Exception {
//		this.createPackageInfoWithAccessorType();
//		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getRootContextNode().getPackages(), 0).getPackageInfo();
//		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();
//
//		ListIterable<XmlSchemaType> xmlSchemaTypes = contextPackageInfo.getXmlSchemaTypes();
//		assertFalse(xmlSchemaTypes.iterator().hasNext());
//
//		//add 3 XmlSchemaTypes
//		AnnotatedElement annotatedElement = this.annotatedElement(resourcePackage);
//		annotatedElement.edit(new Member.Editor() {
//			public void edit(ModifiedDeclaration declaration) {
//				GenericRootContextNodeTests.this.addXmlSchemaType(declaration, 0, "bar");
//				GenericRootContextNodeTests.this.addXmlSchemaType(declaration, 1, "foo");
//				GenericRootContextNodeTests.this.addXmlSchemaType(declaration, 2, "baz");
//			}
//		});
//
//		xmlSchemaTypes = contextPackageInfo.getXmlSchemaTypes();
//		ListIterator<XmlSchemaType> xmlSchemaTypesIterator = xmlSchemaTypes.iterator();
//		assertTrue(xmlSchemaTypesIterator.hasNext());
//		assertEquals("bar", xmlSchemaTypesIterator.next().getName());
//		assertEquals("foo", xmlSchemaTypesIterator.next().getName());
//		assertEquals("baz", xmlSchemaTypesIterator.next().getName());
//		assertFalse(xmlSchemaTypesIterator.hasNext());
//
//
//		annotatedElement.edit(new Member.Editor() {
//			public void edit(ModifiedDeclaration declaration) {
//				GenericRootContextNodeTests.this.moveXmlSchemaType(declaration, 2, 0);
//			}
//		});
//
//		xmlSchemaTypesIterator = xmlSchemaTypes.iterator();
//		assertTrue(xmlSchemaTypesIterator.hasNext());
//		assertEquals("foo", xmlSchemaTypesIterator.next().getName());
//		assertEquals("baz", xmlSchemaTypesIterator.next().getName());
//		assertEquals("bar", xmlSchemaTypesIterator.next().getName());
//		assertFalse(xmlSchemaTypesIterator.hasNext());
//
//
//		annotatedElement.edit(new Member.Editor() {
//			public void edit(ModifiedDeclaration declaration) {
//				GenericRootContextNodeTests.this.moveXmlSchemaType(declaration, 0, 1);
//			}
//		});
//
//		xmlSchemaTypesIterator = xmlSchemaTypes.iterator();
//		assertTrue(xmlSchemaTypesIterator.hasNext());
//		assertEquals("baz", xmlSchemaTypesIterator.next().getName());
//		assertEquals("foo", xmlSchemaTypesIterator.next().getName());
//		assertEquals("bar", xmlSchemaTypesIterator.next().getName());
//		assertFalse(xmlSchemaTypesIterator.hasNext());
//
//
//		annotatedElement.edit(new Member.Editor() {
//			public void edit(ModifiedDeclaration declaration) {
//				GenericRootContextNodeTests.this.removeXmlSchemaType(declaration, 1);
//			}
//		});
//
//		xmlSchemaTypesIterator = xmlSchemaTypes.iterator();
//		assertTrue(xmlSchemaTypesIterator.hasNext());
//		assertEquals("baz", xmlSchemaTypesIterator.next().getName());
//		assertEquals("bar", xmlSchemaTypesIterator.next().getName());
//		assertFalse(xmlSchemaTypesIterator.hasNext());
//
//
//		annotatedElement.edit(new Member.Editor() {
//			public void edit(ModifiedDeclaration declaration) {
//				GenericRootContextNodeTests.this.removeXmlSchemaType(declaration, 1);
//			}
//		});
//
//		xmlSchemaTypesIterator = xmlSchemaTypes.iterator();
//		assertTrue(xmlSchemaTypesIterator.hasNext());
//		assertEquals("baz", xmlSchemaTypesIterator.next().getName());
//		assertFalse(xmlSchemaTypesIterator.hasNext());
//
//
//		annotatedElement.edit(new Member.Editor() {
//			public void edit(ModifiedDeclaration declaration) {
//				GenericRootContextNodeTests.this.removeXmlSchemaType(declaration, 0);
//			}
//		});
//
//		xmlSchemaTypesIterator = xmlSchemaTypes.iterator();
//		assertFalse(xmlSchemaTypesIterator.hasNext());
//	}
//
//	public void testSyncAddXmlSchemaTypes() throws Exception {
//		this.createPackageInfoWithAccessorType();
//		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getRootContextNode().getPackages(), 0).getPackageInfo();
//		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();
//
//		ListIterable<XmlSchemaType> xmlSchemaTypes = contextPackageInfo.getXmlSchemaTypes();
//		assertFalse(xmlSchemaTypes.iterator().hasNext());
//
//		//add 1 XmlSchemaType when none exist
//		AnnotatedElement annotatedElement = this.annotatedElement(resourcePackage);
//		annotatedElement.edit(new Member.Editor() {
//			public void edit(ModifiedDeclaration declaration) {
//				GenericRootContextNodeTests.this.addXmlSchemaType(declaration, 0, "bar");
//			}
//		});
//
//		xmlSchemaTypes = contextPackageInfo.getXmlSchemaTypes();
//		ListIterator<XmlSchemaType> xmlSchemaTypesIterator = xmlSchemaTypes.iterator();
//		assertTrue(xmlSchemaTypesIterator.hasNext());
//		assertEquals("bar", xmlSchemaTypesIterator.next().getName());
//		assertFalse(xmlSchemaTypesIterator.hasNext());
//
//
//		//add 1 XmlSchemaType when 1 standalone exists
//		annotatedElement.edit(new Member.Editor() {
//			public void edit(ModifiedDeclaration declaration) {
//				GenericRootContextNodeTests.this.addXmlSchemaType(declaration, 1, "foo");
//			}
//		});
//
//		xmlSchemaTypes = contextPackageInfo.getXmlSchemaTypes();
//		xmlSchemaTypesIterator = xmlSchemaTypes.iterator();
//		assertTrue(xmlSchemaTypesIterator.hasNext());
//		assertEquals("bar", xmlSchemaTypesIterator.next().getName());
//		assertEquals("foo", xmlSchemaTypesIterator.next().getName());
//		assertFalse(xmlSchemaTypesIterator.hasNext());
//
//		//add 1 XmlSchemaType when a container annotations exists
//		annotatedElement.edit(new Member.Editor() {
//			public void edit(ModifiedDeclaration declaration) {
//				GenericRootContextNodeTests.this.addXmlSchemaType(declaration, 2, "baz");
//			}
//		});
//
//		xmlSchemaTypes = contextPackageInfo.getXmlSchemaTypes();
//		xmlSchemaTypesIterator = xmlSchemaTypes.iterator();
//		assertTrue(xmlSchemaTypesIterator.hasNext());
//		assertEquals("bar", xmlSchemaTypesIterator.next().getName());
//		assertEquals("foo", xmlSchemaTypesIterator.next().getName());
//		assertEquals("baz", xmlSchemaTypesIterator.next().getName());
//		assertFalse(xmlSchemaTypesIterator.hasNext());
//
//		//add 1 XmlSchemaType to beginning of list when a container annotations exists
//		annotatedElement.edit(new Member.Editor() {
//			public void edit(ModifiedDeclaration declaration) {
//				GenericRootContextNodeTests.this.addXmlSchemaType(declaration, 0, "foobaz");
//			}
//		});
//
//		xmlSchemaTypes = contextPackageInfo.getXmlSchemaTypes();
//		xmlSchemaTypesIterator = xmlSchemaTypes.iterator();
//		assertTrue(xmlSchemaTypesIterator.hasNext());
//		assertEquals("foobaz", xmlSchemaTypesIterator.next().getName());
//		assertEquals("bar", xmlSchemaTypesIterator.next().getName());
//		assertEquals("foo", xmlSchemaTypesIterator.next().getName());
//		assertEquals("baz", xmlSchemaTypesIterator.next().getName());
//		assertFalse(xmlSchemaTypesIterator.hasNext());
//	}
//
//	protected void moveXmlSchemaType(ModifiedDeclaration declaration, int targetIndex, int sourceIndex) {
//		this.moveArrayElement((NormalAnnotation) declaration.getAnnotationNamed(JAXB.XML_SCHEMA_TYPES), JAXB.XML_SCHEMA_TYPES__VALUE, targetIndex, sourceIndex);
//	}
//
//	protected void removeXmlSchemaType(ModifiedDeclaration declaration, int index) {
//		this.removeArrayElement((NormalAnnotation) declaration.getAnnotationNamed(JAXB.XML_SCHEMA_TYPES), JAXB.XML_SCHEMA_TYPES__VALUE, index);
//	}
//
//
//
//
//	
//	public void testGetXmlJavaTypeAdapters() throws Exception {
//		this.createPackageInfoWithAccessorOrder();
//		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getRootContextNode().getPackages(), 0).getPackageInfo();
//		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();
//
//		ListIterable<XmlJavaTypeAdapter> xmlJavaTypeAdapters = contextPackageInfo.getXmlJavaTypeAdapters();
//		assertFalse(xmlJavaTypeAdapters.iterator().hasNext());
//
//		//add 2 XmlJavaTypeAdapters
//		AnnotatedElement annotatedElement = this.annotatedElement(resourcePackage);
//		annotatedElement.edit(new Member.Editor() {
//			public void edit(ModifiedDeclaration declaration) {
//				GenericRootContextNodeTests.this.addXmlJavaTypeAdapter(declaration, 0, "String");
//				GenericRootContextNodeTests.this.addXmlJavaTypeAdapter(declaration, 1, "Integer");
//			}
//		});
//
//		xmlJavaTypeAdapters = contextPackageInfo.getXmlJavaTypeAdapters();
//		ListIterator<XmlJavaTypeAdapter> xmlJavaTypeAdaptersIterator = xmlJavaTypeAdapters.iterator();
//		assertTrue(xmlJavaTypeAdaptersIterator.hasNext());
//		assertEquals("String", xmlJavaTypeAdaptersIterator.next().getValue());
//		assertEquals("Integer", xmlJavaTypeAdaptersIterator.next().getValue());
//		assertFalse(xmlJavaTypeAdaptersIterator.hasNext());
//	}
//	
//	protected void addXmlJavaTypeAdapter(ModifiedDeclaration declaration, int index, String name) {
//		NormalAnnotation arrayElement = this.newXmlJavaTypeAdapterAnnotation(declaration.getAst(), name);
//		this.addArrayElement(declaration, JAXB.XML_JAVA_TYPE_ADAPTERS, index, JAXB.XML_JAVA_TYPE_ADAPTERS__VALUE, arrayElement);		
//	}
//
//	protected NormalAnnotation newXmlJavaTypeAdapterAnnotation(AST ast, String valueTypeName) {
//		NormalAnnotation annotation = this.newNormalAnnotation(ast, JAXB.XML_JAVA_TYPE_ADAPTER);
//		this.addMemberValuePair(
//			annotation,
//			JAXB.XML_JAVA_TYPE_ADAPTER__VALUE, 
//			this.newTypeLiteral(ast, valueTypeName));
//		return annotation;
//	}
//
//	public void testGetXmlJavaTypeAdaptersSize() throws Exception {
//		this.createPackageInfoWithAccessorOrder();
//		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getRootContextNode().getPackages(), 0).getPackageInfo();
//		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();
//
//		assertEquals(0, contextPackageInfo.getXmlJavaTypeAdaptersSize());
//
//		//add 2 XmlJavaTypeAdapters
//		AnnotatedElement annotatedElement = this.annotatedElement(resourcePackage);
//		annotatedElement.edit(new Member.Editor() {
//			public void edit(ModifiedDeclaration declaration) {
//				GenericRootContextNodeTests.this.addXmlJavaTypeAdapter(declaration, 0, "String");
//				GenericRootContextNodeTests.this.addXmlJavaTypeAdapter(declaration, 1, "Integer");
//			}
//		});
//		assertEquals(2, contextPackageInfo.getXmlJavaTypeAdaptersSize());
//	}
//
//	public void testAddXmlJavaTypeAdapter() throws Exception {
//		//create a package info with an annotation other than XmlSchema to test
//		//adding things to the null schema annotation
//		this.createPackageInfoWithAccessorType();
//		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getRootContextNode().getPackages(), 0).getPackageInfo();
//		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();
//
//		contextPackageInfo.addXmlJavaTypeAdapter(0).setValue("bar");
//		contextPackageInfo.addXmlJavaTypeAdapter(0).setValue("foo");
//		contextPackageInfo.addXmlJavaTypeAdapter(0).setValue("baz");
//
//		Iterator<XmlJavaTypeAdapterAnnotation> xmlJavaTypeAdapters = this.getXmlJavaTypeAdapterAnnotations(resourcePackage);
//
//		assertEquals("baz", xmlJavaTypeAdapters.next().getValue());
//		assertEquals("foo", xmlJavaTypeAdapters.next().getValue());
//		assertEquals("bar", xmlJavaTypeAdapters.next().getValue());
//		assertFalse(xmlJavaTypeAdapters.hasNext());
//	}
//
//	@SuppressWarnings("unchecked")
//	protected Iterator<XmlJavaTypeAdapterAnnotation> getXmlJavaTypeAdapterAnnotations(JavaResourcePackage resourcePackage) {
//		return (Iterator<XmlJavaTypeAdapterAnnotation>) resourcePackage.getAnnotations(JAXB.XML_JAVA_TYPE_ADAPTER).iterator();
//	}
//
//	public void testAddXmlJavaTypeAdapter2() throws Exception {
//		//create a package info with an annotation other than XmlSchema to test
//		//adding things to the null schema annotation
//		this.createPackageInfoWithAccessorType();
//		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getRootContextNode().getPackages(), 0).getPackageInfo();
//		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();
//
//		contextPackageInfo.addXmlJavaTypeAdapter(0).setValue("bar");
//		contextPackageInfo.addXmlJavaTypeAdapter(1).setValue("foo");
//		contextPackageInfo.addXmlJavaTypeAdapter(0).setValue("baz");
//
//		Iterator<XmlJavaTypeAdapterAnnotation> xmlJavaTypeAdapters = this.getXmlJavaTypeAdapterAnnotations(resourcePackage);
//
//		assertEquals("baz", xmlJavaTypeAdapters.next().getValue());
//		assertEquals("bar", xmlJavaTypeAdapters.next().getValue());
//		assertEquals("foo", xmlJavaTypeAdapters.next().getValue());
//		assertFalse(xmlJavaTypeAdapters.hasNext());
//	}
//
//	public void testRemoveXmlJavaTypeAdapter() throws Exception {
//		this.createPackageInfoWithAccessorType();
//		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getRootContextNode().getPackages(), 0).getPackageInfo();
//		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();
//
//		contextPackageInfo.addXmlJavaTypeAdapter(0).setValue("String");
//		contextPackageInfo.addXmlJavaTypeAdapter(1).setValue("foo");
//		contextPackageInfo.addXmlJavaTypeAdapter(2).setValue("baz");
//
//		Iterator<XmlJavaTypeAdapterAnnotation> xmlJavaTypeAdapters = this.getXmlJavaTypeAdapterAnnotations(resourcePackage);
//		XmlJavaTypeAdapterAnnotation adapterAnnotation = xmlJavaTypeAdapters.next();
//		assertEquals("String", adapterAnnotation.getValue());		
//		assertEquals("java.lang.String", adapterAnnotation.getFullyQualifiedValue());		
//		assertEquals("foo", xmlJavaTypeAdapters.next().getValue());		
//		assertEquals("baz", xmlJavaTypeAdapters.next().getValue());
//		assertFalse(xmlJavaTypeAdapters.hasNext());
//
//		contextPackageInfo.removeXmlJavaTypeAdapter(1);
//
//		xmlJavaTypeAdapters = this.getXmlJavaTypeAdapterAnnotations(resourcePackage);
//		adapterAnnotation = xmlJavaTypeAdapters.next();
//		assertEquals("String", adapterAnnotation.getValue());		
//		assertEquals("java.lang.String", adapterAnnotation.getFullyQualifiedValue());		
//		assertEquals("baz", xmlJavaTypeAdapters.next().getValue());
//		assertFalse(xmlJavaTypeAdapters.hasNext());
//
//		contextPackageInfo.removeXmlJavaTypeAdapter(1);
//		xmlJavaTypeAdapters = this.getXmlJavaTypeAdapterAnnotations(resourcePackage);
//		adapterAnnotation = xmlJavaTypeAdapters.next();
//		assertEquals("String", adapterAnnotation.getValue());		
//		assertEquals("java.lang.String", adapterAnnotation.getFullyQualifiedValue());		
//		assertFalse(xmlJavaTypeAdapters.hasNext());
//
//		contextPackageInfo.removeXmlJavaTypeAdapter(0);
//		xmlJavaTypeAdapters = this.getXmlJavaTypeAdapterAnnotations(resourcePackage);
//		assertFalse(xmlJavaTypeAdapters.hasNext());
//	}
//
//	public void testMoveXmlJavaTypeAdapter() throws Exception {
//		this.createPackageInfoWithAccessorType();
//		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getRootContextNode().getPackages(), 0).getPackageInfo();
//		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();
//
//		contextPackageInfo.addXmlJavaTypeAdapter(0).setValue("bar");
//		contextPackageInfo.addXmlJavaTypeAdapter(1).setValue("foo");
//		contextPackageInfo.addXmlJavaTypeAdapter(2).setValue("baz");
//
//		Iterator<XmlJavaTypeAdapterAnnotation> xmlJavaTypeAdapters = this.getXmlJavaTypeAdapterAnnotations(resourcePackage);
//		assertEquals("bar", xmlJavaTypeAdapters.next().getValue());		
//		assertEquals("foo", xmlJavaTypeAdapters.next().getValue());		
//		assertEquals("baz", xmlJavaTypeAdapters.next().getValue());
//		assertFalse(xmlJavaTypeAdapters.hasNext());
//
//		contextPackageInfo.moveXmlJavaTypeAdapter(2, 0);
//		xmlJavaTypeAdapters = this.getXmlJavaTypeAdapterAnnotations(resourcePackage);
//		assertEquals("foo", xmlJavaTypeAdapters.next().getValue());
//		assertEquals("baz", xmlJavaTypeAdapters.next().getValue());
//		assertEquals("bar", xmlJavaTypeAdapters.next().getValue());		
//		assertFalse(xmlJavaTypeAdapters.hasNext());
//
//		contextPackageInfo.moveXmlJavaTypeAdapter(0, 1);
//		xmlJavaTypeAdapters = this.getXmlJavaTypeAdapterAnnotations(resourcePackage);
//		assertEquals("baz", xmlJavaTypeAdapters.next().getValue());
//		assertEquals("foo", xmlJavaTypeAdapters.next().getValue());
//		assertEquals("bar", xmlJavaTypeAdapters.next().getValue());		
//		assertFalse(xmlJavaTypeAdapters.hasNext());
//	}
//
//	public void testSyncXmlJavaTypeAdapters() throws Exception {
//		this.createPackageInfoWithAccessorType();
//		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getRootContextNode().getPackages(), 0).getPackageInfo();
//		JavaResourcePackage resourcePackage = contextPackageInfo.getResourcePackage();
//
//		ListIterable<XmlJavaTypeAdapter> xmlJavaTypeAdapters = contextPackageInfo.getXmlJavaTypeAdapters();
//		assertFalse(xmlJavaTypeAdapters.iterator().hasNext());
//
//		//add 3 XmlJavaTypeAdapters
//		AnnotatedElement annotatedElement = this.annotatedElement(resourcePackage);
//		annotatedElement.edit(new Member.Editor() {
//			public void edit(ModifiedDeclaration declaration) {
//				GenericRootContextNodeTests.this.addXmlJavaTypeAdapter(declaration, 0, "bar");
//				GenericRootContextNodeTests.this.addXmlJavaTypeAdapter(declaration, 1, "foo");
//				GenericRootContextNodeTests.this.addXmlJavaTypeAdapter(declaration, 2, "baz");
//			}
//		});
//
//		xmlJavaTypeAdapters = contextPackageInfo.getXmlJavaTypeAdapters();
//		ListIterator<XmlJavaTypeAdapter> xmlJavaTypeAdaptersIterator = xmlJavaTypeAdapters.iterator();
//		assertTrue(xmlJavaTypeAdaptersIterator.hasNext());
//		assertEquals("bar", xmlJavaTypeAdaptersIterator.next().getValue());
//		assertEquals("foo", xmlJavaTypeAdaptersIterator.next().getValue());
//		assertEquals("baz", xmlJavaTypeAdaptersIterator.next().getValue());
//		assertFalse(xmlJavaTypeAdaptersIterator.hasNext());
//
//
//		annotatedElement.edit(new Member.Editor() {
//			public void edit(ModifiedDeclaration declaration) {
//				GenericRootContextNodeTests.this.moveXmlJavaTypeAdapter(declaration, 2, 0);
//			}
//		});
//
//		xmlJavaTypeAdapters = contextPackageInfo.getXmlJavaTypeAdapters();
//		xmlJavaTypeAdaptersIterator = xmlJavaTypeAdapters.iterator();
//		assertTrue(xmlJavaTypeAdaptersIterator.hasNext());
//		assertEquals("foo", xmlJavaTypeAdaptersIterator.next().getValue());
//		assertEquals("baz", xmlJavaTypeAdaptersIterator.next().getValue());
//		assertEquals("bar", xmlJavaTypeAdaptersIterator.next().getValue());
//		assertFalse(xmlJavaTypeAdaptersIterator.hasNext());
//
//
//		annotatedElement.edit(new Member.Editor() {
//			public void edit(ModifiedDeclaration declaration) {
//				GenericRootContextNodeTests.this.moveXmlJavaTypeAdapter(declaration, 0, 1);
//			}
//		});
//
//		xmlJavaTypeAdapters = contextPackageInfo.getXmlJavaTypeAdapters();
//		xmlJavaTypeAdaptersIterator = xmlJavaTypeAdapters.iterator();
//		assertTrue(xmlJavaTypeAdaptersIterator.hasNext());
//		assertEquals("baz", xmlJavaTypeAdaptersIterator.next().getValue());
//		assertEquals("foo", xmlJavaTypeAdaptersIterator.next().getValue());
//		assertEquals("bar", xmlJavaTypeAdaptersIterator.next().getValue());
//		assertFalse(xmlJavaTypeAdaptersIterator.hasNext());
//
//
//		annotatedElement.edit(new Member.Editor() {
//			public void edit(ModifiedDeclaration declaration) {
//				GenericRootContextNodeTests.this.removeXmlJavaTypeAdapter(declaration, 1);
//			}
//		});
//
//		xmlJavaTypeAdapters = contextPackageInfo.getXmlJavaTypeAdapters();
//		xmlJavaTypeAdaptersIterator = xmlJavaTypeAdapters.iterator();
//		assertTrue(xmlJavaTypeAdaptersIterator.hasNext());
//		assertEquals("baz", xmlJavaTypeAdaptersIterator.next().getValue());
//		assertEquals("bar", xmlJavaTypeAdaptersIterator.next().getValue());
//		assertFalse(xmlJavaTypeAdaptersIterator.hasNext());
//
//
//		annotatedElement.edit(new Member.Editor() {
//			public void edit(ModifiedDeclaration declaration) {
//				GenericRootContextNodeTests.this.removeXmlJavaTypeAdapter(declaration, 1);
//			}
//		});
//
//		xmlJavaTypeAdapters = contextPackageInfo.getXmlJavaTypeAdapters();
//		xmlJavaTypeAdaptersIterator = xmlJavaTypeAdapters.iterator();
//		assertTrue(xmlJavaTypeAdaptersIterator.hasNext());
//		assertEquals("baz", xmlJavaTypeAdaptersIterator.next().getValue());
//		assertFalse(xmlJavaTypeAdaptersIterator.hasNext());
//
//
//		annotatedElement.edit(new Member.Editor() {
//			public void edit(ModifiedDeclaration declaration) {
//				GenericRootContextNodeTests.this.removeXmlJavaTypeAdapter(declaration, 0);
//			}
//		});
//
//		xmlJavaTypeAdapters = contextPackageInfo.getXmlJavaTypeAdapters();
//		xmlJavaTypeAdaptersIterator = xmlJavaTypeAdapters.iterator();
//		assertFalse(xmlJavaTypeAdaptersIterator.hasNext());
//	}
//
//
//	protected void moveXmlJavaTypeAdapter(ModifiedDeclaration declaration, int targetIndex, int sourceIndex) {
//		this.moveArrayElement((NormalAnnotation) declaration.getAnnotationNamed(JAXB.XML_JAVA_TYPE_ADAPTERS), JAXB.XML_JAVA_TYPE_ADAPTERS__VALUE, targetIndex, sourceIndex);
//	}
//
//	protected void removeXmlJavaTypeAdapter(ModifiedDeclaration declaration, int index) {
//		this.removeArrayElement((NormalAnnotation) declaration.getAnnotationNamed(JAXB.XML_JAVA_TYPE_ADAPTERS), JAXB.XML_JAVA_TYPE_ADAPTERS__VALUE, index);
//	}
}
