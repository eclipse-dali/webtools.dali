/*******************************************************************************
 * Copyright (c) 2009, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.RelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.SpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmMappedByRelationship;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedMappedByRelationshipStrategy;
import org.eclipse.jpt.jpa.core.jpa2.context.OverrideRelationship2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.SpecifiedMappingRelationshipStrategy2_0;
import org.eclipse.jpt.jpa.core.resource.orm.XmlMappedByMapping;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationArgumentMessages;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.osgi.util.NLS;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericOrmMappedByRelationshipStrategy
	extends AbstractOrmXmlContextModel<OrmMappedByRelationship>
	implements SpecifiedMappingRelationshipStrategy2_0, OrmSpecifiedMappedByRelationshipStrategy
{
	protected String mappedByAttribute;


	public GenericOrmMappedByRelationshipStrategy(OrmMappedByRelationship parent) {
		super(parent);
		this.mappedByAttribute = this.getXmlMappedByMapping().getMappedBy();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setMappedByAttribute_(this.getXmlMappedByMapping().getMappedBy());
	}


	// ********** mapped by attribute **********

	public String getMappedByAttribute() {
		return this.mappedByAttribute;
	}

	public void setMappedByAttribute(String mappedByAttribute) {
		this.setMappedByAttribute_(mappedByAttribute);
		this.getXmlMappedByMapping().setMappedBy(mappedByAttribute);
	}

	protected void setMappedByAttribute_(String mappedByAttribute) {
		String old = this.mappedByAttribute;
		this.mappedByAttribute = mappedByAttribute;
		this.firePropertyChanged(MAPPED_BY_ATTRIBUTE_PROPERTY, old, mappedByAttribute);
	}


	// ********** misc **********

	public OrmMappedByRelationship getRelationship() {
		return this.parent;
	}

	protected XmlMappedByMapping getXmlMappedByMapping() {
		return this.getRelationship().getXmlContainer();
	}

	public void initializeFrom(OrmSpecifiedMappedByRelationshipStrategy oldStrategy) {
		this.setMappedByAttribute(oldStrategy.getMappedByAttribute());
	}

	public String getTableName() {
		RelationshipMapping owner = this.getRelationshipOwner();
		return (owner == null) ? null : owner.getRelationship().getStrategy().getTableName();
	}

	public Table resolveDbTable(String tableName) {
		RelationshipMapping owner = this.getRelationshipOwner();
		return (owner == null) ? null : owner.getRelationship().getStrategy().resolveDbTable(tableName);
	}

	public boolean tableNameIsInvalid(String tableName) {
		RelationshipMapping owner = this.getRelationshipOwner();
		return (owner != null) && owner.getRelationship().getStrategy().tableNameIsInvalid(tableName);
	}

	public String getColumnTableNotValidDescription() {
		// this will not be called if getRelationshipOwner() returns null
		return this.getRelationshipOwner().getRelationship().getStrategy().getColumnTableNotValidDescription();
	}

	protected RelationshipMapping getRelationshipOwner() {
		return this.getRelationshipMapping().getRelationshipOwner();
	}

	public boolean isOverridable() {
		return false;
	}

	protected RelationshipMapping getRelationshipMapping() {
		return this.getRelationship().getMapping();
	}

	public boolean relationshipIsOwnedBy(RelationshipMapping otherMapping) {
		String thisEntityName = this.getEntityName();
		Entity otherEntity = otherMapping.getResolvedTargetEntity();
		String otherEntityName = (otherEntity == null) ? null : otherEntity.getName();
		return ObjectTools.equals(thisEntityName, otherEntityName) &&
				ObjectTools.equals(this.mappedByAttribute, otherMapping.getName());
	}

	protected String getEntityName() {
		Entity entity = this.getRelationship().getEntity();
		return (entity == null) ? null : entity.getName();
	}

	public RelationshipStrategy selectOverrideStrategy(OverrideRelationship2_0 overrideRelationship) {
		return null;  // mapped-by strategies cannot be overridden
	}

	public void addStrategy() {
		if (this.mappedByAttribute == null) {
			this.setMappedByAttribute(""); //$NON-NLS-1$
		}
	}

	public void removeStrategy() {
		if (this.mappedByAttribute != null) {
			this.setMappedByAttribute(null);
		}
	}

	public Iterable<String> getCandidateMappedByAttributeNames() {
		return this.getRelationshipMapping().getTargetEntityNonTransientAttributeNames();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);

		if (this.mappedByAttribute == null) {
			return;
		}

		Entity targetEntity = this.getRelationshipMapping().getResolvedTargetEntity();
		if (targetEntity == null) {
			return;  // null target entity is validated elsewhere
		}

		AttributeMapping mappedByMapping = targetEntity.resolveAttributeMapping(this.mappedByAttribute);

		if (mappedByMapping == null) {
			messages.add(
				this.buildMessage(
					JptJpaCoreValidationMessages.MAPPING_UNRESOLVED_MAPPED_BY,
					new String[] {this.mappedByAttribute}
				)
			);
			return;
		}

		if ( ! this.getRelationship().mayBeMappedBy(mappedByMapping)) {
			messages.add(
				this.buildMessage(
					JptJpaCoreValidationMessages.MAPPING_INVALID_MAPPED_BY,
					new String[] {this.mappedByAttribute}
				)
			);
			return;
		}

		// if mappedByMapping is not a relationship owner, then it should have
		// been flagged in above rule (mappedByIsValid)
		if ( ! ((RelationshipMapping) mappedByMapping).isRelationshipOwner()) {
			messages.add(
				this.buildMessage(
					JptJpaCoreValidationMessages.MAPPING_MAPPED_BY_ON_BOTH_SIDES,
					new String[] {this.mappedByAttribute}
				)
			);
		}
	}

	protected IMessage buildMessage(ValidationMessage msg, Object[] args) {
		SpecifiedPersistentAttribute attribute = this.getRelationshipMapping().getPersistentAttribute();
		String attributeDescription = NLS.bind(JptJpaCoreValidationArgumentMessages.ATTRIBUTE_DESC, attribute.getName());
		args = ArrayTools.add(args, 0, attributeDescription);
		return this.buildValidationMessage(this.getValidationTextRange(), msg, args);
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getXmlMappedByMapping().getMappedByTextRange();
		return (textRange != null) ? textRange : this.getRelationship().getValidationTextRange();
	}

	// ********** completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.mappedByTouches(pos)) {
			return this.getCandidateMappedByAttributeNames();
		}
		return null;
	}
	
	protected boolean mappedByTouches(int pos) {
		return this.getXmlMappedByMapping().mappedByTouches(pos);
	}
}
