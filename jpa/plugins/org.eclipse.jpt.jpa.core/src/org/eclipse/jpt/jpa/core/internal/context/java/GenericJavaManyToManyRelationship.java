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
import org.eclipse.jpt.jpa.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaManyToManyRelationship;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaMappingJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.resource.java.OwnableRelationshipMappingAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaManyToManyRelationship
	extends AbstractJavaMappingRelationship<JavaManyToManyMapping>
	implements JavaManyToManyRelationship
{
	protected final SpecifiedMappedByRelationshipStrategy mappedByStrategy;

	protected final JavaSpecifiedJoinTableRelationshipStrategy joinTableStrategy;


	public GenericJavaManyToManyRelationship(JavaManyToManyMapping parent) {
		super(parent);
		this.mappedByStrategy = this.buildMappedByStrategy();
		this.joinTableStrategy = this.buildJoinTableStrategy();

		this.strategy = this.buildStrategy();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.mappedByStrategy.synchronizeWithResourceModel(monitor);
		this.joinTableStrategy.synchronizeWithResourceModel(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.mappedByStrategy.update(monitor);
		this.joinTableStrategy.update(monitor);
	}


	// ********** strategy **********

	@Override
	protected SpecifiedRelationshipStrategy buildStrategy() {
		if (this.mappedByStrategy.getMappedByAttribute() != null) {
			return this.mappedByStrategy;
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

	public void setStrategyToMappedBy() {
		this.mappedByStrategy.addStrategy();
		this.joinTableStrategy.removeStrategy();
		this.updateStrategy();
	}

	public boolean mayBeMappedBy(AttributeMapping mapping) {
		return mapping.getKey() == MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY;
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

	public void setStrategyToJoinTable() {
		// join table is default option, so no need to add to resource
		this.mappedByStrategy.removeStrategy();
		this.updateStrategy();
	}

	public boolean mayHaveDefaultJoinTable() {
		return this.getMappedByStrategy().getMappedByAttribute() == null;
	}

	protected JavaSpecifiedJoinTableRelationshipStrategy buildJoinTableStrategy() {
		return new GenericJavaMappingJoinTableRelationshipStrategy(this);
	}


	// ********** misc **********

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
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}

		result = this.mappedByStrategy.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}

		return this.joinTableStrategy.getCompletionProposals(pos);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.mappedByStrategy.validate(messages, reporter);
		this.joinTableStrategy.validate(messages, reporter);
	}
}
