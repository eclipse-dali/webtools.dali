/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context.orm;

import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.context.SequenceGenerator;
import org.eclipse.jpt.jpa.core.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class OrmSequenceGeneratorTests extends ContextModelTestCase
{
	public OrmSequenceGeneratorTests(String name) {
		super(name);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptJpaCorePlugin.DEFAULT_ORM_XML_RUNTIME_PATH.toString());
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		getPersistenceXmlResource().save(null);
	}
	
	public void testUpdateSpecifiedName() throws Exception {
		SequenceGenerator sequenceGenerator = getEntityMappings().addSequenceGenerator(0);		
		XmlSequenceGenerator sequenceGeneratorResource = getXmlEntityMappings().getSequenceGenerators().get(0);
		
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
		SequenceGenerator sequenceGenerator = getEntityMappings().addSequenceGenerator(0);		
		XmlSequenceGenerator sequenceGeneratorResource = getXmlEntityMappings().getSequenceGenerators().get(0);
		
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
		SequenceGenerator sequenceGenerator = getEntityMappings().addSequenceGenerator(0);		
		XmlSequenceGenerator sequenceGeneratorResource = getXmlEntityMappings().getSequenceGenerators().get(0);
		
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
		SequenceGenerator sequenceGenerator = getEntityMappings().addSequenceGenerator(0);		
		XmlSequenceGenerator sequenceGeneratorResource = getXmlEntityMappings().getSequenceGenerators().get(0);
		
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
		SequenceGenerator sequenceGenerator = getEntityMappings().addSequenceGenerator(0);		
		XmlSequenceGenerator sequenceGeneratorResource = getXmlEntityMappings().getSequenceGenerators().get(0);
		
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
		SequenceGenerator sequenceGenerator = getEntityMappings().addSequenceGenerator(0);		
		XmlSequenceGenerator sequenceGeneratorResource = getXmlEntityMappings().getSequenceGenerators().get(0);
		
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
		SequenceGenerator sequenceGenerator = getEntityMappings().addSequenceGenerator(0);		
		XmlSequenceGenerator sequenceGeneratorResource = getXmlEntityMappings().getSequenceGenerators().get(0);
		
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
		SequenceGenerator sequenceGenerator = getEntityMappings().addSequenceGenerator(0);		
		XmlSequenceGenerator sequenceGeneratorResource = getXmlEntityMappings().getSequenceGenerators().get(0);
		
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