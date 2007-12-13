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
package org.eclipse.jpt.core.tests.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.internal.context.base.IGenerator;
import org.eclipse.jpt.core.internal.context.base.IIdMapping;
import org.eclipse.jpt.core.internal.context.base.ITableGenerator;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.TableGenerator;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaTableGeneratorTests extends ContextModelTestCase
{
	private static final String TABLE_GENERATOR_NAME = "MY_TABLE_GENERATOR";
	
	private void createEntityAnnotation() throws Exception{
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
	}
	
	private void createTableGeneratorAnnotation() throws Exception{
		this.createAnnotationAndMembers("TableGenerator", 
			"String name(); " +
			"String table() default \"\"; " +
			"String catalog() default \"\"; " +
			"String schema() default \"\";" +
			"String pkColumnName() default \"\"; " +
			"String valueColumnName() default \"\"; " +
			"String pkColumnValue() default \"\"; " +
			"int initialValue() default 0; " +
			"int allocationSize() default 50; " +
			"UniqueConstraint[] uniqueConstraints() default {};");		
	}

	private IType createTestEntityWithTableGenerator() throws Exception {
		createEntityAnnotation();
		createTableGeneratorAnnotation();
	
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.TABLE_GENERATOR, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id").append(CR);
				sb.append("@TableGenerator(name=\"" + TABLE_GENERATOR_NAME + "\")");
			}
		});
	}
		
	public JavaTableGeneratorTests(String name) {
		super(name);
	}

	public void testGetName() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();
		assertEquals(TABLE_GENERATOR_NAME, idMapping.getTableGenerator().getName());

		//change resource model tableGenerator name, verify the context model is updated
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);
		
		tableGenerator.setName("foo");
		
		assertEquals("foo", idMapping.getTableGenerator().getName());
	}

	public void testSetName() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();
		assertEquals(TABLE_GENERATOR_NAME, idMapping.getTableGenerator().getName());

		idMapping.getTableGenerator().setName("foo");
		
		assertEquals("foo", idMapping.getTableGenerator().getName());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);
		
		assertEquals("foo", tableGenerator.getName());
	}
	
	public void testSetSpecifiedNameNull() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();
		assertEquals(TABLE_GENERATOR_NAME, idMapping.getTableGenerator().getName());

		idMapping.getTableGenerator().setName(null);
		
		assertNull(idMapping.getTableGenerator());
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);
		
		assertNull(tableGenerator);
	}
	
	public void testGetCatalog() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();
		
		assertNull(idMapping.getTableGenerator().getCatalog());
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);	
		
		tableGenerator.setCatalog("myCatalog");
		
		assertEquals("myCatalog", idMapping.getTableGenerator().getCatalog());
		assertEquals("myCatalog", idMapping.getTableGenerator().getSpecifiedCatalog());
	}
	
	public void testGetDefaultCatalog() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();

		assertNull(idMapping.getTableGenerator().getDefaultCatalog());
		
		idMapping.getTableGenerator().setSpecifiedCatalog("myCatalog");
		
		assertNull(idMapping.getTableGenerator().getDefaultCatalog());
		assertEquals("myCatalog", idMapping.getTableGenerator().getSpecifiedCatalog());
	}
	
	public void testSetSpecifiedCatalog() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();
		idMapping.getTableGenerator().setSpecifiedCatalog("myCatalog");
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);	
		
		assertEquals("myCatalog", tableGenerator.getCatalog());
		
		idMapping.getTableGenerator().setName(null);
		idMapping.getTableGenerator().setSpecifiedCatalog(null);
		assertNull(attributeResource.annotation(JPA.TABLE_GENERATOR));
	}

	public void testGetSchema() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();
		
		assertNull(idMapping.getTableGenerator().getSchema());
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);	
		
		tableGenerator.setSchema("mySchema");
		
		assertEquals("mySchema", idMapping.getTableGenerator().getSchema());
		assertEquals("mySchema", idMapping.getTableGenerator().getSpecifiedSchema());
	}
	
	public void testGetDefaultSchema() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();

		assertNull(idMapping.getTableGenerator().getDefaultSchema());
		
		idMapping.getTableGenerator().setSpecifiedSchema("mySchema");
		
		assertNull(idMapping.getTableGenerator().getDefaultSchema());
		assertEquals("mySchema", idMapping.getTableGenerator().getSpecifiedSchema());
	}
	
	public void testSetSpecifiedSchema() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();
		idMapping.getTableGenerator().setSpecifiedSchema("mySchema");
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);	
		
		assertEquals("mySchema", tableGenerator.getSchema());
		
		idMapping.getTableGenerator().setName(null);
		idMapping.getTableGenerator().setSpecifiedSchema(null);
		assertNull(attributeResource.annotation(JPA.TABLE_GENERATOR));
	}
	
	public void testGetPkColumnName() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();
		
		assertNull(idMapping.getTableGenerator().getPkColumnName());
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);	
		
		tableGenerator.setPkColumnName("myPkColumnName");
		
		assertEquals("myPkColumnName", idMapping.getTableGenerator().getPkColumnName());
		assertEquals("myPkColumnName", idMapping.getTableGenerator().getSpecifiedPkColumnName());
	}
	
	public void testGetDefaultPkColumnName() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();

		assertNull(idMapping.getTableGenerator().getDefaultPkColumnName());
		
		idMapping.getTableGenerator().setSpecifiedPkColumnName("myPkColumnName");
		
		assertNull(idMapping.getTableGenerator().getDefaultPkColumnName());
		assertEquals("myPkColumnName", idMapping.getTableGenerator().getSpecifiedPkColumnName());
	}
	
	public void testSetSpecifiedPkColumnName() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();
		idMapping.getTableGenerator().setSpecifiedPkColumnName("myPkColumnName");
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);	
		
		assertEquals("myPkColumnName", tableGenerator.getPkColumnName());
		
		idMapping.getTableGenerator().setName(null);
		idMapping.getTableGenerator().setSpecifiedPkColumnName(null);
		assertNull(attributeResource.annotation(JPA.TABLE_GENERATOR));
	}	
	
	public void testGetValueColumnName() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();
		
		assertNull(idMapping.getTableGenerator().getValueColumnName());
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);	
		
		tableGenerator.setValueColumnName("myValueColumnName");
		
		assertEquals("myValueColumnName", idMapping.getTableGenerator().getValueColumnName());
		assertEquals("myValueColumnName", idMapping.getTableGenerator().getSpecifiedValueColumnName());
	}
	
	public void testGetDefaultValueColumnName() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();

		assertNull(idMapping.getTableGenerator().getDefaultValueColumnName());
		
		idMapping.getTableGenerator().setSpecifiedValueColumnName("myValueColumnName");
		
		assertNull(idMapping.getTableGenerator().getDefaultValueColumnName());
		assertEquals("myValueColumnName", idMapping.getTableGenerator().getSpecifiedValueColumnName());
	}
	
	public void testSetSpecifiedValueColumnName() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();
		idMapping.getTableGenerator().setSpecifiedValueColumnName("myValueColumnName");
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);	
		
		assertEquals("myValueColumnName", tableGenerator.getValueColumnName());
		
		idMapping.getTableGenerator().setName(null);
		idMapping.getTableGenerator().setSpecifiedValueColumnName(null);
		assertNull(attributeResource.annotation(JPA.TABLE_GENERATOR));
	}
	
	public void testGetPkColumnValue() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();
		
		assertNull(idMapping.getTableGenerator().getPkColumnValue());
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);	
		
		tableGenerator.setPkColumnValue("myPkColumnValue");
		
		assertEquals("myPkColumnValue", idMapping.getTableGenerator().getPkColumnValue());
		assertEquals("myPkColumnValue", idMapping.getTableGenerator().getSpecifiedPkColumnValue());
	}
	
	public void testGetDefaultPkColumnValue() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();

		assertNull(idMapping.getTableGenerator().getDefaultPkColumnValue());
		
		idMapping.getTableGenerator().setSpecifiedPkColumnValue("myPkColumnValue");
		
		assertNull(idMapping.getTableGenerator().getDefaultPkColumnValue());
		assertEquals("myPkColumnValue", idMapping.getTableGenerator().getSpecifiedPkColumnValue());
	}
	
	public void testSetSpecifiedPkColumnValue() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();
		idMapping.getTableGenerator().setSpecifiedPkColumnValue("myPkColumnValue");
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);	
		
		assertEquals("myPkColumnValue", tableGenerator.getPkColumnValue());
		
		idMapping.getTableGenerator().setName(null);
		idMapping.getTableGenerator().setSpecifiedPkColumnValue(null);
		assertNull(attributeResource.annotation(JPA.TABLE_GENERATOR));
	}
	
	public void testGetInitialValue() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();
		
		assertEquals(ITableGenerator.DEFAULT_INITIAL_VALUE, idMapping.getTableGenerator().getInitialValue());
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);	
		
		tableGenerator.setInitialValue(Integer.valueOf(82));
		
		assertEquals(Integer.valueOf(82), idMapping.getTableGenerator().getInitialValue());
		assertEquals(Integer.valueOf(82), idMapping.getTableGenerator().getSpecifiedInitialValue());
	}
	
	public void testGetDefaultInitialValue() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();

		assertEquals(ITableGenerator.DEFAULT_INITIAL_VALUE, idMapping.getTableGenerator().getDefaultInitialValue());
		
		idMapping.getTableGenerator().setSpecifiedInitialValue(Integer.valueOf(82));
		
		assertEquals(ITableGenerator.DEFAULT_INITIAL_VALUE, idMapping.getTableGenerator().getDefaultInitialValue());
		assertEquals(Integer.valueOf(82), idMapping.getTableGenerator().getSpecifiedInitialValue());
	}
	
	public void testSetSpecifiedInitialValue() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();
		idMapping.getTableGenerator().setSpecifiedInitialValue(Integer.valueOf(20));
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);	
		
		assertEquals(Integer.valueOf(20), tableGenerator.getInitialValue());
		
		idMapping.getTableGenerator().setName(null);
		idMapping.getTableGenerator().setSpecifiedInitialValue(null);
		assertNull(attributeResource.annotation(JPA.TABLE_GENERATOR));
	}
	
	public void testGetAllocationSize() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();
		
		assertEquals(IGenerator.DEFAULT_ALLOCATION_SIZE, idMapping.getTableGenerator().getAllocationSize());

		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);	
		
		tableGenerator.setAllocationSize(Integer.valueOf(20));
		
		assertEquals(Integer.valueOf(20), idMapping.getTableGenerator().getAllocationSize());
		assertEquals(Integer.valueOf(20), idMapping.getTableGenerator().getSpecifiedAllocationSize());
	}
	
	public void testGetDefaultAllocationSize() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();

		assertEquals(IGenerator.DEFAULT_ALLOCATION_SIZE, idMapping.getTableGenerator().getDefaultAllocationSize());
		
		idMapping.getTableGenerator().setSpecifiedAllocationSize(Integer.valueOf(20));
		
		assertEquals(IGenerator.DEFAULT_ALLOCATION_SIZE, idMapping.getTableGenerator().getDefaultAllocationSize());
		assertEquals(Integer.valueOf(20), idMapping.getTableGenerator().getSpecifiedAllocationSize());
	}
	
	public void testSetSpecifiedAllocationSize() throws Exception {
		createTestEntityWithTableGenerator();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		IIdMapping idMapping = (IIdMapping) javaPersistentType().attributeNamed("id").getMapping();
		idMapping.getTableGenerator().setSpecifiedAllocationSize(Integer.valueOf(25));
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		TableGenerator tableGenerator = (TableGenerator) attributeResource.annotation(JPA.TABLE_GENERATOR);	
		
		assertEquals(Integer.valueOf(25), tableGenerator.getAllocationSize());
		
		idMapping.getTableGenerator().setName(null);
		idMapping.getTableGenerator().setSpecifiedAllocationSize(null);
		assertNull(attributeResource.annotation(JPA.TABLE_GENERATOR));
	}
}
