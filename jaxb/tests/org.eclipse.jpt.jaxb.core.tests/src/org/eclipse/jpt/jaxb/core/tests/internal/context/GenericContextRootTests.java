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
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentClass;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentEnum;
import org.eclipse.jpt.jaxb.core.context.JaxbRegistry;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.AbstractJavaResourceType;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceEnum;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;


@SuppressWarnings("nls")
public class GenericContextRootTests extends JaxbContextModelTestCase
{
	
	public GenericContextRootTests(String name) {
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

	private ICompilationUnit createTypeWithXmlRegistry() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_REGISTRY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlRegistry");
			}
		});
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

	private ICompilationUnit createTestXmlEnum() throws Exception {
		return this.createTestEnum(new DefaultEnumAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_ENUM);
			}
			@Override
			public void appendEnumAnnotationTo(StringBuilder sb) {
				sb.append("@XmlEnum");
			}
		});
	}

	private ICompilationUnit createTestXmlEnumNoAnnotation(String enumName) throws Exception {
		return this.createTestEnum(PACKAGE_NAME, enumName + ".java", enumName, new DefaultEnumAnnotationWriter());
	}

	public void testGetPackages() throws Exception {
		this.createPackageInfoWithAccessorOrder();
		Iterator<JaxbPackage> packages = this.getContextRoot().getPackages().iterator();
		assertEquals(1, this.getContextRoot().getPackagesSize());
		assertEquals(PACKAGE_NAME, packages.next().getName());
		assertFalse(packages.hasNext());

		//add an unannotated package-info.java and make sure it's not added to the root context node
		this.createUnannotatedPackageInfo("foo");
		packages = this.getContextRoot().getPackages().iterator();
		assertEquals(1, this.getContextRoot().getPackagesSize());
		assertEquals(PACKAGE_NAME, packages.next().getName());
		assertFalse(packages.hasNext());

		//annotate the package-info.java and test it's added to the root context node
		JavaResourcePackage fooResourcePackage = getJaxbProject().getJavaResourcePackage("foo");
		AnnotatedElement annotatedElement = this.annotatedElement(fooResourcePackage);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericContextRootTests.this.addXmlAccessorTypeAnnotation(declaration, JAXB.XML_ACCESS_TYPE__PROPERTY);
			}
		});

		Iterable<String> packageNames = new TransformationIterable<JaxbPackage, String>(this.getContextRoot().getPackages()) {
			@Override
			protected String transform(JaxbPackage o) {
				return o.getName();
			}
		};
		assertEquals(2, this.getContextRoot().getPackagesSize());
		assertTrue(CollectionTools.contains(packageNames, PACKAGE_NAME));
		assertTrue(CollectionTools.contains(packageNames, "foo"));

		//remove the annotation from the package-info.java and test it's removed from the root context node
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericContextRootTests.this.removeXmlAccessorTypeAnnotation(declaration);
			}
		});

		packages = this.getContextRoot().getPackages().iterator();
		assertEquals(1, this.getContextRoot().getPackagesSize());
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

	public void testGetRegistries() throws Exception {
		createTypeWithXmlRegistry();
		Iterator<JaxbRegistry> registries = getContextRoot().getRegistries().iterator();
		assertEquals(1, CollectionTools.size(getContextRoot().getRegistries()));
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, registries.next().getFullyQualifiedName());
		assertFalse(registries.hasNext());
		
		//add an unannotated class and make sure it's not added to the context root
		createUnannotatedTestTypeNamed("Foo");
		registries = getContextRoot().getRegistries().iterator();
		assertEquals(1, CollectionTools.size(getContextRoot().getRegistries()));
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, registries.next().getFullyQualifiedName());
		assertFalse(registries.hasNext());
		
		//annotate the class with @XmlRegistry and test it's added to the root context node
		AbstractJavaResourceType fooResourceType = getJaxbProject().getJavaResourceType("test.Foo");
		AnnotatedElement annotatedElement = annotatedElement(fooResourceType);
		annotatedElement.edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_REGISTRY);
					}
				});
		
		Iterable<String> registryNames = 
				new TransformationIterable<JaxbRegistry, String>(
						getContextRoot().getRegistries()) {
					@Override
					protected String transform(JaxbRegistry o) {
						return o.getFullyQualifiedName();
					}
				};
		assertEquals(2, CollectionTools.size(getContextRoot().getRegistries()));
		assertTrue(CollectionTools.contains(registryNames, "test.Foo"));
		assertTrue(CollectionTools.contains(registryNames, FULLY_QUALIFIED_TYPE_NAME));
		
		//remove the annotation from the class and test it's removed from the context root
		annotatedElement.edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						removeAnnotation(declaration, JAXB.XML_REGISTRY);
					}
				});
		
		registries = getContextRoot().getRegistries().iterator();
		assertEquals(1, CollectionTools.size(getContextRoot().getRegistries()));
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, registries.next().getFullyQualifiedName());
		assertFalse(registries.hasNext());
	}
	
	public void testGetPersistentClasses() throws Exception {
		this.createTypeWithXmlType();
		Iterator<JaxbPersistentClass> persistentClasses = this.getContextRoot().getPersistentClasses().iterator();
		assertEquals(1, CollectionTools.size(getContextRoot().getPersistentClasses()));
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, persistentClasses.next().getFullyQualifiedName());
		assertFalse(persistentClasses.hasNext());
		
		//add an unannotated class and make sure it's not added to the context root
		createUnannotatedTestTypeNamed("Foo");
		persistentClasses = this.getContextRoot().getPersistentClasses().iterator();
		assertEquals(1, CollectionTools.size(getContextRoot().getPersistentClasses()));
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, persistentClasses.next().getFullyQualifiedName());
		assertFalse(persistentClasses.hasNext());

		//annotate the class with @XmlType and test it's added to the context root
		AbstractJavaResourceType fooResourceType = getJaxbProject().getJavaResourceType("test.Foo");
		AnnotatedElement annotatedElement = annotatedElement(fooResourceType);
		annotatedElement.edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_TYPE);
					}
				});
		
		Iterable<String> persistentClassNames = 
				new TransformationIterable<JaxbPersistentClass, String>(
						getContextRoot().getPersistentClasses()) {
					@Override
					protected String transform(JaxbPersistentClass o) {
						return o.getFullyQualifiedName();
					}
				};
		assertEquals(2, CollectionTools.size(getContextRoot().getPersistentClasses()));
		assertTrue(CollectionTools.contains(persistentClassNames, "test.Foo"));
		assertTrue(CollectionTools.contains(persistentClassNames, FULLY_QUALIFIED_TYPE_NAME));

		//remove the annotation from the class and test it's removed from the root context node
		annotatedElement.edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						removeAnnotation(declaration, JAXB.XML_TYPE);
					}
				});
		
		persistentClasses = getContextRoot().getPersistentClasses().iterator();
		assertEquals(1, CollectionTools.size(getContextRoot().getPersistentClasses()));
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, persistentClasses.next().getFullyQualifiedName());
		assertFalse(persistentClasses.hasNext());
	}

	public void testGetPersistentEnums() throws Exception {
		this.createTestXmlEnum();
		Iterator<JaxbPersistentEnum> persistentEnums = this.getContextRoot().getPersistentEnums().iterator();
		assertEquals(1, CollectionTools.size(getContextRoot().getPersistentEnums()));
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, persistentEnums.next().getFullyQualifiedName());
		assertFalse(persistentEnums.hasNext());
		
		//add an unannotated class and make sure it's not added to the context root
		createTestXmlEnumNoAnnotation("Foo");
		persistentEnums = this.getContextRoot().getPersistentEnums().iterator();
		assertEquals(1, CollectionTools.size(getContextRoot().getPersistentEnums()));
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, persistentEnums.next().getFullyQualifiedName());
		assertFalse(persistentEnums.hasNext());

		//annotate the class with @XmlEnum and test it's added to the context root
		JavaResourceEnum fooResourceType = getJaxbProject().getJavaResourceEnum("test.Foo");
		AnnotatedElement annotatedElement = annotatedElement(fooResourceType);
		annotatedElement.edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_ENUM);
					}
				});
		
		Iterable<String> persistentEnumNames = 
				new TransformationIterable<JaxbPersistentEnum, String>(
						getContextRoot().getPersistentEnums()) {
					@Override
					protected String transform(JaxbPersistentEnum o) {
						return o.getFullyQualifiedName();
					}
				};
		assertEquals(2, CollectionTools.size(getContextRoot().getPersistentEnums()));
		assertTrue(CollectionTools.contains(persistentEnumNames, "test.Foo"));
		assertTrue(CollectionTools.contains(persistentEnumNames, FULLY_QUALIFIED_TYPE_NAME));

		//remove the annotation from the class and test it's removed from the root context node
		annotatedElement.edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						removeAnnotation(declaration, JAXB.XML_ENUM);
					}
				});
		
		persistentEnums = getContextRoot().getPersistentEnums().iterator();
		assertEquals(1, CollectionTools.size(getContextRoot().getPersistentEnums()));
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, persistentEnums.next().getFullyQualifiedName());
		assertFalse(persistentEnums.hasNext());
	}
	
	public void testChangeTypeKind() throws Exception {
		createTypeWithXmlRegistry();
		createUnannotatedTestTypeNamed("Foo");
		AbstractJavaResourceType fooResourceType = getJaxbProject().getJavaResourceType("test.Foo");
		AnnotatedElement annotatedElement = annotatedElement(fooResourceType);
		annotatedElement.edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_REGISTRY);
					}
				});
		
		assertEquals(2, getContextRoot().getTypesSize());
		assertEquals(2, CollectionTools.size(getContextRoot().getRegistries()));
		assertEquals(0, CollectionTools.size(getContextRoot().getPersistentClasses()));
		
		// remove the @XmlRegistry annotation and add an @XmlType annotation
		annotatedElement.edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						removeAnnotation(declaration, JAXB.XML_REGISTRY);
						addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_TYPE);
					}
				});
		
		assertEquals(2, getContextRoot().getTypesSize());
		assertEquals(1, CollectionTools.size(getContextRoot().getRegistries()));
		assertEquals(1, CollectionTools.size(getContextRoot().getPersistentClasses()));
		
		// remove the @XmlType annotation and add an @XmlRegistry annotation
		annotatedElement.edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						removeAnnotation(declaration, JAXB.XML_TYPE);
						addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_REGISTRY);
					}
				});
		
		assertEquals(2, getContextRoot().getTypesSize());
		assertEquals(2, CollectionTools.size(getContextRoot().getRegistries()));
		assertEquals(0, CollectionTools.size(getContextRoot().getPersistentClasses()));
	}
}
