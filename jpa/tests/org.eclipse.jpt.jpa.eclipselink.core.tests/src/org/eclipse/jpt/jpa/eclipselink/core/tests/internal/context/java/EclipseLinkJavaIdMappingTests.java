/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.AstNodeType;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.SpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.BaseTemporalConverter;
import org.eclipse.jpt.jpa.core.context.TemporalType;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.TemporalAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkIdMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkMutable;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ConvertAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.MutableAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.EclipseLinkContextModelTestCase;

@SuppressWarnings("nls")
public class EclipseLinkJavaIdMappingTests extends EclipseLinkContextModelTestCase
{
	
	private ICompilationUnit createTestEntityWithIdMapping() throws Exception {		
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.ID);
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
				return IteratorTools.iterator(JPA.ENTITY, JPA.ID, EclipseLink.CONVERT);
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
				return IteratorTools.iterator(JPA.ENTITY, JPA.ID, EclipseLink.MUTABLE);
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
				return IteratorTools.iterator(JPA.ENTITY, JPA.ID, EclipseLink.MUTABLE, "java.util.Date");
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
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		resourceField.addAnnotation(ConvertAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();

		assertEquals(EclipseLinkConvert.class, idMapping.getConverter().getConverterType());
	}
	
	public void testGetConvert2() throws Exception {
		createTestEntityWithConvert();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();

		assertEquals(EclipseLinkConvert.class, idMapping.getConverter().getConverterType());
		assertEquals(EclipseLinkConvert.CLASS_INSTANCE_CONVERTER, ((EclipseLinkConvert) idMapping.getConverter()).getConverterName());
	}

	public void testSetConvert() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();
		assertNull(idMapping.getConverter().getConverterType());
		
		idMapping.setConverter(BaseTemporalConverter.class);
		((BaseTemporalConverter) idMapping.getConverter()).setTemporalType(TemporalType.TIME);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		TemporalAnnotation temporal = (TemporalAnnotation) resourceField.getAnnotation(TemporalAnnotation.ANNOTATION_NAME);
		
		assertEquals(org.eclipse.jpt.jpa.core.resource.java.TemporalType.TIME, temporal.getValue());
		
		idMapping.setConverter(null);
		assertNull(resourceField.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
	}
	
	public void testGetConvertUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithIdMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		IdMapping idMapping = (IdMapping) persistentAttribute.getMapping();

		assertNull(idMapping.getConverter().getConverterType());
		
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		ConvertAnnotation convert = (ConvertAnnotation) resourceField.addAnnotation(ConvertAnnotation.ANNOTATION_NAME);
		convert.setValue("foo");
		getJpaProject().synchronizeContextModel();
		
		assertEquals(EclipseLinkConvert.class, idMapping.getConverter().getConverterType());
		assertEquals("foo", ((EclipseLinkConvert) idMapping.getConverter()).getConverterName());
		
		resourceField.removeAnnotation(ConvertAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		
		assertNull(idMapping.getConverter().getConverterType());
		assertFalse(idMapping.isDefault());
		assertSame(idMapping, persistentAttribute.getMapping());
	}
	
