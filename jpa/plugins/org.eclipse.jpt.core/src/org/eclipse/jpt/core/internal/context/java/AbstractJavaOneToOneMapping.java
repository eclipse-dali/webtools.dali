/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaOneToOneRelationshipReference;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaRelationshipReference;
import org.eclipse.jpt.core.jpa2.JpaFactory2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOneToOneMapping2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOrphanRemovable2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOrphanRemovalHolder2_0;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.OneToOneAnnotation;
import org.eclipse.jpt.utility.internal.ArrayTools;


public abstract class AbstractJavaOneToOneMapping
	extends AbstractJavaSingleRelationshipMapping<OneToOneAnnotation>
	implements JavaOneToOneMapping2_0, JavaOrphanRemovalHolder2_0
{
	protected final JavaOrphanRemovable2_0 orphanRemoval;
	
	// ********** constructor **********
	protected AbstractJavaOneToOneMapping(JavaPersistentAttribute parent) {
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

	@Override
	protected JavaRelationshipReference buildRelationshipReference() {
		return new GenericJavaOneToOneRelationshipReference(this);
	}
	
	public String getAnnotationName() {
		return OneToOneAnnotation.ANNOTATION_NAME;
	}
	
	@Override
	protected String[] buildSupportingAnnotationNames() {
		return ArrayTools.addAll(
			super.buildSupportingAnnotationNames(),
			JPA.PRIMARY_KEY_JOIN_COLUMN,
			JPA.PRIMARY_KEY_JOIN_COLUMNS);
	}
	
	public String getKey() {
		return MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}
	
	@Override
	protected Boolean getResourceOptional() {
		return this.mappingAnnotation.getOptional();
	}
	
	@Override
	protected void setResourceOptional(Boolean newOptional) {
		this.mappingAnnotation.setOptional(newOptional);
	}

	// ********** JavaOneToOneMapping implementation **********
	
	@Override
	public OneToOneAnnotation getMappingAnnotation() {
		return super.getMappingAnnotation();
	}
	
	@Override
	public JavaOneToOneRelationshipReference getRelationshipReference() {
		return (JavaOneToOneRelationshipReference) super.getRelationshipReference();
	}

	// ********** JavaOrphanRemovalHolder2_0 implementation **********

	public JavaOrphanRemovable2_0 getOrphanRemoval() {
		return this.orphanRemoval;
	}
}