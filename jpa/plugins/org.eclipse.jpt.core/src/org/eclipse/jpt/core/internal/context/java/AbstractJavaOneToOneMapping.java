/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.internal.jpa2.context.java.NullJavaOrphanRemoval2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOneToOneMapping2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOrphanRemovable2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOrphanRemovalHolder2_0;
import org.eclipse.jpt.core.resource.java.OneToOneAnnotation;


public abstract class AbstractJavaOneToOneMapping
	extends AbstractJavaSingleRelationshipMapping<OneToOneAnnotation>
	implements JavaOneToOneMapping2_0, JavaOrphanRemovalHolder2_0
{
	protected final JavaOrphanRemovable2_0 orphanRemoval;


	protected AbstractJavaOneToOneMapping(JavaPersistentAttribute parent) {
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
	public JavaOneToOneRelationshipReference getRelationshipReference() {
		return (JavaOneToOneRelationshipReference) super.getRelationshipReference();
	}

	@Override
	protected JavaOneToOneRelationshipReference buildRelationshipReference() {
		return new GenericJavaOneToOneRelationshipReference(this);
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
		return MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	protected String getAnnotationName() {
		return OneToOneAnnotation.ANNOTATION_NAME;
	}
}
