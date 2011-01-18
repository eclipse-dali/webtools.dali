/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink1_1.core.tests.internal.context.orm;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.core.internal.facet.JpaFacetInstallDataModelProperties;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

@SuppressWarnings("nls")
public class EclipseLink1_1OrmPersistentTypeTests extends EclipseLink1_1OrmContextModelTestCase
{
	public EclipseLink1_1OrmPersistentTypeTests(String name) {
		super(name);
	}
	
	
	@Override
	protected IDataModel buildJpaConfigDataModel() {
		IDataModel dataModel = super.buildJpaConfigDataModel();
		// create both orm.xml and eclipselink-orm.xml
		dataModel.setProperty(JpaFacetInstallDataModelProperties.CREATE_ORM_XML, Boolean.TRUE);
		return dataModel;
	}
	
	private ICompilationUnit createTestEntityIdMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID, JPA.COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id");
				sb.append("@Column(name=\"FOO\")");
			}
		});
	}
//	
//	private void createAccessTypeEnum() throws Exception {
//		this.createEnumAndMembers(JPA_ANNOTATIONS_PACKAGE_NAME, "AccessType", "FIELD, PROPERTY;");	
//	}
//	
//	private void createAccessAnnotation() throws Exception {
//		this.createAnnotationAndMembers(JPA_ANNOTATIONS_PACKAGE_NAME, "Access", "AccessType value();");
//		createAccessTypeEnum();
//	}
//	
//	private ICompilationUnit createTestEntityAnnotatedFieldPropertySpecified() throws Exception {
//		createAccessAnnotation();
//		return this.createTestType(new DefaultAnnotationWriter() {
//			@Override
//			public Iterator<String> imports() {
//				return new ArrayIterator<String>(JPA.ENTITY, JPA.BASIC, JPA.ID, JPA.ACCESS, JPA.ACCESS_TYPE);
//			}
//			@Override
//			public void appendTypeAnnotationTo(StringBuilder sb) {
//				sb.append("@Entity");
//				sb.append("@Access(AccessType.PROPERTY)");
//			}
//	
//			@Override
//			public void appendNameFieldAnnotationTo(StringBuilder sb) {
//				sb.append("@Basic");
//				sb.append("@Access(AccessType.FIELD)");
//			}
//			
//			@Override
//			public void appendGetIdMethodAnnotationTo(StringBuilder sb) {
//				sb.append("@Id");
//			}
//		});
//	}
//	
//	private ICompilationUnit createTestEntityFieldSpecifiedPropertyAnnotated() throws Exception {
//		createAccessAnnotation();
//		return this.createTestType(new DefaultAnnotationWriter() {
//			@Override
//			public Iterator<String> imports() {
//				return new ArrayIterator<String>(JPA.ENTITY, JPA.BASIC, JPA.ID, JPA.ACCESS, JPA.ACCESS_TYPE);
//			}
//			@Override
//			public void appendTypeAnnotationTo(StringBuilder sb) {
//				sb.append("@Entity");
//				sb.append("@Access(AccessType.FIELD)");
//			}
//			
//			@Override
//			public void appendGetIdMethodAnnotationTo(StringBuilder sb) {
//				sb.append("@Id");
//				sb.append("@Access(AccessType.PROPERTY)");
//			}
//		});
//	}
//
//	private ICompilationUnit createTestEntityPropertySpecified() throws Exception {
//		createAccessAnnotation();
//		return this.createTestType(new DefaultAnnotationWriter() {
//			@Override
//			public Iterator<String> imports() {
//				return new ArrayIterator<String>(JPA.ENTITY, JPA.ACCESS, JPA.ACCESS_TYPE);
//			}
//			@Override
//			public void appendTypeAnnotationTo(StringBuilder sb) {
//				sb.append("@Entity");
//				sb.append("@Access(AccessType.PROPERTY)");
//			}
//		});
//	}

	public void testGetAccessWithJavaAnnotations() throws Exception {
		createTestEntityIdMapping();
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentType javaPersistentType = ormPersistentType.getJavaPersistentType();
		
		assertEquals(AccessType.FIELD, ormPersistentType.getAccess());
		assertEquals(AccessType.FIELD, javaPersistentType.getAccess());
		assertEquals(null, javaPersistentType.getSpecifiedAccess());
		
		
		OrmReadOnlyPersistentAttribute ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");
		ormPersistentAttribute.convertToSpecified();
		assertEquals(AccessType.FIELD, ormPersistentType.getAccess());
		assertEquals(AccessType.FIELD, javaPersistentType.getAccess());
		assertEquals(null, javaPersistentType.getSpecifiedAccess());
		
		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
		assertEquals(AccessType.FIELD, ormPersistentType.getAccess());
		assertEquals(AccessType.FIELD, javaPersistentType.getAccess());
	}

