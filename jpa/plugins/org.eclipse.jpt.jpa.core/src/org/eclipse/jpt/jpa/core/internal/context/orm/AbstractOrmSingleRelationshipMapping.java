/*******************************************************************************
 * Copyright (c) 2006, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.jpa.core.context.FetchType;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmSingleRelationshipMapping;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.orm.NullOrmDerivedIdentity2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmDerivedIdentity2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmSingleRelationshipMapping2_0;
import org.eclipse.jpt.jpa.core.resource.orm.AbstractXmlSingleRelationshipMapping;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> single relationship mapping (1:1, m:1)
 */
public abstract class AbstractOrmSingleRelationshipMapping<X extends AbstractXmlSingleRelationshipMapping>
	extends AbstractOrmRelationshipMapping<X>
	implements OrmSingleRelationshipMapping2_0
{
	protected Boolean specifiedOptional;
	protected boolean defaultOptional = DEFAULT_OPTIONAL;

	protected final OrmDerivedIdentity2_0 derivedIdentity;


	protected AbstractOrmSingleRelationshipMapping(OrmSpecifiedPersistentAttribute parent, X xmlMapping) {
		super(parent, xmlMapping);
		this.specifiedOptional = xmlMapping.getOptional();
		this.derivedIdentity = this.buildDerivedIdentity();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setSpecifiedOptional_(this.xmlAttributeMapping.getOptional());
		this.derivedIdentity.synchronizeWithResourceModel(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.setDefaultOptional(this.buildDefaultOptional());
		this.derivedIdentity.update(monitor);
	}


	// ********** optional **********

	public boolean isOptional() {
		return (this.specifiedOptional != null) ? this.specifiedOptional.booleanValue() : this.defaultOptional;
	}

	public Boolean getSpecifiedOptional() {
		return this.specifiedOptional;
	}

	public void setSpecifiedOptional(Boolean optional) {
		this.setSpecifiedOptional_(optional);
		this.xmlAttributeMapping.setOptional(optional);
	}

	protected void setSpecifiedOptional_(Boolean optional) {
		Boolean old = this.specifiedOptional;
		this.specifiedOptional = optional;
		this.firePropertyChanged(SPECIFIED_OPTIONAL_PROPERTY, old, optional);
	}

	public boolean isDefaultOptional() {
		return this.defaultOptional;
	}

	protected void setDefaultOptional(boolean optional) {
		boolean old = this.defaultOptional;
		this.defaultOptional = optional;
		this.firePropertyChanged(DEFAULT_OPTIONAL_PROPERTY, old, optional);
	}

	protected boolean buildDefaultOptional() {
		return DEFAULT_OPTIONAL;
	}


	// ********** derived identity **********

	public OrmDerivedIdentity2_0 getDerivedIdentity() {
		return this.derivedIdentity;
	}

	protected OrmDerivedIdentity2_0 buildDerivedIdentity() {
		return this.isOrmXml2_0Compatible() ?
				this.getContextModelFactory2_0().buildOrmDerivedIdentity(this) :
				new NullOrmDerivedIdentity2_0(this);
	}


	// ********** misc **********

	@Override
	protected void initializeFromOrmSingleRelationshipMapping(OrmSingleRelationshipMapping oldMapping) {
		super.initializeFromOrmSingleRelationshipMapping(oldMapping);
		if (this.isJpa2_0Compatible()) {
			this.derivedIdentity.initializeFrom(((OrmSingleRelationshipMapping2_0) oldMapping).getDerivedIdentity());
		}
	}

	/**
	 * pre-condition: the mapping's Java persistent attribute is not
	 * <code>null</code>.
	 */
	@Override
	protected String getJavaTargetType() {
		return this.getJavaPersistentAttribute().getSingleReferenceTargetTypeName();
	}

	@Override
	protected FetchType buildDefaultFetch() {
		return DEFAULT_FETCH_TYPE;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.derivedIdentity.validate(messages, reporter);
	}

	// ********** completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		result = this.derivedIdentity.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		return null;
	}
}
