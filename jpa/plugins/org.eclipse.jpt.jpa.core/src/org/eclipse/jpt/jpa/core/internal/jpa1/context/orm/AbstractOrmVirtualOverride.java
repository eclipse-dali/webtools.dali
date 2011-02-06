/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.orm.OrmOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualOverride;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextNode;

/**
 * Virtual <code>orm.xml</code> override
 */
public abstract class AbstractOrmVirtualOverride<C extends OrmOverrideContainer>
	extends AbstractOrmXmlContextNode
	implements OrmVirtualOverride
{
	protected final String name;  // never null


	protected AbstractOrmVirtualOverride(C parent, String name) {
		super(parent);
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public boolean isVirtual() {
		return true;
	}

	public OrmOverride convertToSpecified() {
		return this.getContainer().convertOverrideToSpecified(this);
	}

	@Override
	@SuppressWarnings("unchecked")
	public C getParent() {
		return (C) super.getParent();
	}

	public C getContainer() {
		return this.getParent();
	}

	public TextRange getValidationTextRange() {
		return this.getParent().getValidationTextRange();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}
}
