/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.TemporalConverter;
import org.eclipse.jpt.core.context.TemporalType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.TemporalAnnotation;
import org.eclipse.jpt.eclipselink.core.context.Convert;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkIdMapping;
import org.eclipse.jpt.eclipselink.core.context.Mutable;
import org.eclipse.jpt.eclipselink.core.resource.java.ConvertAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.java.MutableAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class EclipseLinkJavaIdMappingTests extends EclipseLinkJavaContextModelTestCase
{

	private void createConvertAnnotation() throws Exception{
		this.createAnnotationAndMembers(EclipseLinkJPA.PACKAGE, "Convert", "String value() default \"none\";");		
	}

	private void createMutableAnnotation() throws Exception{
		this.createAnnotationAndMembers(EclipseLinkJPA.PACKAGE, "Mutable", "boolean value() default true");		
	}
	
	private ICompilationUnit createTestEntityWithIdMapping() throws Exception {
		createConvertAnnotation();
		
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id").append(CR);
			}
		});
	}

	
	private ICompilationUnit createTestEntityWithConvert() throws Exception {
		createConvertAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID, EclipseLinkJPA.CONVERT);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id").append(CR);
				sb.append("@Convert(\"class-instance\")").append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithMutableId() throws Exception {
		createMutableAnnotation();
		
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID, EclipseLinkJPA.MUTABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id").append(CR);
				sb.append("@Mutable").append(CR);
			}
		});
	}
		
	public EclipseLinkJavaIdMappingTests(String name) {
		super(name);
	}


	public void testGetConvert() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		attributeResource.addAnnotation(ConvertAnnotation.ANNOTATION_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getSpecifiedMapping();

		assertEquals(Convert.ECLIPSE_LINK_CONVERTER, idMapping.getConverter().getType());
	}
	
	public void testGetConvert2() throws Exception {
		createTestEntityWithConvert();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();

		assertEquals(Convert.ECLIPSE_LINK_CONVERTER, idMapping.getConverter().getType());
		assertEquals(Convert.CLASS_INSTANCE_CONVERTER, ((Convert) idMapping.getConverter()).getConverterName());
	}

	public void testSetConvert() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getSpecifiedMapping();
		assertEquals(Converter.NO_CONVERTER, idMapping.getConverter().getType());
		
		idMapping.setSpecifiedConverter(Converter.TEMPORAL_CONVERTER);
		((TemporalConverter) idMapping.getSpecifiedConverter()).setTemporalType(TemporalType.TIME);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		TemporalAnnotation temporal = (TemporalAnnotation) attributeResource.getAnnotation(TemporalAnnotation.ANNOTATION_NAME);
		
		assertEquals(org.eclipse.jpt.core.resource.java.TemporalType.TIME, temporal.getValue());
		
		idMapping.setSpecifiedConverter(null);
		assertNull(attributeResource.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
	}
	
	public void testGetConvertUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getSpecifiedMapping();

		assertEquals(Converter.NO_CONVERTER, idMapping.getConverter().getType());
		
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		ConvertAnnotation convert = (ConvertAnnotation) attributeResource.addAnnotation(ConvertAnnotation.ANNOTATION_NAME);
		convert.setValue("foo");
		
		assertEquals(Convert.ECLIPSE_LINK_CONVERTER, idMapping.getConverter().getType());
		assertEquals("foo", ((Convert) idMapping.getConverter()).getConverterName());
		
		attributeResource.removeAnnotation(ConvertAnnotation.ANNOTATION_NAME);
		
		assertEquals(Converter.NO_CONVERTER, idMapping.getConverter().getType());
		assertFalse(idMapping.isDefault());
		assertSame(idMapping, persistentAttribute.getSpecifiedMapping());
	}
	
	public void testGetSpecifiedMutable() throws Exception {
		createTestEntityWithMutableId();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		EclipseLinkIdMapping idMapping = (EclipseLinkIdMapping) persistentAttribute.getSpecifiedMapping();
		Mutable mutable = idMapping.getMutable();
		assertEquals(Boolean.TRUE, mutable.getSpecifiedMutable());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		MutableAnnotation mutableAnnotation = (MutableAnnotation) attributeResource.getAnnotation(MutableAnnotation.ANNOTATION_NAME);
		mutableAnnotation.setValue(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, mutable.getSpecifiedMutable());

		mutableAnnotation.setValue(null);
		assertEquals(Boolean.TRUE, mutable.getSpecifiedMutable());

		mutableAnnotation.setValue(Boolean.FALSE);
		assertEquals(Boolean.FALSE, mutable.getSpecifiedMutable());
		
		attributeResource.removeAnnotation(MutableAnnotation.ANNOTATION_NAME);
		assertEquals(null, mutable.getSpecifiedMutable());
		
		attributeResource.addAnnotation(MutableAnnotation.ANNOTATION_NAME);
		assertEquals(Boolean.TRUE, mutable.getSpecifiedMutable());
	}
	
	public void testSetSpecifiedMutable() throws Exception {
		createTestEntityWithMutableId();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		EclipseLinkIdMapping idMapping = (EclipseLinkIdMapping) persistentAttribute.getSpecifiedMapping();
		Mutable mutable = idMapping.getMutable();
		assertEquals(Boolean.TRUE, mutable.getSpecifiedMutable());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		MutableAnnotation mutableAnnotation = (MutableAnnotation) attributeResource.getAnnotation(MutableAnnotation.ANNOTATION_NAME);
		assertEquals(null, mutableAnnotation.getValue());
		
		mutable.setSpecifiedMutable(Boolean.TRUE);	
		assertEquals(null, mutableAnnotation.getValue());

		mutable.setSpecifiedMutable(null);
		mutableAnnotation = (MutableAnnotation) attributeResource.getAnnotation(MutableAnnotation.ANNOTATION_NAME);
		assertEquals(null, mutableAnnotation);
		
		mutable.setSpecifiedMutable(Boolean.FALSE);	
		mutableAnnotation = (MutableAnnotation) attributeResource.getAnnotation(MutableAnnotation.ANNOTATION_NAME);
		assertEquals(Boolean.FALSE, mutableAnnotation.getValue());
		
		mutable.setSpecifiedMutable(Boolean.TRUE);	
		assertEquals(null, mutableAnnotation.getValue());
	}
	
	public void testGetDefaultMutable() throws Exception {
		createTestEntityWithMutableId();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		EclipseLinkIdMapping idMapping = (EclipseLinkIdMapping) persistentAttribute.getSpecifiedMapping();
		Mutable mutable = idMapping.getMutable();
		assertEquals(true, mutable.getDefaultMutable());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		attributeResource.removeAnnotation(MutableAnnotation.ANNOTATION_NAME);
		assertEquals(true, mutable.getDefaultMutable());
		
		mutable.setSpecifiedMutable(Boolean.FALSE);	
		assertEquals(true, mutable.getDefaultMutable());
	}
	
	public void testGetMutable() throws Exception {
		createTestEntityWithMutableId();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		EclipseLinkIdMapping idMapping = (EclipseLinkIdMapping) persistentAttribute.getSpecifiedMapping();
		Mutable mutable = idMapping.getMutable();
		assertEquals(true, mutable.getMutable());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		attributeResource.removeAnnotation(MutableAnnotation.ANNOTATION_NAME);
		assertEquals(true, mutable.getMutable());
		
		mutable.setSpecifiedMutable(Boolean.TRUE);	
		assertEquals(true, mutable.getMutable());
	}
}
