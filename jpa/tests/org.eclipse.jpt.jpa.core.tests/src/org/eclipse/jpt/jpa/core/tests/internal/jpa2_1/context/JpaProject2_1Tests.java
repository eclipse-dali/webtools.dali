/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.jpa2_1.context;

import java.util.Iterator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.internal.operations.JptFileCreationDataModelProperties;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.internal.operations.OrmFileCreationDataModelProvider;
import org.eclipse.jpt.jpa.core.internal.operations.PersistenceFileCreationDataModelProvider;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.JPA2_1;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistence;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

@SuppressWarnings("nls")
public class JpaProject2_1Tests
	extends Generic2_1ContextModelTestCase
{

	public JpaProject2_1Tests(String name) {
		super(name);
	}

	private ICompilationUnit createTestEntity() throws Exception {
		return this.createTestType("MyEntity", new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
		});
	}

	private ICompilationUnit createTestMappedSuperclass() throws Exception {
		return this.createTestType("MyMappedSuperclass", new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.MAPPED_SUPERCLASS);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass");
			}
		});
	}

	private ICompilationUnit createTestEmbeddable() throws Exception {
		return this.createTestType("MyEmbeddable", new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.EMBEDDABLE);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Embeddable");
			}
		});
	}

	private ICompilationUnit createTestConverter() throws Exception {
		return this.createTestType("MyConverter", new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA2_1.CONVERTER);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Converter");
			}
		});
	}

	public void testGetPersistenceXmlResource() throws Exception {
		JptXmlResource resource = this.getJpaProject().getPersistenceXmlResource();
		assertNotNull(resource);
		assertEquals(XmlPersistence.CONTENT_TYPE, resource.getContentType());
		assertEquals("src/META-INF/persistence.xml", resource.getFile().getProjectRelativePath().toString());

		//delete the persistence.xml file and verify it is not returned from getPersistenceXmlResource()
		resource.delete(null);
		resource = this.getJpaProject().getPersistenceXmlResource();
		assertNull(resource);

		//add the persistence.xml file back
		createPersistenceXmlFile();
		resource = this.getJpaProject().getPersistenceXmlResource();
		assertNotNull(resource);
		assertEquals(XmlPersistence.CONTENT_TYPE, resource.getContentType());
		assertEquals("src/META-INF/persistence.xml", resource.getFile().getProjectRelativePath().toString());
	}

	private void createPersistenceXmlFile() throws Exception {
		IDataModel config =
			DataModelFactory.createDataModel(new PersistenceFileCreationDataModelProvider());
		config.setProperty(JptFileCreationDataModelProperties.CONTAINER_PATH, 
				getJpaProject().getProject().getFolder("src/META-INF").getFullPath());
		config.getDefaultOperation().execute(null, null);
	}

	public void testGetDefaultOrmXmlResource() throws Exception {
		JptXmlResource resource = this.getJpaProject().getDefaultOrmXmlResource();
		assertNotNull(resource);
		assertEquals(XmlEntityMappings.CONTENT_TYPE, resource.getContentType());
		assertEquals("src/META-INF/orm.xml", resource.getFile().getProjectRelativePath().toString());

		//delete the orm.xml file and verify it is not returned from getDefaultOrmXmlResource()
		resource.delete(null);
		resource = this.getJpaProject().getDefaultOrmXmlResource();
		assertNull(resource);

		//add the default orm.xml file back
		createDefaultOrmXmlFile();
		resource = this.getJpaProject().getDefaultOrmXmlResource();
		assertNotNull(resource);
		assertEquals(XmlEntityMappings.CONTENT_TYPE, resource.getContentType());
		assertEquals("src/META-INF/orm.xml", resource.getFile().getProjectRelativePath().toString());
	}

	private void createDefaultOrmXmlFile() throws Exception {
		IDataModel config =
			DataModelFactory.createDataModel(new OrmFileCreationDataModelProvider());
		config.setProperty(JptFileCreationDataModelProperties.CONTAINER_PATH, 
				getJpaProject().getProject().getFolder("src/META-INF").getFullPath());
		config.getDefaultOperation().execute(null, null);
	}

	private void createOrmXmlFile(String fileName) throws Exception {
		IDataModel config =
			DataModelFactory.createDataModel(new OrmFileCreationDataModelProvider());
		config.setProperty(JptFileCreationDataModelProperties.CONTAINER_PATH, 
				getJpaProject().getProject().getFolder("src/META-INF").getFullPath());
		config.setProperty(JptFileCreationDataModelProperties.FILE_NAME, fileName);
		config.getDefaultOperation().execute(null, null);
	}

	public void testGetMappingFileResource() throws Exception {
		JptXmlResource resource = this.getJpaProject().getMappingFileXmlResource(XmlEntityMappings.DEFAULT_RUNTIME_PATH);
		assertNotNull(resource);
		assertEquals(XmlEntityMappings.CONTENT_TYPE, resource.getContentType());
		assertEquals("src/META-INF/orm.xml", resource.getFile().getProjectRelativePath().toString());

		//delete the orm.xml file and verify it is not returned from getMappingFileResource()
		resource.delete(null);
		resource = this.getJpaProject().getMappingFileXmlResource(XmlEntityMappings.DEFAULT_RUNTIME_PATH);
		assertNull(resource);

		//add the  orm.xml file back
		createDefaultOrmXmlFile();
		resource = this.getJpaProject().getMappingFileXmlResource(XmlEntityMappings.DEFAULT_RUNTIME_PATH);
		assertNotNull(resource);
		assertEquals(XmlEntityMappings.CONTENT_TYPE, resource.getContentType());
		assertEquals("src/META-INF/orm.xml", resource.getFile().getProjectRelativePath().toString());
	}

	public void testGetMappingFileResourceDifferentlyName() throws Exception {
		JptXmlResource resource = this.getJpaProject().getMappingFileXmlResource(new Path("META-INF/orm2.xml"));
		assertNull(resource);

		//create the orm2.xml file
		createOrmXmlFile("orm2.xml");
		resource = this.getJpaProject().getMappingFileXmlResource(new Path("META-INF/orm2.xml"));
		assertNotNull(resource);
		assertEquals(XmlEntityMappings.CONTENT_TYPE, resource.getContentType());
		assertEquals("src/META-INF/orm2.xml", resource.getFile().getProjectRelativePath().toString());

		//delete the orm2.xml file and verify it is not returned from getMappingFileResource()
		resource.delete(null);
		resource = this.getJpaProject().getMappingFileXmlResource(new Path("META-INF/orm2.xml"));
		assertNull(resource);

		//add the orm2.xml file back
		createOrmXmlFile("orm2.xml");
		resource = this.getJpaProject().getMappingFileXmlResource(new Path("META-INF/orm2.xml"));
		assertNotNull(resource);
		assertEquals(XmlEntityMappings.CONTENT_TYPE, resource.getContentType());
		assertEquals("src/META-INF/orm2.xml", resource.getFile().getProjectRelativePath().toString());
	}

	public void testGetPotentialJavaSourceTypes() throws Exception {
		createTestEntity();
		createTestEmbeddable();
		createTestMappedSuperclass();
		createTestConverter();
		createTestType();

		Iterable<JavaResourceAbstractType> potentialJavaSourceTypes = this.getJpaProject().getPotentialJavaSourceTypes();
		assertEquals(4, IterableTools.size(potentialJavaSourceTypes));
		assertTrue(IterableTools.contains(potentialJavaSourceTypes, getJpaProject().getJavaResourceType("test.MyEntity")));
		assertTrue(IterableTools.contains(potentialJavaSourceTypes, getJpaProject().getJavaResourceType("test.MyEmbeddable")));
		assertTrue(IterableTools.contains(potentialJavaSourceTypes, getJpaProject().getJavaResourceType("test.MyMappedSuperclass")));
		assertTrue(IterableTools.contains(potentialJavaSourceTypes, getJpaProject().getJavaResourceType("test.MyConverter")));
	}
}
