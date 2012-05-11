/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.ListIterator;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;

public class NullOrmAssociationOverrideContainer extends AbstractOrmXmlContextNode
	implements OrmAssociationOverrideContainer
{

	protected final OrmAssociationOverrideContainer.Owner owner;
	
	public NullOrmAssociationOverrideContainer(XmlContextNode parent, OrmAssociationOverrideContainer.Owner owner) {
		super(parent);
		this.owner = owner;
	}

	protected Owner getOwner() {
		return this.owner;
	}
	
	public ListIterator<OrmAssociationOverride> associationOverrides() {
		return EmptyListIterator.instance();
	}
	
	public int associationOverridesSize() {
		return 0;
	}

	public ListIterator<OrmAssociationOverride> virtualAssociationOverrides() {
		return EmptyListIterator.instance();
	}
	
	public int virtualAssociationOverridesSize() {
		return 0;
	}

	public ListIterator<OrmAssociationOverride> specifiedAssociationOverrides() {
		return EmptyListIterator.instance();
	}

	public int specifiedAssociationOverridesSize() {
		return 0;
	}
	
	public void moveSpecifiedAssociationOverride(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}

	public OrmAssociationOverride getAssociationOverrideNamed(String name) {
		return null;
	}

	public void update() {
		//no-op
	}


	public TextRange getValidationTextRange() {
		return null;
	}
}
