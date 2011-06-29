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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceEnum;
import org.eclipse.jpt.common.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentClass;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentEnum;
import org.eclipse.jpt.jaxb.core.context.JaxbRegistry;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;


@SuppressWarnings("nls")
public class GenericContextRootTests
		extends JaxbContextModelTestCase {
	
	public GenericContextRootTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createPackageInfoWithAccessorOrder() throws CoreException {
		return createTestPackageInfo(
				"@XmlAccessorOrder(value = XmlAccessOrder.ALPHABETICAL)",
				JAXB.XML_ACCESS_ORDER, JAXB.XML_ACCESSOR_ORDER);
	}
	
	private ICompilationUnit createAnnotatedPersistentClassWithSuperclassNamed(final String superclassName) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType");
			}
			@Override
			public void appendExtendsImplementsTo(StringBuilder sb) {
				sb.append("extends " + superclassName);
			}
		});
	}
	
	private ICompilationUnit createAnnotatedPersistentClassWithAttributeAndTypeNamed(
			final String attributeName, final String typeName) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType");
			}
			@Override
			public void appendNameFieldAnnotationTo(StringBuilder sb) {
				sb.append("public " + typeName + " " + attributeName + ";" + CR);
				sb.append(CR);
			}
		});
	}
	
	private ICompilationUnit createAnnotatedPersistentClassWithAttributeAndElementTypeNamed(
			final String attributeName, final String typeName) throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>("java.util.List", JAXB.XML_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType");
			}
			@Override
			public void appendNameFieldAnnotationTo(StringBuilder sb) {
				sb.append("public List<" + typeName + "> " + attributeName + ";" + CR);
				sb.append(CR);
			}
		});
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
		addEnumMemberValuePair(annotation, JAXB.XML_ACCESSOR_TYPE__VALUE, accessType);
	}
	
	protected void removeXmlAccessorTypeAnnotation(ModifiedDeclaration declaration) {
		removeAnnotation(declaration, JAXB.XML_ACCESSOR_TYPE);
	}
	
	protected void addXmlSeeAlsoAnnotation(final ModifiedDeclaration declaration, String... typeNames) {
		Annotation annotation = declaration.getAnnotationNamed(JAXB.XML_SEE_ALSO);
		NormalAnnotation normalAnnotation = null;
		if (annotation == null) {
			normalAnnotation = addNormalAnnotation(declaration.getDeclaration(), JAXB.XML_SEE_ALSO);
		}
		else if (annotation.isMarkerAnnotation()) {
			normalAnnotation = replaceMarkerAnnotation((MarkerAnnotation) annotation);
		}
		else {
			normalAnnotation = (NormalAnnotation) annotation;
		}
		
		Expression arrayInitializer = newArrayInitializer(
				declaration.getAst(),
				ArrayTools.array(
						new TransformationIterable<String, TypeLiteral>(new ArrayIterable<String>(typeNames)) {
							@Override
							protected TypeLiteral transform(String o) {
								return newTypeLiteral(declaration.getAst(), o);
							}
						},
						new Expression[0]));
		addMemberValuePair(normalAnnotation, JAXB.XML_SEE_ALSO__VALUE, arrayInitializer);
	}

	public void testGetRegistries() throws Exception {
		createAnnotatedRegistry();
		Iterator<JaxbRegistry> registries = getContextRoot().getRegistries().iterator();
		assertEquals(1, CollectionTools.size(getContextRoot().getRegistries()));
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, registries.next().getFullyQualifiedName());
		assertFalse(registries.hasNext());
		
		//add an unannotated class and make sure it's not added to the context root
		createUnannotatedClassNamed("Foo");
		registries = getContextRoot().getRegistries().iterator();
		assertEquals(1, CollectionTools.size(getContextRoot().getRegistries()));
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, registries.next().getFullyQualifiedName());
		assertFalse(registries.hasNext());
		
		//annotate the class with @XmlRegistry and test it's added to the root context node
		JavaResourceAbstractType fooResourceType = getJaxbProject().getJavaResourceType("test.Foo");
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
		this.createAnnotatedPersistentClass();
		Iterator<JaxbPersistentClass> persistentClasses = this.getContextRoot().getPersistentClasses().iterator();
		assertEquals(1, CollectionTools.size(getContextRoot().getPersistentClasses()));
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, persistentClasses.next().getFullyQualifiedName());
		assertFalse(persistentClasses.hasNext());
		
		//add an unannotated class and make sure it's not added to the context root
		createUnannotatedClassNamed("Foo");
		persistentClasses = this.getContextRoot().getPersistentClasses().iterator();
		assertEquals(1, CollectionTools.size(getContextRoot().getPersistentClasses()));
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, persistentClasses.next().getFullyQualifiedName());
		assertFalse(persistentClasses.hasNext());

		//annotate the class with @XmlType and test it's added to the context root
		JavaResourceAbstractType fooResourceType = getJaxbProject().getJavaResourceType("test.Foo");
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
		this.createAnnotatedPersistentEnum();
		Iterator<JaxbPersistentEnum> persistentEnums = this.getContextRoot().getPersistentEnums().iterator();
		assertEquals(1, CollectionTools.size(getContextRoot().getPersistentEnums()));
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, persistentEnums.next().getFullyQualifiedName());
		assertFalse(persistentEnums.hasNext());
		
		//add an unannotated class and make sure it's not added to the context root
		createUnannotatedEnumNamed("Foo");
		persistentEnums = this.getContextRoot().getPersistentEnums().iterator();
		assertEquals(1, CollectionTools.size(getContextRoot().getPersistentEnums()));
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, persistentEnums.next().getFullyQualifiedName());
		assertFalse(persistentEnums.hasNext());

		//annotate the class with @XmlEnum and test it's added to the context root
		JavaResourceEnum fooResourceType = (JavaResourceEnum) getJaxbProject().getJavaResourceType("test.Foo", JavaResourceAbstractType.Kind.ENUM);
		AnnotatedElement annotatedElement = annotatedElement(fooResourceType);
		annotatedElement.edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_TYPE);
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
						removeAnnotation(declaration, JAXB.XML_TYPE);
					}
				});
		
		persistentEnums = getContextRoot().getPersistentEnums().iterator();
		assertEquals(1, CollectionTools.size(getContextRoot().getPersistentEnums()));
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, persistentEnums.next().getFullyQualifiedName());
		assertFalse(persistentEnums.hasNext());
	}
	
	public void testChangeTypeKind() throws Exception {
		createAnnotatedRegistry();
		createUnannotatedClassNamed("Foo");
		JavaResourceAbstractType fooResourceType = getJaxbProject().getJavaResourceType("test.Foo");
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
	
	public void testDirectReferencedSuperclass() throws Exception {
		String superclassName = "Super" + TYPE_NAME;
		String fqSuperclassName = PACKAGE_NAME_ + superclassName;
		createUnannotatedClassNamed(superclassName);
		
		// make sure unannotated superclass is not in context
		assertTrue(CollectionTools.isEmpty(getContextRoot().getPersistentClasses()));
		
		createAnnotatedPersistentClassWithSuperclassNamed(superclassName);
		Iterable<JaxbPersistentClass> persistentClasses = this.getContextRoot().getPersistentClasses();
		assertEquals(2, CollectionTools.size(persistentClasses));
		assertNotNull(getContextRoot().getPersistentClass(FULLY_QUALIFIED_TYPE_NAME));
		assertNotNull(getContextRoot().getPersistentClass(fqSuperclassName));
		
		// remove annotated class - both classes removed from context
		IFile file = (IFile) getContextRoot().getPersistentClass(FULLY_QUALIFIED_TYPE_NAME).getResource();
		file.delete(true, null);
		
		persistentClasses = this.getContextRoot().getPersistentClasses();
		assertEquals(0, CollectionTools.size(persistentClasses));
		assertNull(getContextRoot().getPersistentClass(FULLY_QUALIFIED_TYPE_NAME));
		assertNull(getContextRoot().getPersistentClass(fqSuperclassName));
	}
	
	public void testDirectReferencedAttribute() throws Exception {
		String otherClassName = "Other" + TYPE_NAME;
		String fqOtherClassName = PACKAGE_NAME_ + otherClassName;
		String attributeName = "other";
		createUnannotatedClassNamed(otherClassName);
		
		// make sure unannotated other class is not in context
		assertTrue(CollectionTools.isEmpty(getContextRoot().getPersistentClasses()));
		
		createAnnotatedPersistentClassWithAttributeAndTypeNamed(attributeName, otherClassName);
		JavaResourceType thisType = (JavaResourceType) getJaxbProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourceAttribute attribute = getFieldNamed(thisType, attributeName);
		AnnotatedElement annotatedAttribute = annotatedElement(attribute);
		
		Iterable<JaxbPersistentClass> persistentClasses = this.getContextRoot().getPersistentClasses();
		assertEquals(2, CollectionTools.size(persistentClasses));
		assertNotNull(getContextRoot().getPersistentClass(FULLY_QUALIFIED_TYPE_NAME));
		assertNotNull(getContextRoot().getPersistentClass(fqOtherClassName));
		
		// add an @XmlElement
		annotatedAttribute.edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_ELEMENT);
					}
				});
		
		persistentClasses = this.getContextRoot().getPersistentClasses();
		assertEquals(2, CollectionTools.size(persistentClasses));
		assertNotNull(getContextRoot().getPersistentClass(FULLY_QUALIFIED_TYPE_NAME));
		assertNotNull(getContextRoot().getPersistentClass(fqOtherClassName));
		
		// change to @XmlTransient
		annotatedAttribute.edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						removeAnnotation(declaration, JAXB.XML_ELEMENT);
						addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_TRANSIENT);
					}
				});
		
		persistentClasses = this.getContextRoot().getPersistentClasses();
		assertEquals(1, CollectionTools.size(persistentClasses));
		assertNotNull(getContextRoot().getPersistentClass(FULLY_QUALIFIED_TYPE_NAME));
		assertNull(getContextRoot().getPersistentClass(fqOtherClassName));
	}
	
	public void testDirectReferencedListAttribute() throws Exception {
		String otherClassName = "Other" + TYPE_NAME;
		String fqOtherClassName = PACKAGE_NAME_ + otherClassName;
		String attributeName = "other";
		createUnannotatedClassNamed(otherClassName);
		
		// make sure unannotated other class is not in context
		assertTrue(CollectionTools.isEmpty(getContextRoot().getPersistentClasses()));
		
		createAnnotatedPersistentClassWithAttributeAndElementTypeNamed(attributeName, otherClassName);
		JavaResourceType thisType = (JavaResourceType) getJaxbProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourceAttribute attribute = getFieldNamed(thisType, attributeName);
		AnnotatedElement annotatedAttribute = annotatedElement(attribute);
		
		Iterable<JaxbPersistentClass> persistentClasses = this.getContextRoot().getPersistentClasses();
		assertEquals(2, CollectionTools.size(persistentClasses));
		assertNotNull(getContextRoot().getPersistentClass(FULLY_QUALIFIED_TYPE_NAME));
		assertNotNull(getContextRoot().getPersistentClass(fqOtherClassName));
		
		// add an @XmlElement
		annotatedAttribute.edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_ELEMENT);
					}
				});
		
		persistentClasses = this.getContextRoot().getPersistentClasses();
		assertEquals(2, CollectionTools.size(persistentClasses));
		assertNotNull(getContextRoot().getPersistentClass(FULLY_QUALIFIED_TYPE_NAME));
		assertNotNull(getContextRoot().getPersistentClass(fqOtherClassName));
		
		// change to @XmlTransient
		annotatedAttribute.edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						removeAnnotation(declaration, JAXB.XML_ELEMENT);
						addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_TRANSIENT);
					}
				});
		
		persistentClasses = this.getContextRoot().getPersistentClasses();
		assertEquals(1, CollectionTools.size(persistentClasses));
		assertNotNull(getContextRoot().getPersistentClass(FULLY_QUALIFIED_TYPE_NAME));
		assertNull(getContextRoot().getPersistentClass(fqOtherClassName));
	}
	
	public void testDirectReferencedSeeAlso() throws Exception {
		final String otherClassName = "Other" + TYPE_NAME;
		final String fqOtherClassName = PACKAGE_NAME_ + otherClassName;
		final String otherClassName2 = "Other" + TYPE_NAME + "2";
		final String fqOtherClassName2 = PACKAGE_NAME_ + otherClassName2;
		createUnannotatedClassNamed(otherClassName);
		createUnannotatedClassNamed(otherClassName2);
		
		// make sure unannotated other classes are not in context
		assertTrue(CollectionTools.isEmpty(getContextRoot().getPersistentClasses()));
		
		createAnnotatedPersistentClass();
		JavaResourceType thisType = (JavaResourceType) getJaxbProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME);
		AnnotatedElement annotatedType = annotatedElement(thisType);
		
		// make sure unannotated other classes are not in context
		assertEquals(1, CollectionTools.size(getContextRoot().getPersistentClasses()));
		assertNotNull(getContextRoot().getPersistentClass(FULLY_QUALIFIED_TYPE_NAME));
		
		// add an @XmlSeeAlso with one class
		annotatedType.edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						addXmlSeeAlsoAnnotation(declaration, otherClassName);
					}
				});
		
		assertEquals(2, CollectionTools.size(getContextRoot().getPersistentClasses()));
		assertNotNull(getContextRoot().getPersistentClass(FULLY_QUALIFIED_TYPE_NAME));
		assertNotNull(getContextRoot().getPersistentClass(fqOtherClassName));
		
		// change to @XmlSeeAlso with two classes
		annotatedType.edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						removeAnnotation(declaration, JAXB.XML_SEE_ALSO);
						addXmlSeeAlsoAnnotation(declaration, otherClassName, otherClassName2);
					}
				});
		
		assertEquals(3, CollectionTools.size(getContextRoot().getPersistentClasses()));
		assertNotNull(getContextRoot().getPersistentClass(FULLY_QUALIFIED_TYPE_NAME));
		assertNotNull(getContextRoot().getPersistentClass(fqOtherClassName));
		assertNotNull(getContextRoot().getPersistentClass(fqOtherClassName2));
		
		// remove the @XmlSeeAlso annotation
		annotatedType.edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						removeAnnotation(declaration, JAXB.XML_SEE_ALSO);
					}
				});
		
		assertEquals(1, CollectionTools.size(getContextRoot().getPersistentClasses()));
		assertNotNull(getContextRoot().getPersistentClass(FULLY_QUALIFIED_TYPE_NAME));
	}
	
	public void testJaxbIndex() throws Exception {
		final String otherClassName = "Other" + TYPE_NAME;
		final String fqOtherClassName = PACKAGE_NAME_ + otherClassName;
		final String otherClassName2 = "Other" + TYPE_NAME + "2";
		final String fqOtherClassName2 = PACKAGE_NAME_ + otherClassName2;
		createUnannotatedClassNamed(otherClassName);
		createUnannotatedClassNamed(otherClassName2);
		
		// make sure unannotated other classes are not in context
		assertTrue(CollectionTools.isEmpty(getContextRoot().getPersistentClasses()));
		
		createAnnotatedPersistentClass();
		
		// make sure unannotated other classes are not in context
		assertEquals(1, CollectionTools.size(getContextRoot().getPersistentClasses()));
		assertNotNull(getContextRoot().getPersistentClass(FULLY_QUALIFIED_TYPE_NAME));
		
		// add a jaxb.index with one class
		IFile jaxbIndex = getJavaProject().getProject().getFile(new Path("src/test/jaxb.index"));
		InputStream stream = new ByteArrayInputStream(otherClassName.getBytes());
		jaxbIndex.create(stream, true, null);
		
		assertEquals(2, CollectionTools.size(getContextRoot().getPersistentClasses()));
		assertNotNull(getContextRoot().getPersistentClass(FULLY_QUALIFIED_TYPE_NAME));
		assertNotNull(getContextRoot().getPersistentClass(fqOtherClassName));
		
		// change to jaxb.index with two classes
		jaxbIndex.setContents(new ByteArrayInputStream((otherClassName + CR + otherClassName2).getBytes()), true, false, null);
		
		assertEquals(3, CollectionTools.size(getContextRoot().getPersistentClasses()));
		assertNotNull(getContextRoot().getPersistentClass(FULLY_QUALIFIED_TYPE_NAME));
		assertNotNull(getContextRoot().getPersistentClass(fqOtherClassName));
		assertNotNull(getContextRoot().getPersistentClass(fqOtherClassName2));
		
		// clear the jaxb.index
		jaxbIndex.setContents(new ByteArrayInputStream(new byte[0]), true, false, null);
		
		assertEquals(1, CollectionTools.size(getContextRoot().getPersistentClasses()));
		assertNotNull(getContextRoot().getPersistentClass(FULLY_QUALIFIED_TYPE_NAME));
	}
}
