/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.Kind;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.context.BasicMapping;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkStructConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkConvertAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkStructConverterAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkTypeConverterAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.EclipseLinkContextModelTestCase;

@SuppressWarnings("nls")
public class EclipseLinkJavaConvertTests extends EclipseLinkContextModelTestCase
{

	private ICompilationUnit createTestEntityWithBasicMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.BASIC);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Basic").append(CR);
			}
		});
	}

	
	private ICompilationUnit createTestEntityWithConvert() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, EclipseLink.CONVERT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Convert(\"class-instance\")").append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithConvertAndTypeConverter() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, EclipseLink.CONVERT, EclipseLink.TYPE_CONVERTER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Convert(\"class-instance\")").append(CR);
				sb.append("    @TypeConverter");
			}
		});
	}
	
	public EclipseLinkJavaConvertTests(String name) {
		super(name);
	}


	public void testGetConverterName() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		resourceField.addAnnotation(EclipseLinkConvertAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		EclipseLinkConvert eclipseLinkConvert = (EclipseLinkConvert) basicMapping.getConverter();
		
		assertEquals(EclipseLinkConvert.NO_CONVERTER, eclipseLinkConvert.getConverterName());
		assertEquals(EclipseLinkConvert.NO_CONVERTER, eclipseLinkConvert.getDefaultConverterName());
		assertEquals(null, eclipseLinkConvert.getSpecifiedConverterName());
	}
	
	public void testGetConvertName2() throws Exception {
		createTestEntityWithConvert();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		EclipseLinkConvert eclipseLinkConvert = (EclipseLinkConvert) basicMapping.getConverter();

		assertEquals(EclipseLinkConvert.CLASS_INSTANCE_CONVERTER, eclipseLinkConvert.getConverterName());
	}

	public void testSetSpecifiedConverterName() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		basicMapping.setConverter(EclipseLinkConvert.class);
		EclipseLinkConvert eclipseLinkConvert = (EclipseLinkConvert) basicMapping.getConverter();
		assertEquals(null, eclipseLinkConvert.getSpecifiedConverterName());
		
		eclipseLinkConvert.setSpecifiedConverterName("foo");
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		EclipseLinkConvertAnnotation convertAnnotation = (EclipseLinkConvertAnnotation) resourceField.getAnnotation(EclipseLinkConvertAnnotation.ANNOTATION_NAME);
		
		assertEquals("foo", convertAnnotation.getValue());
		
		eclipseLinkConvert.setSpecifiedConverterName(null);
		convertAnnotation = (EclipseLinkConvertAnnotation) resourceField.getAnnotation(EclipseLinkConvertAnnotation.ANNOTATION_NAME);
		assertNotNull(convertAnnotation);
		assertEquals(null, convertAnnotation.getValue());
	}
	
	public void testGetConverterNameUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();

		assertNull(basicMapping.getConverter().getType());
		
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		EclipseLinkConvertAnnotation convert = (EclipseLinkConvertAnnotation) resourceField.addAnnotation(EclipseLinkConvertAnnotation.ANNOTATION_NAME);
		convert.setValue("foo");
		getJpaProject().synchronizeContextModel();
		
		assertEquals(EclipseLinkConvert.class, basicMapping.getConverter().getType());
		assertEquals("foo", ((EclipseLinkConvert) basicMapping.getConverter()).getConverterName());
		
		resourceField.removeAnnotation(EclipseLinkConvertAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		
		assertNull(basicMapping.getConverter().getType());
		assertFalse(basicMapping.isDefault());
		assertSame(basicMapping, persistentAttribute.getMapping());
	}
	

	public void testGetConverter() throws Exception {
		createTestEntityWithConvertAndTypeConverter();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		EclipseLinkConvert eclipseLinkConvert = (EclipseLinkConvert) basicMapping.getConverter();
		
		assertEquals(EclipseLinkTypeConverter.class, eclipseLinkConvert.getConverter().getType());
	}
	
	public void testSetConverter() throws Exception {
		createTestEntityWithConvert();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		EclipseLinkConvert eclipseLinkConvert = (EclipseLinkConvert) basicMapping.getConverter();
		
		assertEquals(null, eclipseLinkConvert.getConverter());
		
		eclipseLinkConvert.setConverter(EclipseLinkTypeConverter.class);	
		assertEquals(EclipseLinkTypeConverter.class, eclipseLinkConvert.getConverter().getType());
	
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		assertNotNull(resourceField.getAnnotation(EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME));
		
		eclipseLinkConvert.setConverter(EclipseLinkStructConverter.class);
		assertEquals(EclipseLinkStructConverter.class, eclipseLinkConvert.getConverter().getType());
		assertNotNull(resourceField.getAnnotation(EclipseLinkStructConverterAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME));

		eclipseLinkConvert.setConverter(null);
		assertEquals(null, eclipseLinkConvert.getConverter());
		assertNull(resourceField.getAnnotation(EclipseLinkStructConverterAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(EclipseLinkTypeConverterAnnotation.ANNOTATION_NAME));
		
		eclipseLinkConvert.setConverter(EclipseLinkStructConverter.class);
		assertEquals(EclipseLinkStructConverter.class, eclipseLinkConvert.getConverter().getType());
		assertNotNull(resourceField.getAnnotation(EclipseLinkStructConverterAnnotation.ANNOTATION_NAME));

		
		basicMapping.setConverter(null);
		assertNull(resourceField.getAnnotation(EclipseLinkStructConverterAnnotation.ANNOTATION_NAME));
		assertNull(resourceField.getAnnotation(EclipseLinkConvertAnnotation.ANNOTATION_NAME));
		
	}

}
