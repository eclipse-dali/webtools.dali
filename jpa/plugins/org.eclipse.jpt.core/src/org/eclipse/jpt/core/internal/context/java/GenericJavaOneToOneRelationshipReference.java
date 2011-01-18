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
import org.eclipse.jpt.core.context.ReadOnlyJoinColumnEnabledRelationshipReference;
import org.eclipse.jpt.core.context.ReadOnlyJoinTableEnabledRelationshipReference;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.RelationshipReference;
import org.eclipse.jpt.core.context.java.JavaJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.java.JavaJoinTableJoiningStrategy;
import org.eclipse.jpt.core.context.java.JavaJoiningStrategy;
import org.eclipse.jpt.core.context.java.JavaMappedByJoiningStrategy;
import org.eclipse.jpt.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.core.context.java.JavaPrimaryKeyJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaMappingJoinTableJoiningStrategy;
import org.eclipse.jpt.core.internal.jpa1.context.java.NullJavaJoinTableJoiningStrategy;
import org.eclipse.jpt.core.jpa2.context.java.JavaOneToOneRelationshipReference2_0;
import org.eclipse.jpt.core.resource.java.OwnableRelationshipMappingAnnotation;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaOneToOneRelationshipReference
	extends AbstractJavaMappingRelationship<JavaOneToOneMapping>
	implements JavaOneToOneRelationshipReference2_0
{
	protected final JavaMappedByJoiningStrategy mappedByStrategy;

	protected final JavaPrimaryKeyJoinColumnJoiningStrategy primaryKeyJoinColumnStrategy;

	// JPA 2.0
	protected final JavaJoinTableJoiningStrategy joinTableStrategy;

	protected final JavaJoinColumnJoiningStrategy joinColumnStrategy;


	public GenericJavaOneToOneRelationshipReference(JavaOneToOneMapping parent) {
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
	protected JavaJoiningStrategy buildStrategy() {
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

	public JavaMappedByJoiningStrategy getMappedByJoiningStrategy() {
		return this.mappedByStrategy;
	}

	public boolean usesMappedByJoiningStrategy() {
		return this.strategy == this.mappedByStrategy;
	}

	public void setMappedByJoiningStrategy() {
		this.mappedByStrategy.addStrategy();
		this.joinColumnStrategy.removeStrategy();
		this.primaryKeyJoinColumnStrategy.removeStrategy();
		this.joinTableStrategy.removeStrategy();
		this.updateStrategy();
	}

	public boolean mayBeMappedBy(AttributeMapping mappedByMapping) {
		return mappedByMapping.getKey() == MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}

	protected JavaMappedByJoiningStrategy buildMappedByStrategy() {
		return new GenericJavaMappedByJoiningStrategy(this);
	}


	// ********** primary key join column strategy **********

	public JavaPrimaryKeyJoinColumnJoiningStrategy getPrimaryKeyJoinColumnJoiningStrategy() {
		return this.primaryKeyJoinColumnStrategy;
	}

	public boolean usesPrimaryKeyJoinColumnJoiningStrategy() {
		return this.strategy == this.primaryKeyJoinColumnStrategy;
	}

	public void setPrimaryKeyJoinColumnJoiningStrategy() {
		this.primaryKeyJoinColumnStrategy.addStrategy();
		this.mappedByStrategy.removeStrategy();
		this.joinColumnStrategy.removeStrategy();
		this.joinTableStrategy.removeStrategy();
		this.updateStrategy();
	}

	protected JavaPrimaryKeyJoinColumnJoiningStrategy buildPrimaryKeyJoinColumnStrategy() {
		return new GenericJavaPrimaryKeyJoinColumnJoiningStrategy(this);
	}


	// ********** join table strategy **********

	public JavaJoinTableJoiningStrategy getJoinTableJoiningStrategy() {
		return this.joinTableStrategy;
	}

	public boolean usesJoinTableJoiningStrategy() {
		return this.strategy == this.joinTableStrategy;
	}

	public final void setJoinTableJoiningStrategy() {
		this.joinTableStrategy.addStrategy();
		this.mappedByStrategy.removeStrategy();
		this.joinColumnStrategy.removeStrategy();
		this.primaryKeyJoinColumnStrategy.removeStrategy();
		this.updateStrategy();
	}

	public boolean mayHaveDefaultJoinTable() {
		return false;
	}

	protected JavaJoinTableJoiningStrategy buildJoinTableStrategy() {
		return this.isJpa2_0Compatible() ?
				new GenericJavaMappingJoinTableJoiningStrategy(this) :
				new NullJavaJoinTableJoiningStrategy(this);
	}


	// ********** join column strategy **********

	public JavaJoinColumnJoiningStrategy getJoinColumnJoiningStrategy() {
		return this.joinColumnStrategy;
	}

	public boolean usesJoinColumnJoiningStrategy() {
		return this.strategy == this.joinColumnStrategy;
	}

	public void setJoinColumnJoiningStrategy() {
		// join column strategy is the default; so no need to add stuff,
		// just remove all the others
		this.mappedByStrategy.removeStrategy();
		this.primaryKeyJoinColumnStrategy.removeStrategy();
		this.joinTableStrategy.removeStrategy();
		this.updateStrategy();
	}

	public boolean mayHaveDefaultJoinColumn() {
		return (this.mappedByStrategy.getMappedByAttribute() == null) &&
				(this.primaryKeyJoinColumnStrategy.primaryKeyJoinColumnsSize() == 0) &&
				(this.joinTableStrategy.getJoinTable() == null);
	}

	protected JavaJoinColumnJoiningStrategy buildJoinColumnStrategy() {
		return new GenericJavaMappingJoinColumnJoiningStrategy(this);
	}


	// ********** conversions **********

	public void initializeOn(RelationshipReference newRelationship) {
		newRelationship.initializeFromMappedByRelationship(this);
		newRelationship.initializeFromJoinTableRelationship(this);
		newRelationship.initializeFromJoinColumnRelationship(this);
		// no other pk join column relationships yet
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

	@Override
	public void initializeFromJoinColumnRelationship(ReadOnlyJoinColumnEnabledRelationshipReference oldRelationship) {
		super.initializeFromJoinColumnRelationship(oldRelationship);
		this.joinColumnStrategy.initializeFrom(oldRelationship.getJoinColumnJoiningStrategy());
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
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}

		result = this.mappedByStrategy.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}

		result = this.primaryKeyJoinColumnStrategy.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}

		result = this.joinTableStrategy.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}

		return this.joinColumnStrategy.javaCompletionProposals(pos, filter, astRoot);
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
