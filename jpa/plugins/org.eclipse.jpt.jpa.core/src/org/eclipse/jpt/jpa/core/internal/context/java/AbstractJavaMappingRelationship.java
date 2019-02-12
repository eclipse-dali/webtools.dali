/*******************************************************************************
 * Copyright (c) 2009, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.SpecifiedRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaMappingRelationship;
import org.eclipse.jpt.jpa.core.context.java.JavaRelationshipMapping;

/**
 * <strong>NB:</strong> Subclasses may want to set the {@link #strategy} at the
 * end of their constructors; otherwise, it will be <code>null</code> until it
 * is set during {@link #update(IProgressMonitor)}.
 */
public abstract class AbstractJavaMappingRelationship<P extends JavaRelationshipMapping>
	extends AbstractJavaContextModel<P>
	implements JavaMappingRelationship
{
	protected SpecifiedRelationshipStrategy strategy;


	public AbstractJavaMappingRelationship(P parent) {
		super(parent);
	}


	// ********** synchronize/update **********

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.updateStrategy();
	}


	// ********** strategy **********

	public SpecifiedRelationshipStrategy getStrategy() {
		return this.strategy;
	}

	protected void setStrategy(SpecifiedRelationshipStrategy strategy) {
		SpecifiedRelationshipStrategy old = this.strategy;
		this.strategy = strategy;
		this.firePropertyChanged(STRATEGY_PROPERTY, old, strategy);
	}

	protected abstract SpecifiedRelationshipStrategy buildStrategy();

	/**
	 * This is called by subclasses when the various supported strategies are
	 * added or removed; allowing the strategy to be set synchronously. (?)
	 */
	protected void updateStrategy() {
		this.setStrategy(this.buildStrategy());
	}


	// ********** misc **********

	public P getMapping() {
		return this.parent;
	}

	public TypeMapping getTypeMapping() {
		return this.getMapping().getTypeMapping();
	}

	public Entity getEntity() {
		TypeMapping typeMapping = this.getTypeMapping();
		return (typeMapping instanceof Entity) ? (Entity) typeMapping : null;
	}

	public boolean isOverridable() {
		return this.strategy.isOverridable();
	}

	public boolean isVirtual() {
		return this.getMapping().getPersistentAttribute().isVirtual();
	}

	public boolean isTargetForeignKey() {
		return false;
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		return this.getMapping().getValidationTextRange();
	}
}
