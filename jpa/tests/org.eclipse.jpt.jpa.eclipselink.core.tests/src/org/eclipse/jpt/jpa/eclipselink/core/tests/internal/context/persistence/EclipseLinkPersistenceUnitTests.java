/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.persistence;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.internal.operations.JptFileCreationDataModelProperties;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.ArrayIterator;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.internal.operations.OrmFileCreationDataModelProperties;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.jpa.core.tests.internal.projects.TestJpaProject;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.internal.operations.EclipseLinkOrmFileCreationDataModelProvider;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.EclipseLinkContextModelTestCase;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

@SuppressWarnings("nls")
public class EclipseLinkPersistenceUnitTests
	extends EclipseLinkContextModelTestCase
{
	public EclipseLinkPersistenceUnitTests(String name) {
		super(name);
	}

	@Override
	protected boolean createOrmXml() {
		return true;
	}

	@Override
	protected IDataModel buildEclipseLinkOrmConfig(TestJpaProject testJpaProject) {
		IDataModel dataModel = 
			DataModelFactory.createDataModel(new EclipseLinkOrmFileCreationDataModelProvider());		
		dataModel.setProperty(JptFileCreationDataModelProperties.CONTAINER_PATH, 
				testJpaProject.getProject().getFolder("src/META-INF").getFullPath());
		dataModel.setProperty(OrmFileCreationDataModelProperties.ADD_TO_PERSISTENCE_UNIT, Boolean.FALSE);
		return dataModel;
	}

	private ICompilationUnit createTestEntity() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
		});
	}	
	
	public void testUpdateEclipseLinkImpliedMappingFileRef1() throws Exception {
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// test that there is one initially
		JptXmlResource eclipseLinkOrmResource = getOrmXmlResource();
		assertTrue(eclipseLinkOrmResource.fileExists());
		assertNotNull(persistenceUnit.getImpliedMappingFileRef());
		assertNotNull(persistenceUnit.getImpliedEclipseLinkMappingFileRef());
		
		// remove eclipselink-orm.xml
		deleteResource(eclipseLinkOrmResource);
		
		assertFalse(eclipseLinkOrmResource.fileExists());
		assertNotNull(persistenceUnit.getImpliedMappingFileRef());
		assertNull(persistenceUnit.getImpliedEclipseLinkMappingFileRef());
	}
	
	public void testUpdateEclipseLinkImpliedMappingFileRef2() {
		XmlPersistenceUnit xmlPersistenceUnit = getXmlPersistenceUnit();
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// test that there is one initially
		JptXmlResource eclipseLinkOrmResource = getOrmXmlResource();
		assertTrue(eclipseLinkOrmResource.fileExists());
		assertNotNull(persistenceUnit.getImpliedMappingFileRef());
		assertNotNull(persistenceUnit.getImpliedEclipseLinkMappingFileRef());
		
		// add specified eclipselink-orm.xml
		XmlMappingFileRef xmlMappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		xmlMappingFileRef.setFileName("META-INF/eclipselink-orm.xml");
		xmlPersistenceUnit.getMappingFiles().add(xmlMappingFileRef);
		
		assertEquals(1, IterableTools.size(persistenceUnit.getSpecifiedMappingFileRefs()));
		
		assertTrue(eclipseLinkOrmResource.fileExists());
		assertNotNull(persistenceUnit.getImpliedMappingFileRef());
		assertNull(persistenceUnit.getImpliedEclipseLinkMappingFileRef());
	}
	
	public void testUpdateEclipseLinkImpliedMappingFileRef3() {
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		
		// test that there is one initially
		JptXmlResource eclipseLinkOrmResource = getOrmXmlResource();
		assertTrue(eclipseLinkOrmResource.fileExists());
		assertNotNull(persistenceUnit.getImpliedMappingFileRef());
		assertNotNull(persistenceUnit.getImpliedEclipseLinkMappingFileRef());
		
		// ignore in persistence unit
		persistenceUnit.getGeneralProperties().setExcludeEclipselinkOrm(Boolean.TRUE);
		
		assertTrue(eclipseLinkOrmResource.fileExists());
		assertNotNull(persistenceUnit.getImpliedMappingFileRef());
		assertNull(persistenceUnit.getImpliedEclipseLinkMappingFileRef());
	}

	public void testMappingFileRefs() {
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		ListIterator<MappingFileRef> mappingFileRefs = persistenceUnit.getMappingFileRefs().iterator();
		
		assertEquals(persistenceUnit.getImpliedEclipseLinkMappingFileRef(), mappingFileRefs.next().getMappingFile().getParent());
		assertEquals(persistenceUnit.getImpliedMappingFileRef(), mappingFileRefs.next().getMappingFile().getParent());
	}

	public void testMappingFileRefsSize() {
		EclipseLinkPersistenceUnit persistenceUnit = getPersistenceUnit();
		assertEquals(2, persistenceUnit.getMappingFileRefsSize());
	}

	public void testPersistentType() throws Exception {
		getJpaProject().setDiscoversAnnotatedClasses(false);	
		PersistenceUnit persistenceUnit = getPersistenceUnit();
		createTestEntity();
		
		//persistentType not listed in persistence.xml and discoverAnnotatedClasses is false
		//still find the persistentType because of changes for bug 190317
		assertFalse(getJpaProject().discoversAnnotatedClasses());
		assertNotNull(persistenceUnit.getPersistentType(FULLY_QUALIFIED_TYPE_NAME));
		
		//test persistentType not listed in persistence.xml, discover annotated classes set to true
		getJpaProject().setDiscoversAnnotatedClasses(true);	
		assertNotNull(persistenceUnit.getPersistentType(FULLY_QUALIFIED_TYPE_NAME));
		
		//test persistentType list as class in persistence.xml
		getJpaProject().setDiscoversAnnotatedClasses(false);
		XmlJavaClassRef classRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
		classRef.setJavaClass(FULLY_QUALIFIED_TYPE_NAME);
		getXmlPersistenceUnit().getClasses().add(classRef);
		assertNotNull(persistenceUnit.getPersistentType(FULLY_QUALIFIED_TYPE_NAME));

		
		//test persistentType from orm.xml file that is specified in the persistence.xml
		addXmlMappingFileRef(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		assertNotNull(persistenceUnit.getPersistentType("model.Foo"));
		assertEquals(ormPersistentType, persistenceUnit.getPersistentType("model.Foo"));

		//test persistentType from eclipselink-orm.xml file that is implied(not specified) in the persistence.xml
		getXmlPersistenceUnit().getMappingFiles().remove(0);
		assertNotNull(persistenceUnit.getPersistentType("model.Foo"));
	}
}
