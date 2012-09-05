/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaOneToOneRelationship;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.NullJavaOrphanRemoval2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OneToOneMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OrphanRemovable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OrphanRemovalHolder2_0;
import org.eclipse.jpt.jpa.core.resource.java.OneToOneAnnotation;


public abstract class AbstractJavaOneToOneMapping
	extends AbstractJavaSingleRelationshipMapping<OneToOneAnnotation>
	implements OneToOneMapping2_0, JavaOneToOneMapping, OrphanRemovalHolder2_0
{
	protected final OrphanRemovable2_0 orphanRemoval;


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
	public JavaOneToOneRelationship getRelationship() {
		return (JavaOneToOneRelationship) super.getRelationship();
	}

	@Override
	protected JavaOneToOneRelationship buildRelationship() {
		return new GenericJavaOneToOneRelationship(this);
	}


	// ********** orphan removal **********

	public OrphanRemovable2_0 getOrphanRemoval() {
		return this.orphanRemoval;
	}

	protected OrphanRemovable2_0 buildOrphanRemoval() {
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
