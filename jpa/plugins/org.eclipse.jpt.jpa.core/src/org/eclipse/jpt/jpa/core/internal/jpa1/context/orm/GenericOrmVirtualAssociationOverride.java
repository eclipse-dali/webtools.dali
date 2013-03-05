/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import java.util.List;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyRelationship;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.VirtualOverrideRelationship;
import org.eclipse.jpt.jpa.core.context.orm.OrmAssociationOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualAssociationOverride;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.jpa2.context.ReadOnlyAssociationOverride2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmAssociationOverrideContainer2_0;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Virtual <code>orm.xml</code> association override
 */
public class GenericOrmVirtualAssociationOverride
	extends AbstractOrmVirtualOverride<OrmAssociationOverrideContainer>
	implements OrmVirtualAssociationOverride, ReadOnlyAssociationOverride2_0
{
	protected final VirtualOverrideRelationship relationship;


	public GenericOrmVirtualAssociationOverride(OrmAssociationOverrideContainer parent, String name) {
		super(parent, name);
		this.relationship = this.buildRelationship();
	}

	@Override
	public void update() {
		super.update();
		this.relationship.update();
	}

	@Override
	public OrmAssociationOverride convertToSpecified() {
		return (OrmAssociationOverride) super.convertToSpecified();
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

	public ReadOnlyRelationship resolveOverriddenRelationship() {
		return this.getContainer().resolveOverriddenRelationship(this.name);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.relationship.validate(messages, reporter);
	}

	public JptValidator buildJoinTableValidator(ReadOnlyJoinTable table) {
		return this.getContainer2_0().buildJoinTableValidator(this, table);
	}

	public JptValidator buildJoinTableJoinColumnValidator(ReadOnlyJoinColumn column, ReadOnlyJoinColumn.Owner owner) {
		return this.getContainer2_0().buildJoinTableJoinColumnValidator(this, column, owner);
	}

	public JptValidator buildJoinTableInverseJoinColumnValidator(ReadOnlyJoinColumn column, ReadOnlyJoinColumn.Owner owner) {
		return this.getContainer2_0().buildJoinTableInverseJoinColumnValidator(this, column, owner);
	}
}