	public void testGetSpecifiedMutable() throws Exception {
		createTestEntityWithMutableId();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		EclipseLinkIdMapping idMapping = (EclipseLinkIdMapping) persistentAttribute.getMapping();
		EclipseLinkMutable mutable = idMapping.getMutable();
		assertEquals(Boolean.TRUE, mutable.getSpecifiedMutable());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		MutableAnnotation mutableAnnotation = (MutableAnnotation) resourceField.getAnnotation(MutableAnnotation.ANNOTATION_NAME);
		mutableAnnotation.setValue(Boolean.TRUE);
		getJpaProject().synchronizeContextModel();
		
		assertEquals(Boolean.TRUE, mutable.getSpecifiedMutable());

		mutableAnnotation.setValue(null);
		getJpaProject().synchronizeContextModel();
		assertEquals(Boolean.TRUE, mutable.getSpecifiedMutable());

		mutableAnnotation.setValue(Boolean.FALSE);
		getJpaProject().synchronizeContextModel();
		assertEquals(Boolean.FALSE, mutable.getSpecifiedMutable());
		
		resourceField.removeAnnotation(MutableAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertEquals(null, mutable.getSpecifiedMutable());
		
		resourceField.addAnnotation(MutableAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertEquals(Boolean.TRUE, mutable.getSpecifiedMutable());
	}
	
	public void testSetSpecifiedMutable() throws Exception {
		createTestEntityWithMutableId();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		EclipseLinkIdMapping idMapping = (EclipseLinkIdMapping) persistentAttribute.getMapping();
		EclipseLinkMutable mutable = idMapping.getMutable();
		assertEquals(Boolean.TRUE, mutable.getSpecifiedMutable());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		MutableAnnotation mutableAnnotation = (MutableAnnotation) resourceField.getAnnotation(MutableAnnotation.ANNOTATION_NAME);
		assertEquals(null, mutableAnnotation.getValue());
		
		mutable.setSpecifiedMutable(Boolean.TRUE);	
		assertEquals(null, mutableAnnotation.getValue());

		mutable.setSpecifiedMutable(null);
		mutableAnnotation = (MutableAnnotation) resourceField.getAnnotation(MutableAnnotation.ANNOTATION_NAME);
		assertEquals(null, mutableAnnotation);
		
		mutable.setSpecifiedMutable(Boolean.FALSE);	
		mutableAnnotation = (MutableAnnotation) resourceField.getAnnotation(MutableAnnotation.ANNOTATION_NAME);
		assertEquals(Boolean.FALSE, mutableAnnotation.getValue());
		
		mutable.setSpecifiedMutable(Boolean.TRUE);	
		assertEquals(Boolean.TRUE, mutableAnnotation.getValue());
	}
	
	public void testIsDefaultMutable() throws Exception {
		createTestEntityWithMutableId();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		EclipseLinkIdMapping idMapping = (EclipseLinkIdMapping) persistentAttribute.getMapping();
		EclipseLinkMutable mutable = idMapping.getMutable();
		assertTrue(mutable.getDefaultMutable());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		resourceField.removeAnnotation(MutableAnnotation.ANNOTATION_NAME);
		assertTrue(mutable.getDefaultMutable());
		
		mutable.setSpecifiedMutable(Boolean.FALSE);	
		assertTrue(mutable.getDefaultMutable());
		
		//set mutable default to false in the persistence unit properties, verify default in java still true since this is not a Date/Calendar
		(getPersistenceUnit()).getEclipseLinkOptions().setTemporalMutable(Boolean.FALSE);
		assertTrue(mutable.getDefaultMutable());
	}
	
	public void testIsDefaultMutableForDate() throws Exception {
		createTestEntityWithMutableIdDate();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		EclipseLinkIdMapping idMapping = (EclipseLinkIdMapping) persistentAttribute.getMapping();
		EclipseLinkMutable mutable = idMapping.getMutable();
		assertFalse(mutable.getDefaultMutable());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		resourceField.removeAnnotation(MutableAnnotation.ANNOTATION_NAME);
		assertFalse(mutable.getDefaultMutable());
		
		mutable.setSpecifiedMutable(Boolean.TRUE);	
		assertFalse(mutable.getDefaultMutable());
		
		//set mutable default to false in the persistence unit properties, verify default in java still true since this is not a Date/Calendar
		(getPersistenceUnit()).getEclipseLinkOptions().setTemporalMutable(Boolean.TRUE);
		assertTrue(mutable.getDefaultMutable());
		
		(getPersistenceUnit()).getEclipseLinkOptions().setTemporalMutable(Boolean.FALSE);
		assertFalse(mutable.getDefaultMutable());
		
		(getPersistenceUnit()).getEclipseLinkOptions().setTemporalMutable(null);
		assertFalse(mutable.getDefaultMutable());
	}
	
	public void testIsMutable() throws Exception {
		createTestEntityWithMutableId();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		EclipseLinkIdMapping idMapping = (EclipseLinkIdMapping) persistentAttribute.getMapping();
		EclipseLinkMutable mutable = idMapping.getMutable();
		assertTrue(mutable.isMutable());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		resourceField.removeAnnotation(MutableAnnotation.ANNOTATION_NAME);
		assertTrue(mutable.isMutable());
		
		mutable.setSpecifiedMutable(Boolean.TRUE);	
		assertTrue(mutable.isMutable());
	}
}
