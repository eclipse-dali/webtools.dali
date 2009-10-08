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

import java.util.Iterator;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaRelationshipMapping;
import org.eclipse.jpt.core.resource.java.IdClassAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.MappedSuperclassAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;

public abstract class AbstractJavaMappedSuperclass extends AbstractJavaTypeMapping
	implements JavaMappedSuperclass
{

	protected String idClass;

	protected AbstractJavaMappedSuperclass(JavaPersistentType parent) {
		super(parent);
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
	
	public Iterator<String> supportingAnnotationNames() {
		return new ArrayIterator<String>(
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
			JPA.POST_LOAD);
	}
	
	public char getIdClassEnclosingTypeSeparator() {
		return '.';
	}
	
	public String getIdClass() {
		return this.idClass;
	}
	
	public void setIdClass(String newIdClass) {
		String oldIdClass = this.idClass;
		this.idClass = newIdClass;
		if (this.valuesAreDifferent(newIdClass, oldIdClass)) {
			if (newIdClass != null) {
				if (getResourceIdClass() == null) {
					addResourceIdClass();
				}
				getResourceIdClass().setValue(newIdClass);
			}
			else {
				removeResourceIdClass();
			}
		}
		firePropertyChanged(ID_CLASS_PROPERTY, oldIdClass, newIdClass);
	}
	
	protected void setIdClass_(String newIdClass) {
		String oldIdClass = this.idClass;
		this.idClass = newIdClass;
		firePropertyChanged(ID_CLASS_PROPERTY, oldIdClass, newIdClass);
	}

	protected IdClassAnnotation getResourceIdClass() {
		return (IdClassAnnotation) this.javaResourcePersistentType.
				getAnnotation(IdClassAnnotation.ANNOTATION_NAME);
	}
	
	protected void addResourceIdClass() {
		this.javaResourcePersistentType.addAnnotation(IdClassAnnotation.ANNOTATION_NAME);
	}
	
	protected void removeResourceIdClass() {
		this.javaResourcePersistentType.removeAnnotation(IdClassAnnotation.ANNOTATION_NAME);
	}

	@Override
	public Iterator<JavaRelationshipMapping> overridableAssociations() {
		return new FilteringIterator<JavaAttributeMapping, JavaRelationshipMapping>(this.attributeMappings()) {
			@Override
			protected boolean accept(JavaAttributeMapping o) {
				return o.isOverridableAssociationMapping();
			}
		};
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
		this.idClass = this.getResourceIdClassName(this.getResourceIdClass());
	}

	@Override
	public void update(JavaResourcePersistentType persistentTypeResource) {
		super.update(persistentTypeResource);
		this.setIdClass_(this.getResourceIdClassName(this.getResourceIdClass()));
	}

	protected String getResourceIdClassName(IdClassAnnotation resourceIdClass) {
		return resourceIdClass == null ? null : resourceIdClass.getValue();
	}
}
