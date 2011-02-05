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

import java.beans.Introspector;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.IExtendedModifier;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Modifier.ModifierKeyword;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentClass;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentField;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentProperty;
import org.eclipse.jpt.jaxb.core.context.XmlAccessOrder;
import org.eclipse.jpt.jaxb.core.context.XmlAccessType;
import org.eclipse.jpt.jaxb.core.resource.java.AbstractJavaResourceType;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceField;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceMember;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorOrderAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorTypeAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAttributeAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlRootElementAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlTransientAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlTypeAnnotation;
import org.eclipse.jpt.jaxb.core.tests.internal.context.JaxbContextModelTestCase;


@SuppressWarnings("nls")
public class GenericJavaPersistentClassTests extends JaxbContextModelTestCase
{
	
	public GenericJavaPersistentClassTests(String name) {
		super(name);
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

	private void createTestSubType() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ");
					sb.append(JAXB.XML_TYPE);
					sb.append(";");
					sb.append(CR);
				sb.append("@XmlType");
				sb.append(CR);
				sb.append("public class ").append("AnnotationTestTypeChild").append(" ");
				sb.append("extends " + TYPE_NAME + " ");
				sb.append("{}").append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "AnnotationTestTypeChild.java", sourceWriter);
	}
	
	private ICompilationUnit createXmlTypeWithAccessorType() throws CoreException {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TYPE, JAXB.XML_ACCESS_TYPE, JAXB.XML_ACCESSOR_TYPE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType").append(CR);
				sb.append("@XmlAccessorType(value = XmlAccessType.PROPERTY)");
			}
		});
	}
	
	private ICompilationUnit createXmlTypeWithAccessorOrder() throws CoreException {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TYPE, JAXB.XML_ACCESS_ORDER, JAXB.XML_ACCESSOR_ORDER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType").append(CR);
				sb.append("@XmlAccessorOrder(value = XmlAccessOrder.ALPHABETICAL)");
			}
		});
	}

	private ICompilationUnit createPackageInfoWithAccessorType() throws CoreException {
		return createTestPackageInfo(
				"@XmlAccessorType(value = XmlAccessType.PROPERTY)",
				JAXB.XML_ACCESS_TYPE, JAXB.XML_ACCESSOR_TYPE);
	}

	private void createTestXmlTypeWithFieldAndPublicMemberAccess() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ");
					sb.append(JAXB.XML_TYPE);
					sb.append(";");
					sb.append(CR);
					sb.append("import ");
					sb.append(JAXB.XML_ATTRIBUTE);
					sb.append(";");
					sb.append(CR);
					sb.append("import ");
					sb.append(JAXB.XML_TRANSIENT);
					sb.append(";");
					sb.append(CR);
					sb.append("import java.util.List;");
					sb.append(CR);
				sb.append("@XmlType");
				sb.append(CR);
				sb.append("public class ").append(TYPE_NAME).append(" ");
				sb.append("{").append(CR);
				sb.append("    public int foo;").append(CR);
				sb.append(CR).append("}").append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "AnnotationTestType.java", sourceWriter);
	}

	private void createTestXmlTypeWithPropertyAndPublicMemberAccess() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ");
					sb.append(JAXB.XML_TYPE);
					sb.append(";");
					sb.append(CR);
					sb.append("import ");
					sb.append(JAXB.XML_ATTRIBUTE);
					sb.append(";");
					sb.append(CR);
					sb.append("import ");
					sb.append(JAXB.XML_TRANSIENT);
					sb.append(";");
					sb.append(CR);
					sb.append("import java.util.List;");
					sb.append(CR);
				sb.append("@XmlType");
				sb.append(CR);
				sb.append("public class ").append(TYPE_NAME).append(" ");
				sb.append("{").append(CR);
				sb.append("    public int getFoo() {").append(CR);
				sb.append("        return 1;").append(CR).append("    }").append(CR);
				sb.append("    public void setFoo(int foo) {}").append(CR).append(CR);
				sb.append(CR).append("}").append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "AnnotationTestType.java", sourceWriter);
	}

	private void createTestXmlTypeWithPropertyGetterAndPublicMemberAccess() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ");
					sb.append(JAXB.XML_TYPE);
					sb.append(";");
					sb.append(CR);
					sb.append("import ");
					sb.append(JAXB.XML_ATTRIBUTE);
					sb.append(";");
					sb.append(CR);
					sb.append("import ");
					sb.append(JAXB.XML_TRANSIENT);
					sb.append(";");
					sb.append(CR);
					sb.append("import java.util.List;");
					sb.append(CR);
				sb.append("@XmlType");
				sb.append(CR);
				sb.append("public class ").append(TYPE_NAME).append(" ");
				sb.append("{").append(CR);
				sb.append("    public int getFoo() {").append(CR);
				sb.append("        return 1;").append(CR).append("    }").append(CR);
				sb.append(CR).append("}").append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "AnnotationTestType.java", sourceWriter);
	}

	private void createTestXmlTypeWithPropertyGetterListAndPublicMemberAccess() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ");
					sb.append(JAXB.XML_TYPE);
					sb.append(";");
					sb.append(CR);
					sb.append("import ");
					sb.append(JAXB.XML_ATTRIBUTE);
					sb.append(";");
					sb.append(CR);
					sb.append("import ");
					sb.append(JAXB.XML_TRANSIENT);
					sb.append(";");
					sb.append(CR);
					sb.append("import java.util.List;");
					sb.append(CR);
				sb.append("@XmlType");
				sb.append(CR);
				sb.append("public class ").append(TYPE_NAME).append(" ");
				sb.append("{").append(CR);
				sb.append("    public List<?> getFoo() {").append(CR);
				sb.append("        return 1;").append(CR).append("    }").append(CR);
				sb.append(CR).append("}").append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "AnnotationTestType.java", sourceWriter);
	}

	private void createTestXmlTypeWithPropertySetterAndPublicMemberAccess() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ");
					sb.append(JAXB.XML_TYPE);
					sb.append(";");
					sb.append(CR);
					sb.append("import ");
					sb.append(JAXB.XML_ATTRIBUTE);
					sb.append(";");
					sb.append(CR);
					sb.append("import ");
					sb.append(JAXB.XML_TRANSIENT);
					sb.append(";");
					sb.append(CR);
					sb.append("import java.util.List;");
					sb.append(CR);
				sb.append("@XmlType");
				sb.append(CR);
				sb.append("public class ").append(TYPE_NAME).append(" ");
				sb.append("{").append(CR);
				sb.append("    public void setFoo(int foo) {}").append(CR).append(CR);
				sb.append(CR).append("}").append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "AnnotationTestType.java", sourceWriter);
	}

    public void testModifyFactoryClass() throws Exception {
		createTypeWithXmlType();
		
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		AbstractJavaResourceType resourceType = persistentClass.getJavaResourceType();
	
		assertNull(persistentClass.getFactoryClass());
		
		persistentClass.setFactoryClass("foo");
		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		assertEquals("foo", xmlTypeAnnotation.getFactoryClass());
		assertEquals("foo", persistentClass.getFactoryClass());
		
		persistentClass.setFactoryClass(null);
		xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		assertNull(xmlTypeAnnotation.getFactoryClass());
		assertNull(persistentClass.getFactoryClass());
	
		//add another annotation so that the context model does not get blown away
		persistentClass.setSpecifiedAccessType(XmlAccessType.FIELD);
		resourceType.removeAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		
		//set factoryClass again, this time starting with no XmlType annotation
		persistentClass.setFactoryClass("foo");
		xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		assertEquals("foo", xmlTypeAnnotation.getFactoryClass());
		assertEquals("foo", persistentClass.getFactoryClass());
	}
	
	public void testUpdateFactoryClass() throws Exception {
		createTypeWithXmlType();
		
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		AbstractJavaResourceType resourceType = persistentClass.getJavaResourceType();
	
		assertNull(persistentClass.getFactoryClass());
		
		
		//add a factoryClass member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.addXmlTypeTypeMemberValuePair(declaration, JAXB.XML_TYPE__FACTORY_CLASS, "Foo");
			}
		});
		assertEquals("Foo", persistentClass.getFactoryClass());

		//remove the factoryClass member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlTypeAnnotation = (NormalAnnotation) GenericJavaPersistentClassTests.this.getXmlTypeAnnotation(declaration);
				GenericJavaPersistentClassTests.this.values(xmlTypeAnnotation).remove(0);
			}
		});
		assertNull(persistentClass.getFactoryClass());
	}

	public void testModifyFactoryMethod() throws Exception {
		createTypeWithXmlType();
		
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		AbstractJavaResourceType resourceType = persistentClass.getJavaResourceType();
	
		assertNull(persistentClass.getFactoryMethod());
		
		persistentClass.setFactoryMethod("foo");
		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		assertEquals("foo", xmlTypeAnnotation.getFactoryMethod());
		assertEquals("foo", persistentClass.getFactoryMethod());
		
		persistentClass.setFactoryMethod(null);
		xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		assertNull(xmlTypeAnnotation.getFactoryMethod());
		assertNull(persistentClass.getFactoryMethod());
	
		//add another annotation so that the context model does not get blown away
		persistentClass.setSpecifiedAccessType(XmlAccessType.FIELD);
		resourceType.removeAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		
		//set factoryMethod again, this time starting with no XmlType annotation
		persistentClass.setFactoryMethod("foo");
		xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		assertEquals("foo", xmlTypeAnnotation.getFactoryMethod());
		assertEquals("foo", persistentClass.getFactoryMethod());
	}
	
	public void testUpdateFactoryMethod() throws Exception {
		createTypeWithXmlType();
		
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		AbstractJavaResourceType resourceType = persistentClass.getJavaResourceType();
	
		assertNull(persistentClass.getFactoryMethod());
		
		
		//add a factoryMethod member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.addXmlTypeMemberValuePair(declaration, JAXB.XML_TYPE__FACTORY_METHOD, "foo");
			}
		});
		assertEquals("foo", persistentClass.getFactoryMethod());

		//remove the factoryMethod member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlTypeAnnotation = (NormalAnnotation) GenericJavaPersistentClassTests.this.getXmlTypeAnnotation(declaration);
				GenericJavaPersistentClassTests.this.values(xmlTypeAnnotation).remove(0);
			}
		});
		assertNull(persistentClass.getFactoryMethod());
	}

	public void testModifySchemaTypeName() throws Exception {
		createTypeWithXmlType();
		
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		AbstractJavaResourceType resourceType = persistentClass.getJavaResourceType();
		String defaultXmlTypeName = Introspector.decapitalize(TYPE_NAME);
		
		assertNull(persistentClass.getSpecifiedXmlTypeName());
		assertEquals(defaultXmlTypeName, persistentClass.getDefaultXmlTypeName());
		assertEquals(defaultXmlTypeName, persistentClass.getXmlTypeName());
		
		persistentClass.setSpecifiedXmlTypeName("foo");
		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		assertEquals("foo", xmlTypeAnnotation.getName());
		assertEquals("foo", persistentClass.getSpecifiedXmlTypeName());
		assertEquals("foo", persistentClass.getXmlTypeName());
		
		persistentClass.setSpecifiedXmlTypeName(null);
		xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		assertNull(xmlTypeAnnotation.getName());
		assertNull(persistentClass.getSpecifiedXmlTypeName());
		assertEquals(defaultXmlTypeName, persistentClass.getXmlTypeName());	
		
		//add another annotation so that the context model does not get blown away
		persistentClass.setSpecifiedAccessType(XmlAccessType.FIELD);
		resourceType.removeAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		
		//set name again, this time starting with no XmlType annotation
		persistentClass.setSpecifiedXmlTypeName("foo");
		xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		assertEquals("foo", xmlTypeAnnotation.getName());
		assertEquals("foo", persistentClass.getSpecifiedXmlTypeName());
		assertEquals("foo", persistentClass.getXmlTypeName());
	}
	
	public void testUpdateSchemaTypeName() throws Exception {
		createTypeWithXmlType();
		
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		AbstractJavaResourceType resourceType = persistentClass.getJavaResourceType();
		String defaultXmlTypeName = Introspector.decapitalize(TYPE_NAME);
		
		assertNull(persistentClass.getSpecifiedXmlTypeName());
		assertEquals(defaultXmlTypeName, persistentClass.getDefaultXmlTypeName());
		assertEquals(defaultXmlTypeName, persistentClass.getXmlTypeName());
		
		//add a namespace member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.addXmlTypeMemberValuePair(declaration, JAXB.XML_TYPE__NAME, "foo");
			}
		});
		assertEquals("foo", persistentClass.getSpecifiedXmlTypeName());
		assertEquals("foo", persistentClass.getXmlTypeName());
		
		//remove the namespace member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlTypeAnnotation = (NormalAnnotation) GenericJavaPersistentClassTests.this.getXmlTypeAnnotation(declaration);
				GenericJavaPersistentClassTests.this.values(xmlTypeAnnotation).remove(0);
			}
		});
		assertNull(persistentClass.getSpecifiedXmlTypeName());
		assertEquals(defaultXmlTypeName, persistentClass.getXmlTypeName());
	}

	public void testModifyNamespace() throws Exception {
		createTypeWithXmlType();
		
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		AbstractJavaResourceType resourceType = persistentClass.getJavaResourceType();
		
		assertNull(persistentClass.getSpecifiedNamespace());
		assertEquals("", persistentClass.getDefaultNamespace());
		assertEquals("", persistentClass.getNamespace());
		
		persistentClass.setSpecifiedNamespace("foo");
		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		assertEquals("foo", xmlTypeAnnotation.getNamespace());
		assertEquals("foo", persistentClass.getSpecifiedNamespace());
		assertEquals("foo", persistentClass.getNamespace());
		
		persistentClass.setSpecifiedNamespace(null);
		xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		assertNull(xmlTypeAnnotation.getNamespace());
		assertNull(persistentClass.getSpecifiedNamespace());
		assertEquals("", persistentClass.getNamespace());
		
		//add another annotation so that the context model does not get blown away
		persistentClass.setSpecifiedAccessType(XmlAccessType.FIELD);
		resourceType.removeAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		
		//set namespace again, this time starting with no XmlType annotation
		persistentClass.setSpecifiedNamespace("foo");
		xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		assertEquals("foo", xmlTypeAnnotation.getNamespace());
		assertEquals("foo", persistentClass.getSpecifiedNamespace());
		assertEquals("foo", persistentClass.getNamespace());
	}
	
	public void testUpdateNamespace() throws Exception {
		createTypeWithXmlType();
		
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		AbstractJavaResourceType resourceType = persistentClass.getJavaResourceType();
		
		assertNull(persistentClass.getSpecifiedNamespace());
		assertEquals("", persistentClass.getDefaultNamespace());
		assertEquals("", persistentClass.getNamespace());
		
		//add a namespace member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.addXmlTypeMemberValuePair(declaration, JAXB.XML_TYPE__NAMESPACE, "foo");
			}
		});
		assertEquals("foo", persistentClass.getSpecifiedNamespace());
		assertEquals("foo", persistentClass.getNamespace());
		
		//remove the namespace member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlTypeAnnotation = (NormalAnnotation) GenericJavaPersistentClassTests.this.getXmlTypeAnnotation(declaration);
				GenericJavaPersistentClassTests.this.values(xmlTypeAnnotation).remove(0);
			}
		});
		assertNull(persistentClass.getSpecifiedNamespace());
		assertEquals("", persistentClass.getNamespace());
	}

	public void testModifyAccessType() throws Exception {
		createXmlTypeWithAccessorType();
		
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		AbstractJavaResourceType resourceType = persistentClass.getJavaResourceType();
	
		assertEquals(XmlAccessType.PROPERTY, persistentClass.getSpecifiedAccessType());
		assertEquals(XmlAccessType.PROPERTY, persistentClass.getAccessType());
		assertEquals(XmlAccessType.PUBLIC_MEMBER, persistentClass.getDefaultAccessType());
		
		persistentClass.setSpecifiedAccessType(XmlAccessType.FIELD);
		XmlAccessorTypeAnnotation accessorTypeAnnotation = (XmlAccessorTypeAnnotation) resourceType.getAnnotation(XmlAccessorTypeAnnotation.ANNOTATION_NAME);
		assertEquals(org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType.FIELD, accessorTypeAnnotation.getValue());
		assertEquals(XmlAccessType.FIELD, persistentClass.getAccessType());

		persistentClass.setSpecifiedAccessType(XmlAccessType.PUBLIC_MEMBER);
		assertEquals(org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType.PUBLIC_MEMBER, accessorTypeAnnotation.getValue());
		assertEquals(XmlAccessType.PUBLIC_MEMBER, persistentClass.getAccessType());

		persistentClass.setSpecifiedAccessType(XmlAccessType.NONE);
		assertEquals(org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType.NONE, accessorTypeAnnotation.getValue());
		assertEquals(XmlAccessType.NONE, persistentClass.getAccessType());
		
		persistentClass.setSpecifiedAccessType(null);
		accessorTypeAnnotation = (XmlAccessorTypeAnnotation) resourceType.getAnnotation(XmlAccessorTypeAnnotation.ANNOTATION_NAME);
		assertNull(accessorTypeAnnotation.getValue());
		assertNull(persistentClass.getSpecifiedAccessType());
		assertEquals(XmlAccessType.PUBLIC_MEMBER, persistentClass.getAccessType());
		assertEquals(XmlAccessType.PUBLIC_MEMBER, persistentClass.getDefaultAccessType());
	}
	
	public void testUpdateAccessType() throws Exception {
		createXmlTypeWithAccessorType();
		
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		AbstractJavaResourceType resourceType = persistentClass.getJavaResourceType();
	
		assertEquals(XmlAccessType.PROPERTY, persistentClass.getSpecifiedAccessType());
		assertEquals(XmlAccessType.PROPERTY, persistentClass.getAccessType());
		assertEquals(XmlAccessType.PUBLIC_MEMBER, persistentClass.getDefaultAccessType());
		
		//set the accesser type value to FIELD
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.setEnumMemberValuePair(declaration, XmlAccessorTypeAnnotation.ANNOTATION_NAME, JAXB.XML_ACCESS_TYPE__FIELD);
			}
		});
		assertEquals(XmlAccessType.FIELD, persistentClass.getAccessType());

		//set the accesser type value to PUBLIC_MEMBER
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.setEnumMemberValuePair(declaration, XmlAccessorTypeAnnotation.ANNOTATION_NAME, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
			}
		});
		assertEquals(XmlAccessType.PUBLIC_MEMBER, persistentClass.getAccessType());

		//set the accesser type value to NONE
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.setEnumMemberValuePair(declaration, XmlAccessorTypeAnnotation.ANNOTATION_NAME, JAXB.XML_ACCESS_TYPE__NONE);
			}
		});
		assertEquals(XmlAccessType.NONE, persistentClass.getAccessType());
		
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.removeAnnotation(declaration, XmlAccessorTypeAnnotation.ANNOTATION_NAME);
			}
		});
		assertNull(persistentClass.getSpecifiedAccessType());
		assertEquals(XmlAccessType.PUBLIC_MEMBER, persistentClass.getAccessType());
		assertEquals(XmlAccessType.PUBLIC_MEMBER, persistentClass.getDefaultAccessType());
	}

	/**
	 * If there is a @XmlAccessorType on a class, then it is used.
	 * Otherwise, if a @XmlAccessorType exists on one of its super classes, then it is inherited.
	 * Otherwise, the @XmlAccessorType on a package is inherited. 	
	 */
	public void testGetDefaultAccessType() throws Exception {
		this.createTypeWithXmlType();
		this.createTestSubType();
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JaxbPersistentClass childPersistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);

		assertEquals(XmlAccessType.PUBLIC_MEMBER, childPersistentClass.getDefaultAccessType());

		this.createPackageInfoWithAccessorType();
		assertEquals(XmlAccessType.PROPERTY, childPersistentClass.getDefaultAccessType());

		persistentClass.setSpecifiedAccessType(XmlAccessType.FIELD);
		assertEquals(XmlAccessType.PROPERTY, childPersistentClass.getDefaultAccessType());

		JaxbPackageInfo contextPackageInfo = CollectionTools.get(getContextRoot().getPackages(), 0).getPackageInfo();
		persistentClass.setSpecifiedAccessType(null);
		assertEquals(XmlAccessType.PROPERTY, childPersistentClass.getDefaultAccessType());
		contextPackageInfo.setSpecifiedAccessType(XmlAccessType.FIELD);
		assertEquals(XmlAccessType.FIELD, childPersistentClass.getDefaultAccessType());

		contextPackageInfo.setSpecifiedAccessType(XmlAccessType.NONE);
		assertEquals(XmlAccessType.NONE, childPersistentClass.getDefaultAccessType());

		contextPackageInfo.setSpecifiedAccessType(null);
		assertEquals(XmlAccessType.PUBLIC_MEMBER, childPersistentClass.getDefaultAccessType());
	}

	public void testGetSuperPersistentClass() throws Exception {
		this.createTypeWithXmlType();
		this.createTestSubType();
		JaxbPersistentClass persistentClass = getContextRoot().getPersistentClass(FULLY_QUALIFIED_TYPE_NAME);
		JaxbPersistentClass childPersistentClass = getContextRoot().getPersistentClass(PACKAGE_NAME + ".AnnotationTestTypeChild");

		assertEquals(persistentClass, childPersistentClass.getSuperPersistentClass());

		//This test will change when we no longer depend on there being an @XmlType annotation for something to be persistent
		AnnotatedElement annotatedElement = this.annotatedElement(persistentClass.getJavaResourceType());
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.removeAnnotation(declaration, XmlTypeAnnotation.ANNOTATION_NAME);
			}
		});
		assertNull(childPersistentClass.getSuperPersistentClass());
	}

	public void testModifyAccessOrder() throws Exception {
		createXmlTypeWithAccessorOrder();
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		AbstractJavaResourceType resourceType = persistentClass.getJavaResourceType();

		assertEquals(XmlAccessOrder.ALPHABETICAL, persistentClass.getSpecifiedAccessOrder());
		assertEquals(XmlAccessOrder.ALPHABETICAL, persistentClass.getAccessOrder());
		assertEquals(XmlAccessOrder.UNDEFINED, persistentClass.getDefaultAccessOrder());

		persistentClass.setSpecifiedAccessOrder(XmlAccessOrder.UNDEFINED);
		XmlAccessorOrderAnnotation accessorOrderAnnotation = (XmlAccessorOrderAnnotation) resourceType.getAnnotation(XmlAccessorOrderAnnotation.ANNOTATION_NAME);
		assertEquals(org.eclipse.jpt.jaxb.core.resource.java.XmlAccessOrder.UNDEFINED, accessorOrderAnnotation.getValue());
		assertEquals(XmlAccessOrder.UNDEFINED, persistentClass.getAccessOrder());

		persistentClass.setSpecifiedAccessOrder(null);
		accessorOrderAnnotation = (XmlAccessorOrderAnnotation) resourceType.getAnnotation(XmlAccessorOrderAnnotation.ANNOTATION_NAME);
		assertNull(accessorOrderAnnotation.getValue());
		assertNull(persistentClass.getSpecifiedAccessOrder());
		assertEquals(XmlAccessOrder.UNDEFINED, persistentClass.getAccessOrder());
		assertEquals(XmlAccessOrder.UNDEFINED, persistentClass.getDefaultAccessOrder());
	}

	public void testUpdateAccessOrder() throws Exception {
		createXmlTypeWithAccessorOrder();
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		AbstractJavaResourceType resourceType = persistentClass.getJavaResourceType();

		assertEquals(XmlAccessOrder.ALPHABETICAL, persistentClass.getSpecifiedAccessOrder());
		assertEquals(XmlAccessOrder.ALPHABETICAL, persistentClass.getAccessOrder());
		assertEquals(XmlAccessOrder.UNDEFINED, persistentClass.getDefaultAccessOrder());

		//set the access order value to UNDEFINED
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.setEnumMemberValuePair(declaration, XmlAccessorOrderAnnotation.ANNOTATION_NAME, JAXB.XML_ACCESS_ORDER__UNDEFINED);
			}
		});
		assertEquals(XmlAccessOrder.UNDEFINED, persistentClass.getAccessOrder());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.removeAnnotation(declaration, XmlAccessorOrderAnnotation.ANNOTATION_NAME);
			}
		});
		assertNull(persistentClass.getSpecifiedAccessOrder());
		assertEquals(XmlAccessOrder.UNDEFINED, persistentClass.getAccessOrder());
		assertEquals(XmlAccessOrder.UNDEFINED, persistentClass.getDefaultAccessOrder());
	}

	public void testGetPropOrder() throws Exception {
		this.createTypeWithXmlType();
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		AbstractJavaResourceType resourceType = persistentClass.getJavaResourceType();

		ListIterator<String> props = persistentClass.getPropOrder().iterator();
		assertFalse(props.hasNext());

		//add 2 prop orders
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.addProp(declaration, 0, "bar");
				GenericJavaPersistentClassTests.this.addProp(declaration, 1, "foo");
			}
		});

		props = persistentClass.getPropOrder().iterator();
		assertEquals("bar", props.next());
		assertEquals("foo", props.next());
		assertFalse(props.hasNext());
	}

	protected void addProp(ModifiedDeclaration declaration, int index, String prop) {
		this.addArrayElement(declaration, JAXB.XML_TYPE, index, JAXB.XML_TYPE__PROP_ORDER, this.newStringLiteral(declaration.getAst(), prop));		
	}

	public void testGetPropOrderSize() throws Exception {
		this.createTypeWithXmlType();
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		AbstractJavaResourceType resourceType = persistentClass.getJavaResourceType();

		assertEquals(0, persistentClass.getPropOrderSize());

		//add 2 prop orders
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.addProp(declaration, 0, "bar");
				GenericJavaPersistentClassTests.this.addProp(declaration, 1, "foo");
			}
		});
		assertEquals(2, persistentClass.getPropOrderSize());
	}

	public void testAddProp() throws Exception {
		this.createTypeWithXmlType();
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		AbstractJavaResourceType resourceType = persistentClass.getJavaResourceType();

		persistentClass.addProp(0, "bar");
		persistentClass.addProp(0, "foo");
		persistentClass.addProp(0, "baz");

		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		ListIterator<String> props = xmlTypeAnnotation.getPropOrder().iterator();

		assertEquals("baz", props.next());
		assertEquals("foo", props.next());
		assertEquals("bar", props.next());
		assertFalse(props.hasNext());
	}

	public void testAddProp2() throws Exception {
		this.createTypeWithXmlType();
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		AbstractJavaResourceType resourceType = persistentClass.getJavaResourceType();

		persistentClass.addProp(0, "bar");
		persistentClass.addProp(1, "foo");
		persistentClass.addProp(0, "baz");

		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);
		ListIterator<String> props = xmlTypeAnnotation.getPropOrder().iterator();

		assertEquals("baz", props.next());
		assertEquals("bar", props.next());
		assertEquals("foo", props.next());
		assertFalse(props.hasNext());
	}

	public void testRemoveProp() throws Exception {
		this.createTypeWithXmlType();
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		AbstractJavaResourceType resourceType = persistentClass.getJavaResourceType();

		persistentClass.addProp(0, "bar");
		persistentClass.addProp(1, "foo");
		persistentClass.addProp(2, "baz");

		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);

		persistentClass.removeProp(1);

		ListIterator<String> resourceProps = xmlTypeAnnotation.getPropOrder().iterator();
		assertEquals("bar", resourceProps.next());		
		assertEquals("baz", resourceProps.next());
		assertFalse(resourceProps.hasNext());

		persistentClass.removeProp(1);
		resourceProps = xmlTypeAnnotation.getPropOrder().iterator();
		assertEquals("bar", resourceProps.next());
		assertFalse(resourceProps.hasNext());

		persistentClass.removeProp(0);
		resourceProps = xmlTypeAnnotation.getPropOrder().iterator();
		assertFalse(resourceProps.hasNext());
	}

	public void testMoveProp() throws Exception {
		this.createTypeWithXmlType();
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		AbstractJavaResourceType resourceType = persistentClass.getJavaResourceType();

		persistentClass.addProp(0, "bar");
		persistentClass.addProp(1, "foo");
		persistentClass.addProp(2, "baz");

		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(XmlTypeAnnotation.ANNOTATION_NAME);

		assertEquals(3, xmlTypeAnnotation.getPropOrderSize());		

		persistentClass.moveProp(2, 0);
		ListIterator<String> props = persistentClass.getPropOrder().iterator();
		assertEquals("foo", props.next());
		assertEquals("baz", props.next());
		assertEquals("bar", props.next());		
		assertFalse(props.hasNext());

		ListIterator<String> resourceProps = xmlTypeAnnotation.getPropOrder().iterator();
		assertEquals("foo", resourceProps.next());
		assertEquals("baz", resourceProps.next());
		assertEquals("bar", resourceProps.next());


		persistentClass.moveProp(0, 1);
		props = persistentClass.getPropOrder().iterator();
		assertEquals("baz", props.next());
		assertEquals("foo", props.next());
		assertEquals("bar", props.next());		
		assertFalse(props.hasNext());

		resourceProps = xmlTypeAnnotation.getPropOrder().iterator();
		assertEquals("baz", resourceProps.next());
		assertEquals("foo", resourceProps.next());
		assertEquals("bar", resourceProps.next());
	}

	public void testSyncXmlNsPrefixes() throws Exception {
		this.createTypeWithXmlType();
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		AbstractJavaResourceType resourceType = persistentClass.getJavaResourceType();

		ListIterator<String> props = persistentClass.getPropOrder().iterator();
		assertFalse(props.hasNext());

		//add 3 prop orders
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.addProp(declaration, 0, "bar");
				GenericJavaPersistentClassTests.this.addProp(declaration, 1, "foo");
				GenericJavaPersistentClassTests.this.addProp(declaration, 2, "baz");
			}
		});

		props = persistentClass.getPropOrder().iterator();
		assertTrue(props.hasNext());
		assertEquals("bar", props.next());
		assertEquals("foo", props.next());
		assertEquals("baz", props.next());
		assertFalse(props.hasNext());


		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.moveProp(declaration, 2, 0);
			}
		});

		props = persistentClass.getPropOrder().iterator();
		assertTrue(props.hasNext());
		assertEquals("foo", props.next());
		assertEquals("baz", props.next());
		assertEquals("bar", props.next());
		assertFalse(props.hasNext());


		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.moveProp(declaration, 0, 1);
			}
		});

		props = persistentClass.getPropOrder().iterator();
		assertTrue(props.hasNext());
		assertEquals("baz", props.next());
		assertEquals("foo", props.next());
		assertEquals("bar", props.next());
		assertFalse(props.hasNext());


		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.removeProp(declaration, 1);
			}
		});

		props = persistentClass.getPropOrder().iterator();
		assertTrue(props.hasNext());
		assertEquals("baz", props.next());
		assertEquals("bar", props.next());
		assertFalse(props.hasNext());


		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.removeProp(declaration, 1);
			}
		});

		props = persistentClass.getPropOrder().iterator();
		assertTrue(props.hasNext());
		assertEquals("baz", props.next());
		assertFalse(props.hasNext());


		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.removeProp(declaration, 0);
			}
		});

		props = persistentClass.getPropOrder().iterator();
		assertFalse(props.hasNext());
	}

	protected void addXmlTypeMemberValuePair(ModifiedDeclaration declaration, String name, String value) {
		this.addMemberValuePair((MarkerAnnotation) this.getXmlTypeAnnotation(declaration), name, value);
	}

	protected void addXmlTypeTypeMemberValuePair(ModifiedDeclaration declaration, String name, String typeName) {
		this.addMemberValuePair(
			(MarkerAnnotation) this.getXmlTypeAnnotation(declaration), 
			name, 
			this.newTypeLiteral(declaration.getAst(), typeName));
	}

	protected Annotation getXmlTypeAnnotation(ModifiedDeclaration declaration) {
		return declaration.getAnnotationNamed(XmlTypeAnnotation.ANNOTATION_NAME);
	}

	protected void moveProp(ModifiedDeclaration declaration, int targetIndex, int sourceIndex) {
		this.moveArrayElement((NormalAnnotation) getXmlTypeAnnotation(declaration), JAXB.XML_TYPE__PROP_ORDER, targetIndex, sourceIndex);
	}

	protected void removeProp(ModifiedDeclaration declaration, int index) {
		this.removeArrayElement((NormalAnnotation) getXmlTypeAnnotation(declaration), JAXB.XML_TYPE__PROP_ORDER, index);
	}

	public void testModifyXmlRootElement() throws Exception {
		createTypeWithXmlType();
		
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		AbstractJavaResourceType resourceType = persistentClass.getJavaResourceType();

		assertNull(persistentClass.getRootElement());
		assertFalse(persistentClass.isRootElement());

		persistentClass.setRootElement("foo");
		XmlRootElementAnnotation xmlRootElementAnnotation = (XmlRootElementAnnotation) resourceType.getAnnotation(XmlRootElementAnnotation.ANNOTATION_NAME);
		assertEquals("foo", xmlRootElementAnnotation.getName());
		assertEquals("foo", persistentClass.getRootElement().getName());
		assertTrue(persistentClass.isRootElement());

		persistentClass.setRootElement(null);
		xmlRootElementAnnotation = (XmlRootElementAnnotation) resourceType.getAnnotation(XmlRootElementAnnotation.ANNOTATION_NAME);
		assertNull(xmlRootElementAnnotation);
		assertNull(persistentClass.getRootElement());
		assertFalse(persistentClass.isRootElement());
	}

	public void testUpdateXmlRootElement() throws Exception {
		createTypeWithXmlType();
		
		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		AbstractJavaResourceType resourceType = persistentClass.getJavaResourceType();
	
		assertNull(persistentClass.getRootElement());
		assertFalse(persistentClass.isRootElement());
		
		
		//add a XmlRootElement annotation
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation annotation = GenericJavaPersistentClassTests.this.addNormalAnnotation(declaration.getDeclaration(), JAXB.XML_ROOT_ELEMENT);
				GenericJavaPersistentClassTests.this.addMemberValuePair(annotation, JAXB.XML_ROOT_ELEMENT__NAME, "foo");
			}
		});
		assertEquals("foo", persistentClass.getRootElement().getName());
		assertTrue(persistentClass.isRootElement());

		//remove the XmlRootElement annotation
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.removeAnnotation(declaration, JAXB.XML_ROOT_ELEMENT);
			}
		});
		assertNull(persistentClass.getRootElement());
		assertFalse(persistentClass.isRootElement());
	}


	public void testUpdateFieldAttributes() throws Exception {
		this.createTestXmlTypeWithFieldAndPublicMemberAccess();

		JaxbPersistentClass contextClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		
		//public int foo;
		this.publicFieldTest(contextClass);

		//public transient int foo;
		this.publicTransientFieldTest(contextClass);

		//public static int foo;
		this.publicStaticFieldTest(contextClass);

		//public final int foo;
		this.publicFinalFieldTest(contextClass);

		//public static final int foo;
		this.publicStaticFinalFieldTest(contextClass);

		//private int foo;
		this.privateFieldTest(contextClass);

		//  private transient int foo;
		this.privateTransientFieldTest(contextClass);

		//  @XmlAttribute
		//  private static int foo; //persistent
		this.privateStaticFieldTest(contextClass);

		//  @XmlAttribute
		//  private static final int foo; //persistent
		this.privateStaticFinalFieldTest(contextClass);
	}

	protected void publicFieldTest(JaxbPersistentClass contextClass) {
		//public int foo; PUBLIC_MEMBER access - persistent
		assertEquals(1, contextClass.getAttributesSize());
		Iterator<JaxbPersistentAttribute> attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//public int foo; //PROPERTY access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(0, contextClass.getAttributesSize());

		//public int foo; //FIELD access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//public int foo; //NONE access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(0, contextClass.getAttributesSize());

		//public int foo; PUBLIC_MEMBER access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());


		//add @XmlTransient annotation and test each access type
		JavaResourceField resourceField = ((JaxbPersistentField) contextClass.getAttributes().iterator().next()).getResourceField();
		AnnotatedElement annotatedElement = this.annotatedElement(resourceField);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.addMarkerAnnotation(declaration.getDeclaration(), XmlTransientAnnotation.ANNOTATION_NAME);
			}
		});
		//@XmlTransient
		//public int foo; //PROPERTY access - not "persistent", but comes in to our context model because it is "mapped"
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlTransient
		//public int foo; //FIELD access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlTransient
		//public int foo; //NONE access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlTransient
		//public int foo; PUBLIC_MEMBER access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());
	
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.removeAnnotation(declaration, XmlTransientAnnotation.ANNOTATION_NAME);
			}
		});
	}

	protected void publicTransientFieldTest(JaxbPersistentClass contextClass) {
		Iterator<JaxbPersistentAttribute> attributes = contextClass.getAttributes().iterator();
		JavaResourceField resourceField = ((JaxbPersistentField) attributes.next()).getResourceField();

		//public transient int foo; PUBLIC_MEMBER access - not persistent
		this.addModifiers(resourceField, ModifierKeyword.TRANSIENT_KEYWORD);
		assertEquals(0, contextClass.getAttributesSize());

		//public transient int foo; //PROPERTY access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(0, contextClass.getAttributesSize());

		//public transient int foo; //FIELD access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(0, contextClass.getAttributesSize());

		//public transient int foo; //NONE access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(0, contextClass.getAttributesSize());

		//public transient int foo; PUBLIC_MEMBER access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(0, contextClass.getAttributesSize());

		this.removeModifiers(resourceField, ModifierKeyword.TRANSIENT_KEYWORD);
	}

	protected void publicStaticFieldTest(JaxbPersistentClass contextClass) {
		Iterator<JaxbPersistentAttribute> attributes = contextClass.getAttributes().iterator();
		JavaResourceField resourceField = ((JaxbPersistentField) attributes.next()).getResourceField();

		//public static int foo; PUBLIC_MEMBER access - not persistent
		this.addModifiers(resourceField, ModifierKeyword.STATIC_KEYWORD);
		assertEquals(0, contextClass.getAttributesSize());

		//public static int foo; //PROPERTY access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(0, contextClass.getAttributesSize());

		//public static int foo; //FIELD access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(0, contextClass.getAttributesSize());

		//public static int foo; //NONE access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(0, contextClass.getAttributesSize());

		//public static int foo; PUBLIC_MEMBER access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(0, contextClass.getAttributesSize());

		this.removeModifiers(resourceField, ModifierKeyword.STATIC_KEYWORD);
	}

	protected void publicFinalFieldTest(JaxbPersistentClass contextClass) {
		Iterator<JaxbPersistentAttribute> attributes = contextClass.getAttributes().iterator();
		JavaResourceField resourceField = ((JaxbPersistentField) attributes.next()).getResourceField();

		//public final int foo; PUBLIC_MEMBER access - persistent
		this.addModifiers(resourceField, ModifierKeyword.FINAL_KEYWORD);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//public final int foo; //PROPERTY access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(0, contextClass.getAttributesSize());

		//public final int foo; //FIELD access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//public final int foo; //NONE access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(0, contextClass.getAttributesSize());

		//public final int foo; PUBLIC_MEMBER access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		this.removeModifiers(resourceField, ModifierKeyword.FINAL_KEYWORD);
	}

	protected void publicStaticFinalFieldTest(JaxbPersistentClass contextClass) {
		Iterator<JaxbPersistentAttribute> attributes = contextClass.getAttributes().iterator();
		JavaResourceField resourceField = ((JaxbPersistentField) attributes.next()).getResourceField();

		//public static final int foo; PUBLIC_MEMBER access - not persistent
		this.addModifiers(resourceField, ModifierKeyword.STATIC_KEYWORD, ModifierKeyword.FINAL_KEYWORD);
		assertEquals(0, contextClass.getAttributesSize());

		//public static final int foo; //PROPERTY access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(0, contextClass.getAttributesSize());

		//public static final int foo; //FIELD access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(0, contextClass.getAttributesSize());

		//public static final int foo; //NONE access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(0, contextClass.getAttributesSize());

		//public static final int foo; PUBLIC_MEMBER access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(0, contextClass.getAttributesSize());

		this.removeModifiers(resourceField, ModifierKeyword.STATIC_KEYWORD, ModifierKeyword.FINAL_KEYWORD);
	}

	protected void privateFieldTest(JaxbPersistentClass contextClass) {
		Iterator<JaxbPersistentAttribute> attributes = contextClass.getAttributes().iterator();
		JavaResourceField resourceField = ((JaxbPersistentField) attributes.next()).getResourceField();
		removeModifiers(resourceField, ModifierKeyword.PUBLIC_KEYWORD);
		addModifiers(resourceField, ModifierKeyword.PRIVATE_KEYWORD);

		//private int foo; PUBLIC_MEMBER access - not persistent
		assertEquals(0, contextClass.getAttributesSize());

		//private int foo; //PROPERTY access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(0, contextClass.getAttributesSize());

		//private int foo; //FIELD access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//private int foo; //NONE access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(0, contextClass.getAttributesSize());

		//private int foo; PUBLIC_MEMBER access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(0, contextClass.getAttributesSize());


		//add @XmlAttribute annotation and test each access type
		AnnotatedElement annotatedElement = this.annotatedElement(resourceField);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.addMarkerAnnotation(declaration.getDeclaration(), XmlAttributeAnnotation.ANNOTATION_NAME);
			}
		});
		//@XmlAttribute
		//private int foo; //PROPERTY access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//private int foo; //FIELD access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//private int foo; //NONE access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//private int foo; PUBLIC_MEMBER access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());
	
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.removeAnnotation(declaration, XmlAttributeAnnotation.ANNOTATION_NAME);
			}
		});
		removeModifiers(resourceField, ModifierKeyword.PRIVATE_KEYWORD);
		addModifiers(resourceField, ModifierKeyword.PUBLIC_KEYWORD);
	}

	protected void privateTransientFieldTest(JaxbPersistentClass contextClass) {
		Iterator<JaxbPersistentAttribute> attributes = contextClass.getAttributes().iterator();
		JavaResourceField resourceField = ((JaxbPersistentField) attributes.next()).getResourceField();
		removeModifiers(resourceField, ModifierKeyword.PUBLIC_KEYWORD);
		addModifiers(resourceField, ModifierKeyword.PRIVATE_KEYWORD, ModifierKeyword.TRANSIENT_KEYWORD);

		//private transient int foo; PUBLIC_MEMBER access - not persistent
		assertEquals(0, contextClass.getAttributesSize());

		//private transient int foo; //PROPERTY access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(0, contextClass.getAttributesSize());

		//private transient int foo; //FIELD access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(0, contextClass.getAttributesSize());

		//private transient int foo; //NONE access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(0, contextClass.getAttributesSize());

		//private transient int foo; PUBLIC_MEMBER access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(0, contextClass.getAttributesSize());


		//add @XmlAttribute annotation and test each access type
		AnnotatedElement annotatedElement = this.annotatedElement(resourceField);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.addMarkerAnnotation(declaration.getDeclaration(), XmlAttributeAnnotation.ANNOTATION_NAME);
			}
		});
		//@XmlAttribute
		//private transient int foo; //PROPERTY access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//private transient int foo; //FIELD access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//private transient int foo; //NONE access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//private transient int foo; PUBLIC_MEMBER access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());
	
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.removeAnnotation(declaration, XmlAttributeAnnotation.ANNOTATION_NAME);
			}
		});
		removeModifiers(resourceField, ModifierKeyword.PRIVATE_KEYWORD, ModifierKeyword.TRANSIENT_KEYWORD);
		addModifiers(resourceField, ModifierKeyword.PUBLIC_KEYWORD);
	}

	protected void privateStaticFieldTest(JaxbPersistentClass contextClass) {
		Iterator<JaxbPersistentAttribute> attributes = contextClass.getAttributes().iterator();
		JavaResourceField resourceField = ((JaxbPersistentField) attributes.next()).getResourceField();
		removeModifiers(resourceField, ModifierKeyword.PUBLIC_KEYWORD);
		addModifiers(resourceField, ModifierKeyword.PRIVATE_KEYWORD, ModifierKeyword.STATIC_KEYWORD);

		//private static int foo; PUBLIC_MEMBER access - not persistent
		assertEquals(0, contextClass.getAttributesSize());

		//private static int foo; //PROPERTY access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(0, contextClass.getAttributesSize());

		//private static int foo; //FIELD access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(0, contextClass.getAttributesSize());

		//private static int foo; //NONE access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(0, contextClass.getAttributesSize());

		//private static int foo; PUBLIC_MEMBER access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(0, contextClass.getAttributesSize());


		//add @XmlAttribute annotation and test each access type
		AnnotatedElement annotatedElement = this.annotatedElement(resourceField);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.addMarkerAnnotation(declaration.getDeclaration(), XmlAttributeAnnotation.ANNOTATION_NAME);
			}
		});
		//@XmlAttribute
		//private static int foo; //PROPERTY access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//private static int foo; //FIELD access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//private static int foo; //NONE access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//private static int foo; PUBLIC_MEMBER access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());
	
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.removeAnnotation(declaration, XmlAttributeAnnotation.ANNOTATION_NAME);
			}
		});
		removeModifiers(resourceField, ModifierKeyword.PRIVATE_KEYWORD, ModifierKeyword.STATIC_KEYWORD);
		addModifiers(resourceField, ModifierKeyword.PUBLIC_KEYWORD);
	}

	protected void privateStaticFinalFieldTest(JaxbPersistentClass contextClass) {
		Iterator<JaxbPersistentAttribute> attributes = contextClass.getAttributes().iterator();
		JavaResourceField resourceField = ((JaxbPersistentField) attributes.next()).getResourceField();
		removeModifiers(resourceField, ModifierKeyword.PUBLIC_KEYWORD);
		addModifiers(resourceField, ModifierKeyword.PRIVATE_KEYWORD, ModifierKeyword.STATIC_KEYWORD, ModifierKeyword.FINAL_KEYWORD);

		//private static final int foo; PUBLIC_MEMBER access - not persistent
		assertEquals(0, contextClass.getAttributesSize());

		//private static final int foo; //PROPERTY access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(0, contextClass.getAttributesSize());

		//private static final int foo; //FIELD access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(0, contextClass.getAttributesSize());

		//private static final int foo; //NONE access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(0, contextClass.getAttributesSize());

		//private static final int foo; PUBLIC_MEMBER access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(0, contextClass.getAttributesSize());


		//add @XmlAttribute annotation and test each access type
		AnnotatedElement annotatedElement = this.annotatedElement(resourceField);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.addMarkerAnnotation(declaration.getDeclaration(), XmlAttributeAnnotation.ANNOTATION_NAME);
			}
		});
		//@XmlAttribute
		//private static final int foo; //PROPERTY access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//private static final int foo; //FIELD access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//private static final int foo; //NONE access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//private static final int foo; PUBLIC_MEMBER access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());
	
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.removeAnnotation(declaration, XmlAttributeAnnotation.ANNOTATION_NAME);
			}
		});
	}

	public void testUpdatePropertyAttributes() throws Exception {
		this.createTestXmlTypeWithPropertyAndPublicMemberAccess();

		JaxbPersistentClass contextClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);

		//public int getFoo();, public void setFoo(int);
		this.publicGetterSetterTest(contextClass);

		//public static int getFoo();, public static void setFoo(int);
		this.publicStaticGetterSetterTest(contextClass);

		//private int getFoo();, private void setFoo(int);
		this.privateGetterSetterTest(contextClass);
	}

	public void testUpdateGetterPropertyAttributes() throws Exception {
		this.createTestXmlTypeWithPropertyGetterAndPublicMemberAccess();

		JaxbPersistentClass contextClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);

		//public int getFoo(); PUBLIC_MEMBER access - not persistent
		assertEquals(0, contextClass.getAttributesSize());

		//public int getFoo(); PROPERTY access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(0, contextClass.getAttributesSize());

		//public int getFoo(); FIELD access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(0, contextClass.getAttributesSize());

		//public int getFoo(); NONE access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(0, contextClass.getAttributesSize());

		//public int getFoo(); PUBLIC_MEMBER access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(0, contextClass.getAttributesSize());


		//add @XmlAttribute annotation and test each access type
		JavaResourceMethod resourceMethod = contextClass.getJavaResourceType().getMethods().iterator().next();
		AnnotatedElement annotatedElement = this.annotatedElement(resourceMethod);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.addMarkerAnnotation(declaration.getDeclaration(), XmlAttributeAnnotation.ANNOTATION_NAME);
			}
		});
		//@XmlAttribute
		//public int getFoo(); PROPERTY access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PROPERTY);
		Iterator<JaxbPersistentAttribute> attributes = contextClass.getAttributes().iterator();
		assertEquals(1, contextClass.getAttributesSize());
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//public int getFoo(); FIELD access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//public int getFoo(); NONE access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//public int getFoo(); PUBLIC_MEMBER access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());
	
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.removeAnnotation(declaration, XmlAttributeAnnotation.ANNOTATION_NAME);
			}
		});
	}

	public void testUpdateSetterPropertyAttributes() throws Exception {
		this.createTestXmlTypeWithPropertySetterAndPublicMemberAccess();

		JaxbPersistentClass contextClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);

		//public void setFoo(int foo); PUBLIC_MEMBER access - not persistent
		assertEquals(0, contextClass.getAttributesSize());

		//public void setFoo(int foo); PROPERTY access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(0, contextClass.getAttributesSize());

		//public void setFoo(int foo); FIELD access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(0, contextClass.getAttributesSize());

		//public void setFoo(int foo); NONE access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(0, contextClass.getAttributesSize());

		//public void setFoo(int foo); PUBLIC_MEMBER access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(0, contextClass.getAttributesSize());


		//add @XmlAttribute annotation and test each access type
		JavaResourceMethod resourceMethod = contextClass.getJavaResourceType().getMethods().iterator().next();
		AnnotatedElement annotatedElement = this.annotatedElement(resourceMethod);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.addMarkerAnnotation(declaration.getDeclaration(), XmlAttributeAnnotation.ANNOTATION_NAME);
			}
		});
		//@XmlAttribute
		//public void setFoo(int foo);; PROPERTY access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PROPERTY);
		Iterator<JaxbPersistentAttribute> attributes = contextClass.getAttributes().iterator();
		assertEquals(1, contextClass.getAttributesSize());
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//public void setFoo(int foo); FIELD access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//public void setFoo(int foo); NONE access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//public void setFoo(int foo); PUBLIC_MEMBER access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());
	
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.removeAnnotation(declaration, XmlAttributeAnnotation.ANNOTATION_NAME);
			}
		});
	}

	public void testUpdateGetterListPropertyAttributes() throws Exception {
		this.createTestXmlTypeWithPropertyGetterListAndPublicMemberAccess();

		JaxbPersistentClass contextClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);

		//public List<?> getFoo(); PUBLIC_MEMBER access - persistent
		Iterator<JaxbPersistentAttribute> attributes = contextClass.getAttributes().iterator();
		assertEquals(1, contextClass.getAttributesSize());
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//public List<?> getFoo(); PROPERTY access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PROPERTY);
		attributes = contextClass.getAttributes().iterator();
		assertEquals(1, contextClass.getAttributesSize());
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//public List<?> getFoo(); FIELD access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(0, contextClass.getAttributesSize());

		//public List<?> getFoo(); NONE access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(0, contextClass.getAttributesSize());

		//public List<?> getFoo(); PUBLIC_MEMBER access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		attributes = contextClass.getAttributes().iterator();
		assertEquals(1, contextClass.getAttributesSize());
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());


		//add @XmlAttribute annotation and test each access type
		JavaResourceMethod resourceMethod = contextClass.getJavaResourceType().getMethods().iterator().next();
		AnnotatedElement annotatedElement = this.annotatedElement(resourceMethod);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.addMarkerAnnotation(declaration.getDeclaration(), XmlAttributeAnnotation.ANNOTATION_NAME);
			}
		});
		//@XmlAttribute
		//public List<?> getFoo(); PROPERTY access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PROPERTY);
		attributes = contextClass.getAttributes().iterator();
		assertEquals(1, contextClass.getAttributesSize());
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//public List<?> getFoo(); FIELD access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//public List<?> getFoo(); NONE access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//public List<?> getFoo(); PUBLIC_MEMBER access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());
	
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.removeAnnotation(declaration, XmlAttributeAnnotation.ANNOTATION_NAME);
			}
		});
	}

	protected void publicGetterSetterTest(JaxbPersistentClass contextClass) {
		//public int getFoo();, public void setFoo(int); PUBLIC_MEMBER access - persistent
		assertEquals(1, contextClass.getAttributesSize());
		Iterator<JaxbPersistentAttribute> attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//public int getFoo();, public void setFoo(int); PROPERTY access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//public int getFoo();, public void setFoo(int); FIELD access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(0, contextClass.getAttributesSize());

		//public int getFoo();, public void setFoo(int); NONE access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(0, contextClass.getAttributesSize());

		//public int getFoo();, public void setFoo(int); PUBLIC_MEMBER access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());


		//add @XmlAttribute annotation and test each access type
		JavaResourceMethod resourceMethod = ((JaxbPersistentProperty) contextClass.getAttributes().iterator().next()).getResourceGetterMethod();
		AnnotatedElement annotatedElement = this.annotatedElement(resourceMethod);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.addMarkerAnnotation(declaration.getDeclaration(), XmlAttributeAnnotation.ANNOTATION_NAME);
			}
		});
		//@XmlAttribute
		//public int getFoo();, public void setFoo(int); PROPERTY access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//public int getFoo();, public void setFoo(int); FIELD access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//public int getFoo();, public void setFoo(int); NONE access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//public int getFoo();, public void setFoo(int); PUBLIC_MEMBER access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());
	
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.removeAnnotation(declaration, XmlAttributeAnnotation.ANNOTATION_NAME);
			}
		});
	}

	protected void publicStaticGetterSetterTest(JaxbPersistentClass contextClass) {
		Iterator<JaxbPersistentAttribute> attributes = contextClass.getAttributes().iterator();
		JaxbPersistentProperty contextProperty = (JaxbPersistentProperty) attributes.next();
		JavaResourceMethod resourceGetter = contextProperty.getResourceGetterMethod();
		JavaResourceMethod resourceSetter = contextProperty.getResourceSetterMethod();

		//public static int getFoo();, public static void setFoo(int); PUBLIC_MEMBER access - not persistent
		this.addModifiers(resourceGetter, ModifierKeyword.STATIC_KEYWORD);
		this.addModifiers(resourceSetter, ModifierKeyword.STATIC_KEYWORD);
		assertEquals(0, contextClass.getAttributesSize());


		//public static int getFoo();, public static void setFoo(int); PROPERTY access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(0, contextClass.getAttributesSize());

		//public static int getFoo();, public static void setFoo(int); FIELD access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(0, contextClass.getAttributesSize());

		//public static int getFoo();, public static void setFoo(int); NONE access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(0, contextClass.getAttributesSize());

		//public static int getFoo();, public static void setFoo(int); PUBLIC_MEMBER access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(0, contextClass.getAttributesSize());


		//add @XmlAttribute annotation and test each access type
		AnnotatedElement annotatedElement = this.annotatedElement(resourceGetter);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.addMarkerAnnotation(declaration.getDeclaration(), XmlAttributeAnnotation.ANNOTATION_NAME);
			}
		});
		//@XmlAttribute
		//public static int getFoo();, public static void setFoo(int); PROPERTY access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//public static int getFoo();, public static void setFoo(int); FIELD access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//public static int getFoo();, public static void setFoo(int); NONE access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//public static int getFoo();, public static void setFoo(int); PUBLIC_MEMBER access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());
	
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.removeAnnotation(declaration, XmlAttributeAnnotation.ANNOTATION_NAME);
			}
		});
		this.removeModifiers(resourceGetter, ModifierKeyword.STATIC_KEYWORD);
		this.removeModifiers(resourceSetter, ModifierKeyword.STATIC_KEYWORD);
	}

	protected void privateGetterSetterTest(JaxbPersistentClass contextClass) {
		Iterator<JaxbPersistentAttribute> attributes = contextClass.getAttributes().iterator();
		JaxbPersistentProperty contextProperty = (JaxbPersistentProperty) attributes.next();
		JavaResourceMethod resourceGetter = contextProperty.getResourceGetterMethod();
		JavaResourceMethod resourceSetter = contextProperty.getResourceSetterMethod();

		//private int getFoo();, private void setFoo(int); PUBLIC_MEMBER access - not persistent
		this.removeModifiers(resourceGetter, ModifierKeyword.PUBLIC_KEYWORD);
		this.removeModifiers(resourceSetter, ModifierKeyword.PUBLIC_KEYWORD);
		this.addModifiers(resourceGetter, ModifierKeyword.PRIVATE_KEYWORD);
		this.addModifiers(resourceSetter, ModifierKeyword.PRIVATE_KEYWORD);
		assertEquals(0, contextClass.getAttributesSize());


		//private int getFoo();, private void setFoo(int); PROPERTY access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//private int getFoo();, private void setFoo(int); FIELD access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(0, contextClass.getAttributesSize());

		//private int getFoo();, private void setFoo(int); NONE access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(0, contextClass.getAttributesSize());

		//private int getFoo();, private void setFoo(int); PUBLIC_MEMBER access - not persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(0, contextClass.getAttributesSize());


		//add @XmlAttribute annotation and test each access type
		AnnotatedElement annotatedElement = this.annotatedElement(resourceGetter);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.addMarkerAnnotation(declaration.getDeclaration(), XmlAttributeAnnotation.ANNOTATION_NAME);
			}
		});
		//@XmlAttribute
		//private int getFoo();, private void setFoo(int); PROPERTY access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//private int getFoo();, private void setFoo(int); FIELD access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//private int getFoo();, private void setFoo(int); NONE access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//private int getFoo();, private void setFoo(int); PUBLIC_MEMBER access - persistent
		this.setAccessTypeInJavaSource(contextClass, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(1, contextClass.getAttributesSize());
		attributes = contextClass.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());
	
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.removeAnnotation(declaration, XmlAttributeAnnotation.ANNOTATION_NAME);
			}
		});
		this.removeModifiers(resourceGetter, ModifierKeyword.PRIVATE_KEYWORD);
		this.removeModifiers(resourceSetter, ModifierKeyword.PRIVATE_KEYWORD);
		this.addModifiers(resourceGetter, ModifierKeyword.PUBLIC_KEYWORD);
		this.addModifiers(resourceSetter, ModifierKeyword.PUBLIC_KEYWORD);
	}

	public void testModifyXmlJavaTypeAdapter() throws Exception {
		createTypeWithXmlType();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		AbstractJavaResourceType resourceType = persistentClass.getJavaResourceType();

		XmlJavaTypeAdapterAnnotation xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceType.getAnnotation(0, XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
		assertNull(persistentClass.getXmlJavaTypeAdapter());
		assertNull(xmlJavaTypeAdapterAnnotation);

		persistentClass.addXmlJavaTypeAdapter();
		xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceType.getAnnotation(0, XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
		assertNotNull(persistentClass.getXmlJavaTypeAdapter());
		assertNotNull(xmlJavaTypeAdapterAnnotation);

		persistentClass.removeXmlJavaTypeAdapter();
		xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceType.getAnnotation(0, XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
	}

	public void testUpdateXmlJavaTypeAdapter() throws Exception {
		createTypeWithXmlType();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		AbstractJavaResourceType resourceType = persistentClass.getJavaResourceType();

		XmlJavaTypeAdapterAnnotation xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceType.getAnnotation(0, XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
		assertNull(persistentClass.getXmlJavaTypeAdapter());
		assertNull(xmlJavaTypeAdapterAnnotation);


		//add an XmlJavaTypeAdapter annotation
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.addMarkerAnnotation(declaration.getDeclaration(), XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
			}
		});
		xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceType.getAnnotation(0, XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
		assertNotNull(persistentClass.getXmlJavaTypeAdapter());
		assertNotNull(xmlJavaTypeAdapterAnnotation);

		//remove the XmlJavaTypeAdapter annotation
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.removeAnnotation(declaration, XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
			}
		});
		xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceType.getAnnotation(0, XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
		assertNull(persistentClass.getXmlJavaTypeAdapter());
		assertNull(xmlJavaTypeAdapterAnnotation);
	}

	protected void addModifiers(JavaResourceMember resourceMember, final ModifierKeyword... modifiers) {
		AnnotatedElement annotatedElement = this.annotatedElement(resourceMember);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.addModifiers((BodyDeclaration) declaration.getDeclaration(), modifiers);
			}
		});
	}

	protected void removeModifiers(JavaResourceMember resourceMember, final ModifierKeyword... modifiers) {
		AnnotatedElement annotatedElement = this.annotatedElement(resourceMember);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.removeModifiers((BodyDeclaration) declaration.getDeclaration(), modifiers);
			}
		});
	}

	protected void addModifiers(BodyDeclaration bodyDeclaration, ModifierKeyword... modifiers) {
		for (ModifierKeyword modifier : modifiers) {
			this.modifiers(bodyDeclaration).add(bodyDeclaration.getAST().newModifier(modifier));
		}
	}

	protected void removeModifiers(BodyDeclaration bodyDeclaration, ModifierKeyword... modifierKeywords) {
		for (ModifierKeyword modifierKeyWord : modifierKeywords) {
			for (Iterator<IExtendedModifier> stream = this.modifiers(bodyDeclaration).iterator(); stream.hasNext();) {
				IExtendedModifier modifier = stream.next();
				if (modifier.isModifier()) {
					if (((Modifier) modifier).getKeyword() == modifierKeyWord) {
						stream.remove();
						break;
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	protected List<IExtendedModifier> modifiers(BodyDeclaration bodyDeclaration) {
		return bodyDeclaration.modifiers();
	}

	protected void setAccessTypeInJavaSource(JaxbPersistentClass contextClass, final String accessType) {
		JavaResourceType resourceClass = contextClass.getJavaResourceType();
		AnnotatedElement annotatedElement = this.annotatedElement(resourceClass);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaPersistentClassTests.this.setEnumMemberValuePair(declaration, XmlAccessorTypeAnnotation.ANNOTATION_NAME, accessType);
			}
		});
	}

}