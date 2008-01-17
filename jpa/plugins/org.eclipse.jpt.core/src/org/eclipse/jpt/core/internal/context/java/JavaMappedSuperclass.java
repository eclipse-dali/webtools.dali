/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.context.base.IMappedSuperclass;
import org.eclipse.jpt.core.internal.resource.java.IdClass;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.MappedSuperclass;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

public class JavaMappedSuperclass extends JavaTypeMapping
	implements IJavaMappedSuperclass
{

	protected String idClass;

	public JavaMappedSuperclass(IJavaPersistentType parent) {
		super(parent);
	}

	public boolean isMapped() {
		return true;
	}

	public String getKey() {
		return IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY;
	}

	public String annotationName() {
		return MappedSuperclass.ANNOTATION_NAME;
	}
	public Iterator<String> correspondingAnnotationNames() {
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
	
	public String getIdClass() {
		return this.idClass;
	}
	
	public void setIdClass(String newIdClass) {
		String oldIdClass = this.idClass;
		this.idClass = newIdClass;
		if (newIdClass != oldIdClass) {
			if (newIdClass != null) {
				if (idClassResource() == null) {
					addIdClassResource();
				}
				idClassResource().setValue(newIdClass);
			}
			else {
				removeIdClassResource();
			}
		}
		firePropertyChanged(IMappedSuperclass.ID_CLASS_PROPERTY, oldIdClass, newIdClass);
	}
	
	protected void setIdClass_(String newIdClass) {
		String oldIdClass = this.idClass;
		this.idClass = newIdClass;
		firePropertyChanged(IMappedSuperclass.ID_CLASS_PROPERTY, oldIdClass, newIdClass);
	}

	protected IdClass idClassResource() {
		return (IdClass) this.persistentTypeResource.annotation(IdClass.ANNOTATION_NAME);
	}
	
	protected void addIdClassResource() {
		this.persistentTypeResource.addAnnotation(IdClass.ANNOTATION_NAME);
	}
	
	protected void removeIdClassResource() {
		this.persistentTypeResource.removeAnnotation(IdClass.ANNOTATION_NAME);
	}
	
	@Override
	public Iterator<String> overridableAttributeNames() {
		return this.namesOf(this.overridableAttributes());
	}

	protected Iterator<IJavaPersistentAttribute> overridableAttributes() {
		return new FilteringIterator<IJavaPersistentAttribute>(this.persistentType().attributes()) {
			@Override
			protected boolean accept(Object o) {
				return ((IJavaPersistentAttribute) o).isOverridableAttribute();
			}
		};
	}

	@Override
	public Iterator<String> overridableAssociationNames() {
		return this.namesOf(this.overridableAssociations());
	}

	protected Iterator<IJavaPersistentAttribute> overridableAssociations() {
		return new FilteringIterator<IJavaPersistentAttribute>(this.persistentType().attributes()) {
			@Override
			protected boolean accept(Object o) {
				return ((IJavaPersistentAttribute) o).isOverridableAssociation();
			}
		};
	}

	protected Iterator<String> namesOf(Iterator<IJavaPersistentAttribute> attributes) {
		return new TransformationIterator<IJavaPersistentAttribute, String>(attributes) {
			@Override
			protected String transform(IJavaPersistentAttribute attribute) {
				return attribute.getName();
			}
		};
	}

	@Override
	public void initializeFromResource(JavaPersistentTypeResource persistentTypeResource) {
		super.initializeFromResource(persistentTypeResource);
		this.initializeIdClass(persistentTypeResource);
	}

	protected void initializeIdClass(JavaPersistentTypeResource typeResource) {
		IdClass idClassResource = (IdClass) typeResource.annotation(IdClass.ANNOTATION_NAME);
		if (idClassResource != null) {
			this.idClass = idClassResource.getValue();
		}
	}

	@Override
	public void update(JavaPersistentTypeResource persistentTypeResource) {
		super.update(persistentTypeResource);
		this.updateIdClass(persistentTypeResource);
	}
	
	protected void updateIdClass(JavaPersistentTypeResource typeResource) {
		IdClass idClass = (IdClass) typeResource.annotation(IdClass.ANNOTATION_NAME);
		if (idClass != null) {
			setIdClass_(idClass.getValue());
		}
		else {
			setIdClass_(null);
		}
	}

}
