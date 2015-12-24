/*******************************************************************************
 * Copyright (c) 2012, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmGeneratorContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkGeneratorContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmUuidGenerator;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlUuidGenerator;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlGeneratorContainer2_4;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlUuidGenerator_2_4;

public class EclipseLinkOrmGeneratorContainer
	extends GenericOrmGeneratorContainer
	implements EclipseLinkGeneratorContainer
{
	protected EclipseLinkOrmUuidGenerator uuidGenerator;


	public EclipseLinkOrmGeneratorContainer(JpaContextModel parent, XmlGeneratorContainer2_4 xmlGeneratorContainer) {
		super(parent, xmlGeneratorContainer);
		this.uuidGenerator = this.buildUuidGenerator();
	}

	protected XmlGeneratorContainer2_4 getXmlGeneratorContainer() {
		return (XmlGeneratorContainer2_4) this.xmlGeneratorContainer;
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


	// ********** uuid generator **********

	public EclipseLinkOrmUuidGenerator getUuidGenerator() {
		return this.uuidGenerator;
	}

	public EclipseLinkOrmUuidGenerator addUuidGenerator() {
		if (this.uuidGenerator != null) {
			throw new IllegalStateException("uuid generator already exists: " + this.uuidGenerator); //$NON-NLS-1$
		}
		XmlUuidGenerator xmlGenerator = this.buildXmlUuidGenerator();
		EclipseLinkOrmUuidGenerator generator = this.buildUuidGenerator(xmlGenerator);
		this.setUuidGenerator_(generator);
		this.getXmlGeneratorContainer().setUuidGenerator(xmlGenerator);
		return generator;
	}

	protected XmlUuidGenerator buildXmlUuidGenerator() {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlUuidGenerator();
	}

	public void removeUuidGenerator() {
		if (this.uuidGenerator == null) {
			throw new IllegalStateException("uuid generator does not exist"); //$NON-NLS-1$
		}
		this.setUuidGenerator_(null);
		this.getXmlGeneratorContainer().setUuidGenerator(null);
	}

	protected EclipseLinkOrmUuidGenerator buildUuidGenerator() {
		XmlUuidGenerator_2_4 xmlGenerator = this.getXmlUuidGenerator();
		return (xmlGenerator == null) ? null : this.buildUuidGenerator(xmlGenerator);
	}

	protected XmlUuidGenerator_2_4 getXmlUuidGenerator() {
		return this.getXmlGeneratorContainer().getUuidGenerator();
	}

	protected EclipseLinkOrmUuidGenerator buildUuidGenerator(XmlUuidGenerator_2_4 xmlUuidGenerator) {
		return new EclipseLinkOrmUuidGeneratorImpl(this, xmlUuidGenerator);
	}

	protected void syncUuidGenerator(IProgressMonitor monitor) {
		XmlUuidGenerator_2_4 xmlGenerator = this.getXmlUuidGenerator();
		if (xmlGenerator == null) {
			if (this.uuidGenerator != null) {
				this.setUuidGenerator_(null);
			}
		} else {
			if ((this.uuidGenerator != null) && (this.uuidGenerator.getXmlGenerator() == xmlGenerator)) {
				this.uuidGenerator.synchronizeWithResourceModel(monitor);
			} else {
				this.setUuidGenerator_(this.buildUuidGenerator(xmlGenerator));
			}
		}
	}

	protected void setUuidGenerator_(EclipseLinkOrmUuidGenerator uuidGenerator) {
		EclipseLinkOrmUuidGenerator old = this.uuidGenerator;
		this.uuidGenerator = uuidGenerator;
		this.firePropertyChanged(UUID_GENERATOR_PROPERTY, old, uuidGenerator);
	}


	// ********** completion proposals **********

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
