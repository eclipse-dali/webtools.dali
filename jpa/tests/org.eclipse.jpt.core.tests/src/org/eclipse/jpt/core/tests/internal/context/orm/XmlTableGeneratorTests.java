/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context.orm;

import java.util.Iterator;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.context.base.ITableGenerator;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.orm.TableGenerator;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class XmlTableGeneratorTests extends ContextModelTestCase
{
	public XmlTableGeneratorTests(String name) {
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
	
	private void createIdAnnotation() throws Exception {
		this.createAnnotationAndMembers("Id", "");		
	}
	
	private IType createTestEntity() throws Exception {
		createEntityAnnotation();
		createIdAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id");
			}
		});
	}
	
	private IType createTestSubType() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ");
					sb.append(JPA.ENTITY);
					sb.append(";");
					sb.append(CR);
				sb.append("@Entity");
				sb.append(CR);
				sb.append("public class ").append("AnnotationTestTypeChild").append(" ");
				sb.append("extends " + TYPE_NAME + " ");
				sb.append("{}").append(CR);
			}
		};
		return this.javaProject.createType(PACKAGE_NAME, "AnnotationTestTypeChild.java", sourceWriter);
	}
	
	public void testUpdateSpecifiedName() throws Exception {
		ITableGenerator tableGenerator = entityMappings().addTableGenerator(0);		
		TableGenerator tableGeneratorResource = ormResource().getEntityMappings().getTableGenerators().get(0);
		
		//set name in the resource model, verify context model updated
		tableGeneratorResource.setName("FOO");
		assertEquals("FOO", tableGenerator.getName());
		assertEquals("FOO", tableGeneratorResource.getName());
	
		//set name to null in the resource model
		tableGeneratorResource.setName(null);
		assertNull(tableGenerator.getName());
		assertNull(tableGeneratorResource.getName());
	}
	
	public void testModifySpecifiedName() throws Exception {
		ITableGenerator tableGenerator = entityMappings().addTableGenerator(0);		
		TableGenerator tableGeneratorResource = ormResource().getEntityMappings().getTableGenerators().get(0);
		
		//set name in the context model, verify resource model modified
		tableGenerator.setName("FOO");
		assertEquals("FOO", tableGeneratorResource.getName());
		assertEquals("FOO", tableGenerator.getName());
		
		//set name to null in the context model
		tableGenerator.setName(null);
		assertNull(tableGeneratorResource.getName());
		assertNull(tableGenerator.getName());
	}

	public void testUpdateSpecifiedInitialValue() throws Exception {
		ITableGenerator tableGenerator = entityMappings().addTableGenerator(0);		
		TableGenerator tableGeneratorResource = ormResource().getEntityMappings().getTableGenerators().get(0);
		
		//set initial value in the resource model, verify context model updated
		tableGeneratorResource.setInitialValue(Integer.valueOf(10));
		assertEquals(Integer.valueOf(10), tableGenerator.getSpecifiedInitialValue());
		assertEquals(Integer.valueOf(10), tableGeneratorResource.getInitialValue());
	
		//set initial value to 1, which happens to be the default, in the resource model
		tableGeneratorResource.setInitialValue(Integer.valueOf(1));
		assertEquals(Integer.valueOf(1), tableGenerator.getSpecifiedInitialValue());
		assertEquals(Integer.valueOf(1), tableGeneratorResource.getInitialValue());
	
		//set initial value to null in the resource model
		tableGeneratorResource.setInitialValue(null);
		assertNull(tableGenerator.getSpecifiedInitialValue());
		assertNull(tableGeneratorResource.getInitialValue());
	}
	
	public void testModifySpecifiedInitialValue() throws Exception {
		ITableGenerator tableGenerator = entityMappings().addTableGenerator(0);		
		TableGenerator tableGeneratorResource = ormResource().getEntityMappings().getTableGenerators().get(0);
		
		//set initial value in the context model, verify resource model modified
		tableGenerator.setSpecifiedInitialValue(Integer.valueOf(10));
		assertEquals(Integer.valueOf(10), tableGeneratorResource.getInitialValue());
		assertEquals(Integer.valueOf(10), tableGenerator.getSpecifiedInitialValue());
		
		tableGenerator.setSpecifiedInitialValue(Integer.valueOf(1));
		assertEquals(Integer.valueOf(1), tableGeneratorResource.getInitialValue());
		assertEquals(Integer.valueOf(1), tableGenerator.getSpecifiedInitialValue());

		//set initial value to null in the context model
		tableGenerator.setSpecifiedInitialValue(null);
		assertNull(tableGeneratorResource.getInitialValue());
		assertNull(tableGenerator.getSpecifiedInitialValue());
	}
	
	public void testUpdateSpecifiedAllocationSize() throws Exception {
		ITableGenerator tableGenerator = entityMappings().addTableGenerator(0);		
		TableGenerator tableGeneratorResource = ormResource().getEntityMappings().getTableGenerators().get(0);
		
		//set allocation size in the resource model, verify context model updated
		tableGeneratorResource.setAllocationSize(Integer.valueOf(10));
		assertEquals(Integer.valueOf(10), tableGenerator.getSpecifiedAllocationSize());
		assertEquals(Integer.valueOf(10), tableGeneratorResource.getAllocationSize());
	
		//set allocation size to 50, which happens to be the default, in the resource model
		tableGeneratorResource.setAllocationSize(Integer.valueOf(1));
		assertEquals(Integer.valueOf(1), tableGenerator.getSpecifiedAllocationSize());
		assertEquals(Integer.valueOf(1), tableGeneratorResource.getAllocationSize());
	
		//set allocation size to null in the resource model
		tableGeneratorResource.setAllocationSize(null);
		assertNull(tableGenerator.getSpecifiedAllocationSize());
		assertNull(tableGeneratorResource.getAllocationSize());
	}
	
	public void testModifySpecifiedAllocationSize() throws Exception {
		ITableGenerator tableGenerator = entityMappings().addTableGenerator(0);		
		TableGenerator tableGeneratorResource = ormResource().getEntityMappings().getTableGenerators().get(0);
		
		//set allocation size in the context model, verify resource model modified
		tableGenerator.setSpecifiedAllocationSize(Integer.valueOf(10));
		assertEquals(Integer.valueOf(10), tableGeneratorResource.getAllocationSize());
		assertEquals(Integer.valueOf(10), tableGenerator.getSpecifiedAllocationSize());
		
		tableGenerator.setSpecifiedAllocationSize(Integer.valueOf(50));
		assertEquals(Integer.valueOf(50), tableGeneratorResource.getAllocationSize());
		assertEquals(Integer.valueOf(50), tableGenerator.getSpecifiedAllocationSize());

		//set allocation size to null in the context model
		tableGenerator.setSpecifiedAllocationSize(null);
		assertNull(tableGeneratorResource.getAllocationSize());
		assertNull(tableGenerator.getSpecifiedAllocationSize());
	}
	
	public void testUpdateSpecifiedTable() throws Exception {
		ITableGenerator tableGenerator = entityMappings().addTableGenerator(0);		
		TableGenerator tableGeneratorResource = ormResource().getEntityMappings().getTableGenerators().get(0);
		
		//set name in the resource model, verify context model updated
		tableGeneratorResource.setTable("FOO");
		assertEquals("FOO", tableGenerator.getSpecifiedTable());
		assertEquals("FOO", tableGeneratorResource.getTable());
	
		//set name to null in the resource model
		tableGeneratorResource.setTable(null);
		assertNull(tableGenerator.getSpecifiedTable());
		assertNull(tableGeneratorResource.getTable());
	}
	
	public void testModifySpecifiedTable() throws Exception {
		ITableGenerator tableGenerator = entityMappings().addTableGenerator(0);		
		TableGenerator tableGeneratorResource = ormResource().getEntityMappings().getTableGenerators().get(0);
		
		//set name in the context model, verify resource model modified
		tableGenerator.setSpecifiedTable("FOO");
		assertEquals("FOO", tableGeneratorResource.getTable());
		assertEquals("FOO", tableGenerator.getSpecifiedTable());
		
		//set name to null in the context model
		tableGenerator.setSpecifiedTable(null);
		assertNull(tableGeneratorResource.getTable());
		assertNull(tableGenerator.getSpecifiedTable());
	}

	public void testUpdateSpecifiedSchema() throws Exception {
		ITableGenerator tableGenerator = entityMappings().addTableGenerator(0);		
		TableGenerator tableGeneratorResource = ormResource().getEntityMappings().getTableGenerators().get(0);
		
		//set name in the resource model, verify context model updated
		tableGeneratorResource.setSchema("FOO");
		assertEquals("FOO", tableGenerator.getSpecifiedSchema());
		assertEquals("FOO", tableGeneratorResource.getSchema());
	
		//set name to null in the resource model
		tableGeneratorResource.setSchema(null);
		assertNull(tableGenerator.getSpecifiedSchema());
		assertNull(tableGeneratorResource.getSchema());
	}
	
	public void testModifySpecifiedSchema() throws Exception {
		ITableGenerator tableGenerator = entityMappings().addTableGenerator(0);		
		TableGenerator tableGeneratorResource = ormResource().getEntityMappings().getTableGenerators().get(0);
		
		//set name in the context model, verify resource model modified
		tableGenerator.setSpecifiedSchema("FOO");
		assertEquals("FOO", tableGeneratorResource.getSchema());
		assertEquals("FOO", tableGenerator.getSpecifiedSchema());
		
		//set name to null in the context model
		tableGenerator.setSpecifiedSchema(null);
		assertNull(tableGeneratorResource.getSchema());
		assertNull(tableGenerator.getSpecifiedSchema());
	}
	
	public void testUpdateSpecifiedCatalog() throws Exception {
		ITableGenerator tableGenerator = entityMappings().addTableGenerator(0);		
		TableGenerator tableGeneratorResource = ormResource().getEntityMappings().getTableGenerators().get(0);
		
		//set name in the resource model, verify context model updated
		tableGeneratorResource.setCatalog("FOO");
		assertEquals("FOO", tableGenerator.getSpecifiedCatalog());
		assertEquals("FOO", tableGeneratorResource.getCatalog());
	
		//set name to null in the resource model
		tableGeneratorResource.setCatalog(null);
		assertNull(tableGenerator.getSpecifiedCatalog());
		assertNull(tableGeneratorResource.getCatalog());
	}
	
	public void testModifySpecifiedCatalog() throws Exception {
		ITableGenerator tableGenerator = entityMappings().addTableGenerator(0);		
		TableGenerator tableGeneratorResource = ormResource().getEntityMappings().getTableGenerators().get(0);
		
		//set name in the context model, verify resource model modified
		tableGenerator.setSpecifiedCatalog("FOO");
		assertEquals("FOO", tableGeneratorResource.getCatalog());
		assertEquals("FOO", tableGenerator.getSpecifiedCatalog());
		
		//set name to null in the context model
		tableGenerator.setSpecifiedCatalog(null);
		assertNull(tableGeneratorResource.getCatalog());
		assertNull(tableGenerator.getSpecifiedCatalog());
	}
	
	public void testUpdateSpecifiedPkColumnName() throws Exception {
		ITableGenerator tableGenerator = entityMappings().addTableGenerator(0);		
		TableGenerator tableGeneratorResource = ormResource().getEntityMappings().getTableGenerators().get(0);
		
		//set name in the resource model, verify context model updated
		tableGeneratorResource.setPkColumnName("FOO");
		assertEquals("FOO", tableGenerator.getSpecifiedPkColumnName());
		assertEquals("FOO", tableGeneratorResource.getPkColumnName());
	
		//set name to null in the resource model
		tableGeneratorResource.setPkColumnName(null);
		assertNull(tableGenerator.getSpecifiedPkColumnName());
		assertNull(tableGeneratorResource.getPkColumnName());
	}
	
	public void testModifySpecifiedPkColumnName() throws Exception {
		ITableGenerator tableGenerator = entityMappings().addTableGenerator(0);		
		TableGenerator tableGeneratorResource = ormResource().getEntityMappings().getTableGenerators().get(0);
		
		//set name in the context model, verify resource model modified
		tableGenerator.setSpecifiedPkColumnName("FOO");
		assertEquals("FOO", tableGeneratorResource.getPkColumnName());
		assertEquals("FOO", tableGenerator.getSpecifiedPkColumnName());
		
		//set name to null in the context model
		tableGenerator.setSpecifiedPkColumnName(null);
		assertNull(tableGeneratorResource.getPkColumnName());
		assertNull(tableGenerator.getSpecifiedPkColumnName());
	}
	
	public void testUpdateSpecifiedValueColumnName() throws Exception {
		ITableGenerator tableGenerator = entityMappings().addTableGenerator(0);		
		TableGenerator tableGeneratorResource = ormResource().getEntityMappings().getTableGenerators().get(0);
		
		//set name in the resource model, verify context model updated
		tableGeneratorResource.setValueColumnName("FOO");
		assertEquals("FOO", tableGenerator.getSpecifiedValueColumnName());
		assertEquals("FOO", tableGeneratorResource.getValueColumnName());
	
		//set name to null in the resource model
		tableGeneratorResource.setValueColumnName(null);
		assertNull(tableGenerator.getSpecifiedValueColumnName());
		assertNull(tableGeneratorResource.getValueColumnName());
	}
	
	public void testModifySpecifiedValueColumnName() throws Exception {
		ITableGenerator tableGenerator = entityMappings().addTableGenerator(0);		
		TableGenerator tableGeneratorResource = ormResource().getEntityMappings().getTableGenerators().get(0);
		
		//set name in the context model, verify resource model modified
		tableGenerator.setSpecifiedValueColumnName("FOO");
		assertEquals("FOO", tableGeneratorResource.getValueColumnName());
		assertEquals("FOO", tableGenerator.getSpecifiedValueColumnName());
		
		//set name to null in the context model
		tableGenerator.setSpecifiedValueColumnName(null);
		assertNull(tableGeneratorResource.getValueColumnName());
		assertNull(tableGenerator.getSpecifiedValueColumnName());
	}
	
	public void testUpdateSpecifiedPkColumnValue() throws Exception {
		ITableGenerator tableGenerator = entityMappings().addTableGenerator(0);		
		TableGenerator tableGeneratorResource = ormResource().getEntityMappings().getTableGenerators().get(0);
		
		//set name in the resource model, verify context model updated
		tableGeneratorResource.setPkColumnValue("FOO");
		assertEquals("FOO", tableGenerator.getSpecifiedPkColumnValue());
		assertEquals("FOO", tableGeneratorResource.getPkColumnValue());
	
		//set name to null in the resource model
		tableGeneratorResource.setPkColumnValue(null);
		assertNull(tableGenerator.getSpecifiedPkColumnValue());
		assertNull(tableGeneratorResource.getPkColumnValue());
	}
	
	public void testModifySpecifiedPkColumnValue() throws Exception {
		ITableGenerator tableGenerator = entityMappings().addTableGenerator(0);		
		TableGenerator tableGeneratorResource = ormResource().getEntityMappings().getTableGenerators().get(0);
		
		//set name in the context model, verify resource model modified
		tableGenerator.setSpecifiedPkColumnValue("FOO");
		assertEquals("FOO", tableGeneratorResource.getPkColumnValue());
		assertEquals("FOO", tableGenerator.getSpecifiedPkColumnValue());
		
		//set name to null in the context model
		tableGenerator.setSpecifiedPkColumnValue(null);
		assertNull(tableGeneratorResource.getPkColumnValue());
		assertNull(tableGenerator.getSpecifiedPkColumnValue());
	}
}