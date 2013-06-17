/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.List;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.context.FetchType;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.NullJavaDerivedIdentity2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaDerivedIdentity2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaSingleRelationshipMapping2_0;
import org.eclipse.jpt.jpa.core.resource.java.SingleRelationshipMappingAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java single relationship mapping (1:1, m:1)
 */
public abstract class AbstractJavaSingleRelationshipMapping<A extends SingleRelationshipMappingAnnotation>
	extends AbstractJavaRelationshipMapping<A>
	implements JavaSingleRelationshipMapping2_0
{
	protected Boolean specifiedOptional;
	protected boolean defaultOptional = DEFAULT_OPTIONAL;

	protected final JavaDerivedIdentity2_0 derivedIdentity;


	protected AbstractJavaSingleRelationshipMapping(JavaSpecifiedPersistentAttribute parent) {
		super(parent);
		this.specifiedOptional = this.buildSpecifiedOptional();
		this.derivedIdentity = this.buildDerivedIdentity();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedOptional_(this.buildSpecifiedOptional());
		this.derivedIdentity.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultOptional(this.buildDefaultOptional());
		this.derivedIdentity.update();
	}


	// ********** optional **********

	public boolean isOptional() {
		return (this.specifiedOptional != null) ? this.specifiedOptional.booleanValue() : this.defaultOptional;
	}

	public Boolean getSpecifiedOptional() {
		return this.specifiedOptional;
	}

	public void setSpecifiedOptional(Boolean optional) {
		if (ObjectTools.notEquals(optional, this.specifiedOptional)) {
			this.getAnnotationForUpdate().setOptional(optional);
			this.setSpecifiedOptional_(optional);
		}
	}

	protected void setSpecifiedOptional_(Boolean optional) {
		Boolean old = this.specifiedOptional;
		this.specifiedOptional = optional;
		this.firePropertyChanged(SPECIFIED_OPTIONAL_PROPERTY, old, optional);
	}

	protected Boolean buildSpecifiedOptional() {
		A annotation = this.getMappingAnnotation();
		return (annotation == null) ? null : annotation.getOptional();
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

	public JavaDerivedIdentity2_0 getDerivedIdentity() {
		return this.derivedIdentity;
	}

	protected JavaDerivedIdentity2_0 buildDerivedIdentity() {
		return this.isJpa2_0Compatible() ?
				this.getJpaFactory2_0().buildJavaDerivedIdentity(this) :
				new NullJavaDerivedIdentity2_0(this);
	}


	// ********** misc **********

	@Override
	protected FetchType buildDefaultFetch() {
		return DEFAULT_FETCH_TYPE;
	}

	@Override
	protected String buildDefaultTargetEntity() {
		return this.getPersistentAttribute().getSingleReferenceTargetTypeName();
	}


	// ********** Java completion proposals **********

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


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.derivedIdentity.validate(messages, reporter);
	}
}
