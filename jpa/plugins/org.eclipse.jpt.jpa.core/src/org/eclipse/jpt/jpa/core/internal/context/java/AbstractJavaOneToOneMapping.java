/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaOneToOneRelationship;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.NullJavaOrphanRemoval2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OneToOneMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OrphanRemovable2_0;
import org.eclipse.jpt.jpa.core.resource.java.OneToOneAnnotation;


public abstract class AbstractJavaOneToOneMapping
	extends AbstractJavaSingleRelationshipMapping<OneToOneAnnotation>
	implements OneToOneMapping2_0, JavaOneToOneMapping
{
	protected final OrphanRemovable2_0 orphanRemoval;


	protected AbstractJavaOneToOneMapping(JavaSpecifiedPersistentAttribute parent) {
		super(parent);
		this.orphanRemoval = this.buildOrphanRemoval();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.orphanRemoval.synchronizeWithResourceModel(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.orphanRemoval.update(monitor);
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
