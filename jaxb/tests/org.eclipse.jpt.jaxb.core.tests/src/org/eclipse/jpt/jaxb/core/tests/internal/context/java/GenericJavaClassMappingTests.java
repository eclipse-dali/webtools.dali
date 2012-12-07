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
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMember;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.ArrayIterator;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlAccessOrder;
import org.eclipse.jpt.jaxb.core.context.XmlAccessType;
import org.eclipse.jpt.jaxb.core.context.java.JavaClass;
import org.eclipse.jpt.jaxb.core.context.java.JavaClassMapping;
import org.eclipse.jpt.jaxb.core.internal.context.java.PropertyAccessor;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorOrderAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorTypeAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlRootElementAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSeeAlsoAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlTypeAnnotation;
import org.eclipse.jpt.jaxb.core.tests.internal.context.JaxbContextModelTestCase;


@SuppressWarnings("nls")
public class GenericJavaClassMappingTests
		extends JaxbContextModelTestCase {
	
	public GenericJavaClassMappingTests(String name) {
		super(name);
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
		createClassWithXmlType();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JavaResourceAbstractType resourceType = jaxbClass.getJavaResourceType();
		
		assertNull(classMapping.getSpecifiedFactoryClass());
		assertEquals(JAXB.XML_TYPE__DEFAULT_FACTORY_CLASS, classMapping.getFactoryClass());
		
		classMapping.setSpecifiedFactoryClass("foo");
		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(JAXB.XML_TYPE);
		assertEquals("foo", xmlTypeAnnotation.getFactoryClass());
		assertEquals("foo", classMapping.getFactoryClass());
		
		classMapping.setSpecifiedFactoryClass(null);
		xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(JAXB.XML_TYPE);
		assertNull(xmlTypeAnnotation.getFactoryClass());
		assertNull(classMapping.getSpecifiedFactoryClass());
		assertEquals(JAXB.XML_TYPE__DEFAULT_FACTORY_CLASS, classMapping.getFactoryClass());
		
		//add another annotation so that the context model does not get blown away
		classMapping.setSpecifiedAccessType(XmlAccessType.FIELD);
		resourceType.removeAnnotation(JAXB.XML_TYPE);
		
		//set factoryClass again, this time starting with no XmlType annotation
		classMapping.setSpecifiedFactoryClass("foo");
		xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(JAXB.XML_TYPE);
		assertEquals("foo", xmlTypeAnnotation.getFactoryClass());
		assertEquals("foo", classMapping.getFactoryClass());
	}
	
	public void testUpdateFactoryClass() throws Exception {
		createClassWithXmlType();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JavaResourceAbstractType resourceType = jaxbClass.getJavaResourceType();
		
		assertNull(classMapping.getSpecifiedFactoryClass());
		assertEquals(JAXB.XML_TYPE__DEFAULT_FACTORY_CLASS, classMapping.getFactoryClass());
		
		//add a factoryClass member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.addXmlTypeTypeMemberValuePair(declaration, JAXB.XML_TYPE__FACTORY_CLASS, "Foo");
			}
		});
		assertEquals("Foo", classMapping.getFactoryClass());

		//remove the factoryClass member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlTypeAnnotation = (NormalAnnotation) GenericJavaClassMappingTests.this.getXmlTypeAnnotation(declaration);
				GenericJavaClassMappingTests.this.values(xmlTypeAnnotation).remove(0);
			}
		});
		assertNull(classMapping.getSpecifiedFactoryClass());
		assertEquals(JAXB.XML_TYPE__DEFAULT_FACTORY_CLASS, classMapping.getFactoryClass());
	}

	public void testModifyFactoryMethod() throws Exception {
		createClassWithXmlType();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JavaResourceAbstractType resourceType = jaxbClass.getJavaResourceType();
		
		assertNull(classMapping.getFactoryMethod());
		
		classMapping.setFactoryMethod("foo");
		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(JAXB.XML_TYPE);
		assertEquals("foo", xmlTypeAnnotation.getFactoryMethod());
		assertEquals("foo", classMapping.getFactoryMethod());
		
		classMapping.setFactoryMethod(null);
		xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(JAXB.XML_TYPE);
		assertNull(xmlTypeAnnotation.getFactoryMethod());
		assertNull(classMapping.getFactoryMethod());
	
		//add another annotation so that the context model does not get blown away
		classMapping.setSpecifiedAccessType(XmlAccessType.FIELD);
		resourceType.removeAnnotation(JAXB.XML_TYPE);
		
		//set factoryMethod again, this time starting with no XmlType annotation
		classMapping.setFactoryMethod("foo");
		xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(JAXB.XML_TYPE);
		assertEquals("foo", xmlTypeAnnotation.getFactoryMethod());
		assertEquals("foo", classMapping.getFactoryMethod());
	}
	
	public void testUpdateFactoryMethod() throws Exception {
		createClassWithXmlType();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JavaResourceAbstractType resourceType = jaxbClass.getJavaResourceType();
		
		assertNull(classMapping.getFactoryMethod());
		
		//add a factoryMethod member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.addXmlTypeMemberValuePair(declaration, JAXB.XML_TYPE__FACTORY_METHOD, "foo");
			}
		});
		assertEquals("foo", classMapping.getFactoryMethod());

		//remove the factoryMethod member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlTypeAnnotation = (NormalAnnotation) GenericJavaClassMappingTests.this.getXmlTypeAnnotation(declaration);
				GenericJavaClassMappingTests.this.values(xmlTypeAnnotation).remove(0);
			}
		});
		assertNull(classMapping.getFactoryMethod());
	}

	public void testModifySchemaTypeName() throws Exception {
		createClassWithXmlType();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JavaResourceAbstractType resourceType = jaxbClass.getJavaResourceType();
		String defaultXmlTypeName = Introspector.decapitalize(TYPE_NAME);
		
		assertNull(classMapping.getQName().getSpecifiedName());
		assertEquals(defaultXmlTypeName, classMapping.getQName().getDefaultName());
		assertEquals(defaultXmlTypeName, classMapping.getQName().getName());
		
		classMapping.getQName().setSpecifiedName("foo");
		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(JAXB.XML_TYPE);
		assertEquals("foo", xmlTypeAnnotation.getName());
		assertEquals("foo", classMapping.getQName().getSpecifiedName());
		assertEquals("foo", classMapping.getQName().getName());
		
		classMapping.getQName().setSpecifiedName(null);
		xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(JAXB.XML_TYPE);
		assertNull(xmlTypeAnnotation.getName());
		assertNull(classMapping.getQName().getSpecifiedName());
		assertEquals(defaultXmlTypeName, classMapping.getQName().getName());	
		
		//add another annotation so that the context model does not get blown away
		classMapping.setSpecifiedAccessType(XmlAccessType.FIELD);
		resourceType.removeAnnotation(JAXB.XML_TYPE);
		
		//set name again, this time starting with no XmlType annotation
		classMapping.getQName().setSpecifiedName("foo");
		xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(JAXB.XML_TYPE);
		assertEquals("foo", xmlTypeAnnotation.getName());
		assertEquals("foo", classMapping.getQName().getSpecifiedName());
		assertEquals("foo", classMapping.getQName().getName());
	}
	
	public void testUpdateSchemaTypeName() throws Exception {
		createClassWithXmlType();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JavaResourceAbstractType resourceType = jaxbClass.getJavaResourceType();
		String defaultXmlTypeName = Introspector.decapitalize(TYPE_NAME);
		
		assertNull(classMapping.getQName().getSpecifiedName());
		assertEquals(defaultXmlTypeName, classMapping.getQName().getDefaultName());
		assertEquals(defaultXmlTypeName, classMapping.getQName().getName());
		
		//add a namespace member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.addXmlTypeMemberValuePair(declaration, JAXB.XML_TYPE__NAME, "foo");
			}
		});
		assertEquals("foo", classMapping.getQName().getSpecifiedName());
		assertEquals("foo", classMapping.getQName().getName());
		
		//remove the namespace member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlTypeAnnotation = (NormalAnnotation) GenericJavaClassMappingTests.this.getXmlTypeAnnotation(declaration);
				GenericJavaClassMappingTests.this.values(xmlTypeAnnotation).remove(0);
			}
		});
		assertNull(classMapping.getQName().getSpecifiedName());
		assertEquals(defaultXmlTypeName, classMapping.getQName().getName());
	}

	public void testModifyNamespace() throws Exception {
		createClassWithXmlType();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JavaResourceAbstractType resourceType = jaxbClass.getJavaResourceType();
		
		assertNull(classMapping.getQName().getSpecifiedNamespace());
		assertEquals("", classMapping.getQName().getDefaultNamespace());
		assertEquals("", classMapping.getQName().getNamespace());
		
		classMapping.getQName().setSpecifiedNamespace("foo");
		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(JAXB.XML_TYPE);
		assertEquals("foo", xmlTypeAnnotation.getNamespace());
		assertEquals("foo", classMapping.getQName().getSpecifiedNamespace());
		assertEquals("foo", classMapping.getQName().getNamespace());
		
		classMapping.getQName().setSpecifiedNamespace(null);
		xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(JAXB.XML_TYPE);
		assertNull(xmlTypeAnnotation.getNamespace());
		assertNull(classMapping.getQName().getSpecifiedNamespace());
		assertEquals("", classMapping.getQName().getNamespace());
		
		//add another annotation so that the context model does not get blown away
		classMapping.setSpecifiedAccessType(XmlAccessType.FIELD);
		resourceType.removeAnnotation(JAXB.XML_TYPE);
		
		//set namespace again, this time starting with no XmlType annotation
		classMapping.getQName().setSpecifiedNamespace("foo");
		xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(JAXB.XML_TYPE);
		assertEquals("foo", xmlTypeAnnotation.getNamespace());
		assertEquals("foo", classMapping.getQName().getSpecifiedNamespace());
		assertEquals("foo", classMapping.getQName().getNamespace());
	}
	
	public void testUpdateNamespace() throws Exception {
		createClassWithXmlType();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JavaResourceAbstractType resourceType = jaxbClass.getJavaResourceType();
		
		assertNull(classMapping.getQName().getSpecifiedNamespace());
		assertEquals("", classMapping.getQName().getDefaultNamespace());
		assertEquals("", classMapping.getQName().getNamespace());
		
		//add a namespace member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.addXmlTypeMemberValuePair(declaration, JAXB.XML_TYPE__NAMESPACE, "foo");
			}
		});
		assertEquals("foo", classMapping.getQName().getSpecifiedNamespace());
		assertEquals("foo", classMapping.getQName().getNamespace());
		
		//remove the namespace member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlTypeAnnotation = (NormalAnnotation) GenericJavaClassMappingTests.this.getXmlTypeAnnotation(declaration);
				GenericJavaClassMappingTests.this.values(xmlTypeAnnotation).remove(0);
			}
		});
		assertNull(classMapping.getQName().getSpecifiedNamespace());
		assertEquals("", classMapping.getQName().getNamespace());
	}

	public void testModifyAccessType() throws Exception {
		createXmlTypeWithAccessorType();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JavaResourceAbstractType resourceType = jaxbClass.getJavaResourceType();
		
		assertEquals(XmlAccessType.PROPERTY, classMapping.getSpecifiedAccessType());
		assertEquals(XmlAccessType.PROPERTY, classMapping.getAccessType());
		assertEquals(XmlAccessType.PUBLIC_MEMBER, classMapping.getDefaultAccessType());
		
		classMapping.setSpecifiedAccessType(XmlAccessType.FIELD);
		XmlAccessorTypeAnnotation accessorTypeAnnotation = (XmlAccessorTypeAnnotation) resourceType.getAnnotation(JAXB.XML_ACCESSOR_TYPE);
		assertEquals(org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType.FIELD, accessorTypeAnnotation.getValue());
		assertEquals(XmlAccessType.FIELD, classMapping.getAccessType());

		classMapping.setSpecifiedAccessType(XmlAccessType.PUBLIC_MEMBER);
		assertEquals(org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType.PUBLIC_MEMBER, accessorTypeAnnotation.getValue());
		assertEquals(XmlAccessType.PUBLIC_MEMBER, classMapping.getAccessType());

		classMapping.setSpecifiedAccessType(XmlAccessType.NONE);
		assertEquals(org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType.NONE, accessorTypeAnnotation.getValue());
		assertEquals(XmlAccessType.NONE, classMapping.getAccessType());
		
		classMapping.setSpecifiedAccessType(null);
		accessorTypeAnnotation = (XmlAccessorTypeAnnotation) resourceType.getAnnotation(JAXB.XML_ACCESSOR_TYPE);
		assertNull(accessorTypeAnnotation.getValue());
		assertNull(classMapping.getSpecifiedAccessType());
		assertEquals(XmlAccessType.PUBLIC_MEMBER, classMapping.getAccessType());
		assertEquals(XmlAccessType.PUBLIC_MEMBER, classMapping.getDefaultAccessType());
	}
	
	public void testUpdateAccessType() throws Exception {
		createXmlTypeWithAccessorType();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JavaResourceAbstractType resourceType = jaxbClass.getJavaResourceType();
		
		assertEquals(XmlAccessType.PROPERTY, classMapping.getSpecifiedAccessType());
		assertEquals(XmlAccessType.PROPERTY, classMapping.getAccessType());
		assertEquals(XmlAccessType.PUBLIC_MEMBER, classMapping.getDefaultAccessType());
		
		//set the accesser type value to FIELD
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.setEnumMemberValuePair(declaration, JAXB.XML_ACCESSOR_TYPE, JAXB.XML_ACCESS_TYPE__FIELD);
			}
		});
		assertEquals(XmlAccessType.FIELD, classMapping.getAccessType());

		//set the accesser type value to PUBLIC_MEMBER
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.setEnumMemberValuePair(declaration, JAXB.XML_ACCESSOR_TYPE, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
			}
		});
		assertEquals(XmlAccessType.PUBLIC_MEMBER, classMapping.getAccessType());

		//set the accesser type value to NONE
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.setEnumMemberValuePair(declaration, JAXB.XML_ACCESSOR_TYPE, JAXB.XML_ACCESS_TYPE__NONE);
			}
		});
		assertEquals(XmlAccessType.NONE, classMapping.getAccessType());
		
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.removeAnnotation(declaration, JAXB.XML_ACCESSOR_TYPE);
			}
		});
		assertNull(classMapping.getSpecifiedAccessType());
		assertEquals(XmlAccessType.PUBLIC_MEMBER, classMapping.getAccessType());
		assertEquals(XmlAccessType.PUBLIC_MEMBER, classMapping.getDefaultAccessType());
	}

	/**
	 * If there is a @XmlAccessorType on a class, then it is used.
	 * Otherwise, if a @XmlAccessorType exists on one of its super classes, then it is inherited.
	 * Otherwise, the @XmlAccessorType on a package is inherited. 	
	 */
	public void testGetDefaultAccessType() throws Exception {
		createClassWithXmlType();
		createTestSubType();
		
		JavaClass jaxbClass = (JavaClass) getContextRoot().getJavaType(FULLY_QUALIFIED_TYPE_NAME);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JavaClass childJaxbClass = (JavaClass) getContextRoot().getJavaType("test.AnnotationTestTypeChild");
		JavaClassMapping childClassMapping = childJaxbClass.getMapping();
		
		assertEquals(XmlAccessType.PUBLIC_MEMBER, childClassMapping.getDefaultAccessType());

		this.createPackageInfoWithAccessorType();
		assertEquals(XmlAccessType.PROPERTY, childClassMapping.getDefaultAccessType());

		classMapping.setSpecifiedAccessType(XmlAccessType.FIELD);
		assertEquals(XmlAccessType.FIELD, childClassMapping.getDefaultAccessType());

		JaxbPackageInfo contextPackageInfo = IterableTools.get(getContextRoot().getPackages(), 0).getPackageInfo();
		classMapping.setSpecifiedAccessType(null);
		assertEquals(XmlAccessType.PROPERTY, childClassMapping.getDefaultAccessType());
		contextPackageInfo.setSpecifiedAccessType(XmlAccessType.FIELD);
		assertEquals(XmlAccessType.FIELD, childClassMapping.getDefaultAccessType());

		contextPackageInfo.setSpecifiedAccessType(XmlAccessType.NONE);
		assertEquals(XmlAccessType.NONE, childClassMapping.getDefaultAccessType());

		contextPackageInfo.setSpecifiedAccessType(null);
		assertEquals(XmlAccessType.PUBLIC_MEMBER, childClassMapping.getDefaultAccessType());
	}

	public void testGetSuperPersistentClass() throws Exception {
		createClassWithXmlType();
		createTestSubType();
		
		JavaClass jaxbClass = (JavaClass) getContextRoot().getJavaType(FULLY_QUALIFIED_TYPE_NAME);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JavaClass childJaxbClass = (JavaClass) getContextRoot().getJavaType("test.AnnotationTestTypeChild");
		JavaClassMapping childClassMapping = childJaxbClass.getMapping();
		
		assertEquals(classMapping, childClassMapping.getSuperclass());

		//test that the superClass is not null even when it is unannotated
		AnnotatedElement annotatedElement = this.annotatedElement(jaxbClass.getJavaResourceType());
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.removeAnnotation(declaration, JAXB.XML_TYPE);
			}
		});
		assertEquals(classMapping, childClassMapping.getSuperclass());
	}

	public void testModifyAccessOrder() throws Exception {
		createXmlTypeWithAccessorOrder();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JavaResourceAbstractType resourceType = jaxbClass.getJavaResourceType();
		
		assertEquals(XmlAccessOrder.ALPHABETICAL, classMapping.getSpecifiedAccessOrder());
		assertEquals(XmlAccessOrder.ALPHABETICAL, classMapping.getAccessOrder());
		assertEquals(XmlAccessOrder.UNDEFINED, classMapping.getDefaultAccessOrder());

		classMapping.setSpecifiedAccessOrder(XmlAccessOrder.UNDEFINED);
		XmlAccessorOrderAnnotation accessorOrderAnnotation = (XmlAccessorOrderAnnotation) resourceType.getAnnotation(JAXB.XML_ACCESSOR_ORDER);
		assertEquals(org.eclipse.jpt.jaxb.core.resource.java.XmlAccessOrder.UNDEFINED, accessorOrderAnnotation.getValue());
		assertEquals(XmlAccessOrder.UNDEFINED, classMapping.getAccessOrder());

		classMapping.setSpecifiedAccessOrder(null);
		accessorOrderAnnotation = (XmlAccessorOrderAnnotation) resourceType.getAnnotation(JAXB.XML_ACCESSOR_ORDER);
		assertNull(accessorOrderAnnotation.getValue());
		assertNull(classMapping.getSpecifiedAccessOrder());
		assertEquals(XmlAccessOrder.UNDEFINED, classMapping.getAccessOrder());
		assertEquals(XmlAccessOrder.UNDEFINED, classMapping.getDefaultAccessOrder());
	}

	public void testUpdateAccessOrder() throws Exception {
		createXmlTypeWithAccessorOrder();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JavaResourceAbstractType resourceType = jaxbClass.getJavaResourceType();
		
		assertEquals(XmlAccessOrder.ALPHABETICAL, classMapping.getSpecifiedAccessOrder());
		assertEquals(XmlAccessOrder.ALPHABETICAL, classMapping.getAccessOrder());
		assertEquals(XmlAccessOrder.UNDEFINED, classMapping.getDefaultAccessOrder());

		//set the access order value to UNDEFINED
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.setEnumMemberValuePair(declaration, JAXB.XML_ACCESSOR_ORDER, JAXB.XML_ACCESS_ORDER__UNDEFINED);
			}
		});
		assertEquals(XmlAccessOrder.UNDEFINED, classMapping.getAccessOrder());

		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.removeAnnotation(declaration, JAXB.XML_ACCESSOR_ORDER);
			}
		});
		assertNull(classMapping.getSpecifiedAccessOrder());
		assertEquals(XmlAccessOrder.UNDEFINED, classMapping.getAccessOrder());
		assertEquals(XmlAccessOrder.UNDEFINED, classMapping.getDefaultAccessOrder());
	}

	public void testGetPropOrder() throws Exception {
		this.createClassWithXmlType();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JavaResourceAbstractType resourceType = jaxbClass.getJavaResourceType();
		
		ListIterator<String> props = classMapping.getPropOrder().iterator();
		assertFalse(props.hasNext());

		//add 2 prop orders
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.addProp(declaration, 0, "bar");
				GenericJavaClassMappingTests.this.addProp(declaration, 1, "foo");
			}
		});

		props = classMapping.getPropOrder().iterator();
		assertEquals("bar", props.next());
		assertEquals("foo", props.next());
		assertFalse(props.hasNext());
	}

	protected void addProp(ModifiedDeclaration declaration, int index, String prop) {
		this.addArrayElement(declaration, JAXB.XML_TYPE, index, JAXB.XML_TYPE__PROP_ORDER, this.newStringLiteral(declaration.getAst(), prop));		
	}

	public void testGetPropOrderSize() throws Exception {
		this.createClassWithXmlType();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JavaResourceAbstractType resourceType = jaxbClass.getJavaResourceType();
		
		assertEquals(0, classMapping.getPropOrderSize());

		//add 2 prop orders
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.addProp(declaration, 0, "bar");
				GenericJavaClassMappingTests.this.addProp(declaration, 1, "foo");
			}
		});
		assertEquals(2, classMapping.getPropOrderSize());
	}

	public void testAddProp() throws Exception {
		this.createClassWithXmlType();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JavaResourceAbstractType resourceType = jaxbClass.getJavaResourceType();
		
		classMapping.addProp(0, "bar");
		classMapping.addProp(0, "foo");
		classMapping.addProp(0, "baz");

		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(JAXB.XML_TYPE);
		ListIterator<String> props = xmlTypeAnnotation.getPropOrder().iterator();

		assertEquals("baz", props.next());
		assertEquals("foo", props.next());
		assertEquals("bar", props.next());
		assertFalse(props.hasNext());
	}

	public void testAddProp2() throws Exception {
		createClassWithXmlType();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JavaResourceAbstractType resourceType = jaxbClass.getJavaResourceType();
		
		classMapping.addProp(0, "bar");
		classMapping.addProp(1, "foo");
		classMapping.addProp(0, "baz");

		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(JAXB.XML_TYPE);
		ListIterator<String> props = xmlTypeAnnotation.getPropOrder().iterator();

		assertEquals("baz", props.next());
		assertEquals("bar", props.next());
		assertEquals("foo", props.next());
		assertFalse(props.hasNext());
	}

	public void testRemoveProp() throws Exception {
		createClassWithXmlType();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JavaResourceAbstractType resourceType = jaxbClass.getJavaResourceType();
		
		classMapping.addProp(0, "bar");
		classMapping.addProp(1, "foo");
		classMapping.addProp(2, "baz");

		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(JAXB.XML_TYPE);

		classMapping.removeProp(1);

		ListIterator<String> resourceProps = xmlTypeAnnotation.getPropOrder().iterator();
		assertEquals("bar", resourceProps.next());		
		assertEquals("baz", resourceProps.next());
		assertFalse(resourceProps.hasNext());

		classMapping.removeProp(1);
		resourceProps = xmlTypeAnnotation.getPropOrder().iterator();
		assertEquals("bar", resourceProps.next());
		assertFalse(resourceProps.hasNext());

		classMapping.removeProp(0);
		resourceProps = xmlTypeAnnotation.getPropOrder().iterator();
		assertFalse(resourceProps.hasNext());
	}

	public void testMoveProp() throws Exception {
		createClassWithXmlType();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JavaResourceAbstractType resourceType = jaxbClass.getJavaResourceType();
		
		classMapping.addProp(0, "bar");
		classMapping.addProp(1, "foo");
		classMapping.addProp(2, "baz");

		XmlTypeAnnotation xmlTypeAnnotation = (XmlTypeAnnotation) resourceType.getAnnotation(JAXB.XML_TYPE);

		assertEquals(3, xmlTypeAnnotation.getPropOrderSize());		

		classMapping.moveProp(2, 0);
		ListIterator<String> props = classMapping.getPropOrder().iterator();
		assertEquals("foo", props.next());
		assertEquals("baz", props.next());
		assertEquals("bar", props.next());		
		assertFalse(props.hasNext());

		ListIterator<String> resourceProps = xmlTypeAnnotation.getPropOrder().iterator();
		assertEquals("foo", resourceProps.next());
		assertEquals("baz", resourceProps.next());
		assertEquals("bar", resourceProps.next());
		
		classMapping.moveProp(0, 1);
		props = classMapping.getPropOrder().iterator();
		assertEquals("baz", props.next());
		assertEquals("foo", props.next());
		assertEquals("bar", props.next());		
		assertFalse(props.hasNext());

		resourceProps = xmlTypeAnnotation.getPropOrder().iterator();
		assertEquals("baz", resourceProps.next());
		assertEquals("foo", resourceProps.next());
		assertEquals("bar", resourceProps.next());
	}

	public void testSyncProps() throws Exception {
		createClassWithXmlType();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JavaResourceAbstractType resourceType = jaxbClass.getJavaResourceType();
		
		ListIterator<String> props = classMapping.getPropOrder().iterator();
		assertFalse(props.hasNext());

		//add 3 prop orders
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.addProp(declaration, 0, "bar");
				GenericJavaClassMappingTests.this.addProp(declaration, 1, "foo");
				GenericJavaClassMappingTests.this.addProp(declaration, 2, "baz");
			}
		});

		props = classMapping.getPropOrder().iterator();
		assertTrue(props.hasNext());
		assertEquals("bar", props.next());
		assertEquals("foo", props.next());
		assertEquals("baz", props.next());
		assertFalse(props.hasNext());


		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.moveProp(declaration, 2, 0);
			}
		});

		props = classMapping.getPropOrder().iterator();
		assertTrue(props.hasNext());
		assertEquals("foo", props.next());
		assertEquals("baz", props.next());
		assertEquals("bar", props.next());
		assertFalse(props.hasNext());


		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.moveProp(declaration, 0, 1);
			}
		});

		props = classMapping.getPropOrder().iterator();
		assertTrue(props.hasNext());
		assertEquals("baz", props.next());
		assertEquals("foo", props.next());
		assertEquals("bar", props.next());
		assertFalse(props.hasNext());


		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.removeProp(declaration, 1);
			}
		});

		props = classMapping.getPropOrder().iterator();
		assertTrue(props.hasNext());
		assertEquals("baz", props.next());
		assertEquals("bar", props.next());
		assertFalse(props.hasNext());


		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.removeProp(declaration, 1);
			}
		});

		props = classMapping.getPropOrder().iterator();
		assertTrue(props.hasNext());
		assertEquals("baz", props.next());
		assertFalse(props.hasNext());


		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.removeProp(declaration, 0);
			}
		});

		props = classMapping.getPropOrder().iterator();
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
		return declaration.getAnnotationNamed(JAXB.XML_TYPE);
	}

	protected void moveProp(ModifiedDeclaration declaration, int targetIndex, int sourceIndex) {
		this.moveArrayElement((NormalAnnotation) getXmlTypeAnnotation(declaration), JAXB.XML_TYPE__PROP_ORDER, targetIndex, sourceIndex);
	}

	protected void removeProp(ModifiedDeclaration declaration, int index) {
		this.removeArrayElement((NormalAnnotation) getXmlTypeAnnotation(declaration), JAXB.XML_TYPE__PROP_ORDER, index);
	}

	public void testModifyXmlRootElement() throws Exception {
		createClassWithXmlType();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JavaResourceAbstractType resourceType = jaxbClass.getJavaResourceType();
		
		assertNull(classMapping.getXmlRootElement());
		
		classMapping.addXmlRootElement().getQName().setSpecifiedName("foo");
		XmlRootElementAnnotation xmlRootElementAnnotation = (XmlRootElementAnnotation) resourceType.getAnnotation(JAXB.XML_ROOT_ELEMENT);
		assertEquals("foo", xmlRootElementAnnotation.getName());
		assertEquals("foo", classMapping.getXmlRootElement().getQName().getName());
		
		classMapping.removeXmlRootElement();
		xmlRootElementAnnotation = (XmlRootElementAnnotation) resourceType.getAnnotation(JAXB.XML_ROOT_ELEMENT);
		assertNull(xmlRootElementAnnotation);
		assertNull(classMapping.getXmlRootElement());
	}

	public void testUpdateXmlRootElement() throws Exception {
		createClassWithXmlType();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JavaResourceAbstractType resourceType = jaxbClass.getJavaResourceType();
		
		assertNull(classMapping.getXmlRootElement());
		
		//add a XmlRootElement annotation
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation annotation = GenericJavaClassMappingTests.this.addNormalAnnotation(declaration.getDeclaration(), JAXB.XML_ROOT_ELEMENT);
				GenericJavaClassMappingTests.this.addMemberValuePair(annotation, JAXB.XML_ROOT_ELEMENT__NAME, "foo");
			}
		});
		assertEquals("foo", classMapping.getXmlRootElement().getQName().getName());
		
		//remove the XmlRootElement annotation
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.removeAnnotation(declaration, JAXB.XML_ROOT_ELEMENT);
			}
		});
		assertNull(classMapping.getXmlRootElement());
	}
	
	public void testUpdateFieldAttributes() throws Exception {
		this.createTestXmlTypeWithFieldAndPublicMemberAccess();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		
		//public int foo;
		publicFieldTest(classMapping);
		
		//public transient int foo;
		publicTransientFieldTest(classMapping);
		
		//public static int foo;
		publicStaticFieldTest(classMapping);
		
		//public final int foo;
		publicFinalFieldTest(classMapping);
		
		//public static final int foo;
		publicStaticFinalFieldTest(classMapping);
		
		//private int foo;
		privateFieldTest(classMapping);
		
		//  private transient int foo;
		privateTransientFieldTest(classMapping);
		
		//  @XmlAttribute
		//  private static int foo; //persistent
		privateStaticFieldTest(classMapping);
		
		//  @XmlAttribute
		//  private static final int foo; //persistent
		privateStaticFinalFieldTest(classMapping);
	}
	
	protected void publicFieldTest(JavaClassMapping classMapping) {
		//public int foo; PUBLIC_MEMBER access - persistent
		assertEquals(1, classMapping.getAttributesSize());
		Iterator<JaxbPersistentAttribute> attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());
		
		//public int foo; //PROPERTY access - not persistent
		setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(0, classMapping.getAttributesSize());
		
		//public int foo; //FIELD access - persistent
		setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());
		
		//public int foo; //NONE access - not persistent
		setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(0, classMapping.getAttributesSize());
		
		//public int foo; PUBLIC_MEMBER access - persistent
		setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());
		
		//add @XmlTransient annotation and test each access type
		JavaResourceField resourceField = (JavaResourceField) classMapping.getAttributes().iterator().next().getJavaResourceAttribute();
		AnnotatedElement annotatedElement = this.annotatedElement(resourceField);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_TRANSIENT);
			}
		});
		//@XmlTransient
		//public int foo; //PROPERTY access - not "persistent", but comes in to our context model because it is "mapped"
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlTransient
		//public int foo; //FIELD access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlTransient
		//public int foo; //NONE access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlTransient
		//public int foo; PUBLIC_MEMBER access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());
	
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.removeAnnotation(declaration, JAXB.XML_TRANSIENT);
			}
		});
	}

	protected void publicTransientFieldTest(JavaClassMapping classMapping) {
		Iterator<JaxbPersistentAttribute> attributes = classMapping.getAttributes().iterator();
		JavaResourceField resourceField = (JavaResourceField) attributes.next().getJavaResourceAttribute();

		//public transient int foo; PUBLIC_MEMBER access - not persistent
		this.addModifiers(resourceField, ModifierKeyword.TRANSIENT_KEYWORD);
		assertEquals(0, classMapping.getAttributesSize());

		//public transient int foo; //PROPERTY access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(0, classMapping.getAttributesSize());

		//public transient int foo; //FIELD access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(0, classMapping.getAttributesSize());

		//public transient int foo; //NONE access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(0, classMapping.getAttributesSize());

		//public transient int foo; PUBLIC_MEMBER access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(0, classMapping.getAttributesSize());

		this.removeModifiers(resourceField, ModifierKeyword.TRANSIENT_KEYWORD);
	}

	protected void publicStaticFieldTest(JavaClassMapping classMapping) {
		Iterator<JaxbPersistentAttribute> attributes = classMapping.getAttributes().iterator();
		JavaResourceField resourceField = (JavaResourceField) attributes.next().getJavaResourceAttribute();

		//public static int foo; PUBLIC_MEMBER access - not persistent
		this.addModifiers(resourceField, ModifierKeyword.STATIC_KEYWORD);
		assertEquals(0, classMapping.getAttributesSize());

		//public static int foo; //PROPERTY access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(0, classMapping.getAttributesSize());

		//public static int foo; //FIELD access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(0, classMapping.getAttributesSize());

		//public static int foo; //NONE access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(0, classMapping.getAttributesSize());

		//public static int foo; PUBLIC_MEMBER access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(0, classMapping.getAttributesSize());

		this.removeModifiers(resourceField, ModifierKeyword.STATIC_KEYWORD);
	}

	protected void publicFinalFieldTest(JavaClassMapping classMapping) {
		Iterator<JaxbPersistentAttribute> attributes = classMapping.getAttributes().iterator();
		JavaResourceField resourceField = (JavaResourceField) attributes.next().getJavaResourceAttribute();

		//public final int foo; PUBLIC_MEMBER access - persistent
		this.addModifiers(resourceField, ModifierKeyword.FINAL_KEYWORD);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//public final int foo; //PROPERTY access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(0, classMapping.getAttributesSize());

		//public final int foo; //FIELD access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//public final int foo; //NONE access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(0, classMapping.getAttributesSize());

		//public final int foo; PUBLIC_MEMBER access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		this.removeModifiers(resourceField, ModifierKeyword.FINAL_KEYWORD);
	}

	protected void publicStaticFinalFieldTest(JavaClassMapping classMapping) {
		Iterator<JaxbPersistentAttribute> attributes = classMapping.getAttributes().iterator();
		JavaResourceField resourceField = (JavaResourceField) attributes.next().getJavaResourceAttribute();

		//public static final int foo; PUBLIC_MEMBER access - not persistent
		this.addModifiers(resourceField, ModifierKeyword.STATIC_KEYWORD, ModifierKeyword.FINAL_KEYWORD);
		assertEquals(0, classMapping.getAttributesSize());

		//public static final int foo; //PROPERTY access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(0, classMapping.getAttributesSize());

		//public static final int foo; //FIELD access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(0, classMapping.getAttributesSize());

		//public static final int foo; //NONE access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(0, classMapping.getAttributesSize());

		//public static final int foo; PUBLIC_MEMBER access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(0, classMapping.getAttributesSize());

		this.removeModifiers(resourceField, ModifierKeyword.STATIC_KEYWORD, ModifierKeyword.FINAL_KEYWORD);
	}

	protected void privateFieldTest(JavaClassMapping classMapping) {
		Iterator<JaxbPersistentAttribute> attributes = classMapping.getAttributes().iterator();
		JavaResourceField resourceField = (JavaResourceField) attributes.next().getJavaResourceAttribute();
		removeModifiers(resourceField, ModifierKeyword.PUBLIC_KEYWORD);
		addModifiers(resourceField, ModifierKeyword.PRIVATE_KEYWORD);

		//private int foo; PUBLIC_MEMBER access - not persistent
		assertEquals(0, classMapping.getAttributesSize());

		//private int foo; //PROPERTY access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(0, classMapping.getAttributesSize());

		//private int foo; //FIELD access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//private int foo; //NONE access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(0, classMapping.getAttributesSize());

		//private int foo; PUBLIC_MEMBER access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(0, classMapping.getAttributesSize());


		//add @XmlAttribute annotation and test each access type
		AnnotatedElement annotatedElement = this.annotatedElement(resourceField);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_ATTRIBUTE);
			}
		});
		//@XmlAttribute
		//private int foo; //PROPERTY access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//private int foo; //FIELD access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//private int foo; //NONE access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//private int foo; PUBLIC_MEMBER access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());
	
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.removeAnnotation(declaration, JAXB.XML_ATTRIBUTE);
			}
		});
		removeModifiers(resourceField, ModifierKeyword.PRIVATE_KEYWORD);
		addModifiers(resourceField, ModifierKeyword.PUBLIC_KEYWORD);
	}

	protected void privateTransientFieldTest(JavaClassMapping classMapping) {
		Iterator<JaxbPersistentAttribute> attributes = classMapping.getAttributes().iterator();
		JavaResourceField resourceField = (JavaResourceField) attributes.next().getJavaResourceAttribute();
		removeModifiers(resourceField, ModifierKeyword.PUBLIC_KEYWORD);
		addModifiers(resourceField, ModifierKeyword.PRIVATE_KEYWORD, ModifierKeyword.TRANSIENT_KEYWORD);

		//private transient int foo; PUBLIC_MEMBER access - not persistent
		assertEquals(0, classMapping.getAttributesSize());

		//private transient int foo; //PROPERTY access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(0, classMapping.getAttributesSize());

		//private transient int foo; //FIELD access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(0, classMapping.getAttributesSize());

		//private transient int foo; //NONE access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(0, classMapping.getAttributesSize());

		//private transient int foo; PUBLIC_MEMBER access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(0, classMapping.getAttributesSize());


		//add @XmlAttribute annotation and test each access type
		AnnotatedElement annotatedElement = this.annotatedElement(resourceField);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_ATTRIBUTE);
			}
		});
		//@XmlAttribute
		//private transient int foo; //PROPERTY access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//private transient int foo; //FIELD access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//private transient int foo; //NONE access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//private transient int foo; PUBLIC_MEMBER access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());
	
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.removeAnnotation(declaration, JAXB.XML_ATTRIBUTE);
			}
		});
		removeModifiers(resourceField, ModifierKeyword.PRIVATE_KEYWORD, ModifierKeyword.TRANSIENT_KEYWORD);
		addModifiers(resourceField, ModifierKeyword.PUBLIC_KEYWORD);
	}

	protected void privateStaticFieldTest(JavaClassMapping classMapping) {
		Iterator<JaxbPersistentAttribute> attributes = classMapping.getAttributes().iterator();
		JavaResourceField resourceField = (JavaResourceField) attributes.next().getJavaResourceAttribute();
		removeModifiers(resourceField, ModifierKeyword.PUBLIC_KEYWORD);
		addModifiers(resourceField, ModifierKeyword.PRIVATE_KEYWORD, ModifierKeyword.STATIC_KEYWORD);

		//private static int foo; PUBLIC_MEMBER access - not persistent
		assertEquals(0, classMapping.getAttributesSize());

		//private static int foo; //PROPERTY access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(0, classMapping.getAttributesSize());

		//private static int foo; //FIELD access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(0, classMapping.getAttributesSize());

		//private static int foo; //NONE access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(0, classMapping.getAttributesSize());

		//private static int foo; PUBLIC_MEMBER access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(0, classMapping.getAttributesSize());


		//add @XmlAttribute annotation and test each access type
		AnnotatedElement annotatedElement = this.annotatedElement(resourceField);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_ATTRIBUTE);
			}
		});
		//@XmlAttribute
		//private static int foo; //PROPERTY access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//private static int foo; //FIELD access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//private static int foo; //NONE access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//private static int foo; PUBLIC_MEMBER access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());
	
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.removeAnnotation(declaration, JAXB.XML_ATTRIBUTE);
			}
		});
		removeModifiers(resourceField, ModifierKeyword.PRIVATE_KEYWORD, ModifierKeyword.STATIC_KEYWORD);
		addModifiers(resourceField, ModifierKeyword.PUBLIC_KEYWORD);
	}

	protected void privateStaticFinalFieldTest(JavaClassMapping classMapping) {
		Iterator<JaxbPersistentAttribute> attributes = classMapping.getAttributes().iterator();
		JavaResourceField resourceField = (JavaResourceField) attributes.next().getJavaResourceAttribute();
		removeModifiers(resourceField, ModifierKeyword.PUBLIC_KEYWORD);
		addModifiers(resourceField, ModifierKeyword.PRIVATE_KEYWORD, ModifierKeyword.STATIC_KEYWORD, ModifierKeyword.FINAL_KEYWORD);

		//private static final int foo; PUBLIC_MEMBER access - not persistent
		assertEquals(0, classMapping.getAttributesSize());

		//private static final int foo; //PROPERTY access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(0, classMapping.getAttributesSize());

		//private static final int foo; //FIELD access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(0, classMapping.getAttributesSize());

		//private static final int foo; //NONE access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(0, classMapping.getAttributesSize());

		//private static final int foo; PUBLIC_MEMBER access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(0, classMapping.getAttributesSize());


		//add @XmlAttribute annotation and test each access type
		AnnotatedElement annotatedElement = this.annotatedElement(resourceField);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_ATTRIBUTE);
			}
		});
		//@XmlAttribute
		//private static final int foo; //PROPERTY access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//private static final int foo; //FIELD access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//private static final int foo; //NONE access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//private static final int foo; PUBLIC_MEMBER access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());
	
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.removeAnnotation(declaration, JAXB.XML_ATTRIBUTE);
			}
		});
	}

	public void testUpdatePropertyAttributes() throws Exception {
		createTestXmlTypeWithPropertyAndPublicMemberAccess();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		
		//public int getFoo();, public void setFoo(int);
		publicGetterSetterTest(classMapping);
		
		//public static int getFoo();, public static void setFoo(int);
		publicStaticGetterSetterTest(classMapping);
		
		//private int getFoo();, private void setFoo(int);
		privateGetterSetterTest(classMapping);
	}
	
	public void testUpdateGetterPropertyAttributes() throws Exception {
		createTestXmlTypeWithPropertyGetterAndPublicMemberAccess();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		
		//public int getFoo(); PUBLIC_MEMBER access - not persistent
		assertEquals(0, classMapping.getAttributesSize());
		
		//public int getFoo(); PROPERTY access - not persistent
		setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(0, classMapping.getAttributesSize());
		
		//public int getFoo(); FIELD access - not persistent
		setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(0, classMapping.getAttributesSize());
		
		//public int getFoo(); NONE access - not persistent
		setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(0, classMapping.getAttributesSize());
		
		//public int getFoo(); PUBLIC_MEMBER access - not persistent
		setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(0, classMapping.getAttributesSize());
		
		//add @XmlAttribute annotation and test each access type
		JavaResourceMethod resourceMethod = classMapping.getJavaType().getJavaResourceType().getMethods().iterator().next();
		AnnotatedElement annotatedElement = this.annotatedElement(resourceMethod);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_ATTRIBUTE);
			}
		});
		//@XmlAttribute
		//public int getFoo(); PROPERTY access - persistent
		setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PROPERTY);
		Iterator<JaxbPersistentAttribute> attributes = classMapping.getAttributes().iterator();
		assertEquals(1, classMapping.getAttributesSize());
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//public int getFoo(); FIELD access - persistent
		setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());
		
		//@XmlAttribute
		//public int getFoo(); NONE access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());
		
		//@XmlAttribute
		//public int getFoo(); PUBLIC_MEMBER access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());
		
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.removeAnnotation(declaration, JAXB.XML_ATTRIBUTE);
			}
		});
	}
	
	public void testUpdateSetterPropertyAttributes() throws Exception {
		createTestXmlTypeWithPropertySetterAndPublicMemberAccess();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		
		//public void setFoo(int foo); PUBLIC_MEMBER access - not persistent
		assertEquals(0, classMapping.getAttributesSize());
		
		//public void setFoo(int foo); PROPERTY access - persistent
		setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(0, classMapping.getAttributesSize());
		
		//public void setFoo(int foo); FIELD access - not persistent
		setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(0, classMapping.getAttributesSize());
		
		//public void setFoo(int foo); NONE access - not persistent
		setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(0, classMapping.getAttributesSize());
		
		//public void setFoo(int foo); PUBLIC_MEMBER access - persistent
		setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(0, classMapping.getAttributesSize());
		
		//add @XmlAttribute annotation and test each access type
		JavaResourceMethod resourceMethod = classMapping.getJavaType().getJavaResourceType().getMethods().iterator().next();
		AnnotatedElement annotatedElement = this.annotatedElement(resourceMethod);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_ATTRIBUTE);
			}
		});
		//@XmlAttribute
		//public void setFoo(int foo);; PROPERTY access - persistent
		setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PROPERTY);
		Iterator<JaxbPersistentAttribute> attributes = classMapping.getAttributes().iterator();
		assertEquals(1, classMapping.getAttributesSize());
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());
		
		//@XmlAttribute
		//public void setFoo(int foo); FIELD access - persistent
		setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//public void setFoo(int foo); NONE access - persistent
		setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());
		
		//@XmlAttribute
		//public void setFoo(int foo); PUBLIC_MEMBER access - persistent
		setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());
		
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.removeAnnotation(declaration, JAXB.XML_ATTRIBUTE);
			}
		});
	}
	
	public void testUpdateGetterListPropertyAttributes() throws Exception {
		this.createTestXmlTypeWithPropertyGetterListAndPublicMemberAccess();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		
		//public List<?> getFoo(); PUBLIC_MEMBER access - persistent
		Iterator<JaxbPersistentAttribute> attributes = classMapping.getAttributes().iterator();
		assertEquals(1, classMapping.getAttributesSize());
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//public List<?> getFoo(); PROPERTY access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PROPERTY);
		attributes = classMapping.getAttributes().iterator();
		assertEquals(1, classMapping.getAttributesSize());
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//public List<?> getFoo(); FIELD access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(0, classMapping.getAttributesSize());

		//public List<?> getFoo(); NONE access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(0, classMapping.getAttributesSize());

		//public List<?> getFoo(); PUBLIC_MEMBER access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		attributes = classMapping.getAttributes().iterator();
		assertEquals(1, classMapping.getAttributesSize());
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//add @XmlAttribute annotation and test each access type
		JavaResourceMethod resourceMethod = classMapping.getJavaType().getJavaResourceType().getMethods().iterator().next();
		AnnotatedElement annotatedElement = this.annotatedElement(resourceMethod);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_ATTRIBUTE);
			}
		});
		//@XmlAttribute
		//public List<?> getFoo(); PROPERTY access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PROPERTY);
		attributes = classMapping.getAttributes().iterator();
		assertEquals(1, classMapping.getAttributesSize());
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//public List<?> getFoo(); FIELD access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//public List<?> getFoo(); NONE access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//public List<?> getFoo(); PUBLIC_MEMBER access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());
	
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.removeAnnotation(declaration, JAXB.XML_ATTRIBUTE);
			}
		});
	}

	protected void publicGetterSetterTest(JavaClassMapping classMapping) {
		//public int getFoo();, public void setFoo(int); PUBLIC_MEMBER access - persistent
		assertEquals(1, classMapping.getAttributesSize());
		Iterator<JaxbPersistentAttribute> attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//public int getFoo();, public void setFoo(int); PROPERTY access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//public int getFoo();, public void setFoo(int); FIELD access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(0, classMapping.getAttributesSize());

		//public int getFoo();, public void setFoo(int); NONE access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(0, classMapping.getAttributesSize());

		//public int getFoo();, public void setFoo(int); PUBLIC_MEMBER access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());


		//add @XmlAttribute annotation and test each access type
		JavaResourceMethod resourceMethod = (JavaResourceMethod) classMapping.getAttributes().iterator().next().getJavaResourceAttribute();
		AnnotatedElement annotatedElement = this.annotatedElement(resourceMethod);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_ATTRIBUTE);
			}
		});
		//@XmlAttribute
		//public int getFoo();, public void setFoo(int); PROPERTY access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//public int getFoo();, public void setFoo(int); FIELD access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//public int getFoo();, public void setFoo(int); NONE access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//public int getFoo();, public void setFoo(int); PUBLIC_MEMBER access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());
	
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.removeAnnotation(declaration, JAXB.XML_ATTRIBUTE);
			}
		});
	}

	protected void publicStaticGetterSetterTest(JavaClassMapping classMapping) {
		Iterator<JaxbPersistentAttribute> attributes = classMapping.getAttributes().iterator();
		PropertyAccessor propertyAccessor = (PropertyAccessor) ObjectTools.get(attributes.next(), "accessor");
		JavaResourceMethod resourceGetter = propertyAccessor.getResourceGetterMethod();
		JavaResourceMethod resourceSetter = propertyAccessor.getResourceSetterMethod();

		//public static int getFoo();, public static void setFoo(int); PUBLIC_MEMBER access - not persistent
		this.addModifiers(resourceGetter, ModifierKeyword.STATIC_KEYWORD);
		this.addModifiers(resourceSetter, ModifierKeyword.STATIC_KEYWORD);
		assertEquals(0, classMapping.getAttributesSize());


		//public static int getFoo();, public static void setFoo(int); PROPERTY access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(0, classMapping.getAttributesSize());

		//public static int getFoo();, public static void setFoo(int); FIELD access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(0, classMapping.getAttributesSize());

		//public static int getFoo();, public static void setFoo(int); NONE access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(0, classMapping.getAttributesSize());

		//public static int getFoo();, public static void setFoo(int); PUBLIC_MEMBER access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(0, classMapping.getAttributesSize());


		//add @XmlAttribute annotation and test each access type
		AnnotatedElement annotatedElement = this.annotatedElement(resourceGetter);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_ATTRIBUTE);
			}
		});
		//@XmlAttribute
		//public static int getFoo();, public static void setFoo(int); PROPERTY access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//public static int getFoo();, public static void setFoo(int); FIELD access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//public static int getFoo();, public static void setFoo(int); NONE access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//public static int getFoo();, public static void setFoo(int); PUBLIC_MEMBER access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());
	
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.removeAnnotation(declaration, JAXB.XML_ATTRIBUTE);
			}
		});
		this.removeModifiers(resourceGetter, ModifierKeyword.STATIC_KEYWORD);
		this.removeModifiers(resourceSetter, ModifierKeyword.STATIC_KEYWORD);
	}

	protected void privateGetterSetterTest(JavaClassMapping classMapping) {
		Iterator<JaxbPersistentAttribute> attributes = classMapping.getAttributes().iterator();
		PropertyAccessor propertyAccessor = (PropertyAccessor) ObjectTools.get(attributes.next(), "accessor");
		JavaResourceMethod resourceGetter = propertyAccessor.getResourceGetterMethod();
		JavaResourceMethod resourceSetter = propertyAccessor.getResourceSetterMethod();

		//private int getFoo();, private void setFoo(int); PUBLIC_MEMBER access - not persistent
		this.removeModifiers(resourceGetter, ModifierKeyword.PUBLIC_KEYWORD);
		this.removeModifiers(resourceSetter, ModifierKeyword.PUBLIC_KEYWORD);
		this.addModifiers(resourceGetter, ModifierKeyword.PRIVATE_KEYWORD);
		this.addModifiers(resourceSetter, ModifierKeyword.PRIVATE_KEYWORD);
		assertEquals(0, classMapping.getAttributesSize());


		//private int getFoo();, private void setFoo(int); PROPERTY access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//private int getFoo();, private void setFoo(int); FIELD access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(0, classMapping.getAttributesSize());

		//private int getFoo();, private void setFoo(int); NONE access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(0, classMapping.getAttributesSize());

		//private int getFoo();, private void setFoo(int); PUBLIC_MEMBER access - not persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(0, classMapping.getAttributesSize());


		//add @XmlAttribute annotation and test each access type
		AnnotatedElement annotatedElement = this.annotatedElement(resourceGetter);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_ATTRIBUTE);
			}
		});
		//@XmlAttribute
		//private int getFoo();, private void setFoo(int); PROPERTY access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PROPERTY);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//private int getFoo();, private void setFoo(int); FIELD access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__FIELD);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//private int getFoo();, private void setFoo(int); NONE access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__NONE);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());

		//@XmlAttribute
		//private int getFoo();, private void setFoo(int); PUBLIC_MEMBER access - persistent
		this.setAccessTypeInJavaSource(classMapping, JAXB.XML_ACCESS_TYPE__PUBLIC_MEMBER);
		assertEquals(1, classMapping.getAttributesSize());
		attributes = classMapping.getAttributes().iterator();
		assertEquals("foo", attributes.next().getName());
		assertFalse(attributes.hasNext());
	
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.removeAnnotation(declaration, JAXB.XML_ATTRIBUTE);
			}
		});
		this.removeModifiers(resourceGetter, ModifierKeyword.PRIVATE_KEYWORD);
		this.removeModifiers(resourceSetter, ModifierKeyword.PRIVATE_KEYWORD);
		this.addModifiers(resourceGetter, ModifierKeyword.PUBLIC_KEYWORD);
		this.addModifiers(resourceSetter, ModifierKeyword.PUBLIC_KEYWORD);
	}
	
	public void testModifyXmlSeeAlso() throws Exception {
		createClassWithXmlType();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JavaResourceType resourceType = jaxbClass.getJavaResourceType();
		
		XmlSeeAlsoAnnotation annotation = (XmlSeeAlsoAnnotation) resourceType.getAnnotation(JAXB.XML_SEE_ALSO);
		assertNull(classMapping.getXmlSeeAlso());
		assertNull(annotation);
		
		classMapping.addXmlSeeAlso();
		annotation = (XmlSeeAlsoAnnotation) resourceType.getAnnotation(JAXB.XML_SEE_ALSO);
		assertNotNull(classMapping.getXmlSeeAlso());
		assertNotNull(annotation);
		
		classMapping.removeXmlSeeAlso();
		annotation = (XmlSeeAlsoAnnotation) resourceType.getAnnotation(JAXB.XML_SEE_ALSO);
		assertNull(classMapping.getXmlSeeAlso());
		assertNull(annotation);
	}
	
	public void testUpdateXmlSeeAlso() throws Exception {
		createClassWithXmlType();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JavaResourceType resourceType = jaxbClass.getJavaResourceType();
		
		XmlSeeAlsoAnnotation annotation = (XmlSeeAlsoAnnotation) resourceType.getAnnotation(JAXB.XML_SEE_ALSO);
		assertNull(classMapping.getXmlSeeAlso());
		assertNull(annotation);
		
		AnnotatedElement annotatedElement = this.annotatedElement(resourceType);
		annotatedElement.edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						GenericJavaClassMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_SEE_ALSO);
					}
				});
		annotation = (XmlSeeAlsoAnnotation) resourceType.getAnnotation(JAXB.XML_SEE_ALSO);
		assertNotNull(classMapping.getXmlSeeAlso());
		assertNotNull(annotation);
		
		annotatedElement.edit(
				new Member.Editor() {
					public void edit(
						ModifiedDeclaration declaration) {
							GenericJavaClassMappingTests.this.removeAnnotation(declaration, JAXB.XML_SEE_ALSO);
						}
					});
		annotation = (XmlSeeAlsoAnnotation) resourceType.getAnnotation(JAXB.XML_SEE_ALSO);
		assertNull(classMapping.getXmlSeeAlso());
		assertNull(annotation);
	}
	
	protected void addModifiers(JavaResourceMember resourceMember, final ModifierKeyword... modifiers) {
		AnnotatedElement annotatedElement = this.annotatedElement(resourceMember);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.addModifiers((BodyDeclaration) declaration.getDeclaration(), modifiers);
			}
		});
	}

	protected void removeModifiers(JavaResourceMember resourceMember, final ModifierKeyword... modifiers) {
		AnnotatedElement annotatedElement = this.annotatedElement(resourceMember);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.removeModifiers((BodyDeclaration) declaration.getDeclaration(), modifiers);
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

	protected void setAccessTypeInJavaSource(JavaClassMapping classMapping, final String accessType) {
		JavaResourceType resourceClass = classMapping.getJavaType().getJavaResourceType();
		AnnotatedElement annotatedElement = this.annotatedElement(resourceClass);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaClassMappingTests.this.setEnumMemberValuePair(declaration, JAXB.XML_ACCESSOR_TYPE, accessType);
			}
		});
	}

}