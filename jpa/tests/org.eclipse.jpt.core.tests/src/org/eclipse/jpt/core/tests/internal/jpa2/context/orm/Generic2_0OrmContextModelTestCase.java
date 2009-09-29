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

import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.core.tests.internal.jpa2.context.java.Generic2_0ContextModelTestCase;

public abstract class Generic2_0OrmContextModelTestCase
	extends Generic2_0ContextModelTestCase
{
	protected JpaXmlResource generic2_0OrmXmlResource;
	
	
	protected Generic2_0OrmContextModelTestCase(String name) {
		super(name);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.generic2_0OrmXmlResource = getJpaProject().getDefaultOrmXmlResource();
	}
	
	@Override
	protected void tearDown() throws Exception {
		this.generic2_0OrmXmlResource = null;
		super.tearDown();
	}
	
	@Override
	protected JpaXmlResource getOrmXmlResource() {
		return this.generic2_0OrmXmlResource;
	}
	
	@Override
	protected XmlEntityMappings getXmlEntityMappings() {
		return (XmlEntityMappings) super.getXmlEntityMappings();
	}
}
