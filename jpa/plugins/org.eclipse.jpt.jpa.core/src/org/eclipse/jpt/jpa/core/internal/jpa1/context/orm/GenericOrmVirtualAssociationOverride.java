/*******************************************************************************
 * Copyright (c) 2010, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JoinTable;
import org.eclipse.jpt.jpa.core.context.Relationship;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.VirtualOverrideRelationship;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedAssociationOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualAssociationOverride;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.jpa2.context.AssociationOverride2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmAssociationOverrideContainer2_0;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Virtual <code>orm.xml</code> association override
 */
public class GenericOrmVirtualAssociationOverride
	extends AbstractOrmVirtualOverride<OrmAssociationOverrideContainer>
	implements OrmVirtualAssociationOverride, AssociationOverride2_0
{
	protected final VirtualOverrideRelationship relationship;


	public GenericOrmVirtualAssociationOverride(OrmAssociationOverrideContainer parent, String name) {
		super(parent, name);
		this.relationship = this.buildRelationship();
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.relationship.update(monitor);
	}

	@Override
	public OrmSpecifiedAssociationOverride convertToSpecified() {
		return (OrmSpecifiedAssociationOverride) super.convertToSpecified();
	}

	public RelationshipMapping getMapping() {
		return this.getContainer().getRelationshipMapping(this.name);
	}

	protected OrmAssociationOverrideContainer2_0 getContainer2_0() {
		return (OrmAssociationOverrideContainer2_0) this.getContainer();
	}


	// ********** relationship **********

	public VirtualOverrideRelationship getRelationship() {
		return this.relationship;
	}

	/**
	 * The relationship should be available (since its presence precipitated the
	 * creation of the virtual override).
	 */
	protected VirtualOverrideRelationship buildRelationship() {
		return this.getContextModelFactory().buildOrmVirtualOverrideRelationship(this);
	}

	public Relationship resolveOverriddenRelationship() {
		return this.getContainer().resolveOverriddenRelationship(this.name);
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
}
