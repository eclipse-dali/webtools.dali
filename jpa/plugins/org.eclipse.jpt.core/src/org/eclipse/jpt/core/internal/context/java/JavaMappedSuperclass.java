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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaResource;
import org.eclipse.jpt.core.internal.resource.java.MappedSuperclass;
import org.eclipse.jpt.core.internal.resource.java.NamedNativeQueries;
import org.eclipse.jpt.core.internal.resource.java.NamedNativeQuery;
import org.eclipse.jpt.core.internal.resource.java.NamedQueries;
import org.eclipse.jpt.core.internal.resource.java.NamedQuery;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

public class JavaMappedSuperclass extends JavaTypeMapping
	implements IJavaMappedSuperclass
{
	protected final List<IJavaNamedQuery> namedQueries;

	protected final List<IJavaNamedNativeQuery> namedNativeQueries;

//	protected String idClass;

	public JavaMappedSuperclass(IJavaPersistentType parent) {
		super(parent);
		this.namedQueries = new ArrayList<IJavaNamedQuery>();
		this.namedNativeQueries = new ArrayList<IJavaNamedNativeQuery>();
	}

	@Override
	public void initializeFromResource(JavaPersistentTypeResource persistentTypeResource) {
		super.initializeFromResource(persistentTypeResource);
		this.initializeNamedQueries(persistentTypeResource);
		this.initializeNamedNativeQueries(persistentTypeResource);
//		this.idClass;
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

	public ListIterator<IJavaNamedQuery> namedQueries() {
		return new CloneListIterator<IJavaNamedQuery>(this.namedQueries);
	}
	
	public int namedQueriesSize() {
		return this.namedQueries.size();
	}
	
	public IJavaNamedQuery addNamedQuery(int index) {
		IJavaNamedQuery namedQuery = jpaFactory().createJavaNamedQuery(this);
		this.namedQueries.add(index, namedQuery);
		this.persistentTypeResource.addAnnotation(index, NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		fireItemAdded(IEntity.NAMED_QUERIES_LIST, index, namedQuery);
		return namedQuery;
	}
	
	protected void addNamedQuery(int index, IJavaNamedQuery namedQuery) {
		addItemToList(index, namedQuery, this.namedQueries, IEntity.NAMED_QUERIES_LIST);
	}
	
	public void removeNamedQuery(int index) {
		IJavaNamedQuery removedNamedQuery = this.namedQueries.remove(index);
		this.persistentTypeResource.removeAnnotation(index, NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		fireItemRemoved(IEntity.NAMED_QUERIES_LIST, index, removedNamedQuery);
	}
	
	protected void removeNamedQuery(IJavaNamedQuery namedQuery) {
		removeItemFromList(namedQuery, this.namedQueries, IEntity.NAMED_QUERIES_LIST);
	}
	
	public void moveNamedQuery(int targetIndex, int sourceIndex) {
		this.persistentTypeResource.move(targetIndex, sourceIndex, NamedQueries.ANNOTATION_NAME);
		moveItemInList(targetIndex, sourceIndex, this.namedQueries, IEntity.NAMED_QUERIES_LIST);
	}
	
	public ListIterator<IJavaNamedNativeQuery> namedNativeQueries() {
		return new CloneListIterator<IJavaNamedNativeQuery>(this.namedNativeQueries);
	}
	
	public int namedNativeQueriesSize() {
		return this.namedNativeQueries.size();
	}
	
	public IJavaNamedNativeQuery addNamedNativeQuery(int index) {
		IJavaNamedNativeQuery namedNativeQuery = jpaFactory().createJavaNamedNativeQuery(this);
		this.namedNativeQueries.add(index, namedNativeQuery);
		this.persistentTypeResource.addAnnotation(index, NamedNativeQuery.ANNOTATION_NAME, NamedNativeQueries.ANNOTATION_NAME);
		fireItemAdded(IEntity.NAMED_NATIVE_QUERIES_LIST, index, namedNativeQuery);
		return namedNativeQuery;
	}
	
	protected void addNamedNativeQuery(int index, IJavaNamedNativeQuery namedNativeQuery) {
		addItemToList(index, namedNativeQuery, this.namedNativeQueries, IEntity.NAMED_NATIVE_QUERIES_LIST);
	}
	
	public void removeNamedNativeQuery(int index) {
		IJavaNamedNativeQuery removedNamedNativeQuery = this.namedNativeQueries.remove(index);
		this.persistentTypeResource.removeAnnotation(index, NamedNativeQuery.ANNOTATION_NAME, NamedNativeQueries.ANNOTATION_NAME);
		fireItemRemoved(IEntity.NAMED_NATIVE_QUERIES_LIST, index, removedNamedNativeQuery);
	}
	
	protected void removeNamedNativeQuery(IJavaNamedNativeQuery namedNativeQuery) {
		removeItemFromList(namedNativeQuery, this.namedNativeQueries, IEntity.NAMED_NATIVE_QUERIES_LIST);
	}
	
	public void moveNamedNativeQuery(int targetIndex, int sourceIndex) {
		this.persistentTypeResource.move(targetIndex, sourceIndex, NamedNativeQueries.ANNOTATION_NAME);
		moveItemInList(targetIndex, sourceIndex, this.namedNativeQueries, IEntity.NAMED_NATIVE_QUERIES_LIST);
	}

//	public String getIdClass() {
//		return this.idClass;
//	}
//
//	public void setIdClass(String newIdClass) {
//		String oldIdClass = idClass;
//		idClass = newIdClass;
//		if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_MAPPED_SUPERCLASS__ID_CLASS, oldIdClass, idClass));
//	}
	
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
	
	public String getIdClass() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setIdClass(String value) {
		// TODO Auto-generated method stub
		
	}
	
	protected void initializeNamedQueries(JavaPersistentTypeResource persistentTypeResource) {
		ListIterator<JavaResource> annotations = persistentTypeResource.annotations(NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		
		while(annotations.hasNext()) {
			this.namedQueries.add(createNamedQuery((NamedQuery) annotations.next()));
		}
	}
	
	protected void initializeNamedNativeQueries(JavaPersistentTypeResource persistentTypeResource) {
		ListIterator<JavaResource> annotations = persistentTypeResource.annotations(NamedNativeQuery.ANNOTATION_NAME, NamedNativeQueries.ANNOTATION_NAME);
		
		while(annotations.hasNext()) {
			this.namedNativeQueries.add(createNamedNativeQuery((NamedNativeQuery) annotations.next()));
		}
	}

	@Override
	public void update(JavaPersistentTypeResource persistentTypeResource) {
		super.update(persistentTypeResource);
		this.updateNamedQueries(persistentTypeResource);
		this.updateNamedNativeQueries(persistentTypeResource);
		//this.updateIdClassFromJava(astRoot);
	}
//
//	private void updateIdClassFromJava(CompilationUnit astRoot) {
//		if (this.idClassAnnotationAdapter.getAnnotation(astRoot) == null) {
//			this.setIdClass(null);
//		}
//		else {
//			this.setIdClass(this.idClassValueAdapter.getValue(astRoot));
//		}
//	}
	
	protected void updateNamedQueries(JavaPersistentTypeResource persistentTypeResource) {
		ListIterator<IJavaNamedQuery> namedQueries = namedQueries();
		ListIterator<JavaResource> resourceNamedQueries = persistentTypeResource.annotations(NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		
		while (namedQueries.hasNext()) {
			IJavaNamedQuery namedQuery = namedQueries.next();
			if (resourceNamedQueries.hasNext()) {
				namedQuery.update((NamedQuery) resourceNamedQueries.next());
			}
			else {
				removeNamedQuery(namedQuery);
			}
		}
		
		while (resourceNamedQueries.hasNext()) {
			addNamedQuery(namedQueriesSize(), createNamedQuery((NamedQuery) resourceNamedQueries.next()));
		}	
	}
	
	protected void updateNamedNativeQueries(JavaPersistentTypeResource persistentTypeResource) {
		ListIterator<IJavaNamedNativeQuery> namedNativeQueries = namedNativeQueries();
		ListIterator<JavaResource> resourceNamedNativeQueries = persistentTypeResource.annotations(NamedNativeQuery.ANNOTATION_NAME, NamedNativeQueries.ANNOTATION_NAME);
		
		while (namedNativeQueries.hasNext()) {
			IJavaNamedNativeQuery namedQuery = namedNativeQueries.next();
			if (resourceNamedNativeQueries.hasNext()) {
				namedQuery.update((NamedNativeQuery) resourceNamedNativeQueries.next());
			}
			else {
				removeNamedNativeQuery(namedQuery);
			}
		}
		
		while (resourceNamedNativeQueries.hasNext()) {
			addNamedNativeQuery(namedQueriesSize(), createNamedNativeQuery((NamedNativeQuery) resourceNamedNativeQueries.next()));
		}	
	}
	
	
	protected IJavaNamedQuery createNamedQuery(NamedQuery namedQueryResource) {
		IJavaNamedQuery namedQuery = jpaFactory().createJavaNamedQuery(this);
		namedQuery.initializeFromResource(namedQueryResource);
		return namedQuery;
	}
	
	protected IJavaNamedNativeQuery createNamedNativeQuery(NamedNativeQuery namedNativeQueryResource) {
		IJavaNamedNativeQuery namedNativeQuery = jpaFactory().createJavaNamedNativeQuery(this);
		namedNativeQuery.initializeFromResource(namedNativeQueryResource);
		return namedNativeQuery;
	}

}
