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
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentClass;
import org.eclipse.jpt.jaxb.core.context.XmlAnyAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.XmlElementMapping;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAnyAttributeAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAttributeAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;
import org.eclipse.jpt.jaxb.core.tests.internal.context.JaxbContextModelTestCase;


@SuppressWarnings("nls")
public class GenericJavaXmlAnyAttributeMappingTests extends JaxbContextModelTestCase
{

	public GenericJavaXmlAnyAttributeMappingTests(String name) {
		super(name);
	}

	private ICompilationUnit createTypeWithXmlAnyAttribute() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JAXB.XML_TYPE, JAXB.XML_ANY_ATTRIBUTE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@XmlType");
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@XmlAnyAttribute");
			}
		});
	}

	public void testChangeMappingType() throws Exception {
		createTypeWithXmlAnyAttribute();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(persistentClass.getAttributes(), 0);
		XmlAnyAttributeMapping xmlAnyAttributeMapping = (XmlAnyAttributeMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlAnyAttributeMapping.getParent().getJavaResourceAttribute();

		assertNotNull(xmlAnyAttributeMapping);
		assertNotNull(resourceAttribute.getAnnotation(XmlAnyAttributeAnnotation.ANNOTATION_NAME));

		persistentAttribute.setMappingKey(MappingKeys.XML_ATTRIBUTE_ATTRIBUTE_MAPPING_KEY);
		XmlAttributeMapping xmlAttributeMapping = (XmlAttributeMapping) persistentAttribute.getMapping();
		assertNotNull(xmlAttributeMapping);
		assertNull(resourceAttribute.getAnnotation(XmlAnyAttributeAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceAttribute.getAnnotation(XmlAttributeAnnotation.ANNOTATION_NAME));

		persistentAttribute.setMappingKey(MappingKeys.XML_ANY_ATTRIBUTE_ATTRIBUTE_MAPPING_KEY);
		xmlAnyAttributeMapping = (XmlAnyAttributeMapping) persistentAttribute.getMapping();
		assertNotNull(xmlAnyAttributeMapping);
		assertNotNull(resourceAttribute.getAnnotation(XmlAnyAttributeAnnotation.ANNOTATION_NAME));
		assertNull(resourceAttribute.getAnnotation(XmlAttributeAnnotation.ANNOTATION_NAME));


		persistentAttribute.setMappingKey(MappingKeys.XML_ELEMENT_ATTRIBUTE_MAPPING_KEY);
		XmlElementMapping xmlElementMapping = (XmlElementMapping) persistentAttribute.getMapping();
		assertNotNull(xmlElementMapping);
		assertNull(resourceAttribute.getAnnotation(XmlAnyAttributeAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceAttribute.getAnnotation(XmlElementAnnotation.ANNOTATION_NAME));

		persistentAttribute.setMappingKey(MappingKeys.XML_ANY_ATTRIBUTE_ATTRIBUTE_MAPPING_KEY);
		xmlAnyAttributeMapping = (XmlAnyAttributeMapping) persistentAttribute.getMapping();
		assertNotNull(xmlAnyAttributeMapping);
		assertNotNull(resourceAttribute.getAnnotation(XmlAnyAttributeAnnotation.ANNOTATION_NAME));
		assertNull(resourceAttribute.getAnnotation(XmlElementAnnotation.ANNOTATION_NAME));
	}

	public void testModifyXmlJavaTypeAdapter() throws Exception {
		createTypeWithXmlAnyAttribute();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(persistentClass.getAttributes(), 0);
		XmlAnyAttributeMapping xmlAnyAttributeMapping = (XmlAnyAttributeMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlAnyAttributeMapping.getParent().getJavaResourceAttribute();

		XmlJavaTypeAdapterAnnotation xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceAttribute.getAnnotation(0, XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
		assertNull(xmlAnyAttributeMapping.getXmlJavaTypeAdapter());
		assertNull(xmlJavaTypeAdapterAnnotation);

		xmlAnyAttributeMapping.addXmlJavaTypeAdapter();
		xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceAttribute.getAnnotation(0, XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
		assertNotNull(xmlAnyAttributeMapping.getXmlJavaTypeAdapter());
		assertNotNull(xmlJavaTypeAdapterAnnotation);

		xmlAnyAttributeMapping.removeXmlJavaTypeAdapter();
		xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceAttribute.getAnnotation(0, XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
	}

	public void testUpdateXmlJavaTypeAdapter() throws Exception {
		createTypeWithXmlAnyAttribute();

		JaxbPersistentClass persistentClass = CollectionTools.get(getContextRoot().getPersistentClasses(), 0);
		JaxbPersistentAttribute persistentAttribute = CollectionTools.get(persistentClass.getAttributes(), 0);
		XmlAnyAttributeMapping xmlAnyAttributeMapping = (XmlAnyAttributeMapping) persistentAttribute.getMapping();
		JavaResourceAttribute resourceAttribute = xmlAnyAttributeMapping.getParent().getJavaResourceAttribute();

		XmlJavaTypeAdapterAnnotation xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceAttribute.getAnnotation(0, XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
		assertNull(xmlAnyAttributeMapping.getXmlJavaTypeAdapter());
		assertNull(xmlJavaTypeAdapterAnnotation);


		//add an XmlJavaTypeAdapter annotation
		AnnotatedElement annotatedElement = this.annotatedElement(resourceAttribute);
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlAnyAttributeMappingTests.this.addMarkerAnnotation(declaration.getDeclaration(), XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
			}
		});
		xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceAttribute.getAnnotation(0, XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
		assertNotNull(xmlAnyAttributeMapping.getXmlJavaTypeAdapter());
		assertNotNull(xmlJavaTypeAdapterAnnotation);

		//remove the XmlJavaTypeAdapter annotation
		annotatedElement.edit(new Member.Editor() {
			public void edit(ModifiedDeclaration declaration) {
				GenericJavaXmlAnyAttributeMappingTests.this.removeAnnotation(declaration, XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
			}
		});
		xmlJavaTypeAdapterAnnotation = (XmlJavaTypeAdapterAnnotation) resourceAttribute.getAnnotation(0, XmlJavaTypeAdapterAnnotation.ANNOTATION_NAME);
		assertNull(xmlAnyAttributeMapping.getXmlJavaTypeAdapter());
		assertNull(xmlJavaTypeAdapterAnnotation);
	}

}