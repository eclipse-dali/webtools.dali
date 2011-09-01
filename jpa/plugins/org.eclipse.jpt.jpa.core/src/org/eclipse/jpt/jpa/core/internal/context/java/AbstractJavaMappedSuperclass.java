/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.resource.java.JavaResourceMember;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.java.JavaIdClassReference;
import org.eclipse.jpt.jpa.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaQueryContainer;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.PrimaryKeyTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.GenericMappedSuperclassPrimaryKeyValidator;
import org.eclipse.jpt.jpa.core.resource.java.MappedSuperclassAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java mapped superclass
 */
public abstract class AbstractJavaMappedSuperclass
	extends AbstractJavaTypeMapping<MappedSuperclassAnnotation>
	implements JavaMappedSuperclass, JavaQueryContainer.Owner
{
	protected final JavaIdClassReference idClassReference;
	protected final JavaQueryContainer queryContainer;


	protected AbstractJavaMappedSuperclass(JavaPersistentType parent, MappedSuperclassAnnotation mappingAnnotation) {
		super(parent, mappingAnnotation);
		this.idClassReference = this.buildIdClassReference();
		this.queryContainer = this.buildQueryContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.idClassReference.synchronizeWithResourceModel();
		this.queryContainer.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
		this.idClassReference.update();
		this.queryContainer.update();
	}


	// ********** id class **********

	public JavaIdClassReference getIdClassReference() {
		return this.idClassReference;
	}

	protected JavaIdClassReference buildIdClassReference() {
		return new GenericJavaIdClassReference(this);
	}

	public JavaPersistentType getIdClass() {
		return this.idClassReference.getIdClass();
	}


	// ********** query container **********

	public JavaQueryContainer getQueryContainer() {
		return this.queryContainer;
	}

	protected JavaQueryContainer buildQueryContainer() {
		return this.getJpaFactory().buildJavaQueryContainer(this, this);
	}

	public JavaResourceMember getResourceAnnotatedElement() {
		return this.getJavaResourceType();
	}

	public Iterable<Query> getQueries() {
		return this.queryContainer.getQueries();
	}


	// ********** misc **********

	public String getKey() {
		return MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY;
	}

	public boolean isMapped() {
		return true;
	}

	public boolean tableNameIsInvalid(String tableName) {
		return false;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.validatePrimaryKey(messages, reporter, astRoot);
		this.idClassReference.validate(messages, reporter, astRoot);
		this.queryContainer.validate(messages, reporter, astRoot);
	}

	@Override
	public boolean validatesAgainstDatabase() {
		return false;
	}

	protected void validatePrimaryKey(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		this.buildPrimaryKeyValidator(astRoot).validate(messages, reporter);
	}

	protected JptValidator buildPrimaryKeyValidator(CompilationUnit astRoot) {
		return new GenericMappedSuperclassPrimaryKeyValidator(this, this.buildTextRangeResolver(astRoot));
		// TODO - JPA 2.0 validation
	}

	@Override
	protected PrimaryKeyTextRangeResolver buildTextRangeResolver(CompilationUnit astRoot) {
		return new JavaMappedSuperclassTextRangeResolver(this, astRoot);
	}
}
