/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.MappedByRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.ReadOnlyRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaMappedByRelationship;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationDescriptionMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.core.jpa2.context.MappingRelationshipStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ReadOnlyOverrideRelationship2_0;
import org.eclipse.jpt.jpa.core.resource.java.OwnableRelationshipMappingAnnotation;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.osgi.util.NLS;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaMappedByRelationshipStrategy
	extends AbstractJavaJpaContextNode
	implements MappingRelationshipStrategy2_0, MappedByRelationshipStrategy
{
	protected String mappedByAttribute;


	public GenericJavaMappedByRelationshipStrategy(JavaMappedByRelationship parent) {
		super(parent);
		this.mappedByAttribute = this.buildMappedByAttribute();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setMappedByAttribute_(this.buildMappedByAttribute());
	}


	// ********** mapped by attribute **********

	public String getMappedByAttribute() {
		return this.mappedByAttribute;
	}

	public void setMappedByAttribute(String mappedByAttribute) {
		if (this.valuesAreDifferent(mappedByAttribute, this.mappedByAttribute)) {
			this.getMappingAnnotationForUpdate().setMappedBy(mappedByAttribute);
			this.setMappedByAttribute_(mappedByAttribute);
		}
	}

	protected void setMappedByAttribute_(String mappedByAttribute) {
		String old = this.mappedByAttribute;
		this.mappedByAttribute = mappedByAttribute;
		this.firePropertyChanged(MAPPED_BY_ATTRIBUTE_PROPERTY, old, mappedByAttribute);
	}

	protected String buildMappedByAttribute() {
		OwnableRelationshipMappingAnnotation annotation = this.getMappingAnnotation();
		return (annotation == null) ? null : annotation.getMappedBy();
	}


	// ********** misc **********

	protected OwnableRelationshipMappingAnnotation getMappingAnnotation() {
		return this.getRelationship().getMappingAnnotation();
	}

	protected OwnableRelationshipMappingAnnotation getMappingAnnotationForUpdate() {
		return this.getRelationship().getMappingAnnotationForUpdate();
	}

	@Override
	public JavaMappedByRelationship getParent() {
		return (JavaMappedByRelationship) super.getParent();
	}

	public JavaMappedByRelationship getRelationship() {
		return this.getParent();
	}

	public void initializeFrom(MappedByRelationshipStrategy oldStrategy) {
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
		//this will not be called if getRelationshipOwner() is null
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
		return Tools.valuesAreEqual(thisEntityName, otherEntityName) &&
				Tools.valuesAreEqual(this.mappedByAttribute, otherMapping.getName());
	}

	protected String getEntityName() {
		Entity entity = this.getRelationship().getEntity();
		return (entity == null) ? null : entity.getName();
	}

	public ReadOnlyRelationshipStrategy selectOverrideStrategy(ReadOnlyOverrideRelationship2_0 overrideRelationship) {
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


	// ********** java completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		OwnableRelationshipMappingAnnotation annotation = this.getMappingAnnotation();
		if ((annotation != null) && annotation.mappedByTouches(pos)) {
			result = this.getJavaCandidateMappedByAttributeNames();
		}
		return result;
	}

	public Iterable<String> getCandidateMappedByAttributeNames() {
		return this.getRelationshipMapping().getTargetEntityNonTransientAttributeNames();
	}

	protected Iterable<String> getJavaCandidateMappedByAttributeNames() {
		return StringTools.convertToJavaStringLiteralContents(this.getCandidateMappedByAttributeNames());
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
					JpaValidationMessages.MAPPING_UNRESOLVED_MAPPED_BY,
					new String[] {this.mappedByAttribute}
				)
			);
			return;
		}

		if ( ! this.getRelationship().mayBeMappedBy(mappedByMapping)) {
			messages.add(
				this.buildMessage(
					JpaValidationMessages.MAPPING_INVALID_MAPPED_BY,
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
					JpaValidationMessages.MAPPING_MAPPED_BY_ON_BOTH_SIDES,
					new String[] {this.mappedByAttribute}
				)
			);
		}
	}

	protected IMessage buildMessage(String msgID, String[] parms) {
		PersistentAttribute attribute = this.getRelationshipMapping().getPersistentAttribute();
		String attributeDescription = attribute.isVirtual() ?
				JpaValidationDescriptionMessages.VIRTUAL_ATTRIBUTE_DESC :
				JpaValidationDescriptionMessages.ATTRIBUTE_DESC;
		TextRange textRange = attribute.isVirtual() ?
				attribute.getValidationTextRange() :
				this.getValidationTextRange();
		attributeDescription = NLS.bind(attributeDescription, attribute.getName());
		parms = ArrayTools.add(parms, 0, attributeDescription);
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				msgID,
				parms,
				this,
				textRange
			);
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getAnnotationMappedByTextRange();
		return (textRange != null) ? textRange : this.getRelationship().getValidationTextRange();
	}

	protected TextRange getAnnotationMappedByTextRange() {
		OwnableRelationshipMappingAnnotation annotation = this.getMappingAnnotation();
		return (annotation == null) ? null : annotation.getMappedByTextRange();
	}
}
