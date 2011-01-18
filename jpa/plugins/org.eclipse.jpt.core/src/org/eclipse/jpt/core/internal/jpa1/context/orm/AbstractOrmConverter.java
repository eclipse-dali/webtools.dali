/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmConverter;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.resource.orm.XmlConvertibleMapping;

public abstract class AbstractOrmConverter
	extends AbstractOrmXmlContextNode
	implements OrmConverter
{
	protected AbstractOrmConverter(OrmAttributeMapping parent) {
		super(parent);
	}


	// ********** misc **********

	@Override
	public OrmAttributeMapping getParent() {
		return (OrmAttributeMapping) super.getParent();
	}

	protected OrmAttributeMapping getAttributeMapping() {
		return this.getParent();
	}

	protected XmlAttributeMapping getXmlAttributeMapping() {
		return this.getAttributeMapping().getXmlAttributeMapping();
	}

	protected XmlConvertibleMapping getXmlConvertibleMapping() {
		return (XmlConvertibleMapping) this.getXmlAttributeMapping();
	}
}
