/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.java;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.jpa2.context.DerivedIdentityStrategy2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaDerivedIdentity2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaIdDerivedIdentityStrategy2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaMapsIdDerivedIdentityStrategy2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaSingleRelationshipMapping2_0;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaDerivedIdentity2_0
	extends AbstractJavaJpaContextNode
	implements JavaDerivedIdentity2_0
{
	protected DerivedIdentityStrategy2_0 strategy;

	protected final JavaIdDerivedIdentityStrategy2_0 idStrategy;

	protected final JavaMapsIdDerivedIdentityStrategy2_0 mapsIdStrategy;


	public GenericJavaDerivedIdentity2_0(JavaSingleRelationshipMapping2_0 parent) {
		super(parent);
		this.idStrategy = this.buildIdStrategy();
		this.mapsIdStrategy = this.buildMapsIdStrategy();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.idStrategy.synchronizeWithResourceModel();
		this.mapsIdStrategy.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
		this.idStrategy.update();
		this.mapsIdStrategy.update();
		this.updateStrategy();
	}


	// ********** predominant strategy **********

	public DerivedIdentityStrategy2_0 getPredominantDerivedIdentityStrategy() {
		return this.strategy;
	}

	protected void setStrategy(DerivedIdentityStrategy2_0 strategy) {
		DerivedIdentityStrategy2_0 old = this.strategy;
		this.strategy = strategy;
		this.firePropertyChanged(PREDOMINANT_DERIVED_IDENTITY_STRATEGY_PROPERTY, old, strategy);
	}

	protected void updateStrategy() {
		this.setStrategy(this.buildStrategy());
	}

	protected DerivedIdentityStrategy2_0 buildStrategy() {
		if (this.mapsIdStrategy.isSpecified()) {
			return this.mapsIdStrategy;
		}
		if (this.idStrategy.isSpecified()) {
			return this.idStrategy;
		}
		return null;
	}


	// ********** null strategy **********

	public boolean usesNullDerivedIdentityStrategy() {
		return this.strategy == null;
	}

	public void setNullDerivedIdentityStrategy() {
		this.mapsIdStrategy.removeStrategy();
		this.idStrategy.removeStrategy();
		this.updateStrategy();
	}


	// ********** ID strategy **********

	public JavaIdDerivedIdentityStrategy2_0 getIdDerivedIdentityStrategy() {
		return this.idStrategy;
	}

	public void setIdDerivedIdentityStrategy() {
		this.idStrategy.addStrategy();
		this.mapsIdStrategy.removeStrategy();
		this.updateStrategy();
	}

	public void unsetIdDerivedIdentityStrategy() {
		this.idStrategy.removeStrategy();
		this.updateStrategy();
	}

	public boolean usesIdDerivedIdentityStrategy() {
		return this.strategy == this.idStrategy;
	}

	protected JavaIdDerivedIdentityStrategy2_0 buildIdStrategy() {
		return new GenericJavaIdDerivedIdentityStrategy2_0(this);
	}


	// ********** maps ID strategy **********

	public JavaMapsIdDerivedIdentityStrategy2_0 getMapsIdDerivedIdentityStrategy() {
		return this.mapsIdStrategy;
	}

	public void setMapsIdDerivedIdentityStrategy() {
		this.mapsIdStrategy.addStrategy();
		this.idStrategy.removeStrategy();
		this.updateStrategy();
	}

	public void unsetMapsIdDerivedIdentityStrategy() {
		this.mapsIdStrategy.removeStrategy();
		this.updateStrategy();
	}

	public boolean usesMapsIdDerivedIdentityStrategy() {
		return this.strategy == this.mapsIdStrategy;
	}

	protected JavaMapsIdDerivedIdentityStrategy2_0 buildMapsIdStrategy() {
		return new GenericJavaMapsIdDerivedIdentityStrategy2_0(this);
	}


	// ********** misc **********

	@Override
	public JavaSingleRelationshipMapping2_0 getParent() {
		return (JavaSingleRelationshipMapping2_0) super.getParent();
	}

	public JavaSingleRelationshipMapping2_0 getMapping() {
		return this.getParent();
	}


	// ********** java completion proposals **********

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.mapsIdStrategy.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		result = this.idStrategy.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		return null;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.idStrategy.validate(messages, reporter, astRoot);
		this.mapsIdStrategy.validate(messages, reporter, astRoot);
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getMapping().getValidationTextRange(astRoot);
	}
}
