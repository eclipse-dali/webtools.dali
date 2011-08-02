/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.MappedByRelationship;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumnRelationship;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTableRelationship;
import org.eclipse.jpt.jpa.core.context.Relationship;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.java.JavaMappedByRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPrimaryKeyJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaMappingJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.NullJavaJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaMappingJoinColumnRelationshipStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaMappingJoinTableRelationshipStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaMappingMappedByRelationshipStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaMappingPrimaryKeyJoinColumnRelationshipStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaMappingRelationshipStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaOneToOneRelationship2_0;
import org.eclipse.jpt.jpa.core.resource.java.OwnableRelationshipMappingAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaOneToOneRelationship
	extends AbstractJavaMappingRelationship<JavaOneToOneMapping>
	implements JavaOneToOneRelationship2_0
{
	protected final JavaMappingMappedByRelationshipStrategy2_0 mappedByStrategy;

	protected final JavaMappingPrimaryKeyJoinColumnRelationshipStrategy2_0 primaryKeyJoinColumnStrategy;

	// JPA 2.0
	protected final JavaMappingJoinTableRelationshipStrategy2_0 joinTableStrategy;

	protected final JavaMappingJoinColumnRelationshipStrategy2_0 joinColumnStrategy;


	public GenericJavaOneToOneRelationship(JavaOneToOneMapping parent) {
		super(parent);
		this.mappedByStrategy = this.buildMappedByStrategy();
		this.primaryKeyJoinColumnStrategy = this.buildPrimaryKeyJoinColumnStrategy();
		this.joinTableStrategy = this.buildJoinTableStrategy();
		this.joinColumnStrategy = this.buildJoinColumnStrategy();

		this.strategy = this.buildStrategy();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.mappedByStrategy.synchronizeWithResourceModel();
		this.primaryKeyJoinColumnStrategy.synchronizeWithResourceModel();
		this.joinTableStrategy.synchronizeWithResourceModel();
		this.joinColumnStrategy.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
		this.mappedByStrategy.update();
		this.primaryKeyJoinColumnStrategy.update();
		this.joinTableStrategy.update();
		this.joinColumnStrategy.update();
	}


	// ********** strategy **********

	@Override
	protected JavaMappingRelationshipStrategy2_0 buildStrategy() {
		if (this.mappedByStrategy.getMappedByAttribute() != null) {
			return this.mappedByStrategy;
		}
		if (this.primaryKeyJoinColumnStrategy.hasPrimaryKeyJoinColumns()) {
			return this.primaryKeyJoinColumnStrategy;
		}
		if (this.isJpa2_0Compatible()) {
			if (this.joinTableStrategy.getJoinTable() != null) {
				return this.joinTableStrategy;
			}
		}
		return this.joinColumnStrategy;
	}


	// ********** mapped by strategy **********

	public JavaMappedByRelationshipStrategy getMappedByStrategy() {
		return this.mappedByStrategy;
	}

	public boolean strategyIsMappedBy() {
		return this.strategy == this.mappedByStrategy;
	}

	public void setStrategyToMappedBy() {
		this.mappedByStrategy.addStrategy();
		this.joinColumnStrategy.removeStrategy();
		this.primaryKeyJoinColumnStrategy.removeStrategy();
		this.joinTableStrategy.removeStrategy();
		this.updateStrategy();
	}

	public boolean mayBeMappedBy(AttributeMapping mapping) {
		return mapping.getKey() == MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}

	protected JavaMappingMappedByRelationshipStrategy2_0 buildMappedByStrategy() {
		return new GenericJavaMappedByRelationshipStrategy(this);
	}


	// ********** primary key join column strategy **********

	public JavaPrimaryKeyJoinColumnRelationshipStrategy getPrimaryKeyJoinColumnStrategy() {
		return this.primaryKeyJoinColumnStrategy;
	}

	public boolean strategyIsPrimaryKeyJoinColumn() {
		return this.strategy == this.primaryKeyJoinColumnStrategy;
	}

	public void setStrategyToPrimaryKeyJoinColumn() {
		this.primaryKeyJoinColumnStrategy.addStrategy();
		this.mappedByStrategy.removeStrategy();
		this.joinColumnStrategy.removeStrategy();
		this.joinTableStrategy.removeStrategy();
		this.updateStrategy();
	}

	protected JavaMappingPrimaryKeyJoinColumnRelationshipStrategy2_0 buildPrimaryKeyJoinColumnStrategy() {
		return new GenericJavaPrimaryKeyJoinColumnRelationshipStrategy(this);
	}


	// ********** join table strategy **********

	public JavaJoinTableRelationshipStrategy getJoinTableStrategy() {
		return this.joinTableStrategy;
	}

	public boolean strategyIsJoinTable() {
		return this.strategy == this.joinTableStrategy;
	}

	public final void setStrategyToJoinTable() {
		this.joinTableStrategy.addStrategy();
		this.mappedByStrategy.removeStrategy();
		this.joinColumnStrategy.removeStrategy();
		this.primaryKeyJoinColumnStrategy.removeStrategy();
		this.updateStrategy();
	}

	public boolean mayHaveDefaultJoinTable() {
		return false;
	}

	protected JavaMappingJoinTableRelationshipStrategy2_0 buildJoinTableStrategy() {
		return this.isJpa2_0Compatible() ?
				new GenericJavaMappingJoinTableRelationshipStrategy(this) :
				new NullJavaJoinTableRelationshipStrategy(this);
	}


	// ********** join column strategy **********

	public JavaJoinColumnRelationshipStrategy getJoinColumnStrategy() {
		return this.joinColumnStrategy;
	}

	public boolean strategyIsJoinColumn() {
		return this.strategy == this.joinColumnStrategy;
	}

	public void setStrategyToJoinColumn() {
		// join column strategy is the default; so no need to add stuff,
		// just remove all the others
		this.mappedByStrategy.removeStrategy();
		this.primaryKeyJoinColumnStrategy.removeStrategy();
		this.joinTableStrategy.removeStrategy();
		this.updateStrategy();
	}

	public boolean mayHaveDefaultJoinColumn() {
		return (this.mappedByStrategy.getMappedByAttribute() == null) &&
				(this.primaryKeyJoinColumnStrategy.getPrimaryKeyJoinColumnsSize() == 0) &&
				(this.joinTableStrategy.getJoinTable() == null);
	}

	protected JavaMappingJoinColumnRelationshipStrategy2_0 buildJoinColumnStrategy() {
		return new GenericJavaMappingJoinColumnRelationshipStrategy(this);
	}


	// ********** conversions **********

	public void initializeOn(Relationship newRelationship) {
		newRelationship.initializeFromMappedByRelationship(this);
		newRelationship.initializeFromJoinTableRelationship(this);
		newRelationship.initializeFromJoinColumnRelationship(this);
		// no other pk join column relationships yet
	}

	@Override
	public void initializeFromMappedByRelationship(MappedByRelationship oldRelationship) {
		super.initializeFromMappedByRelationship(oldRelationship);
		this.mappedByStrategy.initializeFrom(oldRelationship.getMappedByStrategy());
	}

	@Override
	public void initializeFromJoinTableRelationship(ReadOnlyJoinTableRelationship oldRelationship) {
		super.initializeFromJoinTableRelationship(oldRelationship);
		this.joinTableStrategy.initializeFrom(oldRelationship.getJoinTableStrategy());
	}

	@Override
	public void initializeFromJoinColumnRelationship(ReadOnlyJoinColumnRelationship oldRelationship) {
		super.initializeFromJoinColumnRelationship(oldRelationship);
		this.joinColumnStrategy.initializeFrom(oldRelationship.getJoinColumnStrategy());
	}


	// ********** misc **********

	@Override
	public JavaOneToOneMapping getMapping() {
		return this.getParent();
	}

	public OwnableRelationshipMappingAnnotation getMappingAnnotation() {
		return this.getMapping().getMappingAnnotation();
	}

	public OwnableRelationshipMappingAnnotation getMappingAnnotationForUpdate() {
		return this.getMapping().getAnnotationForUpdate();
	}

	public boolean isOwner() {
		return this.mappedByStrategy.getMappedByAttribute() == null;
	}

	public boolean isOwnedBy(RelationshipMapping mapping) {
		return this.mappedByStrategy.relationshipIsOwnedBy(mapping);
	}


	// ********** Java completion proposals **********

	@Override
	public Iterable<String> getJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}

		result = this.mappedByStrategy.getJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}

		result = this.primaryKeyJoinColumnStrategy.getJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}

		result = this.joinTableStrategy.getJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}

		return this.joinColumnStrategy.getJavaCompletionProposals(pos, filter, astRoot);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.mappedByStrategy.validate(messages, reporter, astRoot);
		this.primaryKeyJoinColumnStrategy.validate(messages, reporter, astRoot);
		this.joinColumnStrategy.validate(messages, reporter, astRoot);
		this.joinTableStrategy.validate(messages, reporter, astRoot);
	}
}
