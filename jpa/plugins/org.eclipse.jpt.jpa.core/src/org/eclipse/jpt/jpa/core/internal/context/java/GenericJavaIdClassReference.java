/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.List;
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.AstNodeType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.java.JavaIdClassReference;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.jpa.core.resource.java.IdClassAnnotation;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java ID class reference
 */
public class GenericJavaIdClassReference
	extends AbstractJavaJpaContextNode
	implements JavaIdClassReference
{
	protected String idClassName;
	protected String fullyQualifiedIdClassName;
	// the ref holds the type directly because the ref is the type's parent
	protected JavaPersistentType idClass;


	public GenericJavaIdClassReference(JavaTypeMapping parent) {
		super(parent);
		this.idClassName = this.buildIdClassName();
		// 'idClass' is resolved in the update
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setIdClassName_(this.buildIdClassName());
		if (this.idClass != null) {
			this.idClass.synchronizeWithResourceModel();
		}
	}

	@Override
	public void update() {
		super.update();
		this.setFullyQualifiedIdClassName(this.buildFullyQualifiedIdClassName());
		this.updateIdClass();
	}


	// ********** id class name **********

	public String getIdClassName() {
		return this.getSpecifiedIdClassName();
	}

	public String getSpecifiedIdClassName() {
		return this.idClassName;
	}

	public void setSpecifiedIdClassName(String name) {
		if (this.valuesAreDifferent(name, this.idClassName)) {
			this.getIdClassAnnotationForUpdate().setValue(name);
			this.removeIdClassAnnotationIfUnset();
			this.setIdClassName_(name);
		}
	}

	protected void setIdClassName_(String name) {
		String old = this.idClassName;
		this.idClassName = name;
		this.firePropertyChanged(SPECIFIED_ID_CLASS_NAME_PROPERTY, old, name);
	}

	protected String buildIdClassName() {
		IdClassAnnotation annotation = this.getIdClassAnnotation();
		return (annotation == null) ? null : annotation.getValue();
	}

	public String getDefaultIdClassName() {
		return null;
	}

	public boolean isSpecified() {
		return this.idClassName != null;
	}


	// ********** id class annotation **********

	/**
	 * Return <code>null</code> if the annotation does not exists.
	 */
	protected IdClassAnnotation getIdClassAnnotation() {
		return (IdClassAnnotation) this.getJavaResourceType().getAnnotation(this.getIdClassAnnotationName());
	}

	/**
	 * Build the annotation if it does not exist.
	 */
	protected IdClassAnnotation getIdClassAnnotationForUpdate() {
		IdClassAnnotation annotation = this.getIdClassAnnotation();
		return (annotation != null) ? annotation : this.buildIdClassAnnotation();
	}

	protected IdClassAnnotation buildIdClassAnnotation() {
		return (IdClassAnnotation) this.getJavaResourceType().addAnnotation(this.getIdClassAnnotationName());
	}

	protected void removeIdClassAnnotationIfUnset() {
		if (this.getIdClassAnnotation().isUnset()) {
			this.removeIdClassAnnotation();
		}
	}

	protected void removeIdClassAnnotation() {
		this.getJavaResourceType().removeAnnotation(this.getIdClassAnnotationName());
	}

	protected String getIdClassAnnotationName() {
		return IdClassAnnotation.ANNOTATION_NAME;
	}


	// ********** fully-qualified id class name **********

	public String getFullyQualifiedIdClassName() {
		return this.fullyQualifiedIdClassName;
	}

	protected void setFullyQualifiedIdClassName(String name) {
		String old = this.fullyQualifiedIdClassName;
		this.fullyQualifiedIdClassName = name;
		this.firePropertyChanged(FULLY_QUALIFIED_ID_CLASS_PROPERTY, old, name);
	}

	protected String buildFullyQualifiedIdClassName() {
		IdClassAnnotation annotation = this.getIdClassAnnotation();
		return (annotation == null) ? null : annotation.getFullyQualifiedClassName();
	}


	// ********** id class **********

	public JavaPersistentType getIdClass() {
		return this.idClass;
	}

	protected void setIdClass(JavaPersistentType idClass) {
		JavaPersistentType old = this.idClass;
		this.idClass = idClass;
		this.firePropertyChanged(ID_CLASS_PROPERTY, old, idClass);
	}

	protected void updateIdClass() {
		JavaResourceType resourceIdClass = this.resolveResourceIdClass();
		if (resourceIdClass == null) {
			if (this.idClass != null) {
				this.setIdClass(null);
			}
		} else {
			if (this.idClass == null) {
				this.setIdClass(this.buildIdClass(resourceIdClass));
			} else {
				if (this.idClass.getJavaResourceType() == resourceIdClass) {
					this.idClass.update();
				} else {
					this.setIdClass(this.buildIdClass(resourceIdClass));
				}
			}
		}
	}

	protected JavaResourceType resolveResourceIdClass() {
		if (this.fullyQualifiedIdClassName == null) {
			return null;
		} 
		JavaResourceType jrt = this.getIdClassJavaResourceType();
		return (jrt == null) ? null : (jrt.isAnnotatedWithAnyOf(getJpaProject().getTypeMappingAnnotationNames()) ? null : jrt);
	}

	protected JavaResourceType getIdClassJavaResourceType() {
		if (this.fullyQualifiedIdClassName == null) {
			return null;
		}
		return (JavaResourceType) this.getJpaProject().getJavaResourceType(this.fullyQualifiedIdClassName, AstNodeType.TYPE);
	}

	protected JavaPersistentType buildIdClass(JavaResourceType resourceClass) {
		return this.getJpaFactory().buildJavaPersistentType(this, resourceClass);
	}

	public char getIdClassEnclosingTypeSeparator() {
		return '.';
	}


	// ********** misc **********

	@Override
	public JavaTypeMapping getParent() {
		return (JavaTypeMapping) super.getParent();
	}

	protected JavaTypeMapping getTypeMapping() {
		return this.getParent();
	}

	protected JavaPersistentType getPersistentType() {
		return this.getTypeMapping().getPersistentType();
	}

	protected JavaResourceType getJavaResourceType() {
		return this.getPersistentType().getJavaResourceType();
	}


	// ********** PersistentType.Owner implementation **********

	public AccessType getOverridePersistentTypeAccess() {
		return this.getPersistentType().getAccess();
	}

	public AccessType getDefaultPersistentTypeAccess() {
		// this shouldn't be needed, since we've specified an override access, but just to be safe ...
		return this.getPersistentType().getAccess();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateIdClass(messages, reporter);
	}
	
	protected void validateIdClass(List<IMessage> messages, IReporter reporter) {
		if (this.isSpecified()) {
			JavaResourceType jrt = this.getIdClassJavaResourceType();
			if (jrt != null) {

				if (!jrt.isPublic()) {
					messages.add(
							this.buildValidationMessage(
									this.getValidationTextRange(),
									JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_NOT_PUBLIC,
									jrt.getTypeBinding().getQualifiedName()
							)
						);
				}

				if (!JDTTools.typeIsSubType(this.getJpaProject().getJavaProject(), jrt.getTypeBinding().getQualifiedName(), JDTTools.SERIALIZABLE_CLASS_NAME)) {
					messages.add(
							this.buildValidationMessage(
									this.getValidationTextRange(),
									JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_NOT_IMPLEMENT_SERIALIZABLE,
									jrt.getTypeBinding().getQualifiedName()
							)
						);
				}

				if (!jrt.hasEqualsMethod()) {
					messages.add(
							this.buildValidationMessage(
									this.getValidationTextRange(),
									JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_MISSING_EQUALS_METHOD,
									jrt.getTypeBinding().getQualifiedName()
							)
						);
				}

				if (!jrt.hasHashCodeMethod()) {
					messages.add(
							this.buildValidationMessage(
									this.getValidationTextRange(),
									JptJpaCoreValidationMessages.TYPE_MAPPING_ID_CLASS_MISSING_HASHCODE_METHOD,
									jrt.getTypeBinding().getQualifiedName()
							)
						);
				}
			}
		}
	}
	
	public TextRange getValidationTextRange() {
		TextRange textRange = this.getAnnotationTextRange();
		return (textRange != null) ? textRange : this.getTypeMapping().getValidationTextRange();
	}

	protected TextRange getAnnotationTextRange() {
		IdClassAnnotation annotation = this.getIdClassAnnotation();
		return (annotation == null) ? null : annotation.getTextRange();
	}
}
