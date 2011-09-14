/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.core.context.orm.OrmDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.resource.orm.XmlDiscriminatorColumn;

/**
 * <code>orm.xml</code> discriminator column
 */
public class GenericOrmDiscriminatorColumn
	extends AbstractOrmNamedDiscriminatorColumn<XmlDiscriminatorColumn, OrmDiscriminatorColumn.Owner>
	implements OrmDiscriminatorColumn
{

	public GenericOrmDiscriminatorColumn(XmlContextNode parent, OrmDiscriminatorColumn.Owner owner) {
		super(parent, owner);
	}


	// ********** XML column **********

	@Override
	public XmlDiscriminatorColumn getXmlColumn() {
		return this.owner.getXmlColumn();
	}

	@Override
	protected XmlDiscriminatorColumn buildXmlColumn() {
		return this.owner.buildXmlColumn();
	}

	@Override
	protected void removeXmlColumn() {
		this.owner.removeXmlColumn();
	}
}
