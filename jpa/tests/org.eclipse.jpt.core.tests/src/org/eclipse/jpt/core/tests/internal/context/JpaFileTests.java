/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context;

import java.util.Iterator;
import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JpaFileTests extends ContextModelTestCase
{
	public JpaFileTests(String name) {
		super(name);
	}	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
		xmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		persistenceResource().save(null);
	}
	
	private void createEntityAnnotation() throws Exception {
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
	}

	
	private ICompilationUnit createTestEntity() throws Exception {
		createEntityAnnotation();
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
		});
	}
	
	public void testGetRootStructureNode() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);

		IFile file = ormResource().getResourceModel().getFile();
		JpaFile ormXmlJpaFile = JptCorePlugin.getJpaFile(file);
		
		assertEquals(entityMappings(), ormXmlJpaFile.rootStructureNodes().next());
		
		
		ICompilationUnit cu = createTestEntity();
		JpaFile javaJpaFile = JptCorePlugin.getJpaFile((IFile) cu.getResource());
		
		assertEquals(ormPersistentType.getJavaPersistentType(), javaJpaFile.rootStructureNodes().next());
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		//verify the mapping file reference "wins" as the root structure node when both
		//persistence.xml <class> tag and mapping file <entity> tag exist for a particulary java class
		assertEquals(ormPersistentType.getJavaPersistentType(), javaJpaFile.rootStructureNodes().next());

		entityMappings().removeOrmPersistentType(ormPersistentType);

		assertEquals(javaEntity().getPersistentType(), javaJpaFile.rootStructureNodes().next());

		ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		assertEquals(ormPersistentType.getJavaPersistentType(), javaJpaFile.rootStructureNodes().next());
	}
	
	public void testEntityMappingsRootStructureNodeRemoved() throws Exception {
		IFile file = ormResource().getResourceModel().getFile();
		JpaFile ormXmlJpaFile = JptCorePlugin.getJpaFile(file);
		assertEquals(entityMappings(), ormXmlJpaFile.rootStructureNodes().next());
		
		((OrmXml) entityMappings().getParent()).removeEntityMappings();
		
		assertFalse(ormXmlJpaFile.rootStructureNodes().hasNext());
	}

	public void testImpliedEntityMappingsRootStructureNodeRemoved() throws Exception {
		IFile file = ormResource().getResourceModel().getFile();
		JpaFile ormXmlJpaFile = JptCorePlugin.getJpaFile(file);
		
		assertNull(persistenceUnit().getImpliedMappingFileRef());

		xmlPersistenceUnit().getMappingFiles().remove(0);
		assertNotNull(persistenceUnit().getImpliedMappingFileRef());
		assertEquals(persistenceUnit().getImpliedMappingFileRef().getOrmXml().getEntityMappings(), ormXmlJpaFile.rootStructureNodes().next());
				
		persistenceUnit().getImpliedMappingFileRef().getOrmXml().removeEntityMappings();		
		assertFalse(ormXmlJpaFile.rootStructureNodes().hasNext());
	}
	
	public void testEntityMappingsRootStructureNodeRemovedFromResourceModel() throws Exception {
		IFile file = ormResource().getResourceModel().getFile();
		JpaFile ormXmlJpaFile = JptCorePlugin.getJpaFile(file);
		assertEquals(entityMappings(), ormXmlJpaFile.rootStructureNodes().next());
		
		ormResource().getContents().remove(ormResource().getEntityMappings());
		
		assertFalse(ormXmlJpaFile.rootStructureNodes().hasNext());
	}

	public void testUpdatePersistenceRootStructureNodePersistenceRemoved() throws Exception {
		IFile file = persistenceResource().getResourceModel().getFile();
		JpaFile persistenceXmlJpaFile = JptCorePlugin.getJpaFile(file);
		assertEquals(rootContext().getPersistenceXml().getPersistence(), persistenceXmlJpaFile.rootStructureNodes().next());
		
		rootContext().getPersistenceXml().removePersistence();	
		assertFalse(persistenceXmlJpaFile.rootStructureNodes().hasNext());
	}
	
	public void testUpdateOrmJavaRootStructureNodePersistenceRemoved() throws Exception {		
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		ICompilationUnit cu = createTestEntity();
		JpaFile javaJpaFile = JptCorePlugin.getJpaFile((IFile) cu.getResource());
		
		assertEquals(ormPersistentType.getJavaPersistentType(), javaJpaFile.rootStructureNodes().next());
		
		rootContext().getPersistenceXml().removePersistence();
		assertFalse(javaJpaFile.rootStructureNodes().hasNext());
	}
	
	public void testUpdateJavaRootStructureNodePersistenceRemoved() throws Exception {		
		ICompilationUnit cu = createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JpaFile javaJpaFile = JptCorePlugin.getJpaFile((IFile) cu.getResource());
		
		assertEquals(javaPersistentType(), javaJpaFile.rootStructureNodes().next());
		
		rootContext().getPersistenceXml().removePersistence();
		assertFalse(javaJpaFile.rootStructureNodes().hasNext());
	}

	public void testPersistenceRootStructureNodeRemovedFromResourceModel() throws Exception {
		IFile file = persistenceResource().getResourceModel().getFile();
		JpaFile persistenceXmlJpaFile = JptCorePlugin.getJpaFile(file);
		rootContext().getPersistenceXml().getPersistence();
		assertEquals(rootContext().getPersistenceXml().getPersistence(), persistenceXmlJpaFile.rootStructureNodes().next());
		
		persistenceResource().getContents().remove(persistenceResource().getPersistence());
		
		assertFalse(persistenceXmlJpaFile.rootStructureNodes().hasNext());
	}
	
	public void testUpdatePersistenceRootStructureNodePersistenceXmlRemoved() throws Exception {
		IFile file = persistenceResource().getResourceModel().getFile();
		JpaFile persistenceXmlJpaFile = JptCorePlugin.getJpaFile(file);
		assertEquals(rootContext().getPersistenceXml().getPersistence(), persistenceXmlJpaFile.rootStructureNodes().next());
		
		rootContext().removePersistenceXml();
		assertFalse(persistenceXmlJpaFile.rootStructureNodes().hasNext());
	}
	
	public void testUpdateOrmJavaRootStructureNodePersistenceXmlRemoved() throws Exception {		
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		ICompilationUnit cu = createTestEntity();
		JpaFile javaJpaFile = JptCorePlugin.getJpaFile((IFile) cu.getResource());
		
		assertEquals(ormPersistentType.getJavaPersistentType(), javaJpaFile.rootStructureNodes().next());
		
		rootContext().removePersistenceXml();
		assertFalse(javaJpaFile.rootStructureNodes().hasNext());
	}
	
	public void testUpdateJavaRootStructureNodePersistenceXmlRemoved() throws Exception {		
		ICompilationUnit cu = createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JpaFile javaJpaFile = JptCorePlugin.getJpaFile((IFile) cu.getResource());
		
		assertEquals(javaPersistentType(), javaJpaFile.rootStructureNodes().next());
		
		rootContext().removePersistenceXml();
		assertFalse(javaJpaFile.rootStructureNodes().hasNext());
	}
	
	public void testOrmJavaPersistentTypeRootStructureNodeRemoved() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		ICompilationUnit cu = createTestEntity();
		JpaFile javaJpaFile = JptCorePlugin.getJpaFile((IFile) cu.getResource());
		JavaPersistentType javaPersistentType = ormPersistentType.getJavaPersistentType();
		assertEquals(javaPersistentType, javaJpaFile.rootStructureNodes().next());
		assertTrue(javaJpaFile.rootStructureNodes().next().getParent() instanceof OrmEntity);
		
		
		entityMappings().removeOrmPersistentType(0);
		
		assertEquals(1, javaJpaFile.rootStructureNodesSize());
		assertTrue(javaJpaFile.rootStructureNodes().next().getParent() instanceof ClassRef);
		assertNotSame(javaPersistentType, javaJpaFile.rootStructureNodes().next());
	}
	
	public void testOrmJavaPersistentTypeRootStructureNodeRemovedFromResourceModel() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		ICompilationUnit cu = createTestEntity();
		JpaFile javaJpaFile = JptCorePlugin.getJpaFile((IFile) cu.getResource());
		
		JavaPersistentType javaPersistentType = ormPersistentType.getJavaPersistentType();
		assertEquals(javaPersistentType, javaJpaFile.rootStructureNodes().next());
		assertTrue(javaJpaFile.rootStructureNodes().next().getParent() instanceof OrmEntity);
		
		ormResource().getEntityMappings().getEntities().remove(0);
		
		assertEquals(1, javaJpaFile.rootStructureNodesSize());
		assertTrue(javaJpaFile.rootStructureNodes().next().getParent() instanceof ClassRef);
		assertNotSame(javaPersistentType, javaJpaFile.rootStructureNodes().next());
	}

	public void testJavaPersistentTypeRootStructureNodeRemoved() throws Exception {
		ICompilationUnit cu = createTestEntity();
		JpaFile javaJpaFile = JptCorePlugin.getJpaFile((IFile) cu.getResource());
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentType javaPersistentType = javaPersistentType();
		assertEquals(javaPersistentType, javaJpaFile.rootStructureNodes().next());
		assertEquals(entityMappings().getPersistenceUnit().specifiedClassRefs().next(), javaJpaFile.rootStructureNodes().next().getParent());
		
		entityMappings().getPersistenceUnit().removeSpecifiedClassRef(0);
		assertNotSame(javaPersistentType, javaJpaFile.rootStructureNodes().next());
		assertEquals(entityMappings().getPersistenceUnit().impliedClassRefs().next(), javaJpaFile.rootStructureNodes().next().getParent());
	}
	
	public void testJavaPersistentTypeRootStructureNodeRemovedFromResourceModel() throws Exception {
		ICompilationUnit cu = createTestEntity();
		JpaFile javaJpaFile = JptCorePlugin.getJpaFile((IFile) cu.getResource());
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentType javaPersistentType = javaPersistentType();
		Iterator<JpaStructureNode> rootStructureNodes = javaJpaFile.rootStructureNodes();
		JpaStructureNode rootStructureNode = rootStructureNodes.next();
		assertEquals(javaPersistentType, rootStructureNode);
		assertEquals(entityMappings().getPersistenceUnit().specifiedClassRefs().next(), rootStructureNode.getParent());
		assertFalse(rootStructureNodes.hasNext());
		
		removeXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		assertNotSame(javaPersistentType, javaJpaFile.rootStructureNodes().next());
		assertEquals(entityMappings().getPersistenceUnit().impliedClassRefs().next(), javaJpaFile.rootStructureNodes().next().getParent());
	}

	public void testImpliedJavaPersistentTypeRootStructureNodeRemoved() throws Exception {
		jpaProject().setDiscoversAnnotatedClasses(true);
		ICompilationUnit cu = createTestEntity();
		JpaFile javaJpaFile = JptCorePlugin.getJpaFile((IFile) cu.getResource());
		
		JavaPersistentType javaPersistentType = persistenceUnit().impliedClassRefs().next().getJavaPersistentType();
		assertEquals(javaPersistentType, javaJpaFile.rootStructureNodes().next());
		
		javaPersistentType.setMappingKey(MappingKeys.NULL_TYPE_MAPPING_KEY);
		
		assertFalse(javaJpaFile.rootStructureNodes().hasNext());
	}
	
	public void testJavaRootStructureNodesEntityMappingsRemoved() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		ICompilationUnit cu = createTestEntity();
		JpaFile javaJpaFile = JptCorePlugin.getJpaFile((IFile) cu.getResource());
		
		JavaPersistentType javaPersistentType = ormPersistentType.getJavaPersistentType();
		assertEquals(javaPersistentType, javaJpaFile.rootStructureNodes().next());
		
		ormResource().getContents().remove(ormResource().getEntityMappings());
		assertNotSame(javaPersistentType, javaJpaFile.rootStructureNodes().next());
		assertEquals(1, javaJpaFile.rootStructureNodesSize());
		assertEquals(persistenceUnit().impliedClassRefs().next(), javaJpaFile.rootStructureNodes().next().getParent());
	}
	
	public void testJavaRootStructureNodesPersistenceUnitRemovedFromResourceModel() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		ICompilationUnit cu = createTestEntity();
		JpaFile javaJpaFile = JptCorePlugin.getJpaFile((IFile) cu.getResource());
		
		JavaPersistentType javaPersistentType = ormPersistentType.getJavaPersistentType();
		assertEquals(javaPersistentType, javaJpaFile.rootStructureNodes().next());
		
		persistenceResource().getPersistence().getPersistenceUnits().remove(0);

		assertFalse(javaJpaFile.rootStructureNodes().hasNext());
		assertEquals(0, javaJpaFile.rootStructureNodesSize());
	}
	
	public void testJavaRootStructureNodesPersistenceUnitRemoved() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		ICompilationUnit cu = createTestEntity();
		JpaFile javaJpaFile = JptCorePlugin.getJpaFile((IFile) cu.getResource());
		
		JavaPersistentType javaPersistentType = ormPersistentType.getJavaPersistentType();
		assertEquals(javaPersistentType, javaJpaFile.rootStructureNodes().next());
		
		jpaProject().getRootContext().getPersistenceXml().getPersistence().removePersistenceUnit(0);

		assertFalse(javaJpaFile.rootStructureNodes().hasNext());
		assertEquals(0, javaJpaFile.rootStructureNodesSize());
	}

	public void testJavaRootStructureNodesOrmPersistentTypeRemoved() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		ICompilationUnit cu = createTestEntity();
		JpaFile javaJpaFile = JptCorePlugin.getJpaFile((IFile) cu.getResource());
		
		JavaPersistentType javaPersistentType = ormPersistentType.getJavaPersistentType();
		assertEquals(javaPersistentType, javaJpaFile.rootStructureNodes().next());
		
		entityMappings().removeOrmPersistentType(0);
		assertNotSame(javaPersistentType, javaJpaFile.rootStructureNodes().next());
		assertEquals(1, javaJpaFile.rootStructureNodesSize());
		assertEquals(entityMappings().getPersistenceUnit().impliedClassRefs().next(), javaJpaFile.rootStructureNodes().next().getParent());
	}
	
	public void testJavaRootStructureNodesOrmTypeMappingMorphed() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		ICompilationUnit cu = createTestEntity();
		JpaFile javaJpaFile = JptCorePlugin.getJpaFile((IFile) cu.getResource());
		
		JavaPersistentType javaPersistentType = ormPersistentType.getJavaPersistentType();
		assertEquals(javaPersistentType, javaJpaFile.rootStructureNodes().next());
	
		ormPersistentType.setMappingKey(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
		assertEquals(1, javaJpaFile.rootStructureNodesSize());
		javaPersistentType = entityMappings().ormPersistentTypes().next().getJavaPersistentType();
		assertEquals(javaPersistentType, javaJpaFile.rootStructureNodes().next());
		
		entityMappings().removeOrmPersistentType(0);
		assertNotSame(javaPersistentType, javaJpaFile.rootStructureNodes().next());
		assertEquals(1, javaJpaFile.rootStructureNodesSize());
		assertEquals(entityMappings().getPersistenceUnit().impliedClassRefs().next(), javaJpaFile.rootStructureNodes().next().getParent());
	}
	
	public void testUpdateOrmJavaRootStructureNodeMappingFileRefChanged() throws Exception {		
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		ICompilationUnit cu = createTestEntity();
		JpaFile javaJpaFile = JptCorePlugin.getJpaFile((IFile) cu.getResource());
		
		assertEquals(ormPersistentType.getJavaPersistentType(), javaJpaFile.rootStructureNodes().next());
		
		MappingFileRef mappingFileRef = persistenceUnit().mappingFileRefs().next();
		mappingFileRef.setFileName("foo");
		
		ormPersistentType = persistenceUnit().getImpliedMappingFileRef().getOrmXml().getEntityMappings().ormPersistentTypes().next();
		assertEquals(ormPersistentType.getJavaPersistentType(), javaJpaFile.rootStructureNodes().next());
		
		IFile file = persistenceResource().getResourceModel().getFile();
		JpaFile ormXmlJpaFile = JptCorePlugin.getJpaFile(file);		
		
		assertEquals(1, ormXmlJpaFile.rootStructureNodesSize());
	}
	
	public void testUpdateJavaRootStructureNodeMappingFileRefChanged() throws Exception {		
		ICompilationUnit cu = createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JpaFile javaJpaFile = JptCorePlugin.getJpaFile((IFile) cu.getResource());
		
		assertEquals(javaPersistentType(), javaJpaFile.rootStructureNodes().next());
		
		MappingFileRef mappingFileRef = persistenceUnit().mappingFileRefs().next();
		mappingFileRef.setFileName("foo");
		assertEquals(javaPersistentType(), javaJpaFile.rootStructureNodes().next());
	}

	
	public void testUpdateJavaRootStrucutreNodeDeleteOrmResource() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		ICompilationUnit cu = createTestEntity();
		JpaFile javaJpaFile = JptCorePlugin.getJpaFile((IFile) cu.getResource());
		
		JavaPersistentType javaPersistentType = ormPersistentType.getJavaPersistentType();
		assertEquals(javaPersistentType, javaJpaFile.rootStructureNodes().next());

		
		deleteResource(ormResource());
		
		assertNotSame(javaPersistentType, javaJpaFile.rootStructureNodes().next());
		assertEquals(1, javaJpaFile.rootStructureNodesSize());
		assertEquals(persistenceUnit().impliedClassRefs().next(), javaJpaFile.rootStructureNodes().next().getParent());
	}
	
	//TODO test rootStructureNodes with a static inner class
}