/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.core.context.BasicMapping;
import org.eclipse.jpt.jpa.core.context.SpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.BaseTemporalConverter;
import org.eclipse.jpt.jpa.core.context.TemporalType;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.TemporalAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkBasicMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkMutable;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ConvertAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.MutableAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.EclipseLinkContextModelTestCase;

@SuppressWarnings("nls")
public class EclipseLinkJavaBasicMappingTests extends EclipseLinkContextModelTestCase
{	
	
	private ICompilationUnit createTestEntityWithBasicMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.BASIC);
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
				return IteratorTools.iterator(JPA.ENTITY, EclipseLink.CONVERT);
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
	
	private ICompilationUnit createTestEntityWithMutableBasic() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.BASIC, EclipseLink.MUTABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Basic").append(CR);
				sb.append("@Mutable").append(CR);
			}
		});
	}
	
	private ICompilationUnit createTestEntityWithMutableBasicDate() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY, JPA.BASIC, EclipseLink.MUTABLE, "java.util.Date");
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Basic").append(CR);
				sb.append("    @Mutable").append(CR);
				sb.append("    private Date myDate;").append(CR);
				sb.append(CR);
				sb.append("    ");
			}
		});
	}

	public EclipseLinkJavaBasicMappingTests(String name) {
		super(name);
	}


	public void testGetConvert() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		resourceField.addAnnotation(ConvertAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();

		assertEquals(EclipseLinkConvert.class, basicMapping.getConverter().getType());
	}
	
	public void testGetConvert2() throws Exception {
		createTestEntityWithConvert();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();

		assertEquals(EclipseLinkConvert.class, basicMapping.getConverter().getType());
		assertEquals(EclipseLinkConvert.CLASS_INSTANCE_CONVERTER, ((EclipseLinkConvert) basicMapping.getConverter()).getConverterName());
	}

	public void testSetConvert() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();
		assertNull(basicMapping.getConverter().getType());
		
		basicMapping.setConverter(BaseTemporalConverter.class);
		((BaseTemporalConverter) basicMapping.getConverter()).setTemporalType(TemporalType.TIME);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		TemporalAnnotation temporal = (TemporalAnnotation) resourceField.getAnnotation(TemporalAnnotation.ANNOTATION_NAME);
		
		assertEquals(org.eclipse.jpt.jpa.core.resource.java.TemporalType.TIME, temporal.getValue());
		
		basicMapping.setConverter(null);
		assertNull(resourceField.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
	}
	
	public void testGetConvertUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();

		assertNull(basicMapping.getConverter().getType());
		
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		ConvertAnnotation convert = (ConvertAnnotation) resourceField.addAnnotation(ConvertAnnotation.ANNOTATION_NAME);
		convert.setValue("foo");
		getJpaProject().synchronizeContextModel();
		
		assertEquals(EclipseLinkConvert.class, basicMapping.getConverter().getType());
		assertEquals("foo", ((EclipseLinkConvert) basicMapping.getConverter()).getConverterName());
		
		resourceField.removeAnnotation(ConvertAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		
		assertNull(basicMapping.getConverter().getType());
		assertFalse(basicMapping.isDefault());
		assertSame(basicMapping, persistentAttribute.getMapping());
	}
	
	public void testGetSpecifiedMutable() throws Exception {
		createTestEntityWithMutableBasic();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		EclipseLinkBasicMapping basicMapping = (EclipseLinkBasicMapping) persistentAttribute.getMapping();
		EclipseLinkMutable mutable = basicMapping.getMutable();
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
		createTestEntityWithMutableBasic();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		EclipseLinkBasicMapping basicMapping = (EclipseLinkBasicMapping) persistentAttribute.getMapping();
		EclipseLinkMutable mutable = basicMapping.getMutable();
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
		createTestEntityWithMutableBasic();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		EclipseLinkBasicMapping basicMapping = (EclipseLinkBasicMapping) persistentAttribute.getMapping();
		EclipseLinkMutable mutable = basicMapping.getMutable();
		assertTrue(mutable.isDefaultMutable());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		resourceField.removeAnnotation(MutableAnnotation.ANNOTATION_NAME);
		assertTrue(mutable.isDefaultMutable());
		
		mutable.setSpecifiedMutable(Boolean.FALSE);	
		assertTrue(mutable.isDefaultMutable());
		
		//set mutable default to false in the persistence unit properties, verify default in java still true since this is not a Date/Calendar
		getPersistenceUnit().getEclipseLinkOptions().setTemporalMutable(Boolean.FALSE);
		assertTrue(mutable.isDefaultMutable());
	}
	
	public void testIsDefaultMutableForDate() throws Exception {
		createTestEntityWithMutableBasicDate();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		EclipseLinkBasicMapping basicMapping = (EclipseLinkBasicMapping) persistentAttribute.getMapping();
		EclipseLinkMutable mutable = basicMapping.getMutable();
		assertFalse(mutable.isDefaultMutable());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		resourceField.removeAnnotation(MutableAnnotation.ANNOTATION_NAME);
		assertFalse(mutable.isDefaultMutable());
		
		mutable.setSpecifiedMutable(Boolean.TRUE);	
		assertFalse(mutable.isDefaultMutable());
		
		//set mutable default to false in the persistence unit properties, verify default in java still true since this is not a Date/Calendar
		getPersistenceUnit().getEclipseLinkOptions().setTemporalMutable(Boolean.TRUE);
		assertTrue(mutable.isDefaultMutable());
		
		getPersistenceUnit().getEclipseLinkOptions().setTemporalMutable(Boolean.FALSE);
		assertFalse(mutable.isDefaultMutable());
		
		getPersistenceUnit().getEclipseLinkOptions().setTemporalMutable(null);
		assertFalse(mutable.isDefaultMutable());
	}
	
	public void testIsMutable() throws Exception {
		createTestEntityWithMutableBasic();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		SpecifiedPersistentAttribute persistentAttribute = getJavaPersistentType().getAttributes().iterator().next();
		EclipseLinkBasicMapping basicMapping = (EclipseLinkBasicMapping) persistentAttribute.getMapping();
		EclipseLinkMutable mutable = basicMapping.getMutable();
		assertTrue(mutable.isMutable());
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		JavaResourceField resourceField = resourceType.getFields().iterator().next();
		resourceField.removeAnnotation(MutableAnnotation.ANNOTATION_NAME);
		assertTrue(mutable.isMutable());
		
		mutable.setSpecifiedMutable(Boolean.TRUE);	
		assertTrue(mutable.isMutable());
	}
}
