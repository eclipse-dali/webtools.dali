/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.IdClass;
import org.eclipse.jpt.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.resource.java.IdClassAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.MappedSuperclassAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

public class GenericJavaMappedSuperclass extends AbstractJavaTypeMapping
	implements JavaMappedSuperclass
{

	protected String idClass;

	public GenericJavaMappedSuperclass(JavaPersistentType parent) {
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
		firePropertyChanged(IdClass.ID_CLASS_PROPERTY, oldIdClass, newIdClass);
	}
	
	protected void setIdClass_(String newIdClass) {
		String oldIdClass = this.idClass;
		this.idClass = newIdClass;
		firePropertyChanged(IdClass.ID_CLASS_PROPERTY, oldIdClass, newIdClass);
	}

	protected IdClassAnnotation idClassResource() {
		return (IdClassAnnotation) this.javaResourcePersistentType.annotation(IdClassAnnotation.ANNOTATION_NAME);
	}
	
	protected void addIdClassResource() {
		this.javaResourcePersistentType.addAnnotation(IdClassAnnotation.ANNOTATION_NAME);
	}
	
	protected void removeIdClassResource() {
		this.javaResourcePersistentType.removeAnnotation(IdClassAnnotation.ANNOTATION_NAME);
	}
	
	@Override
	public Iterator<String> overridableAttributeNames() {
		return this.namesOf(this.overridableAttributes());
	}

	@Override
	public Iterator<JavaPersistentAttribute> overridableAttributes() {
		return new FilteringIterator<JavaPersistentAttribute, JavaPersistentAttribute>(this.getPersistentType().attributes()) {
			@Override
			protected boolean accept(JavaPersistentAttribute o) {
				return o.isOverridableAttribute();
			}
		};
	}

	@Override
	public Iterator<String> overridableAssociationNames() {
		return this.namesOf(this.overridableAssociations());
	}

	@Override
	public Iterator<JavaPersistentAttribute> overridableAssociations() {
		return new FilteringIterator<JavaPersistentAttribute, JavaPersistentAttribute>(this.getPersistentType().attributes()) {
			@Override
			protected boolean accept(JavaPersistentAttribute o) {
				return o.isOverridableAssociation();
			}
		};
	}

	protected Iterator<String> namesOf(Iterator<JavaPersistentAttribute> attributes) {
		return new TransformationIterator<JavaPersistentAttribute, String>(attributes) {
			@Override
			protected String transform(JavaPersistentAttribute attribute) {
				return attribute.getName();
			}
		};
	}

	@Override
	public void initializeFromResource(JavaResourcePersistentType persistentTypeResource) {
		super.initializeFromResource(persistentTypeResource);
		this.initializeIdClass(persistentTypeResource);
	}

	protected void initializeIdClass(JavaResourcePersistentType typeResource) {
		IdClassAnnotation idClassResource = (IdClassAnnotation) typeResource.annotation(IdClassAnnotation.ANNOTATION_NAME);
		if (idClassResource != null) {
			this.idClass = idClassResource.getValue();
		}
	}

	@Override
	public void update(JavaResourcePersistentType persistentTypeResource) {
		super.update(persistentTypeResource);
		this.updateIdClass(persistentTypeResource);
	}
	
	protected void updateIdClass(JavaResourcePersistentType typeResource) {
		IdClassAnnotation idClass = (IdClassAnnotation) typeResource.annotation(IdClassAnnotation.ANNOTATION_NAME);
		if (idClass != null) {
			setIdClass_(idClass.getValue());
		}
		else {
			setIdClass_(null);
		}
	}

}
