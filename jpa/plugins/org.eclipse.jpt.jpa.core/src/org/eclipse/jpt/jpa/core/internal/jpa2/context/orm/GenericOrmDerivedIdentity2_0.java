/*******************************************************************************
 * Copyright (c) 2009, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.orm;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextModel;
import org.eclipse.jpt.jpa.core.jpa2.context.DerivedIdentityStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmDerivedIdentity2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmIdDerivedIdentityStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmMapsIdDerivedIdentityStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmSingleRelationshipMapping2_0;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmDerivedIdentity2_0
	extends AbstractOrmXmlContextModel<OrmSingleRelationshipMapping2_0>
	implements OrmDerivedIdentity2_0
{
	protected DerivedIdentityStrategy2_0 strategy;

	protected final OrmIdDerivedIdentityStrategy2_0 idStrategy;

	protected final OrmMapsIdDerivedIdentityStrategy2_0 mapsIdStrategy;


	public GenericOrmDerivedIdentity2_0(OrmSingleRelationshipMapping2_0 parent) {
		super(parent);
		this.idStrategy = this.buildIdStrategy();
		this.mapsIdStrategy = this.buildMapsIdStrategy();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.idStrategy.synchronizeWithResourceModel(monitor);
		this.mapsIdStrategy.synchronizeWithResourceModel(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.idStrategy.update(monitor);
		this.mapsIdStrategy.update(monitor);
		this.updateStrategy();
	}


	// ********** predominant strategy **********

	public DerivedIdentityStrategy2_0 getPredominantDerivedIdentityStrategy() {
		return this.strategy;
	}

	protected void setStrategy(DerivedIdentityStrategy2_0 strategy) {
		DerivedIdentityStrategy2_0 old = this.strategy;
		this.strategy = strategy;
		this.firePropertyChanged(PREDOMINANT_DERIVED_IDENTITY_STRATEGY_PROPERTY, old, strategy);
	}

	protected void updateStrategy() {
		this.setStrategy(this.buildStrategy());
	}

	protected DerivedIdentityStrategy2_0 buildStrategy() {
		if (this.mapsIdStrategy.isSpecified()) {
			return this.mapsIdStrategy;
		}
		if (this.idStrategy.isSpecified()) {
			return this.idStrategy;
		}
		return null;
	}


	// ********** null strategy **********

	public boolean usesNullDerivedIdentityStrategy() {
		return this.strategy == null;
	}

	public void setNullDerivedIdentityStrategy() {
		this.mapsIdStrategy.removeStrategy();
		this.idStrategy.removeStrategy();
		this.updateStrategy();
	}


	// ********** ID strategy **********

	public OrmIdDerivedIdentityStrategy2_0 getIdDerivedIdentityStrategy() {
		return this.idStrategy;
	}

	public void setIdDerivedIdentityStrategy() {
		this.idStrategy.addStrategy();
		this.mapsIdStrategy.removeStrategy();
		this.updateStrategy();
	}

	public boolean usesIdDerivedIdentityStrategy() {
		return this.strategy == this.idStrategy;
	}

	protected OrmIdDerivedIdentityStrategy2_0 buildIdStrategy() {
		return new GenericOrmIdDerivedIdentityStrategy2_0(this);
	}


	// ********** maps ID strategy **********

	public OrmMapsIdDerivedIdentityStrategy2_0 getMapsIdDerivedIdentityStrategy() {
		return this.mapsIdStrategy;
	}

	public void setMapsIdDerivedIdentityStrategy() {
		this.mapsIdStrategy.addStrategy();
		this.idStrategy.removeStrategy();
		this.updateStrategy();
	}

	public boolean usesMapsIdDerivedIdentityStrategy() {
		return this.strategy == this.mapsIdStrategy;
	}

	protected OrmMapsIdDerivedIdentityStrategy2_0 buildMapsIdStrategy() {
		return new GenericOrmMapsIdDerivedIdentityStrategy2_0(this);
	}


	// ********** misc **********

	public OrmSingleRelationshipMapping2_0 getMapping() {
		return this.parent;
	}

	public void initializeFrom(OrmDerivedIdentity2_0 oldDerivedIdentity) {
		this.idStrategy.initializeFrom(oldDerivedIdentity.getIdDerivedIdentityStrategy());
		this.mapsIdStrategy.initializeFrom(oldDerivedIdentity.getMapsIdDerivedIdentityStrategy());
		this.updateStrategy();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		if (this.strategy != null) {
			this.strategy.validate(messages, reporter);
		}
	}

	public TextRange getValidationTextRange() {
		return this.getMapping().getValidationTextRange();
	}

	// ************** completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.strategy != null) {
			result = this.strategy.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		return null;
	}
}
