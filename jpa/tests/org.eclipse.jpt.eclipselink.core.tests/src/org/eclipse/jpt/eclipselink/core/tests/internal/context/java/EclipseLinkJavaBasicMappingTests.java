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
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.TemporalConverter;
import org.eclipse.jpt.core.context.TemporalType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.TemporalAnnotation;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkBasicMapping;
import org.eclipse.jpt.eclipselink.core.context.Convert;
import org.eclipse.jpt.eclipselink.core.context.Mutable;
import org.eclipse.jpt.eclipselink.core.resource.java.ConvertAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.java.MutableAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class EclipseLinkJavaBasicMappingTests extends EclipseLinkJavaContextModelTestCase
{

	private void createConvertAnnotation() throws Exception{
		this.createAnnotationAndMembers(EclipseLinkJPA.PACKAGE, "Convert", "String value() default \"none\";");		
	}

	private void createMutableAnnotation() throws Exception{
		this.createAnnotationAndMembers(EclipseLinkJPA.PACKAGE, "Mutable", "boolean value() default true");		
	}
	
	
	private ICompilationUnit createTestEntityWithBasicMapping() throws Exception {
		createConvertAnnotation();
		
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
		createConvertAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, EclipseLinkJPA.CONVERT);
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
		createMutableAnnotation();
		
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.BASIC, EclipseLinkJPA.MUTABLE);
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

	public EclipseLinkJavaBasicMappingTests(String name) {
		super(name);
	}


	public void testGetConvert() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		attributeResource.addAnnotation(ConvertAnnotation.ANNOTATION_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getSpecifiedMapping();

		assertEquals(Convert.ECLIPSE_LINK_CONVERTER, basicMapping.getConverter().getType());
	}
	
	public void testGetConvert2() throws Exception {
		createTestEntityWithConvert();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getMapping();

		assertEquals(Convert.ECLIPSE_LINK_CONVERTER, basicMapping.getConverter().getType());
		assertEquals(Convert.CLASS_INSTANCE_CONVERTER, ((Convert) basicMapping.getConverter()).getConverterName());
	}

	public void testSetConvert() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getSpecifiedMapping();
		assertEquals(Converter.NO_CONVERTER, basicMapping.getConverter().getType());
		
		basicMapping.setSpecifiedConverter(Converter.TEMPORAL_CONVERTER);
		((TemporalConverter) basicMapping.getSpecifiedConverter()).setTemporalType(TemporalType.TIME);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		TemporalAnnotation temporal = (TemporalAnnotation) attributeResource.getAnnotation(TemporalAnnotation.ANNOTATION_NAME);
		
		assertEquals(org.eclipse.jpt.core.resource.java.TemporalType.TIME, temporal.getValue());
		
		basicMapping.setSpecifiedConverter(null);
		assertNull(attributeResource.getAnnotation(TemporalAnnotation.ANNOTATION_NAME));
	}
	
	public void testGetConvertUpdatesFromResourceModelChange() throws Exception {
		createTestEntityWithBasicMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		BasicMapping basicMapping = (BasicMapping) persistentAttribute.getSpecifiedMapping();

		assertEquals(Converter.NO_CONVERTER, basicMapping.getConverter().getType());
		
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		ConvertAnnotation convert = (ConvertAnnotation) attributeResource.addAnnotation(ConvertAnnotation.ANNOTATION_NAME);
		convert.setValue("foo");
		
		assertEquals(Convert.ECLIPSE_LINK_CONVERTER, basicMapping.getConverter().getType());
		assertEquals("foo", ((Convert) basicMapping.getConverter()).getConverterName());
		
		attributeResource.removeAnnotation(ConvertAnnotation.ANNOTATION_NAME);
		
		assertEquals(Converter.NO_CONVERTER, basicMapping.getConverter().getType());
		assertFalse(basicMapping.isDefault());
		assertSame(basicMapping, persistentAttribute.getSpecifiedMapping());
	}
	
	public void testHasMutable() throws Exception {
		createTestEntityWithMutableBasic();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		EclipseLinkBasicMapping basicMapping = (EclipseLinkBasicMapping) persistentAttribute.getSpecifiedMapping();
		Mutable mutable = basicMapping.getMutable();
		assertEquals(true, mutable.hasMutable());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		attributeResource.removeAnnotation(MutableAnnotation.ANNOTATION_NAME);
		
		assertEquals(false, mutable.hasMutable());
		
		attributeResource.addAnnotation(MutableAnnotation.ANNOTATION_NAME);
		assertEquals(true, mutable.hasMutable());
	}
	
	public void testSetMutable() throws Exception {
		createTestEntityWithMutableBasic();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		EclipseLinkBasicMapping basicMapping = (EclipseLinkBasicMapping) persistentAttribute.getSpecifiedMapping();
		Mutable mutable = basicMapping.getMutable();
		assertEquals(true, mutable.hasMutable());
		
		mutable.setMutable(false);
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.getAnnotation(MutableAnnotation.ANNOTATION_NAME));
		assertFalse(mutable.hasMutable());
		
		mutable.setMutable(true);
		assertNotNull(attributeResource.getAnnotation(MutableAnnotation.ANNOTATION_NAME));
		assertTrue(mutable.hasMutable());
	}
	
	public void testGetSpecifiedMutable() throws Exception {
		createTestEntityWithMutableBasic();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		EclipseLinkBasicMapping basicMapping = (EclipseLinkBasicMapping) persistentAttribute.getSpecifiedMapping();
		Mutable mutable = basicMapping.getMutable();
		assertEquals(null, mutable.getSpecifiedMutable());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		MutableAnnotation mutableAnnotation = (MutableAnnotation) attributeResource.getAnnotation(MutableAnnotation.ANNOTATION_NAME);
		mutableAnnotation.setValue(Boolean.TRUE);
		
		assertEquals(Boolean.TRUE, mutable.getSpecifiedMutable());

		mutableAnnotation.setValue(null);
		assertEquals(null, mutable.getSpecifiedMutable());

		mutableAnnotation.setValue(Boolean.FALSE);
		assertEquals(Boolean.FALSE, mutable.getSpecifiedMutable());
		
		attributeResource.removeAnnotation(MutableAnnotation.ANNOTATION_NAME);
		assertEquals(null, mutable.getSpecifiedMutable());
	}
	
	public void testSetSpecifiedMutable() throws Exception {
		createTestEntityWithMutableBasic();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		EclipseLinkBasicMapping basicMapping = (EclipseLinkBasicMapping) persistentAttribute.getSpecifiedMapping();
		Mutable mutable = basicMapping.getMutable();
		assertEquals(null, mutable.getSpecifiedMutable());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		MutableAnnotation mutableAnnotation = (MutableAnnotation) attributeResource.getAnnotation(MutableAnnotation.ANNOTATION_NAME);
		assertEquals(null, mutableAnnotation.getValue());
		
		mutable.setSpecifiedMutable(Boolean.TRUE);	
		assertEquals(Boolean.TRUE, mutableAnnotation.getValue());

		mutable.setSpecifiedMutable(null);
		assertEquals(null, mutableAnnotation.getValue());
		
		mutable.setSpecifiedMutable(Boolean.FALSE);	
		assertEquals(Boolean.FALSE, mutableAnnotation.getValue());
		
		mutable.setMutable(false);
		assertNull(attributeResource.getAnnotation(MutableAnnotation.ANNOTATION_NAME));
	}
	
	public void testGetDefaultMutable() throws Exception {
		createTestEntityWithMutableBasic();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		EclipseLinkBasicMapping basicMapping = (EclipseLinkBasicMapping) persistentAttribute.getSpecifiedMapping();
		Mutable mutable = basicMapping.getMutable();
		assertEquals(Boolean.TRUE, mutable.getDefaultMutable());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		attributeResource.removeAnnotation(MutableAnnotation.ANNOTATION_NAME);
		assertEquals(Boolean.TRUE, mutable.getDefaultMutable());
		
		mutable.setSpecifiedMutable(Boolean.FALSE);	
		assertEquals(Boolean.TRUE, mutable.getDefaultMutable());
	}
	
	public void testGetMutable() throws Exception {
		createTestEntityWithMutableBasic();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = javaPersistentType().attributes().next();
		EclipseLinkBasicMapping basicMapping = (EclipseLinkBasicMapping) persistentAttribute.getSpecifiedMapping();
		Mutable mutable = basicMapping.getMutable();
		assertEquals(Boolean.TRUE, mutable.getMutable());
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.attributes().next();
		attributeResource.removeAnnotation(MutableAnnotation.ANNOTATION_NAME);
		assertEquals(Boolean.TRUE, mutable.getMutable());
		
		mutable.setSpecifiedMutable(Boolean.TRUE);	
		assertEquals(Boolean.TRUE, mutable.getMutable());
	}
}
