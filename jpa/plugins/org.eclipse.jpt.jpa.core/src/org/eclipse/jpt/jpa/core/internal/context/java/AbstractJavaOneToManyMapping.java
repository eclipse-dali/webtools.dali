/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.java.JavaMappingRelationship;
import org.eclipse.jpt.jpa.core.context.java.JavaOneToManyRelationship;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.NullJavaOrphanRemoval2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaOneToManyMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaOrphanRemovable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaOrphanRemovalHolder2_0;
import org.eclipse.jpt.jpa.core.resource.java.OneToManyAnnotation;

public abstract class AbstractJavaOneToManyMapping
	extends AbstractJavaMultiRelationshipMapping<OneToManyAnnotation>
	implements JavaOneToManyMapping2_0, JavaOrphanRemovalHolder2_0
{
	protected final JavaOrphanRemovable2_0 orphanRemoval;


	protected AbstractJavaOneToManyMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.orphanRemoval = this.buildOrphanRemoval();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.orphanRemoval.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
		this.orphanRemoval.update();
	}


	// ********** relationship **********

	@Override
	public JavaOneToManyRelationship getRelationship() {
		return (JavaOneToManyRelationship) super.getRelationship();
	}

	@Override
	protected JavaMappingRelationship buildRelationship() {
		return new GenericJavaOneToManyRelationship(this, this.isJpa2_0Compatible());
	}


	// ********** orphan removal **********

	public JavaOrphanRemovable2_0 getOrphanRemoval() {
		return this.orphanRemoval;
	}

	protected JavaOrphanRemovable2_0 buildOrphanRemoval() {
		return this.isJpa2_0Compatible() ? 
				this.getJpaFactory2_0().buildJavaOrphanRemoval(this) : 
				new NullJavaOrphanRemoval2_0(this);
	}


	// ********** misc **********

	public String getKey() {
		return MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	protected String getAnnotationName() {
		return OneToManyAnnotation.ANNOTATION_NAME;
	}
}
