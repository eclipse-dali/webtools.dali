/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbClass;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlElementMapping;
import org.eclipse.jpt.jaxb.core.context.XmlValueMapping;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;
import org.eclipse.jpt.jaxb.core.tests.internal.context.JaxbContextModelTestCase;


@SuppressWarnings("nls")
public class GenericJavaXmlValueMappingTests extends JaxbContextModelTestCase
{

	public GenericJavaXmlValueMappingTests(String name) {
		super(name);
	}

	private ICompilationUnit createTypeWithXmlValue() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TYPE, JAXB.XML_VALUE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType");
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlValue");
			}
		});
	}
	
	private ICompilationUnit createTypeWithCollectionXmlValue() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("import java.util.List;").append(CR);
				sb.append("import javax.xml.bind.annotation.XmlValue;").append(CR);
				sb.append("import javax.xml.bind.annotation.XmlType;").append(CR);
				sb.append(CR);
				sb.append("@XmlType").append(CR);
				sb.append("public class ").append(TYPE_NAME).append(" {").append(CR);
				sb.append("    @XmlValue").append(CR);
				sb.append("    public List<String> list;").append(CR);
				sb.append("}").append(CR);
			}
		};
		return this.javaProject.createCompilationUnit(PACKAGE_NAME, TYPE_NAME + ".java", sourceWriter);
	}

	public void testChangeMappingType() throws Exception {
		createTypeWithXmlValue();

		JaxbClassMapping classMapping = ((JaxbClass) CollectionTools.get(getContextRoot().getTypes(), 0)).getMapping();
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(classMapping.getAttributes(), 0);
		XmlValueMapping xmlValueMapping = (XmlValueMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlValueMapping.getPersistentAttribute().getJavaResourceAttribute();

		assertNotNull(xmlValueMapping);
		assertNotNull(resourceAttribute.getAnnotation(JAXB.XML_VALUE));

		persistentAttribute.setMappingKey(MappingKeys.XML_ATTRIBUTE_ATTRIBUTE_MAPPING_KEY);
		XmlAttributeMapping xmlAttributeMapping = (XmlAttributeMapping) persistentAttribute.getMapping();
		assertNotNull(xmlAttributeMapping);
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_VALUE));
		assertNotNull(resourceAttribute.getAnnotation(JAXB.XML_ATTRIBUTE));

		persistentAttribute.setMappingKey(MappingKeys.XML_VALUE_ATTRIBUTE_MAPPING_KEY);
		xmlValueMapping = (XmlValueMapping) persistentAttribute.getMapping();
		assertNotNull(xmlValueMapping);
		assertNotNull(resourceAttribute.getAnnotation(JAXB.XML_VALUE));
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_ATTRIBUTE));


		persistentAttribute.setMappingKey(MappingKeys.XML_ELEMENT_ATTRIBUTE_MAPPING_KEY);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		assertNotNull(xmlElementMapping);
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_VALUE));
		assertNotNull(resourceAttribute.getAnnotation(JAXB.XML_ELEMENT));

		persistentAttribute.setMappingKey(MappingKeys.XML_VALUE_ATTRIBUTE_MAPPING_KEY);
		xmlValueMapping = (XmlValueMapping) persistentAttribute.getMapping();
		assertNotNull(xmlValueMapping);
		assertNotNull(resourceAttribute.getAnnotation(JAXB.XML_VALUE));
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_ELEMENT));
	}

	public void testModifyXmlJavaTypeAdapter() throws Exception {
		createTypeWithXmlValue();

		JaxbClassMapping classMapping = ((JaxbClass) CollectionTools.get(getContextRoot().getTypes(), 0)).getMapping();
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(classMapping.getAttributes(), 0);
		XmlValueMapping xmlValueMapping = (XmlValueMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlValueMapping.getPersistentAttribute().getJavaResourceAttribute();

		XmlJavaTypeAdapterAnnotation xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceAttribute.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
		assertNull(xmlValueMapping.getXmlJavaTypeAdapter());
		assertNull(xmlJavaTypeAdapterAnnotation);

		xmlValueMapping.addXmlJavaTypeAdapter();
		xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceAttribute.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
		assertNotNull(xmlValueMapping.getXmlJavaTypeAdapter());
		assertNotNull(xmlJavaTypeAdapterAnnotation);

		xmlValueMapping.removeXmlJavaTypeAdapter();
		xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceAttribute.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
	}

	public void testUpdateXmlJavaTypeAdapter() throws Exception {
		createTypeWithXmlValue();

		JaxbClassMapping classMapping = ((JaxbClass) CollectionTools.get(getContextRoot().getTypes(), 0)).getMapping();
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(classMapping.getAttributes(), 0);
		XmlValueMapping xmlValueMapping = (XmlValueMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlValueMapping.getPersistentAttribute().getJavaResourceAttribute();

		XmlJavaTypeAdapterAnnotation xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceAttribute.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
		assertNull(xmlValueMapping.getXmlJavaTypeAdapter());
		assertNull(xmlJavaTypeAdapterAnnotation);


		//add an XmlJavaTypeAdapter annotation
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlValueMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_JAVA_TYPE_ADAPTER);
			}
		});
		xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceAttribute.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
		assertNotNull(xmlValueMapping.getXmlJavaTypeAdapter());
		assertNotNull(xmlJavaTypeAdapterAnnotation);

		//remove the XmlJavaTypeAdapter annotation
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlValueMappingTests.this.removeAnnotation(declaration, JAXB.XML_JAVA_TYPE_ADAPTER);
			}
		});
		xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceAttribute.getAnnotation(0, JAXB.XML_JAVA_TYPE_ADAPTER);
		assertNull(xmlValueMapping.getXmlJavaTypeAdapter());
		assertNull(xmlJavaTypeAdapterAnnotation);
	}
	
	public void testModifyXmlList1() throws Exception {
		createTypeWithXmlValue();
		
		JaxbClass jaxbClass = (JaxbClass) CollectionTools.get(getContextRoot().getTypes(), 0);
		JaxbClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(classMapping.getAttributes(), 0);
		XmlValueMapping xmlMapping = (XmlValueMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlMapping.getPersistentAttribute().getJavaResourceAttribute();
		
		assertFalse(xmlMapping.isXmlList());
		assertFalse(xmlMapping.isDefaultXmlList());
		assertFalse(xmlMapping.isSpecifiedXmlList());
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_LIST));
		
		xmlMapping.setSpecifiedXmlList(true);
		
		assertTrue(xmlMapping.isXmlList());
		assertFalse(xmlMapping.isDefaultXmlList());
		assertTrue(xmlMapping.isSpecifiedXmlList());
		assertNotNull(resourceAttribute.getAnnotation(JAXB.XML_LIST));
		
		xmlMapping.setSpecifiedXmlList(false);
		
		assertFalse(xmlMapping.isXmlList());
		assertFalse(xmlMapping.isDefaultXmlList());
		assertFalse(xmlMapping.isSpecifiedXmlList());
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_LIST));
	}
	
	public void testModifyXmlList2() throws Exception {
		createTypeWithCollectionXmlValue();
		
		JaxbClass jaxbClass = (JaxbClass) CollectionTools.get(getContextRoot().getTypes(), 0);
		JaxbClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(classMapping.getAttributes(), 0);
		XmlValueMapping xmlMapping = (XmlValueMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlMapping.getPersistentAttribute().getJavaResourceAttribute();
		
		assertTrue(xmlMapping.isXmlList());
		assertTrue(xmlMapping.isDefaultXmlList());
		assertFalse(xmlMapping.isSpecifiedXmlList());
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_LIST));
		
		xmlMapping.setSpecifiedXmlList(true);
		
		assertTrue(xmlMapping.isXmlList());
		assertTrue(xmlMapping.isDefaultXmlList());
		assertTrue(xmlMapping.isSpecifiedXmlList());
		assertNotNull(resourceAttribute.getAnnotation(JAXB.XML_LIST));
		
		xmlMapping.setSpecifiedXmlList(false);
		
		assertTrue(xmlMapping.isXmlList());
		assertTrue(xmlMapping.isDefaultXmlList());
		assertFalse(xmlMapping.isSpecifiedXmlList());
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_LIST));
	}
	
	public void testUpdateXmlList1() throws Exception {
		createTypeWithXmlValue();
		
		JaxbClass jaxbClass = (JaxbClass) CollectionTools.get(getContextRoot().getTypes(), 0);
		JaxbClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(classMapping.getAttributes(), 0);
		XmlValueMapping xmlMapping = (XmlValueMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlMapping.getPersistentAttribute().getJavaResourceAttribute();
		
		assertFalse(xmlMapping.isXmlList());
		assertFalse(xmlMapping.isDefaultXmlList());
		assertFalse(xmlMapping.isSpecifiedXmlList());
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_LIST));
		
		//add an XmlList annotation
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlValueMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_LIST);
			}
		});
		
		assertTrue(xmlMapping.isXmlList());
		assertFalse(xmlMapping.isDefaultXmlList());
		assertTrue(xmlMapping.isSpecifiedXmlList());
		assertNotNull(resourceAttribute.getAnnotation(JAXB.XML_LIST));
		
		//remove the XmlList annotation
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlValueMappingTests.this.removeAnnotation(declaration, JAXB.XML_LIST);
			}
		});
		
		assertFalse(xmlMapping.isXmlList());
		assertFalse(xmlMapping.isDefaultXmlList());
		assertFalse(xmlMapping.isSpecifiedXmlList());
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_LIST));
	}
	
	public void testUpdateXmlList2() throws Exception {
		createTypeWithCollectionXmlValue();
		
		JaxbClass jaxbClass = (JaxbClass) CollectionTools.get(getContextRoot().getTypes(), 0);
		JaxbClassMapping classMapping = jaxbClass.getMapping();
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(classMapping.getAttributes(), 0);
		XmlValueMapping xmlMapping = (XmlValueMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlMapping.getPersistentAttribute().getJavaResourceAttribute();
		
		assertTrue(xmlMapping.isXmlList());
		assertTrue(xmlMapping.isDefaultXmlList());
		assertFalse(xmlMapping.isSpecifiedXmlList());
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_LIST));
		
		//add an XmlList annotation
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlValueMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), JAXB.XML_LIST);
			}
		});
		
		assertTrue(xmlMapping.isXmlList());
		assertTrue(xmlMapping.isDefaultXmlList());
		assertTrue(xmlMapping.isSpecifiedXmlList());
		assertNotNull(resourceAttribute.getAnnotation(JAXB.XML_LIST));
		
		//remove the XmlList annotation
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlValueMappingTests.this.removeAnnotation(declaration, JAXB.XML_LIST);
			}
		});
		
		assertTrue(xmlMapping.isXmlList());
		assertTrue(xmlMapping.isDefaultXmlList());
		assertFalse(xmlMapping.isSpecifiedXmlList());
		assertNull(resourceAttribute.getAnnotation(JAXB.XML_LIST));
	}
}