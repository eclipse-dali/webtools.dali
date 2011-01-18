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
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.OwnableRelationshipReference;
import org.eclipse.jpt.core.context.ReadOnlyJoinTableEnabledRelationshipReference;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.RelationshipReference;
import org.eclipse.jpt.core.context.java.JavaJoinTableJoiningStrategy;
import org.eclipse.jpt.core.context.java.JavaJoiningStrategy;
import org.eclipse.jpt.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.core.context.java.JavaManyToManyRelationshipReference;
import org.eclipse.jpt.core.context.java.JavaMappedByJoiningStrategy;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaMappingJoinTableJoiningStrategy;
import org.eclipse.jpt.core.resource.java.OwnableRelationshipMappingAnnotation;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaManyToManyRelationshipReference
	extends AbstractJavaMappingRelationship<JavaManyToManyMapping>
	implements JavaManyToManyRelationshipReference
{
	protected final JavaMappedByJoiningStrategy mappedByStrategy;

	protected final JavaJoinTableJoiningStrategy joinTableStrategy;


	public GenericJavaManyToManyRelationshipReference(JavaManyToManyMapping parent) {
		super(parent);
		this.mappedByStrategy = this.buildMappedByStrategy();
		this.joinTableStrategy = this.buildJoinTableStrategy();

		this.strategy = this.buildStrategy();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.mappedByStrategy.synchronizeWithResourceModel();
		this.joinTableStrategy.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
		this.mappedByStrategy.update();
		this.joinTableStrategy.update();
	}


	// ********** strategy **********

	@Override
	protected JavaJoiningStrategy buildStrategy() {
		if (this.mappedByStrategy.getMappedByAttribute() != null) {
			return this.mappedByStrategy;
		}
		return this.joinTableStrategy;
	}


	// ********** mapped by strategy **********

	public JavaMappedByJoiningStrategy getMappedByJoiningStrategy() {
		return this.mappedByStrategy;
	}

	public boolean usesMappedByJoiningStrategy() {
		return this.strategy == this.mappedByStrategy;
	}

	public void setMappedByJoiningStrategy() {
		this.mappedByStrategy.addStrategy();
		this.joinTableStrategy.removeStrategy();
		this.updateStrategy();
	}

	public boolean mayBeMappedBy(AttributeMapping mappedByMapping) {
		return mappedByMapping.getKey() == MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY;
	}

	protected JavaMappedByJoiningStrategy buildMappedByStrategy() {
		return new GenericJavaMappedByJoiningStrategy(this);
	}


	// ********** join table strategy **********

	public JavaJoinTableJoiningStrategy getJoinTableJoiningStrategy() {
		return this.joinTableStrategy;
	}

	public boolean usesJoinTableJoiningStrategy() {
		return this.strategy == this.joinTableStrategy;
	}

	public void setJoinTableJoiningStrategy() {
		// join table is default option, so no need to add to resource
		this.mappedByStrategy.removeStrategy();
		this.updateStrategy();
	}

	public boolean mayHaveDefaultJoinTable() {
		return this.getMappedByJoiningStrategy().getMappedByAttribute() == null;
	}

	protected JavaJoinTableJoiningStrategy buildJoinTableStrategy() {
		return new GenericJavaMappingJoinTableJoiningStrategy(this);
	}


	// ********** conversions **********

	public void initializeOn(RelationshipReference newRelationship) {
		newRelationship.initializeFromMappedByRelationship(this);
		newRelationship.initializeFromJoinTableRelationship(this);
	}

	@Override
	public void initializeFromMappedByRelationship(OwnableRelationshipReference oldRelationship) {
		super.initializeFromMappedByRelationship(oldRelationship);
		this.mappedByStrategy.initializeFrom(oldRelationship.getMappedByJoiningStrategy());
	}

	@Override
	public void initializeFromJoinTableRelationship(ReadOnlyJoinTableEnabledRelationshipReference oldRelationship) {
		super.initializeFromJoinTableRelationship(oldRelationship);
		this.joinTableStrategy.initializeFrom(oldRelationship.getJoinTableJoiningStrategy());
	}


	// ********** misc **********

	@Override
	public JavaManyToManyMapping getMapping() {
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
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}

		result = this.mappedByStrategy.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}

		return this.joinTableStrategy.javaCompletionProposals(pos, filter, astRoot);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.mappedByStrategy.validate(messages, reporter, astRoot);
		this.joinTableStrategy.validate(messages, reporter, astRoot);
	}
}
