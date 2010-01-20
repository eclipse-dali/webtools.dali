/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.IdClassReference;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaIdClassReference;
import org.eclipse.jpt.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.MappedSuperclassAnnotation;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.utility.internal.iterables.SubIterableWrapper;
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
	
	public IdClassReference getIdClassReference() {
		return this.idClassReference;
	}
	
	public boolean isMapped() {
		return true;
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
	
	@Override
	public boolean specifiesPrimaryKey() {
		return this.idClassReference.getIdClassName() != null
				|| hasPrimaryKeyAttribute();
	}
	
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
		
		// for JPA portability, a hierarchy must define its primary key on one class 
		// (entity *or* mapped superclass)
		if (primaryKeyIsDefinedOnAncestor()) {
			if (this.idClassReference.getIdClassName() != null) {
				messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.TYPE_MAPPING_PK_REDEFINED_ID_CLASS,
							new String[0],
							this,
							this.idClassReference.getValidationTextRange(astRoot)));
			}
			for (JavaPersistentAttribute each : getPrimaryKeyAttributes()) {
				messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.TYPE_MAPPING_PK_REDEFINED_ID_ATTRIBUTE,
							new String[0],
							each,
							each.getMapping().getValidationTextRange(astRoot)));
			}
			return;
		}
		
		if (this.idClassReference.getIdClass() != null) {
			validateIdClass(messages, reporter, astRoot);
		}
	}
	
	// split out to allow different implementations to override
	// assumes id class is not null
	protected void validateIdClass(
			List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		
		JavaPersistentType idClass = this.idClassReference.getIdClass();
		for (JavaPersistentAttribute idClassAttribute : 
				new SubIterableWrapper<PersistentAttribute, JavaPersistentAttribute>(
					CollectionTools.iterable(idClass.allAttributes()))) {
			boolean foundMatch = false;
			for (JavaPersistentAttribute persistentAttribute : 
					CollectionTools.iterable(getPersistentType().attributes())) {
				if (idClassAttribute.getName().equals(persistentAttribute.getName())) {
					foundMatch = true;
					
					// the matching attribute should be a primary key
					if (! persistentAttribute.isPrimaryKeyAttribute()) {
						messages.add(DefaultJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JpaValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_NOT_PRIMARY_KEY,
								new String[0],
								persistentAttribute,
								persistentAttribute.getValidationTextRange(astRoot)));
					}
					
					// the matching attribute's type should agree
					String persistentAttributeTypeName = persistentAttribute.getTypeName();
					if (persistentAttributeTypeName != null 	// if it's null, there should be 
																// another failing validation elsewhere
							&& ! idClassAttribute.getTypeName().equals(persistentAttributeTypeName)) {
						messages.add(DefaultJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JpaValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_TYPE_DOES_NOT_AGREE,
								new String[0],
								persistentAttribute,
								persistentAttribute.getValidationTextRange(astRoot)));
					}
				}
			}
			
			if (! foundMatch) {
				messages.add(DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_NO_MATCH,
						new String[] {idClassAttribute.getName()},
						this,
						this.idClassReference.getValidationTextRange(astRoot)));
			}
		}
	}
}
