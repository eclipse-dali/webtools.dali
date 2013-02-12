/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.libval;

import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.common.core.libprov.JptLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.common.core.libval.LibraryValidator;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;

class LibraryValidatorConfig {
	private final InternalLibraryValidatorManager manager;
	private final String id;
	private final String className;
	private /* final */ String pluginID;
	private /* final */ Expression enablementExpression;

	// lazily initialized
	private LibraryValidator libraryValidator;


	LibraryValidatorConfig(InternalLibraryValidatorManager manager, String id, String className) {
		super();
		this.manager = manager;
		this.id = id;
		this.className = className;
	}

	InternalLibraryValidatorManager getManager() {
		return this.manager;
	}

	String getID() {
		return this.id;
	}

	String getClassName() {
		return this.className;
	}

	String getPluginID() {
		return this.pluginID;
	}

	void setPluginID(String pluginID) {
		this.pluginID = pluginID;
	}

	Expression getEnablementExpression() {
		return this.enablementExpression;
	}

	void setEnablementExpression(Expression enablementExpression) {
		this.enablementExpression = enablementExpression;
	}

	boolean isEnabled(JptLibraryProviderInstallOperationConfig installConfig) {
		return (this.enablementExpression == null) || this.isEnabled_(installConfig);
	}

	/**
	 * Pre-condition: enablement expression is not <code>null</code>.
	 */
	private boolean isEnabled_(JptLibraryProviderInstallOperationConfig installConfig) {
		EvaluationContext evalContext = new EvaluationContext(null, installConfig);
		evalContext.setAllowPluginActivation(true);
		evalContext.addVariable(CONFIG_ENABLEMENT_EXPRESSION_VARIABLE, installConfig);
		evalContext.addVariable(LIBRARY_PROVIDER_ENABLEMENT_EXPRESSION_VARIABLE, installConfig.getLibraryProvider());

		try {
			// EvaluationResult.NOT_LOADED will return false
			return this.enablementExpression.evaluate(evalContext) == EvaluationResult.TRUE;
		} catch (CoreException ex) {
			JptCommonCorePlugin.instance().logError(ex);
			return false;
		}
	}
	private static final String CONFIG_ENABLEMENT_EXPRESSION_VARIABLE = "config";  //$NON-NLS-1$
	private static final String LIBRARY_PROVIDER_ENABLEMENT_EXPRESSION_VARIABLE = "libraryProvider"; //$NON-NLS-1$

	synchronized LibraryValidator getLibraryValidator() {
		if (this.libraryValidator == null) {
			this.libraryValidator = this.buildLibraryValidator();
		}
		return this.libraryValidator;
	}

	private LibraryValidator buildLibraryValidator() {
		return PlatformTools.instantiate(this.pluginID, this.manager.getExtensionPointName(), this.className, LibraryValidator.class);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.className);
	}


	// ********** enabled filter **********

	static class EnabledFilter
		extends Predicate.Adapter<LibraryValidatorConfig>
	{
		private final JptLibraryProviderInstallOperationConfig installConfig;
		EnabledFilter(JptLibraryProviderInstallOperationConfig installConfig) {
			super();
			this.installConfig = installConfig;
		}
		@Override
		public boolean evaluate(LibraryValidatorConfig config) {
			return config.isEnabled(this.installConfig);
		}
	}


	// ********** library validator transformer **********

	static final Transformer<LibraryValidatorConfig, LibraryValidator> LIBRARY_VALIDATOR_TRANSFORMER = new LibraryValidatorTransformer();
	static class LibraryValidatorTransformer
		extends TransformerAdapter<LibraryValidatorConfig, LibraryValidator>
	{
		@Override
		public LibraryValidator transform(LibraryValidatorConfig config) {
			return config.getLibraryValidator();
		}
	}
}
