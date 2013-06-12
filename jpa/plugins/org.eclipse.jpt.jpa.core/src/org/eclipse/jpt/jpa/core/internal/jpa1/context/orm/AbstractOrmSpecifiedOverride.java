/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.BaseColumn;
import org.eclipse.jpt.jpa.core.context.Override_;
import org.eclipse.jpt.jpa.core.context.TableColumn;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualOverride;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextModel;
import org.eclipse.jpt.jpa.core.resource.orm.XmlOverride;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Specified <code>orm.xml</code> override
 */
public abstract class AbstractOrmSpecifiedOverride<P extends OrmOverrideContainer, X extends XmlOverride>
	extends AbstractOrmXmlContextModel<P>
	implements OrmSpecifiedOverride
{
	protected final X xmlOverride;

	protected String name;


	protected AbstractOrmSpecifiedOverride(P parent, X xmlOverride) {
		super(parent);
		this.xmlOverride = xmlOverride;
		this.name = xmlOverride.getName();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setName_(this.xmlOverride.getName());
	}


	// ********** name **********

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.setName_(name);
		this.xmlOverride.setName(name);
	}

	protected void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}


	// ********** specified/virtual **********

	public boolean isVirtual() {
		return false;
	}

	public OrmVirtualOverride convertToVirtual() {
		return this.getContainer().convertOverrideToVirtual(this);
	}


	// ********** misc **********

	protected P getContainer() {
		return this.parent;
	}

	public X getXmlOverride() {
		return this.xmlOverride;
	}

	protected void initializeFrom(Override_ oldOverride) {
		this.setName(oldOverride.getName());
	}

	public TypeMapping getTypeMapping() {
		return this.getContainer().getTypeMapping();
	}

	public Table resolveDbTable(String tableName) {
		return this.getContainer().resolveDbTable(tableName);
	}
	
	public String getDefaultTableName() {
		return this.getContainer().getDefaultTableName();
	}

	public JpaValidator buildColumnValidator(BaseColumn column, TableColumn.ParentAdapter parentAdapter) {
		return this.getContainer().buildColumnValidator(this, column, parentAdapter);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.buildValidator().validate(messages, reporter);
	}

	protected JpaValidator buildValidator() {
		return this.getContainer().buildOverrideValidator(this);
	}

	/**
	 * @see AbstractOrmOverrideContainer#getValidationTextRange()
	 */
	public TextRange getValidationTextRange() {
		TextRange textRange = this.xmlOverride.getValidationTextRange();
		// skip the container since it really doesn't have a text range
		// (also, this prevents a stack overflow)
		return (textRange != null) ? textRange : this.getContainer().getParent().getValidationTextRange();
	}

	public TextRange getNameTextRange() {
		return this.getValidationTextRange(this.xmlOverride.getNameTextRange());
	}


	public boolean tableNameIsInvalid(String tableName) {
		return this.getContainer().tableNameIsInvalid(tableName);
	}


	public Iterable<String> getCandidateTableNames() {
		return this.getContainer().getCandidateTableNames();
	}

	// ********** completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.nameTouches(pos)) {
			return this.getCandidateNames();
		}
		return null;
	}

	protected boolean nameTouches(int pos) {
		return this.xmlOverride.nameTouches(pos);
	}

	protected Iterable<String> getCandidateNames() {
		return this.getContainer().getAllOverridableNames();
	}
}
