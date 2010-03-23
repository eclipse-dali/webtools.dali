/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaIdClassReference;
import org.eclipse.jpt.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.context.PrimaryKeyTextRangeResolver;
import org.eclipse.jpt.core.internal.context.PrimaryKeyValidator;
import org.eclipse.jpt.core.internal.jpa1.context.GenericMappedSuperclassPrimaryKeyValidator;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.MappedSuperclassAnnotation;
import org.eclipse.jpt.utility.internal.iterables.ArrayIterable;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractJavaMappedSuperclass extends AbstractJavaTypeMapping
	implements JavaMappedSuperclass
{
	protected final JavaIdClassReference idClassReference;
	
	
	protected AbstractJavaMappedSuperclass(JavaPersistentType parent) {
		super(parent);
		this.idClassReference = buildIdClassReference();
	}
	
	
	protected GenericJavaIdClassReference buildIdClassReference() {
		return new GenericJavaIdClassReference(this);
	}
	
	public JavaIdClassReference getIdClassReference() {
		return this.idClassReference;
	}
	
	public boolean isMapped() {
		return true;
	}
	
	@Override
	public JavaPersistentType getIdClass() {
		return this.idClassReference.getIdClass();
	}

	public String getKey() {
		return MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY;
	}

	public String getAnnotationName() {
		return MappedSuperclassAnnotation.ANNOTATION_NAME;
	}
	
	public Iterable<String> getSupportingAnnotationNames() {
		return SUPPORTING_ANNOTATION_NAMES;
	}
	
	protected static final String[] SUPPORTING_ANNOTATION_NAMES_ARRAY = new String[] {
			JPA.ID_CLASS,
			JPA.EXCLUDE_DEFAULT_LISTENERS,
			JPA.EXCLUDE_SUPERCLASS_LISTENERS,
			JPA.ENTITY_LISTENERS,
			JPA.PRE_PERSIST,
			JPA.POST_PERSIST,
			JPA.PRE_REMOVE,
			JPA.POST_REMOVE,
			JPA.PRE_UPDATE,
			JPA.POST_UPDATE,
			JPA.POST_LOAD
	};
	
	protected static final Iterable<String> SUPPORTING_ANNOTATION_NAMES = new ArrayIterable<String>(SUPPORTING_ANNOTATION_NAMES_ARRAY);
	
	public boolean tableNameIsInvalid(String tableName) {
		return false;
	}
	
	@Override
	public boolean shouldValidateAgainstDatabase() {
		return false;
	}
	
	@Override
	public void initialize(JavaResourcePersistentType persistentTypeResource) {
		super.initialize(persistentTypeResource);
		this.idClassReference.initialize();
	}
	
	@Override
	public void update(JavaResourcePersistentType persistentTypeResource) {
		super.update(persistentTypeResource);
		this.idClassReference.update();
	}
	
	
	// **************** validation ********************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		validatePrimaryKey(messages, reporter, astRoot);
	}
	
	protected void validatePrimaryKey(
			List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		
		buildPrimaryKeyValidator(astRoot).validate(messages, reporter);
	}
	
	protected PrimaryKeyValidator buildPrimaryKeyValidator(CompilationUnit astRoot) {
		return new GenericMappedSuperclassPrimaryKeyValidator(this, buildTextRangeResolver(astRoot));
		// TODO - JPA 2.0 validation
	}
	
	protected PrimaryKeyTextRangeResolver buildTextRangeResolver(CompilationUnit astRoot) {
		return new JavaMappedSuperclassTextRangeResolver(this, astRoot);
	}
}
