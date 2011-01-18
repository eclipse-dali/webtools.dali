/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.MappedByJoiningStrategy;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.java.JavaMappedByJoiningStrategy;
import org.eclipse.jpt.core.context.java.JavaOwnableRelationshipReference;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationDescriptionMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.OwnableRelationshipMappingAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.ArrayTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.Tools;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.osgi.util.NLS;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaMappedByJoiningStrategy
	extends AbstractJavaJpaContextNode
	implements JavaMappedByJoiningStrategy
{
	protected String mappedByAttribute;


	public GenericJavaMappedByJoiningStrategy(JavaOwnableRelationshipReference parent) {
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
		return this.getRelationshipReference().getMappingAnnotation();
	}

	protected OwnableRelationshipMappingAnnotation getMappingAnnotationForUpdate() {
		return this.getRelationshipReference().getMappingAnnotationForUpdate();
	}

	@Override
	public JavaOwnableRelationshipReference getParent() {
		return (JavaOwnableRelationshipReference) super.getParent();
	}

	public JavaOwnableRelationshipReference getRelationshipReference() {
		return this.getParent();
	}

	public void initializeFrom(MappedByJoiningStrategy oldStrategy) {
		this.setMappedByAttribute(oldStrategy.getMappedByAttribute());
	}

	public String getTableName() {
		RelationshipMapping owner = this.getRelationshipOwner();
		return (owner == null) ? null : owner.getRelationshipReference().getPredominantJoiningStrategy().getTableName();
	}

	public Table resolveDbTable(String tableName) {
		RelationshipMapping owner = this.getRelationshipOwner();
		return (owner == null) ? null : owner.getRelationshipReference().getPredominantJoiningStrategy().resolveDbTable(tableName);
	}

	public boolean tableNameIsInvalid(String tableName) {
		RelationshipMapping owner = this.getRelationshipOwner();
		return (owner != null) && owner.getRelationshipReference().getPredominantJoiningStrategy().tableNameIsInvalid(tableName);
	}

	public String getColumnTableNotValidDescription() {
		//this will not be called if getRelationshipOwner() is null
		return this.getRelationshipOwner().getRelationshipReference().getPredominantJoiningStrategy().getColumnTableNotValidDescription();
	}

	protected RelationshipMapping getRelationshipOwner() {
		return this.getRelationshipMapping().getRelationshipOwner();
	}

	public boolean isOverridable() {
		return false;
	}

	protected RelationshipMapping getRelationshipMapping() {
		return this.getRelationshipReference().getMapping();
	}

	public boolean relationshipIsOwnedBy(RelationshipMapping otherMapping) {
		String thisEntityName = this.getEntityName();
		Entity otherEntity = otherMapping.getResolvedTargetEntity();
		String otherEntityName = (otherEntity == null) ? null : otherEntity.getName();
		return Tools.valuesAreEqual(thisEntityName, otherEntityName) &&
				Tools.valuesAreEqual(this.mappedByAttribute, otherMapping.getName());
	}

	protected String getEntityName() {
		Entity entity = this.getRelationshipReference().getEntity();
		return (entity == null) ? null : entity.getName();
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
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		OwnableRelationshipMappingAnnotation annotation = this.getMappingAnnotation();
		if ((annotation != null) && annotation.mappedByTouches(pos, astRoot)) {
			result = this.javaCandidateMappedByAttributeNames(filter);
		}
		return result;
	}

	public Iterator<String> candidateMappedByAttributeNames() {
		return this.getRelationshipMapping().allTargetEntityAttributeNames();
	}

	protected Iterator<String> candidateMappedByAttributeNames(Filter<String> filter) {
		return new FilteringIterator<String>(this.candidateMappedByAttributeNames(), filter);
	}

	protected Iterator<String> javaCandidateMappedByAttributeNames(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.candidateMappedByAttributeNames(filter));
	}


	// ********** validation **********

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		OwnableRelationshipMappingAnnotation annotation = this.getMappingAnnotation();
		return (annotation == null) ? null : annotation.getMappedByTextRange(astRoot);
	}

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);

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
					new String[] {this.mappedByAttribute},
					astRoot
				)
			);
			return;
		}

		if ( ! this.getRelationshipReference().mayBeMappedBy(mappedByMapping)) {
			messages.add(
				this.buildMessage(
					JpaValidationMessages.MAPPING_INVALID_MAPPED_BY,
					new String[] {this.mappedByAttribute},
					astRoot
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
					new String[] {this.mappedByAttribute},
					astRoot
				)
			);
		}
	}

	protected IMessage buildMessage(String msgID, String[] parms, CompilationUnit astRoot) {
		PersistentAttribute attribute = this.getRelationshipMapping().getPersistentAttribute();
		String attributeDescription = NLS.bind(JpaValidationDescriptionMessages.ATTRIBUTE_DESC, attribute.getName());
		parms = ArrayTools.add(parms, 0, attributeDescription);
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				msgID,
				parms,
				this,
				this.getValidationTextRange(astRoot)
			);
	}
}
