/*******************************************************************************
 * Copyright (c) 2006, 2015 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.resource.java.JavaResourceMember;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.InheritanceType;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaQueryContainer;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.GenericMappedSuperclassPrimaryKeyValidator;
import org.eclipse.jpt.jpa.core.resource.java.MappedSuperclassAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java mapped superclass
 */
public abstract class AbstractJavaMappedSuperclass
		extends AbstractJavaIdTypeMapping<MappedSuperclassAnnotation>
		implements JavaMappedSuperclass, JavaQueryContainer.Parent {
	
	protected final JavaQueryContainer queryContainer;
	
	
	protected AbstractJavaMappedSuperclass(JavaPersistentType parent, MappedSuperclassAnnotation mappingAnnotation) {
		super(parent, mappingAnnotation);
		this.queryContainer = this.buildQueryContainer();
	}
	
	
	// ***** synchronize/update *****
	
	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.queryContainer.synchronizeWithResourceModel(monitor);
	}
	
	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.queryContainer.update(monitor);
	}
	
	
	// ********** query container **********
	
	public JavaQueryContainer getQueryContainer() {
		return this.queryContainer;
	}
	
	protected JavaQueryContainer buildQueryContainer() {
		return this.getJpaFactory().buildJavaQueryContainer(this);
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
	
	public Entity getRootEntity() {
		return null;
	}
	
	public boolean isRootEntity() {
		return false;
	}
	
	public InheritanceType getInheritanceStrategy() {
		return null;
	}
	
	
	// ***** validation *****
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.queryContainer.validate(messages, reporter);
	}
	
	@Override
	public boolean validatesAgainstDatabase() {
		return false;
	}
	
	@Override
	protected JpaValidator buildPrimaryKeyValidator() {
		return new GenericMappedSuperclassPrimaryKeyValidator(this);
		// TODO - JPA 2.0 validation
	}
}
