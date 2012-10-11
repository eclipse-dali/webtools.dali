/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.jpt.jpa.core.context.SequenceGenerator;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmSequenceGenerator;
import org.eclipse.jpt.jpa.core.resource.orm.XmlSequenceGenerator;

/**
 * <code>orm.xml</code> sequence generator
 */
public class GenericOrmSequenceGenerator
	extends AbstractOrmSequenceGenerator
{
	public GenericOrmSequenceGenerator(JpaContextNode parent, XmlSequenceGenerator xmlSequenceGenerator) {
		super(parent, xmlSequenceGenerator);
	}

	// ********** database stuff **********

	/**
	 * The JPA 1.0 spec does not allow a sequence to specify a catalog.
	 */
	@Override
	protected String getCatalog() {
		return this.getContextDefaultCatalog();
	}

	/**
	 * The JPA 1.0 spec does not allow a sequence to specify a schema.
	 */
	@Override
	protected String getSchema() {
		return this.getContextDefaultSchema();
	}

	// ********** validation **********

	@Override
	protected boolean isEquivalentTo(SequenceGenerator generator) {
		return super.isEquivalentTo(generator) &&
				ObjectTools.equals(this.getDbSchema(), generator.getDbSchema()) &&
				ObjectTools.equals(this.getDbCatalog(), generator.getDbCatalog());
	}

}
