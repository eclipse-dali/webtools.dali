/*******************************************************************************
 * Copyright (c) 2008, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JoinTable;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedOverrideRelationship;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualAssociationOverride;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmAssociationOverrideContainer2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmSpecifiedAssociationOverride2_0;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAssociationOverride;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Specified <code>orm.xml</code> association override
 */
public class GenericOrmSpecifiedAssociationOverride
	extends AbstractOrmSpecifiedOverride<OrmAssociationOverrideContainer, XmlAssociationOverride>
	implements OrmSpecifiedAssociationOverride2_0
{
	protected final OrmSpecifiedOverrideRelationship relationship;


	public GenericOrmSpecifiedAssociationOverride(OrmAssociationOverrideContainer parent, XmlAssociationOverride xmlOverride) {
		super(parent, xmlOverride);
		this.relationship = this.buildRelationship();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.relationship.synchronizeWithResourceModel();
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.relationship.update(monitor);
	}


	// ********** specified/virtual **********

	@Override
	public OrmVirtualAssociationOverride convertToVirtual() {
		return (OrmVirtualAssociationOverride) super.convertToVirtual();
	}


	// ********** relationship **********

	public OrmSpecifiedOverrideRelationship getRelationship() {
		return this.relationship;
	}

	protected OrmSpecifiedOverrideRelationship buildRelationship() {
		return this.getContextModelFactory().buildOrmOverrideRelationship(this);
	}


	// ********** misc **********

	protected OrmAssociationOverrideContainer2_0 getContainer2_0() {
		return (OrmAssociationOverrideContainer2_0) this.getContainer();
	}

	public RelationshipMapping getMapping() {
		return this.getContainer().getRelationshipMapping(this.name);
	}

	public void initializeFrom(OrmVirtualAssociationOverride virtualOverride) {
		super.initializeFrom(virtualOverride);
		this.relationship.initializeFrom(virtualOverride.getRelationship());
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.relationship.validate(messages, reporter);
	}

	public JpaValidator buildJoinTableValidator(JoinTable table) {
		return this.getContainer2_0().buildJoinTableValidator(this, table);
	}

	public JpaValidator buildJoinTableJoinColumnValidator(JoinColumn column, JoinColumn.ParentAdapter parentAdapter) {
		return this.getContainer2_0().buildJoinTableJoinColumnValidator(this, column, parentAdapter);
	}

	public JpaValidator buildJoinTableInverseJoinColumnValidator(JoinColumn column, JoinColumn.ParentAdapter parentAdapter) {
		return this.getContainer2_0().buildJoinTableInverseJoinColumnValidator(this, column, parentAdapter);
	}

	// ********** completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		result = this.relationship.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		return null;
	}
}
