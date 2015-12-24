/*******************************************************************************
 * Copyright (c) 2012, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaGeneratorContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkGeneratorContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaUuidGenerator;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.UuidGeneratorAnnotation2_4;

public class EclipseLinkJavaGeneratorContainer
	extends GenericJavaGeneratorContainer
	implements EclipseLinkGeneratorContainer
{
	protected EclipseLinkJavaUuidGenerator uuidGenerator;


	public EclipseLinkJavaGeneratorContainer(Parent parentAdapter) {
		super(parentAdapter);
		this.uuidGenerator = this.buildUuidGenerator();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.syncUuidGenerator(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		if (this.uuidGenerator != null) {
			this.uuidGenerator.update(monitor);
		}
	}


	// ********** Uuid generator **********

	public EclipseLinkJavaUuidGenerator getUuidGenerator() {
		return this.uuidGenerator;
	}

	public EclipseLinkJavaUuidGenerator addUuidGenerator() {
		if (this.uuidGenerator != null) {
			throw new IllegalStateException("UUID generator already exists: " + this.uuidGenerator); //$NON-NLS-1$
		}
		UuidGeneratorAnnotation2_4 annotation = this.buildUuidGeneratorAnnotation();
		EclipseLinkJavaUuidGenerator generator = this.buildUuidGenerator(annotation);
		this.setUuidGenerator(generator);
		return generator;
	}

	protected UuidGeneratorAnnotation2_4 buildUuidGeneratorAnnotation() {
		return (UuidGeneratorAnnotation2_4) this.parent.getResourceAnnotatedElement().addAnnotation(UuidGeneratorAnnotation2_4.ANNOTATION_NAME);
	}

	public void removeUuidGenerator() {
		if (this.uuidGenerator == null) {
			throw new IllegalStateException("UUID generator does not exist"); //$NON-NLS-1$
		}
		this.parent.getResourceAnnotatedElement().removeAnnotation(UuidGeneratorAnnotation2_4.ANNOTATION_NAME);
		this.setUuidGenerator(null);
	}

	protected EclipseLinkJavaUuidGenerator buildUuidGenerator() {
		UuidGeneratorAnnotation2_4 annotation = this.getUuidGeneratorAnnotation();
		return (annotation == null) ? null : this.buildUuidGenerator(annotation);
	}

	protected UuidGeneratorAnnotation2_4 getUuidGeneratorAnnotation() {
		return (UuidGeneratorAnnotation2_4) this.parent.getResourceAnnotatedElement().getAnnotation(UuidGeneratorAnnotation2_4.ANNOTATION_NAME);
	}

	protected EclipseLinkJavaUuidGenerator buildUuidGenerator(UuidGeneratorAnnotation2_4 uuidGeneratorAnnotation) {
		return this.parent.supportsGenerators() ?
				new EclipseLinkJavaUuidGeneratorImpl(this, uuidGeneratorAnnotation) :
				null;
	}

	protected void syncUuidGenerator(IProgressMonitor monitor) {
		UuidGeneratorAnnotation2_4 annotation = this.getUuidGeneratorAnnotation();
		if (annotation == null) {
			if (this.uuidGenerator != null) {
				this.setUuidGenerator(null);
			}
		} else {
			if ((this.uuidGenerator != null) && (this.uuidGenerator.getGeneratorAnnotation() == annotation)) {
				this.uuidGenerator.synchronizeWithResourceModel(monitor);
			} else {
				this.setUuidGenerator(this.buildUuidGenerator(annotation));
			}
		}
	}

	protected void setUuidGenerator(EclipseLinkJavaUuidGenerator uuidGenerator) {
		EclipseLinkJavaUuidGenerator old = this.uuidGenerator;
		this.uuidGenerator = uuidGenerator;
		this.firePropertyChanged(UUID_GENERATOR_PROPERTY, old, uuidGenerator);
	}


	// ********** code completion **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.uuidGenerator != null) {
			result = this.uuidGenerator.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		return null;
	}


	// ********** misc **********

	@Override
	protected Iterable<Generator> getGenerators_() {
		return IterableTools.<Generator>iterable(this.sequenceGenerator, this.tableGenerator, this.uuidGenerator);
	}
}
