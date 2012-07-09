/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.common.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmGeneratorContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkGeneratorContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmUuidGenerator;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlUuidGenerator;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlGeneratorContainer2_4;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlUuidGenerator_2_4;

public class OrmEclipseLinkGeneratorContainer
	extends GenericOrmGeneratorContainer
	implements EclipseLinkGeneratorContainer
{

	protected OrmUuidGenerator uuidGenerator;


	public OrmEclipseLinkGeneratorContainer(XmlContextNode parent, XmlGeneratorContainer2_4 xmlGeneratorContainer) {
		super(parent, xmlGeneratorContainer);
		this.uuidGenerator = this.buildUuidGenerator();
	}

	protected XmlGeneratorContainer2_4 getXmlGeneratorContainer() {
		return (XmlGeneratorContainer2_4) this.xmlGeneratorContainer;
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.syncUuidGenerator();
	}

	@Override
	public void update() {
		super.update();
		if (this.uuidGenerator != null) {
			this.uuidGenerator.update();
		}
	}


	// ********** uuid generator **********

	public OrmUuidGenerator getUuidGenerator() {
		return this.uuidGenerator;
	}

	public OrmUuidGenerator addUuidGenerator() {
		if (this.uuidGenerator != null) {
			throw new IllegalStateException("uuid generator already exists: " + this.uuidGenerator); //$NON-NLS-1$
		}
		XmlUuidGenerator xmlGenerator = this.buildXmlUuidGenerator();
		OrmUuidGenerator generator = this.buildUuidGenerator(xmlGenerator);
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

	protected OrmUuidGenerator buildUuidGenerator() {
		XmlUuidGenerator_2_4 xmlGenerator = this.getXmlUuidGenerator();
		return (xmlGenerator == null) ? null : this.buildUuidGenerator(xmlGenerator);
	}

	protected XmlUuidGenerator_2_4 getXmlUuidGenerator() {
		return this.getXmlGeneratorContainer().getUuidGenerator();
	}

	protected OrmUuidGenerator buildUuidGenerator(XmlUuidGenerator_2_4 xmlUuidGenerator) {
		return new OrmEclipseLinkUuidGenerator(this, xmlUuidGenerator);
	}

	protected void syncUuidGenerator() {
		XmlUuidGenerator_2_4 xmlGenerator = this.getXmlUuidGenerator();
		if (xmlGenerator == null) {
			if (this.uuidGenerator != null) {
				this.setUuidGenerator_(null);
			}
		} else {
			if ((this.uuidGenerator != null) && (this.uuidGenerator.getXmlGenerator() == xmlGenerator)) {
				this.uuidGenerator.synchronizeWithResourceModel();
			} else {
				this.setUuidGenerator_(this.buildUuidGenerator(xmlGenerator));
			}
		}
	}

	protected void setUuidGenerator_(OrmUuidGenerator uuidGenerator) {
		OrmUuidGenerator old = this.uuidGenerator;
		this.uuidGenerator = uuidGenerator;
		this.firePropertyChanged(UUID_GENERATOR_PROPERTY, old, uuidGenerator);
	}


	// ********** completion proposals **********

	@Override
	public Iterable<String> getXmlCompletionProposals(int pos) {
		Iterable<String> result = super.getXmlCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.uuidGenerator != null) {
			result = this.uuidGenerator.getXmlCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	// ********** misc **********

	@Override
	protected Iterable<Generator> getGenerators_() {
		return new ArrayIterable<Generator>(this.sequenceGenerator, this.tableGenerator, this.uuidGenerator);
	}
}
