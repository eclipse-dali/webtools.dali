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

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.SpecifiedMappedByRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.SpecifiedRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaMappingJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.NullJavaJoinColumnRelationshipStrategy;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaOneToManyRelationship2_0;
import org.eclipse.jpt.jpa.core.resource.java.OneToManyAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaOneToManyRelationship
	extends AbstractJavaMappingRelationship<JavaOneToManyMapping>
	implements JavaOneToManyRelationship2_0
{
	protected final SpecifiedMappedByRelationshipStrategy mappedByStrategy;

	protected final JavaSpecifiedJoinTableRelationshipStrategy joinTableStrategy;

	// JPA 2.0 or EclipseLink
	protected final boolean supportsJoinColumnStrategy;
	protected final JavaSpecifiedJoinColumnRelationshipStrategy joinColumnStrategy;


	public GenericJavaOneToManyRelationship(JavaOneToManyMapping parent, boolean supportsJoinColumnStrategy) {
		super(parent);
		this.mappedByStrategy = this.buildMappedByStrategy();
		this.supportsJoinColumnStrategy = supportsJoinColumnStrategy;
		this.joinColumnStrategy = this.buildJoinColumnStrategy();

		// build join table strategy last since it's dependent on the other strategies
		this.joinTableStrategy = this.buildJoinTableStrategy();

		this.strategy = this.buildStrategy();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.mappedByStrategy.synchronizeWithResourceModel(monitor);
		this.joinColumnStrategy.synchronizeWithResourceModel(monitor);
		this.joinTableStrategy.synchronizeWithResourceModel(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.mappedByStrategy.update(monitor);
		this.joinColumnStrategy.update(monitor);
		this.joinTableStrategy.update(monitor);
	}


	// ********** strategy **********

	@Override
	protected SpecifiedRelationshipStrategy buildStrategy() {
		if (this.mappedByStrategy.getMappedByAttribute() != null) {
			return this.mappedByStrategy;
		}
		if (this.supportsJoinColumnStrategy) {
			if (this.joinColumnStrategy.hasSpecifiedJoinColumns()) {
				return this.joinColumnStrategy;
			}
		}
		return this.joinTableStrategy;
	}


	// ********** mapped by strategy **********

	public SpecifiedMappedByRelationshipStrategy getMappedByStrategy() {
		return this.mappedByStrategy;
	}

	public boolean strategyIsMappedBy() {
		return this.strategy == this.mappedByStrategy;
	}

	public final void setStrategyToMappedBy() {
		this.mappedByStrategy.addStrategy();
		this.joinTableStrategy.removeStrategy();
		this.joinColumnStrategy.removeStrategy();
		this.updateStrategy();
	}

	public boolean mayBeMappedBy(AttributeMapping mapping) {
		String key = mapping.getKey();
		if (key == MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY) {
			return true;
		}
		if (this.supportsJoinColumnStrategy) {
			return key == MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
		}
		return false;
	}

	protected SpecifiedMappedByRelationshipStrategy buildMappedByStrategy() {
		return new GenericJavaMappedByRelationshipStrategy(this);
	}


	// ********** join table strategy **********

	public JavaSpecifiedJoinTableRelationshipStrategy getJoinTableStrategy() {
		return this.joinTableStrategy;
	}

	public boolean strategyIsJoinTable() {
		return this.strategy == this.joinTableStrategy;
	}

	public final void setStrategyToJoinTable() {
		// join table is default, so no need to add annotation
		this.mappedByStrategy.removeStrategy();
		this.joinColumnStrategy.removeStrategy();
		this.updateStrategy();
	}

	public boolean mayHaveDefaultJoinTable() {
		return (this.mappedByStrategy.getMappedByAttribute() == null) &&
				! this.joinColumnStrategy.hasSpecifiedJoinColumns();
	}

	protected JavaSpecifiedJoinTableRelationshipStrategy buildJoinTableStrategy() {
		return new GenericJavaMappingJoinTableRelationshipStrategy(this);
	}


	// ********** join column strategy **********

	public JavaSpecifiedJoinColumnRelationshipStrategy getJoinColumnStrategy() {
		return this.joinColumnStrategy;
	}

	public boolean strategyIsJoinColumn() {
		return this.strategy == this.joinColumnStrategy;
	}

	public void setStrategyToJoinColumn() {
		this.joinColumnStrategy.addStrategy();
		this.mappedByStrategy.removeStrategy();
		this.joinTableStrategy.removeStrategy();
		this.updateStrategy();
	}

	public boolean mayHaveDefaultJoinColumn() {
		return false;
	}

	protected JavaSpecifiedJoinColumnRelationshipStrategy buildJoinColumnStrategy() {
		return this.supportsJoinColumnStrategy ?
				new GenericJavaMappingJoinColumnRelationshipStrategy(this, true) :  // true = target foreign key
				new NullJavaJoinColumnRelationshipStrategy(this);
	}


	// ********** misc **********

	public OneToManyAnnotation getMappingAnnotation() {
		return this.getMapping().getMappingAnnotation();
	}

	public OneToManyAnnotation getMappingAnnotationForUpdate() {
		return this.getMapping().getAnnotationForUpdate();
	}

	public boolean isOwner() {
		return this.mappedByStrategy.getMappedByAttribute() == null;
	}

	public boolean isOwnedBy(RelationshipMapping mapping) {
		return this.mappedByStrategy.relationshipIsOwnedBy(mapping);
	}

	@Override
	public boolean isTargetForeignKey() {
		return this.joinColumnStrategy.isTargetForeignKey();
	}


	// ********** Java completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}

		result = this.mappedByStrategy.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}

		result = this.joinTableStrategy.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}

		return this.joinColumnStrategy.getCompletionProposals(pos);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.mappedByStrategy.validate(messages, reporter);
		this.joinTableStrategy.validate(messages, reporter);
		this.joinColumnStrategy.validate(messages, reporter);
	}
}