//TODO Access annotation test	
//	public void testGetAccessWithJavaAnnotationsProperty() throws Exception {
//		createTestEntityAnnotatedFieldPropertySpecified();
//		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		JavaPersistentType javaPersistentType = ormPersistentType.getJavaPersistentType();
//		
//		assertEquals(AccessType.PROPERTY, ormPersistentType.getAccess());
//		assertEquals(AccessType.PROPERTY, javaPersistentType.getAccess());
//		assertEquals(AccessType.PROPERTY, javaPersistentType.getSpecifiedAccess());
//		
//		
//		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.getAttributeNamed("id");
//		ormPersistentAttribute.makeSpecified();
//		assertEquals(AccessType.PROPERTY, ormPersistentType.getAccess());
//		assertEquals(AccessType.PROPERTY, javaPersistentType.getAccess());
//		assertEquals(AccessType.PROPERTY, javaPersistentType.getSpecifiedAccess());
//		
//		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
//		assertEquals(AccessType.FIELD, ormPersistentType.getAccess());
//		assertEquals(AccessType.PROPERTY, javaPersistentType.getAccess());
//	}
//	
//	public void testVirtualAttributes() throws Exception {
//		createTestEntityAnnotatedFieldPropertySpecified();
//		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		
//		ListIterator<OrmPersistentAttribute> virtualAttributes = ormPersistentType.virtualAttributes();
//		OrmPersistentAttribute ormPersistentAttribute = virtualAttributes.next();
//		assertEquals("id", ormPersistentAttribute.getName());
//		assertEquals(AccessType.PROPERTY, ormPersistentAttribute.getAccess());
//		
//		
//		ormPersistentAttribute = virtualAttributes.next();
//		assertEquals("name", ormPersistentAttribute.getName());
//		assertEquals(AccessType.FIELD, ormPersistentAttribute.getAccess());
//		
//		assertFalse(virtualAttributes.hasNext());
//		
//		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
//		virtualAttributes = ormPersistentType.virtualAttributes();
//		ormPersistentAttribute = virtualAttributes.next();
//		assertEquals("id", ormPersistentAttribute.getName());
//		assertEquals(AccessType.FIELD, ormPersistentAttribute.getAccess());
//		
//		
//		ormPersistentAttribute = virtualAttributes.next();
//		assertEquals("name", ormPersistentAttribute.getName());
//		assertEquals(AccessType.FIELD, ormPersistentAttribute.getAccess());
//		
//		assertFalse(virtualAttributes.hasNext());
//	}
//	
//	public void testVirtualAttributes2() throws Exception {
//		createTestEntityPropertySpecified();
//		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		
//		ListIterator<OrmPersistentAttribute> virtualAttributes = ormPersistentType.virtualAttributes();
//		OrmPersistentAttribute ormPersistentAttribute = virtualAttributes.next();
//		assertEquals("id", ormPersistentAttribute.getName());
//		assertEquals(AccessType.PROPERTY, ormPersistentAttribute.getAccess());
//		
//		assertFalse(virtualAttributes.hasNext());
//		
//		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
//		virtualAttributes = ormPersistentType.virtualAttributes();
//		ormPersistentAttribute = virtualAttributes.next();
//		assertEquals("id", ormPersistentAttribute.getName());
//		assertEquals(AccessType.FIELD, ormPersistentAttribute.getAccess());
//		
//		
//		ormPersistentAttribute = virtualAttributes.next();
//		assertEquals("name", ormPersistentAttribute.getName());
//		assertEquals(AccessType.FIELD, ormPersistentAttribute.getAccess());
//		
//		assertFalse(virtualAttributes.hasNext());
//		
//		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.FALSE);
//		virtualAttributes = ormPersistentType.virtualAttributes();
//		ormPersistentAttribute = virtualAttributes.next();
//		assertEquals("id", ormPersistentAttribute.getName());
//		assertEquals(AccessType.PROPERTY, ormPersistentAttribute.getAccess());
//		
//		assertFalse(virtualAttributes.hasNext());
//	}
//	
//	public void testVirtualAttributes3() throws Exception {
//		createTestEntityFieldSpecifiedPropertyAnnotated();
//		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		
//		ListIterator<OrmPersistentAttribute> virtualAttributes = ormPersistentType.virtualAttributes();
//		OrmPersistentAttribute ormPersistentAttribute = virtualAttributes.next();
//		assertEquals("id", ormPersistentAttribute.getName());
//		assertEquals(AccessType.FIELD, ormPersistentAttribute.getAccess());
//		
//		ormPersistentAttribute = virtualAttributes.next();
//		assertEquals("name", ormPersistentAttribute.getName());
//		assertEquals(AccessType.FIELD, ormPersistentAttribute.getAccess());
//
//		ormPersistentAttribute = virtualAttributes.next();
//		assertEquals("id", ormPersistentAttribute.getName());
//		assertEquals(AccessType.PROPERTY, ormPersistentAttribute.getAccess());
//
//		assertFalse(virtualAttributes.hasNext());
//		
//		
//		ormPersistentAttribute.makeSpecified();
//		
//		virtualAttributes = ormPersistentType.virtualAttributes();
//		ormPersistentAttribute = virtualAttributes.next();
//		assertEquals("id", ormPersistentAttribute.getName());
//		assertEquals(AccessType.FIELD, ormPersistentAttribute.getAccess());
//		
//		ormPersistentAttribute = virtualAttributes.next();
//		assertEquals("name", ormPersistentAttribute.getName());
//		assertEquals(AccessType.FIELD, ormPersistentAttribute.getAccess());
//
//		assertFalse(virtualAttributes.hasNext());
//		
//		
//		OrmPersistentAttribute specifiedPersistentAttribute = ormPersistentType.specifiedAttributes().next();
//		assertEquals("id", specifiedPersistentAttribute.getName());
//		assertEquals(AccessType.PROPERTY, specifiedPersistentAttribute.getSpecifiedAccess());
//		
//		
//		ormPersistentType.virtualAttributes().next().makeSpecified();
//		virtualAttributes = ormPersistentType.virtualAttributes();
//		ormPersistentAttribute = virtualAttributes.next();
//		assertEquals("name", ormPersistentAttribute.getName());
//		assertEquals(AccessType.FIELD, ormPersistentAttribute.getAccess());
//
//		assertFalse(virtualAttributes.hasNext());
//	}
//
//	public void testGetDefaultAccess() throws Exception {
//		createTestEntityPropertySpecified();
//		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
//		JavaPersistentType javaPersistentType = ormPersistentType.getJavaPersistentType();
//		
//		assertEquals(AccessType.PROPERTY, ormPersistentType.getDefaultAccess());
//		
//		javaPersistentType.setSpecifiedAccess(AccessType.FIELD);
//		assertEquals(AccessType.FIELD, ormPersistentType.getDefaultAccess());
//		
//		javaPersistentType.setSpecifiedAccess(AccessType.PROPERTY);
//		assertEquals(AccessType.PROPERTY, ormPersistentType.getDefaultAccess());
//
//		
//		ormPersistentType.getMapping().setSpecifiedMetadataComplete(Boolean.TRUE);
//		assertEquals(AccessType.FIELD, ormPersistentType.getDefaultAccess());
//	}

}