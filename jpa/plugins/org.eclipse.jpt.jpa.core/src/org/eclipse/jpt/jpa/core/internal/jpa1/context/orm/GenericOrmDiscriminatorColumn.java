/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.resource.orm.XmlDiscriminatorColumn;

/**
 * <code>orm.xml</code> discriminator column
 */
public class GenericOrmDiscriminatorColumn
	extends AbstractOrmNamedDiscriminatorColumn<OrmSpecifiedDiscriminatorColumn.ParentAdapter, XmlDiscriminatorColumn>
	implements OrmSpecifiedDiscriminatorColumn
{
	public GenericOrmDiscriminatorColumn(OrmSpecifiedDiscriminatorColumn.ParentAdapter parentAdapter) {
		super(parentAdapter);
	}


	// ********** XML column **********

	@Override
	public XmlDiscriminatorColumn getXmlColumn() {
		return this.parentAdapter.getXmlColumn();
	}

	@Override
	protected XmlDiscriminatorColumn buildXmlColumn() {
		return this.parentAdapter.buildXmlColumn();
	}

	@Override
	protected void removeXmlColumn() {
		this.parentAdapter.removeXmlColumn();
	}


	// ********** validation **********

	@Override
	public boolean isVirtual() {
		return this.getXmlColumn() == null;
	}
}
