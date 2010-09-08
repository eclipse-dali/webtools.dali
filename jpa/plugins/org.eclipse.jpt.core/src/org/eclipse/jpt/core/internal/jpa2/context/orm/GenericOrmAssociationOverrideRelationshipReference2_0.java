/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import java.util.List;
import org.eclipse.jpt.core.context.AssociationOverrideRelationshipReference;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.context.JoinColumn.Owner;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverride;
import org.eclipse.jpt.core.internal.context.JoinColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmAssociationOverrideRelationshipReference;
import org.eclipse.jpt.core.jpa2.context.AssociationOverrideRelationshipReference2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmAssociationOverrideRelationshipReference2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmJoinTableInAssociationOverrideJoiningStrategy2_0;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmAssociationOverrideRelationshipReference2_0 extends AbstractOrmAssociationOverrideRelationshipReference
	implements OrmAssociationOverrideRelationshipReference2_0
{

	protected OrmJoinTableInAssociationOverrideJoiningStrategy2_0 joinTableJoiningStrategy;

	// cache the strategy for property change notification
	protected JoiningStrategy cachedPredominantJoiningStrategy;

	public GenericOrmAssociationOverrideRelationshipReference2_0(OrmAssociationOverride parent, XmlAssociationOverride xao) {
		super(parent, xao);
	}
	
	protected OrmJoinTableInAssociationOverrideJoiningStrategy2_0 buildJoinTableJoiningStrategy(XmlAssociationOverride xao) {
		return new GenericOrmJoinTableInAssociationOverrideJoiningStrategy2_0(this, xao);
	}
	
	@Override
	public void initializeFrom(AssociationOverrideRelationshipReference oldAssociationOverride) {
		if (oldAssociationOverride.getJoinColumnJoiningStrategy().hasSpecifiedJoinColumns()) {
			getJoinColumnJoiningStrategy().initializeFrom(oldAssociationOverride.getJoinColumnJoiningStrategy());
		}
		else {
			getJoinTableJoiningStrategy().initializeFrom(((AssociationOverrideRelationshipReference2_0) oldAssociationOverride).getJoinTableJoiningStrategy());
		}
	}

	@Override
	protected void initializeJoiningStrategies(org.eclipse.jpt.core.resource.orm.XmlAssociationOverride xao) {
		super.initializeJoiningStrategies(xao);
		this.joinTableJoiningStrategy = buildJoinTableJoiningStrategy(xao);
	}
	
	@Override
	protected void updateJoiningStrategies(org.eclipse.jpt.core.resource.orm.XmlAssociationOverride xao) {
		super.updateJoiningStrategies(xao);
		this.joinTableJoiningStrategy.update(xao);
	}
		
	@Override
	protected JoiningStrategy calculatePredominantJoiningStrategy() {
		if (this.joinColumnJoiningStrategy.hasSpecifiedJoinColumns()) {
			return this.joinColumnJoiningStrategy;
		}
		return this.joinTableJoiningStrategy;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.joinTableJoiningStrategy.validate(messages, reporter);
	}


	// **************** join table *******************************************
	

	public OrmJoinTableInAssociationOverrideJoiningStrategy2_0 getJoinTableJoiningStrategy() {
		return this.joinTableJoiningStrategy;
	}
	
	public boolean usesJoinTableJoiningStrategy() {
		return getPredominantJoiningStrategy() == this.joinTableJoiningStrategy;
	}
	
	public void setJoinTableJoiningStrategy() {
		this.joinTableJoiningStrategy.addStrategy();
		this.joinColumnJoiningStrategy.removeStrategy();
	}
	
	public void unsetJoinTableJoiningStrategy() {
		this.joinTableJoiningStrategy.removeStrategy();
	}
	
	public boolean mayHaveDefaultJoinTable() {
		return getAssociationOverride().isVirtual() && this.usesJoinTableJoiningStrategy();
	}

	@Override
	public void setJoinColumnJoiningStrategy() {
		super.setJoinColumnJoiningStrategy();
		this.joinTableJoiningStrategy.removeStrategy();
	}

	public JptValidator buildJoinTableJoinColumnValidator(JoinColumn column, JoinColumn.Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
		return getAssociationOverride().getOwner().buildJoinTableJoinColumnValidator(getAssociationOverride(), column, owner, textRangeResolver);
	}

	public JptValidator buildJoinTableInverseJoinColumnValidator(JoinColumn column, Owner owner, JoinColumnTextRangeResolver textRangeResolver) {
		return getAssociationOverride().getOwner().buildJoinTableInverseJoinColumnValidator(getAssociationOverride(), column, owner, textRangeResolver);
	}
}
