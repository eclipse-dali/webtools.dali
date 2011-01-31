/*******************************************************************************
 *  Copyright (c) 2010, 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.common.core.internal.libval;

import static org.eclipse.jst.common.project.facet.core.internal.FacetedProjectFrameworkJavaPlugin.log;
import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jpt.common.core.internal.XPointUtil;
import org.eclipse.jpt.common.core.libprov.JptLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.common.core.libval.LibraryValidator;

public class LibraryValidatorConfig {
	
	public static final String CONFIG_EXPR_VAR = "config";  //$NON-NLS-1$
	public static final String LIBRARY_PROVIDER_EXPR_VAR = "libraryProvider"; //$NON-NLS-1$
	
	
	private String id;
	private String pluginId;
	private String className;
	private Expression enablementCondition;
	
	
	LibraryValidatorConfig() {
		super();
	}
	
	
	public String getId() {
		return this.id;
	}
	
	void setId(String id) {
		this.id = id;
	}
	
	public String getPluginId() {
		return this.pluginId;
	}
	
	void setPluginId(String pluginId) {
		this.pluginId = pluginId;
	}
	
	public String getClassName() {
		return this.className;
	}
	
	void setClassName(String className) {
		this.className = className;
	}
	
	public Expression getEnablementCondition() {
		return this.enablementCondition;
	}
	
	void setEnablementCondition(Expression enablementCondition) {
		this.enablementCondition = enablementCondition;
	}
	
	public LibraryValidator getLibraryValidator() {
		return XPointUtil.instantiate(
				this.pluginId, LibraryValidatorManager.QUALIFIED_EXTENSION_POINT_ID, 
				this.className, LibraryValidator.class);
	}
	
	public boolean isEnabledFor(JptLibraryProviderInstallOperationConfig config) {
		EvaluationContext evalContext = new EvaluationContext(null, config);
		evalContext.setAllowPluginActivation(true);
		evalContext.addVariable(CONFIG_EXPR_VAR, config);
		evalContext.addVariable(LIBRARY_PROVIDER_EXPR_VAR, config.getLibraryProvider());
		
		if (this.enablementCondition != null) {
			try {
				EvaluationResult evalResult = this.enablementCondition.evaluate(evalContext);
				
				if (evalResult == EvaluationResult.FALSE) {
					return false;
				}
			}
			catch (CoreException e) {
				log(e);
			}
		}
		
		return true;
	}
}
