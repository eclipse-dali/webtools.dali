/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.java.JavaIdClassReference;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.resource.java.IdClassAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.TextRange;

public class GenericJavaIdClassReference
	extends AbstractJavaJpaContextNode
	implements JavaIdClassReference
{
	protected String idClassName;

	protected String fullyQualifiedIdClassName;
	
	protected JavaPersistentType idClass;
	
	
	public GenericJavaIdClassReference(JavaTypeMapping parent) {
		super(parent);
	}
	
	
	protected JavaTypeMapping getTypeMapping() {
		return (JavaTypeMapping) getParent();
	}
	
	protected JavaPersistentType getPersistentType() {
		return getTypeMapping().getPersistentType();
	}
	
	
	// **************** PersistentType.Owner impl *****************************
	
	public AccessType getOverridePersistentTypeAccess() {
		return getPersistentType().getAccess();
	}
	
	public AccessType getDefaultPersistentTypeAccess() {
		// this shouldn't be needed, since we've specified an override access, but just to be safe ...
		return getPersistentType().getAccess();
	}
	
	
	// **************** IdClassReference impl *********************************
	
	public String getSpecifiedIdClassName() {
		return this.idClassName;
	}
	
	public void setSpecifiedIdClassName(String newClassName) {
		String oldClassName = this.idClassName;
		this.idClassName = newClassName;
		if (this.valuesAreDifferent(newClassName, oldClassName)) {
			if (newClassName != null) {
				if (getIdClassAnnotation() == null) {
					addIdClassAnnotation();
				}
				getIdClassAnnotation().setValue(newClassName);
			}
			else {
				removeIdClassAnnotation();
			}
		}
		firePropertyChanged(SPECIFIED_ID_CLASS_NAME_PROPERTY, oldClassName, newClassName);
	}
	
	protected void setIdClassName_(String newClassName) {
		String oldClassName = this.idClassName;
		this.idClassName = newClassName;
		firePropertyChanged(SPECIFIED_ID_CLASS_NAME_PROPERTY, oldClassName, newClassName);
	}
	
	protected String buildIdClassName() {
		IdClassAnnotation annotation = getIdClassAnnotation();
		if (annotation != null) {
			return annotation.getValue();
		}
		return null;
	}
	
	public String getDefaultIdClassName() {
		return null;
	}
	
	public String getIdClassName() {
		return getSpecifiedIdClassName();
	}
	
	public boolean isSpecified() {
		return getSpecifiedIdClassName() != null;
	}
	
	public JavaPersistentType getIdClass() {
		return this.idClass;
	}
	
	protected void setIdClass_(JavaPersistentType newIdClass) {
		JavaPersistentType oldIdClass = this.idClass;
		this.idClass = newIdClass;
		firePropertyChanged(ID_CLASS_PROPERTY, oldIdClass, newIdClass);
	}
	
	protected JavaPersistentType buildIdClass() {
		JavaResourcePersistentType resourceIdClass = getResourceIdClass();
		return (resourceIdClass == null) ? 
				null : this.buildIdClass(resourceIdClass);
	}
	
	protected JavaPersistentType buildIdClass(JavaResourcePersistentType resourceClass) {
		return getJpaFactory().buildJavaPersistentType(this, resourceClass);
	}
	
	protected JavaResourcePersistentType getResourcePersistentType() {
		return getPersistentType().getResourcePersistentType();
	}
	
	protected IdClassAnnotation getIdClassAnnotation() {
		return (IdClassAnnotation) getResourcePersistentType().
				getAnnotation(IdClassAnnotation.ANNOTATION_NAME);
	}
	
	protected void addIdClassAnnotation() {
		getResourcePersistentType().addAnnotation(IdClassAnnotation.ANNOTATION_NAME);
	}
	
	protected void removeIdClassAnnotation() {
		getResourcePersistentType().removeAnnotation(IdClassAnnotation.ANNOTATION_NAME);
	}
	
	protected JavaResourcePersistentType getResourceIdClass() {
		return (this.fullyQualifiedIdClassName == null) ?
				null : getJpaProject().getJavaResourcePersistentType(this.fullyQualifiedIdClassName);
	}

	public String getFullyQualifiedIdClassName() {
		return this.fullyQualifiedIdClassName;
	}

	protected void setFullyQualifiedIdClassName(String idClass) {
		String old = this.fullyQualifiedIdClassName;
		this.fullyQualifiedIdClassName = idClass;
		this.firePropertyChanged(FULLY_QUALIFIED_ID_CLASS_PROPERTY, old, idClass);
	}

	protected String buildFullyQualifiedIdClassName() {
		IdClassAnnotation annotation = getIdClassAnnotation();
		return (annotation == null) ?
				null : annotation.getFullyQualifiedClassName();
	}
	
	public char getIdClassEnclosingTypeSeparator() {
		return '.';
	}
	
	public void initialize() {
		this.idClassName = buildIdClassName();
		this.fullyQualifiedIdClassName = this.buildFullyQualifiedIdClassName();
		this.idClass = buildIdClass();
	}	
	
	public void update() {
		setIdClassName_(buildIdClassName());
		this.setFullyQualifiedIdClassName(this.buildFullyQualifiedIdClassName());
		updateIdClass();
	}
	
	protected void updateIdClass() {
		JavaResourcePersistentType resourceIdClass = getResourceIdClass();
		if (resourceIdClass == null) {
			setIdClass_(null);
		}
		else { 
			if (this.idClass == null || this.idClass.getResourcePersistentType() != resourceIdClass) {
				setIdClass_(buildIdClass(resourceIdClass));
			}
			this.idClass.update(resourceIdClass);
		}
	}
	
	
	// **************** validation ********************************************
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return (getIdClassAnnotation() == null) ?
				getTypeMapping().getValidationTextRange(astRoot)
				: getIdClassAnnotation().getTextRange(astRoot);
	}
}
