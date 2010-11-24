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

	public void testGetPersistentClasses() throws Exception {
		this.createTypeWithXmlType();
		Iterator<JaxbPersistentClass> persistentClasses = this.getContextRoot().getPersistentClasses().iterator();
		assertEquals(1, this.getContextRoot().getPersistentClassesSize());
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, persistentClasses.next().getFullyQualifiedName());
		assertFalse(persistentClasses.hasNext());

		//add an unannotated class and make sure it's not added to the root context node
		this.createUnannotatedTestTypeNamed("Foo");
		persistentClasses = this.getContextRoot().getPersistentClasses().iterator();
		assertEquals(1, this.getContextRoot().getPersistentClassesSize());
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, persistentClasses.next().getFullyQualifiedName());
		assertFalse(persistentClasses.hasNext());

		//annotate the class with @XmlType and test it's added to the root context node
		JavaResourceType fooResourceType = getJaxbProject().getJavaResourceType("test.Foo");
		AnnotatedElement annotatedElement = this.annotatedElement(fooResourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericContextRootTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_TYPE);
			}
		});

		Iterable<String> persistentClassNames = new TransformationIterable<JaxbPersistentClass, String>(this.getContextRoot().getPersistentClasses()) {
			@Override
			protected String transform(JaxbPersistentClass o) {
				return o.getFullyQualifiedName();
			}
		};
		assertEquals(2, this.getContextRoot().getPersistentClassesSize());
		assertTrue(CollectionTools.contains(persistentClassNames, "test.Foo"));
		assertTrue(CollectionTools.contains(persistentClassNames, FULLY_QUALIFIED_TYPE_NAME));

		//remove the annotation from the class and test it's removed from the root context node
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericContextRootTests.this.removeAnnotation(declaration, JAXB.XML_TYPE);
			}
		});

		persistentClasses = this.getContextRoot().getPersistentClasses().iterator();
		assertEquals(1, this.getContextRoot().getPersistentClassesSize());
		assertEquals(FULLY_QUALIFIED_TYPE_NAME, persistentClasses.next().getFullyQualifiedName());
		assertFalse(persistentClasses.hasNext());
	}
}
