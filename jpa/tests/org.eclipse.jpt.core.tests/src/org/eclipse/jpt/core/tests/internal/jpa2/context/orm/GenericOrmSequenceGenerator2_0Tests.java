/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.tests.internal.jpa2.context.orm;

import org.eclipse.jpt.core.jpa2.context.SequenceGenerator2_0;
import org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.core.tests.internal.jpa2.context.Generic2_0ContextModelTestCase;

/**
 *  Generic2_0OrmSequenceGenerator2_0Tests
 */
@SuppressWarnings("nls")
public class GenericOrmSequenceGenerator2_0Tests
	extends Generic2_0ContextModelTestCase
{

	public GenericOrmSequenceGenerator2_0Tests(String name) {
		super(name);
	}
	
	// ********** catalog **********

	public void testUpdateSpecifiedCatalog() throws Exception {
		SequenceGenerator2_0 sequenceGenerator = (SequenceGenerator2_0) getEntityMappings().addSequenceGenerator(0);		
		XmlSequenceGenerator sequenceGeneratorResource = getXmlEntityMappings().getSequenceGenerators().get(0);

		//set name in the resource model, verify context model updated
		sequenceGeneratorResource.setCatalog("FOO");
		assertEquals("FOO", sequenceGenerator.getSpecifiedCatalog());
		assertEquals("FOO", sequenceGeneratorResource.getCatalog());
	
		//set name to null in the resource model
		sequenceGeneratorResource.setCatalog(null);
		assertNull(sequenceGenerator.getSpecifiedCatalog());
		assertNull(sequenceGeneratorResource.getCatalog());
	}
	
	public void testModifySpecifiedCatalog() throws Exception {
		SequenceGenerator2_0 sequenceGenerator = (SequenceGenerator2_0) getEntityMappings().addSequenceGenerator(0);		
		XmlSequenceGenerator sequenceGeneratorResource = getXmlEntityMappings().getSequenceGenerators().get(0);
	
		//set name in the context model, verify resource model modified
		sequenceGenerator.setSpecifiedCatalog("FOO");
		assertEquals("FOO", sequenceGeneratorResource.getCatalog());
		assertEquals("FOO", sequenceGenerator.getSpecifiedCatalog());
		
		//set name to null in the context model
		sequenceGenerator.setSpecifiedCatalog(null);
		assertNull(sequenceGeneratorResource.getCatalog());
		assertNull(sequenceGenerator.getSpecifiedCatalog());
	}
	
	// ********** schema **********

	public void testUpdateSpecifiedSchema() throws Exception {
		SequenceGenerator2_0 sequenceGenerator = (SequenceGenerator2_0) getEntityMappings().addSequenceGenerator(0);		
		XmlSequenceGenerator sequenceGeneratorResource = getXmlEntityMappings().getSequenceGenerators().get(0);

		//set name in the resource model, verify context model updated
		sequenceGeneratorResource.setSchema("FOO");
		assertEquals("FOO", sequenceGenerator.getSpecifiedSchema());
		assertEquals("FOO", sequenceGeneratorResource.getSchema());
	
		//set name to null in the resource model
		sequenceGeneratorResource.setSchema(null);
		assertNull(sequenceGenerator.getSpecifiedSchema());
		assertNull(sequenceGeneratorResource.getSchema());
	}
	
	public void testModifySpecifiedSchema() throws Exception {
		SequenceGenerator2_0 sequenceGenerator = (SequenceGenerator2_0) getEntityMappings().addSequenceGenerator(0);		
		XmlSequenceGenerator sequenceGeneratorResource = getXmlEntityMappings().getSequenceGenerators().get(0);
	
		//set name in the context model, verify resource model modified
		sequenceGenerator.setSpecifiedSchema("FOO");
		assertEquals("FOO", sequenceGeneratorResource.getSchema());
		assertEquals("FOO", sequenceGenerator.getSpecifiedSchema());
		
		//set name to null in the context model
		sequenceGenerator.setSpecifiedSchema(null);
		assertNull(sequenceGeneratorResource.getSchema());
		assertNull(sequenceGenerator.getSpecifiedSchema());
	}
}