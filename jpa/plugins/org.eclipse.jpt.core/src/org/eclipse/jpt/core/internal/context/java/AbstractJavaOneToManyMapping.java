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

import java.util.Vector;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.jpa2.JpaFactory2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOneToManyMapping2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOneToManyRelationshipReference2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOrphanRemovable2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOrphanRemovalHolder2_0;
import org.eclipse.jpt.core.jpa2.resource.java.OneToMany2_0Annotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.OneToManyAnnotation;


public abstract class AbstractJavaOneToManyMapping<T extends OneToMany2_0Annotation>
	extends AbstractJavaMultiRelationshipMapping<T>
	implements JavaOneToManyMapping2_0, JavaOrphanRemovalHolder2_0
{
	protected final JavaOrphanRemovable2_0 orphanRemoval;
	
	protected AbstractJavaOneToManyMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.orphanRemoval = ((JpaFactory2_0) this.getJpaFactory()).buildJavaOrphanRemoval(this);
	}

	// ********** initialize/update **********
	
	@Override
	protected void initialize() {
		super.initialize();
		this.orphanRemoval.initialize(this.getResourcePersistentAttribute());
	}
	
	@Override
	protected void update() {
		super.update();
		this.orphanRemoval.update(this.getResourcePersistentAttribute());
	}
	
	
	public String getAnnotationName() {
		return OneToManyAnnotation.ANNOTATION_NAME;
	}
	
	@Override
	protected void addSupportingAnnotationNamesTo(Vector<String> names) {
		super.addSupportingAnnotationNamesTo(names);
		names.add(JPA.JOIN_COLUMN);
		names.add(JPA.JOIN_COLUMNS);
	}
	
	public String getKey() {
		return MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY;
	}

	// ********** JavaOneToManyMapping implementation **********

	@Override
	public JavaOneToManyRelationshipReference2_0 getRelationshipReference() {
		return (JavaOneToManyRelationshipReference2_0) super.getRelationshipReference();
	}

	// ********** JavaOrphanRemovalHolder2_0 implementation **********

	public JavaOrphanRemovable2_0 getOrphanRemoval() {
		return this.orphanRemoval;
	}
}
