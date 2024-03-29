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

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;
import org.eclipse.jpt.jpa.core.context.BaseColumn;
import org.eclipse.jpt.jpa.core.context.Override_;
import org.eclipse.jpt.jpa.core.context.TableColumn;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualOverride;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaOverrideContainer2_0;
import org.eclipse.jpt.jpa.core.resource.java.OverrideAnnotation;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Specified Java override
 */
public abstract class AbstractJavaSpecifiedOverride<P extends JavaOverrideContainer, A extends OverrideAnnotation>
	extends AbstractJavaContextModel<P>
	implements JavaSpecifiedOverride
{
	protected final A overrideAnnotation;

	protected String name;


	protected AbstractJavaSpecifiedOverride(P parent, A overrideAnnotation) {
		super(parent);
		this.overrideAnnotation = overrideAnnotation;
		this.name = this.buildName();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setName_(this.buildName());
	}


	// ********** name **********

	public String getName() {
		return this.name;
	}

	/**
	 * Strip the prefix (if necessary) before storing the name in the context
	 * model. Pass the unchanged name to the annotation.
	 */
	public void setName(String name) {
		this.overrideAnnotation.setName(name);
		this.setName_(this.stripPrefix(name));
	}

	protected void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}

	/**
	 * Strip the prefix (if necessary) from the name taken from the annotation
	 * before storing it in the context model.
	 */
	protected String buildName() {
		return this.stripPrefix(this.overrideAnnotation.getName());
	}

	protected String stripPrefix(String rawName) {
		if (rawName == null) {
			return null;
		}
		String prefix = this.getPossiblePrefix();
		if (prefix == null) {
			return rawName;
		}
		int prefixLength = prefix.length();
		if ((rawName.length() > prefixLength) &&
				(rawName.charAt(prefixLength) == '.') &&
				rawName.startsWith(prefix)) {
			return rawName.substring(prefixLength + 1);
		}
		return rawName;
	}

	protected String getPossiblePrefix() {
		return this.isJpa2_0Compatible() ? this.getContainer2_0().getPossiblePrefix() : null;
	}


	// ********** specified/virtual **********

	public boolean isVirtual() {
		return false;
	}

	public JavaVirtualOverride convertToVirtual() {
		return this.getContainer().convertOverrideToVirtual(this);
	}


	// ********** misc **********

	protected P getContainer() {
		return this.parent;
	}

	protected JavaOverrideContainer2_0 getContainer2_0() {
		return (JavaOverrideContainer2_0) this.getContainer();
	}

	public A getOverrideAnnotation() {
		return this.overrideAnnotation;
	}

	protected void initializeFrom(Override_ virtualOverride) {
		this.setName(this.prefix(virtualOverride.getName()));
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

	protected String prefix(String oldName) {
		if (oldName == null) {
			return null;
		}
		String prefix = this.getWritePrefix();
		return (prefix == null) ? oldName : (prefix + '.' + oldName);
	}

	protected String getWritePrefix() {
		return this.isJpa2_0Compatible() ? this.getContainer2_0().getWritePrefix() : null;
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ********** Java completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.nameTouches(pos)) {
			return this.getJavaCandidateNames();
		}
		return null;
	}

	protected boolean nameTouches(int pos) {
		return this.overrideAnnotation.nameTouches(pos);
	}

	protected Iterable<String> getJavaCandidateNames() {
		return new TransformationIterable<String, String>(this.getCandidateNames(),
				StringTools.JAVA_STRING_LITERAL_CONTENT_TRANSFORMER);
	}

	protected abstract Iterable<String> getCandidateNames();


	// ********** validation **********
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.buildValidator().validate(messages, reporter);
	}

	protected JpaValidator buildValidator() {
		return this.getContainer().buildOverrideValidator(this);
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.overrideAnnotation.getTextRange();
		return (textRange != null) ? textRange : this.getContainer().getValidationTextRange();
	}

	public TextRange getNameTextRange() {
		return this.getValidationTextRange(this.overrideAnnotation.getNameTextRange());
	}

	public boolean tableNameIsInvalid(String tableName) {
		return this.getContainer().tableNameIsInvalid(tableName);
	}

	public Iterable<String> getCandidateTableNames() {
		return this.getContainer().getCandidateTableNames();
	}
}
