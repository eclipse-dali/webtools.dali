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
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.SequenceGenerator;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class XmlSequenceGeneratorTests extends ContextModelTestCase
{
	public XmlSequenceGeneratorTests(String name) {
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
		SequenceGenerator sequenceGenerator = entityMappings().addSequenceGenerator(0);		
		XmlSequenceGenerator sequenceGeneratorResource = ormResource().getEntityMappings().getSequenceGenerators().get(0);
		
		//set name in the resource model, verify context model updated
		sequenceGeneratorResource.setName("FOO");
		assertEquals("FOO", sequenceGenerator.getName());
		assertEquals("FOO", sequenceGeneratorResource.getName());
	
		//set name to null in the resource model
		sequenceGeneratorResource.setName(null);
		assertNull(sequenceGenerator.getName());
		assertNull(sequenceGeneratorResource.getName());
	}
	
	public void testModifySpecifiedName() throws Exception {
		SequenceGenerator sequenceGenerator = entityMappings().addSequenceGenerator(0);		
		XmlSequenceGenerator sequenceGeneratorResource = ormResource().getEntityMappings().getSequenceGenerators().get(0);
		
		//set name in the context model, verify resource model modified
		sequenceGenerator.setName("FOO");
		assertEquals("FOO", sequenceGeneratorResource.getName());
		assertEquals("FOO", sequenceGenerator.getName());
		
		//set name to null in the context model
		sequenceGenerator.setName(null);
		assertNull(sequenceGeneratorResource.getName());
		assertNull(sequenceGenerator.getName());
	}
	
	public void testUpdateSpecifiedSequenceName() throws Exception {
		SequenceGenerator sequenceGenerator = entityMappings().addSequenceGenerator(0);		
		XmlSequenceGenerator sequenceGeneratorResource = ormResource().getEntityMappings().getSequenceGenerators().get(0);
		
		//set name in the resource model, verify context model updated
		sequenceGeneratorResource.setSequenceName("FOO");
		assertEquals("FOO", sequenceGenerator.getSpecifiedSequenceName());
		assertEquals("FOO", sequenceGeneratorResource.getSequenceName());
	
		//set name to null in the resource model
		sequenceGeneratorResource.setSequenceName(null);
		assertNull(sequenceGenerator.getSpecifiedSequenceName());
		assertNull(sequenceGeneratorResource.getSequenceName());
	}
	
	public void testModifySpecifiedSequenceName() throws Exception {
		SequenceGenerator sequenceGenerator = entityMappings().addSequenceGenerator(0);		
		XmlSequenceGenerator sequenceGeneratorResource = ormResource().getEntityMappings().getSequenceGenerators().get(0);
		
		//set name in the context model, verify resource model modified
		sequenceGenerator.setSpecifiedSequenceName("FOO");
		assertEquals("FOO", sequenceGeneratorResource.getSequenceName());
		assertEquals("FOO", sequenceGenerator.getSpecifiedSequenceName());
		
		//set name to null in the context model
		sequenceGenerator.setSpecifiedSequenceName(null);
		assertNull(sequenceGeneratorResource.getSequenceName());
		assertNull(sequenceGenerator.getSpecifiedSequenceName());
	}

	public void testUpdateSpecifiedInitialValue() throws Exception {
		SequenceGenerator sequenceGenerator = entityMappings().addSequenceGenerator(0);		
		XmlSequenceGenerator sequenceGeneratorResource = ormResource().getEntityMappings().getSequenceGenerators().get(0);
		
		//set initial value in the resource model, verify context model updated
		sequenceGeneratorResource.setInitialValue(Integer.valueOf(10));
		assertEquals(Integer.valueOf(10), sequenceGenerator.getSpecifiedInitialValue());
		assertEquals(Integer.valueOf(10), sequenceGeneratorResource.getInitialValue());
	
		//set initial value to 1, which happens to be the default, in the resource model
		sequenceGeneratorResource.setInitialValue(Integer.valueOf(1));
		assertEquals(Integer.valueOf(1), sequenceGenerator.getSpecifiedInitialValue());
		assertEquals(Integer.valueOf(1), sequenceGeneratorResource.getInitialValue());
	
		//set initial value to null in the resource model
		sequenceGeneratorResource.setInitialValue(null);
		assertNull(sequenceGenerator.getSpecifiedInitialValue());
		assertNull(sequenceGeneratorResource.getInitialValue());
	}
	
	public void testModifySpecifiedInitialValue() throws Exception {
		SequenceGenerator sequenceGenerator = entityMappings().addSequenceGenerator(0);		
		XmlSequenceGenerator sequenceGeneratorResource = ormResource().getEntityMappings().getSequenceGenerators().get(0);
		
		//set initial value in the context model, verify resource model modified
		sequenceGenerator.setSpecifiedInitialValue(Integer.valueOf(10));
		assertEquals(Integer.valueOf(10), sequenceGeneratorResource.getInitialValue());
		assertEquals(Integer.valueOf(10), sequenceGenerator.getSpecifiedInitialValue());
		
		sequenceGenerator.setSpecifiedInitialValue(Integer.valueOf(1));
		assertEquals(Integer.valueOf(1), sequenceGeneratorResource.getInitialValue());
		assertEquals(Integer.valueOf(1), sequenceGenerator.getSpecifiedInitialValue());

		//set initial value to null in the context model
		sequenceGenerator.setSpecifiedInitialValue(null);
		assertNull(sequenceGeneratorResource.getInitialValue());
		assertNull(sequenceGenerator.getSpecifiedInitialValue());
	}
	
	public void testUpdateSpecifiedAllocationSize() throws Exception {
		SequenceGenerator sequenceGenerator = entityMappings().addSequenceGenerator(0);		
		XmlSequenceGenerator sequenceGeneratorResource = ormResource().getEntityMappings().getSequenceGenerators().get(0);
		
		//set allocation size in the resource model, verify context model updated
		sequenceGeneratorResource.setAllocationSize(Integer.valueOf(10));
		assertEquals(Integer.valueOf(10), sequenceGenerator.getSpecifiedAllocationSize());
		assertEquals(Integer.valueOf(10), sequenceGeneratorResource.getAllocationSize());
	
		//set allocation size to 50, which happens to be the default, in the resource model
		sequenceGeneratorResource.setAllocationSize(Integer.valueOf(1));
		assertEquals(Integer.valueOf(1), sequenceGenerator.getSpecifiedAllocationSize());
		assertEquals(Integer.valueOf(1), sequenceGeneratorResource.getAllocationSize());
	
		//set allocation size to null in the resource model
		sequenceGeneratorResource.setAllocationSize(null);
		assertNull(sequenceGenerator.getSpecifiedAllocationSize());
		assertNull(sequenceGeneratorResource.getAllocationSize());
	}
	
	public void testModifySpecifiedAllocationSize() throws Exception {
		SequenceGenerator sequenceGenerator = entityMappings().addSequenceGenerator(0);		
		XmlSequenceGenerator sequenceGeneratorResource = ormResource().getEntityMappings().getSequenceGenerators().get(0);
		
		//set allocation size in the context model, verify resource model modified
		sequenceGenerator.setSpecifiedAllocationSize(Integer.valueOf(10));
		assertEquals(Integer.valueOf(10), sequenceGeneratorResource.getAllocationSize());
		assertEquals(Integer.valueOf(10), sequenceGenerator.getSpecifiedAllocationSize());
		
		sequenceGenerator.setSpecifiedAllocationSize(Integer.valueOf(50));
		assertEquals(Integer.valueOf(50), sequenceGeneratorResource.getAllocationSize());
		assertEquals(Integer.valueOf(50), sequenceGenerator.getSpecifiedAllocationSize());

		//set allocation size to null in the context model
		sequenceGenerator.setSpecifiedAllocationSize(null);
		assertNull(sequenceGeneratorResource.getAllocationSize());
		assertNull(sequenceGenerator.getSpecifiedAllocationSize());
	}
}