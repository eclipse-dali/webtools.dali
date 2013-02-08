/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal.context.java;

import java.util.Iterator;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlElement;
import org.eclipse.jpt.jaxb.core.context.XmlElementMapping;
import org.eclipse.jpt.jaxb.core.context.java.JavaClass;
import org.eclipse.jpt.jaxb.core.context.java.JavaClassMapping;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAttachmentRefAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementWrapperAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlIDAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlIDREFAnnotation;
import org.eclipse.jpt.jaxb.core.tests.internal.context.JaxbContextModelTestCase;


@SuppressWarnings("nls")
public class GenericJavaXmlElementMappingTests extends JaxbContextModelTestCase
{

	public GenericJavaXmlElementMappingTests(String name) {
		super(name);
	}
	
	private ICompilationUnit createTypeWithXmlElement() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JAXB.XML_TYPE, JAXB.XML_ELEMENT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType");
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlElement");
			}
		});
	}
	
	private ICompilationUnit createTypeWithCollectionXmlElement() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("import java.util.List;").append(CR);
				sb.append("import javax.xml.bind.annotation.XmlElement;").append(CR);
				sb.append("import javax.xml.bind.annotation.XmlType;").append(CR);
				sb.append(CR);
				sb.append("@XmlType").append(CR);
				sb.append("public class ").append(TYPE_NAME).append(" {").append(CR);
				sb.append("    @XmlElement").append(CR);
				sb.append("    public List<String> list;").append(CR);
				sb.append("}").append(CR);
			}
		};
		return this.javaProject.createCompilationUnit(PACKAGE_NAME, TYPE_NAME + ".java", sourceWriter);
	}
	
	private void createXmlTypeWithVariousAttributes() throws CoreException {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("import ").append(JAXB.XML_TYPE).append(";").append(CR);
				sb.append("import java.util.List;").append(CR);
				sb.append(CR);
				sb.append("@XmlType").append(CR);
				sb.append("public class ").append(TYPE_NAME).append("{").append(CR);
				sb.append("    public String string;").append(CR);
				sb.append("    public List<String> stringList;").append(CR);
				sb.append("    public String[] stringArray;").append(CR);
				sb.append("}").append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, TYPE_NAME + ".java", sourceWriter);
	}
	
	public void testModifyName() throws Exception {
		createTypeWithXmlElement();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		XmlElement xmlElement = xmlElementMapping.getXmlElement();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getPersistentAttribute().getJavaResourceAttribute();
		
		assertNull(xmlElement.getQName().getSpecifiedName());
		assertEquals("id", xmlElement.getQName().getDefaultName());
		assertEquals("id", xmlElement.getQName().getName());

		xmlElement.getQName().setSpecifiedName("foo");
		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT);
		assertEquals("foo", xmlElementAnnotation.getName());
		assertEquals("foo", xmlElement.getQName().getSpecifiedName());
		assertEquals("id", xmlElement.getQName().getDefaultName());
		assertEquals("foo", xmlElement.getQName().getName());

		xmlElement.getQName().setSpecifiedName(null);
		xmlElementAnnotation = (XmlElementAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT);
		assertNull(xmlElementAnnotation.getName());
		assertNull(xmlElement.getQName().getSpecifiedName());
	}

	public void testUpdateName() throws Exception {
		createTypeWithXmlElement();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		XmlElement xmlElement = xmlElementMapping.getXmlElement();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getPersistentAttribute().getJavaResourceAttribute();
		
		assertNull(xmlElement.getQName().getSpecifiedName());


		//add a Name member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementMappingTests.this.addXmlElementMemberValuePair(declaration, JAXB.XML_ELEMENT__NAME, "foo");
			}
		});
		assertEquals("foo", xmlElement.getQName().getName());

		//remove the Name member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlElementAnnotation = (NormalAnnotation) GenericJavaXmlElementMappingTests.this.getXmlElementAnnotation(declaration);
				GenericJavaXmlElementMappingTests.this.values(xmlElementAnnotation).remove(0);
			}
		});
		assertNull(xmlElement.getQName().getSpecifiedName());
	}

	public void testModifyNamespace() throws Exception {
		createTypeWithXmlElement();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		XmlElement xmlElement = xmlElementMapping.getXmlElement();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getPersistentAttribute().getJavaResourceAttribute();

		assertNull(xmlElement.getQName().getSpecifiedNamespace());

		xmlElement.getQName().setSpecifiedNamespace("foo");
		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT);
		assertEquals("foo", xmlElementAnnotation.getNamespace());
		assertEquals("foo", xmlElement.getQName().getNamespace());

		xmlElement.getQName().setSpecifiedNamespace(null);
		xmlElementAnnotation = (XmlElementAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT);
		assertNull(xmlElementAnnotation.getNamespace());
		assertNull(xmlElement.getQName().getSpecifiedNamespace());
	}

	public void testUpdateNamespace() throws Exception {
		createTypeWithXmlElement();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		XmlElement xmlElement = xmlElementMapping.getXmlElement();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getPersistentAttribute().getJavaResourceAttribute();

		assertNull(xmlElement.getQName().getSpecifiedNamespace());


		//add a namespace member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementMappingTests.this.addXmlElementMemberValuePair(declaration, JAXB.XML_ELEMENT__NAMESPACE, "foo");
			}
		});
		assertEquals("foo", xmlElement.getQName().getNamespace());

		//remove the namespace member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlElementAnnotation = (NormalAnnotation) GenericJavaXmlElementMappingTests.this.getXmlElementAnnotation(declaration);
				GenericJavaXmlElementMappingTests.this.values(xmlElementAnnotation).remove(0);
			}
		});
		assertNull(xmlElement.getQName().getSpecifiedNamespace());
	}

	public void testModifyRequired() throws Exception {
		createTypeWithXmlElement();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		XmlElement xmlElement = xmlElementMapping.getXmlElement();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getPersistentAttribute().getJavaResourceAttribute();

		assertNull(xmlElement.getSpecifiedRequired());
		assertEquals(false, xmlElement.isDefaultRequired());
		assertEquals(false, xmlElement.isRequired());

		xmlElement.setSpecifiedRequired(Boolean.TRUE);
		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT);
		assertEquals(Boolean.TRUE, xmlElementAnnotation.getRequired());
		assertEquals(Boolean.TRUE, xmlElement.getSpecifiedRequired());
		assertEquals(false, xmlElement.isDefaultRequired());
		assertEquals(true, xmlElement.isRequired());

		xmlElement.setSpecifiedRequired(null);
		xmlElementAnnotation = (XmlElementAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT);
		assertNull(xmlElementAnnotation.getName());
		assertNull(xmlElement.getSpecifiedRequired());
		assertEquals(false, xmlElement.isDefaultRequired());
		assertEquals(false, xmlElement.isRequired());
	}

	public void testUpdateRequired() throws Exception {
		createTypeWithXmlElement();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		XmlElement xmlElement = xmlElementMapping.getXmlElement();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getPersistentAttribute().getJavaResourceAttribute();

		assertNull(xmlElement.getSpecifiedRequired());
		assertEquals(false, xmlElement.isDefaultRequired());
		assertEquals(false, xmlElement.isRequired());


		//add a required member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementMappingTests.this.addXmlElementMemberValuePair(declaration, JAXB.XML_ELEMENT__REQUIRED, true);
			}
		});
		assertEquals(Boolean.TRUE, xmlElement.getSpecifiedRequired());
		assertEquals(false, xmlElement.isDefaultRequired());
		assertEquals(true, xmlElement.isRequired());

		//remove the required member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlElementAnnotation = (NormalAnnotation) GenericJavaXmlElementMappingTests.this.getXmlElementAnnotation(declaration);
				GenericJavaXmlElementMappingTests.this.values(xmlElementAnnotation).remove(0);
			}
		});
		assertNull(xmlElement.getSpecifiedRequired());
		assertEquals(false, xmlElement.isDefaultRequired());
		assertEquals(false, xmlElement.isRequired());
	}

	public void testModifyNillable() throws Exception {
		createTypeWithXmlElement();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		XmlElement xmlElement = xmlElementMapping.getXmlElement();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getPersistentAttribute().getJavaResourceAttribute();

		assertNull(xmlElement.getSpecifiedNillable());
		assertEquals(false, xmlElement.isDefaultNillable());
		assertEquals(false, xmlElement.isNillable());

		xmlElement.setSpecifiedNillable(Boolean.TRUE);
		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT);
		assertEquals(Boolean.TRUE, xmlElementAnnotation.getNillable());
		assertEquals(Boolean.TRUE, xmlElement.getSpecifiedNillable());
		assertEquals(false, xmlElement.isDefaultNillable());
		assertEquals(true, xmlElement.isNillable());

		xmlElement.setSpecifiedNillable(null);
		xmlElementAnnotation = (XmlElementAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT);
		assertNull(xmlElementAnnotation.getName());
		assertNull(xmlElement.getSpecifiedNillable());
		assertEquals(false, xmlElement.isDefaultNillable());
		assertEquals(false, xmlElement.isNillable());
	}

	public void testUpdateNillable() throws Exception {
		createTypeWithXmlElement();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		XmlElement xmlElement = xmlElementMapping.getXmlElement();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getPersistentAttribute().getJavaResourceAttribute();

		assertNull(xmlElement.getSpecifiedNillable());
		assertEquals(false, xmlElement.isDefaultNillable());
		assertEquals(false, xmlElement.isNillable());


		//add a nillable member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementMappingTests.this.addXmlElementMemberValuePair(declaration, JAXB.XML_ELEMENT__NILLABLE, true);
			}
		});
		assertEquals(Boolean.TRUE, xmlElement.getSpecifiedNillable());
		assertEquals(false, xmlElement.isDefaultNillable());
		assertEquals(true, xmlElement.isNillable());

		//remove the nillable member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlElementAnnotation = (NormalAnnotation) GenericJavaXmlElementMappingTests.this.getXmlElementAnnotation(declaration);
				GenericJavaXmlElementMappingTests.this.values(xmlElementAnnotation).remove(0);
			}
		});
		assertNull(xmlElement.getSpecifiedNillable());
		assertEquals(false, xmlElement.isDefaultNillable());
		assertEquals(false, xmlElement.isNillable());
	}
	
	public void testDefaultNillable() throws Exception {
		createXmlTypeWithVariousAttributes();
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		
		// string
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		XmlElement xmlElement = xmlElementMapping.getXmlElement();
		JavaResourceAttribute resourceAttribute = persistentAttribute.getJavaResourceAttribute();
		
		assertEquals(false, xmlElement.isDefaultNillable());
		
		annotatedElement(resourceAttribute).edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						GenericJavaXmlElementMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_ELEMENT);
					}
				});
		
		assertEquals(false, xmlElement.isDefaultNillable());
		
		// string list
		persistentAttribute = IterableTools.get(classMapping.getAttributes(), 1);
		xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		xmlElement = xmlElementMapping.getXmlElement();
		resourceAttribute = persistentAttribute.getJavaResourceAttribute();
		
		assertEquals(true, xmlElement.isDefaultNillable());
		
		annotatedElement(resourceAttribute).edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						GenericJavaXmlElementMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_ELEMENT);
					}
				});
		
		assertEquals(false, xmlElement.isDefaultNillable());
		
		// string array
		persistentAttribute = IterableTools.get(classMapping.getAttributes(), 2);
		xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		xmlElement = xmlElementMapping.getXmlElement();
		resourceAttribute = persistentAttribute.getJavaResourceAttribute();
		
		assertEquals(true, xmlElement.isDefaultNillable());
		
		annotatedElement(resourceAttribute).edit(
				new Member.Editor() {
					public void edit(ModifiedDeclaration declaration) {
						GenericJavaXmlElementMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_ELEMENT);
					}
				});
		
		assertEquals(false, xmlElement.isDefaultNillable());
	}

	public void testModifyDefaultValue() throws Exception {
		createTypeWithXmlElement();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		XmlElement xmlElement = xmlElementMapping.getXmlElement();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getPersistentAttribute().getJavaResourceAttribute();

		assertNull(xmlElement.getDefaultValue());

		xmlElement.setDefaultValue("foo");
		XmlElementAnnotation xmlElementAnnotation = (XmlElementAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT);
		assertEquals("foo", xmlElementAnnotation.getDefaultValue());
		assertEquals("foo", xmlElement.getDefaultValue());

		xmlElement.setDefaultValue(null);
		xmlElementAnnotation = (XmlElementAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT);
		assertNull(xmlElementAnnotation.getDefaultValue());
		assertNull(xmlElement.getDefaultValue());
	}

	public void testUpdateDefaultValue() throws Exception {
		createTypeWithXmlElement();

		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		XmlElement xmlElement = xmlElementMapping.getXmlElement();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getPersistentAttribute().getJavaResourceAttribute();

		assertNull(xmlElement.getDefaultValue());


		//add a DefaultValue member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementMappingTests.this.addXmlElementMemberValuePair(declaration, JAXB.XML_ELEMENT__DEFAULT_VALUE, "foo");
			}
		});
		assertEquals("foo", xmlElement.getDefaultValue());

		//remove the DefaultValue member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlElementAnnotation = (NormalAnnotation) GenericJavaXmlElementMappingTests.this.getXmlElementAnnotation(declaration);
				GenericJavaXmlElementMappingTests.this.values(xmlElementAnnotation).remove(0);
			}
		});
		assertNull(xmlElement.getDefaultValue());
	}

	public void testModifyType() throws Exception {
		createTypeWithXmlElement();
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		XmlElement xmlElement = xmlElementMapping.getXmlElement();
		
		assertNull(xmlElement.getSpecifiedType());
		assertEquals("int", xmlElement.getType());
		assertEquals("int", xmlElement.getDefaultType());
		
		xmlElement.setSpecifiedType("Foo");
		assertEquals("Foo", xmlElement.getSpecifiedType());
		assertEquals("Foo", xmlElement.getType());
		
		xmlElement.setSpecifiedType(null);
		assertNull(xmlElement.getSpecifiedType());
		assertEquals("int", xmlElement.getType());
	}
	
	public void testUpdateType() throws Exception {
		createTypeWithXmlElement();
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		XmlElement xmlElement = xmlElementMapping.getXmlElement();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getPersistentAttribute().getJavaResourceAttribute();
		
		assertNull(xmlElement.getSpecifiedType());
		assertEquals("int", xmlElement.getDefaultType());
		assertEquals("int", xmlElement.getType());
		
		//add a Type member value pair
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementMappingTests.this.addXmlElementTypeMemberValuePair(declaration, JAXB.XML_ELEMENT__TYPE, "Foo");
			}
		});
		assertEquals("Foo", xmlElement.getSpecifiedType());
		assertEquals("Foo", xmlElement.getType());
		
		//remove the Type member value pair
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				NormalAnnotation xmlElementAnnotation = (NormalAnnotation) GenericJavaXmlElementMappingTests.this.getXmlElementAnnotation(declaration);
				GenericJavaXmlElementMappingTests.this.values(xmlElementAnnotation).remove(0);
			}
		});
		assertNull(xmlElement.getSpecifiedType());
		assertEquals("int", xmlElement.getType());
	}

	public void testChangeMappingType() throws Exception {
		createTypeWithXmlElement();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getPersistentAttribute().getJavaResourceAttribute();

		assertNotNull(xmlElementMapping);
		assertNotNull(resourceAttribute.getAnnotation(JAXB.XML_ELEMENT));

		persistentAttribute.setMappingKey(MappingKeys.XML_ATTRIBUTE_ATTRIBUTE_MAPPING_KEY);
		XmlAttributeMapping xmlAttributeMapping = (XmlAttributeMapping) persistentAttribute.getMapping();
		assertNotNull(xmlAttributeMapping);
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_ELEMENT));
		assertNotNull(resourceAttribute.getAnnotation(JAXB.XML_ATTRIBUTE));


		persistentAttribute.setMappingKey(MappingKeys.XML_ELEMENT_ATTRIBUTE_MAPPING_KEY);
		xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		assertNotNull(xmlElementMapping);
		assertNotNull(resourceAttribute.getAnnotation(JAXB.XML_ELEMENT));
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_ATTRIBUTE));
	}

	public void testModifyXmlJavaTypeAdapter() throws Exception {
		createTypeWithXmlElement();

		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getPersistentAttribute().getJavaResourceAttribute();
		
		assertNull(xmlElementMapping.getXmlJavaTypeAdapter());
		assertEquals(0, resourceAttribute.getAnnotationsSize(JAXB.XML_JAVA_TYPE_ADAPTER));
		
		xmlElementMapping.addXmlJavaTypeAdapter();
		
		assertNotNull(xmlElementMapping.getXmlJavaTypeAdapter());
		assertNotNull(resourceAttribute.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER));
		
		xmlElementMapping.removeXmlJavaTypeAdapter();
		
		assertNull(xmlElementMapping.getXmlJavaTypeAdapter());
		assertEquals(0, resourceAttribute.getAnnotationsSize(JAXB.XML_JAVA_TYPE_ADAPTER));
	}

	public void testUpdateXmlJavaTypeAdapter() throws Exception {
		createTypeWithXmlElement();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getPersistentAttribute().getJavaResourceAttribute();

		assertNull(xmlElementMapping.getXmlJavaTypeAdapter());
		assertEquals(0, resourceAttribute.getAnnotationsSize(JAXB.XML_JAVA_TYPE_ADAPTER));
		
		//add an XmlJavaTypeAdapter annotation
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_JAVA_TYPE_ADAPTER);
			}
		});
		
		assertNotNull(xmlElementMapping.getXmlJavaTypeAdapter());
		assertNotNull(resourceAttribute.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER));
		
		//remove the XmlJavaTypeAdapter annotation
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementMappingTests.this.removeAnnotation(declaration, JAXB.XML_JAVA_TYPE_ADAPTER);
			}
		});
		
		assertNull(xmlElementMapping.getXmlJavaTypeAdapter());
		assertEquals(0, resourceAttribute.getAnnotationsSize(JAXB.XML_JAVA_TYPE_ADAPTER));
	}

	public void testModifyXmlSchemaType() throws Exception {
		createTypeWithXmlElement();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getPersistentAttribute().getJavaResourceAttribute();
		
		assertNull(xmlElementMapping.getXmlSchemaType());
		assertEquals(0, resourceAttribute.getAnnotationsSize(JAXB.XML_SCHEMA_TYPE));
		
		xmlElementMapping.addXmlSchemaType();
		
		assertNotNull(xmlElementMapping.getXmlSchemaType());
		assertNotNull(resourceAttribute.getAnnotation(0, JAXB.XML_SCHEMA_TYPE));
		
		xmlElementMapping.removeXmlSchemaType();
		
		assertNull(xmlElementMapping.getXmlSchemaType());
		assertEquals(0, resourceAttribute.getAnnotationsSize(JAXB.XML_SCHEMA_TYPE));
	}

	public void testUpdateXmlSchemaType() throws Exception {
		createTypeWithXmlElement();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getPersistentAttribute().getJavaResourceAttribute();
		
		
		assertNull(xmlElementMapping.getXmlSchemaType());
		assertEquals(0, resourceAttribute.getAnnotationsSize(JAXB.XML_SCHEMA_TYPE));
		
		//add an XmlSchemaType annotation
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_SCHEMA_TYPE);
			}
		});
		
		assertNotNull(xmlElementMapping.getXmlSchemaType());
		assertNotNull(resourceAttribute.getAnnotation(0, JAXB.XML_SCHEMA_TYPE));
		
		//remove the XmlSchemaType annotation
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementMappingTests.this.removeAnnotation(declaration, JAXB.XML_SCHEMA_TYPE);
			}
		});
		
		assertNull(xmlElementMapping.getXmlSchemaType());
		assertEquals(0, resourceAttribute.getAnnotationsSize(JAXB.XML_SCHEMA_TYPE));
	}

	protected void addXmlElementMemberValuePair(ModifiedDeclaration declaration, String name, String value) {
		this.addMemberValuePair((MarkerAnnotation) this.getXmlElementAnnotation(declaration), name, value);
	}

	protected void addXmlElementMemberValuePair(ModifiedDeclaration declaration, String name, boolean value) {
		this.addMemberValuePair((MarkerAnnotation) this.getXmlElementAnnotation(declaration), name, value);
	}

	protected void addXmlElementTypeMemberValuePair(ModifiedDeclaration declaration, String name, String typeName) {
		this.addMemberValuePair(
			(MarkerAnnotation) this.getXmlElementAnnotation(declaration), 
			name, 
			this.newTypeLiteral(declaration.getAst(), typeName));
	}

	protected Annotation getXmlElementAnnotation(ModifiedDeclaration declaration) {
		return declaration.getAnnotationNamed(JAXB.XML_ELEMENT);
	}


	public void testModifyXmlElementWrapper() throws Exception {
		createTypeWithXmlElement();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getPersistentAttribute().getJavaResourceAttribute();

		XmlElementWrapperAnnotation xmlElementWrapperAnnotation = (XmlElementWrapperAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_WRAPPER);
		assertNull(xmlElementMapping.getXmlElementWrapper());
		assertNull(xmlElementWrapperAnnotation);

		xmlElementMapping.addXmlElementWrapper();
		xmlElementWrapperAnnotation = (XmlElementWrapperAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_WRAPPER);
		assertNotNull(xmlElementMapping.getXmlElementWrapper());
		assertNotNull(xmlElementWrapperAnnotation);

		xmlElementMapping.removeXmlElementWrapper();
		xmlElementWrapperAnnotation = (XmlElementWrapperAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_WRAPPER);
	}

	public void testUpdateXmlElementWrapper() throws Exception {
		createTypeWithXmlElement();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getPersistentAttribute().getJavaResourceAttribute();

		XmlElementWrapperAnnotation xmlElementWrapperAnnotation = (XmlElementWrapperAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_WRAPPER);
		assertNull(xmlElementMapping.getXmlElementWrapper());
		assertNull(xmlElementWrapperAnnotation);


		//add an XmlElementWrapper annotation
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_ELEMENT_WRAPPER);
			}
		});
		xmlElementWrapperAnnotation = (XmlElementWrapperAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_WRAPPER);
		assertNotNull(xmlElementMapping.getXmlElementWrapper());
		assertNotNull(xmlElementWrapperAnnotation);

		//remove the XmlElementWrapper annotation
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementMappingTests.this.removeAnnotation(declaration, JAXB.XML_ELEMENT_WRAPPER);
			}
		});
		xmlElementWrapperAnnotation = (XmlElementWrapperAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ELEMENT_WRAPPER);
		assertNull(xmlElementMapping.getXmlElementWrapper());
		assertNull(xmlElementWrapperAnnotation);
	}

	public void testModifyXmlList1() throws Exception {
		createTypeWithXmlElement();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getPersistentAttribute().getJavaResourceAttribute();
		
		assertFalse(xmlElementMapping.isXmlList());
		assertFalse(xmlElementMapping.isDefaultXmlList());
		assertFalse(xmlElementMapping.isSpecifiedXmlList());
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_LIST));
		
		xmlElementMapping.setSpecifiedXmlList(true);
		
		assertTrue(xmlElementMapping.isXmlList());
		assertFalse(xmlElementMapping.isDefaultXmlList());
		assertTrue(xmlElementMapping.isSpecifiedXmlList());
		assertNotNull(resourceAttribute.getAnnotation(JAXB.XML_LIST));
		
		xmlElementMapping.setSpecifiedXmlList(false);
		
		assertFalse(xmlElementMapping.isXmlList());
		assertFalse(xmlElementMapping.isDefaultXmlList());
		assertFalse(xmlElementMapping.isSpecifiedXmlList());
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_LIST));
	}
	
	public void testModifyXmlList2() throws Exception {
		createTypeWithCollectionXmlElement();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getPersistentAttribute().getJavaResourceAttribute();
		
		assertFalse(xmlElementMapping.isXmlList());
		assertFalse(xmlElementMapping.isDefaultXmlList());
		assertFalse(xmlElementMapping.isSpecifiedXmlList());
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_LIST));
		
		xmlElementMapping.setSpecifiedXmlList(true);
		
		assertTrue(xmlElementMapping.isXmlList());
		assertFalse(xmlElementMapping.isDefaultXmlList());
		assertTrue(xmlElementMapping.isSpecifiedXmlList());
		assertNotNull(resourceAttribute.getAnnotation(JAXB.XML_LIST));
		
		xmlElementMapping.setSpecifiedXmlList(false);
		
		assertFalse(xmlElementMapping.isXmlList());
		assertFalse(xmlElementMapping.isDefaultXmlList());
		assertFalse(xmlElementMapping.isSpecifiedXmlList());
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_LIST));
	}
	
	public void testUpdateXmlList1() throws Exception {
		createTypeWithXmlElement();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getPersistentAttribute().getJavaResourceAttribute();
		
		assertFalse(xmlElementMapping.isXmlList());
		assertFalse(xmlElementMapping.isDefaultXmlList());
		assertFalse(xmlElementMapping.isSpecifiedXmlList());
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_LIST));
		
		//add an XmlList annotation
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_LIST);
			}
		});
		
		assertTrue(xmlElementMapping.isXmlList());
		assertFalse(xmlElementMapping.isDefaultXmlList());
		assertTrue(xmlElementMapping.isSpecifiedXmlList());
		assertNotNull(resourceAttribute.getAnnotation(JAXB.XML_LIST));
		
		//remove the XmlList annotation
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementMappingTests.this.removeAnnotation(declaration, JAXB.XML_LIST);
			}
		});
		
		assertFalse(xmlElementMapping.isXmlList());
		assertFalse(xmlElementMapping.isDefaultXmlList());
		assertFalse(xmlElementMapping.isSpecifiedXmlList());
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_LIST));
	}
	
	public void testUpdateXmlList2() throws Exception {
		createTypeWithCollectionXmlElement();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getPersistentAttribute().getJavaResourceAttribute();
		
		assertFalse(xmlElementMapping.isXmlList());
		assertFalse(xmlElementMapping.isDefaultXmlList());
		assertFalse(xmlElementMapping.isSpecifiedXmlList());
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_LIST));
		
		//add an XmlList annotation
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_LIST);
			}
		});
		
		assertTrue(xmlElementMapping.isXmlList());
		assertFalse(xmlElementMapping.isDefaultXmlList());
		assertTrue(xmlElementMapping.isSpecifiedXmlList());
		assertNotNull(resourceAttribute.getAnnotation(JAXB.XML_LIST));
		
		//remove the XmlList annotation
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementMappingTests.this.removeAnnotation(declaration, JAXB.XML_LIST);
			}
		});
		
		assertFalse(xmlElementMapping.isXmlList());
		assertFalse(xmlElementMapping.isDefaultXmlList());
		assertFalse(xmlElementMapping.isSpecifiedXmlList());
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_LIST));
	}

	public void testModifyXmlID() throws Exception {
		createTypeWithXmlElement();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getPersistentAttribute().getJavaResourceAttribute();

		XmlIDAnnotation xmlIDAnnotation = (XmlIDAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ID);
		assertNull(xmlElementMapping.getXmlID());
		assertNull(xmlIDAnnotation);

		xmlElementMapping.addXmlID();
		xmlIDAnnotation = (XmlIDAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ID);
		assertNotNull(xmlElementMapping.getXmlID());
		assertNotNull(xmlIDAnnotation);

		xmlElementMapping.removeXmlID();
		xmlIDAnnotation = (XmlIDAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ID);
	}

	public void testUpdateXmlID() throws Exception {
		createTypeWithXmlElement();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getPersistentAttribute().getJavaResourceAttribute();

		XmlIDAnnotation xmlIDAnnotation = (XmlIDAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ID);
		assertNull(xmlElementMapping.getXmlID());
		assertNull(xmlIDAnnotation);


		//add an XmlID annotation
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_ID);
			}
		});
		xmlIDAnnotation = (XmlIDAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ID);
		assertNotNull(xmlElementMapping.getXmlID());
		assertNotNull(xmlIDAnnotation);

		//remove the XmlID annotation
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementMappingTests.this.removeAnnotation(declaration, JAXB.XML_ID);
			}
		});
		xmlIDAnnotation = (XmlIDAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ID);
		assertNull(xmlElementMapping.getXmlID());
		assertNull(xmlIDAnnotation);
	}

	public void testModifyXmlIDREF() throws Exception {
		createTypeWithXmlElement();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getPersistentAttribute().getJavaResourceAttribute();

		XmlIDREFAnnotation xmlIDREFAnnotation = (XmlIDREFAnnotation) resourceAttribute.getAnnotation(JAXB.XML_IDREF);
		assertNull(xmlElementMapping.getXmlIDREF());
		assertNull(xmlIDREFAnnotation);

		xmlElementMapping.addXmlIDREF();
		xmlIDREFAnnotation = (XmlIDREFAnnotation) resourceAttribute.getAnnotation(JAXB.XML_IDREF);
		assertNotNull(xmlElementMapping.getXmlIDREF());
		assertNotNull(xmlIDREFAnnotation);

		xmlElementMapping.removeXmlIDREF();
		xmlIDREFAnnotation = (XmlIDREFAnnotation) resourceAttribute.getAnnotation(JAXB.XML_IDREF);
		assertNull(xmlElementMapping.getXmlIDREF());
		assertNull(xmlIDREFAnnotation);
	}

	public void testUpdateXmlIDREF() throws Exception {
		createTypeWithXmlElement();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getPersistentAttribute().getJavaResourceAttribute();

		XmlIDREFAnnotation xmlIDREFAnnotation = (XmlIDREFAnnotation) resourceAttribute.getAnnotation(JAXB.XML_IDREF);
		assertNull(xmlElementMapping.getXmlIDREF());
		assertNull(xmlIDREFAnnotation);


		//add an XmlIDREF annotation
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_IDREF);
			}
		});
		xmlIDREFAnnotation = (XmlIDREFAnnotation) resourceAttribute.getAnnotation(JAXB.XML_IDREF);
		assertNotNull(xmlElementMapping.getXmlIDREF());
		assertNotNull(xmlIDREFAnnotation);

		//remove the XmlIDREF annotation
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementMappingTests.this.removeAnnotation(declaration, JAXB.XML_IDREF);
			}
		});
		xmlIDREFAnnotation = (XmlIDREFAnnotation) resourceAttribute.getAnnotation(JAXB.XML_IDREF);
		assertNull(xmlElementMapping.getXmlIDREF());
		assertNull(xmlIDREFAnnotation);
	}

	public void testModifyXmlAttachmentRef() throws Exception {
		createTypeWithXmlElement();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getPersistentAttribute().getJavaResourceAttribute();

		XmlAttachmentRefAnnotation xmlAttachmentRefAnnotation = (XmlAttachmentRefAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ATTACHMENT_REF);
		assertNull(xmlElementMapping.getXmlAttachmentRef());
		assertNull(xmlAttachmentRefAnnotation);

		xmlElementMapping.addXmlAttachmentRef();
		xmlAttachmentRefAnnotation = (XmlAttachmentRefAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ATTACHMENT_REF);
		assertNotNull(xmlElementMapping.getXmlAttachmentRef());
		assertNotNull(xmlAttachmentRefAnnotation);

		xmlElementMapping.removeXmlAttachmentRef();
		xmlAttachmentRefAnnotation = (XmlAttachmentRefAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ATTACHMENT_REF);
	}

	public void testUpdateXmlAttachmentRef() throws Exception {
		createTypeWithXmlElement();
		
		JavaClass jaxbClass = (JavaClass) IterableTools.get(getContextRoot().getJavaTypes(), 0);
		JavaClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = IterableTools.get(classMapping.getAttributes(), 0);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlElementMapping.getPersistentAttribute().getJavaResourceAttribute();

		XmlAttachmentRefAnnotation xmlAttachmentRefAnnotation = (XmlAttachmentRefAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ATTACHMENT_REF);
		assertNull(xmlElementMapping.getXmlAttachmentRef());
		assertNull(xmlAttachmentRefAnnotation);


		//add an XmlAttachmentRef annotation
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_ATTACHMENT_REF);
			}
		});
		xmlAttachmentRefAnnotation = (XmlAttachmentRefAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ATTACHMENT_REF);
		assertNotNull(xmlElementMapping.getXmlAttachmentRef());
		assertNotNull(xmlAttachmentRefAnnotation);

		//remove the XmlAttachmentRef annotation
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlElementMappingTests.this.removeAnnotation(declaration, JAXB.XML_ATTACHMENT_REF);
			}
		});
		xmlAttachmentRefAnnotation = (XmlAttachmentRefAnnotation) resourceAttribute.getAnnotation(JAXB.XML_ATTACHMENT_REF);
		assertNull(xmlElementMapping.getXmlAttachmentRef());
		assertNull(xmlAttachmentRefAnnotation);
	}

}