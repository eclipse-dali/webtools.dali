/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context;

import java.util.Iterator;
import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.common.utility.internal.iterator.ArrayIterator;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaNode;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;

@SuppressWarnings("nls")
public class JpaFileTests
	extends ContextModelTestCase
{
	public JpaFileTests(String name) {
		super(name);
	}	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		getPersistenceXmlResource().save(null);
	}
	
	private JpaFile getJpaFile(ICompilationUnit cu) {
		return this.getJpaFile((IFile) cu.getResource());
	}
	
	private JpaFile getJpaFile(IFile file) {
		try {
			return this.getJpaFile_(file);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
			throw new RuntimeException(ex);
		}
	}
	
	private JpaFile getJpaFile_(IFile file) throws InterruptedException {
		return this.getJpaFileReference(file).getValue();
	}
	
	private JpaFile.Reference getJpaFileReference(IFile file) {
		return (JpaFile.Reference) file.getAdapter(JpaFile.Reference.class);
	}
	
	private ICompilationUnit createTestEntity() throws Exception {
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
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);

		IFile file = getOrmXmlResource().getFile();
		JpaFile ormXmlJpaFile = this.getJpaFile(file);
		
		assertEquals(getEntityMappings(), ormXmlJpaFile.getRootStructureNodes().iterator().next());
		
		
		ICompilationUnit cu = createTestEntity();
		JpaFile javaJpaFile = this.getJpaFile(cu);
		
		assertEquals(ormPersistentType.getJavaPersistentType(), javaJpaFile.getRootStructureNodes().iterator().next());
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		//verify the mapping file reference "wins" as the root structure node when both
		//persistence.xml <class> tag and mapping file <entity> tag exist for a particulary java class
		assertEquals(ormPersistentType.getJavaPersistentType(), javaJpaFile.getRootStructureNodes().iterator().next());

		getEntityMappings().removePersistentType(ormPersistentType);

		assertEquals(getJavaEntity().getPersistentType(), javaJpaFile.getRootStructureNodes().iterator().next());

		ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		assertEquals(ormPersistentType.getJavaPersistentType(), javaJpaFile.getRootStructureNodes().iterator().next());
	}
	
	public void testEntityMappingsRootStructureNodeRemoved() throws Exception {
		IFile file = getOrmXmlResource().getFile();
		JpaFile ormXmlJpaFile = this.getJpaFile(file);
		assertEquals(getEntityMappings(), ormXmlJpaFile.getRootStructureNodes().iterator().next());
		
		JptXmlResource resource = (JptXmlResource) ormXmlJpaFile.getResourceModel();
		resource.getContents().remove(resource.getRootObject());
		
		assertFalse(ormXmlJpaFile.getRootStructureNodes().iterator().hasNext());
	}

	public void testImpliedEntityMappingsRootStructureNodeRemoved() throws Exception {
		IFile file = getOrmXmlResource().getFile();
		JpaFile ormXmlJpaFile = this.getJpaFile(file);
		
		assertNull(getPersistenceUnit().getImpliedMappingFileRef());
		
		getXmlPersistenceUnit().getMappingFiles().remove(0);
		assertNotNull(getPersistenceUnit().getImpliedMappingFileRef());
		assertEquals(getPersistenceUnit().getImpliedMappingFileRef().getMappingFile().getRoot(), ormXmlJpaFile.getRootStructureNodes().iterator().next());
		
		JptXmlResource resource = (JptXmlResource) ormXmlJpaFile.getResourceModel();
		resource.getContents().remove(resource.getRootObject());
		
		assertFalse(ormXmlJpaFile.getRootStructureNodes().iterator().hasNext());
	}
	
	public void testEntityMappingsRootStructureNodeRemovedFromResourceModel() throws Exception {
		IFile file = getOrmXmlResource().getFile();
		JpaFile ormXmlJpaFile = this.getJpaFile(file);
		assertEquals(getEntityMappings(), ormXmlJpaFile.getRootStructureNodes().iterator().next());
		
		getOrmXmlResource().getContents().remove(getOrmXmlResource().getRootObject());
		
		assertFalse(ormXmlJpaFile.getRootStructureNodes().iterator().hasNext());
	}

	public void testUpdatePersistenceRootStructureNodePersistenceRemoved() throws Exception {
		IFile file = getPersistenceXmlResource().getFile();
		JpaFile persistenceXmlJpaFile = this.getJpaFile(file);
		assertEquals(getRootContextNode().getPersistenceXml().getRoot(), persistenceXmlJpaFile.getRootStructureNodes().iterator().next());
		
		JptXmlResource resource = (JptXmlResource) persistenceXmlJpaFile.getResourceModel();
		resource.getContents().remove(resource.getRootObject());
		
		assertFalse(persistenceXmlJpaFile.getRootStructureNodes().iterator().hasNext());
	}
	
	public void testUpdateOrmJavaRootStructureNodePersistenceRemoved() throws Exception {		
		IFile file = getPersistenceXmlResource().getFile();
		JpaFile persistenceXmlJpaFile = this.getJpaFile(file);
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		ICompilationUnit cu = createTestEntity();
		JpaFile javaJpaFile = this.getJpaFile(cu);
		
		assertEquals(ormPersistentType.getJavaPersistentType(), javaJpaFile.getRootStructureNodes().iterator().next());
		
		JptXmlResource resource = (JptXmlResource) persistenceXmlJpaFile.getResourceModel();
		resource.getContents().remove(resource.getRootObject());
		
		assertFalse(javaJpaFile.getRootStructureNodes().iterator().hasNext());
	}
	
	public void testUpdateJavaRootStructureNodePersistenceRemoved() throws Exception {		
		IFile file = getPersistenceXmlResource().getFile();
		JpaFile persistenceXmlJpaFile = this.getJpaFile(file);
		ICompilationUnit cu = createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JpaFile javaJpaFile = this.getJpaFile(cu);
		
		assertEquals(getJavaPersistentType(), javaJpaFile.getRootStructureNodes().iterator().next());
		
		JptXmlResource resource = (JptXmlResource) persistenceXmlJpaFile.getResourceModel();
		resource.getContents().remove(resource.getRootObject());
		
		assertFalse(javaJpaFile.getRootStructureNodes().iterator().hasNext());
	}

	public void testPersistenceRootStructureNodeRemovedFromResourceModel() throws Exception {
		IFile file = getPersistenceXmlResource().getFile();
		JpaFile persistenceXmlJpaFile = this.getJpaFile(file);
		getRootContextNode().getPersistenceXml().getRoot();
		assertEquals(getRootContextNode().getPersistenceXml().getRoot(), persistenceXmlJpaFile.getRootStructureNodes().iterator().next());
		
		getPersistenceXmlResource().getContents().remove(getXmlPersistence());
		
		assertFalse(persistenceXmlJpaFile.getRootStructureNodes().iterator().hasNext());
	}
	
	public void testUpdateOrmJavaRootStructureNodePersistenceXmlRemoved() throws Exception {		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		ICompilationUnit cu = createTestEntity();
		JpaFile javaJpaFile = this.getJpaFile(cu);
		
		assertEquals(ormPersistentType.getJavaPersistentType(), javaJpaFile.getRootStructureNodes().iterator().next());
		
		getPersistenceXmlResource().getContents().remove(getXmlPersistence());
		assertFalse(javaJpaFile.getRootStructureNodes().iterator().hasNext());
	}
	
	public void testUpdateJavaRootStructureNodePersistenceXmlRemoved() throws Exception {		
		ICompilationUnit cu = createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JpaFile javaJpaFile = this.getJpaFile(cu);
		
		assertEquals(getJavaPersistentType(), javaJpaFile.getRootStructureNodes().iterator().next());
		
		getPersistenceXmlResource().getContents().remove(getXmlPersistence());
		assertFalse(javaJpaFile.getRootStructureNodes().iterator().hasNext());
	}
	
	public void testOrmJavaPersistentTypeRootStructureNodeRemoved() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		ICompilationUnit cu = createTestEntity();
		JpaFile javaJpaFile = this.getJpaFile(cu);
		JavaPersistentType javaPersistentType = ormPersistentType.getJavaPersistentType();
		assertEquals(javaPersistentType, javaJpaFile.getRootStructureNodes().iterator().next());
		JpaNode node = (JpaNode) javaJpaFile.getRootStructureNodes().iterator().next();
		assertTrue(node.getParent() instanceof OrmPersistentType);
		
		
		getEntityMappings().removePersistentType(0);
		
		assertEquals(1, javaJpaFile.getRootStructureNodesSize());
		node = (JpaNode) javaJpaFile.getRootStructureNodes().iterator().next();
		assertTrue(node.getParent() instanceof ClassRef);
		assertNotSame(javaPersistentType, javaJpaFile.getRootStructureNodes().iterator().next());
	}
	
	public void testOrmJavaPersistentTypeRootStructureNodeRemovedFromResourceModel() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		ICompilationUnit cu = createTestEntity();
		JpaFile javaJpaFile = this.getJpaFile(cu);
		
		JavaPersistentType javaPersistentType = ormPersistentType.getJavaPersistentType();
		assertEquals(javaPersistentType, javaJpaFile.getRootStructureNodes().iterator().next());
		JpaNode node = (JpaNode) javaJpaFile.getRootStructureNodes().iterator().next();
		assertTrue(node.getParent() instanceof OrmPersistentType);
		
		getXmlEntityMappings().getEntities().remove(0);
		
		assertEquals(1, javaJpaFile.getRootStructureNodesSize());
		node = (JpaNode) javaJpaFile.getRootStructureNodes().iterator().next();
		assertTrue(node.getParent() instanceof ClassRef);
		assertNotSame(javaPersistentType, javaJpaFile.getRootStructureNodes().iterator().next());
	}

	public void testJavaPersistentTypeRootStructureNodeRemoved() throws Exception {
		ICompilationUnit cu = createTestEntity();
		JpaFile javaJpaFile = this.getJpaFile(cu);
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentType javaPersistentType = getJavaPersistentType();
		assertEquals(javaPersistentType, javaJpaFile.getRootStructureNodes().iterator().next());
		JpaNode node = (JpaNode) javaJpaFile.getRootStructureNodes().iterator().next();
		assertEquals(getEntityMappings().getPersistenceUnit().getSpecifiedClassRefs().iterator().next(), node.getParent());
		
		getEntityMappings().getPersistenceUnit().removeSpecifiedClassRef(0);
		assertNotSame(javaPersistentType, javaJpaFile.getRootStructureNodes().iterator().next());
		node = (JpaNode) javaJpaFile.getRootStructureNodes().iterator().next();
		assertEquals(getEntityMappings().getPersistenceUnit().getImpliedClassRefs().iterator().next(), node.getParent());
	}
	
	public void testJavaPersistentTypeRootStructureNodeRemovedFromResourceModel() throws Exception {
		ICompilationUnit cu = createTestEntity();
		JpaFile javaJpaFile = this.getJpaFile(cu);
		
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentType javaPersistentType = getJavaPersistentType();
		Iterator<JpaStructureNode> rootStructureNodes = javaJpaFile.getRootStructureNodes().iterator();
		JpaStructureNode rootStructureNode = rootStructureNodes.next();
		assertEquals(javaPersistentType, rootStructureNode);
		assertEquals(getEntityMappings().getPersistenceUnit().getSpecifiedClassRefs().iterator().next(), ((JpaNode) rootStructureNode).getParent());
		assertFalse(rootStructureNodes.hasNext());
		
		removeXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		assertNotSame(javaPersistentType, javaJpaFile.getRootStructureNodes().iterator().next());
		JpaNode node = (JpaNode) javaJpaFile.getRootStructureNodes().iterator().next();
		assertEquals(getEntityMappings().getPersistenceUnit().getImpliedClassRefs().iterator().next(), node.getParent());
	}

	public void testImpliedJavaPersistentTypeRootStructureNodeRemoved() throws Exception {
		getJpaProject().setDiscoversAnnotatedClasses(true);
		ICompilationUnit cu = createTestEntity();
		JpaFile javaJpaFile = this.getJpaFile(cu);
		
		JavaPersistentType javaPersistentType = getPersistenceUnit().getImpliedClassRefs().iterator().next().getJavaPersistentType();
		assertEquals(javaPersistentType, javaJpaFile.getRootStructureNodes().iterator().next());
		
		javaPersistentType.setMappingKey(MappingKeys.NULL_TYPE_MAPPING_KEY);
		
		assertFalse(javaJpaFile.getRootStructureNodes().iterator().hasNext());
	}
	
	public void testJavaRootStructureNodesEntityMappingsRemoved() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		ICompilationUnit cu = createTestEntity();
		JpaFile javaJpaFile = this.getJpaFile(cu);
		
		JavaPersistentType javaPersistentType = ormPersistentType.getJavaPersistentType();
		assertEquals(javaPersistentType, javaJpaFile.getRootStructureNodes().iterator().next());
		
		getOrmXmlResource().getContents().remove(getXmlEntityMappings());
		assertNotSame(javaPersistentType, javaJpaFile.getRootStructureNodes().iterator().next());
		assertEquals(1, javaJpaFile.getRootStructureNodesSize());
		JpaNode node = (JpaNode) javaJpaFile.getRootStructureNodes().iterator().next();
		assertEquals(getPersistenceUnit().getImpliedClassRefs().iterator().next(), node.getParent());
	}
	
	public void testJavaRootStructureNodesPersistenceUnitRemovedFromResourceModel() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		ICompilationUnit cu = createTestEntity();
		JpaFile javaJpaFile = this.getJpaFile(cu);
		
		JavaPersistentType javaPersistentType = ormPersistentType.getJavaPersistentType();
		assertEquals(javaPersistentType, javaJpaFile.getRootStructureNodes().iterator().next());
		
		getXmlPersistence().getPersistenceUnits().remove(0);

		assertFalse(javaJpaFile.getRootStructureNodes().iterator().hasNext());
		assertEquals(0, javaJpaFile.getRootStructureNodesSize());
	}
	
	public void testJavaRootStructureNodesPersistenceUnitRemoved() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		ICompilationUnit cu = createTestEntity();
		JpaFile javaJpaFile = this.getJpaFile(cu);
		
		JavaPersistentType javaPersistentType = ormPersistentType.getJavaPersistentType();
		assertEquals(javaPersistentType, javaJpaFile.getRootStructureNodes().iterator().next());
		
		getJpaProject().getRootContextNode().getPersistenceXml().getRoot().removePersistenceUnit(0);

		assertFalse(javaJpaFile.getRootStructureNodes().iterator().hasNext());
		assertEquals(0, javaJpaFile.getRootStructureNodesSize());
	}

	public void testJavaRootStructureNodesOrmPersistentTypeRemoved() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		ICompilationUnit cu = createTestEntity();
		JpaFile javaJpaFile = this.getJpaFile(cu);
		
		JavaPersistentType javaPersistentType = ormPersistentType.getJavaPersistentType();
		assertEquals(javaPersistentType, javaJpaFile.getRootStructureNodes().iterator().next());
		
		getEntityMappings().removePersistentType(0);
		assertNotSame(javaPersistentType, javaJpaFile.getRootStructureNodes().iterator().next());
		assertEquals(1, javaJpaFile.getRootStructureNodesSize());
		JpaNode node = (JpaNode) javaJpaFile.getRootStructureNodes().iterator().next();
		assertEquals(getEntityMappings().getPersistenceUnit().getImpliedClassRefs().iterator().next(), node.getParent());
	}
	
	public void testJavaRootStructureNodesOrmTypeMappingMorphed() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		ICompilationUnit cu = createTestEntity();
		JpaFile javaJpaFile = this.getJpaFile(cu);
		
		JavaPersistentType javaPersistentType = ormPersistentType.getJavaPersistentType();
		assertEquals(javaPersistentType, javaJpaFile.getRootStructureNodes().iterator().next());
	
		ormPersistentType.setMappingKey(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
		assertEquals(1, javaJpaFile.getRootStructureNodesSize());
		javaPersistentType = getEntityMappings().getPersistentTypes().iterator().next().getJavaPersistentType();
		assertEquals(javaPersistentType, javaJpaFile.getRootStructureNodes().iterator().next());
		
		getEntityMappings().removePersistentType(0);
		assertNotSame(javaPersistentType, javaJpaFile.getRootStructureNodes().iterator().next());
		assertEquals(1, javaJpaFile.getRootStructureNodesSize());
		JpaNode node = (JpaNode) javaJpaFile.getRootStructureNodes().iterator().next();
		assertEquals(getEntityMappings().getPersistenceUnit().getImpliedClassRefs().iterator().next(), node.getParent());
	}
	
	public void testUpdateOrmJavaRootStructureNodeMappingFileRefChanged() throws Exception {		
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		ICompilationUnit cu = createTestEntity();
		JpaFile javaJpaFile = this.getJpaFile(cu);
		
		assertEquals(ormPersistentType.getJavaPersistentType(), javaJpaFile.getRootStructureNodes().iterator().next());
		
		MappingFileRef mappingFileRef = getPersistenceUnit().getMappingFileRefs().iterator().next();
		mappingFileRef.setFileName("foo");
		
		ormPersistentType = ((EntityMappings) getPersistenceUnit().getImpliedMappingFileRef().getMappingFile().getRoot()).getPersistentTypes().iterator().next();
		assertEquals(ormPersistentType.getJavaPersistentType(), javaJpaFile.getRootStructureNodes().iterator().next());
		
		IFile file = getPersistenceXmlResource().getFile();
		JpaFile ormXmlJpaFile = this.getJpaFile(file);		
		
		assertEquals(1, ormXmlJpaFile.getRootStructureNodesSize());
	}
	
	public void testUpdateJavaRootStructureNodeMappingFileRefChanged() throws Exception {		
		ICompilationUnit cu = createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JpaFile javaJpaFile = this.getJpaFile(cu);
		
		assertEquals(getJavaPersistentType(), javaJpaFile.getRootStructureNodes().iterator().next());
		
		MappingFileRef mappingFileRef = getPersistenceUnit().getMappingFileRefs().iterator().next();
		mappingFileRef.setFileName("foo");
		assertEquals(getJavaPersistentType(), javaJpaFile.getRootStructureNodes().iterator().next());
	}

	
	public void testUpdateJavaRootStrucutreNodeDeleteOrmResource() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		
		ICompilationUnit cu = createTestEntity();
		JpaFile javaJpaFile = this.getJpaFile(cu);
		
		JavaPersistentType javaPersistentType = ormPersistentType.getJavaPersistentType();
		assertEquals(javaPersistentType, javaJpaFile.getRootStructureNodes().iterator().next());

		
		deleteResource(getOrmXmlResource());
		
		assertNotSame(javaPersistentType, javaJpaFile.getRootStructureNodes().iterator().next());
		assertEquals(1, javaJpaFile.getRootStructureNodesSize());
		JpaNode node = (JpaNode) javaJpaFile.getRootStructureNodes().iterator().next();
		assertEquals(getPersistenceUnit().getImpliedClassRefs().iterator().next(), node.getParent());
	}
	//TODO test rootStructureNodes with a static inner class
}