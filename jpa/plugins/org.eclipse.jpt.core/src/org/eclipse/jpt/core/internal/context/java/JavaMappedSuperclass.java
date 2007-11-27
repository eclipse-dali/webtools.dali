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
import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.MappedSuperclass;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

public class JavaMappedSuperclass extends JavaTypeMapping
	implements IJavaMappedSuperclass
{

//	protected String idClass;

	public JavaMappedSuperclass(IJavaPersistentType parent) {
		super(parent);
	}

	@Override
	public void initializeFromResource(JavaPersistentTypeResource persistentTypeResource) {
		super.initializeFromResource(persistentTypeResource);
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

	protected Iterator<IPersistentAttribute> overridableAttributes() {
		return new FilteringIterator<IPersistentAttribute>(this.getPersistentType().attributes()) {
			@Override
			protected boolean accept(Object o) {
				return ((IPersistentAttribute) o).isOverridableAttribute();
			}
		};
	}

	@Override
	public Iterator<String> overridableAssociationNames() {
		return this.namesOf(this.overridableAssociations());
	}

	protected Iterator<IPersistentAttribute> overridableAssociations() {
		return new FilteringIterator<IPersistentAttribute>(this.getPersistentType().attributes()) {
			@Override
			protected boolean accept(Object o) {
				return ((IPersistentAttribute) o).isOverridableAssociation();
			}
		};
	}

	protected Iterator<String> namesOf(Iterator<IPersistentAttribute> attributes) {
		return new TransformationIterator<IPersistentAttribute, String>(attributes) {
			@Override
			protected String transform(IPersistentAttribute attribute) {
				return attribute.getName();
			}
		};
	}

	@Override
	public void update(JavaPersistentTypeResource persistentTypeResource) {
		super.update(persistentTypeResource);
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
}
