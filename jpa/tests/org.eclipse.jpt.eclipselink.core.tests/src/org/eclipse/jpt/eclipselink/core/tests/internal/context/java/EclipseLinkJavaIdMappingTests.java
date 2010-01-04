/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkIdMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkMutable;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkConvertAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkMutableAnnotation;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.EclipseLinkContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class EclipseLinkJavaIdMappingTests extends EclipseLinkContextModelTestCase
{
	
	private ICompilationUnit createTestEntityWithIdMapping() throws Exception {		
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
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID, EclipseLink.CONVERT);
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
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID, EclipseLink.MUTABLE);
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
	
	private ICompilationUnit createTestEntityWithMutableIdDate() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID, EclipseLink.MUTABLE, "java.util.Date");
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id").append(CR);
				sb.append("    @Mutable").append(CR);
				sb.append("    private Date myDate;").append(CR);
				sb.append(CR);
				sb.append("    ");
			}
		});
	}
		
	public EclipseLinkJavaIdMappingTests(String name) {
		super(name);
	}


	public void testGetConvert() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.addAnnotation(EclipseLinkConvertAnnotation.ANNOTATION_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getSpecifiedMapping();

		assertEquals(EclipseLinkConvert.ECLIPSE_LINK_CONVERTER, idMapping.getConverter().getType());
	}
	
	public void testGetConvert2() throws Exception {
		createTestEntityWithConvert();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();

		assertEquals(EclipseLinkConvert.ECLIPSE_LINK_CONVERTER, idMapping.getConverter().getType());
		assertEquals(EclipseLinkConvert.CLASS_INSTANCE_CONVERTER, ((EclipseLinkConvert) idMapping.getConverter()).getConverterName());
	}

	public void testSetConvert() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getSpecifiedMapping();
		assertEquals(Converter.NO_CONVERTER, idMapping.getConverter().getType());
		
		idMapping.setConverter(Converter.TEMPORAL_CONVERTER);
		((TemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		TemporalAnnotation temporal = (TemporalAnnotation) attributeResource.getAnnotation(TemporalAnnotation.ANNOTATION_NAME);
		
		assertEquals(org.eclipse.jpt.core.resource.java.TemporalType.TIME, temporal.getValue());
		
		idMapping.setConverter(Converter.NO_CONVERTER);
		assertNull(attributeResource.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
	}
	
	public void testGetConvertUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getSpecifiedMapping();

		assertEquals(Converter.NO_CONVERTER, idMapping.getConverter().getType());
		
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		EclipseLinkConvertAnnotation convert = (EclipseLinkConvertAnnotation) attributeResource.addAnnotation(EclipseLinkConvertAnnotation.ANNOTATION_NAME);
		convert.setValue("foo");
		
		assertEquals(EclipseLinkConvert.ECLIPSE_LINK_CONVERTER, idMapping.getConverter().getType());
		assertEquals("foo", ((EclipseLinkConvert) idMapping.getConverter()).getConverterName());
		
		attributeResource.removeAnnotation(EclipseLinkConvertAnnotation.ANNOTATION_NAME);
		
		assertEquals(Converter.NO_CONVERTER, idMapping.getConverter().getType());
		assertFalse(idMapping.isDefault());
		assertSame(idMapping, persistentAttribute.getSpecifiedMapping());
	}
	
	public void testGetSpecifiedMutable() throws Exception {
		createTestEntityWithMutableId();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		EclipseLinkIdMapping idMapping = (EclipseLinkIdMapping) persistentAttribute.getSpecifiedMapping();
		EclipseLinkMutable mutable = idMapping.getMutable();
		assertEquals(Boolean.TRUE, mutable.getSpecifiedMutable());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		EclipseLinkMutableAnnotation mutableAnnotation = (EclipseLinkMutableAnnotation) attributeResource.getAnnotation(EclipseLinkMutableAnnotation.ANNOTATION_NAME);
		mutableAnnotation.setValue(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, mutable.getSpecifiedMutable());

		mutableAnnotation.setValue(null);
		assertEquals(Boolean.TRUE, mutable.getSpecifiedMutable());

		mutableAnnotation.setValue(Boolean.FALSE);
		assertEquals(Boolean.FALSE, mutable.getSpecifiedMutable());
		
		attributeResource.removeAnnotation(EclipseLinkMutableAnnotation.ANNOTATION_NAME);
		assertEquals(null, mutable.getSpecifiedMutable());
		
		attributeResource.addAnnotation(EclipseLinkMutableAnnotation.ANNOTATION_NAME);
		assertEquals(Boolean.TRUE, mutable.getSpecifiedMutable());
	}
	
	public void testSetSpecifiedMutable() throws Exception {
		createTestEntityWithMutableId();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		EclipseLinkIdMapping idMapping = (EclipseLinkIdMapping) persistentAttribute.getSpecifiedMapping();
		EclipseLinkMutable mutable = idMapping.getMutable();
		assertEquals(Boolean.TRUE, mutable.getSpecifiedMutable());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		EclipseLinkMutableAnnotation mutableAnnotation = (EclipseLinkMutableAnnotation) attributeResource.getAnnotation(EclipseLinkMutableAnnotation.ANNOTATION_NAME);
		assertEquals(null, mutableAnnotation.getValue());
		
		mutable.setSpecifiedMutable(Boolean.TRUE);	
		assertEquals(null, mutableAnnotation.getValue());

		mutable.setSpecifiedMutable(null);
		mutableAnnotation = (EclipseLinkMutableAnnotation) attributeResource.getAnnotation(EclipseLinkMutableAnnotation.ANNOTATION_NAME);
		assertEquals(null, mutableAnnotation);
		
		mutable.setSpecifiedMutable(Boolean.FALSE);	
		mutableAnnotation = (EclipseLinkMutableAnnotation) attributeResource.getAnnotation(EclipseLinkMutableAnnotation.ANNOTATION_NAME);
		assertEquals(Boolean.FALSE, mutableAnnotation.getValue());
		
		mutable.setSpecifiedMutable(Boolean.TRUE);	
		assertEquals(null, mutableAnnotation.getValue());
	}
	
	public void testIsDefaultMutable() throws Exception {
		createTestEntityWithMutableId();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		EclipseLinkIdMapping idMapping = (EclipseLinkIdMapping) persistentAttribute.getSpecifiedMapping();
		EclipseLinkMutable mutable = idMapping.getMutable();
		assertTrue(mutable.isDefaultMutable());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.removeAnnotation(EclipseLinkMutableAnnotation.ANNOTATION_NAME);
		assertTrue(mutable.isDefaultMutable());
		
		mutable.setSpecifiedMutable(Boolean.FALSE);	
		assertTrue(mutable.isDefaultMutable());
		
		//set mutable default to false in the persistence unit properties, verify default in java still true since this is not a Date/Calendar
		(getPersistenceUnit()).getOptions().setTemporalMutable(Boolean.FALSE);
		assertTrue(mutable.isDefaultMutable());
	}
	
	public void testIsDefaultMutableForDate() throws Exception {
		createTestEntityWithMutableIdDate();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		EclipseLinkIdMapping idMapping = (EclipseLinkIdMapping) persistentAttribute.getSpecifiedMapping();
		EclipseLinkMutable mutable = idMapping.getMutable();
		assertFalse(mutable.isDefaultMutable());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.removeAnnotation(EclipseLinkMutableAnnotation.ANNOTATION_NAME);
		assertFalse(mutable.isDefaultMutable());
		
		mutable.setSpecifiedMutable(Boolean.TRUE);	
		assertFalse(mutable.isDefaultMutable());
		
		//set mutable default to false in the persistence unit properties, verify default in java still true since this is not a Date/Calendar
		(getPersistenceUnit()).getOptions().setTemporalMutable(Boolean.TRUE);
		assertTrue(mutable.isDefaultMutable());
		
		(getPersistenceUnit()).getOptions().setTemporalMutable(Boolean.FALSE);
		assertFalse(mutable.isDefaultMutable());
		
		(getPersistenceUnit()).getOptions().setTemporalMutable(null);
		assertFalse(mutable.isDefaultMutable());
	}
	
	public void testIsMutable() throws Exception {
		createTestEntityWithMutableId();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		EclipseLinkIdMapping idMapping = (EclipseLinkIdMapping) persistentAttribute.getSpecifiedMapping();
		EclipseLinkMutable mutable = idMapping.getMutable();
		assertTrue(mutable.isMutable());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.removeAnnotation(EclipseLinkMutableAnnotation.ANNOTATION_NAME);
		assertTrue(mutable.isMutable());
		
		mutable.setSpecifiedMutable(Boolean.TRUE);	
		assertTrue(mutable.isMutable());
	}
}
